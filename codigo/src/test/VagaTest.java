package test;

import Models.Cobranca;
import Models.Estacionamento;
import Models.Vaga;
import Models.Veiculo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VagaTest {

    @Test
    public void testOcuparVaga() {
        Vaga vaga = new Vaga();
        assertTrue(vaga.ocuparVaga());
        assertFalse(vaga.ocuparVaga()); // já ocupada
    }

    @Test
    public void testLiberarVaga() {
        Vaga vaga = new Vaga();
        vaga.ocuparVaga();
    }
}
