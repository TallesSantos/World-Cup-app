package io.github.tallessantos.world_cup_api.backoffice.services;

import io.github.tallessantos.world_cup_api.core.config.AppCommonConfigurationVariables;
import io.github.tallessantos.world_cup_api.core.domain.MediaContentType;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.core.domain.MediaPlatform;
import io.github.tallessantos.world_cup_api.core.domain.PlayerEntity;
import io.github.tallessantos.world_cup_api.core.exception.BusinessException;
import io.github.tallessantos.world_cup_api.core.service.FileSystemStorageService;
import io.github.tallessantos.world_cup_api.infra.repository.MediaRespository;
import io.github.tallessantos.world_cup_api.infra.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlayerBackofficeService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private AppCommonConfigurationVariables appCommonConfigurationVariables;

    @Autowired
    private MediaRespository mediaRespository;

    public Page<PlayerEntity> findPageFiltered(
            int page,
            int size,
            String playerName,
            String team,
            String position,
            Boolean finished,
            String sortField,
            String sortDirection
    ) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return playerRepository.findFiltered(
                playerName,
                team,
                position,
                finished,
                pageable

        );
    }

    public void save(PlayerEntity pendingSave) {
        playerRepository.save(pendingSave);
    }

    public MediaEntity saveProfileImage(PlayerEntity entity, byte[] image) {

        //TODO add audit columns

        String pathToFile = "/players/profile-image/" + entity.getId() + ".jpeg";

        String savedPath = fileSystemStorageService
                .saveImageInStoragePassingPathAndByteArray(appCommonConfigurationVariables.getStoragePath() + pathToFile, image);

        MediaEntity mediaEntity = new MediaEntity();

        mediaEntity.setMediaContentType(MediaContentType.PLAYER_FACE);

        mediaEntity.setMediaPlatform(MediaPlatform.RESOURCE_SERVER);

        mediaEntity.setFullStoragePath(savedPath);

        mediaEntity.setStoragePath(appCommonConfigurationVariables.getStoragePath());

        mediaEntity.setResourcePath(appCommonConfigurationVariables.getResourcePath().replace("/**", ""));

        mediaEntity.setFullResourcePath(appCommonConfigurationVariables.getResourcePath().replace("/**", "") + pathToFile);

        mediaRespository.saveAndFlush(mediaEntity);

        entity.setProfileImage(mediaEntity);

        playerRepository.save(entity);

        return mediaEntity;
    }

    public MediaEntity updateProfileImage(PlayerEntity entity, byte[] image) {
        //TODO add audit columns

        String fullStoragePath =  entity.getProfileImage().getFullStoragePath();
        if(appCommonConfigurationVariables.getStoragePath() == null) {
            throw new BusinessException("Error try update image");
        }
        fileSystemStorageService.replaceImageInStoragePassingPathAndByteArray(fullStoragePath, image);
        return entity.getProfileImage();
    }

}
