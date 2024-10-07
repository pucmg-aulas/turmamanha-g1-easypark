package test;

import Models.Cliente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ClienteTest {

    private static final String ARQUIVO_CLIENTES = "./codigo/src/Archives/Clientes.txt";
    private Cliente cliente;

    @Before
    public void setUp() {
        // Configura um cliente para os testes
        cliente = new Cliente("João da Silva", "123.456.789-00");
    }

    @After
    public void tearDown() {
        // Remove o arquivo de clientes após os testes
        File arquivo = new File(ARQUIVO_CLIENTES);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    @Test
    public void testGravarEmArquivo() {
        // Tenta gravar o cliente em um arquivo
        assertTrue(cliente.gravarEmArquivo());

        // Verifica se o arquivo foi criado e contém as informações corretas
        File arquivo = new File(ARQUIVO_CLIENTES);
        assertTrue(arquivo.exists());
        List<String> clientes = Cliente.lerClientesDoArquivo();
        assertEquals(1, clientes.size());
        assertTrue(clientes.get(0).contains("CPF: " + cliente.getCpf()));
        assertTrue(clientes.get(0).contains("Nome: " + cliente.getNome()));
    }

    @Test
    public void testLerClientesDoArquivo() {
        // Grava um cliente no arquivo
        cliente.gravarEmArquivo();

        // Lê os clientes do arquivo
        List<String> clientes = Cliente.lerClientesDoArquivo();
        assertEquals(1, clientes.size());
        assertTrue(clientes.get(0).contains("CPF: " + cliente.getCpf()));
        assertTrue(clientes.get(0).contains("Nome: " + cliente.getNome()));
    }

    @Test
    public void testGravarEmArquivo_DiretorioNaoExistente() {
        // Muda o caminho do arquivo para um diretório que não existe
        File diretorioInexistente = new File("./codigo/src/Archives/Inexistente/Clientes.txt");
        Cliente clienteInexistente = new Cliente("Maria Souza", "987.654.321-00");

        // Tenta gravar o cliente, o método deve criar o diretório
        assertTrue(clienteInexistente.gravarEmArquivo());

        // Verifica se o arquivo foi criado
        assertTrue(diretorioInexistente.exists());
        assertTrue(diretorioInexistente.exists());

        // Limpa após o teste
        diretorioInexistente.delete();
    }
}