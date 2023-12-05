package co.tz.qroo.spa.payment_method;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    boolean existsByPaymentTypeIgnoreCase(String paymentType);

}
