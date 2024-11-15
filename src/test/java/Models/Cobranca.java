package Models;

import dao.CobrancabdDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cobranca {
    private int idCobranca = 0 ;
    private int idVaga;
    private Veiculo veiculo;
    private int idEstacionamento;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private int tempoTotal;
    public static final long VALORTEMPO = 4;
    public static final double LIMITEPRECO = 50;
    public static final long FRACAOTEMPO = 15;
    private double valorTotal;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Construtor que utiliza o maior ID do banco de dados
    public Cobranca(int idVaga, int idEstacionamento, Veiculo veiculo) throws SQLException {
        this.idCobranca = CobrancabdDAO.getInstance().obterMaiorIdCobranca() + 1; // Obtém o maior ID do banco e incrementa
        this.idVaga = idVaga;
        this.veiculo = veiculo;
        this.idEstacionamento = idEstacionamento;
        this.horaEntrada = LocalDateTime.now();
        this.tempoTotal = 0;
        this.valorTotal = 0;
        this.horaSaida = null;
    }

    // Construtor completo para criação a partir de dados existentes
    public Cobranca(int idCobranca, int idVaga, int idEstacionamento, Veiculo veiculo, LocalDateTime horaEntrada, LocalDateTime horaSaida, double tempoTotal, double valorTotal) throws SQLException {
        this.idCobranca = CobrancabdDAO.getInstance().obterMaiorIdCobranca() + 1;
        this.idVaga = idVaga;
        this.veiculo = veiculo;
        this.idEstacionamento = idEstacionamento;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.tempoTotal = (int) tempoTotal;
        this.valorTotal = valorTotal;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cobranca cobranca = (Cobranca) obj;
        return idCobranca == cobranca.idCobranca;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idCobranca);
    }
}
