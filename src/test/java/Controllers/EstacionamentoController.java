package Controllers;

import Models.Estacionamento;
import Models.Vaga;

import java.util.ArrayList;
import java.util.List;

public class EstacionamentoController {
    private List<Estacionamento> estacionamentos;

    public EstacionamentoController() {
        this.estacionamentos = new ArrayList<>();
    }


    public void cadastrarEstacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) {
        Estacionamento estacionamento = new Estacionamento(nome, rua, bairro, numero, qntdVagas);
        estacionamentos.add(estacionamento);
        salvarEstacionamento(estacionamento);
    }


    public void adicionarVaga(int idEstacionamento, Vaga vaga) {
        Estacionamento estacionamento = buscarEstacionamentoPorId(idEstacionamento);
        if (estacionamento != null) {
            estacionamento.adicionarVaga(vaga);
        } else {
            System.out.println("Estacionamento não encontrado.");
        }
    }


    public Estacionamento buscarEstacionamentoPorId(int id) {
        return estacionamentos.stream().filter(e -> e.getNome().equals(id)).findFirst().orElse(null);
    }


    public List<Estacionamento> listarEstacionamentos() {
        return estacionamentos;
    }


    public boolean reservarVaga(int idEstacionamento, int idVaga) {
        Estacionamento estacionamento = buscarEstacionamentoPorId(idEstacionamento);
        if (estacionamento != null) {
            return estacionamento.reservarVagaPorId(idVaga);
        }
        return false;
    }


    public boolean salvarEstacionamento(Estacionamento estacionamento) {
        return estacionamento.gravarEstacionamentosEmArquivo(); // Implementar a lógica de gravação
    }


    public void exibirDetalhesEstacionamento(int idEstacionamento) {
        Estacionamento estacionamento = buscarEstacionamentoPorId(idEstacionamento);
        if (estacionamento != null) {
            System.out.println("Estacionamento: " + estacionamento.getNome());
            System.out.println("Rua: " + estacionamento.getRua() + ", " + estacionamento.getNumero());
            System.out.println("Bairro: " + estacionamento.getBairro());
            System.out.println("Quantidade de Vagas: " + estacionamento.getVagas().size());
            System.out.println("Vagas Disponíveis: " + estacionamento.getVagasDisponiveis().size());
            System.out.println("Vagas Ocupadas: " + estacionamento.getVagasOcupadas().size());
        } else {
            System.out.println("Estacionamento não encontrado.");
        }
    }
}
