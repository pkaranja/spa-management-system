package co.tz.qroo.spa.kyc;

import co.tz.qroo.spa.appointment.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Kyc {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(unique = true, columnDefinition = "char(36)")
    private UUID userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "longtext")
    private String remarks;

    @Column(nullable = false)
    private Boolean acceptTerms;

    @Column(nullable = false)
    private Boolean acceptAgeLimit;

    @Column(nullable = false, unique = true)
    private String referralId;

    @Column(nullable = false, unique = true)
    private Integer points;

    @ManyToMany
    @JoinTable(
            name = "CustomerAppointments",
            joinColumns = @JoinColumn(name = "kycId"),
            inverseJoinColumns = @JoinColumn(name = "appointmentId")
    )
    private Set<Appointment> appointments;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
