
package Controllers;

import dao.PagamentobdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import view.RelatorioFrequenciaUsoVagaView;


public class RelatorioFrequenciaUsoVagaController {
    
    private RelatorioFrequenciaUsoVagaView view;
    private JDesktopPane desktopPane;
    private PagamentobdDAO pagamentos;
    private int idEstacionamento;
    
    public RelatorioFrequenciaUsoVagaController(JDesktopPane desktopPane, int idEstacionamento) throws SQLException, IOException{
        this.view = new RelatorioFrequenciaUsoVagaView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.desktopPane = desktopPane;
        this.pagamentos = PagamentobdDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
        carregarTabela();
    }
    
    public void carregarTabela() throws SQLException {

        Object[] colunas = {"Tipo Vaga", "Arrecadação Média (R$)", "Ocupação Média (min)"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        try {
            Map<String, Map<String, Double>> arrecadacaoETempo = pagamentos.buscarTempoMedioEOcupacao(idEstacionamento);

            arrecadacaoETempo.forEach((tipoVaga, dados) -> {
                Double faturamentoMedio = dados.get("FaturamentoMedioPorUso");
                Double tempoMedio = dados.get("TempoMedioOcupacao");

                tm.addRow(new Object[]{
                    tipoVaga,
                    String.format("%.2f", faturamentoMedio),
                    String.format("%.2f", tempoMedio)
                });
            });

            view.getTabela().setModel(tm);

        } catch (SQLException e) {
            System.out.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }
}
