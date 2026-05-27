package io.github.tallessantos.world_cup_api.api.dto;

import io.github.tallessantos.world_cup_api.core.domain.WorldCup;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public record WorldCupSummaryResponse(
        String id,
        String title,
        String status,
        LocalDate startDate,
        LocalDate endDate,
        List<String> hostCountries,
        String imgBannerUrl
) {
    public static WorldCupSummaryResponse from(WorldCup worldCup) {
        return new WorldCupSummaryResponse(
                worldCup.id(),
                worldCup.title(),
                worldCup.status().name().toLowerCase(Locale.ROOT),
                worldCup.startDate(),
                worldCup.endDate(),
                worldCup.hostCountries(),
                worldCup.imgBannerUrl()
        );
    }
}
