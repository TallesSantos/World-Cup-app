package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, String> {
    List<MatchEntity> findByWorldCupId(String worldCupId);

    List<MatchEntity> findByHomeTeamInitialsOrAwayTeamInitials(String homeTeamInitials, String awayTeamInitials);

    @Query("""
            SELECT m
            FROM MatchEntity m
            WHERE
            (:stage IS NULL OR LOWER(CAST(m.stage AS string)) LIKE LOWER(CONCAT('%', CAST(:stage AS string), '%')))
            AND (:city IS NULL OR LOWER(CAST(m.city AS string)) LIKE LOWER(CONCAT('%', CAST(:city AS string), '%')))
            AND (:homeTeamName IS NULL OR LOWER(CAST(m.homeTeamName AS string)) LIKE LOWER(CONCAT('%', CAST(:homeTeamName AS string), '%')))
            AND (:awayTeamName IS NULL OR LOWER(CAST(m.awayTeamName AS string)) LIKE LOWER(CONCAT('%', CAST(:awayTeamName AS string), '%')))
            AND (:finished IS NULL OR m.audit.finished = :finished)
            """)
    Page<MatchEntity> findFiltered(
            @Param("stage") String stage,
            @Param("city") String city,
            @Param("homeTeamName") String homeTeamName,
            @Param("awayTeamName") String awayTeamName,
            @Param("finished") Boolean finished,
            Pageable pageable
    );

}
