/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.swing.JOptionPane;

public class send_code extends javax.swing.JFrame {
 
int randomCode;
    public send_code() {
        initComponents();
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        questxt = new javax.swing.JTextField();
        usertxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        answertxt = new javax.swing.JTextField();
        newpasstxt = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        melek = new javax.swing.JLabel();
        picek = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(252, 249, 137));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 440, -1));

        jPanel3.setBackground(new java.awt.Color(0, 87, 118));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 440, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Lupa? Ingat Pertanyaanmu?");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 360, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, -1));

        jPanel4.setBackground(new java.awt.Color(0, 87, 118));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(questxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 260, -1));
        jPanel4.add(usertxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 170, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(252, 249, 137));
        jLabel1.setText("Username");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(252, 249, 137));
        jLabel2.setText("Security Question");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(252, 249, 137));
        jLabel4.setText("Answer");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(252, 249, 137));
        jLabel5.setText("New Password");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));
        jPanel4.add(answertxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 170, -1));
        jPanel4.add(newpasstxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 170, -1));

        jButton1.setBackground(new java.awt.Color(224, 25, 25));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        jButton2.setBackground(new java.awt.Color(224, 25, 25));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 70, -1));

        jButton3.setBackground(new java.awt.Color(224, 25, 25));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Kembali");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        melek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/visible.jpg"))); // NOI18N
        melek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                melekMousePressed(evt);
            }
        });
        jPanel4.add(melek, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 40, 20));

        picek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/invisible.jpg"))); // NOI18N
        picek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                picekMousePressed(evt);
            }
        });
        jPanel4.add(picek, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 40, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 440, 300));

        setSize(new java.awt.Dimension(440, 397));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       //Fungsi untuk mengambil pertanyaan berdasarkan username yang sudah kita daftarkan diinputkan melalui textfield usertxt
       String username = usertxt.getText();
       try{
           Connection con = (Connection)Koneksi.configDB();
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery("select Pertanyaan from login where username='"+username+"'");
           //Jika Username yang diinputkan benar maka akan muncul juga Pertanyaan dari Usernamenya          
           if(rs.next())
           {
               questxt.setText(rs.getString(1));
           }
           //Jika Username yang diinputkan salah maka akan muncul notifikasi peringatan 
           else
               JOptionPane.showMessageDialog(null, "Please write correct Username");
           con.close();
           rs.close();
       }catch(Exception e){
           
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Fungsi untuk kembali ke halaman Login
        Login2 log = new Login2();
               log.setVisible(true);
               this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Fungsi menyimpan perubahan password 
        String username = usertxt.getText();
        String newpass = newpasstxt.getText();
        String Pertanyaan = questxt.getText();
        String answer = answertxt.getText();
        
        try{
           Connection con = (Connection)Koneksi.configDB();
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery("select *from login where username='"+username+"' and Jawaban='"+answer+"'");
           if(rs.next())
               //Jika jawaban yang dimasukkan sesuai maka akan muncul notifikasi sukses
           {
               st.executeUpdate("UPDATE login set password='"+newpass+"' where username='"+username+"' and Jawaban='"+answer+"'");
               JOptionPane.showMessageDialog(null, "Your Password is Succsessfully updated");
               Login2 log = new Login2();
               log.setVisible(true);
               this.dispose();
           }
           //Jika jawaban yang dimasukkan salah maka akan muncul notifikasi peringatan
           else
               JOptionPane.showMessageDialog(null, "Please write correct Username or Answer");
        
       }catch(Exception e){
               e.printStackTrace();
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void melekMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_melekMousePressed
        picek.setVisible(true);
        melek.setVisible(false);
        newpasstxt.setEchoChar((char)0);
    }//GEN-LAST:event_melekMousePressed

    private void picekMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_picekMousePressed
        melek.setVisible(true);
        picek.setVisible(false);
        newpasstxt.setEchoChar('*');
    }//GEN-LAST:event_picekMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(send_code.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(send_code.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(send_code.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(send_code.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new send_code().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answertxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel melek;
    private javax.swing.JPasswordField newpasstxt;
    private javax.swing.JLabel picek;
    private javax.swing.JTextField questxt;
    private javax.swing.JTextField usertxt;
    // End of variables declaration//GEN-END:variables
}