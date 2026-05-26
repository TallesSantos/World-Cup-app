package io.github.tallessantos.world_cup_api.api.dto;

import io.github.tallessantos.world_cup_api.business.core.domain.WorldCupDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public record WorldCupDetailResponse(
        String id,
        String title,
        String status,
        LocalDate startDate,
        LocalDate endDate,
        List<String> hostCountries,
        String imgBannerUrl,
        WorldCupDetail.FinalStandings finalStandings,
        WorldCupDetail.Awards awards,
        WorldCupDetail.TournamentStatistics statistics,
        List<WorldCupDetail.GroupStageGroup> groupStage,
        WorldCupDetail.KnockoutStage knockoutStage
) {
    public static WorldCupDetailResponse from(WorldCupDetail worldCupDetail) {
        return new WorldCupDetailResponse(
                worldCupDetail.id(),
                worldCupDetail.title(),
                worldCupDetail.status().name().toLowerCase(Locale.ROOT),
                worldCupDetail.startDate(),
                worldCupDetail.endDate(),
                worldCupDetail.hostCountries(),
                worldCupDetail.imgBannerUrl(),
                worldCupDetail.finalStandings(),
                worldCupDetail.awards(),
                worldCupDetail.statistics(),
                worldCupDetail.groupStage(),
                worldCupDetail.knockoutStage()
        );
    }
}
