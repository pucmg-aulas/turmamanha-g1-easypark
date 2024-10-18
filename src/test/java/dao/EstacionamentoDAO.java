package dao;

import Models.Estacionamento;

import java.util.ArrayList;
import java.util.List;

public class EstacionamentoDAO extends AbstractDAO {

    private List<Estacionamento> estacionamentos;
    private static EstacionamentoDAO instance;
    private final String Arquivo = Estacionamento.getArquivoPath();

    private EstacionamentoDAO() {
        estacionamentos = ler(Arquivo);
        if(estacionamentos == null){
            estacionamentos = new ArrayList<>();
        }
    }
    public static EstacionamentoDAO getInstance() {
        if (instance == null) {
            instance = new EstacionamentoDAO();
        }
        return instance;
    }

    public void addEstacionamento(Estacionamento estacionamento) {
        estacionamentos.add(estacionamento);
        gravar(Arquivo, estacionamentos);
    }

    public void removeEstacionamento(Estacionamento estacionamento) {
        estacionamentos.remove(estacionamento);
        gravar(Arquivo, estacionamentos);
    }

    public List<Estacionamento> getAllEstacionamentos(){
        return estacionamentos;
    }
    
    public Estacionamento getEstacionamentoPorId(int id) {
        for (Estacionamento estacionamento : estacionamentos) {
            if (estacionamento.getId() == id) {
                return estacionamento;
            }
        }
        return null;
    }
}
