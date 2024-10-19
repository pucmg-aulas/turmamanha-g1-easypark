package Models;

public class VagaRegular extends Vaga{

    public VagaRegular(int idEstacionamento) {
        super(idEstacionamento);
    }
    
    public VagaRegular(int idEstacionamento, boolean status) {
        super(idEstacionamento, status);
    }

    @Override
    public String getTipo(){
        return "Regular";
    }
}
