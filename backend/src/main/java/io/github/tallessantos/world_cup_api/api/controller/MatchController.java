package io.github.tallessantos.world_cup_api.api.controller;

import io.github.tallessantos.world_cup_api.api.dto.MatchDetailResponse;
import io.github.tallessantos.world_cup_api.core.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.tallessantos.world_cup_api.api.config.ApiPathConfig.ENDPOINT_PREFIX;

@RestController
@RequestMapping(ENDPOINT_PREFIX + "/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public MatchDetailResponse getById(@PathVariable String id) {
        return MatchDetailResponse.from(matchService.getMatchById(id));
    }
}
