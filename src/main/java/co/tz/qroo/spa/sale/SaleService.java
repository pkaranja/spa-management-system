package co.tz.qroo.spa.sale;

import co.tz.qroo.spa.appointment.Appointment;
import co.tz.qroo.spa.appointment.AppointmentRepository;
import co.tz.qroo.spa.attendant.Attendant;
import co.tz.qroo.spa.attendant.AttendantRepository;
import co.tz.qroo.spa.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final AttendantRepository attendantRepository;
    private final AppointmentRepository appointmentRepository;

    public SaleService(final SaleRepository saleRepository,
            final AttendantRepository attendantRepository,
            final AppointmentRepository appointmentRepository) {
        this.saleRepository = saleRepository;
        this.attendantRepository = attendantRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<SaleDTO> findAll() {
        final List<Sale> sales = saleRepository.findAll(Sort.by("id"));
        return sales.stream()
                .map(sale -> mapToDTO(sale, new SaleDTO()))
                .toList();
    }

    public SaleDTO get(final UUID id) {
        return saleRepository.findById(id)
                .map(sale -> mapToDTO(sale, new SaleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final SaleDTO saleDTO) {
        final Sale sale = new Sale();
        mapToEntity(saleDTO, sale);
        return saleRepository.save(sale).getId();
    }

    public void update(final UUID id, final SaleDTO saleDTO) {
        final Sale sale = saleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(saleDTO, sale);
        saleRepository.save(sale);
    }

    public void delete(final UUID id) {
        saleRepository.deleteById(id);
    }

    private SaleDTO mapToDTO(final Sale sale, final SaleDTO saleDTO) {
        saleDTO.setId(sale.getId());
        saleDTO.setSaleType(sale.getSaleType());
        saleDTO.setSaleItem(sale.getSaleItem());
        saleDTO.setQuantity(sale.getQuantity());
        saleDTO.setTotalAmount(sale.getTotalAmount());
        saleDTO.setDiscountAmount(sale.getDiscountAmount());
        saleDTO.setPaidAmount(sale.getPaidAmount());
        saleDTO.setExternalTransactionId(sale.getExternalTransactionId());
        saleDTO.setAttendants(sale.getAttendants().stream()
                .map(attendant -> attendant.getId())
                .toList());
        saleDTO.setAppointment(sale.getAppointment() == null ? null : sale.getAppointment().getId());
        return saleDTO;
    }

    private Sale mapToEntity(final SaleDTO saleDTO, final Sale sale) {
        sale.setSaleType(saleDTO.getSaleType());
        sale.setSaleItem(saleDTO.getSaleItem());
        sale.setQuantity(saleDTO.getQuantity());
        sale.setTotalAmount(saleDTO.getTotalAmount());
        sale.setDiscountAmount(saleDTO.getDiscountAmount());
        sale.setPaidAmount(saleDTO.getPaidAmount());
        sale.setExternalTransactionId(saleDTO.getExternalTransactionId());
        final List<Attendant> attendants = attendantRepository.findAllById(
                saleDTO.getAttendants() == null ? Collections.emptyList() : saleDTO.getAttendants());
        if (attendants.size() != (saleDTO.getAttendants() == null ? 0 : saleDTO.getAttendants().size())) {
            throw new NotFoundException("one of attendants not found");
        }
        sale.setAttendants(attendants.stream().collect(Collectors.toSet()));
        final Appointment appointment = saleDTO.getAppointment() == null ? null : appointmentRepository.findById(saleDTO.getAppointment())
                .orElseThrow(() -> new NotFoundException("appointment not found"));
        sale.setAppointment(appointment);
        return sale;
    }

}
