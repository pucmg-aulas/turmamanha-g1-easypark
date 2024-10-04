package test;

import Models.Cobranca;
import Models.Estacionamento;
import Models.Vaga;
import Models.Veiculo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VeiculoTest {

    @Test
    public void testCadastroVeiculo() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        assertEquals("ABC-1234", veiculo.getPlaca());
}
}
