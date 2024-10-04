package Models;

public class VagaIdoso extends Vaga{
    private static final double DESCONTO_IDOSO = 0.15;

    public VagaIdoso() {
        super();
    }

    @Override
    public double calcularValor(double tarifaBase) {
        return tarifaBase * (1 - DESCONTO_IDOSO);
    }
}
