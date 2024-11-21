/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import dao.EstacionamentobdDAO;
import dao.PagamentoDAO;
import dao.PagamentobdDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    private RelatorioArrecadacaoVagaController listarVagasController;
    private GerarCobrancaController gerarCobrancaController;
    private PagarCobrancaController pagarCobrancaController;
    private ValorArrecadadoController valorArrecadadoController;
    private RankingClientesController rankingClientesController;
    private int idEstacionamento;
    private EstacionamentobdDAO estacionamentoDAO;
    private JDesktopPane desktopPane;
    private PagamentobdDAO pagamentos;
    
    public MenuEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento) throws IOException, SQLException{
        this.view = new MenuEstacionamentoView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        this.pagamentos = PagamentobdDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
  
        
        this.view.addDetalhesBtnActionListener(e -> {
            try {
                this.exibirDetalhesController = new ExibirDetalhesEstacionamentoController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.addListarVagasBtnActionListener(e -> {
            try {
                try {
                    this.listarVagasController = new RelatorioArrecadacaoVagaController(desktopPane, idEstacionamento);
                } catch (SQLException ex) {
                    Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.addGerarCobrancaBtnActionListener(e -> {
            try {
                this.gerarCobrancaController = new GerarCobrancaController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                showMessageDialog(null, "Ocorreu um erro ao iniciar tela de geraçao da cobrança");
            } catch (SQLException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.addPagarCobrancaBtnActionListener(e -> {
            try {
                String dataSaida = PagamentoDAO.getHoraSaida();
                LocalDateTime dataSaida2 = LocalDateTime.parse(dataSaida, PagamentoDAO.FORMATTER);
                this.pagarCobrancaController = new PagarCobrancaController(desktopPane, idEstacionamento, dataSaida2);
            } catch (IOException ex) {
                showMessageDialog(null, "Ocorreu um erro ao iniciar tela de pagamento da cobrança");
            } catch (SQLException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.view.getVoltarBtn().addActionListener(e ->{
            sair();
        });
         
        this.view.getValorArrecadadoBtn().addActionListener(e -> {
            try {
                this.valorArrecadadoController = new ValorArrecadadoController(desktopPane, idEstacionamento);
            } catch (Exception ex) {
                showMessageDialog(null, "Erro ao abrir a tela de valor arrecadado: " + ex.getMessage());
            }
        });
        
        this.view.getRankingClientes().addActionListener(e ->{
            try {
                this.rankingClientesController = new RankingClientesController(desktopPane, idEstacionamento);
            } catch (IOException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MenuEstacionamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private void sair(){
         this.view.dispose();
     }
}
