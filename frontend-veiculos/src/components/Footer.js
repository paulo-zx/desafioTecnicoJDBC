import React from "react";
import { Box, Typography, IconButton } from "@mui/material";
import GitHubIcon from "@mui/icons-material/GitHub";

const Footer = () => {
  return (
    <Box
      component="footer"
      sx={{
        backgroundColor: "primary.main",
        color: "white",
        textAlign: "center",
        py: 2,
        mt: "auto", 
        width: "100%",
      }}
    >
      <Typography variant="body1">Paulo Roberto</Typography>
      <Typography variant="body2" sx={{ mt: 1 }}>
        © {new Date().getFullYear()} - Todos os direitos reservados
      </Typography>
      <IconButton
        color="inherit"
        href="https://github.com/paulo-zx"
        target="_blank"
        rel="noopener noreferrer"
        sx={{ mt: 1 }}
      >
        <GitHubIcon />
      </IconButton>
    </Box>
  );
};

export default Footer;

