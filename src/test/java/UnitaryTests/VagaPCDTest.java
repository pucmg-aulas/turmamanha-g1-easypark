package UnitaryTests;

import Models.VagaPCD;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VagaPCDTest {

    @Test
    void testCalculoValor() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD(1, 1);
        double valorOriginal = 100.0;
        double descontoEsperado = valorOriginal * 0.87;

        // Act
        double valorCalculado = vagaPCD.calculoValor(valorOriginal);

        // Assert
        assertEquals(descontoEsperado, valorCalculado, 0.01, "O cálculo do valor com desconto está incorreto.");
    }

    @Test
    void testGetTipo() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD(1, 1);

        // Act
        String tipo = vagaPCD.getTipo();

        // Assert
        assertEquals("PCD", tipo, "O tipo da vaga deve ser 'PCD'.");
    }

    @Test
    void testTarifaBaseComDesconto() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD(1, 1);
        double tarifaBaseOriginal = 100.0; // Simule uma tarifa base original, conforme implementação.
        vagaPCD.setTarifaBase(tarifaBaseOriginal); // Ajuste se o método setTarifaBase estiver disponível.
        double tarifaEsperada = tarifaBaseOriginal * 0.87;

        // Act
        double tarifaAtual = vagaPCD.getTarifaBase();

        // Assert
        assertEquals(tarifaEsperada, tarifaAtual, 0.01, "A tarifa base deve aplicar o desconto corretamente.");
    }

    @Test
    void testConstrutorComStatus() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD(1, true, 2);

        // Act & Assert
        assertTrue(vagaPCD.getStatus(), "O status inicial da vaga não foi configurado corretamente.");
        assertEquals(2, vagaPCD.getId(), "O ID da vaga não foi configurado corretamente.");
    }

    @Test
    void testConstrutorSemParametros() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD();

        // Act & Assert
        assertNotNull(vagaPCD, "O objeto deve ser inicializado.");
    }

    @Test
    void testGetDescontoPCD() throws Exception {
        // Arrange
        VagaPCD vagaPCD = new VagaPCD(1, 1);

        // Act
        double desconto = vagaPCD.getDESCONTO_PCD();

        // Assert
        assertEquals(0.87, desconto, 0.01, "O valor do desconto PCD está incorreto.");
    }
}
