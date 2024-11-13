package Controllers;

import dao.ClienteDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import view.MenuClienteView;

public class MenuClienteController {
    
    private MenuClienteView view;
    private ClienteDAO clientes;
    private JDesktopPane desktopPane;
    private MenuClienteController menuCliente;
    private ExibirDetalhesClienteController detalhesCliente;
    private ExibirVeiculosClienteController veiculosCliente;
    private AddVeiculoController addVeiculo;
    
    private ExibirHistoricoUsoController exibirHistoricoUso;
    
    public MenuClienteController(JDesktopPane desktopPane, String cpf) throws IOException{
        this.view = new MenuClienteView(desktopPane);
        clientes = ClienteDAO.getInstance();
        this. desktopPane = desktopPane;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        view.getExibirDetalhesBtn().addActionListener(e -> {
            try {
                detalhesCliente = new ExibirDetalhesClienteController(desktopPane, cpf);
            } catch (IOException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getExibirVeiculosBtn().addActionListener(e -> {
            try {
                veiculosCliente = new ExibirVeiculosClienteController(desktopPane, cpf);
            } catch (IOException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getCadastrarVeiculoBtn().addActionListener(e -> {
            try {
                addVeiculo = new AddVeiculoController(desktopPane, cpf);
            } catch (IOException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getHistoricoUsoBtn().addActionListener(e -> {
           try{
               exibirHistoricoUso = new ExibirHistoricoUsoController(desktopPane, cpf); 
           } catch (IOException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
