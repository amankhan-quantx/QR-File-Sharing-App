// ======================================
// Material UI Components
// ======================================

import {
  Card,
  CardContent,
  Typography,
  TextField,
} from "@mui/material";

// ======================================
// Component
// ======================================

function SearchCard({ search, setSearch }) {

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
          Search Files
        </Typography>

        <TextField
          fullWidth
          label="Search by file name"
          variant="outlined"
          value={search}
          onChange={(event) =>
            setSearch(event.target.value)
          }
        />

      </CardContent>

    </Card>

  );

}

export default SearchCard;