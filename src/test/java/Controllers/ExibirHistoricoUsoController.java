package Controllers;

import Models.Cobranca;
import Models.Estacionamento;
import Models.HistoricoUso;
import Models.ITipo;
import Models.Pagamento;
import Models.Veiculo;
import dao.CobrancaDAO;
import dao.EstacionamentoDAO;
import dao.PagamentoDAO;
import dao.VeiculoDAO;
import java.time.LocalDate;
import java.io.FileNotFoundException;
import view.ExibirHistoricoUsoView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        
        this.view.getFiltrarBtn().addActionListener(e -> {
            String dataInicio = view.getDataInicio(); 
            String dataFim = view.getDataFim();
            filtrarHistoricoPorData(dataInicio, dataFim);
        });
    }

    public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }

    private void carregarHistoricoCliente() {
        Object colunas[] = {"CPF", "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Data Entrada", "Data Saida", "Valor(R$)"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        try {
            List<Pagamento> todosPagamentos = pagamentos.getPagamentosPorCpf(clienteCpf);

            if (todosPagamentos.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nenhum histórico encontrado para o CPF: " + clienteCpf);
            } else {
                for (Pagamento pagamento : todosPagamentos) {
                    adicionarHistoricoNaTabela(tm, pagamento);
                }
            }
            view.getTableHistorico().setModel(tm);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar histórico: " + e.getMessage());
        }
    }
        
    private void adicionarHistoricoNaTabela(DefaultTableModel tm, Pagamento pagamento) throws FileNotFoundException {
        Cobranca cobranca = new Cobranca();
        String placaVeiculo = pagamento.getPlacaVeiculo();
        Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
        String cpfAtual = veiculoAtual.getCliente().getCpf();
        Estacionamento estacionamentoAtual = estacionamentos.getEstacionamentoPorId(pagamento.getIdEstacionamento());
        String estacionamentoNome = estacionamentoAtual.getNome();
        ITipo vaga = pagamento.getTipoVaga();
        String tipoVaga = vaga.getTipo();
        double valorTotal = pagamento.getValorPago();
        String dataEntrada = cobranca.getHoraEntrada().format(DateTimeFormatter.ISO_DATE);
        String dataSaida = pagamento.getDataPagamento().format(DateTimeFormatter.ISO_DATE);

        String[] linha = new String[7];
        linha[0] = cpfAtual;
        linha[1] = estacionamentoNome;
        linha[2] = tipoVaga;
        linha[3] = placaVeiculo;
        linha[4] = dataSaida;
        linha[5] = dataEntrada;
        linha[6] = String.valueOf(valorTotal);
        tm.addRow(linha);
    }

    public void filtrarHistoricoPorData(String dataInicioStr, String dataFimStr) {
        try {
            LocalDate dataInicio = null;
            LocalDate dataFim = null;

            if (!dataInicioStr.isEmpty()) {
                dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            if (!dataFimStr.isEmpty()) {
                dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            if (dataInicio == null && dataFim == null) {
                carregarHistoricoCliente();
                return;
            }

            if ((dataInicio == null && dataFim != null) || (dataInicio != null && dataFim == null)) {
                JOptionPane.showMessageDialog(view, "Por favor, preencha ambos os campos de data ou nenhum.");
                return;
            }

            List<Pagamento> todosPagamentos = pagamentos.getPagamentosPorCpf(clienteCpf);

            final LocalDate finalDataInicio = dataInicio;
            final LocalDate finalDataFim = dataFim;

            List<Pagamento> pagamentosFiltrados = todosPagamentos.stream()
                    .filter(pagamento -> {
                        LocalDateTime dataUso = pagamento.getDataPagamento();
                        return (dataUso.toLocalDate().isEqual(finalDataInicio) || dataUso.toLocalDate().isAfter(finalDataInicio))
                                && (dataUso.toLocalDate().isEqual(finalDataFim) || dataUso.toLocalDate().isBefore(finalDataFim));
                    })
                    .toList();

            DefaultTableModel tm = new DefaultTableModel(new Object[]{"CPF", "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Tempo de Permanência (min)", "Valor(R$)"}, 0);
            if (pagamentosFiltrados.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nenhum histórico encontrado para o intervalo de datas.");
            } else {
                for (Pagamento pagamento : pagamentosFiltrados) {
                    adicionarHistoricoNaTabela(tm, pagamento);
                }
            }
            view.getTableHistorico().setModel(tm);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view, "Formato de data inválido. Utilize o formato dd/MM/yyyy.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar histórico: " + e.getMessage());
        }
    }


}
