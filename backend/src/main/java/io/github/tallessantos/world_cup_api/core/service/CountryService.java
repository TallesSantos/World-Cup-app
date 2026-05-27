package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.infra.repository.MatchRepository;
import io.github.tallessantos.world_cup_api.core.domain.CountryDetail;
import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerReference;
import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerRepository;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.infra.repository.csv.CsvSupport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CountryService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final WorldCupRepository worldCupRepository;

    public CountryService(
            MatchRepository matchRepository,
            PlayerRepository playerRepository,
            WorldCupRepository worldCupRepository
    ) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.worldCupRepository = worldCupRepository;
    }

    public CountryDetail getCountryById(String id) {
        List<MatchEntity> matches = matchRepository.findAll();
        CountryLookup lookup = resolveCountryLookup(id, matches);
        if (lookup == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found: " + id);
        }

        List<MatchEntity> countryMatches = matchRepository.findByHomeTeamInitialsOrAwayTeamInitials(lookup.fifaCode(), lookup.fifaCode());
        List<String> matchIds = countryMatches.stream().map(MatchEntity::getId).toList();
        List<PlayerAppearanceEntity> countryPlayers = playerRepository.findByTeamInitials(lookup.fifaCode());
        List<WorldCupEntity> worldCups = worldCupRepository.findAll();

        Set<Integer> years = countryMatches.stream()
                .map(match -> parseYear(match.getWorldCupId()))
                .collect(Collectors.toCollection(TreeSet::new));

        CountryDetail.AllTimeStatistics allTimeStatistics = buildAllTimeStatistics(countryMatches, lookup.fifaCode());
        PlayerReference captain = findCaptain(countryPlayers);

        return new CountryDetail(
                lookup.id(),
                lookup.name(),
                lookup.fifaCode(),
                "/images/flags/" + lookup.id() + ".svg",
                null,
                latestCoach(countryPlayers),
                captain,
                null,
                years.size(),
                resolveBestWorldCupFinish(lookup.name(), worldCups),
                new CountryDetail.Titles(countTitles(lookup.name(), worldCups), null, null, null),
                allTimeStatistics,
                new CountryDetail.AllTimeSquad(
                        buildCurrentSquad(countryPlayers, countryMatches, years),
                        buildPlayers(countryPlayers)
                ),
                buildLegendaryPlayers(countryPlayers),
                buildWorldCupHistory(lookup, countryMatches, countryPlayers, worldCups, matchIds)
        );
    }

    private List<CountryDetail.CountryWorldCupHistory> buildWorldCupHistory(
            CountryLookup lookup,
            List<MatchEntity> countryMatches,
            List<PlayerAppearanceEntity> countryPlayers,
            List<WorldCupEntity> worldCups,
            List<String> matchIds
    ) {
        return countryMatches.stream()
                .map(match -> parseYear(match.getWorldCupId()))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(year -> {
                    WorldCupEntity worldCup = worldCups.stream()
                            .filter(item -> parseYear(item.getId()) == year)
                            .findFirst()
                            .orElse(null);

                    List<MatchEntity> matchesByYear = countryMatches.stream()
                            .filter(match -> parseYear(match.getWorldCupId()) == year)
                            .toList();

                    List<PlayerAppearanceEntity> playersByYear = countryPlayers.stream()
                            .filter(player -> matchIds.contains(player.getMatchId()))
                            .filter(player -> matchesByYear.stream().anyMatch(match -> match.getId().equals(player.getMatchId())))
                            .toList();

                    String finalPosition = resolveFinalPosition(lookup.name(), worldCup);
                    CountryDetail.AllTimeStatistics statistics = buildAllTimeStatistics(matchesByYear, lookup.fifaCode());
                    PlayerReference topScorerPlayer = topScorer(playersByYear);
                    Integer topScorerGoals = countGoals(playersByYear, topScorerPlayer == null ? null : topScorerPlayer.id());

                    return new CountryDetail.CountryWorldCupHistory(
                            Integer.toString(year),
                            worldCup == null ? null : worldCup.getHostCountriesRaw(),
                            finalPosition,
                            latestCoach(playersByYear),
                            findCaptain(playersByYear),
                            lookup.name() + " finished as " + finalPosition + " in FIFA World Cup " + year + ".",
                            statistics,
                            topScorerPlayer == null ? null : new CountryDetail.TopScorer(topScorerPlayer, topScorerGoals),
                            buildPlayers(playersByYear)
                    );
                })
                .toList();
    }

    private CountryDetail.AllTimeStatistics buildAllTimeStatistics(List<MatchEntity> matches, String teamInitials) {
        int wins = 0;
        int draws = 0;
        int losses = 0;
        int goalsScored = 0;
        int goalsConceded = 0;

        for (MatchEntity match : matches) {
            boolean home = match.getHomeTeamInitials().equals(teamInitials);
            int scored = home ? match.getHomeTeamGoals() : match.getAwayTeamGoals();
            int conceded = home ? match.getAwayTeamGoals() : match.getHomeTeamGoals();

            goalsScored += scored;
            goalsConceded += conceded;
            if (scored > conceded) {
                wins++;
            } else if (scored == conceded) {
                draws++;
            } else {
                losses++;
            }
        }

        return new CountryDetail.AllTimeStatistics(matches.size(), wins, draws, losses, goalsScored, goalsConceded);
    }

    private List<PlayerReference> buildCurrentSquad(List<PlayerAppearanceEntity> countryPlayers, List<MatchEntity> countryMatches, Set<Integer> years) {
        if (years.isEmpty()) {
            return List.of();
        }

        int latestYear = years.stream().max(Integer::compareTo).orElseThrow();
        Set<String> latestYearMatchIds = countryMatches.stream()
                .filter(match -> parseYear(match.getWorldCupId()) == latestYear)
                .map(MatchEntity::getId)
                .collect(Collectors.toSet());

        List<PlayerAppearanceEntity> latestPlayers = countryPlayers.stream()
                .filter(player -> latestYearMatchIds.contains(player.getMatchId()))
                .toList();

        return buildPlayers(latestPlayers);
    }

    private List<PlayerReference> buildLegendaryPlayers(List<PlayerAppearanceEntity> countryPlayers) {
        Map<String, Long> appearances = countryPlayers.stream()
                .collect(Collectors.groupingBy(
                        player -> CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                        Collectors.mapping(PlayerAppearanceEntity::getMatchId, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return buildPlayers(countryPlayers).stream()
                .sorted(Comparator.comparing((PlayerReference player) -> appearances.getOrDefault(player.id(), 0L)).reversed())
                .limit(5)
                .toList();
    }

    private List<PlayerReference> buildPlayers(List<PlayerAppearanceEntity> players) {
        Map<String, PlayerReference> references = new LinkedHashMap<>();
        players.forEach(player -> references.putIfAbsent(
                CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                new PlayerReference(
                        CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                        CsvSupport.toDisplayName(player.getPlayerName()),
                        normalizeShirtNumber(player.getShirtNumber()),
                        normalizePosition(player.getPosition())
                )
        ));
        return List.copyOf(references.values());
    }

    private PlayerReference findCaptain(List<PlayerAppearanceEntity> players) {
        return players.stream()
                .filter(player -> "C".equals(CsvSupport.cleanValue(player.getPosition())))
                .findFirst()
                .map(player -> new PlayerReference(
                        CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()),
                        CsvSupport.toDisplayName(player.getPlayerName()),
                        normalizeShirtNumber(player.getShirtNumber()),
                        "Captain"
                ))
                .orElseGet(() -> {
                    List<PlayerReference> mapped = buildPlayers(players);
                    return mapped.isEmpty() ? null : mapped.get(0);
                });
    }

    private PlayerReference topScorer(List<PlayerAppearanceEntity> players) {
        Map<String, Integer> goals = new LinkedHashMap<>();
        Map<String, PlayerReference> refs = new LinkedHashMap<>();

        for (PlayerAppearanceEntity player : players) {
            String playerId = CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName());
            refs.putIfAbsent(playerId, new PlayerReference(
                    playerId,
                    CsvSupport.toDisplayName(player.getPlayerName()),
                    normalizeShirtNumber(player.getShirtNumber()),
                    normalizePosition(player.getPosition())
            ));
            goals.merge(playerId, countGoals(player.getEvent()), Integer::sum);
        }

        return goals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> refs.get(entry.getKey()))
                .orElse(null);
    }

    private int countGoals(List<PlayerAppearanceEntity> players, String playerId) {
        if (playerId == null) {
            return 0;
        }

        return players.stream()
                .filter(player -> CsvSupport.playerId(player.getTeamInitials(), player.getPlayerName()).equals(playerId))
                .mapToInt(player -> countGoals(player.getEvent()))
                .sum();
    }

    private int countGoals(String event) {
        String cleaned = CsvSupport.cleanValue(event);
        if (cleaned.isBlank()) {
            return 0;
        }

        int goals = 0;
        for (String token : cleaned.split("\\s+")) {
            if (token.startsWith("G")) {
                goals++;
            }
        }
        return goals;
    }

    private String latestCoach(List<PlayerAppearanceEntity> players) {
        return players.stream()
                .reduce((first, second) -> second)
                .map(PlayerAppearanceEntity::getCoachName)
                .map(CsvSupport::cleanValue)
                .orElse(null);
    }

    private String resolveBestWorldCupFinish(String countryName, List<WorldCupEntity> worldCups) {
        List<String> order = List.of("Winner", "Runner-up", "Third place", "Fourth place", "Participant");
        return worldCups.stream()
                .map(worldCup -> resolveFinalPosition(countryName, worldCup))
                .min(Comparator.comparingInt(order::indexOf))
                .orElse("Participant");
    }

    private String resolveFinalPosition(String countryName, WorldCupEntity worldCup) {
        if (worldCup == null) {
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

    private int countTitles(String countryName, List<WorldCupEntity> worldCups) {
        return (int) worldCups.stream().filter(worldCup -> countryName.equals(worldCup.getWinner())).count();
    }

    private CountryLookup resolveCountryLookup(String id, List<MatchEntity> matches) {
        return matches.stream()
                .flatMap(match -> Stream.of(
                        new CountryLookup(CsvSupport.slugify(match.getHomeTeamName()), CsvSupport.cleanValue(match.getHomeTeamName()), match.getHomeTeamInitials()),
                        new CountryLookup(CsvSupport.slugify(match.getAwayTeamName()), CsvSupport.cleanValue(match.getAwayTeamName()), match.getAwayTeamInitials())
                ))
                .filter(country -> country.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int parseYear(String worldCupId) {
        return Integer.parseInt(worldCupId.replace("world-cup-", ""));
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

    public List<CountryDetail> findAll() {

        return List.of(BRASIL_MOCK);
    }

    private record CountryLookup(String id, String name, String fifaCode) {
    }

    private static final CountryDetail BRASIL_MOCK = new CountryDetail(
            "brazil",
            "Brazil",
            "BRA",
            "https://example.com/flags/brazil.png",
            "CONMEBOL",
            "Dorival Júnior",
            new PlayerReference(
                    "casemiro",
                    "Casemiro",
                    "5.",
                    "Forward"
            ),
            5,
            22,
            "Champion",
            new CountryDetail.Titles(
                    5,
                    9,
                    4,
                    2
            ),
            new CountryDetail.AllTimeStatistics(
                    114,
                    76,
                    19,
                    19,
                    237,
                    108
            ),
            new CountryDetail.AllTimeSquad(
                    List.of(
                            new PlayerReference("vinicius-jr", "Vinícius Júnior", "11","Forward" ),
                            new PlayerReference("rodrygo", "Rodrygo", "12", "Forward"),
                            new PlayerReference("marquinhos", "Marquinhos", "13","Defender")
                    ),
                    List.of(
                            new PlayerReference("pele", "Pelé","10", "Forward"),
                            new PlayerReference("ronaldo", "Ronaldo", "9","Forward"),
                            new PlayerReference("romario", "Romário", "9","Forward"),
                            new PlayerReference("zico", "Zico", "10","Midfielder"),
                            new PlayerReference("cafu", "Cafu", "2", "Defender")
                    )
            ),
            List.of(
                    new PlayerReference("pele", "Pelé","10", "Forward"),
                    new PlayerReference("garrincha", "Garrincha","10", "Winger"),
                    new PlayerReference("ronaldo", "Ronaldo", "09","Forward"),
                    new PlayerReference("ronaldinho", "Ronaldinho", "10", "Midfielder")
            ),
            List.of(
                    new CountryDetail.CountryWorldCupHistory(
                            "2002",
                            "South Korea / Japan",
                            "Champion",
                            "Luiz Felipe Scolari",
                            new PlayerReference(
                                    "cafu",
                                    "Cafu",
                                    "02",
                                    "Defender"
                            ),
                            "Won all matches and defeated Germany in the final.",
                            new CountryDetail.AllTimeStatistics(
                                    7,
                                    7,
                                    0,
                                    0,
                                    18,
                                    4
                            ),
                            new CountryDetail.TopScorer(
                                    new PlayerReference(
                                            "ronaldo",
                                            "Ronaldo",
                                            "09",
                                            "Forward"
                                    ),
                                    8
                            ),
                            List.of(
                                    new PlayerReference("ronaldo", "Ronaldo","09", "Forward"),
                                    new PlayerReference("rivaldo", "Rivaldo","11", "Midfielder"),
                                    new PlayerReference("ronaldinho", "Ronaldinho","10", "Midfielder")
                            )
                    ),
                    new CountryDetail.CountryWorldCupHistory(
                            "1994",
                            "United States",
                            "Champion",
                            "Carlos Alberto Parreira",
                            new PlayerReference(
                                    "dunga",
                                    "Dunga",
                                    "04",
                                    "Midfielder"
                            ),
                            "Won the title on penalties against Italy.",
                            new CountryDetail.AllTimeStatistics(
                                    7,
                                    5,
                                    2,
                                    0,
                                    11,
                                    3
                            ),
                            new CountryDetail.TopScorer(
                                    new PlayerReference(
                                            "romario",
                                            "Romário",
                                            "09",
                                            "Forward"
                                    ),
                                    5
                            ),
                            List.of(
                                    new PlayerReference("romario", "Romário", "09", "Forward"),
                                    new PlayerReference("bebeto", "Bebeto", "09","Forward"),
                                    new PlayerReference("dunga", "Dunga", "04","Midfielder")
                            )
                    )
            )
    );
}
