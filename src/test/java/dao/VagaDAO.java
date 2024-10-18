/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Vaga;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class VagaDAO extends AbstractDAO{
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
    
    public void addVaga(Vaga vaga){
        vagas.add(vaga);
        gravar(vaga.getArquivoPath(), (List) vaga);
    }
    
    public void removerVaga(Vaga vaga){
        vagas.remove(vaga);
    }
    
    public Vaga pesquisarVaga(int id){
        for(Vaga vaga: vagas){
            if(vaga.getId() == id){
                return vaga;
            }
        }
        
        return null;
    }
    
}
