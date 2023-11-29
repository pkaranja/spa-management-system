package co.tz.qroo.spa.service_category;

import co.tz.qroo.spa.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;

    public ServiceCategoryService(final ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    public List<ServiceCategoryDTO> findAll() {
        final List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll(Sort.by("id"));
        return serviceCategories.stream()
                .map(serviceCategory -> mapToDTO(serviceCategory, new ServiceCategoryDTO()))
                .toList();
    }

    public ServiceCategoryDTO get(final UUID id) {
        return serviceCategoryRepository.findById(id)
                .map(serviceCategory -> mapToDTO(serviceCategory, new ServiceCategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ServiceCategoryDTO serviceCategoryDTO) {
        final ServiceCategory serviceCategory = new ServiceCategory();
        mapToEntity(serviceCategoryDTO, serviceCategory);
        return serviceCategoryRepository.save(serviceCategory).getId();
    }

    public void update(final UUID id, final ServiceCategoryDTO serviceCategoryDTO) {
        final ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serviceCategoryDTO, serviceCategory);
        serviceCategoryRepository.save(serviceCategory);
    }

    public void delete(final UUID id) {
        serviceCategoryRepository.deleteById(id);
    }

    private ServiceCategoryDTO mapToDTO(final ServiceCategory serviceCategory,
            final ServiceCategoryDTO serviceCategoryDTO) {
        serviceCategoryDTO.setId(serviceCategory.getId());
        serviceCategoryDTO.setCategoryName(serviceCategory.getCategoryName());
        serviceCategoryDTO.setActive(serviceCategory.getActive());
        return serviceCategoryDTO;
    }

    private ServiceCategory mapToEntity(final ServiceCategoryDTO serviceCategoryDTO,
            final ServiceCategory serviceCategory) {
        serviceCategory.setCategoryName(serviceCategoryDTO.getCategoryName());
        serviceCategory.setActive(serviceCategoryDTO.getActive());
        return serviceCategory;
    }

}
