package io.github.tallessantos.world_cup_api.backoffice.services;

import io.github.tallessantos.world_cup_api.core.config.AppCommonConfigurationVariables;
import io.github.tallessantos.world_cup_api.core.domain.MediaContentType;
import io.github.tallessantos.world_cup_api.core.domain.MediaEntity;
import io.github.tallessantos.world_cup_api.core.domain.MediaPlatform;
import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.core.exception.BusinessException;
import io.github.tallessantos.world_cup_api.core.service.FileSystemStorageService;
import io.github.tallessantos.world_cup_api.infra.repository.MediaRespository;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class WorldCupBackofficeService {

    @Autowired
    private WorldCupRepository worldCupRepository;

    @Autowired
    private AppCommonConfigurationVariables appCommonConfigurationVariables;

    @Autowired
    private MediaRespository mediaRespository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    public void save(WorldCupEntity worldCup) {
        worldCupRepository.save(worldCup);
    }

    public Page<WorldCupEntity> findPageFiltered(
            int page,
            int size,
            String title,
            String status,
            String winner,
            Boolean finished,
            String sortField,
            String sortDirection
    ) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return worldCupRepository.findFiltered(
                title,
                status,
                winner,
                finished,
                pageable
        );
    }

    public MediaEntity saveBannerImage(WorldCupEntity entity, byte[] image) {

        //TODO add audit columns

        String pathToFile = "/world-cups/banners/" + entity.getId() + ".jpeg";

        String savedPath = fileSystemStorageService
                .saveImageInStoragePassingPathAndByteArray(appCommonConfigurationVariables.getStoragePath() + pathToFile, image);

        MediaEntity mediaEntity = new MediaEntity();

        mediaEntity.setMediaContentType(MediaContentType.WORLD_CUP_BANNER);

        mediaEntity.setMediaPlatform(MediaPlatform.RESOURCE_SERVER);

        mediaEntity.setFullStoragePath(savedPath);

        mediaEntity.setStoragePath(appCommonConfigurationVariables.getStoragePath());

        mediaEntity.setResourcePath(appCommonConfigurationVariables.getResourcePath().replace("/**", ""));

        mediaEntity.setFullResourcePath(appCommonConfigurationVariables.getResourcePath().replace("/**", "") + pathToFile);

        mediaRespository.saveAndFlush(mediaEntity);

        entity.setWorldCupBannerMedia(mediaEntity);

        worldCupRepository.save(entity);

        return mediaEntity;
    }

    public MediaEntity updateBannerImage(WorldCupEntity entity, byte[] image){

        //TODO add audit columns

        String fullStoragePath =  entity.getWorldCupBannerMedia().getFullStoragePath();
        if(appCommonConfigurationVariables.getStoragePath() == null) {
            throw new BusinessException("Error try update image");
        }
        fileSystemStorageService.replaceImageInStoragePassingPathAndByteArray(fullStoragePath, image);
        return entity.getWorldCupBannerMedia();
    }
}
