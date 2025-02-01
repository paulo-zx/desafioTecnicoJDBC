package com.example.DesafioTecnicoProgramador.entity;



import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;



@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Carro.class, name = "CARRO"),
        @JsonSubTypes.Type(value = Moto.class, name = "MOTO")
})


public abstract class Veiculo {

    private int id;
    private String modelo;
    private String fabricante;
    private int ano;
    private BigDecimal preco;
    private String tipo; // Pode ser "CARRO" ou "MOTO"

    public Veiculo(int id, String modelo, String fabricante, int ano, BigDecimal preco, String tipo) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.preco = preco;
        this.tipo = tipo;
    }

    public Veiculo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
