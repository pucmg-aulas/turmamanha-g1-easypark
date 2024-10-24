
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarVagasView extends javax.swing.JInternalFrame {
    
    private JTable tbVagas;  
    private JScrollPane scrollPane;  
  
    public ListarVagasView() {
        initComponents();
        configuraTabela();
        
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    private void configuraTabela() {
        // Criação da tabela com um modelo de tabela padrão (vazio inicialmente)
        tbVagas = new JTable(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Tipo", "Status"}
        ));

        // Adicionando a tabela em um JScrollPane para ter a barra de rolagem
        scrollPane = new JScrollPane(tbVagas);

        // Definindo o tamanho inicial da tabela
        scrollPane.setSize(590, 347);
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JTable getTbVagas() {
        return tbVagas;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
