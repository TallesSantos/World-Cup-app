package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query("""
            SELECT p
            FROM PlayerEntity p
            WHERE
                (:playerName IS NULL OR LOWER(p.playerName) LIKE LOWER(CONCAT('%', :playerName, '%')))
            AND (:teamInitials IS NULL OR LOWER(p.teamInitials) LIKE LOWER(CONCAT('%', :teamInitials, '%')))
            AND (:position IS NULL OR LOWER(p.position) LIKE LOWER(CONCAT('%', :position, '%')))
            AND (:finished IS NULL OR p.audit.finished = :finished)
            """)
    Page<PlayerEntity> findFiltered(@Param("playerName") String playerName,
                                    @Param("teamInitials") String teamInitials,
                                    @Param("position") String position,
                                    @Param("finished") Boolean finished,
                                    Pageable pageable);

    Optional<PlayerEntity> findByPlayerName(String playerName);
}
