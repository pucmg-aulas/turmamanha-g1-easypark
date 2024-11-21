
package Controllers;

import dao.PagamentobdDAO;
import javax.swing.JDesktopPane;
import view.RelatorioFrequenciaUsoVagaView;


public class RelatorioFrequenciaUsoVagaController {
    
    private RelatorioFrequenciaUsoVagaView view;
    private JDesktopPane desktopPane;
    private PagamentobdDAO pagamentos;
    private int idEstacionamento;
    
    public RelatorioFrequenciaUsoVagaController(JDesktopPane desktopPane, int idEstacionamento){
        this.idEstacionamento = idEstacionamento;
        this.desktopPane = desktopPane;
        
        desktopPane.add(view);
        this.view.setVisible(true);
    }
}
