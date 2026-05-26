package io.github.tallessantos.world_cup_api.business.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    private String id;
    private String worldCupId;
    private LocalDateTime kickoffAt;
    private String stage;
    private String stadium;
    private String city;
    private String homeTeamName;
    private String homeTeamInitials;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;
    private String awayTeamName;
    private String awayTeamInitials;
    private String winConditions;
    private Integer attendance;
    private String referee;
}
