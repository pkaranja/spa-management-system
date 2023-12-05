package co.tz.qroo.spa.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
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
public class Business {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column
    private String logo;

    @Column(nullable = false, unique = true)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String adminEmail;

    @Column(columnDefinition = "longtext")
    private String businessDescription;

    @Column
    private String reportsEmail;

    @Column
    private String mobileNumber;

    @Column
    private String telephoneNumber;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String openingTime;

    @Column
    private String closingTime;

    @Column
    private String currency;

    @Column
    private String timezone;

    @Column(nullable = false)
    private Boolean active;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
