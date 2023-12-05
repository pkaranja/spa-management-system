package co.tz.qroo.spa.kyc;

import co.tz.qroo.spa.appointment.Appointment;
import co.tz.qroo.spa.appointment.AppointmentRepository;
import co.tz.qroo.spa.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class KycService {

    private final KycRepository kycRepository;
    private final AppointmentRepository appointmentRepository;

    public KycService(final KycRepository kycRepository,
            final AppointmentRepository appointmentRepository) {
        this.kycRepository = kycRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<KycDTO> findAll() {
        final List<Kyc> kycs = kycRepository.findAll(Sort.by("id"));
        return kycs.stream()
                .map(kyc -> mapToDTO(kyc, new KycDTO()))
                .toList();
    }

    public KycDTO get(final UUID id) {
        return kycRepository.findById(id)
                .map(kyc -> mapToDTO(kyc, new KycDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final KycDTO kycDTO) {
        final Kyc kyc = new Kyc();
        mapToEntity(kycDTO, kyc);
        return kycRepository.save(kyc).getId();
    }

    public void update(final UUID id, final KycDTO kycDTO) {
        final Kyc kyc = kycRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(kycDTO, kyc);
        kycRepository.save(kyc);
    }

    public void delete(final UUID id) {
        kycRepository.deleteById(id);
    }

    private KycDTO mapToDTO(final Kyc kyc, final KycDTO kycDTO) {
        kycDTO.setId(kyc.getId());
        kycDTO.setUserId(kyc.getUserId());
        kycDTO.setFirstName(kyc.getFirstName());
        kycDTO.setLastName(kyc.getLastName());
        kycDTO.setPhone(kyc.getPhone());
        kycDTO.setEmail(kyc.getEmail());
        kycDTO.setGender(kyc.getGender());
        kycDTO.setDateOfBirth(kyc.getDateOfBirth());
        kycDTO.setRemarks(kyc.getRemarks());
        kycDTO.setAcceptTerms(kyc.getAcceptTerms());
        kycDTO.setAcceptAgeLimit(kyc.getAcceptAgeLimit());
        kycDTO.setReferralId(kyc.getReferralId());
        kycDTO.setPoints(kyc.getPoints());
        kycDTO.setAppointments(kyc.getAppointments().stream()
                .map(appointment -> appointment.getId())
                .toList());
        return kycDTO;
    }

    private Kyc mapToEntity(final KycDTO kycDTO, final Kyc kyc) {
        kyc.setUserId(kycDTO.getUserId());
        kyc.setFirstName(kycDTO.getFirstName());
        kyc.setLastName(kycDTO.getLastName());
        kyc.setPhone(kycDTO.getPhone());
        kyc.setEmail(kycDTO.getEmail());
        kyc.setGender(kycDTO.getGender());
        kyc.setDateOfBirth(kycDTO.getDateOfBirth());
        kyc.setRemarks(kycDTO.getRemarks());
        kyc.setAcceptTerms(kycDTO.getAcceptTerms());
        kyc.setAcceptAgeLimit(kycDTO.getAcceptAgeLimit());
        kyc.setReferralId(kycDTO.getReferralId());
        kyc.setPoints(kycDTO.getPoints());
        final List<Appointment> appointments = appointmentRepository.findAllById(
                kycDTO.getAppointments() == null ? Collections.emptyList() : kycDTO.getAppointments());
        if (appointments.size() != (kycDTO.getAppointments() == null ? 0 : kycDTO.getAppointments().size())) {
            throw new NotFoundException("one of appointments not found");
        }
        kyc.setAppointments(appointments.stream().collect(Collectors.toSet()));
        return kyc;
    }

    public boolean userIdExists(final UUID userId) {
        return kycRepository.existsByUserId(userId);
    }

    public boolean phoneExists(final String phone) {
        return kycRepository.existsByPhoneIgnoreCase(phone);
    }

    public boolean emailExists(final String email) {
        return kycRepository.existsByEmailIgnoreCase(email);
    }

    public boolean referralIdExists(final String referralId) {
        return kycRepository.existsByReferralIdIgnoreCase(referralId);
    }

    public boolean pointsExists(final Integer points) {
        return kycRepository.existsByPoints(points);
    }

}
