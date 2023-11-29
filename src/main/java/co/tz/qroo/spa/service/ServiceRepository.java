package co.tz.qroo.spa.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, UUID> {

    boolean existsByServiceNameIgnoreCase(String serviceName);

}
