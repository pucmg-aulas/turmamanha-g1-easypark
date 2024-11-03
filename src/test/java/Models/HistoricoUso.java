package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoUso {
    private String cpfCliente;
    private int idCobranca;
    private int idEstacionamento;
    private int idVaga;
    private String placaVeiculo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public HistoricoUso(String cpfCliente, int idCobranca, int idEstacionamento, int idVaga, 
                        String placaVeiculo, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.cpfCliente = cpfCliente;
        this.idCobranca = idCobranca;
        this.idEstacionamento = idEstacionamento;
        this.idVaga = idVaga;
        this.placaVeiculo = placaVeiculo;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    // Getters e Setters para todos os atributos
    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
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
    

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    // Método para calcular o tempo de permanência em minutos
    public long calcularTempoPermanencia() {
        if (dataSaida != null) {
            return java.time.Duration.between(dataEntrada, dataSaida).toMinutes();
        }
        return 0;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return placaVeiculo + ";" + dataEntrada.format(formatter) + ";" + 
               (dataSaida != null ? dataSaida.format(formatter) : "") + ";" + 
               calcularTempoPermanencia();
    }
}