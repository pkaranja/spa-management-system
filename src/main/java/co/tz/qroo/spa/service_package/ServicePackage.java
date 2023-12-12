package co.tz.qroo.spa.service_package;

import co.tz.qroo.spa.service.Service;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
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
public class ServicePackage {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String packageName;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private LocalTime serviceStartTime;

    @Column(nullable = false)
    private LocalTime serviceEndTime;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column
    private String daysOffered;

    @Column
    private String image;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @ManyToMany
    @JoinTable(
            name = "PackageServices",
            joinColumns = @JoinColumn(name = "servicePackageId"),
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
