package co.tz.qroo.spa.service;

import co.tz.qroo.spa.appointment.AppointmentRepository;
import co.tz.qroo.spa.attendant.AttendantRepository;
import co.tz.qroo.spa.service_category.ServiceCategory;
import co.tz.qroo.spa.service_category.ServiceCategoryRepository;
import co.tz.qroo.spa.service_package.ServicePackageRepository;
import co.tz.qroo.spa.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;


@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServicePackageRepository servicePackageRepository;
    private final AppointmentRepository appointmentRepository;
    private final AttendantRepository attendantRepository;

    public ServiceService(final ServiceRepository serviceRepository,
            final ServiceCategoryRepository serviceCategoryRepository,
            final ServicePackageRepository servicePackageRepository,
            final AppointmentRepository appointmentRepository,
            final AttendantRepository attendantRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.servicePackageRepository = servicePackageRepository;
        this.appointmentRepository = appointmentRepository;
        this.attendantRepository = attendantRepository;
    }

    public List<ServiceDTO> findAll() {
        final List<Service> services = serviceRepository.findAll(Sort.by("id"));
        return services.stream()
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .toList();
    }

    public ServiceDTO get(final UUID id) {
        return serviceRepository.findById(id)
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ServiceDTO serviceDTO) {
        final Service service = new Service();
        mapToEntity(serviceDTO, service);
        return serviceRepository.save(service).getId();
    }

    public void update(final UUID id, final ServiceDTO serviceDTO) {
        final Service service = serviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serviceDTO, service);
        serviceRepository.save(service);
    }

    public void delete(final UUID id) {
        final Service service = serviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        servicePackageRepository.findAllByServices(service)
                .forEach(servicePackage -> servicePackage.getServices().remove(service));
        appointmentRepository.findAllByServices(service)
                .forEach(appointment -> appointment.getServices().remove(service));
        attendantRepository.findAllByServices(service)
                .forEach(attendant -> attendant.getServices().remove(service));
        serviceRepository.delete(service);
    }

    private ServiceDTO mapToDTO(final Service service, final ServiceDTO serviceDTO) {
        serviceDTO.setId(service.getId());
        serviceDTO.setServiceName(service.getServiceName());
        serviceDTO.setPrice(service.getPrice());
        serviceDTO.setServiceStartTime(service.getServiceStartTime());
        serviceDTO.setServiceEndTime(service.getServiceEndTime());
        serviceDTO.setActive(service.getActive());
        serviceDTO.setCategory(service.getCategory() == null ? null : service.getCategory().getId());
        serviceDTO.setCategoryName(service.getCategory() == null ? null : service.getCategory().getCategoryName());
        return serviceDTO;
    }

    private Service mapToEntity(final ServiceDTO serviceDTO, final Service service) {
        service.setServiceName(serviceDTO.getServiceName());
        service.setPrice(serviceDTO.getPrice());
        service.setServiceStartTime(serviceDTO.getServiceStartTime());
        service.setServiceEndTime(serviceDTO.getServiceEndTime());
        service.setActive(serviceDTO.getActive());
        final ServiceCategory category = serviceDTO.getCategory() == null ? null : serviceCategoryRepository.findById(serviceDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        service.setCategory(category);
        return service;
    }

    public boolean serviceNameExists(final String serviceName) {
        return serviceRepository.existsByServiceNameIgnoreCase(serviceName);
    }

}
