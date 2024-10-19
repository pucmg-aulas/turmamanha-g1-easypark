package Models;

public class VagaVIP extends Vaga{

    private static final double AUMENTO_VIP = 0.20;

    public VagaVIP(int idEstacionamento) {
        super(idEstacionamento);
    }
    
    public VagaVIP(int idEstacionamento, boolean status) {
        super(idEstacionamento, status);
    }

    public static double getAumentoVip() {
        return AUMENTO_VIP;
    }

    @Override
    public String getTipo(){
        return "VIP";
    }
}
