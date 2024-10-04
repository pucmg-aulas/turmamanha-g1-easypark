package test;


import Models.Cobranca;
import Models.Estacionamento;
import Models.Vaga;
import Models.Veiculo;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class EstacionamentoTest
{

    @Test
    public void testCadastroEstacionamento()
    {
        Estacionamento est = new Estacionamento("Estacionamento A", "Rua 1", "Centro", 123);
        assertEquals("Estacionamento A", est.getNome());
        assertEquals("Rua 1", est.getRua());
    }

    @Test
    public void testAdicionarVaga()
    {
        Estacionamento est = new Estacionamento("Estacionamento B", "Rua 2", "Bairro", 456);
        Vaga vaga = new Vaga();
        est.adicionarVaga(vaga);
        assertTrue(est.getVagas().contains(vaga));
    }
}
