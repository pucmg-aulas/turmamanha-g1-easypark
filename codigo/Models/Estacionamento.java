package Models;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private String nome;
    private String rua;
    private String bairro;
    private int numero;
    private List<Vaga> vagas;

    public Estacionamento(String nome, String rua, String bairro, int numero) {
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.vagas = new ArrayList<>();
    }

    public void adicionarVaga(Vaga vaga) {
        this.vagas.add(vaga);
    }

    public Vaga getVagaPorId(int id) {
        for (Vaga v : vagas) {
            if (v.getId() == id) {
                return v;
            }
        }

        return null;
    }

    public String getNome() {
        return nome;
    }


    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }


    public int getNumero() {
        return numero;
    }



    public List<Vaga> getVagas() {
        return vagas;
    }

}
