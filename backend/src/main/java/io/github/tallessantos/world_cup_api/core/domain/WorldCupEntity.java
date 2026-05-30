package io.github.tallessantos.world_cup_api.core.domain;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;
import io.github.tallessantos.world_cup_api.core.listeners.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "world_cups")
public class WorldCupEntity implements Auditable {

    @Id
    private String id;
    private String title;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hostCountriesRaw;
    private String imgBannerUrl;
    private String winner;
    private String runnersUp;
    private String thirdPlace;
    private String fourthPlace;
    private Integer goalsScored;
    private Integer qualifiedTeams;
    private Integer matchesPlayed;
    private String attendance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "world_cup_banner_media_id")
    private MediaEntity worldCupBannerMedia;

    @Embedded
    private AuditMetadataEntity audit = new AuditMetadataEntity();
}
