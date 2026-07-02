package com.aman.qrfilesharing.service;

import com.aman.qrfilesharing.dto.StorageStatsDTO;
import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.repository.FileMetadataRepository;
import com.google.zxing.WriterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileMetadataService {

    // ======================================
    // Dependencies
    // ======================================

    private final FileMetadataRepository repository;
    private final QRCodeService qrCodeService;

    // ======================================
    // Logger
    // ======================================

    private static final Logger logger =
            LoggerFactory.getLogger(FileMetadataService.class);

    // ======================================
    // Constants
    // ======================================

    private static final long MAX_FILE_SIZE =
            100 * 1024 * 1024;

    private static final List<String> ALLOWED_TYPES = List.of(
            "application/pdf",
            "image/png",
            "image/jpeg",
            "application/zip",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "text/plain"
    );

    // ======================================
    // Constructor
    // ======================================

    public FileMetadataService(
            FileMetadataRepository repository,
            QRCodeService qrCodeService) {

        this.repository = repository;
        this.qrCodeService = qrCodeService;
    }

    // ======================================
    // Upload Operations
    // ======================================

    public FileMetadata uploadFile(MultipartFile file)
            throws IOException, WriterException {

        logger.info("Uploading file: {}",
                file.getOriginalFilename());

        validateFile(file);

        Path filePath = savePhysicalFile(file);

        FileMetadata metadata =
                createMetadata(file, filePath);

        FileMetadata savedMetadata =
                repository.save(metadata);

        logger.info("Metadata saved with ID: {}",
                savedMetadata.getId());

        generateQrCode(savedMetadata);

        return repository.save(savedMetadata);
    }

    // ======================================
    // File Retrieval
    // ======================================

    public Page<FileMetadata> getFiles(
            int page,
            int size,
            String search) {

        PageRequest pageable =
                PageRequest.of(page, size);

        if (search == null || search.isBlank()) {
            return repository.findAll(pageable);
        }

        return repository.findByFileNameContainingIgnoreCase(
                search,
                pageable
        );
    }

    public FileMetadata getFileById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));
    }

    // ======================================
    // File Management
    // ======================================

    public void deleteFile(Long id) throws IOException {

        FileMetadata metadata = getFileById(id);

        Files.deleteIfExists(Paths.get(metadata.getFilePath()));

        Files.deleteIfExists(Paths.get(metadata.getQrPath()));

        repository.delete(metadata);

        logger.info("Deleted file with ID: {}", id);
    }

    public void deleteAllFiles() throws IOException {

        List<FileMetadata> files = repository.findAll();

        for (FileMetadata file : files) {
            deleteFile(file.getId());
        }

        logger.info("All files deleted successfully.");
    }

    // ======================================
    // QR Code Operations
    // ======================================

    public Path getQrCode(Long id) {

        FileMetadata metadata = getFileById(id);

        return Paths.get(metadata.getQrPath());
    }

    // ======================================
    // Storage Statistics
    // ======================================

    public StorageStatsDTO getStorageStats() {

        List<FileMetadata> files = repository.findAll();

        long totalFiles = files.size();

        long totalStorageUsed = files.stream()
                .mapToLong(FileMetadata::getFileSize)
                .sum();

        long expiredFiles = files.stream()
                .filter(file ->
                        file.getExpiresAt()
                                .isBefore(LocalDateTime.now()))
                .count();

        return new StorageStatsDTO(
                totalFiles,
                totalStorageUsed,
                expiredFiles
        );
    }

    // ======================================
    // Helper Methods
    // ======================================

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File cannot be empty.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Maximum file size allowed is 100 MB.");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(
                    "File type is not supported.");
        }
    }

    private Path savePhysicalFile(MultipartFile file)
            throws IOException {

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath =
                uploadPath.resolve(file.getOriginalFilename());

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return filePath;
    }

    private FileMetadata createMetadata(
            MultipartFile file,
            Path filePath) {

        FileMetadata metadata = new FileMetadata();

        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setFilePath(filePath.toString());
        metadata.setUploadTime(LocalDateTime.now());
        metadata.setExpiresAt(
                LocalDateTime.now().plusDays(90)
        );

        return metadata;
    }

    private void generateQrCode(FileMetadata metadata)
            throws IOException, WriterException {

        String downloadUrl =
                "http://localhost:8080/api/files/download/"
                        + metadata.getId();

        String qrPath =
                qrCodeService.generateQRCode(
                        downloadUrl,
                        metadata.getId()
                );

        logger.info("QR Code generated at: {}", qrPath);

        metadata.setQrPath(qrPath);
    }
}
