
package UnitaryTests;

import Controllers.ListarVagasController;
import Models.Vaga;
import dao.VagabdDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertEquals;


public class ListarVagasControllerTest {

    private ListarVagasController controller;
    private VagabdDAO vagaDAO;
    private JDesktopPane desktopPane;

    @Before
    public void setUp() throws IOException, SQLException {
        // Configura o DAO e o banco de dados
        vagaDAO = VagabdDAO.getInstance(1);
      

        // Adiciona dados iniciais
        boolean cadastrarVaga = vagaDAO.cadastrarVaga(new Vaga(1, true, 50));
        vagaDAO.cadastrarVaga(new Vaga(2, false, 31)); // Ocupada
        vagaDAO.cadastrarVaga(new Vaga(3, true, 32));

        // Inicializa o controller
        desktopPane = new JDesktopPane();
        controller = new ListarVagasController(desktopPane, 1);
    }

    @Test
    public void testCarregarTabelaTodas() {
        // Executa o método
        controller.carregarTabelaTodas();

        // Obtém o modelo da tabela
        DefaultTableModel model = (DefaultTableModel) controller.view.getTableVagas().getModel();

        // Valida o número de linhas e conteúdo
        assertEquals(3, model.getRowCount(), "Deve carregar todas as 3 vagas");
        assertEquals("Desocupado", model.getValueAt(0, 2), "Status da vaga 1 deve ser 'Desocupado'");
        assertEquals("Ocupado", model.getValueAt(1, 2), "Status da vaga 2 deve ser 'Ocupado'");
    }

    @Test
    public void testCarregarVagasDisponiveis() throws SQLException {
        // Executa o método
        controller.carregarVagasDisponiveis();

        // Obtém o modelo da tabela
        DefaultTableModel model = (DefaultTableModel) controller.view.getTableVagas().getModel();

        // Valida o número de linhas e conteúdo
        assertEquals(2, model.getRowCount(), "Deve carregar 2 vagas disponíveis");
        assertEquals("Desocupado", model.getValueAt(0, 2), "Todas as vagas devem estar 'Desocupado'");
    }

    @Test
    public void testCarregarVagasOcupadas() throws SQLException {
        // Executa o método
        controller.carregarVagasOcupadas();

        // Obtém o modelo da tabela
        DefaultTableModel model = (DefaultTableModel) controller.view.getTableVagas().getModel();

        // Valida o número de linhas e conteúdo
        assertEquals(1, model.getRowCount(), "Deve carregar apenas 1 vaga ocupada");
        assertEquals("Ocupado", model.getValueAt(0, 2), "Status deve ser 'Ocupado'");
    }

}

