package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    // Método para gravar os dados do estacionamento em um arquivo de texto
    public void gravarEmArquivo() {
        File arquivo = new File("entrada.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            escritor.write("Estacionamento: " + this.nome + "\n");
            escritor.write("Endereço: " + this.rua + ", " + this.numero + " - " + this.bairro + "\n");
            escritor.write("Vagas Disponíveis:\n");

            for (Vaga vaga : vagas) {
                escritor.write("  - Vaga ID: " + vaga.getId() + "\n");
            }

            System.out.println("Dados do estacionamento gravados em " + arquivo.getName());
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }
}
