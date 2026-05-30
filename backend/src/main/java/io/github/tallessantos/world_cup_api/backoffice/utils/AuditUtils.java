package io.github.tallessantos.world_cup_api.backoffice.utils;

import io.github.tallessantos.world_cup_api.core.listeners.Auditable;

import java.time.LocalDateTime;

public final class AuditUtils {

    private AuditUtils(){}

    public static void markFinished(
            Auditable entity,
            boolean finished,
            String updatedBy
    ){

        entity.getAudit().setFinished(
                finished
        );

        entity.getAudit().setUpdatedBy(
                updatedBy
        );

        entity.getAudit().setUpdatedAt(
                LocalDateTime.now()
        );

        if(finished){

            entity.getAudit().setFinishedAt(
                    LocalDateTime.now()
            );

        }else{

            entity.getAudit().setFinishedAt(
                    null
            );

        }

    }

}