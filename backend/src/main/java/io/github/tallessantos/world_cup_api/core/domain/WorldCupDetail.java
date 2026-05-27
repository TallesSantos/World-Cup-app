package io.github.tallessantos.world_cup_api.core.domain;

import java.time.LocalDate;
import java.util.List;

public record WorldCupDetail(
        String id,
        String title,
        WorldCupStatus status,
        LocalDate startDate,
        LocalDate endDate,
        List<String> hostCountries,
        String imgBannerUrl,
        FinalStandings finalStandings,
        Awards awards,
        TournamentStatistics statistics,
        List<GroupStageGroup> groupStage,
        KnockoutStage knockoutStage
) {
    public record FinalStandings(
            String firstPlace,
            String secondPlace,
            String thirdPlace
    ) {
    }

    public record Awards(
            String bestPlayer,
            String topScorer,
            Integer topScorerGoals,
            String bestGoalkeeper
    ) {
    }

    public record TournamentStatistics(
            Integer totalMatches,
            Integer totalGoals,
            String attendance
    ) {
    }

    public record GroupStageGroup(
            String groupName,
            List<TeamReference> teams,
            List<MatchSummary> matches
    ) {
    }

    public record KnockoutStage(
            RoundMatches roundOf16,
            RoundMatches quarterFinals,
            RoundMatches semiFinals,
            MatchSummary thirdPlaceMatch,
            MatchSummary finalMatch
    ) {
    }

    public record RoundMatches(
            List<MatchSummary> matches
    ) {
    }
}
