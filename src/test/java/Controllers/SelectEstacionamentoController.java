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

    public SelectEstacionamentoController(JDesktopPane desktopPane) throws IOException {
        this.view = new SelecionarEstacionamentoView(desktopPane);
        this.estacionamentoDAO = EstacionamentoDAO.getInstance();
        
        desktopPane.add(view);
        this.view.setVisible(true);
        listarEstacionamentos();

        this.view.addConfirmButtonActionListener(e -> {
            Estacionamento estacionamentoSelecionado = (Estacionamento) view.getListaEstacionamento().getSelectedItem();
            //String estacionamentoSelecionado = (String) this.view.getListaEstacionamento().getSelectedItem();
            
         try {
            MenuEstacionamentoController menuEstacionamentoController = new MenuEstacionamentoController(jDesktopPane1);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalViewJframe.class.getName()).log(Level.SEVERE, null, ex);
        }
         
            JOptionPane.showMessageDialog(view,"Estacionamento selecionado: " + estacionamentoSelecionado);
        });
    }

    public void listarEstacionamentos() {
        try {
         
            List<Estacionamento> estacionamentos = estacionamentoDAO.lerEstacionamentos();

            view.getListaEstacionamento().removeAllItems();

            for (Estacionamento estacionamento : estacionamentos) {
          
               view.getListaEstacionamento().addItem(estacionamento.getNome());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao carregar estacionamentos: " + e.getMessage());
        }
    }
}
