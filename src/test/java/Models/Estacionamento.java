//package Models;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Estacionamento implements EncontrarMaior{
//    private int id;
//    private String nome;
//    private String rua;
//    private String bairro;
//    private int numero;
//    private int qntdVagas;
//    private List<Vaga> vagas;
//    private static String arquivoEstacionamento = "./codigo/src/Archives/Estacionamentos.txt";
//
//    public Estacionamento(String nome, String rua, String bairro, int numero, int qntdVagas) {
//        this.nome = nome;
//        this.rua = rua;
//        this.bairro = bairro;
//        this.numero = numero;
//        this.qntdVagas = qntdVagas;
//        this.vagas = new ArrayList<>();
//        this.id = EncontrarMaiorId() + 1;
//        this.instanciarVagas();
//    }
//
//    public void instanciarVagas() {
//        int vagasRegulares = (int) (qntdVagas * 0.5);
//        int vagasIdoso = (int) (qntdVagas * 0.2);
//        int vagasPCD = (int) (qntdVagas * 0.2);
//        int vagasVIP = (int) (qntdVagas * 0.1);
//        int idVaga = 1;
//
//        int totalInstanciadas = vagasRegulares + vagasIdoso + vagasPCD + vagasVIP;
//
//        // Ajusta para compensar qualquer arredondamento que cause diferença no total
//        while (totalInstanciadas < qntdVagas) {
//            vagasRegulares++;
//            totalInstanciadas++;
//        }
//
//        for (int i = 0; i < vagasRegulares; i++) {
//            vagas.add(new VagaRegular(idVaga++));
//        }
//        for (int i = 0; i < vagasIdoso; i++) {
//            vagas.add(new VagaIdoso(idVaga++));
//        }
//        for (int i = 0; i < vagasPCD; i++) {
//            vagas.add(new VagaPCD(idVaga++));
//        }
//        for (int i = 0; i < vagasVIP; i++) {
//            vagas.add(new VagaVIP(idVaga++));
//        }
//    }
//
//    @Override
//    public int EncontrarMaiorId() {
//        File arquivo = new File(arquivoEstacionamento);
//        int maiorId = 0;
//
//        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
//            String linha;
//            while ((linha = leitor.readLine()) != null) {
//                if (linha.startsWith("ID: ")) {
//                    int idAtual = Integer.parseInt(linha.replace("ID: ", "").trim());
//                    if (idAtual > maiorId) {
//                        maiorId = idAtual;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Erro ao ler o arquivo para obter o maior ID: " + e.getMessage());
//        }
//
//        return maiorId;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getRua() {
//        return rua;
//    }
//
//    public void setRua(String rua) {
//        this.rua = rua;
//    }
//
//    public String getBairro() {
//        return bairro;
//    }
//
//    public void setBairro(String bairro) {
//        this.bairro = bairro;
//    }
//
//    public int getNumero() {
//        return numero;
//    }
//
//    public void setNumero(int numero) {
//        this.numero = numero;
//    }
//
//    public int getQntdVagas() {
//        return qntdVagas;
//    }
//
//    public void setQntdVagas(int qntdVagas) {
//        this.qntdVagas = qntdVagas;
//    }
//
//    public List<Vaga> getVagas() {
//        return vagas;
//    }
//
//    public void setVagas(List<Vaga> vagas) {
//        this.vagas = vagas;
//    }
//
//    public static String getArquivoEstacionamento() {
//        return arquivoEstacionamento;
//    }
//
//    public static void setArquivoEstacionamento(String arquivoEstacionamento) {
//        Estacionamento.arquivoEstacionamento = arquivoEstacionamento;
//    }
//}