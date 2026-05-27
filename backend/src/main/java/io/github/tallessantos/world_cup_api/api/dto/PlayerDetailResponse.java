package io.github.tallessantos.world_cup_api.api.dto;

import io.github.tallessantos.world_cup_api.core.domain.PlayerDetail;

import java.util.List;

public record PlayerDetailResponse(
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
        List<PlayerDetail.ShirtHistory> shirtHistory,
        List<PlayerDetail.PositionHistory> positionsHistory,
        PlayerDetail.Attributes attributes,
        PlayerDetail.CareerStatistics careerStatistics,
        PlayerDetail.NationalTeamStatistics nationalTeamStatistics,
        List<PlayerDetail.ClubHistory> clubsHistory,
        List<PlayerDetail.PlayerWorldCupHistory> worldCupHistory,
        List<PlayerDetail.PlayerAward> awards,
        List<PlayerDetail.PlayerInjury> injuriesHistory,
        PlayerDetail.SocialMedia socialMedia,
        String biography
) {
    public static PlayerDetailResponse from(PlayerDetail playerDetail) {
        return new PlayerDetailResponse(
                playerDetail.id(),
                playerDetail.name(),
                playerDetail.fullName(),
                playerDetail.nickname(),
                playerDetail.profileImageUrl(),
                playerDetail.number(),
                playerDetail.position(),
                playerDetail.country(),
                playerDetail.birthDate(),
                playerDetail.age(),
                playerDetail.height(),
                playerDetail.weight(),
                playerDetail.preferredFoot(),
                playerDetail.currentClub(),
                playerDetail.marketValue(),
                playerDetail.captain(),
                playerDetail.retired(),
                playerDetail.shirtHistory(),
                playerDetail.positionsHistory(),
                playerDetail.attributes(),
                playerDetail.careerStatistics(),
                playerDetail.nationalTeamStatistics(),
                playerDetail.clubsHistory(),
                playerDetail.worldCupHistory(),
                playerDetail.awards(),
                playerDetail.injuriesHistory(),
                playerDetail.socialMedia(),
                playerDetail.biography()
        );
    }
}
