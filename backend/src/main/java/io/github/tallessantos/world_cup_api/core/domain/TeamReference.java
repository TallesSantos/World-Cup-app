package io.github.tallessantos.world_cup_api.core.domain;

public record TeamReference(
        String id,
        String name,
        String fifaCode,
        String flagUrl,
        Integer points,
        Integer position,
        Boolean qualified,
        String stageResult
) {
}
