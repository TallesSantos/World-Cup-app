package io.github.tallessantos.world_cup_api.core.listeners;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;

public interface Auditable {

    AuditMetadataEntity getAudit();

}