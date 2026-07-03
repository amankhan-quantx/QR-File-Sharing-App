package com.aman.qrfilesharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {

    private Long id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private LocalDateTime uploadTime;

    private String downloadUrl;

    private String qrUrl;

    private LocalDateTime expiresAt;
    
}