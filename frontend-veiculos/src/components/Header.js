import React from "react";
import { AppBar, Toolbar, Typography, IconButton, Switch } from "@mui/material";
import { Home as HomeIcon, DarkMode, LightMode } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";

const Header = ({ darkMode, setDarkMode }) => {
  const navigate = useNavigate();

  return (
    <AppBar position="static" sx={{ bgcolor: darkMode ? "#333" : "#1976d2" }}>
      <Toolbar>
        <IconButton edge="start" color="inherit" onClick={() => navigate("/")}>
          <HomeIcon />
        </IconButton>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Gerenciamento de Sistema
        </Typography>
        <IconButton color="inherit">
          {darkMode ? <DarkMode /> : <LightMode />}
        </IconButton>
        <Switch checked={darkMode} onChange={() => setDarkMode(!darkMode)} />
      </Toolbar>
    </AppBar>
  );
};

export default Header;
