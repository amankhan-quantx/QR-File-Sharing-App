package com.aman.qrfilesharing.controller;

import com.aman.qrfilesharing.dto.FileResponseDTO;
import com.aman.qrfilesharing.dto.StorageStatsDTO;
import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.mapper.FileMapper;
import com.aman.qrfilesharing.service.FileMetadataService;
import com.google.zxing.WriterException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/files")
public class FileMetadataController {

    private final FileMetadataService service;

    public FileMetadataController(FileMetadataService service) {
        this.service = service;
    }

    // ==========================
    // Get All Files / Search / Pagination
    // ==========================
    @GetMapping
    public List<FileResponseDTO> getFiles(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String search) {

        return service.getFiles(page, size, search)
                .stream()
                .map(FileMapper::toDTO)
                .toList();
    }

    // ==========================
    // Upload File
    // ==========================
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FileResponseDTO> uploadFile(
            @RequestParam("file") MultipartFile file)
            throws IOException, WriterException {

        FileMetadata uploadedFile = service.uploadFile(file);

        FileResponseDTO response =
                FileMapper.toDTO(uploadedFile);

        return ResponseEntity.ok(response);
    }

    // ==========================
    // Download File
    // ==========================
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id) throws IOException {

        FileMetadata metadata = service.getFileById(id);

        Path path = Paths.get(metadata.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                resource.getFilename() + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // ==========================
    // Delete One File
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long id)
            throws IOException {

        service.deleteFile(id);

        return ResponseEntity.noContent().build();
    }

    // ==========================
    // Delete All Files
    // ==========================
    @DeleteMapping
    public ResponseEntity<Void> deleteAllFiles()
            throws IOException {

        service.deleteAllFiles();

        return ResponseEntity.noContent().build();
    }

    // ==========================
    // View QR Code
    // ==========================
    @GetMapping("/qr/{id}")
    public ResponseEntity<Resource> viewQrCode(
            @PathVariable Long id)
            throws IOException {

        Path path = service.getQrCode(id);

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    // ==========================
    // Storage Statistics
    // ==========================
    @GetMapping("/stats")
    public ResponseEntity<StorageStatsDTO> getStorageStats() {

        return ResponseEntity.ok(
                service.getStorageStats()
        );
    }
}