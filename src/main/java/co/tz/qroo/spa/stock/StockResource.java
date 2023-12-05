package co.tz.qroo.spa.stock;

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
@RequestMapping(value = "/api/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockResource {

    private final StockService stockService;

    public StockResource(final StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStock(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(stockService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createStock(@RequestBody @Valid final StockDTO stockDTO) {
        final UUID createdId = stockService.create(stockDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateStock(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final StockDTO stockDTO) {
        stockService.update(id, stockDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStock(@PathVariable(name = "id") final UUID id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
