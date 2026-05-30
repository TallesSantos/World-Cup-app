package io.github.tallessantos.world_cup_api.infra.repository;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Repository
public class FileSistemRepositoryImpl implements FileSystemRepository {

    @Override
    public String saveFile(String path, byte[] document) {

        try {
            Path filePath = Paths.get(path);

            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.write(filePath, document);
            return filePath.toFile().getPath();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while saving file: " + path,
                    e
            );
        }

    }

    @Override
    public String replaceFile(String path, byte[] document) {

        Path filePath = Paths.get(path);

        try {
            Files.write(
                    filePath,
                    document,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath.toString();
    }


    @Override
    public void deleteFile(String fullPath) {

        try {

            Path path = Paths.get(fullPath);

            Files.deleteIfExists(path);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error deleting file: " + fullPath,
                    e
            );

        }

    }

}