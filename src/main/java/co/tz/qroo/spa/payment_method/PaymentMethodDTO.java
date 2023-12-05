package co.tz.qroo.spa.payment_method;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentMethodDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String paymentType;

    @NotNull
    private Boolean active;

}
