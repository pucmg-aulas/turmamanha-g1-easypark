/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import dao.EstacionamentoDAO;
import javax.swing.JDesktopPane;
import view.ExibirDetalhesEstacionamentoView;

/**
 *
 * @author Enzo
 */
public class ExibirDetalhesEstacionamentoController {

    private ExibirDetalhesEstacionamentoView view;
    private int idEstacionamento;
    private EstacionamentoDAO estacionamentoDAO;
    private JDesktopPane desktopPane;

public ExibirDetalhesEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento){
       this.view = new ExibirDetalhesEstacionamentoView(desktopPane);
        this.idEstacionamento = idEstacionamento;
        

        desktopPane.add(view);
        this.view.setVisible(true);

       
        buscarDetalhesEstacionamento();
        
}

public void buscarDetalhesEstacionamento(){
    try{
        Estacionamento estacionamento = estacionamentoDAO.buscarInformacoesPorId(idEstacionamento);
                view.exibirDetalhesEstacionamento(estacionamento);
    }catch(Exception e){
        //possiveis exceções
    }
}
    

}
