package co.tz.qroo.spa.appointment;

import co.tz.qroo.spa.kyc.KycRepository;
import co.tz.qroo.spa.service.ServiceRepository;
import co.tz.qroo.spa.service_package.ServicePackage;
import co.tz.qroo.spa.service_package.ServicePackageRepository;
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
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final ServicePackageRepository servicePackageRepository;
    private final KycRepository kycRepository;

    public AppointmentService(final AppointmentRepository appointmentRepository,
            final ServiceRepository serviceRepository,
            final ServicePackageRepository servicePackageRepository,
            final KycRepository kycRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.servicePackageRepository = servicePackageRepository;
        this.kycRepository = kycRepository;
    }

    public List<AppointmentDTO> findAll() {
        final List<Appointment> appointments = appointmentRepository.findAll(Sort.by("id"));
        return appointments.stream()
                .map(appointment -> mapToDTO(appointment, new AppointmentDTO()))
                .toList();
    }

    public AppointmentDTO get(final UUID id) {
        return appointmentRepository.findById(id)
                .map(appointment -> mapToDTO(appointment, new AppointmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final AppointmentDTO appointmentDTO) {
        final Appointment appointment = new Appointment();
        mapToEntity(appointmentDTO, appointment);
        return appointmentRepository.save(appointment).getId();
    }

    public void update(final UUID id, final AppointmentDTO appointmentDTO) {
        final Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(appointmentDTO, appointment);
        appointmentRepository.save(appointment);
    }

    public void delete(final UUID id) {
        final Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        kycRepository.findAllByAppointments(appointment)
                .forEach(kyc -> kyc.getAppointments().remove(appointment));
        appointmentRepository.delete(appointment);
    }

    private AppointmentDTO mapToDTO(final Appointment appointment,
            final AppointmentDTO appointmentDTO) {
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
        appointmentDTO.setServices(appointment.getServices().stream()
                .map(service -> service.getId())
                .toList());
        appointmentDTO.setPackages(appointment.getPackages().stream()
                .map(servicePackage -> servicePackage.getId())
                .toList());
        return appointmentDTO;
    }

    private Appointment mapToEntity(final AppointmentDTO appointmentDTO,
            final Appointment appointment) {
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        final List<co.tz.qroo.spa.service.Service> services = serviceRepository.findAllById(
                appointmentDTO.getServices() == null ? Collections.emptyList() : appointmentDTO.getServices());
        if (services.size() != (appointmentDTO.getServices() == null ? 0 : appointmentDTO.getServices().size())) {
            throw new NotFoundException("one of services not found");
        }
        appointment.setServices(services.stream().collect(Collectors.toSet()));
        final List<ServicePackage> packages = servicePackageRepository.findAllById(
                appointmentDTO.getPackages() == null ? Collections.emptyList() : appointmentDTO.getPackages());
        if (packages.size() != (appointmentDTO.getPackages() == null ? 0 : appointmentDTO.getPackages().size())) {
            throw new NotFoundException("one of packages not found");
        }
        appointment.setPackages(packages.stream().collect(Collectors.toSet()));
        return appointment;
    }

}
