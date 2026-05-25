package io.github.tallessantos.world_cup_api.repository;

import io.github.tallessantos.world_cup_api.domain.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, String> {
    List<MatchEntity> findByWorldCupId(String worldCupId);
    List<MatchEntity> findByHomeTeamInitialsOrAwayTeamInitials(String homeTeamInitials, String awayTeamInitials);
}
