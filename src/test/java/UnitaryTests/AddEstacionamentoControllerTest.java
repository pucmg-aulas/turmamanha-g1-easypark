package UnitaryTests;


import Controllers.AddEstacionamentoController;
import Models.Estacionamento;
import dao.EstacionamentobdDAO;
import dao.VagabdDAO;
import java.sql.SQLException;


import javax.swing.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;



class AddEstacionamentoControllerTest {
    private JDesktopPane desktopPane;
    private AddEstacionamentoController controller;
    private EstacionamentobdDAO estacionamentobdDAO;

    @Before
    void setUp() throws Exception {
        // Configurando o ambiente
        desktopPane = new JDesktopPane();
        controller = new AddEstacionamentoController(desktopPane);
        estacionamentobdDAO = EstacionamentobdDAO.getInstance();

    }


    @Test
    void testAddEstacionamentoComCamposValidos() throws SQLException {
        // Simulando entrada do usuário
        controller.view.getTxtNomeEstacionamento().setText("Estacionamento Central");
        controller.view.getTxtBairroEstacionamento().setText("Centro");
        controller.view.getTxtRuaEstacionamento().setText("Rua A");
        controller.view.getTxtNumeroEstacionamento().setText("100");
        controller.view.getSpnQntdVagasEstacionamento().setValue(50);

        // Executando a ação de cadastro
        controller.addEstacionamento();

        // Validando se o estacionamento foi cadastrado
        Estacionamento estacionamento = estacionamentobdDAO.getEstacionamentoPorId(1); // ID esperado
        assertNotNull(estacionamento, "Estacionamento deveria ser cadastrado.");
        assertEquals("Estacionamento Central", estacionamento.getNome());
        assertEquals(50, estacionamento.getQntdVagas());
    }

    @Test
    void testAddEstacionamentoComCamposInvalidos() throws SQLException {
        // Simulando entrada inválida
        controller.view.getTxtNomeEstacionamento().setText("");
        controller.view.getTxtBairroEstacionamento().setText("");
        controller.view.getTxtRuaEstacionamento().setText("");
        controller.view.getTxtNumeroEstacionamento().setText("");
        controller.view.getSpnQntdVagasEstacionamento().setValue(0);

        // Executando a ação de cadastro
        controller.addEstacionamento();

        // Verificando que o estacionamento não foi cadastrado
        Estacionamento estacionamento = estacionamentobdDAO.getEstacionamentoPorId(1);
        assertNull(estacionamento, "Estacionamento não deveria ser cadastrado com campos inválidos.");
    }

    @Test
    void testLimparCampos() {
        // Preenchendo os campos
        controller.view.getTxtNomeEstacionamento().setText("Teste");
        controller.view.getTxtBairroEstacionamento().setText("Teste Bairro");
        controller.view.getTxtRuaEstacionamento().setText("Teste Rua");
        controller.view.getTxtNumeroEstacionamento().setText("123");
        controller.view.getSpnQntdVagasEstacionamento().setValue(10);

        // Validando que os campos estão vazios
        assertTrue(controller.view.getTxtNomeEstacionamento().getText().isEmpty());
        assertTrue(controller.view.getTxtBairroEstacionamento().getText().isEmpty());
        assertTrue(controller.view.getTxtRuaEstacionamento().getText().isEmpty());
        assertTrue(controller.view.getTxtNumeroEstacionamento().getText().isEmpty());
        assertEquals(0, controller.view.getSpnQntdVagasEstacionamento().getValue());
    }
}

