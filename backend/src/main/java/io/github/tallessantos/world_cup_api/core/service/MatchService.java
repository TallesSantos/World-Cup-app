package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.core.domain.MatchDetail;
import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerReference;
import io.github.tallessantos.world_cup_api.core.domain.StatisticItem;
import io.github.tallessantos.world_cup_api.core.domain.TeamReference;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public MatchDetail getMatchById(String id) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found: " + id));

        List<PlayerAppearanceEntity> players = playerRepository.findByMatchId(id);

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
        return new TeamReference(id, CsvSupport.cleanValue(teamName), initials, "/images/flags/" + id + ".svg", null, null, null, null);
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

    public List<MatchEntity> findAll() {
        return matchRepository.findAll();
    }

    public void save(MatchEntity pendingSave) {
        matchRepository.save(pendingSave);
    }

    public Page<MatchEntity> findFiltered(String filterStage,
                                          String filterCity,
                                          String filterHomeTeam,
                                          String filterAwayTeam,
                                          Boolean filterFinished,
                                          int page,
                                          int size,
                                          String sortField,
                                          String sortDirection) {


        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return matchRepository.findFiltered(
                filterStage,
                filterCity,
                filterHomeTeam,
                filterAwayTeam,
                filterFinished,
                pageable

        );
    }
}
