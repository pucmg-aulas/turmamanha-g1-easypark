package Controllers;

import dao.VagaDAO;
import Models.Vaga;
import Models.Estacionamento;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import view.CadastroEstacionamentoView;

import javax.swing.*;
import java.util.List;

public class VagaController {

    private CadastroEstacionamentoView view;
    private VagaDAO vagaDAO;
    private Estacionamento estacionamento;
    private final String ARQUIVOEstacionamento = "./src/test/java/Archives/Estacionamentos.txt";

    public VagaController(int idEstacionamento) throws IOException {
        this.vagaDAO = VagaDAO.getInstance(idEstacionamento);
        this.estacionamento = getEstacionamento(idEstacionamento);
    }
    
    private Estacionamento getEstacionamento(int idEstacionamento) throws IOException{
        Estacionamento estacionamento = vagaDAO.lerEstacionamentoPorId(idEstacionamento);
        if(estacionamento != null){
            return estacionamento;
        }
        
        return null;
    }

   

    public List<Vaga> listarVagasDisponiveis(int idEstacionamento) {
        return vagaDAO.getVagasDisponiveis(idEstacionamento);
    }

    public List<Vaga> listarVagasOcupadas(int idEstacionamento) {
        return vagaDAO.getVagasOcupadas(idEstacionamento);
    }

    public void salvarVaga(List<Vaga> vaga, int idEstacionamento) throws IOException {
        vagaDAO.salvarVagasArquivo(vaga, idEstacionamento);
    }

    public Vaga buscarVagaPorId(int id) {
        return vagaDAO.getVagaPorId(id);
    }
    
   
}



