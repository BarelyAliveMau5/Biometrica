/*
 * Copyright 2017 BarelyAliveMau5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui;

import gerenciador.Controle;
import gerenciador.Perfil;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author BarelyAliveMau5
 */
public class frmAutenticar extends javax.swing.JFrame {

    private final Controle ctl;
    private File digital_selecionada;
    /**
     * Creates new form frmPrincipal
     * @param ctl controlador
     */
    public frmAutenticar(Controle ctl) {
        initComponents();
        System.out.println("lol");
        this.ctl = ctl;
        digital_selecionada = null;
        this.setLocationRelativeTo(null);  // centralizar janela
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnAcessar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblSelDigital = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biometrica");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnAcessar.setText("Acessar");
        btnAcessar.setEnabled(false);
        btnAcessar.setNextFocusableComponent(lblSelDigital);
        btnAcessar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcessarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        getContentPane().add(btnAcessar, gridBagConstraints);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(134, 163));
        jPanel1.setMinimumSize(new java.awt.Dimension(134, 163));
        jPanel1.setPreferredSize(new java.awt.Dimension(134, 163));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        lblSelDigital.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelDigital.setText("<html><div style='text-align: center;'>Clique aqui <br>p/ selecionar <br>a imagem <br /> da digital</div></html>");
        lblSelDigital.setFocusCycleRoot(true);
        lblSelDigital.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSelDigital.setNextFocusableComponent(btnAcessar);
        lblSelDigital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSelDigitalMouseClicked(evt);
            }
        });
        jPanel1.add(lblSelDigital);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jPanel1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html><div style='text-align: center;'>Biometrica<br>acessar perfil</div></html>");
        jLabel3.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 20, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcessarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcessarActionPerformed
        if (digital_selecionada != null) {
            Perfil perfil = ctl.testeUsuario(digital_selecionada);
            if (perfil != null) {
                frmPerfil frmperfil = new frmPerfil(this, perfil);
                frmperfil.setVisible(true);
                this.setVisible(false);
            } else
                JOptionPane.showMessageDialog(null, "Digital não reconhecida ou não registrada.", "Erro" ,
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAcessarActionPerformed

    private void lblSelDigitalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSelDigitalMouseClicked
        digital_selecionada = Utilidade.carregarImagem(lblSelDigital);
        if (digital_selecionada != null){
            lblSelDigital.setText("");
            btnAcessar.setEnabled(true);
        }
    }//GEN-LAST:event_lblSelDigitalMouseClicked
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcessar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblSelDigital;
    // End of variables declaration//GEN-END:variables
}
