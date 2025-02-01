package com.example.DesafioTecnicoProgramador.dao;

import com.example.DesafioTecnicoProgramador.entity.Veiculo;

import java.util.List;

public interface VeiculoRepository {

    Veiculo saveVeiculo(Veiculo veiculo);
    Veiculo updateVeiculo(Veiculo veiculo);
    Veiculo getById(int id);

    String deleteById(int id);
    List<Veiculo> allVeiculos();

    List<Veiculo> allCarros();
    List<Veiculo> allMotos();

    List<Veiculo> allByFabricante(String fabricante);

    List<Veiculo> allByAno(int ano);
    List<Veiculo> allByModelo(String modelo);





}
