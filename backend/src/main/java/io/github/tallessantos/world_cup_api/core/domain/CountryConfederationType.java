package io.github.tallessantos.world_cup_api.core.domain;

public enum CountryConfederationType {
    CONMEBOL("South America"),
    UEFA("Europe"),
    CONCACAF("North/Central America & Caribbean"),
    CAF("Africa"),
    AFC("Asia"),
    OFC("Oceania");

    private final String displayName;

    CountryConfederationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
