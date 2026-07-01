package com.aman.qrfilesharing.service;

import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.nio.file.StandardCopyOption;

@Service
public class FileMetadataService {

    private final FileMetadataRepository repository;

    public FileMetadataService(FileMetadataRepository repository) {
        this.repository = repository;
    }

    public List<FileMetadata> getAllFiles() {
        return repository.findAll();
    }

    public FileMetadata saveFile(FileMetadata file) {
        return repository.save(file);
    }
    
    public FileMetadata uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(
            file.getInputStream(),
            filePath,
            StandardCopyOption.REPLACE_EXISTING
        );
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(fileName);
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setFilePath(filePath.toString());
        metadata.setUploadTime(LocalDateTime.now());
       
        return repository.save(metadata);
    }

}