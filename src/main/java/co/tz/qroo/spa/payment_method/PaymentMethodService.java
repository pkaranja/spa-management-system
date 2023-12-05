package co.tz.qroo.spa.payment_method;

import co.tz.qroo.spa.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethodDTO> findAll() {
        final List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll(Sort.by("id"));
        return paymentMethods.stream()
                .map(paymentMethod -> mapToDTO(paymentMethod, new PaymentMethodDTO()))
                .toList();
    }

    public PaymentMethodDTO get(final UUID id) {
        return paymentMethodRepository.findById(id)
                .map(paymentMethod -> mapToDTO(paymentMethod, new PaymentMethodDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PaymentMethodDTO paymentMethodDTO) {
        final PaymentMethod paymentMethod = new PaymentMethod();
        mapToEntity(paymentMethodDTO, paymentMethod);
        return paymentMethodRepository.save(paymentMethod).getId();
    }

    public void update(final UUID id, final PaymentMethodDTO paymentMethodDTO) {
        final PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentMethodDTO, paymentMethod);
        paymentMethodRepository.save(paymentMethod);
    }

    public void delete(final UUID id) {
        paymentMethodRepository.deleteById(id);
    }

    private PaymentMethodDTO mapToDTO(final PaymentMethod paymentMethod,
            final PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setId(paymentMethod.getId());
        paymentMethodDTO.setPaymentType(paymentMethod.getPaymentType());
        paymentMethodDTO.setActive(paymentMethod.getActive());
        return paymentMethodDTO;
    }

    private PaymentMethod mapToEntity(final PaymentMethodDTO paymentMethodDTO,
            final PaymentMethod paymentMethod) {
        paymentMethod.setPaymentType(paymentMethodDTO.getPaymentType());
        paymentMethod.setActive(paymentMethodDTO.getActive());
        return paymentMethod;
    }

    public boolean paymentTypeExists(final String paymentType) {
        return paymentMethodRepository.existsByPaymentTypeIgnoreCase(paymentType);
    }

}
