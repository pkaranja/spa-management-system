package co.tz.qroo.spa.attendant;

import co.tz.qroo.spa.sale.SaleRepository;
import co.tz.qroo.spa.service.ServiceRepository;
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
public class AttendantService {

    private final AttendantRepository attendantRepository;
    private final ServiceRepository serviceRepository;
    private final SaleRepository saleRepository;

    public AttendantService(final AttendantRepository attendantRepository,
            final ServiceRepository serviceRepository, final SaleRepository saleRepository) {
        this.attendantRepository = attendantRepository;
        this.serviceRepository = serviceRepository;
        this.saleRepository = saleRepository;
    }

    public List<AttendantDTO> findAll() {
        final List<Attendant> attendants = attendantRepository.findAll(Sort.by("id"));
        return attendants.stream()
                .map(attendant -> mapToDTO(attendant, new AttendantDTO()))
                .toList();
    }

    public AttendantDTO get(final UUID id) {
        return attendantRepository.findById(id)
                .map(attendant -> mapToDTO(attendant, new AttendantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final AttendantDTO attendantDTO) {
        final Attendant attendant = new Attendant();
        mapToEntity(attendantDTO, attendant);
        return attendantRepository.save(attendant).getId();
    }

    public void update(final UUID id, final AttendantDTO attendantDTO) {
        final Attendant attendant = attendantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(attendantDTO, attendant);
        attendantRepository.save(attendant);
    }

    public void delete(final UUID id) {
        final Attendant attendant = attendantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        saleRepository.findAllByAttendants(attendant)
                .forEach(sale -> sale.getAttendants().remove(attendant));
        attendantRepository.delete(attendant);
    }

    private AttendantDTO mapToDTO(final Attendant attendant, final AttendantDTO attendantDTO) {
        attendantDTO.setId(attendant.getId());
        attendantDTO.setUserId(attendant.getUserId());
        attendantDTO.setFirstName(attendant.getFirstName());
        attendantDTO.setLastName(attendant.getLastName());
        attendantDTO.setPhoneNumber(attendant.getPhoneNumber());
        attendantDTO.setEmailAddress(attendant.getEmailAddress());
        attendantDTO.setGender(attendant.getGender());
        attendantDTO.setDateOfBirth(attendant.getDateOfBirth());
        attendantDTO.setActive(attendant.getActive());
        attendantDTO.setAvailabilityStartTime(attendant.getAvailabilityStartTime());
        attendantDTO.setAvailabilityEndTime(attendant.getAvailabilityEndTime());
        attendantDTO.setServices(attendant.getServices().stream()
                .map(service -> service.getId())
                .toList());
        return attendantDTO;
    }

    private Attendant mapToEntity(final AttendantDTO attendantDTO, final Attendant attendant) {
        attendant.setUserId(attendantDTO.getUserId());
        attendant.setFirstName(attendantDTO.getFirstName());
        attendant.setLastName(attendantDTO.getLastName());
        attendant.setPhoneNumber(attendantDTO.getPhoneNumber());
        attendant.setEmailAddress(attendantDTO.getEmailAddress());
        attendant.setGender(attendantDTO.getGender());
        attendant.setDateOfBirth(attendantDTO.getDateOfBirth());
        attendant.setActive(attendantDTO.getActive());
        attendant.setAvailabilityStartTime(attendantDTO.getAvailabilityStartTime());
        attendant.setAvailabilityEndTime(attendantDTO.getAvailabilityEndTime());
        final List<co.tz.qroo.spa.service.Service> services = serviceRepository.findAllById(
                attendantDTO.getServices() == null ? Collections.emptyList() : attendantDTO.getServices());
        if (services.size() != (attendantDTO.getServices() == null ? 0 : attendantDTO.getServices().size())) {
            throw new NotFoundException("one of services not found");
        }
        attendant.setServices(services.stream().collect(Collectors.toSet()));
        return attendant;
    }

    public boolean userIdExists(final UUID userId) {
        return attendantRepository.existsByUserId(userId);
    }

}
