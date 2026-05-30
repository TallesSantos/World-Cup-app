package io.github.tallessantos.world_cup_api.api.dto;

import io.github.tallessantos.world_cup_api.core.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public record MatchDetailResponse(
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
        List<MediaEntity> matchVideos
) {
    public static MatchDetailResponse from(MatchDetail matchDetail) {
        return new MatchDetailResponse(
                matchDetail.id(),
                matchDetail.worldCupId(),
                matchDetail.status(),
                matchDetail.stage(),
                matchDetail.groupName(),
                matchDetail.kickoffAt(),
                matchDetail.stadium(),
                matchDetail.city(),
                matchDetail.homeTeam(),
                matchDetail.visitingTeam(),
                matchDetail.homeTeamScore(),
                matchDetail.visitingTeamScore(),
                matchDetail.basicStatistics(),
                matchDetail.comparativeStatistics(),
                matchDetail.homeTeamMatchPlayers(),
                matchDetail.visitingTeamMatchPlayers(),
                matchDetail.matchVideos()
        );
    }
}
