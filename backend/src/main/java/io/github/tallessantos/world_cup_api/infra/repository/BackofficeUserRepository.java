package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.BackofficeUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BackofficeUserRepository extends JpaRepository<BackofficeUserEntity, Long> {

    Optional<BackofficeUserEntity> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password")String password
    );
}
