/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import dao.EstacionamentoDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import static javax.swing.JOptionPane.showMessageDialog;
import view.MenuEstacionamentoView;



/**
 *
 * @author user
 */
public class MenuEstacionamentoController {
    private MenuEstacionamentoView view;
    private ExibirDetalhesEstacionamentoController exibirDetalhesController;
    private ListarVagasController listarVagasController;
    private GerarCobrancaController gerarCobrancaController;
    private PagarCobrancaController pagarCobrancaController;
    private int idEstacionamento;
    private EstacionamentoDAO estacionamentoDAO;
    private JDesktopPane desktopPane;
    
    public MenuEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento){
        this.view = new MenuEstacionamentoView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        
        desktopPane.add(view);
        this.view.setVisible(true);
  
        
        this.view.addDetalhesBtnActionListener(e -> {
            try {
                this.exibirDetalhesController = new ExibirDetalhesEstacionamentoController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.addListarVagasBtnActionListener(e -> {
            try {
                this.listarVagasController = new ListarVagasController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.addGerarCobrancaBtnActionListener(e -> {
            try {
                this.gerarCobrancaController = new GerarCobrancaController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                showMessageDialog(null, "Ocorreu um erro ao iniciar tela de geraçao da cobrança");
            }
        });
        
        this.view.addPagarCobrancaBtnActionListener(e -> {
            try {
                this.pagarCobrancaController = new PagarCobrancaController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                showMessageDialog(null, "Ocorreu um erro ao iniciar tela de pagamento da cobrança");
            }
        });
         
    }
}
