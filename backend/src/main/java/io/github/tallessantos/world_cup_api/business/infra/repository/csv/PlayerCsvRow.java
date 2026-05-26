package io.github.tallessantos.world_cup_api.business.infra.repository.csv;

public record PlayerCsvRow(
        String roundId,
        String matchId,
        String teamInitials,
        String coachName,
        String lineup,
        String shirtNumber,
        String playerName,
        String position,
        String event
) {
}
