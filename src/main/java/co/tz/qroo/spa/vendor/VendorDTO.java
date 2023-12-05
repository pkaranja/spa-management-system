package co.tz.qroo.spa.vendor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VendorDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String emailAddress;

    @Size(max = 255)
    private String address;

    private String description;

}
