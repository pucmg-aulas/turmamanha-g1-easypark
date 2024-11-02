
package Controllers;

import javax.swing.JDesktopPane;
import view.ValorArrecadadoView;

public class ValorArrecadadoController {

    private JDesktopPane desktopPane;
    private int idEstacionamento;
    private ValorArrecadadoView view;
    
    public ValorArrecadadoController(JDesktopPane desktopPane, int idEstacionamento){
        this.view = new ValorArrecadadoView(desktopPane);
        this.desktopPane = desktopPane;
        this.idEstacionamento = idEstacionamento;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        
        this.view.getVoltarBtn().addActionListener(e -> {
            sair();
        });
        
        
        
    }
    
    
    
    private void sair() {
        this.view.dispose();
    }
}
