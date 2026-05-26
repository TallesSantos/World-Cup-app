package io.github.tallessantos.world_cup_api.admin_manager.beans;

import io.github.tallessantos.world_cup_api.business.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.business.core.domain.PlayerReference;
import io.github.tallessantos.world_cup_api.business.core.service.PlayerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PlayerBean implements Serializable {

    @Getter
    private List<PlayerAppearanceEntity> players;

    @Inject
    private PlayerService service;

    @PostConstruct
    public void init() {
        players = service.findAll();
    }

}