package io.github.tallessantos.world_cup_api.business.core.domain;

import java.util.List;

public record PlayerDetail(
        String id,
        String name,
        String fullName,
        String nickname,
        String profileImageUrl,
        String number,
        String position,
        String country,
        String birthDate,
        Integer age,
        String height,
        String weight,
        String preferredFoot,
        String currentClub,
        String marketValue,
        Boolean captain,
        Boolean retired,
        List<ShirtHistory> shirtHistory,
        List<PositionHistory> positionsHistory,
        Attributes attributes,
        CareerStatistics careerStatistics,
        NationalTeamStatistics nationalTeamStatistics,
        List<ClubHistory> clubsHistory,
        List<PlayerWorldCupHistory> worldCupHistory,
        List<PlayerAward> awards,
        List<PlayerInjury> injuriesHistory,
        SocialMedia socialMedia,
        String biography
) {
    public record ShirtHistory(
            String club,
            String number,
            String startYear,
            String endYear
    ) {
    }

    public record PositionHistory(
            String position,
            String startYear,
            String endYear
    ) {
    }

    public record Attributes(
            Integer pace,
            Integer shooting,
            Integer passing,
            Integer dribbling,
            Integer defending,
            Integer physical
    ) {
    }

    public record CareerStatistics(
            Integer matches,
            Integer goals,
            Integer assists,
            Integer yellowCards,
            Integer redCards,
            Integer minutesPlayed
    ) {
    }

    public record NationalTeamStatistics(
            Integer matches,
            Integer goals,
            Integer assists
    ) {
    }

    public record ClubHistory(
            String clubName,
            String startYear,
            String endYear,
            Integer matches,
            Integer goals
    ) {
    }

    public record PlayerWorldCupHistory(
            String year,
            String hostCountry,
            String finalPosition,
            Integer matches,
            Integer goals,
            Integer assists
    ) {
    }

    public record PlayerAward(
            String title,
            String year
    ) {
    }

    public record PlayerInjury(
            String injury,
            String startDate,
            String endDate
    ) {
    }

    public record SocialMedia(
            String instagram,
            String twitter,
            String facebook
    ) {
    }
}
