package io.github.tallessantos.world_cup_api.controller;

import io.github.tallessantos.world_cup_api.dto.WorldCupSummaryResponse;
import io.github.tallessantos.world_cup_api.dto.WorldCupDetailResponse;
import io.github.tallessantos.world_cup_api.service.WorldCupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/world-cups")
public class WorldCupController {

    private final WorldCupService worldCupService;

    public WorldCupController(WorldCupService worldCupService) {
        this.worldCupService = worldCupService;
    }

    @GetMapping
    public List<WorldCupSummaryResponse> list() {
        return worldCupService.listWorldCups().stream()
                .map(WorldCupSummaryResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public WorldCupDetailResponse getById(@PathVariable String id) {
        return WorldCupDetailResponse.from(worldCupService.getWorldCupById(id));
    }
}
