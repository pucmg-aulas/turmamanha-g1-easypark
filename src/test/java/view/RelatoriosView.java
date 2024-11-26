package view;

import Controllers.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

public class RelatoriosView extends javax.swing.JInternalFrame {

    private JDesktopPane jDesktopPane1;

    public RelatoriosView(JDesktopPane jDesktopPane1) {
        initComponents();
        this.jDesktopPane1 = jDesktopPane1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        arrecadacaoBtn = new javax.swing.JButton();
        frequenciaBtn = new javax.swing.JButton();

        setClosable(true);

        arrecadacaoBtn.setText("Arrecadação por Vaga");
        arrecadacaoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrecadacaoBtnActionPerformed(evt);
            }
        });

        frequenciaBtn.setText("Frequência de Uso por Vaga");
        frequenciaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frequenciaBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(arrecadacaoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frequenciaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(arrecadacaoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(frequenciaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arrecadacaoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrecadacaoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_arrecadacaoBtnActionPerformed

    private void frequenciaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frequenciaBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_frequenciaBtnActionPerformed

    public void addArrecadacaoBtnActionListener(ActionListener actionListener) {
        arrecadacaoBtn.addActionListener(actionListener);
    }

    public void addFrequenciaBtnActionListener(ActionListener actionListener) {
        frequenciaBtn.addActionListener(actionListener);
    }

    public JButton getArrecadacaoBtn() {
        return arrecadacaoBtn;
    }

    public JButton getFrequenciaBtn() {
        return frequenciaBtn;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton arrecadacaoBtn;
    private javax.swing.JButton frequenciaBtn;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
