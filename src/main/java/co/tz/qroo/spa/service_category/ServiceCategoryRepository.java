package co.tz.qroo.spa.service_category;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
}
