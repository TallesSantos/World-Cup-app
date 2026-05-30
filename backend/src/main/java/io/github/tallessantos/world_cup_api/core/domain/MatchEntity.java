package io.github.tallessantos.world_cup_api.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "match_id")
    private List<MediaEntity> listOfVideos = new ArrayList<>();
}
