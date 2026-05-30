package io.github.tallessantos.world_cup_api.core.listeners;

import io.github.tallessantos.world_cup_api.core.domain.metadata.AuditMetadataEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void onCreate(Object entity) {

        if (!(entity instanceof Auditable auditable)) {
            return;
        }

        AuditMetadataEntity audit = auditable.getAudit();

        LocalDateTime now = LocalDateTime.now();

        audit.setCreatedAt(now);

        audit.setUpdatedAt(now);

        if (audit.getFinished() == null) {
            audit.setFinished(false);
        }
    }

    @PreUpdate
    public void onUpdate(Object entity) {

        if (!(entity instanceof Auditable auditable)) {
            return;
        }

        auditable.getAudit().setUpdatedAt(LocalDateTime.now());

    }

}