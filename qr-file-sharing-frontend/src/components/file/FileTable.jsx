// ======================================
// Material UI Components
// ======================================

import {
  Card,
  CardContent,
  Typography,
  Box,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  TableContainer,
  Pagination,
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
// Component
// ======================================

function FileTable({ search, refresh, onDelete }) {

  // ======================================
  // State
  // ======================================

  const [files, setFiles] = useState([]);
  const [page, setPage] = useState(1);
  const pageSize = 10;

  // ======================================
  // Lifecycle
  // ======================================

  useEffect(() => {
    loadFiles();
  }, [page, search, refresh]);

  // ======================================
  // Load Files
  // ======================================

  const loadFiles = async () => {

    try {

      const response =
        await fileService.getFiles(
          page - 1,
          pageSize,
          search
        );

      setFiles(response.data);

    } catch (error) {

      console.error("Failed to load files", error);

    }

  };

  // ======================================
  // Download File
  // ======================================

  const downloadFile = (id) => {

    window.open(
      `http://localhost:8080/api/files/download/${id}`,
      "_blank"
    );

  };

  // ======================================
  // View QR Code
  // ======================================

  const viewQrCode = (id) => {

    window.open(
      `http://localhost:8080/api/files/qr/${id}`,
      "_blank"
    );

  };

  // ======================================
  // Delete File
  // ======================================

  const deleteFile = async (id) => {

    const confirmed = window.confirm(
      "Are you sure you want to delete this file?"
    );

    if (!confirmed) {
      return;
    }

    try {

      await fileService.deleteFile(id);
      if (onDelete) {
        onDelete();
      }

      alert("File deleted successfully!");

      loadFiles();

    } catch (error) {

      console.error("Delete failed", error);

      alert("Failed to delete file.");

    }

  };
    // ======================================
    // Delete All Files
    // ======================================

    const deleteAllFiles = async () => {

        const confirmed = window.confirm(
            "Delete ALL files?"
        );

        if (!confirmed) {
            return;
        }

        try {

            await fileService.deleteAllFiles();

            alert("All files deleted successfully!");

            if (onDelete) {
            onDelete();
            }

            loadFiles();

        } catch (error) {

            console.error(error);

            alert("Failed to delete all files.");

        }

    };

  // ======================================
  // UI
  // ======================================

  return (

    <Card elevation={3}>

      <CardContent>

        {/* ==========================
            Title
        ========================== */}

        <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={2}
        >

        <Typography variant="h5">
            Uploaded Files
        </Typography>

        <Button
            color="error"
            variant="contained"
            onClick={deleteAllFiles}
        >
            Delete All
        </Button>

        </Box>

        {/* ==========================
            Scrollable Table
        ========================== */}

        <TableContainer
          sx={{
            overflowX: "auto",
            scrollBehavior: "smooth",
          }}
        >

          <Table
            size="small"
            sx={{
              minWidth: 900,

              "& .MuiTableCell-root": {
                py: 1.5,
                whiteSpace: "nowrap",
              },
            }}
          >

            {/* ==========================
                Table Header
            ========================== */}

            <TableHead>

              <TableRow>

                <TableCell>
                  <strong>File Name</strong>
                </TableCell>

                <TableCell>
                  <strong>Size (KB)</strong>
                </TableCell>

                <TableCell>
                  <strong>Download</strong>
                </TableCell>

                <TableCell>
                  <strong>QR Code</strong>
                </TableCell>

                <TableCell>
                  <strong>Delete</strong>
                </TableCell>

              </TableRow>

            </TableHead>

            {/* ==========================
                Table Body
            ========================== */}

            <TableBody>

              {files.length === 0 ? (

                <TableRow>

                  <TableCell
                    colSpan={5}
                    align="center"
                  >
                    No files found
                  </TableCell>

                </TableRow>

              ) : (

                files.map((file) => (

                  <TableRow key={file.id}>

                    {/* File Name */}

                    <TableCell>
                      {file.fileName}
                    </TableCell>

                    {/* File Size */}

                    <TableCell>
                      {(file.fileSize / 1024).toFixed(2)}
                    </TableCell>

                    {/* Download */}

                    <TableCell>

                      <Button
                        variant="contained"
                        size="small"
                        onClick={() => downloadFile(file.id)}
                      >
                        Download
                      </Button>

                    </TableCell>

                    {/* QR */}

                    <TableCell>

                      <Button
                        variant="outlined"
                        size="small"
                        onClick={() => viewQrCode(file.id)}
                      >
                        QR
                      </Button>

                    </TableCell>

                    {/* Delete */}

                    <TableCell>

                      <Button
                        color="error"
                        variant="contained"
                        size="small"
                        onClick={() => deleteFile(file.id)}
                      >
                        Delete
                      </Button>

                    </TableCell>

                  </TableRow>

                ))

              )}

            </TableBody>

          </Table>

        </TableContainer>

      </CardContent>

    </Card>

  );

}

export default FileTable;