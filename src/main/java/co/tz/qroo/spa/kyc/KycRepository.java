package co.tz.qroo.spa.kyc;

import co.tz.qroo.spa.appointment.Appointment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KycRepository extends JpaRepository<Kyc, UUID> {

    List<Kyc> findAllByAppointments(Appointment appointment);

    boolean existsByUserId(UUID userId);

    boolean existsByPhoneIgnoreCase(String phone);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByReferralIdIgnoreCase(String referralId);

    boolean existsByPoints(Integer points);

}
