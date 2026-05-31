package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Named
@SessionScope
@Getter
@Setter
public class SessionBean implements Serializable {

    private boolean authenticated;

    private String username;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;

        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx != null) {
            Object sessionObj = ctx
                    .getExternalContext()
                    .getSession(true); // cria sessão se não existir

            if (sessionObj instanceof jakarta.servlet.http.HttpSession httpSession) {
                if (authenticated) {
                    httpSession.setAttribute("authenticated", Boolean.TRUE);
                } else {
                    httpSession.removeAttribute("authenticated");
                }
            }
        }
    }

    public String logout() {

        authenticated = false;
        username = null;

        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx != null) {
            ctx.getExternalContext().invalidateSession();
        }

        return "/backoffice/login.xhtml?faces-redirect=true";
    }
}