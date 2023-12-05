package co.tz.qroo.spa.stock;

import co.tz.qroo.spa.util.NotFoundException;
import co.tz.qroo.spa.vendor.Vendor;
import co.tz.qroo.spa.vendor.VendorRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StockService {

    private final StockRepository stockRepository;
    private final VendorRepository vendorRepository;

    public StockService(final StockRepository stockRepository,
            final VendorRepository vendorRepository) {
        this.stockRepository = stockRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<StockDTO> findAll() {
        final List<Stock> stocks = stockRepository.findAll(Sort.by("id"));
        return stocks.stream()
                .map(stock -> mapToDTO(stock, new StockDTO()))
                .toList();
    }

    public StockDTO get(final UUID id) {
        return stockRepository.findById(id)
                .map(stock -> mapToDTO(stock, new StockDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final StockDTO stockDTO) {
        final Stock stock = new Stock();
        mapToEntity(stockDTO, stock);
        return stockRepository.save(stock).getId();
    }

    public void update(final UUID id, final StockDTO stockDTO) {
        final Stock stock = stockRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(stockDTO, stock);
        stockRepository.save(stock);
    }

    public void delete(final UUID id) {
        stockRepository.deleteById(id);
    }

    private StockDTO mapToDTO(final Stock stock, final StockDTO stockDTO) {
        stockDTO.setId(stock.getId());
        stockDTO.setProductName(stock.getProductName());
        stockDTO.setItemCode(stock.getItemCode());
        stockDTO.setCost(stock.getCost());
        stockDTO.setSellingPrice(stock.getSellingPrice());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setAlertQuantity(stock.getAlertQuantity());
        stockDTO.setDescription(stock.getDescription());
        stockDTO.setActive(stock.getActive());
        stockDTO.setVendor(stock.getVendor() == null ? null : stock.getVendor().getId());
        return stockDTO;
    }

    private Stock mapToEntity(final StockDTO stockDTO, final Stock stock) {
        stock.setProductName(stockDTO.getProductName());
        stock.setItemCode(stockDTO.getItemCode());
        stock.setCost(stockDTO.getCost());
        stock.setSellingPrice(stockDTO.getSellingPrice());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setAlertQuantity(stockDTO.getAlertQuantity());
        stock.setDescription(stockDTO.getDescription());
        stock.setActive(stockDTO.getActive());
        final Vendor vendor = stockDTO.getVendor() == null ? null : vendorRepository.findById(stockDTO.getVendor())
                .orElseThrow(() -> new NotFoundException("vendor not found"));
        stock.setVendor(vendor);
        return stock;
    }

}
