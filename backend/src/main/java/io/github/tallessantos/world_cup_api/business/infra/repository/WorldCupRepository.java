package io.github.tallessantos.world_cup_api.business.infra.repository;

import io.github.tallessantos.world_cup_api.business.core.domain.WorldCupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldCupRepository extends JpaRepository<WorldCupEntity, String> {
}
