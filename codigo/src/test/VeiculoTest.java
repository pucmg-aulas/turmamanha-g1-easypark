package test;

import Models.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VeiculoTest {

    @Test
    public void testCadastroVeiculo() {
        Veiculo veiculo = new Veiculo("ABC-1234", new Cliente("guilherme", "111"), "Tracker");
        assertEquals("ABC-1234", veiculo.getPlaca());
}
}
