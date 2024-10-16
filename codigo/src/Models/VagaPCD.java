package Models;

public class VagaPCD extends Vaga {

    public static final double DESCONTO_PCD = 0.13; // Tornar público e estático

    public VagaPCD(int idEstacionamento) {
        super(idEstacionamento);
    }

    @Override
    public double calcularValor(double tarifaBase) {
        return tarifaBase * (1 - DESCONTO_PCD);
    }

    @Override
    public String getTipo() {
        return "PCD";
    }
}