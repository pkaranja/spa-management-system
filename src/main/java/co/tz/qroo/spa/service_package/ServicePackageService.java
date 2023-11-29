package co.tz.qroo.spa.service_package;

import co.tz.qroo.spa.appointment.AppointmentRepository;
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
public class ServicePackageService {

    private final ServicePackageRepository servicePackageRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;

    public ServicePackageService(final ServicePackageRepository servicePackageRepository,
            final ServiceRepository serviceRepository,
            final AppointmentRepository appointmentRepository) {
        this.servicePackageRepository = servicePackageRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<ServicePackageDTO> findAll() {
        final List<ServicePackage> servicePackages = servicePackageRepository.findAll(Sort.by("id"));
        return servicePackages.stream()
                .map(servicePackage -> mapToDTO(servicePackage, new ServicePackageDTO()))
                .toList();
    }

    public ServicePackageDTO get(final UUID id) {
        return servicePackageRepository.findById(id)
                .map(servicePackage -> mapToDTO(servicePackage, new ServicePackageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ServicePackageDTO servicePackageDTO) {
        final ServicePackage servicePackage = new ServicePackage();
        mapToEntity(servicePackageDTO, servicePackage);
        return servicePackageRepository.save(servicePackage).getId();
    }

    public void update(final UUID id, final ServicePackageDTO servicePackageDTO) {
        final ServicePackage servicePackage = servicePackageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(servicePackageDTO, servicePackage);
        servicePackageRepository.save(servicePackage);
    }

    public void delete(final UUID id) {
        final ServicePackage servicePackage = servicePackageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        appointmentRepository.findAllByPackages(servicePackage)
                .forEach(appointment -> appointment.getPackages().remove(servicePackage));
        servicePackageRepository.delete(servicePackage);
    }

    private ServicePackageDTO mapToDTO(final ServicePackage servicePackage,
            final ServicePackageDTO servicePackageDTO) {
        servicePackageDTO.setId(servicePackage.getId());
        servicePackageDTO.setPackageName(servicePackage.getPackageName());
        servicePackageDTO.setActive(servicePackage.getActive());
        servicePackageDTO.setServiceStartTime(servicePackage.getServiceStartTime());
        servicePackageDTO.setServiceEndTime(servicePackage.getServiceEndTime());
        servicePackageDTO.setServices(servicePackage.getServices().stream()
                .map(service -> service.getId())
                .toList());
        return servicePackageDTO;
    }

    private ServicePackage mapToEntity(final ServicePackageDTO servicePackageDTO,
            final ServicePackage servicePackage) {
        servicePackage.setPackageName(servicePackageDTO.getPackageName());
        servicePackage.setActive(servicePackageDTO.getActive());
        servicePackage.setServiceStartTime(servicePackageDTO.getServiceStartTime());
        servicePackage.setServiceEndTime(servicePackageDTO.getServiceEndTime());
        final List<co.tz.qroo.spa.service.Service> services = serviceRepository.findAllById(
                servicePackageDTO.getServices() == null ? Collections.emptyList() : servicePackageDTO.getServices());
        if (services.size() != (servicePackageDTO.getServices() == null ? 0 : servicePackageDTO.getServices().size())) {
            throw new NotFoundException("one of services not found");
        }
        servicePackage.setServices(services.stream().collect(Collectors.toSet()));
        return servicePackage;
    }

}
