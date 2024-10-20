package Models;

import java.io.IOException;

public class VagaRegular extends Vaga{

    public VagaRegular(int idEstacionamento, int id) throws IOException {
        super(idEstacionamento, id);
    }
    
    public VagaRegular(int idEstacionamento, boolean status, int id) throws IOException {
        super(idEstacionamento, status, id);
    }

    @Override
    public String getTipo(){
        return "Regular";
    }
}
