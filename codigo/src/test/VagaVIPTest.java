package test;

import Models.VagaVIP;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VagaVIPTest {
    private static final int ID_ESTACIONAMENTO = 1;
    private static final double TARIFA_BASE = 10.0;
    private VagaVIP vagaVIP;

    @Before
    public void setUp() {
        vagaVIP = new VagaVIP(ID_ESTACIONAMENTO);
    }

    @Test
    public void testCriacaoVagaVIP() {
        assertNotNull("A vaga VIP não deve ser nula após a criação", vagaVIP);
        assertTrue("A vaga deve ser desocupada ao ser criada", vagaVIP.isDesocupada());
        assertEquals("A tarifa base deve ser igual à tarifa inicial", 10.0, vagaVIP.getTarifaBase(), 0.001);
    }

    @Test
    public void testCalcularValorComAumento() {
        double valorEsperado = TARIFA_BASE * (1 + VagaVIP.AUMENTO_VIP);
        double valorCalculado = vagaVIP.calcularValor(TARIFA_BASE);

        assertEquals("O valor da tarifa deve ser calculado com o aumento para VIP", valorEsperado, valorCalculado, 0.001);
    }

    @Test
    public void testCalcularValorComTarifaBaseZero() {
        double valorEsperado = 0.0;
        double valorCalculado = vagaVIP.calcularValor(0.0);

        assertEquals("O valor da tarifa deve ser 0 quando a tarifa base é 0", valorEsperado, valorCalculado, 0.001);
    }

    @Test
    public void testCalcularValorComTarifaBaseNegativa() {
        double valorEsperado = -TARIFA_BASE * (1 + VagaVIP.AUMENTO_VIP);
        double valorCalculado = vagaVIP.calcularValor(-TARIFA_BASE);

        assertEquals("O valor da tarifa deve ser negativo se a tarifa base é negativa", valorEsperado, valorCalculado, 0.001);
    }
}