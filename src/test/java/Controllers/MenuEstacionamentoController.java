/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javax.swing.JDesktopPane;
import view.MenuEstacionamentoView;

/**
 *
 * @author user
 */
public class MenuEstacionamentoController {
    private MenuEstacionamentoView view;
    
    
    public MenuEstacionamentoController(JDesktopPane desktopPane, int idEstacionamento){
        this.view = new MenuEstacionamentoView(desktopPane);
        
        desktopPane.add(view);
        this.view.setVisible(true);
    }
}
