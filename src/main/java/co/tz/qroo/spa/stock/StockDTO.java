package co.tz.qroo.spa.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StockDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String productName;

    @Size(max = 255)
    private String itemCode;

    @NotNull
    @Size(max = 255)
    private String cost;

    @Size(max = 255)
    private String sellingPrice;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer alertQuantity;

    private String description;

    private Boolean active;

    @NotNull
    private UUID vendor;

}
