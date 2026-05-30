package io.github.tallessantos.world_cup_api.core.domain;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;
import io.github.tallessantos.world_cup_api.core.listeners.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_appearances")
public class PlayerAppearanceEntity implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchId;
    private String teamInitials;
    private String coachName;
    private String lineup;
    private String shirtNumber;
    private String playerName;
    private String position;
    private String event;

    @Embedded
    private AuditMetadataEntity audit = new AuditMetadataEntity();
}
