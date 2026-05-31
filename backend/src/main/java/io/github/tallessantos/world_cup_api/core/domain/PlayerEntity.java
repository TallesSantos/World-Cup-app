package io.github.tallessantos.world_cup_api.core.domain;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;
import io.github.tallessantos.world_cup_api.core.listeners.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "player")
public class PlayerEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String playerName;

    private String position;

    private String birthDate;

    private String deathData;

    private String commonShirtNumber;

    private String teamInitials;

    @ManyToOne
    @JoinColumn(name = "profile_image_id")
    private MediaEntity profileImage;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    @Embedded
    private AuditMetadataEntity audit = new AuditMetadataEntity();

    public CountryEntity getCountryentity() {
        return countryEntity;
    }
}
