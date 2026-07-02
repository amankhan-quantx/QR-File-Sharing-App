package com.aman.qrfilesharing.repository;

import com.aman.qrfilesharing.entity.FileMetadata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository
        extends JpaRepository<FileMetadata, Long> {

    Page<FileMetadata> findByFileNameContainingIgnoreCase(
            String fileName,
            Pageable pageable
    );
}