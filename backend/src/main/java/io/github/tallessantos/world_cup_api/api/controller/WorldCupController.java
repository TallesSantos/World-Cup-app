package io.github.tallessantos.world_cup_api.api.controller;

import io.github.tallessantos.world_cup_api.api.dto.WorldCupSummaryResponse;
import io.github.tallessantos.world_cup_api.api.dto.WorldCupDetailResponse;
import io.github.tallessantos.world_cup_api.api.service.WorldCupApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.github.tallessantos.world_cup_api.api.config.ApiPathConfig.ENDPOINT_PREFIX;

@RestController
@RequestMapping(ENDPOINT_PREFIX + "/world-cups")
public class WorldCupController {

    private final WorldCupApiService worldCupApiService;

    public WorldCupController(WorldCupApiService worldCupApiService) {
        this.worldCupApiService = worldCupApiService;
    }

    @GetMapping
    public List<WorldCupSummaryResponse> list() {
        return worldCupApiService.listWorldCups().stream()
                .map(WorldCupSummaryResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public WorldCupDetailResponse getById(@PathVariable String id) {
        return WorldCupDetailResponse.from(worldCupApiService.getWorldCupById(id));
    }
}
