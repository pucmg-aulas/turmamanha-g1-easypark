package Models;

import java.io.IOException;

public class VagaIdoso extends Vaga implements ITipo{
    private static final double DESCONTO_IDOSO = 0.85;

    public VagaIdoso(int idEstacionamento, int id) throws IOException {
        super(idEstacionamento, id);
        this.tarifaBase = getTarifaBase() * DESCONTO_IDOSO;
    }
    public VagaIdoso(int idEstacionamento, boolean status, int id) throws IOException {
        super(idEstacionamento, status, id);
        this.tarifaBase = getTarifaBase() * DESCONTO_IDOSO;
    }

    public VagaIdoso() throws IOException{
    
    }
    
    public double getDESCONTO_IDOSO() {
        return DESCONTO_IDOSO;
    }

    @Override
    public String getTipo(){
        return "Idoso";
    }
    
    @Override
   public double calculoValor(double valor){
       return valor*DESCONTO_IDOSO;
   }
}
