package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento implements EncontrarMaior {
    private int id;
    private String nome;
    private String rua;
    private String numero;
    private String bairro;
    private List<Vaga> vagas;
    private static final String ARQUIVO = "./src/test/java/Archives/Estacionamentos.txt";

    public Estacionamento(String nome, String rua, String bairro, String numero, int qntdVagas) {
        this.id = EncontrarMaiorId() + 1;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>(qntdVagas);
    }


    private void instanciarVagas(int qntdVagas) {
        for (int i = 0; i < qntdVagas; i++) {
            vagas.add(new Vaga(i + 1) {
                // Implementação concreta da classe Vaga
            });
        }
    }

    @Override
    public int EncontrarMaiorId() {
        File arquivo = new File(ARQUIVO);
        int maiorId = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    int idAtual = Integer.parseInt(linha.replace("ID: ", "").trim());
                    if (idAtual > maiorId) {
                        maiorId = idAtual;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para obter o maior ID: " + e.getMessage());
        }

        return maiorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
    }
    
    public static String getArquivoPath(){
        return ARQUIVO;
    }
}
