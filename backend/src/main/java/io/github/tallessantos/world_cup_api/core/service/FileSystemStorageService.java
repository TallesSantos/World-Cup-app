package io.github.tallessantos.world_cup_api.core.service;

import io.github.tallessantos.world_cup_api.infra.repository.FileSystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileSystemStorageService {

    @Autowired
    private FileSystemRepository fileSystemRepository;

    public String saveImageInStoragePassingPathAndByteArray(String path, byte[] image) {
        try {
            return fileSystemRepository.saveFile(path, image);
        } catch (Exception e) {
            log.error("Error try save media: {}", e.getMessage());
        }
        return null;
    }

    public void replaceImageInStoragePassingPathAndByteArray(String fullImagePath, byte[] image) {
        try {

            fileSystemRepository.deleteFile(fullImagePath);

            fileSystemRepository.saveFile(fullImagePath, image);

        } catch (Exception e) {
            log.error("Error try replace media: {}", e.getMessage());
        }
    }
}
