package Models;

public class VagaVIP extends Vaga{

    private static final double AUMENTO_VIP = 0.20;

    public VagaVIP() {
        super();
    }

    @Override
    public double calcularValor(double tarifaBase) {
        return tarifaBase * (1 + AUMENTO_VIP);
    }
}
