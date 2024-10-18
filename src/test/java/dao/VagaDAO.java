/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Vaga;
import Models.VagaIdoso;
import Models.VagaRegular;
import Models.VagaVIP;
import Models.VagaPCD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class VagaDAO{
    private ArrayList<Vaga> vagas;
    private static VagaDAO instance;

    private VagaDAO(){
        if(vagas == null){
            vagas = new ArrayList<>();
        }
      
    }
    
    public static VagaDAO getInstance(){
        if(instance == null){
            instance = new VagaDAO();
        }
        return instance;
    }

    public List<Vaga> carregarVagasArquivo(int idEstacionamento) {
        List<Vaga> vagasCarregadas = new ArrayList<>();
        File arquivo = new File("./src/java/Archives/Vagas" + idEstacionamento + ".txt");

        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    int id = Integer.parseInt(dados[0]);
                    String tipo = dados[1];
                    String statusString = dados[2];

                    boolean status = statusString.contains("Desocupado") ? true : false;

                    Vaga vaga = null;
                    switch (tipo) {
                        case "Regular":
                            vaga = new VagaRegular(id, status);
                            break;
                        case "Idoso":
                            vaga = new VagaIdoso(id, status);
                            break;
                        case "PCD":
                            vaga = new VagaPCD(id, status);
                            break;
                        case "VIP":
                            vaga = new VagaVIP(id, status);
                            break;
                    }

                    if (vaga != null) {
                        vagasCarregadas.add(vaga);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return vagasCarregadas;
    }

    private boolean salvarVagasArquivo(List<Vaga> vagas, int idEstacionamento) throws IOException {

        File arquivo = new File("./src/java/Archives/Vagas"+idEstacionamento+".txt");

        try{
            File diretorio = arquivo.getParentFile();
            if(diretorio != null && diretorio.exists()){
                diretorio.mkdir();
            }

            if (!arquivo.exists()){
                arquivo.createNewFile();
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))){
                for(Vaga vaga: vagas){
                    String statusString = vaga.getStatus() ? "Desocupado" : "Ocupado";
                    bw.write(vaga.getId() + ";" + vaga.getTipo() + ";" + statusString);
                    bw.newLine();
                    bw.flush();
                    return true;
                }
            }
        }catch(IOException ex){
            throw new IOException(ex.getMessage());
        }
        return false;
    }

    public void salvarVagaEmArquivo(Vaga vaga, int idEstacionamento) {
        try {
            List<Vaga> listaVagaUnica = new ArrayList<>();
            listaVagaUnica.add(vaga);
            salvarVagasArquivo(listaVagaUnica, idEstacionamento);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void instanciarVagas(int qntdVagas, int idEstacionamento) {
        int vagasRegulares = (int) (qntdVagas * 0.5);
        int vagasIdoso = (int) (qntdVagas * 0.2);
        int vagasPCD = (int) (qntdVagas * 0.2);
        int vagasVIP = (int) (qntdVagas * 0.1);
        int idVaga = 1;

        int totalInstanciadas = vagasRegulares + vagasIdoso + vagasPCD + vagasVIP;

        while (totalInstanciadas < qntdVagas) {
            vagasRegulares++;
            totalInstanciadas++;
        }

        for (int i = 0; i < vagasRegulares; i++) {
            Vaga vaga = new VagaRegular(idVaga++);
            vagas.add(vaga);
            salvarVagaEmArquivo(vaga, idEstacionamento);
        }
        for (int i = 0; i < vagasIdoso; i++) {
            Vaga vaga = new VagaIdoso(idVaga++);
            vagas.add(vaga);
            salvarVagaEmArquivo(vaga, idEstacionamento);
        }
        for (int i = 0; i < vagasPCD; i++) {
            Vaga vaga = new VagaPCD(idVaga++);
            vagas.add(vaga);
            salvarVagaEmArquivo(vaga, idEstacionamento);
        }
        for (int i = 0; i < vagasVIP; i++) {
            Vaga vaga = new VagaVIP(idVaga++);
            vagas.add(vaga);
            salvarVagaEmArquivo(vaga, idEstacionamento);
        }
    }

    public Vaga getVagaPorId(int id){
        for(Vaga vaga: vagas){
            if(vaga.getId() == id){
                return vaga;
            }
        }
        return null;
    }

    public List<Vaga> getVagasDisponiveis(int idEstacionamento) {
        List<Vaga> vagasDisponiveis = new ArrayList<>();
        List<Vaga> vagasCarregadas = carregarVagasArquivo(idEstacionamento);

        for (Vaga vaga : vagasCarregadas) {
            if (vaga.getStatus()) {  // true = Desocupado
                vagasDisponiveis.add(vaga);
            }
        }

        return vagasDisponiveis;
    }

    public List<Vaga> getVagasOcupadas(int idEstacionamento) {
        List<Vaga> vagasOcupadas = new ArrayList<>();
        List<Vaga> vagasCarregadas = carregarVagasArquivo(idEstacionamento);

        for (Vaga vaga : vagasCarregadas) {
            if (!vaga.getStatus()) {  // false = Ocupado
                vagasOcupadas.add(vaga);
            }
        }

        return vagasOcupadas;
    }


    
}
