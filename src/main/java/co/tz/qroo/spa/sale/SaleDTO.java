package co.tz.qroo.spa.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SaleDTO {

    private UUID id;

    @NotNull
    private SaleType saleType;

    @NotNull
    private UUID saleItem;

    @NotNull
    private Integer quantity;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "52.08")
    private BigDecimal totalAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "67.08")
    private BigDecimal discountAmount;

    @NotNull
    @Size(max = 255)
    private String paidAmount;

    @NotNull
    @Size(max = 255)
    private String externalTransactionId;

    private List<UUID> staff;

    private UUID appointment;

}
