package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorldCupRepository extends JpaRepository<WorldCupEntity, Long> {

    @Query("""
            SELECT w
            FROM WorldCupEntity w
            WHERE
                (:title IS NULL OR LOWER(w.title) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%')))
            AND (:status IS NULL OR LOWER(w.status) LIKE LOWER(CONCAT('%', CAST(:status AS string), '%')))
            AND (:winner IS NULL OR LOWER(w.winner) LIKE LOWER(CONCAT('%', CAST(:winner AS string), '%')))
            AND (:finished IS NULL OR w.audit.finished = :finished)
            """)
    Page<WorldCupEntity> findFiltered(
            @Param("title") String title,
            @Param("status") String status,
            @Param("winner") String winner,
            @Param("finished") Boolean finished,
            Pageable pageable
    );

    Optional<WorldCupEntity> findByReference(@Param("reference") String reference);
}
