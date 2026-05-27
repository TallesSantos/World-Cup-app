package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorldCupRepository extends JpaRepository<WorldCupEntity, String> {

    @Query("""
            SELECT w
            FROM WorldCupEntity w
            WHERE
                (:title IS NULL OR LOWER(w.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:status IS NULL OR LOWER(w.status) LIKE LOWER(CONCAT('%', :status, '%')))
            AND (:winner IS NULL OR LOWER(w.winner) LIKE LOWER(CONCAT('%', :winner, '%')))
            """)
    Page<WorldCupEntity> findFiltered(
            @Param("title") String title,
            @Param("status") String status,
            @Param("winner") String winner,
            Pageable pageable
    );
}
