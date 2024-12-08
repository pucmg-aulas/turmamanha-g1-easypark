package Controllers;

import Models.Cliente;
import Models.Pagamento;
import Models.Veiculo;
import dao.ClientebdDAO;
import dao.PagamentoDAO;
import dao.PagamentobdDAO;
import dao.VeiculoDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import javax.swing.JOptionPane;
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
    private ClientebdDAO clientes;
    private PagamentobdDAO pagamentos;
    private VeiculoDAO veiculos;
    
    public RankingClientesController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException{
        this.desktopPane = desktopPane;
        this.view = new RankingClientesView(desktopPane);
        
        this.clientes = ClientebdDAO.getInstance();
        this.pagamentos = PagamentobdDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
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
            } catch (SQLException ex) {
                Logger.getLogger(RankingClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
        
         
    view.getTableClientes().addMouseListener(new MouseAdapter() {
    
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { 
                abrirDetalhesCliente();
            }
        }
    });
    }
      
    
    public void carregarTabela() throws IOException, SQLException {
        Object colunas[] = {"CPF", "Nome", "Valor "};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0){
        @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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
                String placaVeiculo = pagamento.getPlacaVeiculo();
                Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                
                String cpfCliente = veiculoAtual.getCliente().getCpf();
                String nomeCliente = veiculoAtual.getCliente().getNome();
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
    
    
    public void carregarTabelaFiltrada() throws IOException, SQLException{
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
                pagamento.getPlacaVeiculo();
                String placaVeiculo = pagamento.getPlacaVeiculo();
                Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                
                String cpfCliente = veiculoAtual.getCliente().getCpf();
                String nomeCliente = veiculoAtual.getCliente().getNome();
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
    
    
private void abrirDetalhesCliente() {
    
    if (view.getTableClientes().getRowCount() > 0) {
        int selectedRow = view.getTableClientes().getSelectedRow();
        
        if (selectedRow != -1) {
            String cpf = (String) view.getTableClientes().getValueAt(selectedRow, 0);

            try {
                new ExibirDetalhesClienteController(desktopPane, cpf);
            } catch (IOException ex) {
                Logger.getLogger(RankingClientesController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(view, "Erro ao abrir os detalhes do cliente.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um cliente para ver os detalhes.");
        }
    } else {
        JOptionPane.showMessageDialog(view, "Não há clientes na tabela para exibir os detalhes.");
    }
}
}
