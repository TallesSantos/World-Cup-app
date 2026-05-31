package io.github.tallessantos.world_cup_api.core.domain;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;
import io.github.tallessantos.world_cup_api.core.listeners.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String initials;
    private String fifaCode;
    private String flagUrl;
    private String fifaRanking;

    @Enumerated(EnumType.STRING)
    private CountryConfederationType confederation;

    @OneToOne
    @JoinColumn(name = "country_flag_image_id")
    private MediaEntity countryFlagImage;

    //List<CountryWorldCupHistoryEntity> worldCupHistory;
    List<String> worldCupHistory;

    @Embedded
    private AuditMetadataEntity audit = new AuditMetadataEntity();

}
