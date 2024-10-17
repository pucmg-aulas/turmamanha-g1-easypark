//package UnitaryTests;
//
//import Models.Cliente;
//import Models.Veiculo;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class VeiculoTest {
//    private Veiculo veiculo;
//    private static final String PLACA = "ABC1234";
//    private static final String MODELO = "Fusca";
//    private static final String CPF = "123.456.789-00";
//    private static final String NOME_CLIENTE = "João Silva";
//
//    @Before
//    public void setUp() {
//        Cliente cliente = new Cliente(NOME_CLIENTE, CPF);
//        veiculo = new Veiculo(PLACA, cliente, MODELO);
//    }
//
//    @Test
//    public void testCriacaoVeiculo() {
//        assertNotNull("O veículo não deve ser nulo após a criação", veiculo);
//        assertEquals("A placa deve ser a mesma informada", PLACA, veiculo.getPlaca());
//        assertEquals("O modelo deve ser o mesmo informado", MODELO, veiculo.getModelo());
//        assertEquals("O cliente deve ser o mesmo informado", NOME_CLIENTE, veiculo.getCliente().getNome());
//    }
//
//    @Test
//    public void testGravarEmArquivo() {
//        // Testa a gravação do veículo em arquivo
//        boolean resultado = veiculo.gravarEmArquivo();
//        assertTrue("A gravação do veículo em arquivo deve ser bem-sucedida", resultado);
//
//        // Verifica se o arquivo foi criado e contém os dados corretos
//        try (BufferedReader leitor = new BufferedReader(new FileReader("./codigo/src/Archives/Veiculos.txt"))) {
//            String linha;
//            boolean veiculoEncontrado = false;
//
//            while ((linha = leitor.readLine()) != null) {
//                if (linha.contains("Placa: " + PLACA)) {
//                    veiculoEncontrado = true;
//                    break;
//                }
//            }
//
//            assertTrue("O veículo deve ser encontrado no arquivo", veiculoEncontrado);
//        } catch (IOException e) {
//            e.printStackTrace();
//            fail("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
//        }
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Veiculos.txt").delete();
//    }
//
//    @Test
//    public void testLerVeiculoPorPlaca() {
//        veiculo.gravarEmArquivo(); // Grava o veículo para poder ler
//
//        boolean resultado = veiculo.lerVeiculoPorPlaca(PLACA);
//        assertTrue("A leitura do veículo deve ser bem-sucedida", resultado);
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Veiculos.txt").delete();
//    }
//
//    @Test
//    public void testLerVeiculoPorPlacaInexistente() {
//        boolean resultado = veiculo.lerVeiculoPorPlaca("ZZZ9999");
//        assertTrue("A leitura deve ser bem-sucedida mesmo que o veículo não exista", resultado);
//    }
//
//    @Test
//    public void testLerVeiculosDoArquivo() {
//        veiculo.gravarEmArquivo(); // Grava o veículo para poder ler
//
//        List<String> veiculos = Veiculo.lerVeiculosDoArquivo();
//        assertFalse("A lista de veículos não deve estar vazia", veiculos.isEmpty());
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Veiculos.txt").delete();
//    }
//
//    @Test
//    public void testExibirVeiculosPorCpf() {
//        veiculo.gravarEmArquivo(); // Grava o veículo para poder exibir
//
//        // Redireciona a saída padrão para capturar a saída do console
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        Veiculo.exibirVeiculosPorCpf(CPF);
//        String output = outContent.toString();
//
//        assertTrue("A saída deve conter a placa do veículo", output.contains(PLACA));
//        assertTrue("A saída deve conter o modelo do veículo", output.contains(MODELO));
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Veiculos.txt").delete();
//
//        // Restaura a saída padrão
//        System.setOut(System.out);
//    }
//
//    @Test
//    public void testGetVeiculoPorPlaca() {
//        veiculo.gravarEmArquivo(); // Grava o veículo para poder buscar
//
//        Veiculo veiculoBuscado = Veiculo.getVeiculoPorPlaca(PLACA);
//        assertNotNull("O veículo buscado não deve ser nulo", veiculoBuscado);
//        assertEquals("A placa do veículo buscado deve ser a mesma", PLACA, veiculoBuscado.getPlaca());
//        assertEquals("O modelo do veículo buscado deve ser o mesmo", MODELO, veiculoBuscado.getModelo());
//        assertEquals("O cliente do veículo buscado deve ser o mesmo", NOME_CLIENTE, veiculoBuscado.getCliente().getNome());
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Veiculos.txt").delete();
//    }
//}