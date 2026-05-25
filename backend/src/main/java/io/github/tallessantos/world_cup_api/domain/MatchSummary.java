package io.github.tallessantos.world_cup_api.domain;

public record MatchSummary(
        String id,
        TeamReference homeTeam,
        Integer homeTeamScore,
        TeamReference visitingTeam,
        Integer visitingTeamScore
) {
}
