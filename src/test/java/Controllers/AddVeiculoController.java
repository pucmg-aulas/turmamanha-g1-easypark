package Controllers;

import Models.Cliente;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.VeiculoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.CadastroVeiculoClienteView;

public class AddVeiculoController {
    public CadastroVeiculoClienteView view;
    private VeiculoDAO veiculos;
    private ClientebdDAO clientes;
    private JDesktopPane desktopPane;
    private String clienteCpf;
    private String placaVeiculo;
    
    public AddVeiculoController(JDesktopPane desktopPane, String dado, boolean isCpf) throws IOException{
            view = new CadastroVeiculoClienteView(desktopPane);
            clientes = ClientebdDAO.getInstance();
            veiculos = VeiculoDAO.getInstance();
            this.desktopPane = desktopPane;
            desktopPane.add(view);
            this.view.setVisible(true);
            
        if(isCpf){
            clienteCpf = dado;
        }else{
            placaVeiculo = dado;
            view.getPlaca().setText(placaVeiculo);
            
            clienteCpf = JOptionPane.showInputDialog(view,
                    "Digite o CPF do cliente: ",
                    "CPF Necessário",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            if (clienteCpf == null || clienteCpf.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    view,
                    "O CPF é obrigatório para continuar.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                view.dispose(); // Fecha a tela se não há CPF
                return; // Cancela o fluxo de execução
            }else{
               Cliente clienteAtual = clientes.buscarClientePorCpf(clienteCpf);
               if(clienteAtual == null){
                    JOptionPane.showMessageDialog(
                         view,
                         "Esse cliente não está cadastrado",
                         "Erro",
                         JOptionPane.ERROR_MESSAGE
                    );
                    view.dispose(); 
                    return; 
               }
            }
            
           
            
        }
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
    
    public AddVeiculoController(JDesktopPane desktopPane, String placaVeiculo, String Cpf) throws IOException{
            view = new CadastroVeiculoClienteView(desktopPane);
            clientes = ClientebdDAO.getInstance();
            veiculos = VeiculoDAO.getInstance();
            this.desktopPane = desktopPane;
            desktopPane.add(view);
            this.view.setVisible(true);
            
        
            clienteCpf = Cpf;
            view.getPlaca().setText(placaVeiculo);
            
               Cliente clienteAtual = clientes.buscarClientePorCpf(clienteCpf);
               if(clienteAtual == null){
                    JOptionPane.showMessageDialog(
                         view,
                         "Esse cliente não está cadastrado",
                         "Erro",
                         JOptionPane.ERROR_MESSAGE
                    );
                    view.dispose(); 
                    return; 
               }
               
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
