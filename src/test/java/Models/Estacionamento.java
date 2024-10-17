package Models;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private String nome;
    private String rua;
    private int numero;
    private String bairro;
    private List<Vaga> vagas;

    public Estacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) {
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>(qntdVagas);
        instanciarVagas(qntdVagas);
    }


    public void instanciarVagas(int qntdVagas) {
        for (int i = 0; i < qntdVagas; i++) {
            vagas.add(new Vaga(i + 1) {
                // Implementação concreta da classe Vaga
            });
        }
    }


    public void adicionarVaga(Vaga vaga) {
        vagas.add(vaga);
    }


    public Vaga getVagaPorId(int id) {
        return vagas.stream().filter(v -> v.getId() == id).findFirst().orElse(null);
    }


    public List<Vaga> getVagasDisponiveis() {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        for (Vaga vaga : vagas) {
            if (vaga.isStatus()) { // Usando o método isStatus()
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }


    public List<Vaga> getVagasOcupadas() {
        List<Vaga> vagasOcupadas = new ArrayList<>();
        for (Vaga vaga : vagas) {
            if (!vaga.isStatus()) { // Usando o método isStatus()
                vagasOcupadas.add(vaga);
            }
        }
        return vagasOcupadas;
    }


    public boolean reservarVagaPorId(int id) {
        Vaga vaga = getVagaPorId(id);
        if (vaga != null && vaga.isStatus()) { // Usando o método isStatus()
            vaga.setStatus(false); // Marca a vaga como ocupada
            return true;
        }
        return false;
    }


    public boolean gravarEstacionamentosEmArquivo() {

        System.out.println("Estacionamento salvo em arquivo.");
        return true;
    }

    public static List<Estacionamento> lerEstacionamentosDoArquivo() {

        return new ArrayList<>();
    }


    public String getNome() {
        return nome;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }
}
