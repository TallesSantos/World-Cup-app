package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@SessionScoped
@Getter
@Setter
public class SessionBean implements Serializable {

    private boolean authenticated;

    private String username;

    public String logout() {

        authenticated=false;

        username=null;

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "/backoffice/login.xhtml?faces-redirect=true";
    }

}