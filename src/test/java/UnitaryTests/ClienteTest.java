package UnitaryTests;

import Models.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testGettersAndSetters() {
        // Criar um objeto Cliente
        Cliente cliente = new Cliente("João Silva", "123.456.789-00");

        // Testar os getters
        assertEquals("João Silva", cliente.getNome());
        assertEquals("123.456.789-00", cliente.getCpf());

        // Testar os setters
        cliente.setNome("Maria Oliveira");
        cliente.setCpf("987.654.321-00");

        assertEquals("Maria Oliveira", cliente.getNome());
        assertEquals("987.654.321-00", cliente.getCpf());
    }

    @Test
    void testConstructor() {
        // Criar um objeto Cliente e verificar o construtor
        Cliente cliente = new Cliente("Ana Costa", "111.222.333-44");

        assertNotNull(cliente);
        assertEquals("Ana Costa", cliente.getNome());
        assertEquals("111.222.333-44", cliente.getCpf());
    }
}
