package Controllers;

import Models.Cliente;
import Models.Pagamento;
import dao.ClienteDAO;
import dao.PagamentoDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 
        listarMeses();
        carregarTabela();
        
        view.getMesesBox().addActionListener(e -> {
            try {
                carregarTabelaFiltrada();
            } catch (IOException ex) {
                Logger.getLogger(RankingClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
    }
      
    
    private void carregarTabela() throws IOException {
        Object colunas[] = {"CPF", "Nome", "Valor "};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        List<Pagamento> listaPagamentos = pagamentos.listarPagamentos();

        if (listaPagamentos == null || listaPagamentos.isEmpty()) {
            JOptionPane.showMessageDialog(view, "A lista de pagamentos está vazia ou é nula.");
            return;
        }

        // Mapa para armazenar a soma dos pagamentos por CPF
        Map<String, Double> somaPagamentosPorCpf = new HashMap<>();
        Map<String, String> nomesPorCpf = new HashMap<>();

        for (Pagamento pagamento : listaPagamentos) {
            if (pagamento.getIdEstacionamento() == idEstacionamento) {
                String cpfCliente = pagamento.getCliente().getCpf();
                String nomeCliente = pagamento.getCliente().getNome();
                double valorPago = pagamento.getValorPago();

                // Adiciona ou soma o valor no mapa
                somaPagamentosPorCpf.put(cpfCliente, somaPagamentosPorCpf.getOrDefault(cpfCliente, 0.0) + valorPago);
                nomesPorCpf.putIfAbsent(cpfCliente, nomeCliente); // Armazena o nome se ainda não existir
            }
        }

        // Preenche a tabela com os valores agregados
        for (String cpf : somaPagamentosPorCpf.keySet()) {
            double somaPagamento = somaPagamentosPorCpf.get(cpf);
            String nomeCliente = nomesPorCpf.get(cpf);
            tm.addRow(new Object[]{cpf, nomeCliente, somaPagamento});
        }

        view.getTableClientes().setModel(tm);

        // Criando o TableRowSorter e associando-o ao modelo da tabela
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tm);
        view.getTableClientes().setRowSorter(sorter);

        // Configurando para ordenar a coluna "Valor" (índice 2) em ordem decrescente
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
     private void sair() {
        this.view.dispose();
    }
     
    private void listarMeses() {
       view.getMesesBox().removeAllItems();
    
        // Array com os nomes dos meses em português
        String[] mesesEmPortugues = {
            "", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
    
        // Loop para adicionar os meses no formato desejado
        for (int i = 1; i < mesesEmPortugues.length; i++) {
            String item = (i) + " - " + mesesEmPortugues[i];
            view.getMesesBox().addItem(item);
    }
}   

    private int extrairMes(LocalDateTime dataHora){
        return dataHora.getMonthValue();
    }

    private List<Pagamento> filtrarPagamentosPorMes(List<Pagamento> pagamentos, int mesSelecionado){
        List<Pagamento> pagamentosFiltrados = new ArrayList<>();
        
        for(Pagamento pagamento : pagamentos){
            int mesPagamento = extrairMes(pagamento.getDataPagamento());
            
            if(mesPagamento == mesSelecionado){
                pagamentosFiltrados.add(pagamento);
            }
        }
        return pagamentosFiltrados;
    }
    
    
    private void carregarTabelaFiltrada() throws IOException{
        String mesSelecionado = (String) view.getMesesBox().getSelectedItem();
        String[] dadosMes = mesSelecionado.split("-");
        int numeroMes = Integer.parseInt(dadosMes[0].trim());
        
        Object colunas[] = {"CPF", "Nome", "Valor"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        List<Pagamento> listaPagamentos = pagamentos.listarPagamentos();
        if (listaPagamentos == null || listaPagamentos.isEmpty()) {
            JOptionPane.showMessageDialog(view, "A lista de pagamentos está vazia ou é nula.");
            return;
        }
        
         List<Pagamento> pagamentosFiltrados = filtrarPagamentosPorMes(listaPagamentos, numeroMes);
         
          Map<String, Double> somaPagamentosPorCpf = new HashMap<>();
          Map<String, String> nomesPorCpf = new HashMap<>();
          
          for (Pagamento pagamento : pagamentosFiltrados) {
            if (pagamento.getIdEstacionamento() == idEstacionamento) {
                String cpfCliente = pagamento.getCliente().getCpf();
                String nomeCliente = pagamento.getCliente().getNome();
                double valorPago = pagamento.getValorPago();

                // Adiciona ou soma o valor no mapa
                somaPagamentosPorCpf.put(cpfCliente, somaPagamentosPorCpf.getOrDefault(cpfCliente, 0.0) + valorPago);
                nomesPorCpf.putIfAbsent(cpfCliente, nomeCliente);
            }
        }
          for (String cpf : somaPagamentosPorCpf.keySet()) {
            double somaPagamento = somaPagamentosPorCpf.get(cpf);
            String nomeCliente = nomesPorCpf.get(cpf);
            tm.addRow(new Object[]{cpf, nomeCliente, somaPagamento});
        }
          view.getTableClientes().setModel(tm);
          TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tm);
           view.getTableClientes().setRowSorter(sorter);
           List<RowSorter.SortKey> sortKeys = new ArrayList<>();
           sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
           sorter.setSortKeys(sortKeys);
           sorter.sort();
    }
}
