package UnitaryTests;

import Models.Cliente;
import Models.Cobranca;
import Models.Veiculo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;

class CobrancaTest {

    private Cobranca cobranca;
    private Veiculo veiculo;
    private Cliente cliente;

    @BeforeEach
    void setUp() throws SQLException {
        cliente = new Cliente("João Silva", "123.456.789-00");
        veiculo = new Veiculo("ABC-1234", "Carro", cliente);
        cobranca = new Cobranca(1, 1, veiculo, LocalDateTime.now(), null, 0, 0);
    }

    @Test
    void testGetIdVaga() {
        assertEquals(1, cobranca.getIdVaga(), "O ID da vaga deve ser 1");
    }

    @Test
    void testSetIdVaga() {
        cobranca.setIdVaga(2);
        assertEquals(2, cobranca.getIdVaga(), "O ID da vaga deve ser atualizado para 2");
    }

    @Test
    void testGetVeiculo() {
        assertEquals(veiculo, cobranca.getVeiculo(), "O veículo deve ser igual ao instanciado no setup");
    }

    @Test
    void testSetVeiculo() {
        Cliente novoCliente = new Cliente("Maria Oliveira", "987.654.321-00");
        Veiculo novoVeiculo = new Veiculo("XYZ-5678", "Moto", novoCliente);
        cobranca.setVeiculo(novoVeiculo);
        assertEquals(novoVeiculo, cobranca.getVeiculo(), "O veículo deve ser atualizado para o novo veículo");
    }

    @Test
    void testGetHoraEntrada() {
        assertNotNull(cobranca.getHoraEntrada(), "A hora de entrada não deve ser nula");
    }

    @Test
    void testSetHoraEntrada() {
        LocalDateTime novaHoraEntrada = LocalDateTime.of(2024, 12, 1, 10, 30);
        cobranca.setHoraEntrada(novaHoraEntrada);
        assertEquals(novaHoraEntrada, cobranca.getHoraEntrada(), "A hora de entrada deve ser atualizada");
    }

    @Test
    void testSetHoraSaida() {
        LocalDateTime horaSaida = LocalDateTime.of(2024, 12, 1, 12, 30);
        cobranca.setHoraSaida(horaSaida);
        assertEquals(horaSaida, cobranca.getHoraSaida(), "A hora de saída deve ser atualizada");
    }

    @Test
    void testSetTempoTotal() {
        cobranca.setTempoTotal(120);
        assertEquals(120, cobranca.getTempoTotal(), "O tempo total deve ser 120 minutos");
    }

    @Test
    void testSetValorTotal() {
        cobranca.setValorTotal(35.0);
        assertEquals(35.0, cobranca.getValorTotal(), "O valor total deve ser 35.0");
    }

    @Test
    void testEquals() throws SQLException {
        Cobranca outraCobranca = new Cobranca(1, 1, veiculo, LocalDateTime.now(), null, 0, 0);
        assertTrue(cobranca.equals(outraCobranca), "As cobranças com o mesmo ID devem ser iguais");
    }

    @Test
    void testHashCode() {
        int expectedHashCode = Integer.hashCode(cobranca.getIdCobranca());
        assertEquals(expectedHashCode, cobranca.hashCode(), "O hashCode deve ser baseado no ID da cobrança");
    }
}
