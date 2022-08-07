package com.MyProject.ToyWorld.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void init();

    void delete(String filename) throws IOException;

    Path load(String filename);

    Resource loadAsResource(String storedFilename);

    void store(MultipartFile file, String storedFilename);

    String getStoredFilename(MultipartFile file, String id);

    String saveProductImage(MultipartFile file);

    void deleteProductImage(String fileName);

}
