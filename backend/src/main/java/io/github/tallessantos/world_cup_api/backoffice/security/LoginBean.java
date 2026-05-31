package io.github.tallessantos.world_cup_api.backoffice.security;

import io.github.tallessantos.world_cup_api.backoffice.services.AuthService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Named
@ViewScoped
@Getter
@Setter
public class LoginBean {

    private String username;

    private String password;

    @Autowired
    private AuthService authService;

    @Inject
    private SessionBean sessionBean;

    public String redirectIfAuthenticated() {
        if (sessionBean.isAuthenticated()) {
            return "/backoffice/index.xhtml?faces-redirect=true";
        }
        return null;
    }

    public String login() {

        if (authService.login(username, password)) {

            sessionBean.setAuthenticated(true);
            sessionBean.setUsername(username);

            return "/backoffice/index.xhtml?faces-redirect=true";
        }

        FacesContext.getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Usuário ou senha inválidos.",
                                null
                        )
                );

        // Limpa a senha após falha — não deixa no ViewState
        password = null;

        return null;
    }
}