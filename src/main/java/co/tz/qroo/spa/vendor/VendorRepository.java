package co.tz.qroo.spa.vendor;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    boolean existsByNameIgnoreCase(String name);

}
