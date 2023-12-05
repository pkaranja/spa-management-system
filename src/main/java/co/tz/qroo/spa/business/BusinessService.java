package co.tz.qroo.spa.business;

import co.tz.qroo.spa.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    public BusinessService(final BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<BusinessDTO> findAll() {
        final List<Business> businesses = businessRepository.findAll(Sort.by("id"));
        return businesses.stream()
                .map(business -> mapToDTO(business, new BusinessDTO()))
                .toList();
    }

    public BusinessDTO get(final UUID id) {
        return businessRepository.findById(id)
                .map(business -> mapToDTO(business, new BusinessDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final BusinessDTO businessDTO) {
        final Business business = new Business();
        mapToEntity(businessDTO, business);
        return businessRepository.save(business).getId();
    }

    public void update(final UUID id, final BusinessDTO businessDTO) {
        final Business business = businessRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(businessDTO, business);
        businessRepository.save(business);
    }

    public void delete(final UUID id) {
        businessRepository.deleteById(id);
    }

    private BusinessDTO mapToDTO(final Business business, final BusinessDTO businessDTO) {
        businessDTO.setId(business.getId());
        businessDTO.setLogo(business.getLogo());
        businessDTO.setBusinessName(business.getBusinessName());
        businessDTO.setAdminEmail(business.getAdminEmail());
        businessDTO.setBusinessDescription(business.getBusinessDescription());
        businessDTO.setReportsEmail(business.getReportsEmail());
        businessDTO.setMobileNumber(business.getMobileNumber());
        businessDTO.setTelephoneNumber(business.getTelephoneNumber());
        businessDTO.setAddress(business.getAddress());
        businessDTO.setCity(business.getCity());
        businessDTO.setCountry(business.getCountry());
        businessDTO.setOpeningTime(business.getOpeningTime());
        businessDTO.setClosingTime(business.getClosingTime());
        businessDTO.setCurrency(business.getCurrency());
        businessDTO.setTimezone(business.getTimezone());
        businessDTO.setActive(business.getActive());
        return businessDTO;
    }

    private Business mapToEntity(final BusinessDTO businessDTO, final Business business) {
        business.setLogo(businessDTO.getLogo());
        business.setBusinessName(businessDTO.getBusinessName());
        business.setAdminEmail(businessDTO.getAdminEmail());
        business.setBusinessDescription(businessDTO.getBusinessDescription());
        business.setReportsEmail(businessDTO.getReportsEmail());
        business.setMobileNumber(businessDTO.getMobileNumber());
        business.setTelephoneNumber(businessDTO.getTelephoneNumber());
        business.setAddress(businessDTO.getAddress());
        business.setCity(businessDTO.getCity());
        business.setCountry(businessDTO.getCountry());
        business.setOpeningTime(businessDTO.getOpeningTime());
        business.setClosingTime(businessDTO.getClosingTime());
        business.setCurrency(businessDTO.getCurrency());
        business.setTimezone(businessDTO.getTimezone());
        business.setActive(businessDTO.getActive());
        return business;
    }

    public boolean businessNameExists(final String businessName) {
        return businessRepository.existsByBusinessNameIgnoreCase(businessName);
    }

    public boolean adminEmailExists(final String adminEmail) {
        return businessRepository.existsByAdminEmailIgnoreCase(adminEmail);
    }

}
