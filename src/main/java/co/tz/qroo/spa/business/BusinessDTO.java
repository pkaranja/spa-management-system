package co.tz.qroo.spa.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BusinessDTO {

    private UUID id;

    @Size(max = 255)
    private String logo;

    @NotNull
    @Size(max = 255)
    private String businessName;

    @NotNull
    @Size(max = 255)
    private String adminEmail;

    private String businessDescription;

    @Size(max = 255)
    private String reportsEmail;

    @Size(max = 255)
    private String mobileNumber;

    @Size(max = 255)
    private String telephoneNumber;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String country;

    @Size(max = 255)
    private String openingTime;

    @Size(max = 255)
    private String closingTime;

    @Size(max = 255)
    private String currency;

    @Size(max = 255)
    private String timezone;

    @NotNull
    private Boolean active;

}
