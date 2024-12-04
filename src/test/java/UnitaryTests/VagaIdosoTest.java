package UnitaryTests;

import Models.VagaIdoso;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VagaIdosoTest {

    @Test
    void testCalculoValor() throws Exception {
        // Arrange
        VagaIdoso vagaIdoso = new VagaIdoso(1, 1);
        double valorOriginal = 100.0;
        double descontoEsperado = valorOriginal * 0.85;

        // Act
        double valorCalculado = vagaIdoso.calculoValor(valorOriginal);

        // Assert
        assertEquals(descontoEsperado, valorCalculado, 0.01, "O cálculo do valor com desconto está incorreto.");
    }

    @Test
    void testGetTipo() throws Exception {
        // Arrange
        VagaIdoso vagaIdoso = new VagaIdoso(1, 1);

        // Act
        String tipo = vagaIdoso.getTipo();

        // Assert
        assertEquals("Idoso", tipo, "O tipo da vaga deve ser 'Idoso'.");
    }

    @Test
    void testTarifaBaseComDesconto() throws Exception {
        // Arrange
        VagaIdoso vagaIdoso = new VagaIdoso(1, 1);
        double tarifaBaseOriginal = 100.0; // Simule uma tarifa base original, conforme implementação.
        vagaIdoso.setTarifaBase(tarifaBaseOriginal); // Ajuste se o método setTarifaBase estiver disponível.
        double tarifaEsperada = tarifaBaseOriginal * 0.85;

        // Act
        double tarifaAtual = vagaIdoso.getTarifaBase();

        // Assert
        assertEquals(tarifaEsperada, tarifaAtual, 0.01, "A tarifa base deve aplicar o desconto corretamente.");
    }

    @Test
    void testConstrutorComStatus() throws Exception {
        // Arrange
        VagaIdoso vagaIdoso = new VagaIdoso(1, true, 2);

        // Act & Assert
        assertTrue(vagaIdoso.getStatus(), "O status inicial da vaga não foi configurado corretamente.");
        assertEquals(2, vagaIdoso.getId(), "O ID da vaga não foi configurado corretamente.");
    }

    @Test
    void testConstrutorSemParametros() throws Exception {
        // Arrange
        VagaIdoso vagaIdoso = new VagaIdoso();

        // Act & Assert
        assertNotNull(vagaIdoso, "O objeto deve ser inicializado.");
    }
}
