package com.aman.qrfilesharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorageStatsDTO {

    private long totalFiles;

    private long totalStorageUsed;

    private long expiredFiles;
}