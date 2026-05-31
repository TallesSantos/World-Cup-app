package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.PlayerAppearanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PlayerAppearanceRepository extends JpaRepository<PlayerAppearanceEntity, Long> {
    List<PlayerAppearanceEntity> findByMatchId(String matchId);

    List<PlayerAppearanceEntity> findByMatchIdIn(Collection<String> matchIds);

    List<PlayerAppearanceEntity> findByTeamInitials(String teamInitials);

    @Query("""
            SELECT p
            FROM PlayerAppearanceEntity p
            WHERE
                (:playerName IS NULL OR LOWER(p.playerName) LIKE LOWER(CONCAT('%', :playerName, '%')))
            AND (:teamInitials IS NULL OR LOWER(p.teamInitials) LIKE LOWER(CONCAT('%', :teamInitials, '%')))
            AND (:position IS NULL OR LOWER(p.position) LIKE LOWER(CONCAT('%', :position, '%')))
            """)
    Page<PlayerAppearanceEntity> findFiltered(
            @Param("playerName") String playerName,
            @Param("teamInitials") String teamInitials,
            @Param("position") String position,
            Pageable pageable
    );

    @Query("""
                SELECT p.shirtNumber
                FROM PlayerAppearanceEntity p
                WHERE p.teamInitials = :teamInitials
                  AND p.playerName = :playerName
                GROUP BY p.shirtNumber
                ORDER BY COUNT(p.shirtNumber) DESC
            """)
    List<String> findMostUsedShirtNumbers(
            @Param("teamInitials") String teamInitials,
            @Param("playerName") String playerName
    );
}
