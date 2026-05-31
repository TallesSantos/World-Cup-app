package io.github.tallessantos.world_cup_api.api.service;

import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorldCupApiService {

    private final WorldCupRepository worldCupRepository;
    private final MatchRepository matchRepository;

    public WorldCupApiService(WorldCupRepository worldCupRepository, MatchRepository matchRepository) {
        this.worldCupRepository = worldCupRepository;
        this.matchRepository = matchRepository;
    }

    public List<WorldCup> listWorldCups() {
        return worldCupRepository.findAll().stream()
                .map(this::toSummary)
                .sorted(Comparator.comparing(WorldCup::startDate).reversed())
                .toList();
    }

    public WorldCupDetail getWorldCupById(String id) {
        WorldCupEntity worldCup = worldCupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "World cup not found: " + id));

        List<MatchEntity> matches = matchRepository.findByWorldCupId(id);

        return new WorldCupDetail(
                worldCup.getId(),
                worldCup.getTitle(),
                WorldCupStatus.valueOf(worldCup.getStatus().toUpperCase()),
                worldCup.getStartDate(),
                worldCup.getEndDate(),
                splitHostCountries(worldCup.getHostCountriesRaw()),
                worldCup.getImgBannerUrl(),
                new WorldCupDetail.FinalStandings(worldCup.getWinner(), worldCup.getRunnersUp(), worldCup.getThirdPlace()),
                new WorldCupDetail.Awards(null, null, null, null),
                new WorldCupDetail.TournamentStatistics(worldCup.getMatchesPlayed(), worldCup.getGoalsScored(), worldCup.getAttendance()),
                buildGroupStage(matches),
                buildKnockoutStage(matches)
        );
    }

    private WorldCup toSummary(WorldCupEntity entity) {
        return new WorldCup(
                entity.getId(),
                entity.getTitle(),
                WorldCupStatus.valueOf(entity.getStatus().toUpperCase()),
                entity.getStartDate(),
                entity.getEndDate(),
                splitHostCountries(entity.getHostCountriesRaw()),
                entity.getImgBannerUrl()
        );
    }

    private List<String> splitHostCountries(String value) {
        return Arrays.stream(value.split("/"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }

    private List<WorldCupDetail.GroupStageGroup> buildGroupStage(List<MatchEntity> matches) {
        Map<String, List<MatchEntity>> groupMatches = matches.stream()
                .filter(match -> match.getStage().startsWith("Group"))
                .collect(Collectors.groupingBy(MatchEntity::getStage));

        return groupMatches.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new WorldCupDetail.GroupStageGroup(
                        entry.getKey(),
                        buildGroupTeams(entry.getValue()),
                        entry.getValue().stream().map(this::toMatchSummary).toList()
                ))
                .toList();
    }

    private List<TeamReference> buildGroupTeams(List<MatchEntity> groupMatches) {
        Map<String, TeamStats> statsByTeam = groupMatches.stream()
                .flatMap(match -> Stream.of(
                        match.getHomeTeamInitials() + "|" + match.getHomeTeamName(),
                        match.getAwayTeamInitials() + "|" + match.getAwayTeamName()
                ))
                .distinct()
                .collect(Collectors.toMap(value -> value, value -> new TeamStats(), (left, right) -> left));

        for (MatchEntity match : groupMatches) {
            updateGroupStats(statsByTeam.get(match.getHomeTeamInitials() + "|" + match.getHomeTeamName()), match.getHomeTeamGoals(), match.getAwayTeamGoals());
            updateGroupStats(statsByTeam.get(match.getAwayTeamInitials() + "|" + match.getAwayTeamName()), match.getAwayTeamGoals(), match.getHomeTeamGoals());
        }

        List<Map.Entry<String, TeamStats>> ordered = new ArrayList<>(statsByTeam.entrySet());
        ordered.sort(Comparator
                .comparing((Map.Entry<String, TeamStats> entry) -> entry.getValue().points).reversed()
                .thenComparing(entry -> entry.getValue().goalDifference, Comparator.reverseOrder())
                .thenComparing(entry -> entry.getValue().goalsScored, Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey));

        List<TeamReference> teams = new ArrayList<>();
        for (int index = 0; index < ordered.size(); index++) {
            String[] parts = ordered.get(index).getKey().split("\\|", 2);
            TeamStats stats = ordered.get(index).getValue();
            String id = CsvSupport.slugify(parts[1]);
            teams.add(new TeamReference(id, parts[1], parts[0], "/images/flags/" + id + ".svg", stats.points, index + 1, null, null));
        }

        return teams;
    }

    private void updateGroupStats(TeamStats stats, int scored, int conceded) {
        stats.goalsScored += scored;
        stats.goalDifference += scored - conceded;
        if (scored > conceded) {
            stats.points += 3;
        } else if (scored == conceded) {
            stats.points += 1;
        }
    }

    private WorldCupDetail.KnockoutStage buildKnockoutStage(List<MatchEntity> matches) {
        List<MatchSummary> roundOf16 = matches.stream()
                .filter(match -> match.getStage().equals("Round of 16") || match.getStage().equals("Preliminary round"))
                .map(this::toMatchSummary)
                .toList();
        List<MatchSummary> quarterFinals = matches.stream()
                .filter(match -> match.getStage().equals("Quarter-finals"))
                .map(this::toMatchSummary)
                .toList();
        List<MatchSummary> semiFinals = matches.stream()
                .filter(match -> match.getStage().equals("Semi-finals"))
                .map(this::toMatchSummary)
                .toList();
        MatchSummary thirdPlaceMatch = matches.stream()
                .filter(match -> match.getStage().equals("Match for third place"))
                .findFirst()
                .map(this::toMatchSummary)
                .orElse(null);
        MatchSummary finalMatch = matches.stream()
                .filter(match -> match.getStage().equals("Final"))
                .findFirst()
                .map(this::toMatchSummary)
                .orElse(null);

        return new WorldCupDetail.KnockoutStage(
                new WorldCupDetail.RoundMatches(roundOf16),
                new WorldCupDetail.RoundMatches(quarterFinals),
                new WorldCupDetail.RoundMatches(semiFinals),
                thirdPlaceMatch,
                finalMatch
        );
    }

    private MatchSummary toMatchSummary(MatchEntity match) {
        return new MatchSummary(
                match.getId(),
                toTeamReference(match.getHomeTeamName(), match.getHomeTeamInitials()),
                match.getHomeTeamGoals(),
                toTeamReference(match.getAwayTeamName(), match.getAwayTeamInitials()),
                match.getAwayTeamGoals()
        );
    }

    private TeamReference toTeamReference(String teamName, String initials) {
        String id = CsvSupport.slugify(teamName);
        return new TeamReference(id, teamName, initials, "/images/flags/" + id + ".svg", null, null, null, null);
    }

    public List<WorldCupEntity> findAll() {
        return worldCupRepository.findAll();
    }

    public List<WorldCupEntity> findPage(int page, int size) {
        return worldCupRepository.findAll(
                PageRequest.of(page, size)
        ).getContent();
    }

    private static final class TeamStats {
        private int points;
        private int goalsScored;
        private int goalDifference;
    }
}
