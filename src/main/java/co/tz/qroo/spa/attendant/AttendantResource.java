package co.tz.qroo.spa.attendant;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/attendants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttendantResource {

    private final AttendantService attendantService;

    public AttendantResource(final AttendantService attendantService) {
        this.attendantService = attendantService;
    }

    @GetMapping
    public ResponseEntity<List<AttendantDTO>> getAllAttendants() {
        return ResponseEntity.ok(attendantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendantDTO> getAttendant(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(attendantService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createAttendant(
            @RequestBody @Valid final AttendantDTO attendantDTO) {
        final UUID createdId = attendantService.create(attendantDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateAttendant(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final AttendantDTO attendantDTO) {
        attendantService.update(id, attendantDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAttendant(@PathVariable(name = "id") final UUID id) {
        attendantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
