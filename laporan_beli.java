/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.sql.PreparedStatement;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class laporan_beli extends javax.swing.JFrame {

   public void tampil_data(String d1, String d2) throws SQLException{
       //Menampilkan data dari database laporan_beli ke tabel laporan harian (pembelian)
          Connection con = (Connection) Koneksi.configDB();
          PreparedStatement stm ;
          ResultSet res ;
         
         
         try{
             
             if(d1.equals("") || d2.equals("")){
                 stm = con.prepareStatement("SELECT * FROM `laporan_beli`");
             }else{
                 stm = con.prepareStatement("SELECT * FROM `laporan_beli` WHERE `Tanggal` BETWEEN ? AND ?");
                 stm.setString(1, d1);
                 stm.setString(2, d2);
             }
             res = stm.executeQuery();
             DefaultTableModel model = (DefaultTableModel)tabelharian.getModel();
             Object[] row;
             
             while(res.next()){
                  row = new Object[8];
                  
                  row [0] = res.getString(1);
                  row [1] = res.getString(2);
                  row [2] = res.getString(3);
                  row [3] = res.getInt(4);
                  row [4] = res.getInt(5);
                  row [5] = res.getInt(6);
                  row [6] = res.getString(7);
                  row [7] = res.getDate(8);
                  model.addRow(row);
             }
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
   }
   
   public void tampil_data2(String d1, String d2) throws SQLException{
       //Menampilkan data dari database laporan_bulanan ke tabel laporan bulanan (pembelian)
         Connection con = (Connection) Koneksi.configDB();
          PreparedStatement stm ;
          ResultSet res ;
         
         
         try{
             
             if(d1.equals("") || d2.equals("")){
                 stm = con.prepareStatement("SELECT * FROM `laporan_beli`");
             }else{
                 stm = con.prepareStatement("SELECT * FROM `laporan_beli` WHERE `Tanggal` BETWEEN ? AND ?");
                 stm.setString(1, d1);
                 stm.setString(2, d2);
             }
             res = stm.executeQuery();
             DefaultTableModel model = (DefaultTableModel)tabelbulanan.getModel();
             Object[] row;
             
             while(res.next()){
                 row = new Object[8];
                                 
                  row [0] = res.getString(1);
                  row [1] = res.getString(2);
                  row [2] = res.getString(3);
                  row [3] = res.getInt(4);
                  row [4] = res.getInt(5);
                  row [5] = res.getInt(6);
                  row [6] = res.getString(7);
                  row [7] = res.getDate(8);
                  model.addRow(row);
             }
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
   }
   
  
    public laporan_beli() throws SQLException {
        initComponents();
       tampil_data("", "");
       tampil_data2("", "");
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelbulanan = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelharian = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        backbutton = new javax.swing.JLabel();
        notranstxt = new javax.swing.JTextField();
        idbrgtxt = new javax.swing.JTextField();
        namatxt = new javax.swing.JTextField();
        jumlahtxt = new javax.swing.JTextField();
        hbtxt = new javax.swing.JTextField();
        totaltxt = new javax.swing.JTextField();
        jenistxt = new javax.swing.JTextField();
        tgltxt = new javax.swing.JTextField();
        tanggalan = new com.toedter.calendar.JDateChooser();
        tanggalan1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tanggal2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        tanggal1 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 87, 118));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelbulanan.setBackground(new java.awt.Color(252, 249, 137));
        tabelbulanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "Id Barang", "Nama Barang", "Jumlah", "Harga Beli", "Total Beli", "Jenis pembayaran", "Tanggal"
            }
        ));
        tabelbulanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbulananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelbulanan);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 1010, 200));

        tabelharian.setBackground(new java.awt.Color(252, 249, 137));
        tabelharian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "Id Barang", "Nama Barang ", "Jumlah", "Harga Beli", "Total Beli", "Jenis pembayaran", "Tanggal"
            }
        ));
        tabelharian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelharianMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelharian);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 1010, 190));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(252, 249, 137));
        jLabel2.setText("BULANAN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 320, 180, -1));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 10, 560));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(252, 249, 137));
        jLabel29.setText("LAPORAN PEMBELIAN");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 340, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, -1));

        backbutton.setBackground(new java.awt.Color(0, 0, 0));
        backbutton.setFont(new java.awt.Font("Lucida Sans", 1, 36)); // NOI18N
        backbutton.setForeground(new java.awt.Color(255, 255, 255));
        backbutton.setText("??");
        backbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backbuttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backbuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backbuttonMouseExited(evt);
            }
        });
        jPanel1.add(backbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 30, 30));

        notranstxt.setText("jTextField1");
        jPanel1.add(notranstxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 480, -1, -1));

        idbrgtxt.setText("jTextField1");
        jPanel1.add(idbrgtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 460, -1, -1));

        namatxt.setText("jTextField1");
        jPanel1.add(namatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 480, -1, -1));

        jumlahtxt.setText("jTextField1");
        jPanel1.add(jumlahtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        hbtxt.setText("jTextField1");
        jPanel1.add(hbtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, -1, -1));

        totaltxt.setText("jTextField1");
        jPanel1.add(totaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 440, -1, -1));

        jenistxt.setText("jTextField1");
        jPanel1.add(jenistxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 500, -1, -1));

        tgltxt.setText("jTextField1");
        jPanel1.add(tgltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 490, -1, -1));

        tanggalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tanggalanMouseClicked(evt);
            }
        });
        jPanel1.add(tanggalan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 170, 30));

        tanggalan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tanggalan1MouseClicked(evt);
            }
        });
        jPanel1.add(tanggalan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 170, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(252, 249, 137));
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 330, 60, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(252, 249, 137));
        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 60, 30));
        jPanel1.add(tanggal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 170, 30));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(252, 249, 137));
        jLabel5.setText("HARIAN");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, 150, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 150, 40));
        jPanel1.add(tanggal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 170, 30));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("Batal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 60, 30));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("Batal");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 60, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/transaksi_jual_logo.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 360, 370));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 630));

        setSize(new java.awt.Dimension(1099, 625));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelbulananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbulananMouseClicked
    
       
    }//GEN-LAST:event_tabelbulananMouseClicked

    private void tabelharianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelharianMouseClicked
       
       

    }//GEN-LAST:event_tabelharianMouseClicked

    private void backbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseClicked
        Laporan board = new Laporan();
        board.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_backbuttonMouseClicked

    private void backbuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseEntered
        // TODO add your handling code here:
        backbutton.setForeground(Color.YELLOW);
    }//GEN-LAST:event_backbuttonMouseEntered

    private void backbuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseExited
        // TODO add your handling code here:
        backbutton.setForeground(Color.WHITE);
    }//GEN-LAST:event_backbuttonMouseExited

    private void tanggalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tanggalanMouseClicked
       
    }//GEN-LAST:event_tanggalanMouseClicked

    private void tanggalan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tanggalan1MouseClicked
      
    }//GEN-LAST:event_tanggalan1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
          tabelbulanan.setModel(new DefaultTableModel(null, new Object[]{"No_Transaksi", "ID_Barang", "Nama_Barang", "Jumlah", "Harga_Beli", "Total", "Jenis_Pembayaran", "Tanggal"}));
          SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
          String date1 = date.format(tanggal1.getDate());
          String date2 = date.format(tanggal2.getDate());
          
          tampil_data2(date1, date2);
      }catch(Exception e){
          e.printStackTrace();
      }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      try{
          tabelharian.setModel(new DefaultTableModel(null, new Object[]{"No_Transaksi", "ID_Barang", "Nama_Barang", "Jumlah", "Harga_Beli", "Total", "Jenis_Pembayaran", "Tanggal"}));
          SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
          String date1 = date.format(tanggalan.getDate());
          String date2 = date.format(tanggalan1.getDate());
          
          tampil_data(date1, date2);
      }catch(Exception e){
          e.printStackTrace();
      }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            tampil_data("", "");
        } catch (SQLException ex) {
            Logger.getLogger(laporan_jual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            tampil_data2("", "");
        } catch (SQLException ex) {
            Logger.getLogger(laporan_jual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(laporan_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporan_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporan_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporan_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new laporan_beli().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(laporan_beli.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backbutton;
    private javax.swing.JTextField hbtxt;
    private javax.swing.JTextField idbrgtxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jenistxt;
    private javax.swing.JTextField jumlahtxt;
    private javax.swing.JTextField namatxt;
    private javax.swing.JTextField notranstxt;
    private javax.swing.JTable tabelbulanan;
    private javax.swing.JTable tabelharian;
    private com.toedter.calendar.JDateChooser tanggal1;
    private com.toedter.calendar.JDateChooser tanggal2;
    private com.toedter.calendar.JDateChooser tanggalan;
    private com.toedter.calendar.JDateChooser tanggalan1;
    private javax.swing.JTextField tgltxt;
    private javax.swing.JTextField totaltxt;
    // End of variables declaration//GEN-END:variables
}
