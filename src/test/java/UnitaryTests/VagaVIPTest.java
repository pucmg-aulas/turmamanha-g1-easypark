package UnitaryTests;

import Models.VagaVIP;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VagaVIPTest {

    @Test
    void testCalculoValor() throws Exception {
        VagaVIP vagaVIP = new VagaVIP(1, 1);
        double valorOriginal = 100.0;
        double valorEsperado = valorOriginal * 1.20;
        assertEquals(valorEsperado, vagaVIP.calculoValor(valorOriginal), 0.01);
    }

    @Test
    void testGetTipo() throws Exception {
        VagaVIP vagaVIP = new VagaVIP(1, 1);
        assertEquals("VIP", vagaVIP.getTipo());
    }

    @Test
    void testTarifaBaseComAumento() throws Exception {
        VagaVIP vagaVIP = new VagaVIP(1, 1);
        double tarifaBaseEsperada = 10.0 * 1.20; // Tarifa padrão * AUMENTO_VIP
        assertEquals(tarifaBaseEsperada, vagaVIP.getTarifaBase(), 0.01);
    }

    @Test
    void testConstrutorComStatus() throws Exception {
        VagaVIP vagaVIP = new VagaVIP(1, false, 2);
        assertFalse(vagaVIP.getStatus());
        assertEquals(2, vagaVIP.getId());
    }

    @Test
    void testGetAumentoVip() {
        assertEquals(1.20, VagaVIP.getAumentoVip(), 0.01);
    }
}
