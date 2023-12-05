package co.tz.qroo.spa.staff;

import co.tz.qroo.spa.kyc.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StaffDTO {

    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String emailAddress;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Size(max = 255)
    private String designation;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime checkinTime;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime checkoutTime;

    @NotNull
    @Size(max = 255)
    private String joiningDate;

    @NotNull
    private Boolean active;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "90.08")
    private BigDecimal salary;

    private Double commission;

    private List<UUID> services;

}
