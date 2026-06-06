package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.CountryConfederationType;
import io.github.tallessantos.world_cup_api.core.domain.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository
        extends JpaRepository<CountryEntity, Long> {

    List<CountryEntity> findAllByInitials(
            @Param("initials")
            String initials
    );

    @Query("""
            SELECT c
            FROM CountryEntity c
            WHERE
            (:filterCountryName IS NULL OR LOWER(CAST(c.name AS string))
                LIKE LOWER(CONCAT('%', CAST(:filterCountryName AS string), '%')))
            AND
            (:filterFifaCode IS NULL OR LOWER(CAST(c.fifaCode AS string))
                LIKE LOWER(CONCAT('%', CAST(:filterFifaCode AS string), '%')))
            AND
            (:filterConfederation IS NULL OR c.confederation = :filterConfederation)
            AND
            (:filterFinished IS NULL OR c.audit.finished = :filterFinished)
            """)
    Page<CountryEntity> findFiltered(
            @Param("filterCountryName")
            String filterCountryName,

            @Param("filterFifaCode")
            String filterFifaCode,

            @Param("filterConfederation")
            CountryConfederationType filterConfederation,

            @Param("filterFinished")
            Boolean filterFinished,

            Pageable pageable
    );

    Optional<CountryEntity> findByInitials(@Param("initials") String initials);
}