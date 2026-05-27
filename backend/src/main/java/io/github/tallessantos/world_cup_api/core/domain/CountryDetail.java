package io.github.tallessantos.world_cup_api.core.domain;

import java.util.List;

public record CountryDetail(
        String id,
        String name,
        String fifaCode,
        String flagUrl,
        String confederation,
        String coach,
        PlayerReference captain,
        Integer fifaRanking,
        Integer worldCupAppearances,
        String bestWorldCupFinish,
        Titles titles,
        AllTimeStatistics allTimeStatistics,
        AllTimeSquad allTimeSquad,
        List<PlayerReference> legendaryPlayers,
        List<CountryWorldCupHistory> worldCupHistory
) {
    public record Titles(
            Integer worldCups,
            Integer continentalCups,
            Integer confederationsCup,
            Integer olympicGoldMedals
    ) {
    }

    public record AllTimeStatistics(
            Integer matches,
            Integer wins,
            Integer draws,
            Integer losses,
            Integer goalsScored,
            Integer goalsConceded
    ) {
    }

    public record AllTimeSquad(
            List<PlayerReference> currentSquad,
            List<PlayerReference> allPlayers
    ) {
    }

    public record CountryWorldCupHistory(
            String worldCupYear,
            String hostCountry,
            String finalPosition,
            String coach,
            PlayerReference captain,
            String campaignSummary,
            AllTimeStatistics statistics,
            TopScorer topScorer,
            List<PlayerReference> squad
    ) {
    }

    public record TopScorer(
            PlayerReference player,
            Integer goals
    ) {
    }
}
