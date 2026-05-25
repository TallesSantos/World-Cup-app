package io.github.tallessantos.world_cup_api.controller;

import io.github.tallessantos.world_cup_api.dto.MatchDetailResponse;
import io.github.tallessantos.world_cup_api.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
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
