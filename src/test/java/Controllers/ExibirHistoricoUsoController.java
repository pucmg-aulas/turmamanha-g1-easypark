
package Controllers;

import dao.HistoricoUsoDAO;
import Models.HistoricoUso;
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
    private HistoricoUsoDAO historicoUsoDAO;
    private String clienteCpf;
    
    public ExibirHistoricoUsoController(JDesktopPane desktopPane, String cpf) throws IOException {
        this.view = new ExibirHistoricoUsoView(desktopPane);
        this.historicoUsoDAO = HistoricoUsoDAO.getInstance();
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
        Object colunas[] = { "Nome Estacionamento", "Tipo Vaga", "Placa Veículo", "Data Entrada", "Data Saída", "Tempo de Permanência (min)", "Valor(R$)" };
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        try {
            // Lê todos os históricos
            List<HistoricoUso> todosHistoricos = historicoUsoDAO.lerHistoricos();
            boolean registrosEncontrados = false;

            // Itera sobre os históricos e verifica se o CPF corresponde
            for (HistoricoUso historico : todosHistoricos) {
                if (historico.getCpfCliente().equals(clienteCpf)) {
                    registrosEncontrados = true;

                    // Monta os dados para a linha da tabela
                    String[] linha = new String[7];
                    linha[0] = String.valueOf(historico.getIdCobranca());
                    linha[1] = String.valueOf(historico.getIdEstacionamento());
                    linha[2] = String.valueOf(historico.getIdVaga());
                    linha[3] = historico.getPlacaVeiculo();
                    linha[4] = historico.getDataEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    linha[5] = historico.getDataSaida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    linha[6] = String.valueOf(historico.calcularTempoPermanencia());

                    // Adiciona a linha na tabela
                    tm.addRow(linha);
                }
            }

            // Verifica se existem registros
            if (!registrosEncontrados) {
                JOptionPane.showMessageDialog(view, "Nenhum histórico encontrado para o CPF: " + clienteCpf);
            }

            // Atualiza a tabela com os dados
            view.getTableHistorico().setModel(tm);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar histórico: " + e.getMessage());
        }
    }
}
 