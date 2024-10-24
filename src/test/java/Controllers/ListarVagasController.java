package Controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import view.*;
import Models.Vaga;
import java.util.List;

public class ListarVagasController {

    //Corrigir o idEstacionamento que foi selecionado 
    private ListarVagasView view;
    private VagaController vagaController;
    private int idEstacionamento;

    public ListarVagasController(int idEstacionamento) throws IOException {
        this.idEstacionamento = idEstacionamento;
        this.view = new ListarVagasView();
        this.vagaController = new VagaController(idEstacionamento);

        // Carregando todas as vagas ao inicializar o controller
        carregaTabelaTodas();

        // Configurando botões da view
        //this.view.getBtnVagasOcupadas().addActionListener(e -> carregaTabelaOcupadas());
        //this.view.getBtnVagasDisponiveis().addActionListener(e -> carregaTabelaDisponiveis());
        //this.view.addListarVagasBtnActionListener(e -> carregaTabelaTodas());

        this.view.setVisible(true);
    }

    public JScrollPane getScrollPane() {
        return view.getScrollPane();
    }

    // Carrega todas as vagas (vai ser chamada no menu Estacionamento)
    private void carregaTabelaTodas() {
        List<Vaga> vagas = vagaController.listarVagasDisponiveis(idEstacionamento);
        vagas.addAll(vagaController.listarVagasOcupadas(idEstacionamento));

        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        for (Vaga vaga : vagas) {
            tm.addRow(new Object[]{vaga.getId(), vaga.getTipo(), vaga.getStatus()});
        }

        view.getTbVagas().setModel(tm);
    }

    // Carrega apenas as vagas ocupadas (vai ser chamada no pagar Cobranca)
    private void carregaTabelaOcupadas() {
        List<Vaga> vagasOcupadas = vagaController.listarVagasOcupadas(idEstacionamento);

        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        for (Vaga vaga : vagasOcupadas) {
            tm.addRow(new Object[]{vaga.getId(), vaga.getTipo(), vaga.getStatus()});
        }

        view.getTbVagas().setModel(tm);
    }

    // Carrega apenas as vagas disponíveis (vai ser chamada no gerar cobranca)
    private void carregaTabelaDisponiveis() {
        List<Vaga> vagasDisponiveis = vagaController.listarVagasDisponiveis(idEstacionamento);

        Object colunas[] = {"ID", "Tipo", "Status"};
        DefaultTableModel tm = new DefaultTableModel(colunas, 0);

        for (Vaga vaga : vagasDisponiveis) {
            tm.addRow(new Object[]{vaga.getId(), vaga.getTipo(), vaga.getStatus()});
        }

        view.getTbVagas().setModel(tm);
    }
}
