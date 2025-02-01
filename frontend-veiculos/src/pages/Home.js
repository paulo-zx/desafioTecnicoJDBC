import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Paper,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import { Edit, Delete } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Home = () => {
  const [veiculos, setVeiculos] = useState([]);
  const [filtroModelo, setFiltroModelo] = useState("");
  const [filtroFabricante, setFiltroFabricante] = useState("");
  const [filtroAno, setFiltroAno] = useState("");
  const [filtroTipo, setFiltroTipo] = useState("");
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedId, setSelectedId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/veiculos")
      .then((response) => {
        setVeiculos(response.data);
      })
      .catch((error) => {
        console.error("Erro ao buscar veículos:", error);
      });
  }, []);

  const handleDelete = (id) => {
    setSelectedId(id);
    setOpenDialog(true);
  };

  const confirmDelete = () => {
    axios
      .delete(`http://localhost:8080/veiculo/${selectedId}`)
      .then(() => {
        setVeiculos(veiculos.filter((v) => v.id !== selectedId));
        alert("Veículo excluído com sucesso!");
      })
      .catch((error) => {
        alert("Erro ao excluir veículo.");
      });
    setOpenDialog(false);
  };

  const handleEdit = (id) => {
    navigate(`/editar/${id}`);
  };

  const filteredVeiculos = veiculos.filter(
    (v) =>
      v.modelo.toLowerCase().includes(filtroModelo.toLowerCase()) &&
      (!filtroFabricante || v.fabricante.toLowerCase() === filtroFabricante.toLowerCase()) &&
      (!filtroAno || v.ano.toString() === filtroAno) &&
      (!filtroTipo || v.tipo.toLowerCase() === filtroTipo.toLowerCase())
  );

  const totalCarros = veiculos.filter((v) => v.tipo.toLowerCase() === "carro").length;
  const totalMotos = veiculos.filter((v) => v.tipo.toLowerCase() === "moto").length;

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: "flex", justifyContent: "space-between", mb: 2 }}>
        <h3>Carros: {totalCarros} 🚗 | Motos: {totalMotos} 🏍️</h3>
      </Box>
      <Box sx={{ display: "flex", flexWrap: "wrap", gap: 2, mb: 2 }}>
        <TextField label="Filtrar por modelo" variant="outlined" size="small" value={filtroModelo} onChange={(e) => setFiltroModelo(e.target.value)} />
        <TextField label="Filtrar por fabricante" variant="outlined" size="small" value={filtroFabricante} onChange={(e) => setFiltroFabricante(e.target.value)} />
        <TextField label="Filtrar por ano" variant="outlined" size="small" type="number" value={filtroAno} onChange={(e) => setFiltroAno(e.target.value)} />
        <FormControl size="small" sx={{ minWidth: 120 }}>
          <InputLabel>Filtrar por tipo</InputLabel>
          <Select value={filtroTipo} onChange={(e) => setFiltroTipo(e.target.value)} label="Filtrar por tipo">
            <MenuItem value="">Todos</MenuItem>
            <MenuItem value="carro">Carro</MenuItem>
            <MenuItem value="moto">Moto</MenuItem>
          </Select>
        </FormControl>
        <Button variant="contained" color="primary" onClick={() => navigate("/adicionar")}>Adicionar Veículo</Button>
      </Box>

      <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 3 }}>
        <Table>
          <TableHead sx={{ backgroundColor: "#1976d2" }}>
            <TableRow>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>ID</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Modelo</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Fabricante</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Ano</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Tipo</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredVeiculos.map((veiculo) => (
              <TableRow key={veiculo.id} hover sx={{ backgroundColor: veiculo.tipo.toLowerCase() === "carro" ? "#e3f2fd" : "#e8f5e9" }}>
                <TableCell>{veiculo.id}</TableCell>
                <TableCell>
                  {veiculo.tipo.toLowerCase() === "carro" ? "🚗" : "🏍️"} {veiculo.modelo}
                </TableCell>
                <TableCell>{veiculo.fabricante}</TableCell>
                <TableCell>{veiculo.ano}</TableCell>
                <TableCell>{veiculo.tipo}</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleEdit(veiculo.id)}>
                    <Edit />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(veiculo.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          <DialogContentText>Tem certeza que deseja excluir este veículo?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)} color="primary">Cancelar</Button>
          <Button onClick={confirmDelete} color="secondary">Excluir</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Home;
