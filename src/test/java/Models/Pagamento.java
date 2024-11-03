package Models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pagamento {
    private int idPagamento;
    private LocalDateTime dataPagamento;
    private int idEstacionamento;
    private double valorPago;
    private String placaVeiculo;
    private ITipo tipoVaga;
    private int tempoTotal;
    
    private static final String Arquivo = "./src/test/java/Archives/Pagamentos.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Pagamento() throws IOException {
        this.idPagamento = encontrarMaiorId() + 1;
        this.dataPagamento = LocalDateTime.now();
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }
    
    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    private int encontrarMaiorId() throws IOException {
        File arquivo = new File(Arquivo);
        int maiorId = 0;

        if (!arquivo.exists()) {
            return maiorId;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                int idAtual = Integer.parseInt(dados[0]);

                if (idAtual > maiorId) {
                    maiorId = idAtual;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para obter o maior ID: " + e.getMessage());
        }
        return maiorId;
    }


    public int getIdEstacionamento() {
        return idEstacionamento;
    }

    public void setIdEstacionamento(int idEstacionamento) {
        this.idEstacionamento = idEstacionamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public ITipo getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(ITipo tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }
    
    
    
    
    
}
