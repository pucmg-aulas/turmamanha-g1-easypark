package UnitaryTests;

import Models.VagaRegular;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VagaRegularTest {

    @Test
    void testCalculoValor() throws Exception {
        // Arrange
        VagaRegular vagaRegular = new VagaRegular(1, 1);
        double valorOriginal = 100.0;

        // Act
        double valorCalculado = vagaRegular.calculoValor(valorOriginal);

        // Assert
        assertEquals(valorOriginal, valorCalculado, 0.01, "O cálculo do valor para vaga regular deve retornar o valor original.");
    }

    @Test
    void testGetTipo() throws Exception {
        // Arrange
        VagaRegular vagaRegular = new VagaRegular(1, 1);

        // Act
        String tipo = vagaRegular.getTipo();

        // Assert
        assertEquals("Regular", tipo, "O tipo da vaga deve ser 'Regular'.");
    }

    @Test
    void testConstrutorComStatus() throws Exception {
        // Arrange
        VagaRegular vagaRegular = new VagaRegular(1, true, 2);

        // Act & Assert
        assertTrue(vagaRegular.getStatus(), "O status inicial da vaga não foi configurado corretamente.");
        assertEquals(2, vagaRegular.getId(), "O ID da vaga não foi configurado corretamente.");
    }

    @Test
    void testConstrutorSemParametros() throws Exception {
        // Arrange
        VagaRegular vagaRegular = new VagaRegular();

        // Act & Assert
        assertNotNull(vagaRegular, "O objeto deve ser inicializado.");
    }
}
