package io.github.tallessantos.world_cup_api.controller;

import io.github.tallessantos.world_cup_api.dto.PlayerDetailResponse;
import io.github.tallessantos.world_cup_api.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public PlayerDetailResponse getById(@PathVariable String id) {
        return PlayerDetailResponse.from(playerService.getPlayerById(id));
    }
}
