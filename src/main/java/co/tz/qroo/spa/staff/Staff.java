package co.tz.qroo.spa.staff;

import co.tz.qroo.spa.kyc.Gender;
import co.tz.qroo.spa.service.Service;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class Staff {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "char(36)")
    private UUID userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String emailAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private LocalTime checkinTime;

    @Column(nullable = false)
    private LocalTime checkoutTime;

    @Column(nullable = false)
    private String joiningDate;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column
    private Double commission;

    @ManyToMany
    @JoinTable(
            name = "AttendantServices",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId")
    )
    private Set<Service> services;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
