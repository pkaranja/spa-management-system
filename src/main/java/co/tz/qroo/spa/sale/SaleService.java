package co.tz.qroo.spa.sale;

import co.tz.qroo.spa.appointment.Appointment;
import co.tz.qroo.spa.appointment.AppointmentRepository;
import co.tz.qroo.spa.staff.Staff;
import co.tz.qroo.spa.staff.StaffRepository;
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
    private final StaffRepository staffRepository;
    private final AppointmentRepository appointmentRepository;

    public SaleService(final SaleRepository saleRepository, final StaffRepository staffRepository,
            final AppointmentRepository appointmentRepository) {
        this.saleRepository = saleRepository;
        this.staffRepository = staffRepository;
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
        saleDTO.setStaff(sale.getStaff().stream()
                .map(staff -> staff.getId())
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
        final List<Staff> staff = staffRepository.findAllById(
                saleDTO.getStaff() == null ? Collections.emptyList() : saleDTO.getStaff());
        if (staff.size() != (saleDTO.getStaff() == null ? 0 : saleDTO.getStaff().size())) {
            throw new NotFoundException("one of staff not found");
        }
        sale.setStaff(staff.stream().collect(Collectors.toSet()));
        final Appointment appointment = saleDTO.getAppointment() == null ? null : appointmentRepository.findById(saleDTO.getAppointment())
                .orElseThrow(() -> new NotFoundException("appointment not found"));
        sale.setAppointment(appointment);
        return sale;
    }

}
