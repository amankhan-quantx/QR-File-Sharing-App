// ======================================
// Material UI Components
// ======================================

import { Container, Grid } from "@mui/material";

// ======================================
// React
// ======================================

import { useState } from "react";

// ======================================
// Layout Components
// ======================================

import Navbar from "../components/layout/Navbar";

// ======================================
// File Components
// ======================================

import UploadCard from "../components/file/UploadCard";
import StatsCard from "../components/file/StatsCard";
import SearchCard from "../components/file/SearchCard";
import FileTable from "../components/file/FileTable";

// ======================================
// Component
// ======================================

function Dashboard() {

  // ======================================
  // State
  // ======================================

  const [search, setSearch] = useState("");
  const [refresh, setRefresh] = useState(false);

  // ======================================
  // UI
  // ======================================

  return (
    <>

      {/* ==========================
          Navigation Bar
      ========================== */}

      <Navbar />

      {/* ==========================
          Dashboard Container
      ========================== */}

      <Container sx={{ mt: 4 }}>

        <Grid container spacing={3}>

          {/* ==========================
              Top Section
          ========================== */}

          <Grid size={{ xs: 12, md: 6 }}>
            <UploadCard
              onUpload={() => setRefresh(!refresh)}
            />
          </Grid>

          <Grid size={{ xs: 12, md: 6 }}>
            <StatsCard
              refresh={refresh}
            />
          </Grid>

          {/* ==========================
              Search Section
          ========================== */}

          <Grid size={{ xs: 12 }}>
            <SearchCard
              search={search}
              setSearch={setSearch}
            />
          </Grid>

          {/* ==========================
              File Table
          ========================== */}

          <Grid size={{ xs: 12 }}>
            <FileTable
              search={search}
              refresh={refresh}
              onDelete={() => setRefresh(!refresh)}
            />
          </Grid>

        </Grid>

      </Container>

    </>
  );

}

export default Dashboard;