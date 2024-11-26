package Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import view.RelatoriosView;

public class RelatoriosController {

    private RelatoriosView view;
    private RelatorioArrecadacaoVagaController relatorioArrecadacao;
    private RelatorioFrequenciaUsoVagaController relatorioFrequencia;
    private JDesktopPane desktopPane;
    private int idEstacionamento;

    public RelatoriosController(JDesktopPane desktopPane, int idEstacionamento) {
        this.view = new RelatoriosView(desktopPane);
        this.idEstacionamento = idEstacionamento;

        desktopPane.add(view);
        this.view.setVisible(true);

        this.view.addArrecadacaoBtnActionListener(e -> {
            try {
                this.relatorioArrecadacao = new RelatorioArrecadacaoVagaController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                Logger.getLogger(RelatoriosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatoriosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.view.addFrequenciaBtnActionListener(e -> {
            try {
                this.relatorioFrequencia = new RelatorioFrequenciaUsoVagaController(desktopPane, idEstacionamento);
            } catch (SQLException ex) {
                Logger.getLogger(RelatoriosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RelatoriosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
