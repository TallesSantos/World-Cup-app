package io.github.tallessantos.world_cup_api.infra.repository;

import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRespository extends JpaRepository<MediaEntity, Long> {
}
