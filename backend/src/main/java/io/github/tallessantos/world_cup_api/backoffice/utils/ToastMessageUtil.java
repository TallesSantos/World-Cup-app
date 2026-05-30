package io.github.tallessantos.world_cup_api.backoffice.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.stereotype.Component;

@Component
public class ToastMessageUtil {

    public void addMessage(FacesMessage.Severity severity, String message) {
        FacesMessage facesMessage = new FacesMessage(severity,
                message,
                null
        );

        if (severity == FacesMessage.SEVERITY_ERROR) {
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

        } else {

            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);

        }

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
