package Models;

public class VagaRegular extends Vaga{

    public VagaRegular(int idEstacionamento) {
        super(idEstacionamento);
    }

    @Override
    public String getTipo(){
        return "Regular";
    }
}
