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

public class ListarVagasController {

    private ListarVagasView view;
    private int idEstacionamento;
    private JDesktopPane desktopPane;
    private VagaDAO vagas;

    public ListarVagasController(JDesktopPane desktopPane,int idEstacionamento) throws IOException {
        this.idEstacionamento = idEstacionamento;
        this.view = new ListarVagasView(desktopPane);
        this.desktopPane = desktopPane;
        this.vagas = VagaDAO.getInstance(idEstacionamento);
        
        desktopPane.add(view);
        this.view.setVisible(true);

        // Carregando todas as vagas ao inicializar o controller
        carregarTabelaTodas();
        
        this.view.getVoltarBtn().addActionListener(e -> {
            this.view.dispose();
        });

        // Configurando botões da view
        //this.view.getBtnVagasOcupadas().addActionListener(e -> carregaTabelaOcupadas());
        //this.view.getBtnVagasDisponiveis().addActionListener(e -> carregaTabelaDisponiveis());
        //this.view.addListarVagasBtnActionListener(e -> carregaTabelaTodas());

    }

    public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }

    // Carrega todas as vagas (vai ser chamada no menu Estacionamento)
    private void carregarTabelaTodas() {
        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagas().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            if("true".equals(linha[2])){
                linha[2] = "Desocupado";
            }else{
                linha[2] = "Ocupado";
            }
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        view.getTableVagas().setModel(tm);
    }

     private void carregarVagasDisponíveis() {
        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagasDisponiveis().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            linha[2] = "Desocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        view.getTableVagas().setModel(tm);
    }
     
      private void carregarVagasOcupadas() {
        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);
        tm.setNumRows(0);
        Iterator<Vaga> it = vagas.getVagasOcupadas().iterator();
        while(it.hasNext()){
            Vaga v = it.next();
            String vaga = v.toString();
            String linha[] = vaga.split("-");
            linha[2] = "Ocupado";
            tm.addRow(new Object[]{linha[0], linha[1], linha[2]});
        }
        view.getTableVagas().setModel(tm);
    }
}
