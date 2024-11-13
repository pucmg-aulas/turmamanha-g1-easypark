package Controllers;

import Models.Cobranca;
import Models.Vaga;
import dao.CobrancabdDAO;
import dao.VagaDAO;
import dao.PagamentobdDAO;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.PagarCobrancaView;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;

public class PagarCobrancaController {

    private PagarCobrancaView view;
    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private VagaDAO vagas;
    private CobrancabdDAO cobrancas;
    private PagamentobdDAO pagamentos;
    private LocalDateTime dataSaida;

    public PagarCobrancaController(JDesktopPane desktopPane, int idEstacionamento, LocalDateTime dataSaida) throws IOException, SQLException {
        this.view = new PagarCobrancaView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancabdDAO.getInstance();
        this.pagamentos = PagamentobdDAO.getInstance();
        this.dataSaida = dataSaida;
        
        desktopPane.add(view);
        this.view.setVisible(true);

        // Configuração dos botões
        view.getVoltarBtn().addActionListener(e -> sair());
        view.getConfirmarBtn().addActionListener(e -> {
            try {
                confirmarPagamento();
                limparCampos();
                carregarVagasOcupadas();
            } catch (Exception ex) {
                Logger.getLogger(PagarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
                showMessage("Erro ao confirmar pagamento: " + ex.getMessage());
            }
        });

        carregarVagasOcupadas();
        
        view.getVagasTable().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    mostrarValor();
                } catch (Exception ex) {
                    Logger.getLogger(PagarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
                    showMessage("Erro ao mostrar valor: " + ex.getMessage());
                }
            }
        });
    }

    private void sair() {
        view.dispose();
    }

    private void carregarVagasOcupadas() throws SQLException, FileNotFoundException {
        Object[] colunas = {"ID", "Tipo", "Status", "Placa"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        
        Iterator<Vaga> it = vagas.getVagasOcupadas().iterator();
        while (it.hasNext()) {
            Vaga v = it.next();
            String[] linha = v.toString().split("-");
            linha[2] = "Ocupado";  // Força o status como "Ocupado"
            String placa = cobrancas.getCobranca(Integer.parseInt(linha[0])).getVeiculo().getPlaca();
            
            tm.addRow(new Object[]{linha[0], linha[1], linha[2], placa});
        }

        view.getVagasTable().setModel(tm);
    }

    private void confirmarPagamento() throws SQLException, FileNotFoundException, IOException {
        Object[] dadosVaga = recuperarDadosVaga();
        
        if (dadosVaga == null) {
            showMessage("Dados inválidos.");
            return;
        }

        Integer idVaga = (Integer) dadosVaga[0];
        String placaText = (String) dadosVaga[1];

        if (idVaga == null || placaText.isEmpty()) {
            showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
            return;
        }

        Cobranca cobranca = cobrancas.lerCobrancas().stream()
                .filter(c -> c.getIdVaga() == idVaga && c.getVeiculo().getPlaca().equals(placaText))
                .findFirst()
                .orElse(null);

        if (cobranca == null) {
            showMessage("Cobrança não encontrada para os dados fornecidos.");
            return;
        }

        cobranca.setValorTotal(mostrarValor());
        pagamentos.salvarPagamento(cobranca);
        
        boolean removido = cobrancas.removerCobranca(cobranca);
        if (removido) {
            VagaDAO vagaDAO = VagaDAO.getInstance(cobranca.getIdEstacionamento());
            boolean vagaLiberada = vagaDAO.liberarVaga(idVaga);

            if (vagaLiberada) {
                showMessage("Cobrança paga e vaga liberada com sucesso!");
                carregarVagasOcupadas();
            } else {
                showMessage("Erro ao liberar a vaga.");
            }
        } else {
            showMessage("Erro ao remover a cobrança.");
        }
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }
    
    private void limparCampos(){
        view.getValor().setText("");
    }
    
    private long calculoFracao(long diferencaEmMinutos){
       return diferencaEmMinutos / Cobranca.FRACAOTEMPO;
    }
    
    private long calculoValorParcial(long qtdFracao){
        return qtdFracao * Cobranca.VALORTEMPO;
    }
    
    private double mostrarValor() throws IOException, SQLException {
        Object[] dadosVaga = recuperarDadosVaga();
        Integer idVaga = (Integer) dadosVaga[0];
        String placaText = (String) dadosVaga[1];
        String tipoVaga = (String) dadosVaga[2];

        if (idVaga == null || placaText.isEmpty()) {
            showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
            return 0;
        }

        Cobranca cobranca;
        cobranca = cobrancas.lerCobrancas().stream()
                .filter(c -> c.getIdVaga() == idVaga && c.getVeiculo() != null && placaText.equals(c.getVeiculo().getPlaca()))
                .findFirst()
                .orElse(null);


        if (cobranca == null) {
            showMessage("Cobrança não encontrada para os dados fornecidos.");
            return 0;
        }

        long diferencaEmMinutos = Duration.between(cobranca.getHoraEntrada(), dataSaida).toMinutes();
        cobranca.setTempoTotal((int) diferencaEmMinutos);
        cobrancas.atualizarCobranca(cobranca);
        
        Vaga vagaEspecifica = new Vaga(idEstacionamento, idVaga);
        
        switch (tipoVaga) {
            case "PCD":
                vagaEspecifica.setTipo(new VagaPCD(idEstacionamento, idVaga));
                break;
            case "Idoso":
                vagaEspecifica.setTipo(new VagaIdoso(idEstacionamento, idVaga));
                break;
            case "Regular":
                vagaEspecifica.setTipo(new VagaRegular(idEstacionamento, idVaga));
                break;
            case "VIP":
                vagaEspecifica.setTipo(new VagaVIP(idEstacionamento, idVaga));
                break;
        }

        double valor = vagaEspecifica.calculoValor(calculoValorParcial(calculoFracao(diferencaEmMinutos)));
        view.getValor().setText(String.format("R$ %.2f", valor));
        return valor;
    }
    
    private Object[] recuperarDadosVaga() {
        int selectedRow = view.getVagasTable().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Selecione uma vaga para confirmar o pagamento.");
            return null;
        }

        String idVagaText = (String) view.getVagasTable().getValueAt(selectedRow, 0);
        Integer idVaga = Integer.parseInt(idVagaText);
        String placaText = (String) view.getVagasTable().getValueAt(selectedRow, 3);
        String tipoVaga = (String) view.getVagasTable().getValueAt(selectedRow, 1);

        return new Object[]{idVaga, placaText, tipoVaga};
    }
}
