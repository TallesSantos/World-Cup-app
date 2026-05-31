package io.github.tallessantos.world_cup_api.api.controller;

import io.github.tallessantos.world_cup_api.api.dto.PlayerDetailResponse;
import io.github.tallessantos.world_cup_api.api.service.PlayerApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.tallessantos.world_cup_api.api.config.ApiPathConfig.ENDPOINT_PREFIX;

@RestController
@RequestMapping(ENDPOINT_PREFIX + "/players")
public class PlayerController {

    private final PlayerApiService playerApiService;

    public PlayerController(PlayerApiService playerApiService) {
        this.playerApiService = playerApiService;
    }

    @GetMapping("/{id}")
    public PlayerDetailResponse getById(@PathVariable String id) {
        return PlayerDetailResponse.from(playerApiService.getPlayerById(id));
    }
}
