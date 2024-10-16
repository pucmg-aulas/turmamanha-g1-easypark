//package test;
//
//import Models.Estacionamento;
//import Models.Vaga;
//import Models.VagaIdoso;
//import Models.VagaPCD;
//import Models.VagaVIP;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class EstacionamentoTest {
//
//    private Estacionamento estacionamento;
//
//    @Before
//    public void setUp() {
//        estacionamento = new Estacionamento("Estacionamento A", "Rua 1", "Centro", 123, 50);
//    }
//
//    @Test
//    public void testCadastroEstacionamento() {
//        assertEquals("Estacionamento A", estacionamento.getNome());
//        assertEquals("Rua 1", estacionamento.getRua());
//        assertEquals("Centro", estacionamento.getBairro());
//        assertEquals(123, estacionamento.getNumero());
//    }
//
//   // @Test
////    public void testAdicionarVaga() {
////        Vaga vaga = new Vaga(1);
////        estacionamento.adicionarVaga(vaga);
////        assertTrue(estacionamento.getVagas().contains(vaga));
////    }
//
// //   @Test
//    public void testReservarVagaPorId() {
//        Vaga vaga = new Vaga(3);
//        estacionamento.adicionarVaga(vaga);
//        boolean resultado = estacionamento.reservarVagaPorId(3);
//        assertTrue(resultado);
//        assertFalse(vaga.isDesocupada()); // Verifica se a vaga foi marcada como ocupada
//    }
//
//   // @Test
//    public void testGravarEstacionamentosEmArquivo() {
//        boolean resultado = estacionamento.gravarEstacionamentosEmArquivo();
//        assertTrue(resultado);
//        // Verifica se o arquivo foi criado
//        File arquivo = new File("./codigo/src/Archives/Estacionamentos.txt");
//        assertTrue(arquivo.exists());
//    }
//
//   // @Test
//    public void testLerEstacionamentosDoArquivo() {
//        List<String> estacionamentos = Estacionamento.lerEstacionamentosDoArquivo();
//        assertNotNull(estacionamentos);
//        assertFalse(estacionamentos.isEmpty());
//    }
//
//  //  @Test
//    public void testGetVagasDisponiveis() {
//        Vaga vaga1 = new Vaga(4);
//        Vaga vaga2 = new VagaPCD(5);
//        estacionamento.adicionarVaga(vaga1);
//        estacionamento.adicionarVaga(vaga2);
//
//        List<Vaga> vagasDisponiveis = estacionamento.getVagasDisponiveis();
//        assertTrue(vagasDisponiveis.size() >= 2); // As duas vagas devem ser contadas como disponíveis inicialmente
//    }
//
//   // @Test
//    public void testGetVagasOcupadas() {
//        Vaga vaga1 = new Vaga(6);
//        estacionamento.adicionarVaga(vaga1);
//        estacionamento.reservarVagaPorId(6);
//
//        List<Vaga> vagasOcupadas = estacionamento.getVagasOcupadas();
//        assertEquals(1, vagasOcupadas.size()); // Deve conter uma vaga ocupada
//        assertTrue(vagasOcupadas.get(0).isDesocupada() == false); // A vaga ocupada não deve ser desocupada
//    }
//
//   // @Test
//    public void testSalvarVagaEmArquivo() {
//        Vaga vaga = new Vaga(7);
//        estacionamento.adicionarVaga(vaga);
//        // Verifica se o arquivo da vaga foi criado
//        File arquivoVaga = new File("./codigo/src/Archives/Vagas" + estacionamento.getId() + ".txt");
//        assertTrue(arquivoVaga.exists());
//    }
//}