package io.github.tallessantos.world_cup_api.backoffice.views.helpers;

import io.github.tallessantos.world_cup_api.backoffice.utils.ToastMessageUtil;
import io.github.tallessantos.world_cup_api.core.domain.metadata.GoalMetadataEntity;
import io.github.tallessantos.world_cup_api.core.domain.MatchEntity;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.core.domain.MediaPlatform;
import io.github.tallessantos.world_cup_api.core.service.MatchService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Named("matchBean")
@ViewScoped
@Getter
@Setter
public class AddVideoView implements Serializable {

    @Autowired
    private ToastMessageUtil toastMessageUtil;

    private MatchEntity selectedMatch;

    private String newVideoTitle;

    private String newYoutubeUrl;

    private String goalPlayer;

    private String goalNationalTeam;

    private String goalAgainstTeam;

    private String goalScoredByTeam;

    private final MatchService service;

    public AddVideoView(MatchService service) {
        this.service = service;
    }

    public void openManageVideos(MatchEntity match) {
        selectedMatch = match;
    }

    public void addVideo() {

        if (selectedMatch == null) {
            return;
        }

        boolean invalidData = isBlank(newVideoTitle)
                || isBlank(newYoutubeUrl)
                || isBlank(goalPlayer)
                || isBlank(goalScoredByTeam);

        if (invalidData) {

            toastMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Fill all video fields before adding.");

            return;
        }

        MediaEntity media = new MediaEntity();

        media.setTitle(newVideoTitle);

        media.setYoutubeVideoId(extractYoutubeId(newYoutubeUrl));

        media.setMediaPlatform(MediaPlatform.YOUTUBE);

        GoalMetadataEntity metadata = new GoalMetadataEntity();

        metadata.setPlayerName(goalPlayer);

        metadata.setPlayerNationalTeam(goalNationalTeam);

        metadata.setAgainstNationalTeam(goalAgainstTeam);

        media.setGoalMetadata(metadata);

        selectedMatch.getListOfVideos().add(media);

        service.save(selectedMatch);

        toastMessageUtil.addMessage(
                FacesMessage.SEVERITY_INFO,
                "Video added successfully."
        );

        clearFields();
    }

    private boolean isBlank(String value) {

        return value == null || value.isBlank();
    }

    private void clearFields() {

        newVideoTitle = null;
        newYoutubeUrl = null;

        goalPlayer = null;
        goalNationalTeam = null;
        goalAgainstTeam = null;
    }

    public void deleteVideo(MatchEntity match, MediaEntity video) {

        //TODO Adjust the video exclusion, as it is currently not robust.

        match.getListOfVideos().removeIf(v -> {

            if (v == video) {
                return true;
            }

            if (v.getId() != null && video.getId() != null) {
                return v
                        .getId()
                        .equals(video.getId());
            }

            return false;
        });

        service.save(match);
    }

    private String extractYoutubeId(String url) {

        if (url.contains("watch?v=")) {
            return url.split("watch\\?v=")[1];
        }

        if (url.contains("youtu.be/")) {
            return url.substring(
                    url.lastIndexOf("/") + 1
            );
        }

        return url;
    }

    public void closeManageVideos() {

        selectedMatch = null;

        clearForm();
    }

    private void clearForm() {

        newVideoTitle = null;

        newYoutubeUrl = null;

        goalPlayer = null;

        goalNationalTeam = null;

        goalAgainstTeam = null;
    }


}