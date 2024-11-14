package Controllers;

import Models.Cobranca;
import Models.Estacionamento;
import Models.ITipo;
import Models.Pagamento;
import Models.Veiculo;
import dao.EstacionamentoDAO;
import dao.EstacionamentobdDAO;
import dao.PagamentobdDAO;
import dao.VeiculoDAO;
import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import view.ExibirHistoricoUsoView;

public class ExibirHistoricoUsoController {

    private ExibirHistoricoUsoView view;
    private JDesktopPane desktopPane;
    private PagamentobdDAO pagamentos;
    private String clienteCpf;
    private VeiculoDAO veiculos;
    private EstacionamentobdDAO estacionamentos;

    public ExibirHistoricoUsoController(JDesktopPane desktopPane, String cpf) throws IOException, SQLException {
        this.view = new ExibirHistoricoUsoView(desktopPane);
        this.pagamentos = PagamentobdDAO.getInstance();
        this.veiculos = VeiculoDAO.getInstance();
        this.estacionamentos = EstacionamentobdDAO.getInstance();
        this.desktopPane = desktopPane;
        this.clienteCpf = cpf;

        desktopPane.add(view);
        this.view.setVisible(true);

        carregarHistoricoCliente();

        this.view.getFiltrarBtn().addActionListener(e -> {
            String dataInicio = view.getDataInicio();
            String dataFim = view.getDataFim();
            try {
                filtrarHistoricoPorData(dataInicio, dataFim);
            } catch (SQLException ex) {
                Logger.getLogger(ExibirHistoricoUsoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }

    private void carregarHistoricoCliente() throws SQLException {
        Object colunas[] = {"CPF", "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Data Entrada", "Data Saída", "Valor(R$)"};
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

    private void adicionarHistoricoNaTabela(DefaultTableModel tm, Pagamento pagamento) throws IOException, SQLException {
        String placaVeiculo = pagamento.getPlacaVeiculo();
        Veiculo veiculoAtual = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
        String cpfAtual = veiculoAtual.getCliente().getCpf();
        Estacionamento estacionamentoAtual = estacionamentos.getEstacionamentoPorId(pagamento.getIdEstacionamento());
        String estacionamentoNome = estacionamentoAtual.getNome();
        ITipo vaga = pagamento.getTipoVaga();
        String tipoVaga = vaga.getTipo();
        double valorTotal = pagamento.getValorPago();
        String dataEntrada = pagamento.getDataEntrada() != null ? pagamento.getDataEntrada().format(DateTimeFormatter.ISO_DATE) : "Data não disponível";
        String dataSaida = pagamento.getDataPagamento() != null ? pagamento.getDataPagamento().format(DateTimeFormatter.ISO_DATE) : "Data não disponível";

        String[] linha = new String[7];
        linha[0] = cpfAtual;
        linha[1] = estacionamentoNome;
        linha[2] = tipoVaga;
        linha[3] = placaVeiculo;
        linha[4] = dataEntrada;
        linha[5] = dataSaida;
        linha[6] = String.valueOf(valorTotal);
        tm.addRow(linha);
    }

    public void filtrarHistoricoPorData(String dataInicioStr, String dataFimStr) throws SQLException {
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

            DefaultTableModel tm = new DefaultTableModel(new Object[]{"CPF", "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Data Entrada", "Data Saída", "Valor(R$)"}, 0);
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

    boolean isVisible() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
