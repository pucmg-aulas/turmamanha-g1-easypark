package src.Models;

import java.time.LocalTime;
import java.time.Duration;

public class Cobranca {
    private Veiculo veiculo;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private int tempoTotal;
    private static final double VALORTEMPO = 4;
    private static final double LIMITEPRECO = 50;
    private static final double FRACAOTEMPO = 15;
    private double valorTotal;

    public Cobranca(int idVaga, Estacionamento estacionamento, Veiculo veiculo){
        this.vaga = estacionamento.getVagaPorId(idVaga);
        this.veiculo = veiculo;
        this.horaEntrada = LocalTime.now();
        if (this.vaga != null){
            this.vaga.ocuparVaga();
        }else{
            System.out.println("Vaga não encontrada!");
        }
    }

    public Vaga getVaga() {
        return this.vaga;
    }

    public double getValorTotal(){
        return valorTotal;
    }

    public void setHoraSaida(LocalTime horaSaida){
        this.horaSaida = LocalTime.of(horaSaida.getHour(), horaSaida.getMinute());
    }

    public void calcularTempoFinal(){
        Duration duracao = Duration.between(horaEntrada, horaSaida);
        this.tempoTotal += duracao.toMinutes() + 1;
    }

    private boolean verificarValorLimite(){
        if(this.valorTotal >= LIMITEPRECO){
            return false;
        }
        return true;
    }

    public void calcularValorTotal(){
        if(verificarValorLimite()){
            this.valorTotal = (this.tempoTotal / FRACAOTEMPO) * VALORTEMPO;
        }else{
            this.valorTotal = LIMITEPRECO;
        }
    }

    public boolean pagar(){
        if(this.vaga != null && this.vaga.liberarVaga()){
            return true;
        }
        return false;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

}
