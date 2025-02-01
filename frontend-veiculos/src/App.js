import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Header from "./components/Header";
import Footer from "./components/Footer";
import AddVeiculo from "./pages/AddVeiculo";
import EditVeiculo from "./pages/EditVeiculo";
import { CssBaseline, ThemeProvider, createTheme } from "@mui/material";

const App = () => {

  const [darkMode, setDarkMode] = useState(false);

  const theme = createTheme({
    palette: {
      mode: darkMode ? "dark" : "light",
      background: {
        default: darkMode ? "#121212" : "#ffffff",
        paper: darkMode ? "#1e1e1e" : "#f9f9f9", 
      },
      text: {
        primary: darkMode ? "#ffffff" : "#000000",  
      },
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
    <Router>
      <div className="app-container">
        <Header darkMode={darkMode} setDarkMode={setDarkMode}/>
        <main style={{ flexGrow: 1, padding: "20px" }}>
          <Routes>
            <Route path="/" element={<Home  />} />
            <Route path="/adicionar" element={<AddVeiculo  />} />
            <Route path="/editar/:id" element={<EditVeiculo  />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
    </ThemeProvider>
  );
};

export default App;
