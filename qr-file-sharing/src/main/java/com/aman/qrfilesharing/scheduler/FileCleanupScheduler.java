package com.aman.qrfilesharing.scheduler;

import com.aman.qrfilesharing.entity.FileMetadata;
import com.aman.qrfilesharing.repository.FileMetadataRepository;
import com.aman.qrfilesharing.service.FileMetadataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FileCleanupScheduler {

    // ======================================
    // Logger
    // ======================================

    private static final Logger logger =
            LoggerFactory.getLogger(FileCleanupScheduler.class);

    // ======================================
    // Dependencies
    // ======================================

    private final FileMetadataRepository repository;
    private final FileMetadataService service;

    // ======================================
    // Constructor
    // ======================================

    public FileCleanupScheduler(
            FileMetadataRepository repository,
            FileMetadataService service) {

        this.repository = repository;
        this.service = service;
    }

    // ======================================
    // Scheduled Cleanup
    // Runs every day at 2:00 AM
    // ======================================

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupExpiredFiles() throws IOException {

        logger.info("Starting scheduled file cleanup...");

        List<FileMetadata> files = repository.findAll();

        for (FileMetadata file : files) {

            if (file.getExpiresAt().isBefore(LocalDateTime.now())) {

                service.deleteFile(file.getId());

                logger.info(
                        "Deleted expired file: {}",
                        file.getFileName()
                );
            }
        }

        logger.info("Scheduled cleanup completed.");
    }
}