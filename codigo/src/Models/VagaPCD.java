package Models;

public class VagaPCD extends Vaga{

    private static final double DESCONTO_PCD = 0.13;

    public VagaPCD() {
        super();
    }

    @Override
    public double calcularValor(double tarifaBase) {
        return tarifaBase * (1 - DESCONTO_PCD);
    }
}