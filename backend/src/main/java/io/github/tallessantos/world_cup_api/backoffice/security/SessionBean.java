package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Named
@SessionScope
@Getter
@Setter
@Slf4j
public class SessionBean implements Serializable {

    private boolean authenticated;

    private String username;

    private String baseUrl;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;

        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx != null) {
            Object sessionObj = ctx
                    .getExternalContext()
                    .getSession(true); // cria sessão se não existir

            HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

            baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            log.info("Logged user, and ctx of application: " + baseUrl);

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