package co.tz.qroo.spa.appointment;

import co.tz.qroo.spa.service.Service;
import co.tz.qroo.spa.service_package.ServicePackage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByServices(Service service);

    List<Appointment> findAllByPackages(ServicePackage servicePackage);

}
