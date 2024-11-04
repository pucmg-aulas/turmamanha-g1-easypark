package view;

import javax.swing.JButton;
import javax.swing.JDesktopPane;


public class MenuClienteView extends javax.swing.JInternalFrame {

    private JDesktopPane jDesktopPane1;
    
    
    public MenuClienteView(JDesktopPane jDesktopPane1) {
        initComponents();
        this.jDesktopPane1 = jDesktopPane1;
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        cadastrarBtn = new javax.swing.JButton();
        exibirVeiculosBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        exibirDetalhesBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        historicoUsoBtn = new javax.swing.JButton();

        setClosable(true);

        cadastrarBtn.setText("Cadastrar Veículo do Cliente");
        cadastrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarBtnActionPerformed(evt);
            }
        });

        exibirVeiculosBtn.setText("Exibir Veículos do Cliente");
        exibirVeiculosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exibirVeiculosBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Menu do Cliente");

        exibirDetalhesBtn.setText("Exibir Detalhes do Cliente");
        exibirDetalhesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exibirDetalhesBtnActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ações Disponíveis: ");

        historicoUsoBtn.setText("Histórico de Uso ");
        historicoUsoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historicoUsoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cadastrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exibirVeiculosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exibirDetalhesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(historicoUsoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(156, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exibirDetalhesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(cadastrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(exibirVeiculosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(historicoUsoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cadastrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarBtnActionPerformed
        // TODO add your handling code here:
//        CadastroVeiculoClienteView telaCadVeiculo = new CadastroVeiculoClienteView(cc.getCliente().getCpf());
//        jDesktopPane1.add(telaCadVeiculo);
//        telaCadVeiculo.setVisible(true);
    }//GEN-LAST:event_cadastrarBtnActionPerformed

    private void exibirVeiculosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exibirVeiculosBtnActionPerformed
        // TODO add your handling code here:
//        ExibirVeiculosClienteView telaExibirVeiculos = new ExibirVeiculosClienteView(cc.getCliente().getCpf());
//        jDesktopPane1.add(telaExibirVeiculos);
//        telaExibirVeiculos.setVisible(true);
    }//GEN-LAST:event_exibirVeiculosBtnActionPerformed

    private void exibirDetalhesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exibirDetalhesBtnActionPerformed
        // TODO add your handling code here:
        
//        ExibirDetalhesClienteView telaDetalhesCli = new ExibirDetalhesClienteView(cc.getCliente().getCpf());
//        jDesktopPane1.add(telaDetalhesCli);
//        telaDetalhesCli.setVisible(true);
    }//GEN-LAST:event_exibirDetalhesBtnActionPerformed

    private void historicoUsoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historicoUsoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historicoUsoBtnActionPerformed

    public JButton getExibirDetalhesBtn(){
        return exibirDetalhesBtn;
    }
    public JButton getExibirVeiculosBtn(){
        return exibirVeiculosBtn;
    }
    
    public JButton getCadastrarVeiculoBtn(){
        return cadastrarBtn;
    }
    
    public JButton getHistoricoUsoBtn(){
        return historicoUsoBtn;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cadastrarBtn;
    private javax.swing.JButton exibirDetalhesBtn;
    private javax.swing.JButton exibirVeiculosBtn;
    private javax.swing.JButton historicoUsoBtn;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
