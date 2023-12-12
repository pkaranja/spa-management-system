package co.tz.qroo.spa.staff;

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
public class StaffService {

    private final StaffRepository staffRepository;
    private final ServiceRepository serviceRepository;
    private final SaleRepository saleRepository;

    public StaffService(final StaffRepository staffRepository,
            final ServiceRepository serviceRepository, final SaleRepository saleRepository) {
        this.staffRepository = staffRepository;
        this.serviceRepository = serviceRepository;
        this.saleRepository = saleRepository;
    }

    public List<StaffDTO> findAll() {
        final List<Staff> staffs = staffRepository.findAll(Sort.by("id"));
        return staffs.stream()
                .map(staff -> mapToDTO(staff, new StaffDTO()))
                .toList();
    }

    public StaffDTO get(final UUID id) {
        return staffRepository.findById(id)
                .map(staff -> mapToDTO(staff, new StaffDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final StaffDTO staffDTO) {
        final Staff staff = new Staff();
        mapToEntity(staffDTO, staff);
        return staffRepository.save(staff).getId();
    }

    public void update(final UUID id, final StaffDTO staffDTO) {
        final Staff staff = staffRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(staffDTO, staff);
        staffRepository.save(staff);
    }

    public void delete(final UUID id) {
        final Staff staff = staffRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        saleRepository.findAllByStaff(staff)
                .forEach(sale -> sale.getStaff().remove(staff));
        staffRepository.delete(staff);
    }

    private StaffDTO mapToDTO(final Staff staff, final StaffDTO staffDTO) {
        staffDTO.setId(staff.getId());
        staffDTO.setUserId(staff.getUserId());
        staffDTO.setFirstName(staff.getFirstName());
        staffDTO.setLastName(staff.getLastName());
        staffDTO.setPhoneNumber(staff.getPhoneNumber());
        staffDTO.setEmailAddress(staff.getEmailAddress());
        staffDTO.setGender(staff.getGender());
        staffDTO.setDateOfBirth(staff.getDateOfBirth());
        staffDTO.setDesignation(staff.getDesignation());
        staffDTO.setCheckinTime(staff.getCheckinTime());
        staffDTO.setCheckoutTime(staff.getCheckoutTime());
        staffDTO.setJoiningDate(staff.getJoiningDate());
        staffDTO.setActive(staff.getActive());
        staffDTO.setSalary(staff.getSalary());
        staffDTO.setCommissionAmount(staff.getCommissionAmount());
        staffDTO.setCommissionPercent(staff.getCommissionPercent());
        staffDTO.setOffDays(staff.getOffDays());
        staffDTO.setServices(staff.getServices().stream()
                .map(service -> service.getId())
                .toList());
        return staffDTO;
    }

    private Staff mapToEntity(final StaffDTO staffDTO, final Staff staff) {
        staff.setUserId(staffDTO.getUserId());
        staff.setFirstName(staffDTO.getFirstName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setEmailAddress(staffDTO.getEmailAddress());
        staff.setGender(staffDTO.getGender());
        staff.setDateOfBirth(staffDTO.getDateOfBirth());
        staff.setDesignation(staffDTO.getDesignation());
        staff.setCheckinTime(staffDTO.getCheckinTime());
        staff.setCheckoutTime(staffDTO.getCheckoutTime());
        staff.setJoiningDate(staffDTO.getJoiningDate());
        staff.setActive(staffDTO.getActive());
        staff.setSalary(staffDTO.getSalary());
        staff.setCommissionAmount(staffDTO.getCommissionAmount());
        staff.setCommissionPercent(staffDTO.getCommissionPercent());
        staff.setOffDays(staffDTO.getOffDays());
        final List<co.tz.qroo.spa.service.Service> services = serviceRepository.findAllById(
                staffDTO.getServices() == null ? Collections.emptyList() : staffDTO.getServices());
        if (services.size() != (staffDTO.getServices() == null ? 0 : staffDTO.getServices().size())) {
            throw new NotFoundException("one of services not found");
        }
        staff.setServices(services.stream().collect(Collectors.toSet()));
        return staff;
    }

    public boolean userIdExists(final UUID userId) {
        return staffRepository.existsByUserId(userId);
    }

}
