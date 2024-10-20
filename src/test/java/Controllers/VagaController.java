package Controllers;

import dao.VagaDAO;
import Models.Vaga;
import Models.Estacionamento;
import view.CadastroEstacionamentoView;


import javax.swing.*;
import java.util.List;

public class VagaController {

    private CadastroEstacionamentoView view;
    private VagaDAO vagaDAO;
    private Estacionamento estacionamento;

    public VagaController(int idEstacionamento) {
        this.vagaDAO = VagaDAO.getInstance(idEstacionamento);
    }

    public void instanciarVagas(int quantidadeVagas, int idEstacionamento) {
        try{
            int qntdVagas = estacionamento.getQntdVagas();
            vagaDAO.instanciarVagas(qntdVagas, estacionamento.getId());

        }catch(Exception ex){
            JOptionPane.showMessageDialog(view, "Erro ao cadastrar estacionamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Vaga> listarVagasDisponiveis(int idEstacionamento) {
        return vagaDAO.getVagasDisponiveis(idEstacionamento);
    }

    public List<Vaga> listarVagasOcupadas(int idEstacionamento) {
        return vagaDAO.getVagasOcupadas(idEstacionamento);
    }

    public void salvarVaga(Vaga vaga, int idEstacionamento) {
        vagaDAO.salvarVagaEmArquivo(vaga, idEstacionamento);
    }

    public Vaga buscarVagaPorId(int id) {
        return vagaDAO.getVagaPorId(id);
    }

    }


