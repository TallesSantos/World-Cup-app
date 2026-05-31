package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.backoffice.utils.AuditUtils;
import io.github.tallessantos.world_cup_api.backoffice.utils.ToastMessageUtil;
import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.backoffice.services.MatchBackofficeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Named("matchesBean")
@ViewScoped
public class MatchesView implements Serializable {

    @Autowired
    private ToastMessageUtil toastMessageUtil;

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
    private Boolean filterFinished;

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

    private final MatchBackofficeService service;

    public MatchesView(MatchBackofficeService service) {
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
                filterFinished,
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
        filterFinished = null;

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

        if(Boolean.TRUE.equals(
                pendingSave.getAudit().getFinished()
        )){

            AuditUtils.markFinished(
                    pendingSave,
                    true,
                    "BACKOFFICE_USER"
            );

        }

        service.save(pendingSave);

        pendingSave = null;

        loadPage();
        toastMessageUtil.addMessage(
                FacesMessage.SEVERITY_INFO,
                "Successfully saved registry data."
        );
    }

    public void cancelSave() {
        pendingSave = null;
        loadPage();
    }
}