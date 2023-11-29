package co.tz.qroo.spa.service_category;

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
@RequestMapping(value = "/api/serviceCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceCategoryResource {

    private final ServiceCategoryService serviceCategoryService;

    public ServiceCategoryResource(final ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryDTO>> getAllServiceCategories() {
        return ResponseEntity.ok(serviceCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> getServiceCategory(
            @PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(serviceCategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createServiceCategory(
            @RequestBody @Valid final ServiceCategoryDTO serviceCategoryDTO) {
        final UUID createdId = serviceCategoryService.create(serviceCategoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateServiceCategory(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final ServiceCategoryDTO serviceCategoryDTO) {
        serviceCategoryService.update(id, serviceCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable(name = "id") final UUID id) {
        serviceCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
