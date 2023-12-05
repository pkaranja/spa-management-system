package co.tz.qroo.spa.vendor;

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
@RequestMapping(value = "/api/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
public class VendorResource {

    private final VendorService vendorService;

    public VendorResource(final VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        return ResponseEntity.ok(vendorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(vendorService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createVendor(@RequestBody @Valid final VendorDTO vendorDTO) {
        final UUID createdId = vendorService.create(vendorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateVendor(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final VendorDTO vendorDTO) {
        vendorService.update(id, vendorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteVendor(@PathVariable(name = "id") final UUID id) {
        vendorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
