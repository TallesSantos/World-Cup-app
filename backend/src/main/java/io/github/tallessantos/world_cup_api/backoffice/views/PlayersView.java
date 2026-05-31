package io.github.tallessantos.world_cup_api.backoffice.views;

import io.github.tallessantos.world_cup_api.backoffice.utils.AuditUtils;
import io.github.tallessantos.world_cup_api.backoffice.utils.ToastMessageUtil;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.core.domain.PlayerEntity;
import io.github.tallessantos.world_cup_api.core.service.PlayerService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("playersBean")
@ViewScoped
public class PlayersView implements Serializable {

    @Autowired
    private ToastMessageUtil toastMessageUtil;

    private PlayerService service;

    @Getter
    private List<PlayerEntity> players;

    @Getter
    @Setter
    private PlayerEntity selectedPlayer = new PlayerEntity();

    @Getter
    private Map<Long, Part> uploadedProfiles =
            new HashMap<>();

    @Getter
    @Setter
    private PlayerEntity pendingSave;

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
    private Boolean filterFinished;

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

    public PlayersView(PlayerService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        loadPage();
    }

    public void loadPage() {

        Page<PlayerEntity> page = service.findPageFiltered(
                currentPage,
                pageSize,
                filterPlayerName,
                filterTeam,
                filterPosition,
                filterFinished,
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

    public void selectPlayer(PlayerEntity player) {

        this.selectedPlayer = player;
    }

    public void clearSelection() {

        this.selectedPlayer =
                new PlayerEntity();
    }

    public void askSave(PlayerEntity player) {

        this.pendingSave = player;
    }

    public void confirmSave() {

        if (Boolean.TRUE.equals(
                pendingSave.getAudit().getFinished()
        )) {

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

    public void uploadProfileImage(
            PlayerEntity player
    ) {

        Part uploadedProfile =
                uploadedProfiles.get(
                        player.getId()
                );

        if (uploadedProfile == null) {

            return;
        }

        try {

            byte[] bytes =
                    uploadedProfile
                            .getInputStream()
                            .readAllBytes();

            MediaEntity media;

            if (player.getProfileImage() == null) {

                media =
                        service.saveProfileImage(
                                player,
                                bytes
                        );

            } else {

                media =
                        service.updateProfileImage(
                                player,
                                bytes
                        );

            }

            player.setProfileImage(
                    media
            );

            service.save(
                    player
            );

            uploadedProfiles.remove(
                    player.getId()
            );

            toastMessageUtil.addMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Profile image saved"
            );

            loadPage();

        } catch (Exception e) {

            throw new RuntimeException(
                    e
            );
        }

    }
}