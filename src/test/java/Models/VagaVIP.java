package Models;

import java.io.IOException;

public class VagaVIP extends Vaga{

    private static final double AUMENTO_VIP = 1.20;

    public VagaVIP(int idEstacionamento, int id) throws IOException {
        super(idEstacionamento, id);
        this.tarifaBase = getTarifaBase() * AUMENTO_VIP;

    }
    
    public VagaVIP(int idEstacionamento, boolean status, int id) throws IOException {
        super(idEstacionamento, status, id);
        this.tarifaBase = getTarifaBase() * AUMENTO_VIP;

    }

    public static double getAumentoVip() {
        return AUMENTO_VIP;
    }

    @Override
    public String getTipo(){
        return "VIP";
    }
}
