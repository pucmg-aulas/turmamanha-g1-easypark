package dao;

import Models.Estacionamento;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class EstacionamentoDAO extends AbstractDAO {

    private List<Estacionamento> estacionamentos;
    private static EstacionamentoDAO instance;
    private final String Arquivo = Estacionamento.getArquivoPath();

    private EstacionamentoDAO() {
        estacionamentos = ler(Arquivo);
        if(estacionamentos == null){
            estacionamentos = new ArrayList<>();
        }
    }
    public static EstacionamentoDAO getInstance() {
        if (instance == null) {
            instance = new EstacionamentoDAO();
        }
        return instance;
    }

    public void addEstacionamento(Estacionamento estacionamento) throws IOException {
        estacionamentos.add(estacionamento);
        cadastrarEstacionamento(estacionamento);
    }

    public void removeEstacionamento(Estacionamento estacionamento) {
        estacionamentos.remove(estacionamento);
        gravar(Arquivo, estacionamentos);
    }

    public List<Estacionamento> getAllEstacionamentos(){
        return estacionamentos;
    }
    
    public Estacionamento getEstacionamentoPorId(int id) {
        for (Estacionamento estacionamento : estacionamentos) {
            if (estacionamento.getId() == id) {
                return estacionamento;
            }
        }
        return null;
    }
    
    public boolean cadastrarEstacionamento(Estacionamento estacionamento) throws IOException{
        salvarEstacionamentoArquivo(estacionamento);
        return true;
    } 
    
   private boolean salvarEstacionamentoArquivo(Estacionamento e) throws IOException{
       File arquivo = new File(Arquivo);
       
       try{
           File diretorio = arquivo.getParentFile();
           if(diretorio != null && diretorio.exists()){
               diretorio.mkdir();
           }
           
           if (!arquivo.exists()){
               arquivo.createNewFile();
           }
           
           try(BufferedWriter bw = new BufferedWriter(new FileWriter(Arquivo, true))){
               bw.write(e.getId() + ";" + e.getNome() + ";" + e.getRua()+ ";" + e.getBairro()+ ";" + e.getNumero()+ ";");
               bw.newLine();
               bw.flush();
               return true;
           }
       }catch(IOException ex){
           throw new IOException(ex.getMessage());
       }
   }
}
