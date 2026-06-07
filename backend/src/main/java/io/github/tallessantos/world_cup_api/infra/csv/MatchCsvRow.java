package io.github.tallessantos.world_cup_api.infra.csv;

public record MatchCsvRow(
        int year,
        String datetime,
        String stage,
        String stadium,
        String city,
        String homeTeamName,
        int homeTeamGoals,
        int awayTeamGoals,
        String awayTeamName,
        String winConditions,
        Integer attendance,
        Integer halfTimeHomeGoals,
        Integer halfTimeAwayGoals,
        String referee,
        String assistant1,
        String assistant2,
        String roundId,
        String matchId,
        String homeTeamInitials,
        String awayTeamInitials
) {
}
