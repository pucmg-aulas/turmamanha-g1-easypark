/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Cliente;
import dao.ClienteDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.ExibirDetalhesClienteView;
import view.MenuClienteView;

public class ExibirDetalhesClienteController {
     
    private ExibirDetalhesClienteView view;
    private ExibirHistoricoUsoController historicoCliente;
    private ClienteDAO clientes;
    private final JDesktopPane desktopPane;
    private String cpf;
    
    public ExibirDetalhesClienteController(JDesktopPane desktopPane, String cpf) throws IOException{
        this.view = new ExibirDetalhesClienteView(desktopPane);
        clientes = ClienteDAO.getInstance();
        this.desktopPane = desktopPane;
        this.cpf = cpf;
        
        desktopPane.add(view);
        this.view.setVisible(true);
 
        carregarCliente();
        
        this.view.getVerHistoricoBtn().addActionListener(e -> abrirHistoricoCliente());
        }
    
    private void abrirHistoricoCliente() {
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
        Cliente clienteAtual = clientes.buscarClientePorCpf(cpf);
        String nome = clienteAtual.getNome();
        String cpf = clienteAtual.getCpf();
        view.getNome().setText(nome);
        view.getCpf().setText(cpf);
    }
}
