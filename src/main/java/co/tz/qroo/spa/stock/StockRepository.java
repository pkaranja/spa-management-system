package co.tz.qroo.spa.stock;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock, UUID> {
}
