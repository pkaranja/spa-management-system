package co.tz.qroo.spa.attendant;

import co.tz.qroo.spa.service.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttendantRepository extends JpaRepository<Attendant, UUID> {

    List<Attendant> findAllByServices(Service service);

    boolean existsByUserId(UUID userId);

}
