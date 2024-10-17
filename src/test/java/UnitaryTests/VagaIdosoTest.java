//package UnitaryTests;
//
//import Models.VagaIdoso;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class VagaIdosoTest {
//    private static final int ID_ESTACIONAMENTO = 1;
//    private static final double TARIFA_BASE = 10.0;
//    private VagaIdoso vagaIdoso;
//
//
//    @Before
//    public void setUp() {
//        vagaIdoso = new VagaIdoso(ID_ESTACIONAMENTO);
//    }
//
//    @Test
//    public void testCriacaoVagaIdoso() {
//        assertNotNull("A vaga para idosos não deve ser nula após a criação", vagaIdoso);
//        assertTrue("A vaga deve ser desocupada ao ser criada", vagaIdoso.isDesocupada());
//        assertEquals("A tarifa base deve ser igual à tarifa inicial", 10.0, vagaIdoso.getTarifaBase(), 0.001);
//    }
//
//    @Test
//    public void testCalcularValorComDesconto() {
//        double valorEsperado = TARIFA_BASE * (1 - 0.15); // Verifique se 0.15 é a porcentagem correta.
//        double valorCalculado = vagaIdoso.calcularValor(TARIFA_BASE);
//
//        assertEquals("O valor da tarifa deve ser calculado com o desconto para idosos", valorEsperado, valorCalculado, 0.001);
//    }
//
//    @Test
//    public void testCalcularValorComTarifaBaseZero() {
//        double valorEsperado = 0.0;
//        double valorCalculado = vagaIdoso.calcularValor(0.0);
//
//        assertEquals("O valor da tarifa deve ser 0 quando a tarifa base é 0", valorEsperado, valorCalculado, 0.001);
//    }
//
//    @Test
//    public void testCalcularValorComTarifaBaseNegativa() {
//        double valorCalculado = vagaIdoso.calcularValor(-TARIFA_BASE);
//
//        assertEquals("O valor da tarifa deve ser negativo se a tarifa base é negativa", -TARIFA_BASE, valorCalculado, 0.001);
//    }
//}