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

                    (
                        COALESCE(:playerName, '') = ''
                        OR
                        LOWER(CAST(p.playerName AS string))
                        LIKE CONCAT(
                            '%',
                            LOWER(CAST(:playerName AS string)),
                            '%'
                        )
                    )

                AND

                    (
                        COALESCE(:teamInitials, '') = ''
                        OR
                        LOWER(CAST(p.teamInitials AS string))
                        LIKE CONCAT(
                            '%',
                            LOWER(CAST(:teamInitials AS string)),
                            '%'
                        )
                    )

                AND

                    (
                        COALESCE(:position, '') = ''
                        OR
                        LOWER(CAST(p.position AS string))
                        LIKE CONCAT(
                            '%',
                            LOWER(CAST(:position AS string)),
                            '%'
                        )
                    )

                AND

                    (
                        :finished IS NULL
                        OR
                        p.audit.finished = :finished
                    )

            """)
    Page<PlayerEntity> findFiltered(
            @Param("playerName") String playerName,
            @Param("teamInitials") String teamInitials,
            @Param("position") String position,
            @Param("finished") Boolean finished,
            Pageable pageable
    );

    Optional<PlayerEntity> findByPlayerName(String playerName);
}
