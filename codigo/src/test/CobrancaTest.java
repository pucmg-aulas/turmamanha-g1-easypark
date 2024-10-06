package test;

import Models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class CobrancaTest {

    private Estacionamento estacionamento;
    private Veiculo veiculo;

    @Before
    public void setup() {
        // Configuração de um estacionamento de teste
        estacionamento = new Estacionamento("Estacionamento Teste", "Rua Teste", "Bairro Teste", 100);
        Vaga vaga1 = new Vaga();
        Vaga vaga2 = new Vaga();
        estacionamento.adicionarVaga(vaga1);
        estacionamento.adicionarVaga(vaga2);

        // Veículo de teste
        veiculo = new Veiculo("ABC-1234", new Cliente("guilherme", "111"), "Tracker");
    }

    @Test
    public void testCriarCobrancaComVagaExistente() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        assertNotNull("A vaga não deve ser nula", cobranca.getVaga());
        assertEquals("A placa do veículo deve ser 'ABC-1234'", "ABC-1234", cobranca.getVeiculo().getPlaca());
    }

    @Test
    public void testCriarCobrancaComVagaInexistente() {
        Cobranca cobranca = new Cobranca(99, estacionamento, veiculo);
        assertNull("A vaga não deveria ser encontrada", cobranca.getVaga());
    }

    @Test
    public void testCalcularValorTotalRegular() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(30)); // 30 minutos de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        double valorEsperado = (30.0 / 15) * 4; // 2 frações de 15 minutos, 4 reais por fração
        assertEquals("O valor total deve ser 8 reais", valorEsperado, cobranca.getValorTotal(), 0.01);
    }

    @Test
    public void testCalcularValorTotalVIP() {
        Cobranca cobranca = new Cobranca(2, estacionamento, veiculo); // Vaga VIP
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(30)); // 30 minutos de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        double valorBase = (30.0 / 15) * 4; // 2 frações de 15 minutos
        double valorEsperado = valorBase * 1.20; // Vaga VIP, 20% mais caro
        assertEquals("O valor total para uma vaga VIP deve ser maior", valorEsperado, cobranca.getValorTotal(), 0.01);
    }

    @Test
    public void testValorTotalLimite() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(600)); // 10 horas de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        assertEquals("O valor total deve ser limitado a 50 reais", 50.0, cobranca.getValorTotal(), 0.01);
    }

    @Test
    public void testLiberarVagaAposPagamento() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.pagar(); // Libera a vaga após o pagamento
        assertFalse("A vaga deve estar liberada após o pagamento", !cobranca.getVaga().isDesocupada());
    }

    @Test
    public void testGravarEmArquivo() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(LocalTime.now().plusMinutes(30));
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();
        boolean sucesso = cobranca.gravarEmArquivo();
        assertTrue("A gravação em arquivo deve ser bem-sucedida", sucesso);
    }
}