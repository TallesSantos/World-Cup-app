package io.github.tallessantos.world_cup_api.business.infra.repository;

import io.github.tallessantos.world_cup_api.business.core.domain.PlayerAppearanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerAppearanceEntity, Long> {
    List<PlayerAppearanceEntity> findByMatchId(String matchId);
    List<PlayerAppearanceEntity> findByMatchIdIn(Collection<String> matchIds);
    List<PlayerAppearanceEntity> findByTeamInitials(String teamInitials);
}
