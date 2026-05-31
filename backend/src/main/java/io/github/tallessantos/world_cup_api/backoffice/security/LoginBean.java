package io.github.tallessantos.world_cup_api.backoffice.security;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class LoginBean {

    private String username;

    private String password;

    @Inject
    private SessionBean sessionBean;

    public String login(){

        String expectedUser="admin";

        String expectedPassword="123";

        if(
                expectedUser.equals(username)
                        &&
                        expectedPassword.equals(password)
        ){

            sessionBean.setAuthenticated(true);

            sessionBean.setUsername(username);

            return "/backoffice/index.xhtml?faces-redirect=true";

        }

        FacesContext.getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Invalid credentials",
                                null
                        )
                );

        return null;

    }

}