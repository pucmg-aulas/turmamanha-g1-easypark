package dao;

import Models.Estacionamento;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;






public class EstacionamentoDAO{

    private List<Estacionamento> estacionamentos;
    private static EstacionamentoDAO instance;
    private final String Arquivo = Estacionamento.getArquivoPath();

    private EstacionamentoDAO() throws IOException {
        estacionamentos = lerEstacionamentos();
        if(estacionamentos == null){
            estacionamentos = new ArrayList<>();
        }
    }
    public static EstacionamentoDAO getInstance() throws IOException {
        if (instance == null) {
            instance = new EstacionamentoDAO();
        }
        return instance;
    }


    public void removeEstacionamento(Estacionamento estacionamento) throws IOException {
        estacionamentos.remove(estacionamento);
        salvarEstacionamentoArquivo(estacionamentos);
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
        estacionamentos.add(estacionamento);
        return salvarEstacionamentoArquivo(estacionamentos);
    } 
    
   private boolean salvarEstacionamentoArquivo(List<Estacionamento> e) throws IOException{
       File arquivo = new File(Arquivo);
       
       try{
           File diretorio = arquivo.getParentFile();
           if(diretorio != null && !diretorio.exists()){
               diretorio.mkdir();
           }
           
           if (!arquivo.exists()){
               arquivo.createNewFile();
           }
           
           try(BufferedWriter bw = new BufferedWriter(new FileWriter(Arquivo))){
               for(Estacionamento es: e){
                   bw.write(es.getId() + ";" + es.getNome() + ";" + es.getRua()+ ";" + es.getBairro()+ ";" + es.getNumero()+ ";" + es.getQntdVagas());
                   bw.newLine();
                   bw.flush();
              
               }
                return true;
           }
       }catch(IOException ex){
           throw new IOException(ex.getMessage());
       }
   }

    public Estacionamento buscarInformacoesPorId(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(Arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (Integer.parseInt(dados[0]) == id) {
                    String nome = dados[1];
                    String rua = dados[2];
                    String bairro = dados[3];
                    int numero = Integer.parseInt(dados[4]);
                    int numeroVagas = Integer.parseInt(dados[5]);

                    return new Estacionamento(id, nome, rua, bairro, numero, numeroVagas);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao formatar número: " + e.getMessage());
        }

        return null; // Retorna null se o ID não for encontrado
    }

    public List<Estacionamento> lerEstacionamentos() throws FileNotFoundException, IOException{
       List<Estacionamento> estacionamentosLista = new ArrayList();
       try(BufferedReader br = new BufferedReader(new FileReader(Arquivo))){
           String linha;
           
           while((linha = br.readLine()) != null){
               String[] dados = linha.split(";");
               String id = dados[0];
               String nome = dados[1];
               String rua = dados[2];
               String bairro = dados[3];
               String numero = dados[4];
               String qntVagas = dados[5];
               
               Estacionamento e = new Estacionamento(Integer.parseInt(id), nome, rua, bairro, Integer.parseInt(numero),Integer.parseInt(qntVagas));
               estacionamentosLista.add(e);
           }
           
           return estacionamentosLista;
       }catch(IOException e){
           throw new RuntimeException(e);
       }
       
   }
}
