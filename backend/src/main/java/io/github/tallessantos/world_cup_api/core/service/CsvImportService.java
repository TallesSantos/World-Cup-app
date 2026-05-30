package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerRepository;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvDataSource;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import io.github.tallessantos.world_cup_api.infra.repository.csv.MatchCsvRow;
import io.github.tallessantos.world_cup_api.infra.repository.csv.PlayerCsvRow;
import io.github.tallessantos.world_cup_api.infra.repository.csv.WorldCupCsvRow;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImportService implements CommandLineRunner {

    private static final String CREATED_BY_NAME = "csv_import_automation";

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

        List<WorldCupEntity> listWorldCups = new ArrayList<>();
        for (WorldCupCsvRow entity : csvDataSource.loadWorldCups()) {
            WorldCupEntity worldCupEntity = toWorldCupEntity(CREATED_BY_NAME, entity);
            listWorldCups.add(worldCupEntity);
        }
        worldCupRepository.saveAll(listWorldCups);

        List<MatchEntity> listMatches = new ArrayList<>();
        for (MatchCsvRow entity : csvDataSource.loadMatches()) {
            MatchEntity worldCupEntity = toMatchEntity(CREATED_BY_NAME, entity);
            listMatches.add(worldCupEntity);
        }
        matchRepository.saveAll(listMatches);

        List<PlayerAppearanceEntity> listPlayerAppearenceEntity = new ArrayList<>();
        for (PlayerCsvRow entity : csvDataSource.loadPlayers()) {
            PlayerAppearanceEntity playerEntity = toPlayerAppearanceEntity(CREATED_BY_NAME, entity);
            listPlayerAppearenceEntity.add(playerEntity);
        }
        playerRepository.saveAll(listPlayerAppearenceEntity);
    }

    private WorldCupEntity toWorldCupEntity(String createdBy, WorldCupCsvRow row) {

        int year = row.year();
        WorldCupEntity entity = new WorldCupEntity();
        entity.getAudit().setCreatedBy(createdBy);
        entity.getAudit().setCreatedAt(LocalDateTime.now());
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

    private MatchEntity toMatchEntity(String createdBy, MatchCsvRow row) {
        MatchEntity entity = new MatchEntity();
        entity.getAudit().setCreatedBy(createdBy);
        entity.getAudit().setCreatedAt(LocalDateTime.now());
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

    private PlayerAppearanceEntity toPlayerAppearanceEntity(String createdBy, PlayerCsvRow row) {
        PlayerAppearanceEntity entity = new PlayerAppearanceEntity();
        entity.getAudit().setCreatedBy(createdBy);
        entity.getAudit().setCreatedAt(LocalDateTime.now());
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
