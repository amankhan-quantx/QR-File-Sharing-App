package com.aman.qrfilesharing.controller;

import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.service.FileMetadataService;
import org.springframework.http.MediaType;
import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    value="/api/files/upload",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FileMetadata> uploadFile(
        @RequestParam("file") MultipartFile file)
        throws IOException, WriterException {

        FileMetadata uploadedFile = service.uploadFile(file);

        return ResponseEntity.ok(uploadedFile);
    }
    @GetMapping("/api/files/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
        @PathVariable String fileName) throws Exception {

        Path path = Paths.get("uploads").resolve(fileName);

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\""
        )
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
    }
}
