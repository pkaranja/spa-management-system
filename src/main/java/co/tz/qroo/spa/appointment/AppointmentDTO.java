package co.tz.qroo.spa.appointment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppointmentDTO {

    private UUID id;

    @NotNull
    private LocalDateTime appointmentDate;

    private List<UUID> services;

    private List<UUID> packages;

}
