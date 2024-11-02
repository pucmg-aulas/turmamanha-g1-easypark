package Controllers;

import Models.Cliente;
import Models.Pagamento;
import dao.ClienteDAO;
import dao.PagamentoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
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
    private PagamentoDAO pagamentos;
    
    public RankingClientesController(JDesktopPane desktopPane, int idEstacionamento) throws IOException{
        this.desktopPane = desktopPane;
        this.view = new RankingClientesView(desktopPane);
        
        this.clientes = ClienteDAO.getInstance();
        this.pagamentos = PagamentoDAO.getInstance();
        this.idEstacionamento = idEstacionamento;
    
        desktopPane.add(view);
        view.setVisible(true);
 
        carregarTabela();
        
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
    }
      
    
    private void carregarTabela() throws IOException {
        Object colunas[] = {"CPF", "Nome", "Valor "};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        List<Pagamento> ListaPagamentos = pagamentos.listarPagamentos();
        
        if (ListaPagamentos == null || ListaPagamentos.isEmpty()) {
             JOptionPane.showMessageDialog(view, "A lista de pagamentos está vazia ou é nula.");
        return;
        }
        
        Iterator<Pagamento> it = ListaPagamentos.iterator();
        while(it.hasNext()){
            Pagamento p = it.next();
            String cpfCliente = p.getCliente().getCpf();
            String nomeCliente = p.getCliente().getNome();
            double somaPagamento = 0;
            for(Pagamento pagamento : ListaPagamentos){
                if(pagamento.getIdEstacionamento() == idEstacionamento){
                    if(pagamento.getCliente().getCpf().equals(cpfCliente)){
                    somaPagamento += pagamento.getValorPago();
                }
                }
                
            }
            
            tm.addRow(new Object[]{cpfCliente, nomeCliente, somaPagamento});
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
