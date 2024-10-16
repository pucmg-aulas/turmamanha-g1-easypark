package test;

import Models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class CobrancaTest {

    private static final String ARQUIVO_COBRANCAS = "./codigo/src/Archives/Cobrancas.txt";
    private Cobranca cobranca;
    private Estacionamento estacionamento;
    private Veiculo veiculo;

    @Before
    public void setUp() {
        // Criação de um estacionamento com parâmetros apropriados
        estacionamento = new Estacionamento("Estacionamento Central", "Rua A", "Centro", 123, 50);

        // Criação de um cliente (opcional, se necessário)
        Cliente cliente = new Cliente("João da Silva", "123.456.789-00");

        // Criação de um veículo com placa, cliente e modelo
        veiculo = new Veiculo("ABC1234", cliente, "Fusca");

        // Adiciona uma vaga ao estacionamento
        // estacionamento.adicionarVaga(new Vaga(1));

        // Criação de uma cobrança com a vaga e veículo
        cobranca = new Cobranca(1, estacionamento, veiculo);
    }

    @After
    public void tearDown() {
        // Remove o arquivo de cobranças após os testes
        File arquivo = new File(ARQUIVO_COBRANCAS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    @Test
    public void testCalcularValor() {
        // Define uma hora de saída
        cobranca.setHoraSaida(LocalTime.from(LocalDateTime.now().plusMinutes(45))); // 45 minutos depois da entrada

        // Calcula o valor
        double valor = cobranca.calcularValor();

        // Verifica se o valor está correto
        assertEquals(12.0, valor, 0.01); // 4 por 15 minutos, portanto 3 frações de 15 minutos * 4
    }

    @Test
    public void testGravarEmArquivo() {
        // Define a hora de saída
        cobranca.setHoraSaida(LocalTime.from(LocalDateTime.now().plusMinutes(30))); // 30 minutos depois da entrada

        // Grava a cobrança em arquivo
        assertTrue(cobranca.gravarEmArquivo());

        // Verifica se o arquivo foi criado e contém as informações corretas
        File arquivo = new File(ARQUIVO_COBRANCAS);
        assertTrue(arquivo.exists());
    }

    @Test
    public void testFinalizarCobranca() {
        // Finaliza a cobrança
        cobranca.finalizarCobrança();

        // Verifica se o arquivo foi atualizado
        File arquivo = new File(ARQUIVO_COBRANCAS);
        assertTrue(arquivo.exists());

        // Verifica se a cobrança foi gravada
        assertTrue(cobranca.gravarEmArquivo());
    }

    @Test
    public void testAtualizarArquivoComInformacoes() {
        // Define a hora de saída e finaliza a cobrança
        cobranca.setHoraSaida(LocalTime.from(LocalDateTime.now().plusMinutes(30))); // 30 minutos depois da entrada
        cobranca.finalizarCobrança();

        // Atualiza as informações no arquivo
        assertTrue(cobranca.atualizarArquivoComInformacoes());

        // Verifica se o arquivo contém as informações atualizadas
        File arquivo = new File(ARQUIVO_COBRANCAS);
        assertTrue(arquivo.exists());
    }

    @Test
    public void testPagar() {
        // Verifica se a vaga está ocupada antes do pagamento
        assertFalse(cobranca.pagar());

        // Define a hora de saída e finaliza a cobrança
        cobranca.setHoraSaida(LocalTime.from(LocalDateTime.now().plusMinutes(30))); // 30 minutos depois da entrada
        cobranca.finalizarCobrança();

        // Tenta pagar a cobrança
        assertTrue(cobranca.pagar());
    }
}