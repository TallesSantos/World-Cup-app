package io.github.tallessantos.world_cup_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "world_cups")
public class WorldCupEntity {

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
}
