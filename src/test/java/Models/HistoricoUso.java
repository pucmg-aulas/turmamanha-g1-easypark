package Models;

public class HistoricoUso {
    private String cpfCliente;
    private String NomeEstacionamento;
    private ITipo tipoVaga;
    private String placaVeiculo;
    private double valorPago;
    private int tempoTotal;

    public HistoricoUso(String cpfCliente, String NomeEstacionamento, ITipo tipoVaga, 
                        String placaVeiculo, double valorPago, int tempoTotal) {
        this.cpfCliente = cpfCliente;
        this.NomeEstacionamento = NomeEstacionamento;
        this.tipoVaga = tipoVaga;
        this.placaVeiculo = placaVeiculo;
        this.valorPago = valorPago;
        this.tempoTotal = tempoTotal;
    }

    // Getters e Setters para todos os atributos
    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
    
     public String getNomeEstacionamento() {
        return NomeEstacionamento;
    }

    public void setNomeEstacionamento(String NomeEstacionamento) {
        this.NomeEstacionamento = NomeEstacionamento;
    }
    

    public ITipo getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(ITipo tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    

    // Método para calcular o tempo de permanência em minutos
//    public long calcularTempoPermanencia() {
//        if (dataSaida != null) {
//            return java.time.Duration.between(dataEntrada, dataSaida).toMinutes();
//        }
//        return 0;
//    }

//    @Override
//    public String toString() {
//        return placaVeiculo + ";" + 
//    }
}