package Models;

import java.io.*;

public abstract class Vaga {
    private int id;
    protected double tarifaBase;
    private int idEstacionamento;

    // Status True: Desocupado, False: Ocupado
    private boolean status;
    private int nextId = 1;
    private final String FILE_PATH = "./codigo/src/Archives/Vagas" + idEstacionamento;

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

    public abstract String getTipo();
}

