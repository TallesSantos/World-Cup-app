package io.github.tallessantos.world_cup_api.api.service;

import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.infra.repository.CountryRepository;
import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerAppearanceRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class MatchApiService {

    private final MatchRepository matchRepository;
    private final PlayerAppearanceRepository playerAppearanceRepository;
    private final CountryRepository countryRepository;

    public MatchApiService(MatchRepository matchRepository, PlayerAppearanceRepository playerAppearanceRepository,
                           CountryRepository countryRepository) {
        this.matchRepository = matchRepository;
        this.playerAppearanceRepository = playerAppearanceRepository;
        this.countryRepository = countryRepository;
    }

    public MatchDetail getMatchById(String id) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found: " + id));

        List<PlayerAppearanceEntity> players = playerAppearanceRepository.findByMatchId(id);

        return new MatchDetail(
                match.getId(),
                match.getWorldCupId(),
                "completed",
                normalizeStage(match.getStage()),
                extractGroupName(match.getStage()),
                match.getKickoffAt(),
                match.getStadium(),
                match.getCity(),
                toTeamReference(match.getHomeTeamName(), match.getHomeTeamInitials()),
                toTeamReference(match.getAwayTeamName(), match.getAwayTeamInitials()),
                match.getHomeTeamGoals(),
                match.getAwayTeamGoals(),
                buildBasicStatistics(match),
                List.of(),
                buildTeamPlayers(players, match.getHomeTeamInitials()),
                buildTeamPlayers(players, match.getAwayTeamInitials()),
                List.of()
        );
    }

    private List<StatisticItem> buildBasicStatistics(MatchEntity match) {
        return List.of(
                new StatisticItem("Stage", safeValue(match.getStage())),
                new StatisticItem("Date", match.getKickoffAt() == null ? "Unknown" : match.getKickoffAt().toString()),
                new StatisticItem("Attendance", match.getAttendance() == null ? "Unknown" : match.getAttendance().toString()),
                new StatisticItem("City", safeValue(match.getCity())),
                new StatisticItem("Referee", safeValue(match.getReferee())),
                new StatisticItem("Win conditions", match.getWinConditions() == null || match.getWinConditions().isBlank() ? "Regular time" : match.getWinConditions())
        );
    }

    private List<PlayerReference> buildTeamPlayers(List<PlayerAppearanceEntity> players, String teamInitials) {
        Map<String, PlayerReference> playersById = new LinkedHashMap<>();

        players.stream()
                .filter(player -> player.getTeamInitials().equals(teamInitials))
                .sorted(Comparator.comparing(PlayerAppearanceEntity::getLineup))
                .forEach(player -> playersById.putIfAbsent(
                        CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                        new PlayerReference(
                                CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                                CsvSupport.toDisplayName(player.getPlayerName()),
                                normalizeShirtNumber(player.getShirtNumber()),
                                normalizePosition(player.getPosition())
                        )
                ));

        return List.copyOf(playersById.values());
    }

    private TeamReference toTeamReference(String teamName, String initials) {
        String id = CsvSupport.slugify(teamName);

        Optional<CountryEntity> entity = countryRepository.findByInitials(initials);
        String pathImage = null;
        if(entity.isPresent() && entity.get().getCountryFlagImage() != null){
            pathImage = entity.get().getCountryFlagImage().getFullResourcePath();
        }
        return new TeamReference(id, CsvSupport.cleanValue(teamName), initials, pathImage, null, null, null, null);
    }

    private String normalizeStage(String stage) {
        if (stage.startsWith("Group")) {
            return "group";
        }

        return switch (stage) {
            case "Round of 16", "Preliminary round" -> "round_of_16";
            case "Quarter-finals" -> "quarter_finals";
            case "Semi-finals" -> "semi_finals";
            case "Match for third place" -> "third_place";
            case "Final" -> "final";
            default -> CsvSupport.slugify(stage).replace('-', '_');
        };
    }

    private String extractGroupName(String stage) {
        return stage.startsWith("Group") ? stage : null;
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

    private String safeValue(String value) {
        String cleaned = CsvSupport.cleanValue(value);
        return cleaned.isBlank() ? "Unknown" : cleaned;
    }

    public void save(MatchEntity pendingSave) {
        matchRepository.save(pendingSave);
    }

    public List<PlayerAppearanceEntity> getPlayerByMatchId(String id) {
        return playerAppearanceRepository.findByMatchId(id);
    }
}
