package Controllers;

import Models.Cliente;
import Models.Veiculo;
import dao.ClienteDAO;
import dao.VeiculoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.CadastroVeiculoClienteView;

public class AddVeiculoController {
    private CadastroVeiculoClienteView view;
    private VeiculoDAO veiculos;
    private ClienteDAO clientes;
    private JDesktopPane desktopPane;
    private String clienteCpf;
    
    public AddVeiculoController(JDesktopPane desktopPane, String cpf) throws IOException{
        view = new CadastroVeiculoClienteView(desktopPane);
        clientes = ClienteDAO.getInstance();
        veiculos = VeiculoDAO.getInstance();
        this.desktopPane = desktopPane;
        clienteCpf = cpf;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        view.getConfirmarBtn().addActionListener(e -> {
            try {
                if(cadastrarVeiculo()){
                    limparCampos();
                    view.dispose();
                };
            } catch (IOException ex) {
                Logger.getLogger(AddVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private Cliente getCliente(){
        return clientes.buscarClientePorCpf(clienteCpf);
    }
    
    public boolean cadastrarVeiculo() throws IOException{
        String placa = view.getPlaca().getText();
        String modelo = view.getModelo().getText();
        
        if(placa.isEmpty() || modelo.isEmpty()){
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return false;
        }
        Cliente clienteAtual = getCliente();
        Veiculo novoVeiculo = new Veiculo(placa, clienteAtual, modelo);
        
        if(veiculos.cadastrarVeiculoPorCliente(novoVeiculo)){
             JOptionPane.showMessageDialog(view, "Veículo cadastrado: (Placa) -" + novoVeiculo.getPlaca());
             return true;
        }else{
             JOptionPane.showMessageDialog(view, "Erro ao cadastrar Veículo!");
             return false;
        }
        
    }
    
     private void limparCampos(){
        view.getPlaca().setText("");
        view.getModelo().setText("");  
    }
}
