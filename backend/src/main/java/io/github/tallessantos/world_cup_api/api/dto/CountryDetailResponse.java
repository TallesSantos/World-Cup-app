package io.github.tallessantos.world_cup_api.api.dto;

import io.github.tallessantos.world_cup_api.core.domain.CountryDetail;
import io.github.tallessantos.world_cup_api.core.domain.PlayerReference;

import java.util.List;

public record CountryDetailResponse(
        String id,
        String name,
        String fifaCode,
        String flagUrl,
        String confederation,
        String coach,
        PlayerReference captain,
        Integer fifaRanking,
        Integer worldCupAppearances,
        String bestWorldCupFinish,
        CountryDetail.Titles titles,
        CountryDetail.AllTimeStatistics allTimeStatistics,
        CountryDetail.AllTimeSquad allTimeSquad,
        List<PlayerReference> legendaryPlayers,
        List<CountryDetail.CountryWorldCupHistory> worldCupHistory
) {
    public static CountryDetailResponse from(CountryDetail countryDetail) {
        return new CountryDetailResponse(
                countryDetail.id(),
                countryDetail.name(),
                countryDetail.fifaCode(),
                countryDetail.flagUrl(),
                countryDetail.confederation(),
                countryDetail.coach(),
                countryDetail.captain(),
                countryDetail.fifaRanking(),
                countryDetail.worldCupAppearances(),
                countryDetail.bestWorldCupFinish(),
                countryDetail.titles(),
                countryDetail.allTimeStatistics(),
                countryDetail.allTimeSquad(),
                countryDetail.legendaryPlayers(),
                countryDetail.worldCupHistory()
        );
    }
}
