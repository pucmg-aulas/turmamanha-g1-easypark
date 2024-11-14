/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Cliente;
import dao.ClienteDAO;
import dao.ClientebdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.ExibirDetalhesClienteView;
import view.MenuClienteView;

public class ExibirDetalhesClienteController {
     
    private ExibirDetalhesClienteView view;
    private ExibirHistoricoUsoController historicoCliente;
    private ClientebdDAO clientes;
    private final JDesktopPane desktopPane;
    private String cpf;
    
    public ExibirDetalhesClienteController(JDesktopPane desktopPane, String cpf) throws IOException{
        this.view = new ExibirDetalhesClienteView(desktopPane);
        clientes = ClientebdDAO.getInstance();
        this.desktopPane = desktopPane;
        this.cpf = cpf;
        
        desktopPane.add(view);
        this.view.setVisible(true);
 
        carregarCliente();
        
        this.view.getVerHistoricoBtn().addActionListener(e -> {
            try {
                abrirHistoricoCliente();
            } catch (SQLException ex) {
                Logger.getLogger(ExibirDetalhesClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }
    
    private void abrirHistoricoCliente() throws SQLException {
        try {
            if (historicoCliente == null || !historicoCliente.isVisible()) {
                historicoCliente = new ExibirHistoricoUsoController(desktopPane, cpf);
            } else {
                JOptionPane.showMessageDialog(view, "A tela de histórico já está aberta.");
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuClienteController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(view, "Erro ao abrir o histórico: " + ex.getMessage());
        }
    }
   
    
    
    private void carregarCliente(){
        JOptionPane.showMessageDialog(view, cpf);
        Cliente clienteAtual = clientes.buscarClientePorCpf(cpf);
        JOptionPane.showMessageDialog(view, clienteAtual.getCpf() + clienteAtual.getNome());
        String nome = clienteAtual.getNome();
        String cpf = clienteAtual.getCpf();
        view.getNome().setText(nome);
        view.getCpf().setText(cpf);
    }
}
