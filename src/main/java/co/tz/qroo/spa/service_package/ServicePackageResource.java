package co.tz.qroo.spa.service_package;

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
@RequestMapping(value = "/api/servicePackages", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServicePackageResource {

    private final ServicePackageService servicePackageService;

    public ServicePackageResource(final ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
    }

    @GetMapping
    public ResponseEntity<List<ServicePackageDTO>> getAllServicePackages() {
        return ResponseEntity.ok(servicePackageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePackageDTO> getServicePackage(
            @PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(servicePackageService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createServicePackage(
            @RequestBody @Valid final ServicePackageDTO servicePackageDTO) {
        final UUID createdId = servicePackageService.create(servicePackageDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateServicePackage(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final ServicePackageDTO servicePackageDTO) {
        servicePackageService.update(id, servicePackageDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteServicePackage(@PathVariable(name = "id") final UUID id) {
        servicePackageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
