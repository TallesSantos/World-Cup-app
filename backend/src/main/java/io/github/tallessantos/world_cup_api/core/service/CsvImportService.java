package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.infra.repository.*;
import io.github.tallessantos.world_cup_api.infra.repository.csv.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class CsvImportService {

    private static final String CREATED_BY_NAME = "csv_import_automation";

    private final CsvDataSource csvDataSource;
    private final WorldCupRepository worldCupRepository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final PlayerAppearanceRepository playerAppearanceRepository;
    private final CountryRepository countryRepository;

    @Value("${app.data.world-cups-after-2014-path}")
    private String worldCupsAfter2014;

    @Value("${app.data.world-cups-matches-2018-path}")
    private String worldCupMatches2018;

    @Value("${app.data.world-cups-matches-2022-path}")
    private String worldCupMatches2022;

    @Value("${app.data.world-cups-players-2018-path}")
    private String worldCupPlayers2018;

    @Value("${app.data.world-cups-players-2022-path}")
    private String worldCupPlayers2022;

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


    public void dataEntryUpToThe2014WorldCup() {

        if (!isWorldCupTableEmpty()) {
            log.warn("Already has data of world cups in database");
            return;
        }

        worldCupDataIngestion(csvDataSource.loadWorldCups());
        worldCupMatchesDataIngestion(csvDataSource.loadMatches());
        worldCupCountriesDataIngestion(csvDataSource.loadMatches());
        worldCupAppearenceDataIngestion(csvDataSource.loadPlayers());
        log.info("Ingestion data ended");
    }

    public void dataEntryAfterToThe2014WorldCup() {

        Optional<WorldCupEntity> worldCup2018 = worldCupRepository.findByReference("world-cup-2018");
        if (worldCup2018.isPresent()) {
            log.info("Already has 2018 world cup data in database");
            return;
        }

        worldCupDataIngestion(csvDataSource.loadWorldCups(Path.of(worldCupsAfter2014)));
        worldCupMatchesDataIngestion(csvDataSource.loadMatches(Path.of(worldCupMatches2018)));
        worldCupMatchesDataIngestion(csvDataSource.loadMatches(Path.of(worldCupMatches2022)));
        List<PlayerCsvRow> players2018 = csvDataSource.loadPlayers(Path.of(worldCupPlayers2018));
        if(!players2018.isEmpty()){
            worldCupAppearenceDataIngestion(players2018);
        }else{
            log.warn("Csv file don't have data");
        }

        List<PlayerCsvRow> players2022 = csvDataSource.loadPlayers(Path.of(worldCupPlayers2022));
        if(!players2022.isEmpty()){
            worldCupAppearenceDataIngestion(players2022);
        }else{
            log.warn("Csv file don't have data");
        }log.info("Ingestion data ended");
    }


    private void worldCupAppearenceDataIngestion(List<PlayerCsvRow> rows) {
        List<PlayerAppearanceEntity> listPlayerAppearenceEntity = new ArrayList<>();
        for (PlayerCsvRow entity : rows) {
            PlayerAppearanceEntity playerEntity = toPlayerAppearanceEntity(CREATED_BY_NAME, entity);
            listPlayerAppearenceEntity.add(playerEntity);
        }
        playerAppearanceRepository.saveAll(listPlayerAppearenceEntity);

        Map<String, PlayerEntity> playerMap = new LinkedHashMap<>();

        for (PlayerCsvRow row : rows) {
            String id = CsvSupport.playerId(row.teamInitials(), row.playerName());
            PlayerEntity entity = playerMap.computeIfAbsent(id, _id -> {
                PlayerEntity e = new PlayerEntity();
                e.getAudit().setCreatedBy(CREATED_BY_NAME);
                e.getAudit().setCreatedAt(LocalDateTime.now());
                return e;
            });

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

    private void worldCupCountriesDataIngestion(List<MatchCsvRow> rows) {
        Map<String, CountryEntity> countries = new HashMap<>();

        for (MatchCsvRow match : rows) {

            if (countries.get(match.homeTeamName().trim()) == null) {
                CountryEntity entity = createCountryEntity();
                entity.setName(match.homeTeamName().trim());
                entity.setInitials(match.homeTeamInitials().trim());
                countries.put(match.homeTeamName().trim(), entity);
            }
            if (countries.get(match.awayTeamName().trim()) == null) {
                CountryEntity entity = createCountryEntity();
                entity.setName(match.awayTeamName().trim());
                entity.setInitials(match.awayTeamInitials().trim());
                countries.put(match.awayTeamName().trim(), entity);
            }
        }
        countryRepository.saveAllAndFlush(new HashSet<>(countries.values()));
    }

    private void worldCupMatchesDataIngestion(List<MatchCsvRow> rows) {
        List<MatchEntity> listMatches = new ArrayList<>();
        for (MatchCsvRow entity : rows) {
            MatchEntity worldCupEntity = toMatchEntity(CREATED_BY_NAME, entity);
            listMatches.add(worldCupEntity);
        }
        matchRepository.saveAllAndFlush(listMatches);
    }

    private void worldCupDataIngestion(List<WorldCupCsvRow> rows) {
        List<WorldCupEntity> listWorldCups = new ArrayList<>();
        for (WorldCupCsvRow entity : rows) {
            WorldCupEntity worldCupEntity = toWorldCupEntity(CREATED_BY_NAME, entity);
            listWorldCups.add(worldCupEntity);
        }
        worldCupRepository.saveAllAndFlush(listWorldCups);
    }

    private boolean isWorldCupTableEmpty() {
        if (worldCupRepository.count() > 0L) {
            return false;
        }
        return true;
    }

    private CountryEntity createCountryEntity() {
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
        entity.setReference("world-cup-" + year);
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
