package io.github.tallessantos.world_cup_api.business.core.service;

import io.github.tallessantos.world_cup_api.business.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.business.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.business.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.business.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.business.infra.repository.PlayerRepository;
import io.github.tallessantos.world_cup_api.business.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.business.infra.repository.csv.CsvDataSource;
import io.github.tallessantos.world_cup_api.business.infra.repository.csv.CsvSupport;
import io.github.tallessantos.world_cup_api.business.infra.repository.csv.MatchCsvRow;
import io.github.tallessantos.world_cup_api.business.infra.repository.csv.PlayerCsvRow;
import io.github.tallessantos.world_cup_api.business.infra.repository.csv.WorldCupCsvRow;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CsvImportService implements CommandLineRunner {

    private final CsvDataSource csvDataSource;
    private final WorldCupRepository worldCupRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public CsvImportService(
            CsvDataSource csvDataSource,
            WorldCupRepository worldCupRepository,
            MatchRepository matchRepository,
            PlayerRepository playerRepository
    ) {
        this.csvDataSource = csvDataSource;
        this.worldCupRepository = worldCupRepository;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) {
        if (worldCupRepository.count() > 0L) {
            return;
        }

        worldCupRepository.saveAll(csvDataSource.loadWorldCups().stream().map(this::toWorldCupEntity).toList());
        matchRepository.saveAll(csvDataSource.loadMatches().stream().map(this::toMatchEntity).toList());
        playerRepository.saveAll(csvDataSource.loadPlayers().stream().map(this::toPlayerAppearanceEntity).toList());
    }

    private WorldCupEntity toWorldCupEntity(WorldCupCsvRow row) {
        int year = row.year();
        WorldCupEntity entity = new WorldCupEntity();
        entity.setId("world-cup-" + year);
        entity.setTitle("FIFA World Cup " + row.country() + " " + year);
        entity.setStatus(resolveStatus(year));
        entity.setStartDate(LocalDate.of(year, 6, 1));
        entity.setEndDate(LocalDate.of(year, 7, 31));
        entity.setHostCountriesRaw(row.country());
        entity.setImgBannerUrl("/images/world-cups/" + year + "/banner.jpg");
        entity.setWinner(row.winner());
        entity.setRunnersUp(row.runnersUp());
        entity.setThirdPlace(row.third());
        entity.setFourthPlace(row.fourth());
        entity.setGoalsScored(row.goalsScored());
        entity.setQualifiedTeams(row.qualifiedTeams());
        entity.setMatchesPlayed(row.matchesPlayed());
        entity.setAttendance(row.attendance());
        return entity;
    }

    private MatchEntity toMatchEntity(MatchCsvRow row) {
        MatchEntity entity = new MatchEntity();
        entity.setId(row.matchId());
        entity.setWorldCupId("world-cup-" + row.year());
        entity.setKickoffAt(CsvSupport.parseMatchDateTime(row.datetime()));
        entity.setStage(row.stage());
        entity.setStadium(row.stadium());
        entity.setCity(row.city());
        entity.setHomeTeamName(row.homeTeamName());
        entity.setHomeTeamInitials(row.homeTeamInitials());
        entity.setHomeTeamGoals(row.homeTeamGoals());
        entity.setAwayTeamGoals(row.awayTeamGoals());
        entity.setAwayTeamName(row.awayTeamName());
        entity.setAwayTeamInitials(row.awayTeamInitials());
        entity.setWinConditions(row.winConditions());
        entity.setAttendance(row.attendance());
        entity.setReferee(row.referee());
        return entity;
    }

    private PlayerAppearanceEntity toPlayerAppearanceEntity(PlayerCsvRow row) {
        PlayerAppearanceEntity entity = new PlayerAppearanceEntity();
        entity.setMatchId(row.matchId());
        entity.setTeamInitials(row.teamInitials());
        entity.setCoachName(row.coachName());
        entity.setLineup(row.lineup());
        entity.setShirtNumber(row.shirtNumber());
        entity.setPlayerName(row.playerName());
        entity.setPosition(row.position());
        entity.setEvent(row.event());
        return entity;
    }

    private String resolveStatus(int year) {
        int currentYear = LocalDate.now().getYear();
        if (year < currentYear) {
            return "completed";
        }
        if (year > currentYear) {
            return "upcoming";
        }
        return "ongoing";
    }
}
