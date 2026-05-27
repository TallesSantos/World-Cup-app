package io.github.tallessantos.world_cup_api.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_appearances")
public class PlayerAppearanceEntity {

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
}
