/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Cliente;
import dao.ClienteDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.RankingClientesView;

/**
 *
 * @author Enzo
 */
    public class RankingClientesController {
    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private RankingClientesView view;
    private ClienteDAO clientes;
    
    public RankingClientesController(JDesktopPane desktopPane) throws IOException{
         this.desktopPane = desktopPane;
        this.view = new RankingClientesView(desktopPane);
        this.clientes = ClienteDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        carregarTabela();
        
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
    }
      
    
    private void carregarTabela() {
        Object colunas[] = {"ID", "Tipo"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Cliente> it = clientes.getCliente().iterator();
        while(it.hasNext()){
            Cliente c = it.next();
            String vaga = c.toString();
            String linha[] = vaga.split("-");
            tm.addRow(new Object[]{linha[0], linha[1]});
        }
        view.getTableClientes().setModel(tm);
        
         // Criando o TableRowSorter e associando-o ao modelo da tabela
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tm);
    view.getTableClientes().setRowSorter(sorter);

    // Configurando para ordenar a coluna "ValorArrecadado" (índice 1) em ordem decrescente
    List<RowSorter.SortKey> sortKeys = new ArrayList<>();
    sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
    sorter.setSortKeys(sortKeys);
    sorter.sort();
    }
    
     private void sair() {
        this.view.dispose();
    }
}
