package com.example.DesafioTecnicoProgramador.entity;



import java.math.BigDecimal;


public class Moto extends Veiculo {

    private int cilindrada;

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }



    public Moto(int id, String modelo, String fabricante, int ano, BigDecimal preco, String tipo, int cilindrada) {
        super(id, modelo, fabricante, ano, preco, tipo);
        this.cilindrada = cilindrada;
    }
}
