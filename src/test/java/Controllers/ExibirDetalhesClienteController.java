/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Cliente;
import dao.ClienteDAO;
import java.io.IOException;
import javax.swing.JDesktopPane;
import view.ExibirDetalhesClienteView;
import view.MenuClienteView;

public class ExibirDetalhesClienteController {
     
    private ExibirDetalhesClienteView view;
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
    }
    
    
    private void carregarCliente(){
        Cliente clienteAtual = clientes.buscarClientePorCpf(cpf);
        String nome = clienteAtual.getNome();
        String cpf = clienteAtual.getCpf();
        view.getNome().setText(nome);
        view.getCpf().setText(cpf);
    }
}
