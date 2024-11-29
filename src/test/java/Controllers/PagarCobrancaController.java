package Controllers;

import Models.Cobranca;
import Models.Vaga;
import dao.CobrancabdDAO;
import dao.VagaDAO;
import dao.PagamentobdDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.PagarCobrancaView;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import dao.VagabdDAO;
import dao.VeiculoDAO;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagarCobrancaController {

    private PagarCobrancaView view;
    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private VagabdDAO vagas;
    private CobrancabdDAO cobrancas;
    private PagamentobdDAO pagamentos;
    private LocalDateTime dataSaida;
    private VeiculoDAO veiculos;

    public PagarCobrancaController(JDesktopPane desktopPane, int idEstacionamento, LocalDateTime dataSaida) throws IOException, SQLException {
        this.view = new PagarCobrancaView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.vagas = VagabdDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancabdDAO.getInstance();
        this.pagamentos = PagamentobdDAO.getInstance();
        this.dataSaida = dataSaida;
        this.veiculos = VeiculoDAO.getInstance();
        
        
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
        Object[] colunas = {"ID", "Tipo", "Status", "Placa", "Entrada", "Tempo Total", "Valor Total"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

            // Itera sobre as cobranças ocupadas e filtra pelo ID do estacionamento
        for (Cobranca cobranca : cobrancas.lerCobrancasPorEstacionamento(idEstacionamento)) {
            if (cobranca.getVeiculo() != null && cobranca.getHoraSaida() == null  && cobranca.getIdEstacionamento() == idEstacionamento) { // Filtra pelo ID do estacionamento

                Vaga vaga = vagas.getVagaPorId(cobranca.getIdVaga());
                String tipoVaga = vaga != null ? vaga.getTipo() : "Desconhecido";
                String placa = cobranca.getVeiculo().getPlaca();
                LocalDateTime entrada = cobranca.getHoraEntrada();
                int tempoTotal = cobranca.getTempoTotal();
                double valorTotal = cobranca.getValorTotal();

                tm.addRow(new Object[]{cobranca.getIdVaga(), tipoVaga, "Ocupado", placa, entrada, tempoTotal, valorTotal});
            }
        
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

        Cobranca cobranca = cobrancas.getCobranca(idVaga);

        if (cobranca == null || !placaText.equals(cobranca.getVeiculo().getPlaca())) {
            showMessage("Cobrança não encontrada para os dados fornecidos.");
            return;
        }

        // Definindo a data de pagamento como Timestamp
        Timestamp dataPagamento = Timestamp.valueOf(dataSaida);
        
        // Salvando pagamento com data correta
        pagamentos.salvarPagamento(cobranca, dataPagamento);
        
        boolean removido = cobrancas.removerCobranca(cobranca);
        if (removido) {
            VagabdDAO vagabdDAO = VagabdDAO.getInstance(cobranca.getIdEstacionamento());
            boolean vagaLiberada = vagabdDAO.liberarVaga(idVaga);

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

        if (idVaga == null || placaText.isEmpty()) {
            showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
            return 0;
        }

        Cobranca cobranca = cobrancas.getCobranca(idVaga);

        if (cobranca == null || cobranca.getVeiculo() == null || !placaText.equals(cobranca.getVeiculo().getPlaca())) {
            showMessage("Cobrança não encontrada para os dados fornecidos.");
            return 0;
        }

        long diferencaEmMinutos = Duration.between(cobranca.getHoraEntrada(), dataSaida).toMinutes();
        cobranca.setTempoTotal((int) diferencaEmMinutos);

        double valor = calculoValorParcial(calculoFracao(diferencaEmMinutos));
        valor = valor > 50 ? 50 : valor;
        cobranca.setValorTotal(valor);
        cobrancas.atualizarCobranca(cobranca);
        view.getValor().setText(String.format("R$ %.2f", valor));
        return valor;
    }
    
    private Object[] recuperarDadosVaga() {
        int selectedRow = view.getVagasTable().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Selecione uma vaga para confirmar o pagamento.");
            return null;
        }

        // Recupera os dados da tabela com o tipo correto de cada coluna
        Integer idVaga = (Integer) view.getVagasTable().getValueAt(selectedRow, 0); // Coluna ID como Integer
        String tipoVaga = (String) view.getVagasTable().getValueAt(selectedRow, 1); // Coluna Tipo
        String status = (String) view.getVagasTable().getValueAt(selectedRow, 2);   // Coluna Status
        String placaText = (String) view.getVagasTable().getValueAt(selectedRow, 3); // Coluna Placa

        return new Object[]{idVaga, placaText, tipoVaga};
    }

    }

    
