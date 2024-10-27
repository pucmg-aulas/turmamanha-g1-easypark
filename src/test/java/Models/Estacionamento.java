package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento{
    private int id;
    private String nome;
    private String rua;
    private int numero;
    private String bairro;
    private List<Vaga> vagas;
    private int qntdVagas;
    private static final String ARQUIVO = "./src/test/java/Archives/Estacionamentos.txt";

    public Estacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) throws FileNotFoundException {
        this.id = EncontrarMaiorId() + 1;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>();
        this.qntdVagas = qntdVagas;
    }

    public Estacionamento(int id, String nome, String rua, String bairro, int numero, int qntdVagas) {
        this.id = id;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.vagas = new ArrayList<>();
        this.qntdVagas = qntdVagas;
    }


//    private void instanciarVagas(int qntdVagas) {
//        for (int i = 0; i < qntdVagas; i++) {
//            vagas.add(new Vaga(i + 1) {
//                // Implementação concreta da classe Vaga
//            });
//        }
//    }

    public int getQntdVagas(){
        return qntdVagas;
    }


    public int EncontrarMaiorId() throws FileNotFoundException {
        File arquivo = new File(ARQUIVO);
        int maiorId = 0;

        if(!arquivo.exists()){
            return maiorId;
        }
        
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                int idAtual = Integer.parseInt(dados[0]);
                
                if(idAtual > maiorId){
                    maiorId = idAtual;
                } 
               }
            }catch (IOException e) {
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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
    public double calcularValorMedioUso(List<Pagamento> pagamentos) {
        double soma = pagamentos.stream().mapToDouble(Pagamento::getValor).sum();
        return pagamentos.isEmpty() ? 0 : soma / pagamentos.size();
    }

    public List<ClienteRanking> gerarRankingClientes(List<Pagamento> pagamentos) {
        return pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente, Collectors.summingDouble(Pagamento::getValor)))
                .entrySet().stream()
                .map(entry -> new ClienteRanking(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingDouble(ClienteRanking::getTotalGasto).reversed())
                .collect(Collectors.toList());
    }
}
