package UnitaryTests;

import Models.Cliente;
import Models.Veiculo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VeiculoTest {

    private Veiculo veiculo;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("João Silva", "123.456.789-00");
        veiculo = new Veiculo("ABC-1234", "Carro", cliente);
    }

    @Test
    void testGetPlaca() {
        assertEquals("ABC-1234", veiculo.getPlaca(), "A placa deve ser 'ABC-1234'");
    }

    @Test
    void testSetPlaca() {
        veiculo.setPlaca("XYZ-5678");
        assertEquals("XYZ-5678", veiculo.getPlaca(), "A placa deve ser atualizada para 'XYZ-5678'");
    }

    @Test
    void testGetModelo() {
        assertEquals("Carro", veiculo.getModelo(), "O modelo deve ser 'Carro'");
    }

    @Test
    void testSetModelo() {
        veiculo.setModelo("Moto");
        assertEquals("Moto", veiculo.getModelo(), "O modelo deve ser atualizado para 'Moto'");
    }

    @Test
    void testGetCliente() {
        assertEquals(cliente, veiculo.getCliente(), "O cliente deve ser igual ao instanciado no setup");
    }

    @Test
    void testSetCliente() {
        Cliente novoCliente = new Cliente("Maria Oliveira", "987.654.321-00");
        veiculo.setCliente(novoCliente);
        assertEquals(novoCliente, veiculo.getCliente(), "O cliente deve ser atualizado para o novo cliente");
    }
}
