package view;//import Controllers.EstacionamentoController;
//import Models.Estacionamento;
//import Models.Vaga;

import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author user
 */
public class CadastroEstacionamentoView extends javax.swing.JInternalFrame {

    /**
     * Creates new form CadastroEstacionamentoView
     */
  // private EstacionamentoController estacionamentoController;

    public CadastroEstacionamentoView() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nomeEstacionamento = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        bairroEstacionamento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ruaEstacionamento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        numeroEstacionamento = new javax.swing.JTextField();
        qntdVagasEstacionamento = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        BtnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();

        setClosable(true);
        setVisible(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Cadastrar Estacionamento");

        jLabel2.setText("Nome:");

        jLabel3.setText("Bairro:");

        jLabel4.setText("Rua: ");

        jLabel5.setText("Número:");

        jLabel6.setText("Quantidade de Vagas: ");

        BtnCancelar.setText("Cancelar");

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(175, 175, 175))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(ruaEstacionamento, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bairroEstacionamento, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nomeEstacionamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qntdVagasEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bairroEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ruaEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numeroEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qntdVagasEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarActionPerformed

    public void addConfirmButtonActionListener(ActionListener actionListener) {
        btnConfirmar.addActionListener(actionListener);
    }
    
    public void addCancelButtonActionListener(ActionListener actionListener) {
        BtnCancelar.addActionListener(actionListener);
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }
    
    public JButton getBtnCancelar() {
        return BtnCancelar;
    }


    public JTextField getTxtNomeEstacionamento() {
        return nomeEstacionamento;
    }

    public void setTxtNomeEstacionamento(JTextField nomeEstacionamento) {
        this.nomeEstacionamento = nomeEstacionamento;
    }

    public JTextField getTxtBairroEstacionamento() {
        return bairroEstacionamento;
    }

    public void setTxtBairroEstacionamento(JTextField bairroEstacionamento) {
        this.bairroEstacionamento = bairroEstacionamento;
    }

    public JTextField getTxtRuaEstacionamento() {
        return ruaEstacionamento;
    }

    public void setTxtRuaEstacionamento(JTextField ruaEstacionamento) {
        this.ruaEstacionamento = ruaEstacionamento;
    }

    public JTextField getTxtNumeroEstacionamento() {
        return numeroEstacionamento;
    }

    public void setTxtNumeroEstacionamento(JTextField numeroEstacionamento) {
        this.numeroEstacionamento = numeroEstacionamento;
    }

    public JSpinner getSpnQntdVagasEstacionamento() {
        return qntdVagasEstacionamento;
    }

    public void setSpnQntdVagasEstacionamento(JSpinner qntdVagasEstacionamento) {
        this.qntdVagasEstacionamento = qntdVagasEstacionamento;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JTextField bairroEstacionamento;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nomeEstacionamento;
    private javax.swing.JTextField numeroEstacionamento;
    private javax.swing.JSpinner qntdVagasEstacionamento;
    private javax.swing.JTextField ruaEstacionamento;
    // End of variables declaration//GEN-END:variables
}
