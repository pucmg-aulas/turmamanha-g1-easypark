package Models;

public class VagaIdoso extends Vaga{
    private static final double DESCONTO_IDOSO = 0.15;

    public VagaIdoso(int idEstacionamento) {
        super(idEstacionamento);
    }

    @Override
    public double calcularValor(double tarifaBase) {
        double valorBase = super.calcularValor(tarifaBase);
        return tarifaBase * (1 - DESCONTO_IDOSO);
    }
}
