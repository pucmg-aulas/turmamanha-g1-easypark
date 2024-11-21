package Controllers;

import Models.Pagamento;
import dao.PagamentobdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import view.ValorArrecadadoView;

public class ValorArrecadadoController {

    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private ValorArrecadadoView view;
    private PagamentobdDAO pagamentos;

    public ValorArrecadadoController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException {
        this.view = new ValorArrecadadoView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.pagamentos = PagamentobdDAO.getInstance();


        desktopPane.add(view);
        ajustarTamanhoEPosicao();

        this.view.setVisible(true);

        exibirArrecadacaoTotal();
        exibirValorMedioUtilizacao(); 
        listarMeses();

        this.view.mesesAno().addActionListener(e -> {
            try {
                exibirArrecadacaoMensal();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(ValorArrecadadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.view.getVoltarBtn().addActionListener(e -> sair());
    }

    private void ajustarTamanhoEPosicao() {
        this.view.setSize(desktopPane.getWidth() / 2, (int)(desktopPane.getHeight() / 1.5));
        this.view.setLocation(
            (desktopPane.getWidth() - this.view.getWidth()) / 2,
            (desktopPane.getHeight() - this.view.getHeight()) / 2
        );
    }

    public double exibirArrecadacaoTotal() throws SQLException, IOException {
        List<Pagamento> pagamentosFiltrados = pagamentos.getPagamentosPorEstacionamento(idEstacionamento);
        if (pagamentosFiltrados.isEmpty()) {
            view.getValorTotal().setText("Nenhum pagamento encontrado.");
            return 0;
        } else {
            double totalArrecadado = pagamentosFiltrados.stream()
                    .mapToDouble(Pagamento::getValorPago)
                    .sum();
            view.getValorTotal().setText(String.format("R$ %.2f", totalArrecadado));
            return totalArrecadado;
        }
    }

    public void exibirValorMedioUtilizacao() throws SQLException, IOException {
        List<Pagamento> pagamentosFiltrados = pagamentos.getPagamentosPorEstacionamento(idEstacionamento);
        if (pagamentosFiltrados.isEmpty()) {
            view.getValorMedio().setText("Nenhum pagamento encontrado.");
        } else {
            double totalArrecadado = pagamentosFiltrados.stream()
                    .mapToDouble(Pagamento::getValorPago)
                    .sum();
            double valorMedio = totalArrecadado / pagamentosFiltrados.size();
            view.getValorMedio().setText(String.format("R$ %.2f", valorMedio));
        }
    }

    private void sair() {
        this.view.dispose();
    }

    private void listarMeses() {
        view.mesesAno().removeAllItems();
        String[] mesesEmPortugues = {
            "", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };

        for (int i = 1; i < mesesEmPortugues.length; i++) {
            String item = i + " - " + mesesEmPortugues[i];
            view.mesesAno().addItem(item);
        }
    }

    private int extrairMes(LocalDateTime dataHora) {
        return dataHora.getMonthValue();
    }

    private List<Pagamento> filtrarPagamentosPorMes(List<Pagamento> pagamentos, int mesSelecionado, int idEstacionamento) {
        List<Pagamento> pagamentosFiltrados = new ArrayList<>();

        for (Pagamento pagamento : pagamentos) {
            int mesPagamento = extrairMes(pagamento.getDataPagamento());
            if (mesPagamento == mesSelecionado && pagamento.getIdEstacionamento() == idEstacionamento) {
                pagamentosFiltrados.add(pagamento);
            }
        }
        return pagamentosFiltrados;
    }

    private void exibirArrecadacaoMensal() throws IOException, SQLException {
        String mesSelecionado = (String) view.mesesAno().getSelectedItem();
        if (mesSelecionado == null || mesSelecionado.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Selecione um mês válido.");
            return;
        }

        String[] dadosMes = mesSelecionado.split("-");
        int numeroMes = Integer.parseInt(dadosMes[0].trim());

        List<Pagamento> listaPagamentos = pagamentos.listarPagamentos();
        if (listaPagamentos.isEmpty()) {
            JOptionPane.showMessageDialog(view, "A lista de pagamentos está vazia.");
            return;
        }

        List<Pagamento> pagamentosFiltrados = filtrarPagamentosPorMes(listaPagamentos, numeroMes, idEstacionamento);
        double valorTotalMensal = pagamentosFiltrados.stream()
                .mapToDouble(Pagamento::getValorPago)
                .sum();

        String valorFormatado = String.format("R$ %.2f", valorTotalMensal);
        this.view.getValorMensal().setText(valorFormatado);
    }
}
