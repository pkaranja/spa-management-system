package co.tz.qroo.spa.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String serviceName;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "75.08")
    private BigDecimal price;

    @Schema(type = "string", example = "18:30")
    private LocalTime serviceStartTime;

    @Schema(type = "string", example = "18:30")
    private LocalTime serviceEndTime;

    @NotNull
    private Boolean active;

    @NotNull
    private UUID category;

}
