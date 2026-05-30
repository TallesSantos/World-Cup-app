package io.github.tallessantos.world_cup_api.infra.repository;

public interface FileSystemRepository {

    String saveFile (String path, byte[] document);

    String replaceFile(String path, byte[] document);

    void deleteFile(String storagePath);
}
