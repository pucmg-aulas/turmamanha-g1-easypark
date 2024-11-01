package Models;

import java.io.IOException;

public class VagaPCD extends Vaga implements ITipo {

    private static final double DESCONTO_PCD = 0.87;

    public VagaPCD(int idEstacionamento, int id) throws IOException {
        super(idEstacionamento, id);
        this.tarifaBase = getTarifaBase() * DESCONTO_PCD;
    }

    public VagaPCD(int idEstacionamento, boolean status, int id) throws IOException {
        super(idEstacionamento, status, id);
        this.tarifaBase = getTarifaBase() * DESCONTO_PCD;
    }
    public double getDESCONTO_PCD() {
        return DESCONTO_PCD;
    }

    @Override
    public String getTipo(){
        return "PCD";
    }
    
    @Override
     public double calculoValor(double valor){
       return valor *DESCONTO_PCD;
   }
    
}