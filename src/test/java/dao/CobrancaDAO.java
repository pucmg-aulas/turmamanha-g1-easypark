package dao;

import Models.Cliente;
import Models.Cobranca;
import Models.Veiculo;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CobrancaDAO {

    private static final String Arquivo = "./src/test/java/Archives/Cobrancas.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<Cobranca> cobrancas;
    private static CobrancaDAO instance;
    private VeiculoDAO veiculos;
    
    CobrancaDAO(){
        this.veiculos = VeiculoDAO.getInstance();
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
                Veiculo automovel = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                 
                if(idVagaAtual == idVaga){
                    
                    return new Cobranca(idCobranca, idVagaAtual, idEstacionamento, automovel, dataEntrada);
                   
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
                    bw.write(c.getIdCobranca() + ";" + c.getIdVaga() + ";" + c.getVeiculo().getPlaca() + ";" + c.getIdEstacionamento() + ";" + dataEntrada + ";" + c.getTempoTotal());
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

            if (dados.length < 6 || dados[0].isEmpty() || dados[1].isEmpty() || dados[3].isEmpty() || dados[4].isEmpty() || dados[5].isEmpty()) {
                System.out.println("Linha inválida ou incompleta: " + linha);
                continue; 
            }

            try {
                int idCobranca = Integer.parseInt(dados[0]);
                int idVaga = Integer.parseInt(dados[1]);
                String placaVeiculo = dados[2];
                int idEstacionamento = Integer.parseInt(dados[3]);
                LocalDateTime horaEntrada = LocalDateTime.parse(dados[4], formatter);
                int tempoTotal = Integer.parseInt(dados[5]);
                Veiculo automovel = veiculos.buscarVeiculoPorPlaca(placaVeiculo);
                
                Cobranca cobrancaAtual = new Cobranca(idCobranca, idVaga, idEstacionamento, automovel, horaEntrada);
                cobrancaAtual.setTempoTotal(tempoTotal);
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

    public boolean atualizarCobranca(Cobranca cobrancaAtualizada) throws IOException {
    // Procura a cobrança na lista de cobranças pelo ID ou ID da vaga (conforme aplicável)
    for (int i = 0; i < cobrancas.size(); i++) {
        Cobranca cobranca = cobrancas.get(i);

        if (cobranca.getIdCobranca() == cobrancaAtualizada.getIdCobranca()) { // Compara pelo ID da cobrança
            // Atualiza as informações da cobrança encontrada
            cobranca.setIdVaga(cobrancaAtualizada.getIdVaga());
            cobranca.setVeiculo(cobrancaAtualizada.getVeiculo());
            cobranca.setIdEstacionamento(cobrancaAtualizada.getIdEstacionamento());
            cobranca.setHoraEntrada(cobrancaAtualizada.getHoraEntrada());
            cobranca.setTempoTotal(cobrancaAtualizada.getTempoTotal());

            // Salva as cobranças atualizadas no arquivo
            return salvarCobrancaArquivo(cobrancas);
        }
    }

    System.out.println("Cobrança não encontrada para atualização.");
    return false; // Retorna false se a cobrança não foi encontrada
}
    
}
