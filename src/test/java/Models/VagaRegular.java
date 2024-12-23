package Models;

import java.io.IOException;

public class VagaRegular extends Vaga implements ITipo{

    public VagaRegular(int idEstacionamento, int id) throws IOException {
        super(idEstacionamento, id);
    }
    
    public VagaRegular(int idEstacionamento, boolean status, int id) throws IOException {
        super(idEstacionamento, status, id);
    }

    public VagaRegular() throws IOException{
    }
    
    @Override
    public String getTipo(){
        return "Regular";
    }
    
    @Override
     public double calculoValor(double valor){
       return valor;
   }
}
