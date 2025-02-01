package com.example.DesafioTecnicoProgramador.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.DesafioTecnicoProgramador.dao.VeiculoRepository;
import com.example.DesafioTecnicoProgramador.entity.Carro;
import com.example.DesafioTecnicoProgramador.entity.Moto;
import com.example.DesafioTecnicoProgramador.entity.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class VeiculoControllerTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoController veiculoController;

    private Carro carro;
    private Moto moto;



    @BeforeEach
    void setUp() {
        carro = new Carro(1, "Modelo A", "Fabricante A", 2022, BigDecimal.valueOf(50000), "CARRO", 4, "Gasolina");
        moto = new Moto(2, "Modelo B", "Fabricante B", 2021, BigDecimal.valueOf(15000), "MOTO", 250);
    }

    @Test
    void testAddCarro() {
        when(veiculoRepository.saveVeiculo(carro)).thenReturn(carro);
        Veiculo result = veiculoController.addVeiculo(carro);
        assertNotNull(result);
        assertEquals("CARRO", result.getTipo());
        verify(veiculoRepository, times(1)).saveVeiculo(carro);
    }

    @Test
    void testAddMoto() {
        when(veiculoRepository.saveVeiculo(moto)).thenReturn(moto);
        Veiculo result = veiculoController.addVeiculo(moto);
        assertNotNull(result);
        assertEquals("MOTO", result.getTipo());
        verify(veiculoRepository, times(1)).saveVeiculo(moto);
    }

    @Test
    void testGetVeiculo() {
        when(veiculoRepository.getById(1)).thenReturn(carro);
        Veiculo result = veiculoController.getVeiculo(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetVeiculos() {
        List<Veiculo> veiculos = Arrays.asList(carro, moto);
        when(veiculoRepository.allVeiculos()).thenReturn(veiculos);
        List<Veiculo> result = veiculoController.getVeiculos();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteVeiculo() {
        when(veiculoRepository.deleteById(1)).thenReturn("Veículo removido");
        String result = veiculoController.deleteVeiculo(1);
        assertEquals("Veículo removido", result);
    }


    @Test
    void updateVeiculo() {
        when(veiculoRepository.updateVeiculo(carro)).thenReturn(carro);
        Veiculo resultado = veiculoController.updateVeiculo(carro);
        assertEquals(carro, resultado);
        verify(veiculoRepository, times(1)).updateVeiculo(carro);
    }

    @Test
    void getCarros() {
        when(veiculoRepository.allCarros()).thenReturn(Arrays.asList(carro));
        List<Veiculo> resultado = veiculoController.getCarros();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Fabricante A", resultado.get(0).getFabricante());
    }

    @Test
    void getMotos() {
        when(veiculoRepository.allMotos()).thenReturn(Arrays.asList(moto));
        List<Veiculo> resultado = veiculoController.getMotos();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Fabricante B", resultado.get(0).getFabricante());
    }

    @Test
    void getByFabricante() {
        when(veiculoRepository.allByFabricante("Fabricante A")).thenReturn(Arrays.asList(carro));
        List<Veiculo> resultado = veiculoController.getByFabricante("Fabricante A");
        assertFalse(resultado.isEmpty());
        assertEquals("Fabricante A", resultado.get(0).getFabricante());
    }

    @Test
    void getByAno() {
        when(veiculoRepository.allByAno(2022)).thenReturn(Arrays.asList(carro));
        List<Veiculo> resultado = veiculoController.getByAno(2022);
        assertFalse(resultado.isEmpty());
        assertEquals(2022, resultado.get(0).getAno());
    }

    @Test
    void getByModelo() {
        when(veiculoRepository.allByModelo("Modelo A")).thenReturn(Arrays.asList(carro));
        List<Veiculo> resultado = veiculoController.getByModelo("Modelo A");
        assertFalse(resultado.isEmpty());
        assertEquals("Modelo A", resultado.get(0).getModelo());
    }

}