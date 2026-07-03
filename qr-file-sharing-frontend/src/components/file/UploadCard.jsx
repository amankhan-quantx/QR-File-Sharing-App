// ======================================
// Material UI Components
// ======================================

import {
  Card,
  CardContent,
  Typography,
  Button,
  Stack,
} from "@mui/material";

// ======================================
// Icons
// ======================================

import UploadFileIcon from "@mui/icons-material/UploadFile";

// ======================================
// React
// ======================================

import { useState } from "react";

// ======================================
// Services
// ======================================

import fileService from "../../services/fileService";

// ======================================
// Component
// ======================================

function UploadCard({ onUpload }) {

  // ======================================
  // State
  // ======================================

  const [selectedFile, setSelectedFile] = useState(null);

  // ======================================
  // Upload File
  // ======================================

  const uploadFile = async () => {

    // Check whether user selected a file
    if (!selectedFile) {
      alert("Please select a file.");
      return;
    }

    try {

      // Upload using service layer
      await fileService.uploadFile(selectedFile);

      alert("File uploaded successfully!");

      // Clear selected file
      setSelectedFile(null);

      // Refresh Dashboard
      if (onUpload) {
        onUpload();
      }

    } catch (error) {

      console.error(error);

      alert("Upload failed.");

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
          📤 Upload File
        </Typography>

        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ mb: 3 }}
        >
          Choose a file from your computer to upload.
        </Typography>

        <Stack
          direction="row"
          spacing={2}
        >

          <Button
            variant="outlined"
            component="label"
            startIcon={<UploadFileIcon />}
          >

            Choose File

            <input
              hidden
              type="file"
              onChange={(event) =>
                setSelectedFile(event.target.files[0])
              }
            />

          </Button>

          <Button
            variant="contained"
            color="primary"
            onClick={uploadFile}
          >
            Upload
          </Button>

        </Stack>

        {selectedFile && (

          <Typography
            variant="body2"
            sx={{ mt: 2 }}
          >
            Selected File:{" "}
            <strong>{selectedFile.name}</strong>
          </Typography>

        )}

      </CardContent>

    </Card>

  );

}

export default UploadCard;