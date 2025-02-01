package com.example.DesafioTecnicoProgramador.controller;

import com.example.DesafioTecnicoProgramador.dao.VeiculoRepository;
import com.example.DesafioTecnicoProgramador.entity.Carro;
import com.example.DesafioTecnicoProgramador.entity.Moto;
import com.example.DesafioTecnicoProgramador.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    VeiculoRepository veiculoRepository;


    @PostMapping("/veiculo")
    public Veiculo addVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo instanceof Carro) {
            Carro carro = (Carro) veiculo;
            if (carro.getQuantidadePortas() <= 0 || carro.getTipoCombustivel() == null) {
                throw new IllegalArgumentException("Carro deve ter quantidade de portas maior que 0 e tipo de combustível.");
            }
            veiculo.setTipo("CARRO");
        } else if (veiculo instanceof Moto) {
            Moto moto = (Moto) veiculo;
            if (moto.getCilindrada() <= 0) {
                throw new IllegalArgumentException("Moto deve ter cilindrada maior que 0.");
            }
            veiculo.setTipo("MOTO");
        } else {
            throw new IllegalArgumentException("Tipo de veículo inválido.");
        }
        return veiculoRepository.saveVeiculo(veiculo);
    }

    @PutMapping("/veiculo")
    public Veiculo updateVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo instanceof Carro || veiculo instanceof Moto) {
            return veiculoRepository.updateVeiculo(veiculo);
        }
        throw new IllegalArgumentException("Tipo de veículo inválido.");
    }

    @GetMapping("/veiculo/{id}")
    public Veiculo getVeiculo(@PathVariable("id") int id) {
        return veiculoRepository.getById(id);
    }

    @GetMapping("/veiculos")
    public List<Veiculo> getVeiculos() {
        return veiculoRepository.allVeiculos();
    }

    @DeleteMapping("/veiculo/{id}")
    public String deleteVeiculo(@PathVariable("id")int id){
            return veiculoRepository.deleteById(id);
    }

    @GetMapping("/carros")
    public List<Veiculo> getCarros() {
        return veiculoRepository.allCarros();
    }

    @GetMapping("/motos")
    public List<Veiculo> getMotos() {
        return veiculoRepository.allMotos();
    }

    @GetMapping("/fabricantes/{fabricante}")
    public List<Veiculo> getByFabricante(@PathVariable String fabricante) {
        return veiculoRepository.allByFabricante(fabricante);
    }

    @GetMapping("/anos/{ano}")
    public List<Veiculo> getByAno(@PathVariable int ano) {
        return veiculoRepository.allByAno(ano);
    }

    @GetMapping("/modelos/{modelo}")
    public List<Veiculo> getByModelo(@PathVariable String modelo) {
        return veiculoRepository.allByModelo(modelo);
    }



}
