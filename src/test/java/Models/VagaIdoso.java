package Models;

public class VagaIdoso extends Vaga{
    private static final double DESCONTO_IDOSO = 0.15;

    public VagaIdoso(int idEstacionamento) {
        super(idEstacionamento);
    }

    public double getDESCONTO_IDOSO() {
        return DESCONTO_IDOSO;
    }

    @Override
    public String getTipo(){
        return "Idoso";
    }
}
