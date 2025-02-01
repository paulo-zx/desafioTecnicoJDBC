package com.example.DesafioTecnicoProgramador.dao;

import com.example.DesafioTecnicoProgramador.entity.Carro;
import com.example.DesafioTecnicoProgramador.entity.Moto;
import com.example.DesafioTecnicoProgramador.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VeiculoImpl implements VeiculoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String GET_VEICULO_BY_ID_QUERY = """
    SELECT id, modelo, fabricante, ano, preco, tipo,
           COALESCE(quantidadePortas, 0) AS quantidadePortas,
           COALESCE(tipoCombustivel, '') AS tipoCombustivel,
           COALESCE(cilindrada, 0) AS cilindrada
    FROM Veiculo WHERE ID=?
""";


    private static final String DELETE_VEICULO_BY_ID_QUERY = "DELETE FROM Veiculo WHERE ID=?";




    @Override
    public Veiculo saveVeiculo(Veiculo veiculo) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        if (veiculo instanceof Carro) {
            Carro carro = (Carro) veiculo;
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO Veiculo (modelo, fabricante, ano, preco, quantidadePortas, tipoCombustivel, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        ps.setString(1, carro.getModelo());
                        ps.setString(2, carro.getFabricante());
                        ps.setInt(3, carro.getAno());
                        ps.setBigDecimal(4, carro.getPreco()); // Corrigido para BigDecimal
                        ps.setInt(5, carro.getQuantidadePortas());
                        ps.setString(6, carro.getTipoCombustivel());
                        ps.setString(7, "CARRO");
                        return ps;
                    },
                    keyHolder
            );
        } else if (veiculo instanceof Moto) {
            Moto moto = (Moto) veiculo;
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO Veiculo (modelo, fabricante, ano, preco, cilindrada, tipo) VALUES (?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        ps.setString(1, moto.getModelo());
                        ps.setString(2, moto.getFabricante());
                        ps.setInt(3, moto.getAno());
                        ps.setBigDecimal(4, moto.getPreco()); // Corrigido para BigDecimal
                        ps.setInt(5, moto.getCilindrada());
                        ps.setString(6, "MOTO");
                        return ps;
                    },
                    keyHolder
            );
        } else {
            throw new IllegalArgumentException("Tipo de veículo inválido.");
        }


        if (keyHolder.getKey() != null) {
            veiculo.setId(keyHolder.getKey().intValue());
        }

        return veiculo;
    }



    @Override
    public Veiculo updateVeiculo(Veiculo veiculo) {
        jdbcTemplate.update(
                "UPDATE Veiculo SET modelo=?, fabricante=?, ano=?, preco=?, " +
                        "quantidadePortas=?, tipoCombustivel=?, cilindrada=?, tipo=? WHERE id=?",
                veiculo.getModelo(),
                veiculo.getFabricante(),
                veiculo.getAno(),
                veiculo.getPreco() != null ? veiculo.getPreco() : BigDecimal.ZERO,
                veiculo instanceof Carro ? ((Carro) veiculo).getQuantidadePortas() : null,
                veiculo instanceof Carro ? ((Carro) veiculo).getTipoCombustivel() : null,
                veiculo instanceof Moto ? ((Moto) veiculo).getCilindrada() : null,
                veiculo instanceof Carro ? "CARRO" : "MOTO",  // Atualiza o tipo no banco
                veiculo.getId()
        );

        return veiculo;
    }



    @Override
    public String deleteById(int id) {
        int rowsAffected = jdbcTemplate.update(DELETE_VEICULO_BY_ID_QUERY, id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Veículo com ID " + id + " não encontrado.");
        }
        return "Veículo removido com sucesso.";
    }


    @Override
    public Veiculo getById(int id) {
        return jdbcTemplate.queryForObject(
                GET_VEICULO_BY_ID_QUERY,
                new Object[]{id},
                (rs, rowNum) -> {

                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    String tipoVeiculo = rs.getString("tipo"); // Incluímos o tipo do veículo aqui


                    if ("CARRO".equals(tipoVeiculo)) {
                        int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                        String tipoCombustivel = rs.getString("tipoCombustivel");
                        if (tipoCombustivel == null) {
                            tipoCombustivel = "";
                        }


                        return new Carro(
                                veiculoId,
                                modelo,
                                fabricante,
                                ano,
                                preco,
                                tipoVeiculo,
                                quantidadePortas,
                                tipoCombustivel
                        );

                    } else if ("MOTO".equals(tipoVeiculo)) {
                        int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;


                        return new Moto(
                                veiculoId,
                                modelo,
                                fabricante,
                                ano,
                                preco,
                                tipoVeiculo,
                                cilindrada
                        );

                    } else {
                        throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
                    }
                }
        );


    }


    @Override
    public List<Veiculo> allVeiculos() {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo",
                (rs, rowNum) -> {
                    // Campos comuns para todos os veículos
                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    String tipoVeiculo = rs.getString("tipo"); // Incluímos o tipo do veículo aqui


                    if ("CARRO".equals(tipoVeiculo)) {
                        int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                        String tipoCombustivel = rs.getString("tipoCombustivel");
                        if (tipoCombustivel == null) {
                            tipoCombustivel = "";
                        }


                        return new Carro(
                                veiculoId,
                                modelo,
                                fabricante,
                                ano,
                                preco,
                                tipoVeiculo,
                                quantidadePortas,
                                tipoCombustivel
                        );

                    } else if ("MOTO".equals(tipoVeiculo)) {
                        int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;


                        return new Moto(
                                veiculoId,
                                modelo,
                                fabricante,
                                ano,
                                preco,
                                tipoVeiculo,
                                cilindrada
                        );

                    } else {
                        throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
                    }
                }
        );
    }

    @Override
    public List<Veiculo> allCarros() {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo WHERE tipo = 'CARRO'",
                (rs, rowNum) -> {
                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                    String tipoCombustivel = rs.getString("tipoCombustivel");
                    if (tipoCombustivel == null) {
                        tipoCombustivel = "";
                    }

                    // Construtor para Carro
                    return new Carro(
                            veiculoId,
                            modelo,
                            fabricante,
                            ano,
                            preco,
                            "CARRO",
                            quantidadePortas,
                            tipoCombustivel
                    );
                }
        );
    }

    @Override
    public List<Veiculo> allMotos() {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo WHERE tipo = 'MOTO'",
                (rs, rowNum) -> {
                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;


                    return new Moto(
                            veiculoId,
                            modelo,
                            fabricante,
                            ano,
                            preco,
                            "MOTO",
                            cilindrada
                    );
                }
        );
    }

    @Override
    public List<Veiculo> allByFabricante(String fabricante) {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo WHERE fabricante = ?",
                new Object[]{fabricante},
                (rs, rowNum) -> {
                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    String tipoVeiculo = rs.getString("tipo");

                    if ("CARRO".equals(tipoVeiculo)) {
                        int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                        String tipoCombustivel = rs.getString("tipoCombustivel");
                        return new Carro(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, quantidadePortas, tipoCombustivel
                        );
                    } else if ("MOTO".equals(tipoVeiculo)) {
                        int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;
                        return new Moto(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, cilindrada
                        );
                    } else {
                        throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
                    }
                }
        );
    }

    @Override
    public List<Veiculo> allByAno(int ano) {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo WHERE ano = ?",
                new Object[]{ano},
                (rs, rowNum) -> {
                    int veiculoId = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String fabricante = rs.getString("fabricante");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    String tipoVeiculo = rs.getString("tipo");

                    if ("CARRO".equals(tipoVeiculo)) {
                        int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                        String tipoCombustivel = rs.getString("tipoCombustivel");
                        return new Carro(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, quantidadePortas, tipoCombustivel
                        );
                    } else if ("MOTO".equals(tipoVeiculo)) {
                        int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;
                        return new Moto(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, cilindrada
                        );
                    } else {
                        throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
                    }
                }
        );
    }

    @Override
    public List<Veiculo> allByModelo(String modelo) {
        return jdbcTemplate.query(
                "SELECT * FROM Veiculo WHERE modelo = ?",
                new Object[]{modelo},
                (rs, rowNum) -> {
                    int veiculoId = rs.getInt("id");
                    String fabricante = rs.getString("fabricante");
                    int ano = rs.getInt("ano");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    String tipoVeiculo = rs.getString("tipo");

                    if ("CARRO".equals(tipoVeiculo)) {
                        int quantidadePortas = rs.getObject("quantidadePortas") != null ? rs.getInt("quantidadePortas") : 0;
                        String tipoCombustivel = rs.getString("tipoCombustivel");
                        return new Carro(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, quantidadePortas, tipoCombustivel
                        );
                    } else if ("MOTO".equals(tipoVeiculo)) {
                        int cilindrada = rs.getObject("cilindrada") != null ? rs.getInt("cilindrada") : 0;
                        return new Moto(
                                veiculoId, modelo, fabricante, ano, preco, tipoVeiculo, cilindrada
                        );
                    } else {
                        throw new IllegalArgumentException("Tipo de veículo inválido: " + tipoVeiculo);
                    }
                }
        );
    }




}
