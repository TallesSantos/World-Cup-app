package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, String> {
    List<MatchEntity> findByWorldCupId(String worldCupId);
    List<MatchEntity> findByHomeTeamInitialsOrAwayTeamInitials(String homeTeamInitials, String awayTeamInitials);

    @Query("""
    SELECT m
    FROM MatchEntity m
    WHERE
        (:stage IS NULL OR LOWER(m.stage) LIKE LOWER(CONCAT('%', :stage, '%')))
    AND (:city IS NULL OR LOWER(m.city) LIKE LOWER(CONCAT('%', :city, '%')))
    AND (:homeTeamName IS NULL OR LOWER(m.homeTeamName) LIKE LOWER(CONCAT('%', :homeTeamName, '%')))
    AND (:awayTeamName IS NULL OR LOWER(m.awayTeamName) LIKE LOWER(CONCAT('%', :awayTeamName, '%')))
    """)
    Page<MatchEntity> findFiltered(
            @Param("stage") String stage,
            @Param("city") String city,
            @Param("homeTeamName") String homeTeamName,
            @Param("awayTeamName") String awayTeamName,
            Pageable pageable
    );

}
