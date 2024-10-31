/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import dao.EstacionamentoDAO;
import view.SelecionarEstacionamentoView;
import Models.Estacionamento;

import javax.swing.*;
import java.io.IOException;
import java.util.List;


public class SelectEstacionamentoController {
    private SelecionarEstacionamentoView view;
    private EstacionamentoDAO estacionamentoDAO;
    private JDesktopPane desktopPane;

    public SelectEstacionamentoController(JDesktopPane desktopPane) throws IOException {
        this.view = new SelecionarEstacionamentoView(desktopPane);
        this.estacionamentoDAO = EstacionamentoDAO.getInstance();
        this.desktopPane = desktopPane;
        
        desktopPane.add(view);
        this.view.setVisible(true);
        listarEstacionamentos();

        this.view.addConfirmButtonActionListener(e -> {
            String estacionamentoSelecionado = (String) view.getListaEstacionamento().getSelectedItem();
            String[] dadosEstacionamento = estacionamentoSelecionado.split("-");
            int idEstacionamentoSelecionado = Integer.parseInt(dadosEstacionamento[0]);
            JOptionPane.showMessageDialog(view,"Estacionamento selecionado: " + idEstacionamentoSelecionado);
            MenuEstacionamentoController menuEstacionamentoController = new MenuEstacionamentoController(desktopPane, idEstacionamentoSelecionado);
            sair();
            
        });
    }

    public void listarEstacionamentos() {
        try {
         
            List<Estacionamento> estacionamentos = estacionamentoDAO.lerEstacionamentos();

            view.getListaEstacionamento().removeAllItems();

            for (Estacionamento estacionamento : estacionamentos) {
          
               view.getListaEstacionamento().addItem(estacionamento.getId() + "-" + estacionamento.getNome());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao carregar estacionamentos: " + e.getMessage());
        }
    }
    
    private void sair(){
         this.view.dispose();
     }
}
