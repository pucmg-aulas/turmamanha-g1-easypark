//package UnitaryTests;
//
//import Models.Vaga;
//import Models.VagaIdoso;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.*;
//
//import static org.junit.Assert.*;
//
//public class VagaTest {
//    private Vaga vaga;
//    private static final int ID_ESTACIONAMENTO = 1;
//
//    @Before
//    public void setUp() {
//        vaga = new VagaIdoso(ID_ESTACIONAMENTO);
//    }
//
//    @Test
//    public void testCriacaoVaga() {
//        assertNotNull("A vaga não deve ser nula após a criação", vaga);
//        assertTrue("A vaga deve estar desocupada ao ser criada", vaga.isDesocupada());
//        assertEquals("O ID da vaga deve ser maior que 0", true, vaga.getId() > 0);
//    }
//
//    @Test
//    public void testLiberarVaga() {
//        vaga.setStatus(false); // Marca a vaga como ocupada
//        assertFalse("A vaga deve estar ocupada", vaga.isDesocupada());
//
//        boolean resultado = vaga.liberarVaga();
//        assertTrue("A vaga deve ser liberada com sucesso", resultado);
//        assertTrue("A vaga deve estar desocupada após a liberação", vaga.isDesocupada());
//    }
//
//    @Test
//    public void testLiberarVagaJaLiberada() {
//        boolean resultado = vaga.liberarVaga();
//        assertTrue("A vaga deve ser liberada com sucesso", resultado);
//        // Tenta liberar novamente
//        resultado = vaga.liberarVaga();
//        assertFalse("A vaga já está desocupada, não deve ser liberada novamente", resultado);
//    }
//
//    @Test
//    public void testLerVagaPorId() {
//        // Cria um arquivo de teste
//        try {
//            FileWriter fileWriter = new FileWriter("./codigo/src/Archives/Vagas" + ID_ESTACIONAMENTO + ".txt");
//            BufferedWriter writer = new BufferedWriter(fileWriter);
//            writer.write("ID: " + vaga.getId() + "\n");
//            writer.write("Status: Desocupada\n");
//            writer.write("Tipo: Vaga\n");
//            writer.write("--------------------------------\n");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Testa a leitura da vaga
//        boolean encontrado = vaga.lerVagaPorId(vaga.getId());
//        assertTrue("A vaga deve ser encontrada pelo ID", encontrado);
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Vagas" + ID_ESTACIONAMENTO + ".txt").delete();
//    }
//
//    @Test
//    public void testLerVagaPorIdInexistente() {
//        boolean encontrado = vaga.lerVagaPorId(9999); // ID que não existe
//        assertTrue("A leitura deve ser bem-sucedida mesmo que a vaga não exista", encontrado);
//    }
//
//    @Test
//    public void testAtualizarStatusNoArquivo() {
//        // Simular a atualização do status da vaga
//        try {
//            FileWriter fileWriter = new FileWriter("./codigo/src/Archives/Vagas" + ID_ESTACIONAMENTO + ".txt");
//            BufferedWriter writer = new BufferedWriter(fileWriter);
//            writer.write("ID: " + vaga.getId() + "\n");
//            writer.write("Status: Desocupada\n");
//            writer.write("Tipo: Vaga\n");
//            writer.write("--------------------------------\n");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Testa a atualização do status
//        boolean resultado = vaga.atualizarStatusNoArquivo("Ocupada");
//        assertTrue("A atualização do status deve ser bem-sucedida", resultado);
//
//        // Verifica se o status foi atualizado
//        try (BufferedReader leitor = new BufferedReader(new FileReader("./codigo/src/Archives/Vagas" + ID_ESTACIONAMENTO + ".txt"))) {
//            String linha;
//            boolean statusAtualizado = false;
//
//            while ((linha = leitor.readLine()) != null) {
//                if (linha.contains("ID: " + vaga.getId())) {
//                    linha = leitor.readLine(); // Leitura da linha do status
//                    if (linha.contains("Ocupada")) {
//                        statusAtualizado = true;
//                    }
//                    break;
//                }
//            }
//            assertTrue("O status da vaga deve ser atualizado para 'Ocupada'", statusAtualizado);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Limpa o arquivo de teste após o teste
//        new File("./codigo/src/Archives/Vagas" + ID_ESTACIONAMENTO + ".txt").delete();
//    }
//}