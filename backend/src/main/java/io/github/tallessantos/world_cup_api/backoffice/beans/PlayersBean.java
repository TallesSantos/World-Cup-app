package io.github.tallessantos.world_cup_api.backoffice.beans;

import io.github.tallessantos.world_cup_api.core.domain.PlayerAppearanceEntity;
import io.github.tallessantos.world_cup_api.core.service.PlayerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class PlayersBean implements Serializable {

    private PlayerService service;

    @Getter
    private List<PlayerAppearanceEntity> players;

    @Getter
    @Setter
    private PlayerAppearanceEntity selectedPlayer =
            new PlayerAppearanceEntity();

    @Getter
    @Setter
    private PlayerAppearanceEntity pendingSave;

    @Getter
    @Setter
    private String filterPlayerName;

    @Getter
    @Setter
    private String filterTeam;

    @Getter
    @Setter
    private String filterPosition;

    @Getter
    @Setter
    private String sortField = "playerName";

    @Getter
    @Setter
    private String sortDirection = "asc";

    @Getter
    @Setter
    private int pageSize = 5;

    @Getter
    private int currentPage = 0;

    @Getter
    private boolean hasNextPage;

    @Getter
    private long totalRecords;

    @Getter
    private int totalPages;

    public PlayersBean(PlayerService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        loadPage();
    }

    public void loadPage() {

        Page<PlayerAppearanceEntity> page = service.findPageFiltered(
                currentPage,
                pageSize,
                filterPlayerName,
                filterTeam,
                filterPosition,
                sortField,
                sortDirection
        );

        players = page.stream().toList();

        totalRecords = page.getTotalElements();

        totalPages = page.getTotalPages();

        if (players == null) {
            players = Collections.emptyList();
        }

        hasNextPage = players.size() == pageSize;
    }
    public void filter() {

        currentPage = 0;

        loadPage();
    }

    public void clearFilters() {

        filterPlayerName = null;
        filterTeam = null;
        filterPosition = null;

        sortField = "playerName";
        sortDirection = "asc";

        currentPage = 0;

        loadPage();
    }

    public void nextPage() {

        currentPage++;

        loadPage();
    }

    public void previousPage() {

        if (currentPage > 0) {

            currentPage--;

            loadPage();
        }
    }

    public void changePageSize() {

        currentPage = 0;

        loadPage();
    }

    public void selectPlayer(PlayerAppearanceEntity player) {

        this.selectedPlayer = player;
    }

    public void clearSelection() {

        this.selectedPlayer =
                new PlayerAppearanceEntity();
    }

    public String askSave(PlayerAppearanceEntity player) {

        this.pendingSave = player;

        return null;
    }

    public String confirmSave() {

        System.out.println("CONFIRM SAVE");

        service.save(pendingSave);

        pendingSave = null;

        loadPage();

        return null;
    }

    public String cancelSave() {

        System.out.println("CANCEL SAVE");

        pendingSave = null;

        return null;
    }
}