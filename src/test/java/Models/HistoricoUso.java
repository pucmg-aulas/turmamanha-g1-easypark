package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoUso {
    private String cpfCliente;
    private String nomeCliente;
    private int idCobranca;
    private int idVaga;
    private String placaVeiculo;
    private boolean status;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    // Construtor para inicializar todos os atributos, sem data de saída
    public HistoricoUso(String cpfCliente, String nomeCliente, int idCobranca, int idVaga, 
                        String placaVeiculo, boolean status, LocalDateTime dataEntrada) {
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.idCobranca = idCobranca;
        this.idVaga = idVaga;
        this.placaVeiculo = placaVeiculo;
        this.status = status;
        this.dataEntrada = dataEntrada;
    }

    // Construtor completo com data de saída
    public HistoricoUso(String cpfCliente, String nomeCliente, int idCobranca, int idVaga, 
                        String placaVeiculo, boolean status, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.idCobranca = idCobranca;
        this.idVaga = idVaga;
        this.placaVeiculo = placaVeiculo;
        this.status = status;
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

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(int idCobranca) {
        this.idCobranca = idCobranca;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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