package Controllers;

import Models.Cobranca;
import Models.Vaga;
import dao.CobrancaDAO;
import dao.VagaDAO;
import dao.PagamentoDAO;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.PagarCobrancaView;
import Models.ITipo;
import Models.VagaIdoso;
import Models.VagaPCD;
import Models.VagaRegular;
import Models.VagaVIP;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagarCobrancaController {

    private PagarCobrancaView view;
    private Cobranca modelCobranca;
    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private VagaDAO vagas;
    private CobrancaDAO cobrancas;
    private PagamentoDAO pagamentos;
    private LocalDateTime dataSaida;

    public PagarCobrancaController(JDesktopPane desktopPane, int idEstacionamento, LocalDateTime dataSaida) throws IOException {
        this.view = new PagarCobrancaView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        this.cobrancas = CobrancaDAO.getInstance();
        this.pagamentos = PagamentoDAO.getInstance();
        this.dataSaida = dataSaida;
        
        desktopPane.add(view);
        this.view.setVisible(true);

        // Configurações dos botões
        view.getVoltarBtn().addActionListener(e -> sair());
        view.getConfirmarBtn().addActionListener(e -> {
            confirmarPagamento();
            limparCampos();
            carregarVagasOcupadas();
        });


        carregarVagasOcupadas();
        
        view.getVagasTable().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    mostrarValor();
                } catch (IOException ex) {
                    Logger.getLogger(PagarCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
            String placa = cobrancas.getCobranca(Integer.parseInt(linha[0])).getPlacaVeiculo();
            tm.addRow(new Object[]{linha[0], linha[1], linha[2], placa});
        }

        view.getVagasTable().setModel(tm);
    }

    private void confirmarPagamento() {
        try {
            Object[] dadosVaga = recuperarDadosVaga();
            
             if(dadosVaga == null){
               showMessage("Dados inválidos.");
               return;
            }
             
            Integer idVaga = (Integer) dadosVaga[0];
            String placaText = (String) dadosVaga[1];
            String tipoVaga = (String) dadosVaga[2];


         
            if (idVaga == null || placaText.isEmpty()) {
                showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
                return;
            }

            // Busca a cobrança correspondente no DAO
            Cobranca cobranca = cobrancas.lerCobrancas().stream()
                .filter(c -> c.getIdVaga() == idVaga && c.getPlacaVeiculo().equals(placaText))
                .findFirst()
                .orElse(null);

            if (cobranca == null) {
                showMessage("Cobrança não encontrada para os dados fornecidos.");
                return;
            }
            cobranca.setValorTotal(mostrarValor());
            pagamentos.salvarPagamento(cobranca);
            
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
    
    private void limparCampos(){
        view.getValor().setText("");
    }
    
    private long calculoFracao(long diferencaEmMinutos){
       long qtdFracao = diferencaEmMinutos/modelCobranca.FRACAOTEMPO;
       return qtdFracao;
    }
    
    private long calculoValorParcial(long qtdFracao){
        long valorTotal = qtdFracao*modelCobranca.VALORTEMPO;
        return valorTotal;
    }
    
    private double mostrarValor() throws IOException {
        Object[] dadosVaga = recuperarDadosVaga();
        Integer idVaga = (Integer) dadosVaga[0];
        String placaText = (String) dadosVaga[1];
        String tipoVaga = (String) dadosVaga[2];

        if (idVaga == null || placaText.isEmpty()) {
            showMessage("Preencha o ID da Vaga e a Placa do Veículo.");
            return 0;
        }

        // Busca a cobrança correspondente no DAO
        Cobranca cobranca = cobrancas.lerCobrancas().stream()
                .filter(c -> c.getIdVaga() == idVaga && c.getPlacaVeiculo().equals(placaText))
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
                vagaEspecifica.setTipo(new VagaRegular(idEstacionamento, idVaga));;
                break;
            case "VIP":
                vagaEspecifica.setTipo(new VagaVIP(idEstacionamento, idVaga));
                break;
        }

        double valor = vagaEspecifica.calculoValor(calculoValorParcial(calculoFracao(diferencaEmMinutos)));
        String valorImpresso = String.format("R$ %.2f", valor);
        view.getValor().setText(valorImpresso);
        return valor;
        
    }
    
    private Object[] recuperarDadosVaga() {
        // Verifica se uma vaga foi selecionada na tabela
        int selectedRow = view.getVagasTable().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Selecione uma vaga para confirmar o pagamento.");
            return null; // Retorna null se não houver linha selecionada
        }

        // Recupera os dados inseridos nos campos de texto
        String idVagaText = (String) view.getVagasTable().getValueAt(selectedRow, 0);
        Integer idVaga = Integer.parseInt(idVagaText);
        String placaText = (String) view.getVagasTable().getValueAt(selectedRow, 3);
        String tipoVaga = (String) view.getVagasTable().getValueAt(selectedRow, 1);

        return new Object[]{idVaga, placaText, tipoVaga}; // Retorna um array de Object
    }
}
