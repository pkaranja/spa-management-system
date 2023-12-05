package co.tz.qroo.spa.staff;

import co.tz.qroo.spa.service.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StaffRepository extends JpaRepository<Staff, UUID> {

    List<Staff> findAllByServices(Service service);

    boolean existsByUserId(UUID userId);

}
