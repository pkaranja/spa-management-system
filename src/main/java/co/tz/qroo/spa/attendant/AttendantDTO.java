package co.tz.qroo.spa.attendant;

import co.tz.qroo.spa.kyc.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttendantDTO {

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
    private Boolean active;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime availabilityStartTime;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime availabilityEndTime;

    private List<UUID> services;

}
