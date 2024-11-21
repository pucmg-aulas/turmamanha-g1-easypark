package Controllers;

import Models.Cliente;
import dao.ClientebdDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.SelecionarClienteView;



public class SelectClienteController {
    private SelecionarClienteView view;
    private ClientebdDAO clientes;
    private JDesktopPane desktopPane;
    private MenuClienteController menuCliente;
     
    
    public SelectClienteController(JDesktopPane desktopPane) throws IOException{
       this.view = new SelecionarClienteView(desktopPane);
       clientes = ClientebdDAO.getInstance();
       this.desktopPane = desktopPane;
       
        desktopPane.add(view);
        this.view.setVisible(true);
        
        view.getConfirmarBtn().addActionListener(e ->{
            Cliente clienteAtual = selecionarCliente();
            if(clienteAtual != null){
                JOptionPane.showMessageDialog(view,"Cliente Encontrado: " + clienteAtual.getNome());
                try {
                    menuCliente = new MenuClienteController(desktopPane, clienteAtual.getCpf());
                } catch (IOException ex) {
                    Logger.getLogger(SelectClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    private boolean validarCampo(String cpf){
        return !(cpf.isEmpty());
    }
    
    private String getCpf(){
        String cpf = view.getCpf().getText();
        
        if(validarCampo(cpf)){
            return cpf;
        }
        
        JOptionPane.showMessageDialog(view,"Preencha todos os campos!");
        return null;
    }
    
    private Cliente selecionarCliente(){
        String cpf = getCpf();
        if(cpf != null){
            Cliente clienteAtual = clientes.buscarClientePorCpf(cpf);
            return clienteAtual != null ? clienteAtual : null;
        }
        
        return null;
    }
    
}
