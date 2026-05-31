package io.github.tallessantos.world_cup_api.backoffice.services;

import io.github.tallessantos.world_cup_api.core.config.AppCommonConfigurationVariables;
import io.github.tallessantos.world_cup_api.core.domain.*;
import io.github.tallessantos.world_cup_api.core.exception.BusinessException;
import io.github.tallessantos.world_cup_api.core.service.FileSystemStorageService;
import io.github.tallessantos.world_cup_api.infra.repository.CountryRepository;
import io.github.tallessantos.world_cup_api.infra.repository.MediaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CountryBackofficeService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AppCommonConfigurationVariables appCommonConfigurationVariables;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private MediaRespository mediaRespository;

    public Page<CountryEntity> findPageFiltered(int currentPage, int pageSize, String filterCountryName, String filterFifaCode, CountryConfederationType filterCountryConfederationType, Boolean filterFinished, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

        return countryRepository.findFiltered(
                filterCountryName != null && filterCountryName.isEmpty() ? null: filterCountryName,
                filterFifaCode != null && filterFifaCode.isEmpty() ? null : filterFifaCode,
                filterCountryConfederationType,
                filterFinished,
                pageable
        );

    }

    public void save(CountryEntity pendingSave) {
        countryRepository.save(pendingSave);
    }


    public MediaEntity saveFlagImage(CountryEntity entity, byte[] image) {

        //TODO add audit columns

        String pathToFile = "/players/profile-image/" + entity.getId() + ".jpeg";

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

        entity.setCountryFlagImage(mediaEntity);

        countryRepository.save(entity);

        return mediaEntity;
    }

    public MediaEntity updateFlagImage(CountryEntity entity, byte[] image) {
        //TODO add audit columns

        String fullStoragePath =  entity.getCountryFlagImage().getFullStoragePath();
        if(appCommonConfigurationVariables.getStoragePath() == null) {
            throw new BusinessException("Error try update image");
        }
        fileSystemStorageService.replaceImageInStoragePassingPathAndByteArray(fullStoragePath, image);
        return entity.getCountryFlagImage();
    }
}
