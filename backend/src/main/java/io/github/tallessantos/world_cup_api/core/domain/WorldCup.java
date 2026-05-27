package io.github.tallessantos.world_cup_api.core.domain;

import java.time.LocalDate;
import java.util.List;

public record WorldCup(
        String id,
        String title,
        WorldCupStatus status,
        LocalDate startDate,
        LocalDate endDate,
        List<String> hostCountries,
        String imgBannerUrl
) {
}
