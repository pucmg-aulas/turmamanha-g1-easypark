package UnitaryTests;

import Models.Estacionamento;
import Models.Vaga;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EstacionamentoTest {

    private Estacionamento estacionamento;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento(1, "Estacionamento Central", "Rua Principal", "Centro", 123, 50);
    }

    @Test
    void testGetId() {
        assertEquals(1, estacionamento.getId(), "O ID do estacionamento deve ser 1");
    }

    @Test
    void testSetId() {
        estacionamento.setId(2);
        assertEquals(2, estacionamento.getId(), "O ID do estacionamento deve ser atualizado para 2");
    }

    @Test
    void testGetNome() {
        assertEquals("Estacionamento Central", estacionamento.getNome(), "O nome do estacionamento deve ser 'Estacionamento Central'");
    }

    @Test
    void testSetNome() {
        estacionamento.setNome("Estacionamento Novo");
        assertEquals("Estacionamento Novo", estacionamento.getNome(), "O nome do estacionamento deve ser atualizado para 'Estacionamento Novo'");
    }

    @Test
    void testGetRua() {
        assertEquals("Rua Principal", estacionamento.getRua(), "A rua do estacionamento deve ser 'Rua Principal'");
    }

    @Test
    void testSetRua() {
        estacionamento.setRua("Rua Secundária");
        assertEquals("Rua Secundária", estacionamento.getRua(), "A rua do estacionamento deve ser atualizada para 'Rua Secundária'");
    }

    @Test
    void testGetNumero() {
        assertEquals(123, estacionamento.getNumero(), "O número do estacionamento deve ser 123");
    }

    @Test
    void testSetNumero() {
        estacionamento.setNumero(456);
        assertEquals(456, estacionamento.getNumero(), "O número do estacionamento deve ser atualizado para 456");
    }

    @Test
    void testGetBairro() {
        assertEquals("Centro", estacionamento.getBairro(), "O bairro do estacionamento deve ser 'Centro'");
    }

    @Test
    void testSetBairro() {
        estacionamento.setBairro("Zona Norte");
        assertEquals("Zona Norte", estacionamento.getBairro(), "O bairro do estacionamento deve ser atualizado para 'Zona Norte'");
    }

    @Test
    void testGetQntdVagas() {
        assertEquals(50, estacionamento.getQntdVagas(), "A quantidade de vagas deve ser 50");
    }

    @Test
    void testGetVagas() {
        List<Vaga> vagas = new ArrayList<>();
        estacionamento.setVagas(vagas);
        assertEquals(vagas, estacionamento.getVagas(), "A lista de vagas deve ser igual à definida");
    }

    @Test
    void testSetVagas() {
        List<Vaga> novasVagas = new ArrayList<>();
        estacionamento.setVagas(novasVagas);
        assertEquals(novasVagas, estacionamento.getVagas(), "A lista de vagas deve ser atualizada");
    }
}
