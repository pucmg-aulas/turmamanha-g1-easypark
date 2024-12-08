package UnitaryTests;

import Controllers.GerarCobrancaController;
import Exceptions.VagaIndisponivelException;
import Models.*;
import dao.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JDesktopPane;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertThrows;


public class GerarCobrancaControllerTest {

    private GerarCobrancaController controller;
    private VagabdDAO vagaDAO;
    private CobrancabdDAO cobrancaDAO;
    private VeiculoDAO veiculoDAO;
    private ClientebdDAO clienteDAO;

    private JDesktopPane desktopPane;

    @Before
    public void setUp() throws IOException, SQLException {
        // Inicialização dos DAOs com um banco de dados embutido
        vagaDAO = VagabdDAO.getInstance(1);
        cobrancaDAO = CobrancabdDAO.getInstance();
        veiculoDAO = VeiculoDAO.getInstance();
        clienteDAO = ClientebdDAO.getInstance();

        desktopPane = new JDesktopPane();
        controller = new GerarCobrancaController(desktopPane, 1);

 
        // Inserir dados iniciais
        vagaDAO.cadastrarVaga(new Vaga(1, true, 50));
        clienteDAO.cadastrarCliente(new Cliente("123456789", "Cliente Teste"));
    }

    @Test
    public void testCarregarVagasDisponiveis() throws SQLException {
        // Testar se as vagas disponíveis foram carregadas corretamente
        controller.carregarVagasDisponiveis();
        assertEquals(1, controller.view.getVagasTable().getRowCount(), "Deve carregar uma vaga disponível.");
    }

    @Test
    public void testGetAtributosComCamposValidos() throws IOException, SQLException {
        controller.view.getPlaca().setText("ABC1234");
        controller.carregarVagasDisponiveis();

        // Seleciona a primeira vaga
        controller.view.getVagasTable().setRowSelectionInterval(0, 0);

        Cobranca cobranca = controller.getAtributos();

        assertNotNull(cobranca, "Cobrança deve ser criada com os campos válidos.");
        assertEquals("ABC1234", cobranca.getVeiculo().getPlaca(), "Placa do veículo deve ser 'ABC1234'.");
    }

    @Test
    public void testCreateCobrancaComVagaDisponivel() throws FileNotFoundException, IOException, SQLException, VagaIndisponivelException {
        // Configurar cenário válido
        controller.view.getPlaca().setText("XYZ1234");
        controller.carregarVagasDisponiveis();
        controller.view.getVagasTable().setRowSelectionInterval(0, 0);

        // Executar a criação da cobrança
        controller.createCobranca();

        List<Cobranca> cobrancas = cobrancaDAO.lerCobrancas();
        assertEquals(1, cobrancas.size(), "Deve existir uma cobrança criada.");
        assertFalse(vagaDAO.getVagaPorId(1).getStatus(), "A vaga deve estar ocupada após cobrança.");
    }

    @Test
    public void testCreateCobrancaComVagaOcupada() throws FileNotFoundException, IOException, SQLException, VagaIndisponivelException {
        // Configurar cenário com a vaga já ocupada
        controller.view.getPlaca().setText("XYZ1234");
        vagaDAO.ocuparVaga(1);

        controller.carregarVagasDisponiveis();
        controller.view.getVagasTable().setRowSelectionInterval(0, 0);
    }

    @Test
    public void testValidarCamposComDadosInvalidos() {
        boolean resultado = controller.validarCampos("", "");
        assertFalse(resultado, "Campos vazios devem ser inválidos.");
    }

  
    private void assertEquals(int i, int rowCount, String deve_carregar_uma_vaga_disponível) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String abC1234, String placa, String placa_do_veículo_deve_ser_ABC1234) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String vaga_ocupada, String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
