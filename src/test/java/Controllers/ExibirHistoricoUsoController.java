
package Controllers;

import Models.Estacionamento;
import Models.HistoricoUso;
import Models.ITipo;
import Models.Pagamento;
import Models.Veiculo;
import dao.EstacionamentoDAO;
import dao.PagamentoDAO;
import dao.VeiculoDAO;
import view.ExibirHistoricoUsoView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;



public class ExibirHistoricoUsoController {
    private ExibirHistoricoUsoView view;
    private JDesktopPane desktopPane;
    private PagamentoDAO pagamentos;
    private String clienteCpf;
    private VeiculoDAO veiculos;
    private EstacionamentoDAO estacionamentos;
    
    public ExibirHistoricoUsoController(JDesktopPane desktopPane, String cpf) throws IOException {
        this.view = new ExibirHistoricoUsoView(desktopPane);
        this.pagamentos = PagamentoDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.estacionamentos = EstacionamentoDAO.getInstance();
        this.desktopPane = desktopPane;
        this.clienteCpf = cpf;
        
        desktopPane.add(view);
        this.view.setVisible(true);
                
        carregarHistoricoCliente();
    }
    
     public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }
    
      private void carregarHistoricoCliente() {
        Object colunas[] = { "CPF", "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Tempo de Permanência (min)", "Valor(R$)" };
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        try {
            // Lê todos os históricos
            List<Pagamento> todosPagamentos = pagamentos.getPagamentosPorCpf(clienteCpf);
            
            if (todosPagamentos.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nenhum histórico encontrado para o CPF: " + clienteCpf);
            } else {
                for (Pagamento pagamento : todosPagamentos) {
                    String placaVeiculo = pagamento.getPlacaVeiculo();
                    Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                    String cpfAtual = veiculoAtual.getCliente().getCpf();
                    Estacionamento estacionamentoAtual = estacionamentos.getEstacionamentoPorId(pagamento.getIdEstacionamento());
                    String estacionamentoNome = estacionamentoAtual.getNome();
                    ITipo vaga = pagamento.getTipoVaga();
                    String tipoVaga = vaga.getTipo();
                    int tempoTotal = pagamento.getTempoTotal();
                    double valorTotal = pagamento.getValorPago();
                    
                    String[] linha = new String[6];
                    linha[0] = cpfAtual;
                    linha[1] = estacionamentoNome;
                    linha[2] = tipoVaga;
                    linha[3] = placaVeiculo;
                    linha[4] = String.valueOf(tempoTotal);
                    linha[5] = String.valueOf(valorTotal);
                    tm.addRow(linha);
                }
            }
            view.getTableHistorico().setModel(tm);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar histórico: " + e.getMessage());
        }
    }
}
 