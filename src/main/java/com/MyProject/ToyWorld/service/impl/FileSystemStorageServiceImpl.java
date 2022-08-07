package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.config.StorageProperties;
import com.MyProject.ToyWorld.exception.StorageException;
import com.MyProject.ToyWorld.exception.StorageFileNotFoundException;
import com.MyProject.ToyWorld.service.StorageService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLocation;
    private static final String PRODUCT_IMAGE_PATH_STR = "./uploads/images";
    private static final Path PRODUCT_IMAGE_PATH = Paths.get(PRODUCT_IMAGE_PATH_STR);

    public String getStoredFilename(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "." + ext;
    }


    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public void store(MultipartFile file, String storedFilename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void delete(String storedFilename) throws IOException {
        Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
        Files.delete(destinationFile);
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String saveProductImage(MultipartFile file) {
        String fileName = UUID.randomUUID() + FilenameUtils.EXTENSION_SEPARATOR_STR + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (!Files.exists(PRODUCT_IMAGE_PATH)) {
                Files.createDirectories(PRODUCT_IMAGE_PATH);
            }

            InputStream inputStream = file.getInputStream();
            Path filePath = PRODUCT_IMAGE_PATH.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new StorageException("Could not save upload file: " + fileName);
        }
        return fileName;
    }

    @Override
    public void deleteProductImage(String fileName) {
        Path filePath = PRODUCT_IMAGE_PATH.resolve(fileName);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
