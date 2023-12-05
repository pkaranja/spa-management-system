package co.tz.qroo.spa.sale;

import co.tz.qroo.spa.staff.Staff;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SaleRepository extends JpaRepository<Sale, UUID> {

    List<Sale> findAllByStaff(Staff staff);

}
