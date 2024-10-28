package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoUso {
    private String placaCarro;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public HistoricoUso(String placaCarro, LocalDateTime dataEntrada) {
        this.placaCarro = placaCarro;
        this.dataEntrada = dataEntrada;
    }

    public void registrarSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public long calcularTempoPermanencia() {
        if (dataSaida != null) {
            return java.time.Duration.between(dataEntrada, dataSaida).toMinutes();
        }
        return 0;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return placaCarro + ";" + dataEntrada.format(formatter) + ";" + 
               (dataSaida != null ? dataSaida.format(formatter) : "") + ";" + 
               calcularTempoPermanencia();
    }
}
