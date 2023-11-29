package co.tz.qroo.spa.service_package;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServicePackageDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String packageName;

    @NotNull
    private Boolean active;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime serviceStartTime;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime serviceEndTime;

    private List<UUID> services;

}
