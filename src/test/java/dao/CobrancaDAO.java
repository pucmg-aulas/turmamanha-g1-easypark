package dao;

import Models.Cliente;
import Models.Cobranca;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CobrancaDAO {

    private static final String Arquivo = "./src/test/java/Archives/Cobrancas.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<Cobranca> cobrancas;
    private static CobrancaDAO instance;
    
    private CobrancaDAO(){
        cobrancas = lerCobrancas();
        if(cobrancas == null){
            cobrancas = new ArrayList<>();
        }
    }
    
    public static CobrancaDAO getInstance(){
        if(instance == null){
            instance = new CobrancaDAO();
        }
        return instance;
    }
    
    public Cobranca getCobranca(int idVaga){

        try (BufferedReader leitor = new BufferedReader(new FileReader(Arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                int idCobranca = Integer.parseInt(dados[0]);
                int idVagaAtual = Integer.parseInt(dados[1]);
                String placaVeiculo = dados[2];
                int idEstacionamento = Integer.parseInt(dados[3]);
                LocalDateTime dataEntrada = LocalDateTime.parse(dados[4], formatter);
                if(idVagaAtual == idVaga){
                    return new Cobranca(idCobranca, idVagaAtual, idEstacionamento, placaVeiculo, dataEntrada);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de cobranças: " + e.getMessage());
        }

        return null;
    }
    
    
    public boolean gerarCobranca(Cobranca cobranca) throws IOException{
        cobrancas.add(cobranca);
        return salvarCobrancaArquivo(cobrancas);
    }
    
    public boolean salvarCobrancaArquivo(List<Cobranca> cobrancas) throws IOException {
        File arquivo = new File(Arquivo);

        try {
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Cria o arquivo se não existir
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Cobranca c : cobrancas) {
                    String dataEntrada = c.getHoraEntrada().format(formatter);
                    bw.write(c.getIdCobranca() + ";" + c.getIdVaga() + ";" + c.getPlacaVeiculo() + ";" + c.getIdEstacionamento() + ";" + dataEntrada);
                    bw.newLine();
                }
                return true; // Indica que a operação foi bem-sucedida
            }
        } catch (IOException e) {
            throw new IOException("Erro ao salvar cobranças: " + e.getMessage());
        }
    }


    // Método para ler todas as cobranças do arquivo
   public List<Cobranca> lerCobrancas() {
    List<Cobranca> cobrancas = new ArrayList<>();

    File arquivo = new File(Arquivo);
    if (!arquivo.exists()) {
        System.out.println("Arquivo de cobranças não encontrado.");
        return cobrancas; 
    }
    
    try (BufferedReader leitor = new BufferedReader(new FileReader(Arquivo))) {
        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] dados = linha.split(";");

            if (dados.length < 5 || dados[0].isEmpty() || dados[1].isEmpty() || dados[3].isEmpty() || dados[4].isEmpty()) {
                System.out.println("Linha inválida ou incompleta: " + linha);
                continue; 
            }

            try {
                int idCobranca = Integer.parseInt(dados[0]);
                int idVaga = Integer.parseInt(dados[1]);
                String placaVeiculo = dados[2];
                int idEstacionamento = Integer.parseInt(dados[3]);
                LocalDateTime horaEntrada = LocalDateTime.parse(dados[4], formatter);

                Cobranca cobrancaAtual = new Cobranca(idCobranca, idVaga, idEstacionamento, placaVeiculo, horaEntrada);
                cobrancas.add(cobrancaAtual);

            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println("Erro ao processar a linha: " + linha + " - " + e.getMessage());
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo de cobranças: " + e.getMessage());
    }

    return cobrancas.isEmpty() ? null : cobrancas;
}
    // Método para remover uma cobrança do arquivo
    public boolean removerCobranca(Cobranca cobranca) throws IOException {
        // Remove a cobrança da lista
        boolean removido = cobrancas.remove(cobranca);

        // Salva as cobranças atualizadas no arquivo
        if (removido) {
            return salvarCobrancaArquivo(cobrancas);
        }
        return false; // Retorna false se a cobrança não foi encontrada na lista
    }

    
    
}
