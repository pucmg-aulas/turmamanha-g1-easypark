package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Models.Estacionamento;
import src.Models.Vaga;
import src.Models.Veiculo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

public class CobrancaTest {

    private Estacionamento estacionamento;
    private Veiculo veiculo;

    @BeforeEach
    public void setup() {
        // Configuração de um estacionamento de teste
        estacionamento = new Estacionamento("Estacionamento Teste", "Rua Teste", "Bairro Teste", 100);
        Vaga vaga1 = new Vaga(Vaga.TipoVaga.REGULAR);
        Vaga vaga2 = new Vaga(Vaga.TipoVaga.VIP);
        estacionamento.adicionarVaga(vaga1);
        estacionamento.adicionarVaga(vaga2);

        // Veículo de teste
        veiculo = new Veiculo("ABC-1234");
    }

    @Test
    public void testCriarCobrancaComVagaExistente() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        assertNotNull(cobranca.getVaga());
        assertEquals("ABC-1234", cobranca.getVeiculo().getPlaca());
    }

    @Test
    public void testCriarCobrancaComVagaInexistente() {
        Cobranca cobranca = new Cobranca(99, estacionamento, veiculo);
        assertNull(cobranca.getVaga(), "A vaga não deveria ser encontrada.");
    }

    @Test
    public void testCalcularValorTotalRegular() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(30)); // 30 minutos de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        double valorEsperado = (30.0 / 15) * 4; // 2 frações de 15 minutos, 4 reais por fração
        assertEquals(valorEsperado, cobranca.getValorTotal(), 0.01);
    }

    @Test
    public void testCalcularValorTotalVIP() {
        Cobranca cobranca = new Cobranca(2, estacionamento, veiculo); // Vaga VIP
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(30)); // 30 minutos de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        double valorBase = (30.0 / 15) * 4; // 2 frações de 15 minutos
        double valorEsperado = valorBase * 1.20; // Vaga VIP, 20% mais caro
        assertEquals(valorEsperado, cobranca.getValorTotal(), 0.01);
    }

    @Test
    public void testValorTotalLimite() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(cobranca.getHoraEntrada().plusMinutes(600)); // 10 horas de estadia
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();

        assertEquals(50.0, cobranca.getValorTotal(), 0.01); // Limite de R$50.00
    }

    @Test
    public void testLiberarVagaAposPagamento() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.pagar(); // Libera a vaga após o pagamento
        assertFalse(cobranca.getVaga().isOcupada()); // Verifica se a vaga foi liberada
    }

    @Test
    public void testGravarEmArquivo() {
        Cobranca cobranca = new Cobranca(1, estacionamento, veiculo);
        cobranca.setHoraSaida(LocalTime.now().plusMinutes(30));
        cobranca.calcularTempoFinal();
        cobranca.calcularValorTotal();
        boolean sucesso = cobranca.gravarEmArquivo();
        assertTrue(sucesso, "A gravação em arquivo deve ser bem-sucedida.");
    }
}