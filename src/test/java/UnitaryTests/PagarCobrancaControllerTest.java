/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UnitaryTests;

import Controllers.PagarCobrancaController;
import Models.Cobranca;
import Models.Vaga;
import dao.CobrancabdDAO;
import dao.PagamentobdDAO;
import dao.VagabdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import static junit.framework.TestCase.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


public class PagarCobrancaControllerTest {

    private PagarCobrancaController controller;
    private VagabdDAO vagasDAO;
    private CobrancabdDAO cobrancasDAO;
    private PagamentobdDAO pagamentosDAO;
    private JDesktopPane desktopPane;

    @Before
    public void setUp() throws IOException, SQLException {
        // Inicializa DAOs
        vagasDAO = VagabdDAO.getInstance(1);
        cobrancasDAO = CobrancabdDAO.getInstance();
        pagamentosDAO = PagamentobdDAO.getInstance();

        

        // Adiciona vaga e cobrança para teste
        boolean cadastrarVaga = vagasDAO.cadastrarVaga(new Vaga(1, true, 50));

        // Inicializa controller
        desktopPane = new JDesktopPane();
        controller = new PagarCobrancaController(desktopPane, 1, LocalDateTime.now());
    }

    @Test
    public void testCarregarVagasOcupadas() throws SQLException, IOException {
        controller.carregarVagasOcupadas();
        
        DefaultTableModel model = (DefaultTableModel) controller.view.getVagasTable().getModel();
        
        // Verifica se há uma vaga ocupada carregada
        assertEquals(1, model.getRowCount(), "Deve haver uma vaga ocupada carregada");
        
    }

    @Test
    public void testMostrarValor() throws Exception {
        // Simula o cálculo de valor
        double valor = controller.mostrarValor();
        
        // Verifica o cálculo (tempo de 120 minutos / 15 min * 5 reais por fração)
        assertEquals1(40.0, valor, 0.01, "O valor deve ser R$ 40,00");
    }

    @Test
    public void testConfirmarPagamento() throws Exception {
        // Executa pagamento
        controller.carregarVagasOcupadas();
        controller.view.getVagasTable().setRowSelectionInterval(0, 0); // Seleciona a primeira linha
        controller.confirmarPagamento();

        // Verifica se a cobrança foi removida e a vaga liberada
        assertNull(cobrancasDAO.getCobranca(1), "A cobrança deve ser removida");

    }

    private void assertEquals1(double d, double valor, double d0, String o_valor_deve_ser_R$_4000) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

