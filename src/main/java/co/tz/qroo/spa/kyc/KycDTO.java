package co.tz.qroo.spa.kyc;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KycDTO {

    private UUID id;

    private UUID userId;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 15)
    private String phone;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    private Gender gender;

    private LocalDate dateOfBirth;

    private String remarks;

    @NotNull
    private Boolean acceptTerms;

    @NotNull
    private Boolean acceptAgeLimit;

    @NotNull
    @Size(max = 255)
    private String referralId;

    @NotNull
    private Integer points;

    private List<UUID> appointments;

}
