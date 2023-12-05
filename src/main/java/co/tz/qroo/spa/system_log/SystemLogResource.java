package co.tz.qroo.spa.system_log;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/systemLogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemLogResource {

    private final SystemLogService systemLogService;

    public SystemLogResource(final SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping
    public ResponseEntity<List<SystemLogDTO>> getAllSystemLogs() {
        return ResponseEntity.ok(systemLogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemLogDTO> getSystemLog(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(systemLogService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSystemLog(
            @RequestBody @Valid final SystemLogDTO systemLogDTO) {
        final Long createdId = systemLogService.create(systemLogDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSystemLog(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SystemLogDTO systemLogDTO) {
        systemLogService.update(id, systemLogDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSystemLog(@PathVariable(name = "id") final Long id) {
        systemLogService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
