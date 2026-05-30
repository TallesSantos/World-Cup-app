package io.github.tallessantos.world_cup_api.backoffice.beans.matches;

import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.core.service.MatchService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class MatchesBean implements Serializable {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    @Getter
    private List<MatchEntity> matches;

    @Getter
    @Setter
    private MatchEntity selectedMatch = new MatchEntity();

    @Getter
    @Setter
    private MatchEntity pendingSave;

    @Getter
    @Setter
    private String filterStage;

    @Getter
    @Setter
    private String filterCity;

    @Getter
    @Setter
    private String filterHomeTeam;

    @Getter
    @Setter
    private String filterAwayTeam;

    @Getter
    @Setter
    private String sortField = "kickoffAt";

    @Getter
    @Setter
    private String sortDirection = "asc";

    @Getter
    @Setter
    private int currentPage = 0;

    @Getter
    @Setter
    private int pageSize = 5;

    @Getter
    private long totalRecords;

    @Getter
    private int totalPages;


    @Getter
    private boolean hasNextPage;

    public String formatDate(LocalDateTime date) {

        if (date == null) {
            return "";
        }

        return date.format(DATE_FORMATTER);
    }

    private final MatchService service;

    public MatchesBean(MatchService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        loadPage();
    }

    public void loadPage() {

        Page<MatchEntity> page = service.findFiltered(
                filterStage,
                filterCity,
                filterHomeTeam,
                filterAwayTeam,
                currentPage,
                pageSize,
                sortField,
                sortDirection
        );

        matches = page.stream().toList();

        totalPages = page.getTotalPages();

        totalRecords = page.getTotalElements();

        if (matches == null) {
            matches = Collections.emptyList();
        }

        hasNextPage = matches.size() == pageSize;
    }

    public void filter() {
        currentPage = 0;
        loadPage();
    }

    public void clearFilters() {

        filterStage = null;
        filterCity = null;
        filterHomeTeam = null;
        filterAwayTeam = null;

        sortField = "kickoffAt";
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
        }

        loadPage();
    }

    public void changePageSize() {
        currentPage = 0;
        loadPage();
    }

    public void selectMatch(MatchEntity match) {
        this.selectedMatch = match;
    }

    public void clearSelection() {
        this.selectedMatch = new MatchEntity();
    }

    public void askSave(MatchEntity match) {

        this.pendingSave = match;
    }

    public void confirmSave() {

        service.save(pendingSave);
        pendingSave = null;
        loadPage();
    }

    public void cancelSave() {
        pendingSave = null;
        loadPage();
    }
}