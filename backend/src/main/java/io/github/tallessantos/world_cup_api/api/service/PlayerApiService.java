package io.github.tallessantos.world_cup_api.api.service;

import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerAppearanceRepository;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerRepository;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerApiService {

    private final PlayerAppearanceRepository playerAppearanceRepository;
    private final MatchRepository matchRepository;
    private final WorldCupRepository worldCupRepository;
    private final PlayerRepository playerRepository;

    public PlayerApiService(PlayerAppearanceRepository playerAppearanceRepository, MatchRepository matchRepository, WorldCupRepository worldCupRepository, PlayerRepository playerRepository) {
        this.playerAppearanceRepository = playerAppearanceRepository;
        this.matchRepository = matchRepository;
        this.worldCupRepository = worldCupRepository;
        this.playerRepository = playerRepository;
    }

    public PlayerDetail getPlayerById(String id) {
        List<PlayerAppearanceEntity> appearances = playerAppearanceRepository.findAll().stream()
                .filter(player -> CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()).equals(id))
                .toList();

        if (appearances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id);
        }

        List<MatchEntity> matches = matchRepository.findAll();
        List<WorldCupEntity> worldCups = worldCupRepository.findAll();
        PlayerAppearanceEntity latest = appearances.get(appearances.size() - 1);
        String country = resolveCountryName(latest.getTeamInitials(), matches);
        CareerTotals totals = calculateCareerTotals(appearances);

        return new PlayerDetail(
                id,
                CsvSupport.toDisplayName(latest.getPlayerName()),
                CsvSupport.toDisplayName(latest.getPlayerName()),
                null,
                "/images/players/" + id + ".jpg",
                normalizeShirtNumber(latest.getShirtNumber()),
                normalizePosition(latest.getPosition()),
                country,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                appearances.stream().anyMatch(player -> "C".equals(CsvSupport.cleanValue(player.getPosition()))),
                null,
                buildShirtHistory(appearances, matches),
                buildPositionHistory(appearances, matches),
                null,
                new PlayerDetail.CareerStatistics(totals.matches(), totals.goals(), null, totals.yellowCards(), totals.redCards(), null),
                new PlayerDetail.NationalTeamStatistics(totals.matches(), totals.goals(), null),
                List.of(),
                buildWorldCupHistory(appearances, matches, worldCups, latest.getTeamInitials()),
                List.of(),
                List.of(),
                null,
                buildBiography(latest, country, totals.matches(), totals.goals())
        );
    }

    private List<PlayerDetail.ShirtHistory> buildShirtHistory(List<PlayerAppearanceEntity> appearances, List<MatchEntity> matches) {
        Map<Integer, String> shirtsByYear = new LinkedHashMap<>();

        for (PlayerAppearanceEntity player : appearances) {
            if (normalizeShirtNumber(player.getShirtNumber()) == null) {
                continue;
            }
            shirtsByYear.putIfAbsent(findYearByMatchId(player.getMatchId(), matches), player.getShirtNumber());
        }

        return shirtsByYear.entrySet().stream()
                .map(entry -> new PlayerDetail.ShirtHistory("National Team", entry.getValue(), Integer.toString(entry.getKey()), Integer.toString(entry.getKey())))
                .toList();
    }

    private List<PlayerDetail.PositionHistory> buildPositionHistory(List<PlayerAppearanceEntity> appearances, List<MatchEntity> matches) {
        Map<String, Integer> firstYearByPosition = new LinkedHashMap<>();

        for (PlayerAppearanceEntity player : appearances) {
            firstYearByPosition.putIfAbsent(normalizePosition(player.getPosition()), findYearByMatchId(player.getMatchId(), matches));
        }

        return firstYearByPosition.entrySet().stream()
                .map(entry -> new PlayerDetail.PositionHistory(entry.getKey(), Integer.toString(entry.getValue()), null))
                .toList();
    }

    private List<PlayerDetail.PlayerWorldCupHistory> buildWorldCupHistory(
            List<PlayerAppearanceEntity> appearances,
            List<MatchEntity> matches,
            List<WorldCupEntity> worldCups,
            String teamInitials
    ) {
        return appearances.stream()
                .map(player -> findYearByMatchId(player.getMatchId(), matches))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(year -> {
                    List<PlayerAppearanceEntity> byYear = appearances.stream()
                            .filter(player -> findYearByMatchId(player.getMatchId(), matches) == year)
                            .toList();
                    WorldCupEntity worldCup = worldCups.stream()
                            .filter(item -> parseYear(item.getId()) == year)
                            .findFirst()
                            .orElse(null);
                    CareerTotals totals = calculateCareerTotals(byYear);
                    return new PlayerDetail.PlayerWorldCupHistory(
                            Integer.toString(year),
                            worldCup == null ? null : worldCup.getHostCountriesRaw(),
                            resolveFinalPosition(resolveCountryName(teamInitials, matches), worldCup),
                            totals.matches(),
                            totals.goals(),
                            null
                    );
                })
                .toList();
    }

    private CareerTotals calculateCareerTotals(List<PlayerAppearanceEntity> appearances) {
        int goals = 0;
        int yellowCards = 0;
        int redCards = 0;
        Set<String> matches = appearances.stream().map(PlayerAppearanceEntity::getMatchId).collect(Collectors.toSet());

        for (PlayerAppearanceEntity player : appearances) {
            for (String token : CsvSupport.cleanValue(player.getEvent()).split("\\s+")) {
                if (token.startsWith("G")) {
                    goals++;
                } else if (token.startsWith("Y")) {
                    yellowCards++;
                } else if (token.startsWith("R")) {
                    redCards++;
                }
            }
        }

        return new CareerTotals(matches.size(), goals, yellowCards, redCards);
    }

    private String resolveCountryName(String teamInitials, List<MatchEntity> matches) {
        return matches.stream()
                .filter(match -> match.getHomeTeamInitials().equals(teamInitials) || match.getAwayTeamInitials().equals(teamInitials))
                .findFirst()
                .map(match -> match.getHomeTeamInitials().equals(teamInitials) ? match.getHomeTeamName() : match.getAwayTeamName())
                .orElse(null);
    }

    private int findYearByMatchId(String matchId, List<MatchEntity> matches) {
        return matches.stream()
                .filter(match -> match.getId().equals(matchId))
                .map(match -> parseYear(match.getWorldCupId()))
                .findFirst()
                .orElse(0);
    }

    private int parseYear(String worldCupId) {
        return Integer.parseInt(worldCupId.replace("world-cup-", ""));
    }

    private String resolveFinalPosition(String countryName, WorldCupEntity worldCup) {
        if (worldCup == null || countryName == null) {
            return "Participant";
        }
        if (countryName.equals(worldCup.getWinner())) {
            return "Winner";
        }
        if (countryName.equals(worldCup.getRunnersUp())) {
            return "Runner-up";
        }
        if (countryName.equals(worldCup.getThirdPlace())) {
            return "Third place";
        }
        if (countryName.equals(worldCup.getFourthPlace())) {
            return "Fourth place";
        }
        return "Participant";
    }

    private String buildBiography(PlayerAppearanceEntity player, String country, int matches, int goals) {
        return CsvSupport.toDisplayName(player.getPlayerName()) + " represented " + country
                + " in World Cup matches and recorded " + goals + " goals across " + matches + " matches in the available dataset.";
    }

    private String normalizeShirtNumber(String shirtNumber) {
        return "0".equals(shirtNumber) || shirtNumber == null || shirtNumber.isBlank() ? null : shirtNumber;
    }

    private String normalizePosition(String position) {
        return switch (CsvSupport.cleanValue(position)) {
            case "GK" -> "Goalkeeper";
            case "C" -> "Captain";
            case "" -> "Unknown";
            default -> CsvSupport.toDisplayName(position);
        };
    }

    public List<PlayerAppearanceEntity> findAll() {
        return playerAppearanceRepository.findAll();
    }


    public List<PlayerAppearanceEntity> findPage(int page, int size) {
        return playerAppearanceRepository.findAll(
                PageRequest.of(page, size)
        ).getContent();
    }

    public void save(PlayerEntity pendingSave) {
        playerRepository.save(pendingSave);
    }

    private record CareerTotals(int matches, int goals, int yellowCards, int redCards) {
    }
}
