package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.infra.repository.*;
import io.github.tallessantos.world_cup_api.infra.repository.csv.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class CsvImportService implements CommandLineRunner {

    private static final String CREATED_BY_NAME = "csv_import_automation";

    private final CsvDataSource csvDataSource;
    private final WorldCupRepository worldCupRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final PlayerAppearanceRepository playerAppearanceRepository;
    private final CountryRepository countryRepository;

    public CsvImportService(
            CsvDataSource csvDataSource,
            WorldCupRepository worldCupRepository,
            MatchRepository matchRepository,
            PlayerAppearanceRepository playerAppearanceRepository,
            PlayerRepository playerRepository,
            CountryRepository countryRepository
    ) {
        this.csvDataSource = csvDataSource;
        this.worldCupRepository = worldCupRepository;
        this.matchRepository = matchRepository;
        this.playerAppearanceRepository = playerAppearanceRepository;
        this.playerRepository = playerRepository;
        this.countryRepository = countryRepository;
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

        Map<String, CountryEntity> countries = new HashMap<>();

        for (MatchCsvRow match : csvDataSource.loadMatches()) {

            if (countries.get(match.homeTeamName().trim()) == null) {
                CountryEntity entity = populateCountryEntity(match);
                entity.setName(match.homeTeamName().trim());
                entity.setInitials(match.homeTeamInitials().trim());
                countries.put(match.homeTeamName().trim(), entity);
            }
            if (countries.get(match.awayTeamName().trim()) == null) {
                CountryEntity entity = populateCountryEntity(match);
                entity.setName(match.homeTeamName().trim());
                entity.setInitials(match.awayTeamInitials().trim());
                countries.put(match.awayTeamName().trim(), entity);
            }
        }
        countryRepository.saveAll(new HashSet<>(countries.values()));

        List<PlayerAppearanceEntity> listPlayerAppearenceEntity = new ArrayList<>();
        for (PlayerCsvRow entity : csvDataSource.loadPlayers()) {
            PlayerAppearanceEntity playerEntity = toPlayerAppearanceEntity(CREATED_BY_NAME, entity);
            listPlayerAppearenceEntity.add(playerEntity);
        }
        playerAppearanceRepository.saveAll(listPlayerAppearenceEntity);

        List<PlayerCsvRow> allRows = csvDataSource.loadPlayers();
        Map<String, PlayerEntity> playerMap = new LinkedHashMap<>();

        for (PlayerCsvRow row : allRows) {
            String id = CsvSupport.playerId(row.teamInitials(), row.playerName());
            PlayerEntity entity = playerMap.computeIfAbsent(id, _id -> {
                PlayerEntity e = new PlayerEntity();
                e.getAudit().setCreatedBy(CREATED_BY_NAME);
                e.getAudit().setCreatedAt(LocalDateTime.now());
                return e;
            });
            // Sobrescreve sempre: garante que a última aparição no CSV
            // (normalmente a mais recente) define os dados canônicos.
            entity.setPlayerName(row.playerName());
            entity.setPlayerName(CsvSupport.toDisplayName(row.playerName()));
            entity.setTeamInitials(row.teamInitials());
            entity.setPosition(row.position());
            entity.setCommonShirtNumber(row.shirtNumber());
            try {
                CountryEntity countryEntity = countryRepository.findAllByInitials(row.teamInitials().trim()).getFirst();
                entity.setCountryEntity(countryEntity);
            } catch (Exception e) {
                log.warn("not possible add country entity into player: {}", row);
            }
        }

        playerRepository.saveAll(playerMap.values());
    }

    private CountryEntity populateCountryEntity(MatchCsvRow match) {
        CountryEntity entity = new CountryEntity();
        entity.getAudit().setCreatedBy(CREATED_BY_NAME);
        entity.getAudit().setCreatedAt(LocalDateTime.now());
        return entity;
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
