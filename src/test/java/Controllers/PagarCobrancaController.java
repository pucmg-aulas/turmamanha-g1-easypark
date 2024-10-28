package Controllers;

import Models.Cobranca;
import Models.Vaga;
import dao.CobrancaDAO;
import dao.VagaDAO;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import view.PagarCobrancaView;


public class PagarCobrancaController {
    private PagarCobrancaView view;
   // private final CobrancaDAO cobrancaDAO;
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
        
        view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
        
        carregarVagasOcupadas();
        
        view.getConfirmarBtn().addActionListener(e ->{
            
        });
        
    }
    
    private void sair(){
        view.dispose();
    }
    
     private void carregarVagasOcupadas() {
        Object colunas[] = {"ID", "Tipo", "Status", "Placa"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagasOcupadas().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            linha[2] = "Ocupado";
            String linha3 = cobrancas.getPlacaCobranca(Integer.parseInt(linha[0]));
            tm.addRow(new Object[]{linha[0], linha[1], linha[2], linha3});
        }
        view.getVagasTable().setModel(tm);
    }
    
//    public void pagarCobranca(Cobranca cobranca) {
//        this.cobrancaDAO.removerCobranca(cobranca.getVaga().getId());
//    }
    
//    public List<Cobranca> listarCobrancas(Integer estacionamento) {
//        return this.cobrancaDAO.listarCobrancas(estacionamento);
//    }
}
