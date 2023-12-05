package co.tz.qroo.spa.business;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusinessRepository extends JpaRepository<Business, UUID> {

    boolean existsByBusinessNameIgnoreCase(String businessName);

    boolean existsByAdminEmailIgnoreCase(String adminEmail);

}
