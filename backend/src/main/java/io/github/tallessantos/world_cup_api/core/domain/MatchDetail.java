package io.github.tallessantos.world_cup_api.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public record MatchDetail(
        String id,
        String worldCupId,
        String status,
        String stage,
        String groupName,
        LocalDateTime kickoffAt,
        String stadium,
        String city,
        TeamReference homeTeam,
        TeamReference visitingTeam,
        Integer homeTeamScore,
        Integer visitingTeamScore,
        List<StatisticItem> basicStatistics,
        List<ComparativeStatistic> comparativeStatistics,
        List<PlayerReference> homeTeamMatchPlayers,
        List<PlayerReference> visitingTeamMatchPlayers,
        List<MatchVideo> matchVideos
) {
}
