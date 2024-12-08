package UnitaryTests;

import Controllers.AddVeiculoController;
import Models.Cliente;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.VeiculoDAO;
import javax.swing.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;



class AddVeiculoControllerTest {
    private JDesktopPane desktopPane;
    private AddVeiculoController controller;
    private ClientebdDAO clientebdDAO;
    private VeiculoDAO veiculoDAO;

    @Before
    void setUp() throws Exception {
        // Configuração inicial
        desktopPane = new JDesktopPane();
        clientebdDAO = ClientebdDAO.getInstance();
        veiculoDAO = VeiculoDAO.getInstance();

;
    }



    @Test
    void testCadastrarVeiculoComClienteValido() throws Exception {
        // Simulando cliente existente no sistema
        Cliente cliente = new Cliente("Cliente Teste", "12345678900");
        clientebdDAO.cadastrarCliente(cliente);

        // Configurando controlador para o fluxo de cadastro de veículo
        controller = new AddVeiculoController(desktopPane, cliente.getCpf(), true);

        // Simulando entrada do usuário
        controller.view.getPlaca().setText("ABC1234");
        controller.view.getModelo().setText("Modelo Teste");

        // Executando ação de cadastro
        boolean sucesso = controller.cadastrarVeiculo();

        // Validando se o veículo foi cadastrado corretamente
        assertTrue(sucesso);
        Veiculo veiculo = veiculoDAO.buscarVeiculoPorPlaca("ABC1234");
        assertNotNull(veiculo, "Veículo deveria existir no banco de dados.");
        assertEquals(cliente.getCpf(), veiculo.getCliente().getCpf(), "Veículo deveria estar associado ao cliente correto.");
    }

    @Test
    void testCadastrarVeiculoSemClienteValido() throws Exception {
        // Configurando controlador para o fluxo de cadastro com cliente inexistente
        controller = new AddVeiculoController(desktopPane, "12345678900", true);

        // Simulando entrada do usuário
        controller.view.getPlaca().setText("DEF5678");
        controller.view.getModelo().setText("Outro Modelo");

        // Executando ação de cadastro
        boolean sucesso = controller.cadastrarVeiculo();

        // Validando que o veículo não foi cadastrado
        assertFalse(sucesso, "Veículo não deveria ser cadastrado sem cliente válido.");
        Veiculo veiculo = veiculoDAO.buscarVeiculoPorPlaca("DEF5678");
        assertNull(veiculo, "Veículo não deveria existir no banco de dados.");
    }

    @Test
    void testCadastrarVeiculoComCamposVazios() throws Exception {
        // Simulando cliente existente
        Cliente cliente = new Cliente("Cliente Teste", "12345678900");
        clientebdDAO.cadastrarCliente(cliente);

        // Configurando controlador para cadastro de veículo
        controller = new AddVeiculoController(desktopPane, cliente.getCpf(), true);

        // Simulando entrada com campos vazios
        controller.view.getPlaca().setText("");
        controller.view.getModelo().setText("");

        // Executando ação de cadastro
        boolean sucesso = controller.cadastrarVeiculo();

        // Validando que o veículo não foi cadastrado
        assertFalse(sucesso, "Veículo não deveria ser cadastrado com campos vazios.");
    }

    @Test
    void testLimparCampos() throws Exception {
        // Simulando cliente existente
        Cliente cliente = new Cliente("Cliente Teste", "12345678900");
        clientebdDAO.cadastrarCliente(cliente);

        // Configurando controlador para cadastro de veículo
        controller = new AddVeiculoController(desktopPane, cliente.getCpf(), true);

        // Preenchendo os campos
        controller.view.getPlaca().setText("XYZ9876");
        controller.view.getModelo().setText("Modelo Limpar");


        // Validando que os campos estão vazios
        assertTrue(controller.view.getPlaca().getText().isEmpty());
        assertTrue(controller.view.getModelo().getText().isEmpty());
    }

    private void assertTrue(boolean sucesso) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
