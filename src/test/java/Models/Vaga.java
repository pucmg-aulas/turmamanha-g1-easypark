package Models;

import java.io.*;

public class Vaga {
    private int id;
    protected double tarifaBase;
    private int idEstacionamento;
    private ITipo tipoVaga;

    // Status True: Desocupado, False: Ocupado
    private boolean status;
    private int nextId = 1;
    private final String FILE_PATH = "./src/test/java/Archives/Vagas" + idEstacionamento;

    public Vaga(int idEstacionamento, int id) throws IOException {
        this.id = id;
        this.status = true;
        this.tarifaBase = 10.0;
        this.idEstacionamento = idEstacionamento;
    }
    
    public Vaga(int idEstacionamento, boolean status, int id) throws IOException {
        this.id = id;
        this.status = status;
        this.tarifaBase = 10.0;
        this.idEstacionamento = idEstacionamento;
    }
    
    public Vaga(ITipo tipoVaga ){
        this.tipoVaga = tipoVaga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArquivoPath(){
        return FILE_PATH;
    }
   
    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public int getIdEstacionamento() {
        return idEstacionamento;
    }

    public void setIdEstacionamento(int idEstacionamento) {
        this.idEstacionamento = idEstacionamento;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status; 
    }

    public  int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }


    public ITipo getTipoVaga() {
    return tipoVaga;
}
    
    public String getTipo(){
    return "";
    }

public void setTipo(ITipo tipoVaga) {
    this.tipoVaga = tipoVaga;
}
    
    @Override
    public String toString(){
        return id + "-" + getTipo() + "-" + status;
    }
    
    public double calculoValor(double valor){
        return tipoVaga.calculoValor(valor);
    }
}

