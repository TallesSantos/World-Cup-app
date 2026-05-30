package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.backoffice.utils.AuditUtils;
import io.github.tallessantos.world_cup_api.backoffice.utils.ToastMessageUtil;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.core.service.WorldCupService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("worldCupBean")
@ViewScoped
public class WorldCupView implements Serializable {

    @Autowired
    private ToastMessageUtil toastMessageUtil;

    @Inject
    private WorldCupService service;

    @Getter
    @Setter
    private int pageSize = 5;

    @Getter
    private List<WorldCupEntity> worldCups;

    @Getter
    @Setter
    private WorldCupEntity selectedWorldCup = new WorldCupEntity();

    @Getter
    private int currentPage = 0;

    @Getter
    private boolean hasNextPage;

    /*
     * FILTERS
     */
    @Getter
    @Setter
    private String filterTitle;

    @Getter
    @Setter
    private String filterStatus;

    @Getter
    @Setter
    private String filterWinner;

    @Getter
    @Setter
    private Boolean filterFinished;

    /*
     * SORT
     */
    @Getter
    @Setter
    private String sortField = "title";

    @Getter
    @Setter
    private String sortDirection = "asc";

    @Getter
    @Setter
    private WorldCupEntity pendingSave;

    @Getter
    private long totalRecords;

    @Getter
    private int totalPages;

    @Getter
    private Map<String, Part> uploadedBanners = new HashMap<>();

    @PostConstruct
    public void init() {
        loadPage();
    }

    public void loadPage() {

        Page<WorldCupEntity> page = service.findPageFiltered(
                currentPage,
                pageSize,
                filterTitle,
                filterStatus,
                filterWinner,
                filterFinished,
                sortField,
                sortDirection
        );

        worldCups = page.stream().toList();

        totalRecords = page.getTotalElements();

        totalPages = page.getTotalPages();

        if (worldCups == null) {
            worldCups = Collections.emptyList();
        }

        hasNextPage = worldCups.size() == pageSize;
    }

    public void filter() {
        currentPage = 0;
        loadPage();
    }

    public void changePageSize() {
        currentPage = 0;
        loadPage();
    }

    public void clearFilters() {

        filterTitle = null;
        filterStatus = null;
        filterWinner = null;
        filterFinished = null;

        sortField = "title";
        sortDirection = "asc";

        currentPage = 0;

        loadPage();
    }

    public void askSave(WorldCupEntity worldCup) {

        this.pendingSave = worldCup;
    }

    public void confirmSave() {

        if (Boolean.TRUE.equals(pendingSave.getAudit().getFinished())) {
            AuditUtils.markFinished(
                    pendingSave,
                    pendingSave.getAudit().getFinished(),
                    "BACKOFFICE_USER"
            );
        }


        service.save(pendingSave);
        pendingSave = null;
        toastMessageUtil.addMessage(
                FacesMessage.SEVERITY_INFO,
                "Successfully saved registry data"
        );
        loadPage();
    }

    public void cancelSave() {
        pendingSave = null;
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

    public void selectWorldCup(WorldCupEntity worldCup) {
        this.selectedWorldCup = worldCup;
    }

    public void clearSelection() {
        this.selectedWorldCup = new WorldCupEntity();
    }

    public void uploadBanner(WorldCupEntity worldCup) {
        //TODO fix delay of image wen replace banner

        Part uploadedBanner =
                uploadedBanners.get(worldCup.getId());

        if (uploadedBanner == null) {
            return;
        }

        try {

            byte[] bytes = uploadedBanner
                    .getInputStream()
                    .readAllBytes();

            MediaEntity media;

            if (worldCup.getWorldCupBannerMedia() == null) {
                media =
                        service.saveBannerImage(
                                worldCup,
                                bytes
                        );
                service.save(worldCup);
                toastMessageUtil.addMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Successfully saved banner image"
                );
            } else {
                media =
                        service.updateBannerImage(
                                worldCup,
                                bytes
                        );
                service.save(worldCup);
                toastMessageUtil.addMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Successfully changed banner image"
                );
            }

            worldCup.setWorldCupBannerMedia(media);

            service.save(worldCup);

            uploadedBanners.remove(worldCup.getId());

            loadPage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}