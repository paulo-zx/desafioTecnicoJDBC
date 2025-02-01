package com.example.DesafioTecnicoProgramador.entity;



import java.math.BigDecimal;


public class Carro extends Veiculo {



    public Carro(int id, String modelo, String fabricante, int ano, BigDecimal preco, String tipo, int quantidadePortas, String tipoCombustivel) {
        super(id, modelo, fabricante, ano, preco, tipo);
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    private int quantidadePortas;
    private String tipoCombustivel; // gasolina, etanol, diesel, flex

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
