package Controllers;

import Models.Vaga;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import view.*;
import java.util.Iterator;
import javax.swing.JDesktopPane;
import dao.VagaDAO;
import dao.VagabdDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListarVagasController {

    public ListarVagasView view;
    private int idEstacionamento;
    private JDesktopPane desktopPane;
    private VagabdDAO vagas;

    public ListarVagasController(JDesktopPane desktopPane, int idEstacionamento) throws IOException {
        this.idEstacionamento = idEstacionamento;
        this.view = new ListarVagasView(desktopPane);
        this.desktopPane = desktopPane;
        try {
            this.vagas = VagabdDAO.getInstance(idEstacionamento);
        } catch (SQLException ex) {
            Logger.getLogger(ListarVagasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        desktopPane.add(view);
        this.view.setVisible(true);

        // Carrega todas as vagas ao inicializar o controller
        carregarTabelaTodas();
        
        this.view.getVoltarBtn().addActionListener(e -> {
            this.view.dispose();
        });
    }

    public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }

    // Carrega todas as vagas (chamado no menu Estacionamento)
    public void carregarTabelaTodas() {
        Object[] colunas = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        Iterator<Vaga> it = vagas.getVagas().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            if(v.getIdEstacionamento() == this.idEstacionamento){
                String vaga = v.toString();
                String[] linha = vaga.split("-");
                linha[2] = "true".equals(linha[2]) ? "Desocupado" : "Ocupado";
                tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
            }
        }
        
        view.getTableVagas().setModel(tm);
    }

    // Carrega apenas vagas disponíveis
    public void carregarVagasDisponiveis() throws SQLException {
        Object[] colunas = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        Iterator<Vaga> it = vagas.getVagasDisponiveis().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String[] linha = vaga.split("-");
            linha[2] = "Desocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        
        view.getTableVagas().setModel(tm);
    }

    // Carrega apenas vagas ocupadas
    public void carregarVagasOcupadas() throws SQLException {
        Object[] colunas = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        
        Iterator<Vaga> it = vagas.getVagasOcupadas().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String[] linha = vaga.split("-");
            linha[2] = "Ocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        
        view.getTableVagas().setModel(tm);
    }
}
