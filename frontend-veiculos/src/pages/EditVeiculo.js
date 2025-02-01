import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Box, Button, MenuItem, TextField } from "@mui/material";
import axios from "axios";

const EditVeiculo = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [veiculo, setVeiculo] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/veiculo/${id}`)
      .then((response) => {
        setVeiculo(response.data);
      })
      .catch((error) => {
        console.error("Erro ao buscar veículo:", error);
      });
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setVeiculo({ ...veiculo, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios
      .put("http://localhost:8080/veiculo", veiculo)
      .then(() => {
        alert("Veículo atualizado com sucesso!");
        navigate("/");
      })
      .catch((error) => {
        console.error("Erro ao atualizar veículo:", error);
        alert("Erro ao atualizar veículo.");
      });
  };

  if (!veiculo) return <p>Carregando...</p>;

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ maxWidth: 500, margin: "0 auto" }}>
      <h2>Editar Veículo</h2>
      <TextField
        label="Modelo"
        name="modelo"
        value={veiculo.modelo}
        onChange={handleChange}
        fullWidth
        required
        margin="normal"
      />
      <TextField
        label="Fabricante"
        name="fabricante"
        value={veiculo.fabricante}
        onChange={handleChange}
        fullWidth
        required
        margin="normal"
      />
      <TextField
        label="Ano"
        name="ano"
        value={veiculo.ano}
        onChange={handleChange}
        type="number"
        fullWidth
        required
        margin="normal"
      />
      <TextField
        label="Tipo"
        name="tipo"
        value={veiculo.tipo}
        onChange={handleChange}
        select
        fullWidth
        required
        margin="normal"
      >
        <MenuItem value="CARRO">Carro</MenuItem>
        <MenuItem value="MOTO">Moto</MenuItem>
      </TextField>
      {veiculo.tipo === "CARRO" && (
        <>
          <TextField
            label="Quantidade de Portas"
            name="quantidadePortas"
            value={veiculo.quantidadePortas}
            onChange={handleChange}
            type="number"
            fullWidth
            margin="normal"
          />
          <TextField
            label="Tipo de Combustível"
            name="tipoCombustivel"
            value={veiculo.tipoCombustivel}
            onChange={handleChange}
            fullWidth
            margin="normal"
          />
        </>
      )}
      {veiculo.tipo === "MOTO" && (
        <TextField
          label="Cilindrada"
          name="cilindrada"
          value={veiculo.cilindrada}
          onChange={handleChange}
          type="number"
          fullWidth
          margin="normal"
        />
      )}
      {}
      <TextField
        label="Preço"
        name="preco"
        value={veiculo.preco || ""}
        onChange={handleChange}
        type="number"
        fullWidth
        required
        margin="normal"
        inputProps={{ step: "0.01" }} 
      />
      <Button type="submit" variant="contained" color="primary" fullWidth>
        Atualizar Veículo
      </Button>
    </Box>
  );
};

export default EditVeiculo;
