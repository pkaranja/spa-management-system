package co.tz.qroo.spa.system_log;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SystemLogDTO {

    private Long id;

    @NotNull
    private UUID actionedBy;

    @NotNull
    private String activity;

    @NotNull
    @Size(max = 255)
    private String type;

}
