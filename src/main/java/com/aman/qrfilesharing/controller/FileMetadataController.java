package com.aman.qrfilesharing.controller;

import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.service.FileMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileMetadataController {

    private final FileMetadataService service;

    public FileMetadataController(FileMetadataService service) {
        this.service = service;
    }

    @GetMapping("/api/files")
    public List<FileMetadata> getAllFiles() {
        return service.getAllFiles();
    }

    @PostMapping(
        value = "/api/files/upload",
        consumes = "multipart/form-data"
    )
    public ResponseEntity<FileMetadata> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {

        FileMetadata uploadedFile = service.uploadFile(file);

        return ResponseEntity.ok(uploadedFile);
    }
}
