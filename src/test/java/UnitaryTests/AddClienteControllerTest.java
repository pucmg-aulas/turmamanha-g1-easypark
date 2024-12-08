package UnitaryTests;

import Controllers.AddClienteController;
import Models.Cliente;
import dao.ClientebdDAO;
import javax.swing.JDesktopPane;



import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;



class AddClienteControllerTest {
    private JDesktopPane desktopPane;
    private AddClienteController controller;
    private ClientebdDAO clientebdDAO;

    @Before
    void setUp() throws IOException {
        // Configurando a interface gráfica e instância do DAO
        desktopPane = new JDesktopPane();
        controller = new AddClienteController(desktopPane);
        clientebdDAO = ClientebdDAO.getInstance();
    }

    @Test
    void testAddClienteComCamposValidos() {
        // Simulando a entrada do usuário
        controller.view.getNome().setText("João da Silva");
        controller.view.getCpf().setText("12345678901");

        // Executando a ação de cadastro
        controller.addCliente();

        // Validando se o cliente foi adicionado
        Cliente cliente = clientebdDAO.buscarClientePorCpf("12345678901");
        assertNotNull(cliente, "Cliente deveria estar cadastrado.");
        assertEquals("João da Silva", cliente.getNome());
    }

    @Test
    void testAddClienteComCamposVazios() {
        // Simulando entrada inválida
        controller.view.getNome().setText("");
        controller.view.getCpf().setText("");

        // Executando a ação de cadastro
        controller.addCliente();

        // Verificando que o cliente não foi cadastrado
        Cliente cliente = clientebdDAO.buscarClientePorCpf("");
        assertNull(cliente, "Cliente não deveria ser cadastrado com campos vazios.");
    }

    @Test
    void testLimparCampos() {
        // Preenchendo os campos
        controller.view.getNome().setText("Teste");
        controller.view.getCpf().setText("123");

      

        // Validando os campos vazios
        assertTrue(controller.view.getNome().getText().isEmpty());
        assertTrue(controller.view.getCpf().getText().isEmpty());
    }
}

