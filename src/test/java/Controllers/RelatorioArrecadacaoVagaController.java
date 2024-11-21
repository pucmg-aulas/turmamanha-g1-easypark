package Controllers;

import Models.Vaga;
import dao.PagamentobdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import view.RelatorioArrecadacaoVagaView;

public class RelatorioArrecadacaoVagaController {
    
    private RelatorioArrecadacaoVagaView view;
    private JDesktopPane desktopPane;
    private PagamentobdDAO pagamentos;
    private int idEstacionamento;
    
    public RelatorioArrecadacaoVagaController(JDesktopPane desktopPane, int idEstacionamento) throws SQLException, IOException{
        this.view = new RelatorioArrecadacaoVagaView(desktopPane);
        this.desktopPane = desktopPane;
        this.pagamentos = PagamentobdDAO.getInstance();
        this.idEstacionamento = idEstacionamento;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        carregarTabela();
        
        view.getVoltarBtn().addActionListener(e ->{
            this.view.dispose();
        });
        
        
    }
    
    public void carregarTabela() throws SQLException {
        Object[] colunas = {"Tipo Vaga", "Total Arrecadado (R$)"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        try{
            Map<String, Double> arrecadacao = pagamentos.getArrecadacaoPorTipoVaga(idEstacionamento);
            
            arrecadacao.forEach((tipoVaga, totalArrecadado) -> {
                tm.addRow(new Object[]{tipoVaga, String.format("%.2f", totalArrecadado)});
            });
            
            view.getArrecadacaoTable().setModel(tm);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
