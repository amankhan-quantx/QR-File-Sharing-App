// ======================================
// Material UI Components
// ======================================

import {
  Card,
  CardContent,
  Typography,
  Stack,
} from "@mui/material";

// ======================================
// React
// ======================================

import { useEffect, useState } from "react";

// ======================================
// Services
// ======================================

import fileService from "../../services/fileService";

// ======================================
// Icons
// ======================================

import DescriptionIcon from "@mui/icons-material/Description";
import StorageIcon from "@mui/icons-material/Storage";
import ScheduleIcon from "@mui/icons-material/Schedule";

// ======================================
// Component
// ======================================

function StatsCard({ refresh }) {

  // ======================================
  // State
  // ======================================

  const [stats, setStats] = useState({
    totalFiles: 0,
    totalStorageUsed: 0,
    expiredFiles: 0,
  });

  // ======================================
  // Lifecycle
  // ======================================

  useEffect(() => {
    loadStats();
  }, [refresh]);

  // ======================================
  // Load Storage Statistics
  // ======================================

  const loadStats = async () => {

    try {

      const response =
        await fileService.getStorageStats();

      setStats(response.data);

    } catch (error) {

      console.error(
        "Failed to load statistics",
        error
      );

    }

  };

  // ======================================
  // UI
  // ======================================

  return (

    <Card elevation={3}>

      <CardContent>

        <Typography
          variant="h5"
          gutterBottom
        >
          📊 Storage Statistics
        </Typography>

        <Stack
          spacing={2}
          sx={{ mt: 2 }}
        >

          <Typography>

            <DescriptionIcon
              fontSize="small"
              sx={{ mr: 1 }}
            />

            Total Files : {stats.totalFiles}

          </Typography>

          <Typography>

            <StorageIcon
              fontSize="small"
              sx={{ mr: 1 }}
            />

            Total Storage :
            {" "}
            {(stats.totalStorageUsed / 1024 / 1024).toFixed(2)}
            {" "}MB

          </Typography>

          <Typography>

            <ScheduleIcon
              fontSize="small"
              sx={{ mr: 1 }}
            />

            Expired Files : {stats.expiredFiles}

          </Typography>

        </Stack>

      </CardContent>

    </Card>

  );

}

export default StatsCard;