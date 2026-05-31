package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.backoffice.security.SessionBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class IndexView {

    @Inject
    private SessionBean sessionBean;
}
