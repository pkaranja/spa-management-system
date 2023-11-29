package co.tz.qroo.spa.service_package;

import co.tz.qroo.spa.service.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServicePackageRepository extends JpaRepository<ServicePackage, UUID> {

    List<ServicePackage> findAllByServices(Service service);

}
