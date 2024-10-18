package Models;

public class VagaPCD extends Vaga {

    private static final double DESCONTO_PCD = 0.13;

    public VagaPCD(int idEstacionamento) {
        super(idEstacionamento);
    }

    public double getDESCONTO_PCD() {
        return DESCONTO_PCD;
    }

    @Override
    public String getTipo(){
        return "PCD";
    }
}