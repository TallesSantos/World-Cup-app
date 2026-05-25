package io.github.tallessantos.world_cup_api.repository;

import io.github.tallessantos.world_cup_api.domain.WorldCupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldCupRepository extends JpaRepository<WorldCupEntity, String> {
}
