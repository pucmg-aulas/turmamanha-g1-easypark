package Controllers;

import Models.Cobranca;
import Models.Vaga;
import dao.CobrancaDAO;
import dao.VagaDAO;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import view.PagarCobrancaView;

public class PagarCobrancaController {

    private PagarCobrancaView view;
    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private VagaDAO vagas;
    private CobrancaDAO cobrancas;

    public PagarCobrancaController(JDesktopPane desktopPane, int idEstacionamento) throws IOException {
        this.view = new PagarCobrancaView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancaDAO.getInstance();

        desktopPane.add(view);
        this.view.setVisible(true);

        // Configurações dos botões
        view.getVoltarBtn().addActionListener(e -> sair());
        view.getConfirmarBtn().addActionListener(e -> confirmarPagamento());

        carregarVagasOcupadas();
    }

    private void sair() {
        view.dispose();
    }

    private void carregarVagasOcupadas() {
        Object[] colunas = {"ID", "Tipo", "Status", "Placa"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        Iterator<Vaga> it = vagas.getVagasOcupadas().iterator();
        while (it.hasNext()) {
            Vaga v = it.next();
            String[] linha = v.toString().split("-");
            linha[2] = "Ocupado";  // Força o status como "Ocupado"
            String placa = cobrancas.getPlacaCobranca(Integer.parseInt(linha[0]));
            tm.addRow(new Object[]{linha[0], linha[1], linha[2], placa});
        }

        view.getVagasTable().setModel(tm);
    }

    private void confirmarPagamento() {
        try {
            // Verifica se uma vaga foi selecionada na tabela
            int selectedRow = view.getVagasTable().getSelectedRow();
            if (selectedRow == -1) {
                showMessage("Selecione uma vaga para confirmar o pagamento.");
                return;
            }

            // Recupera os dados inseridos nos campos de texto
            String idVagaText = view.getId().getText();
            String placaText = view.getPlaca().getText();

            if (idVagaText.isEmpty() || placaText.isEmpty()) {
                showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
                return;
            }

            int idVaga = Integer.parseInt(idVagaText);

            // Busca a cobrança correspondente no DAO
            Cobranca cobranca = cobrancas.lerCobrancas().stream()
                .filter(c -> c.getIdVaga() == idVaga && c.getPlacaVeiculo().equals(placaText))
                .findFirst()
                .orElse(null);

            if (cobranca == null) {
                showMessage("Cobrança não encontrada para os dados fornecidos.");
                return;
            }

            // Remove a cobrança e libera a vaga
            boolean removido = cobrancas.removerCobranca(cobranca);
            if (removido) {
                VagaDAO vagaDAO = VagaDAO.getInstance(cobranca.getIdEstacionamento());
                boolean vagaLiberada = vagaDAO.liberarVaga(idVaga); // Muda o status da vaga para desocupada

                if (vagaLiberada) {
                    showMessage("Cobrança paga e vaga liberada com sucesso!");
                    carregarVagasOcupadas();  // Atualiza a tabela
                } else {
                    showMessage("Erro ao liberar a vaga.");
                }
            } else {
                showMessage("Erro ao remover a cobrança.");
            }
        } catch (NumberFormatException ex) {
            showMessage("ID da Vaga deve ser um número válido.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Ocorreu um erro: " + ex.getMessage());
        }
    }


    private void showMessage(String message) {
        view.showMessage(message);
    }
}
