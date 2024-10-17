package Models;

import java.io.*;



public abstract class Vaga{

    private int id;
    protected double tarifaBase;
    private int idEstacionamento;

    // Status True: Desocupado
    // Status False: Ocupado
    private boolean status;
    private static int nextId = 1;
    private static final String FILE_PATH = "./codigo/src/Archives/Vagas";


    public Vaga(int idEstacionamento) {
        this.id = EncontrarMaiorId(idEstacionamento) + 1;
        this.status = true;
        this.tarifaBase = 10.0;
        this.idEstacionamento = idEstacionamento;
    }

    public int EncontrarMaiorId(int idEstacionamento) {
        File arquivo = new File(FILE_PATH);
        int maiorId = 0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.startsWith("ID: ")) {
                    int idAtual = Integer.parseInt(linha.replace("ID: ", "").trim());
                    if (idAtual > maiorId) {
                        maiorId = idAtual;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo para obter o maior ID: " + e.getMessage());
        }

        return maiorId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Vaga.nextId = nextId;
    }
}
