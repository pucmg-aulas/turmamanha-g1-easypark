package test;

import Models.VagaPCD;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VagaPCDTest {
    private static final int ID_ESTACIONAMENTO = 1;
    private static final double TARIFA_BASE = 10.0;
    private VagaPCD vagaPCD;

    @Before
    public void setUp() {
        vagaPCD = new VagaPCD(ID_ESTACIONAMENTO);
    }

    @Test
    public void testCriacaoVagaPCD() {
        assertNotNull("A vaga PCD não deve ser nula após a criação", vagaPCD);
        assertTrue("A vaga deve ser desocupada ao ser criada", vagaPCD.isDesocupada());
        assertEquals("A tarifa base deve ser igual à tarifa inicial", 10.0, vagaPCD.getTarifaBase(), 0.001);
    }

    @Test
    public void testCalcularValorComDesconto() {
        double valorEsperado = TARIFA_BASE * (1 - VagaPCD.DESCONTO_PCD);
        double valorCalculado = vagaPCD.calcularValor(TARIFA_BASE);

        assertEquals("O valor da tarifa deve ser calculado com o desconto para PCD", valorEsperado, valorCalculado, 0.001);
    }

    @Test
    public void testCalcularValorComTarifaBaseZero() {
        double valorEsperado = 0.0;
        double valorCalculado = vagaPCD.calcularValor(0.0);

        assertEquals("O valor da tarifa deve ser 0 quando a tarifa base é 0", valorEsperado, valorCalculado, 0.001);
    }

    @Test
    public void testCalcularValorComTarifaBaseNegativa() {
        double valorCalculado = vagaPCD.calcularValor(-TARIFA_BASE);

        assertEquals("O valor da tarifa deve ser negativo se a tarifa base é negativa", -TARIFA_BASE, valorCalculado, 0.001);
    }
}