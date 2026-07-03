import api from "./api";

const fileService = {

  // Get all files
  getFiles(page = 0, size = 10, search = "") {
    return api.get("/files", {
      params: {
        page,
        size,
        search,
      },
    });
  },

  // Upload file
  uploadFile(file) {

    const formData = new FormData();

    formData.append("file", file);

    return api.post(
      "/files/upload",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
  },

  // Download file
  downloadFile(id) {
    return api.get(
      `/files/download/${id}`,
      {
        responseType: "blob",
      }
    );
  },

  // View QR Code
  getQrCode(id) {
    return `${api.defaults.baseURL}/files/qr/${id}`;
  },

  // Delete one file
  deleteFile(id) {
    return api.delete(`/files/${id}`);
  },

  // Delete all files
  deleteAllFiles() {
    return api.delete("/files");
  },

  // Storage statistics
  getStorageStats() {
    return api.get("/files/stats");
  }

};

export default fileService;