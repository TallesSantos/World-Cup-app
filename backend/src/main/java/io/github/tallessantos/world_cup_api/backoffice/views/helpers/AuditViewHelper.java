package io.github.tallessantos.world_cup_api.backoffice.views.helpers;

import io.github.tallessantos.world_cup_api.core.listeners.Auditable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class AuditViewHelper {

    public String resolveRowClass(Auditable entity){

        if(entity == null){
            return "";
        }

        return Boolean.TRUE.equals(
                entity.getAudit().getFinished()
        )
                ? "finished-row"
                : "";
    }

}