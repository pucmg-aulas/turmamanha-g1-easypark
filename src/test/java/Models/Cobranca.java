package Models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cobranca {
    private int idCobranca;
    private int idVaga;
    private String placaVeiculo;
    private int idEstacionamento;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private int tempoTotal;
    public static final long VALORTEMPO = 4;
    public static final double LIMITEPRECO = 50;
    public static final long FRACAOTEMPO = 15;
    private double valorTotal;
    private static final String Arquivo = "./src/test/java/Archives/Cobrancas.txt";
private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Cobranca(int idVaga, int idEstacionamento, String placaVeiculo) throws FileNotFoundException{
        this.idCobranca = EncontrarMaior() + 1;
        this.idVaga = idVaga;
        this.placaVeiculo = placaVeiculo;
        this.idEstacionamento = idEstacionamento;
        this.horaEntrada = LocalDateTime.now();
        this.tempoTotal = 0;
        this.valorTotal = 0;
        this.horaSaida = null;
    }
    
    public Cobranca(int idCobranca, int idVaga, int idEstacionamento, String placaVeiculo, LocalDateTime horaEntrada){
        this.idCobranca = idCobranca;
        this.idVaga = idVaga;
        this.placaVeiculo = placaVeiculo;
        this.idEstacionamento = idEstacionamento;
        this.horaEntrada = horaEntrada;
        this.tempoTotal = 0;
        this.valorTotal = 0;
        this.horaSaida = null;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo( String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(int idCobranca) {
        this.idCobranca = idCobranca;
    }

    public int getIdEstacionamento() {
        return idEstacionamento;
    }

    public void setIdEstacionamento(int idEstacionamento) {
        this.idEstacionamento = idEstacionamento;
    }
    
     public int EncontrarMaior() throws FileNotFoundException {
        File arquivo = new File(Arquivo);
        int maiorId = 0;

        if(!arquivo.exists()){
            return maiorId;
        }
        
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
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
     
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cobranca cobranca = (Cobranca) obj;
        return idCobranca == cobranca.idCobranca; // Compara pelo idCobranca
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idCobranca); // Deve corresponder ao equals
    }
}
