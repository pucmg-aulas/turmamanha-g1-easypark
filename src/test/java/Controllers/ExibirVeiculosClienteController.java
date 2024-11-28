package Controllers;

import Models.Cliente;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.VeiculoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import view.ExibirVeiculosClienteView;
import Exceptions.VeiculoNaoEncontradoException;

public class ExibirVeiculosClienteController {
    private ExibirVeiculosClienteView view;
    private String cpfCliente;
    private VeiculoDAO veiculoDAO;
    private ClientebdDAO clienteDAO;
    private JDesktopPane desktopPane;
    
    public ExibirVeiculosClienteController(JDesktopPane desktopPane, String cpf) throws IOException{
        this.view = new ExibirVeiculosClienteView(desktopPane);
        cpfCliente = cpf;
        veiculoDAO = VeiculoDAO.getInstance();
        clienteDAO = ClientebdDAO.getInstance();
        this.desktopPane = desktopPane;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        try {
            carregarVeiculos();
        } catch (VeiculoNaoEncontradoException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
            view.dispose();
        }
        
    }
   
    private Cliente getCliente(String cpf){
        return clienteDAO.buscarClientePorCpf(cpf);
    }
    
    private void carregarVeiculos() throws IOException, VeiculoNaoEncontradoException {
        Cliente clienteAtual = getCliente(cpfCliente);
        List<Veiculo> listaVeiculos = veiculoDAO.buscarVeiculosPorCliente(clienteAtual);

        if (listaVeiculos.isEmpty()) {
            throw new VeiculoNaoEncontradoException("Não há veículos cadastrados para o cliente!");
        }

        DefaultListModel<String> listaVeiculosTexto = new DefaultListModel<>();
        for (Veiculo veiculo : listaVeiculos) {
            String texto = veiculo.getPlaca() + " - " + veiculo.getModelo();
            listaVeiculosTexto.addElement(texto);
        }
        view.getListaVeiculos().setModel(listaVeiculosTexto);
    }
    
}
