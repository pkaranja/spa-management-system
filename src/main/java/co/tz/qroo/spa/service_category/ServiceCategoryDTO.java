package co.tz.qroo.spa.service_category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceCategoryDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String categoryName;

    @NotNull
    private Boolean active;

}
