package io.github.tallessantos.world_cup_api.repository.csv;

public record WorldCupCsvRow(
        int year,
        String country,
        String winner,
        String runnersUp,
        String third,
        String fourth,
        int goalsScored,
        int qualifiedTeams,
        int matchesPlayed,
        String attendance
) {
}
