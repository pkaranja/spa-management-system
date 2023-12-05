package co.tz.qroo.spa.vendor;

import co.tz.qroo.spa.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(final VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<VendorDTO> findAll() {
        final List<Vendor> vendors = vendorRepository.findAll(Sort.by("id"));
        return vendors.stream()
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .toList();
    }

    public VendorDTO get(final UUID id) {
        return vendorRepository.findById(id)
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final VendorDTO vendorDTO) {
        final Vendor vendor = new Vendor();
        mapToEntity(vendorDTO, vendor);
        return vendorRepository.save(vendor).getId();
    }

    public void update(final UUID id, final VendorDTO vendorDTO) {
        final Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vendorDTO, vendor);
        vendorRepository.save(vendor);
    }

    public void delete(final UUID id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO mapToDTO(final Vendor vendor, final VendorDTO vendorDTO) {
        vendorDTO.setId(vendor.getId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setPhoneNumber(vendor.getPhoneNumber());
        vendorDTO.setEmailAddress(vendor.getEmailAddress());
        vendorDTO.setAddress(vendor.getAddress());
        vendorDTO.setDescription(vendor.getDescription());
        return vendorDTO;
    }

    private Vendor mapToEntity(final VendorDTO vendorDTO, final Vendor vendor) {
        vendor.setName(vendorDTO.getName());
        vendor.setPhoneNumber(vendorDTO.getPhoneNumber());
        vendor.setEmailAddress(vendorDTO.getEmailAddress());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setDescription(vendorDTO.getDescription());
        return vendor;
    }

    public boolean nameExists(final String name) {
        return vendorRepository.existsByNameIgnoreCase(name);
    }

}
