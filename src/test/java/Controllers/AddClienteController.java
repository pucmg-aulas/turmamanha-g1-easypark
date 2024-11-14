package Controllers;

import Models.Cliente;
import dao.ClienteDAO;
import dao.ClientebdDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.CadastroClienteView;

public class AddClienteController {
    private CadastroClienteView view;
    private ClientebdDAO clientes;
    
    public AddClienteController(JDesktopPane desktopPane) throws IOException{
        
        this.view = new CadastroClienteView();
        this.clientes = ClientebdDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        this.view.getBtnConfirmar().addActionListener(e ->{
            addCliente();
            limparCampos();
        });
        
    }
    
    private Cliente getAtributosCliente(){
        String cpf = view.getCpf().getText();
        String nome = view.getNome().getText();
       
        return validarCampos(cpf, nome) ? new Cliente(nome, cpf) : null;
    }
    
    private boolean validarCampos(String cpf, String nome){
        return !(nome.isEmpty() || cpf.isEmpty());
    }
    
    public void addCliente() {
        Cliente novoCliente = getAtributosCliente();
        if(novoCliente == null){
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }
        clientes.cadastrarCliente(novoCliente);
        JOptionPane.showMessageDialog(view, "Cliente Cadastrado com sucesso!");
    }
    
     private void limparCampos(){
        view.getNome().setText("");
        view.getCpf().setText("");
        
        
    }
}
