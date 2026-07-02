package com.aman.qrfilesharing.mapper;

import com.aman.qrfilesharing.dto.FileResponseDTO;
import com.aman.qrfilesharing.entity.FileMetadata;

public class FileMapper {

    public static FileResponseDTO toDTO(FileMetadata file) {

        FileResponseDTO dto = new FileResponseDTO();

        dto.setId(file.getId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        dto.setFileSize(file.getFileSize());
        dto.setUploadTime(file.getUploadTime());

        dto.setDownloadUrl("/api/files/download/" + file.getId());

        dto.setQrUrl("/api/files/qr/" + file.getId());

        dto.setExpiresAt(file.getExpiresAt());

        return dto;
    }
}