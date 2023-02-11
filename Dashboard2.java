/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package base;

import com.mysql.cj.xdevapi.Statement;
import static com.orsoncharts.data.DataUtils.total;
import com.sun.jdi.connect.spi.Connection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


public class Dashboard2 extends javax.swing.JFrame {
     
    public void tampil_data(){
        //Method untuk menampilkan data dari tabel laporan_jual ke tabelharian
        
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("No.");
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah");
        model.addColumn("Harga Jual");
        model.addColumn("Total");
      
        String cari = carilaporan.getText(); //fungsi untuk mengambil inputan text dari user
        try {
            //Fungsi untuk mencari data dari tabel laporan_jual berdasarkan nama barang
            int no = 1;
            String sql = "Select * From laporan_jual WHERE Nama_Barang LIKE'%" + cari + "%'";
            java.sql.Connection con = (java.sql.Connection) Koneksi.configDB();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                model.addRow(new Object[]{no++, res.getString(3), res.getString(4), res.getString(5), res.getString(6)});

                tabelharian.setModel(model); 
            
            }
        } catch (SQLException e) {
            System.out.println("ERROR :" + e.getMessage());
           

        }
    }
    
private void getpendapatan() throws SQLException{
    //Method yang menghitung jumlah pendapatan berdasarkan penjumlahan kolom Total dari tabel penjualan_rinci
    try{
        String sql = "select sum(Total) as pendapatan from penjualan_rinci as tabel ";
                     
                
        java.sql.Connection c = (java.sql.Connection) Koneksi.configDB();
            java.sql.Statement stm = c.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String pendapatan;
            //Jika penjumlahan berhasil maka akan muncul di textfield pendapatantxt
            if(res.next()){
                pendapatan = res.getString("pendapatan");
                this.pendapatantxt.setText(pendapatan);               
                c.close(); stm.close(); res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
private void getpengeluaran() throws SQLException{
    //Method yang menghitung jumlah pengeluaran berdasarkan penjumlahan kolom Total dari tabel pembelian_rinci
    try{
        String sql = "select sum(Total) as pengeluaran from pembelian_rinci as tabel ";
                     
                
        java.sql.Connection c = (java.sql.Connection) Koneksi.configDB();
            java.sql.Statement stm = c.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String pengeluaran;
            //Jika penjumlahan berhasil maka akan muncul di textfield pengeluarantxt
            if(res.next()){
                pengeluaran = res.getString("pengeluaran");
                this.pengeluarantxt.setText(pengeluaran);               
                c.close(); stm.close(); res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
public void getjumlahbrg() throws SQLException{
    //Method yang menghitung jumlah barang berdasarkan penjumlahan kolom Jumlah dari tabel penjualan_rinci saat tanggal hari ini tampil
    try{
        String sql = "select sum(Jumlah) as jumlah_barang from penjualan_rinci where DATE(Tanggal) = CURRENT_DATE()";                
        java.sql.Connection c = (java.sql.Connection) Koneksi.configDB();
            java.sql.Statement stm = c.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String jumlah;
            //Jika penjumlahan berhasil maka akan muncul di textfield terjualtxt
            if(res.next()){
                jumlah = res.getString("jumlah_barang");
                this.terjualtxt.setText(jumlah);               
                c.close(); stm.close(); res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
public void getpembeli(){
    //Method yang menghitung jumlah pembeli berdasarkan penjumlahan kolom ID_Pembeli dari tabel penjualan_rinci saat tanggal hari ini tampil
    try{
        String sql = "select count(ID_Pembeli) as pembeli from penjualan_rinci where date(Tanggal) = CURRENT_DATE()";
        
        java.sql.Connection c = (java.sql.Connection) Koneksi.configDB();
            java.sql.Statement stm = c.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            String  pembeli;
            //Jika penjumlahan berhasil maka akan muncul di textfield jmlhpembelitxt        
            if(res.next()){
                pembeli = res.getString("pembeli");               
                this.jmlhpembelitxt.setText(pembeli);
                c.close(); stm.close(); res.close();
            }
            
    }catch(Exception e){
        e.printStackTrace();
    }
}

 public void tanggal() {
          //Method yang berfungsi untuk menampilkan tanggal otomatis (continue)
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggalan.setText(s.format(date));
    }
 private static String getHari(){
     //Method untuk menampilkan hari terkini
            Calendar dar = Calendar.getInstance();
            switch(dar.get(Calendar.DAY_OF_WEEK)){
                case Calendar.MONDAY:
                    return "Senin";
                    case Calendar.TUESDAY:
                    return "Selasa";
                    case Calendar.WEDNESDAY:
                    return "Rabu";
                    case Calendar.THURSDAY:
                    return "Kamis";
                    case Calendar.FRIDAY:
                    return "Jum'at";
                    case Calendar.SATURDAY:
                    return "Sabtu";
                    case Calendar.SUNDAY:
                    return "Minggu";
                    default: return "null";
            }
        }
   private static String getBulan(){
       //Method untuk menampilkan hari terkini
            Calendar dar = Calendar.getInstance();
            switch(dar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    return "Januari";
                    case Calendar.FEBRUARY:
                    return "Februari";
                    case Calendar.MARCH:
                    return "Maret";
                    case Calendar.APRIL:
                    return "April";
                    case Calendar.MAY:
                    return "Mei";
                    case Calendar.JUNE:
                    return "Juni";
                    case Calendar.JULY:
                    return "Juli";
                    case Calendar.AUGUST:
                    return "Agustus";
                    case Calendar.SEPTEMBER:
                    return "September";
                    case Calendar.OCTOBER:
                    return "Oktober";
                    case Calendar.NOVEMBER:
                    return "November";
                    case Calendar.DECEMBER:
                    return "Desember";
                    default: return "null";
            }
        }
    public Dashboard2() throws SQLException {
        //Fungsi penampil method
        initComponents();
        getpendapatan();
        tanggal();
        getpembeli();
        getpengeluaran();
        getjumlahbrg();
        tampil_data();
        this.haritxt.setText(getHari());
        this.haritxt1.setText(getHari());
        this.labelbulan.setText(getBulan());
        this.labelbulan1.setText(getBulan());
       
        namabrgtxt.setVisible(false);
        totaltxt.setVisible(false);
        hargatxt.setVisible(false);
        jumlahtxt.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        roundedPanel13 = new org.hq.RoundedPanel();
        labelexit = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panellogout = new org.hq.RoundedPanel();
        paneldash = new org.hq.RoundedPanel();
        labeltrans6 = new javax.swing.JLabel();
        panellap = new org.hq.RoundedPanel();
        labeltrans2 = new javax.swing.JLabel();
        panelkrwn = new org.hq.RoundedPanel();
        labeltrans3 = new javax.swing.JLabel();
        panelsup = new org.hq.RoundedPanel();
        labeltrans5 = new javax.swing.JLabel();
        panelrang = new org.hq.RoundedPanel();
        labeltrans4 = new javax.swing.JLabel();
        paneltrans = new org.hq.RoundedPanel();
        labeltrans1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        roundedPanel2 = new org.hq.RoundedPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        roundedPanel16 = new org.hq.RoundedPanel();
        roundedPanel17 = new org.hq.RoundedPanel();
        tanggaltxt = new javax.swing.JTextField();
        tanggalan = new javax.swing.JTextField();
        roundedPanel3 = new org.hq.RoundedPanel();
        roundedPanel4 = new org.hq.RoundedPanel();
        pendapatantxt = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelbulan1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        panelluar = new org.hq.RoundedPanel();
        pengeluarantxt = new javax.swing.JLabel();
        labelluar = new javax.swing.JLabel();
        labelbulan = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        roundedPanel7 = new org.hq.RoundedPanel();
        jLabel8 = new javax.swing.JLabel();
        haritxt1 = new javax.swing.JLabel();
        terjualtxt = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        panelchart = new org.hq.RoundedPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        roundedPanel5 = new org.hq.RoundedPanel();
        roundedPanel9 = new org.hq.RoundedPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        roundedPanel11 = new org.hq.RoundedPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        roundedPanel12 = new org.hq.RoundedPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        roundedPanel14 = new org.hq.RoundedPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panelbrg = new org.hq.RoundedPanel();
        jmlhpembelitxt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        haritxt = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        findbutton3 = new javax.swing.JButton();
        carilaporan = new javax.swing.JTextField();
        namabrgtxt = new javax.swing.JTextField();
        jumlahtxt = new javax.swing.JTextField();
        hargatxt = new javax.swing.JTextField();
        totaltxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelharian = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 87, 118));
        jLabel4.setText("DASHBOARD");
        jPanel21.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 180, 50));

        jLabel22.setBackground(new java.awt.Color(0, 87, 118));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 87, 118));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("   MAIN  MENU");
        jPanel21.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 160, 20));

        roundedPanel13.setBackground(new java.awt.Color(0, 0, 0));
        roundedPanel13.setRoundBottomLeft(15);
        roundedPanel13.setRoundBottomRight(15);
        roundedPanel13.setRoundTopLeft(15);
        roundedPanel13.setRoundTopRight(15);
        roundedPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelexit.setBackground(new java.awt.Color(255, 255, 255));
        labelexit.setForeground(new java.awt.Color(255, 255, 255));
        labelexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/logoutnew().png"))); // NOI18N
        labelexit.setText("⟪⟪⟪");
        labelexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelexitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelexitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelexitMouseExited(evt);
            }
        });
        roundedPanel13.add(labelexit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 30, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 87, 118));
        jLabel7.setText("DASHBOARD");
        roundedPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 180, 50));

        panellogout.setBackground(new java.awt.Color(0, 0, 0));
        panellogout.setRoundBottomLeft(15);
        panellogout.setRoundTopRight(15);

        javax.swing.GroupLayout panellogoutLayout = new javax.swing.GroupLayout(panellogout);
        panellogout.setLayout(panellogoutLayout);
        panellogoutLayout.setHorizontalGroup(
            panellogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        panellogoutLayout.setVerticalGroup(
            panellogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        roundedPanel13.add(panellogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 50, 50));

        paneldash.setBackground(new java.awt.Color(255, 255, 255));
        paneldash.setRoundBottomRight(15);
        paneldash.setRoundTopRight(15);

        labeltrans6.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans6.setText("DASHBOARD");
        labeltrans6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout paneldashLayout = new javax.swing.GroupLayout(paneldash);
        paneldash.setLayout(paneldashLayout);
        paneldashLayout.setHorizontalGroup(
            paneldashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldashLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        paneldashLayout.setVerticalGroup(
            paneldashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneldashLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labeltrans6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        roundedPanel13.add(paneldash, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 160, -1));

        panellap.setBackground(new java.awt.Color(0, 0, 0));
        panellap.setRoundBottomRight(15);
        panellap.setRoundTopRight(15);

        labeltrans2.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans2.setForeground(new java.awt.Color(255, 255, 255));
        labeltrans2.setText("LAPORAN");
        labeltrans2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panellapLayout = new javax.swing.GroupLayout(panellap);
        panellap.setLayout(panellapLayout);
        panellapLayout.setHorizontalGroup(
            panellapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panellapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        panellapLayout.setVerticalGroup(
            panellapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panellapLayout.createSequentialGroup()
                .addComponent(labeltrans2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        roundedPanel13.add(panellap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 160, 20));

        panelkrwn.setBackground(new java.awt.Color(0, 0, 0));
        panelkrwn.setRoundBottomRight(15);
        panelkrwn.setRoundTopRight(15);

        labeltrans3.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans3.setForeground(new java.awt.Color(255, 255, 255));
        labeltrans3.setText("DATA KARYAWAN");
        labeltrans3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans3MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelkrwnLayout = new javax.swing.GroupLayout(panelkrwn);
        panelkrwn.setLayout(panelkrwnLayout);
        panelkrwnLayout.setHorizontalGroup(
            panelkrwnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelkrwnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans3)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelkrwnLayout.setVerticalGroup(
            panelkrwnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelkrwnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labeltrans3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        roundedPanel13.add(panelkrwn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 160, 20));

        panelsup.setBackground(new java.awt.Color(0, 0, 0));
        panelsup.setRoundBottomRight(15);
        panelsup.setRoundTopRight(15);

        labeltrans5.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans5.setForeground(new java.awt.Color(255, 255, 255));
        labeltrans5.setText("DATA SUPPLIER");
        labeltrans5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans5MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelsupLayout = new javax.swing.GroupLayout(panelsup);
        panelsup.setLayout(panelsupLayout);
        panelsupLayout.setHorizontalGroup(
            panelsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans5)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        panelsupLayout.setVerticalGroup(
            panelsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelsupLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labeltrans5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        roundedPanel13.add(panelsup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 20));

        panelrang.setBackground(new java.awt.Color(0, 0, 0));
        panelrang.setRoundBottomRight(15);
        panelrang.setRoundTopRight(15);

        labeltrans4.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans4.setForeground(new java.awt.Color(255, 255, 255));
        labeltrans4.setText("DATA BARANG");
        labeltrans4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelrangLayout = new javax.swing.GroupLayout(panelrang);
        panelrang.setLayout(panelrangLayout);
        panelrangLayout.setHorizontalGroup(
            panelrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelrangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans4)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        panelrangLayout.setVerticalGroup(
            panelrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelrangLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labeltrans4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        roundedPanel13.add(panelrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 160, 20));

        paneltrans.setBackground(new java.awt.Color(0, 0, 0));
        paneltrans.setRoundBottomRight(15);
        paneltrans.setRoundTopRight(15);

        labeltrans1.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        labeltrans1.setForeground(new java.awt.Color(255, 255, 255));
        labeltrans1.setText("TRANSAKSI");
        labeltrans1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labeltrans1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labeltrans1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labeltrans1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout paneltransLayout = new javax.swing.GroupLayout(paneltrans);
        paneltrans.setLayout(paneltransLayout);
        paneltransLayout.setHorizontalGroup(
            paneltransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltransLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        paneltransLayout.setVerticalGroup(
            paneltransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneltransLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labeltrans1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        roundedPanel13.add(paneltrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 160, -1));

        jPanel21.add(roundedPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 170, 480));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/iconnew-picsay.png"))); // NOI18N
        jPanel21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 120, -1));

        getContentPane().add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 630));

        roundedPanel2.setBackground(new java.awt.Color(0, 0, 0));
        roundedPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 87, 118));
        jLabel3.setText("DASHBOARD");
        jPanel35.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 180, 50));

        roundedPanel16.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel16.setRoundBottomLeft(15);
        roundedPanel16.setRoundBottomRight(15);
        roundedPanel16.setRoundTopLeft(15);
        roundedPanel16.setRoundTopRight(15);

        javax.swing.GroupLayout roundedPanel16Layout = new javax.swing.GroupLayout(roundedPanel16);
        roundedPanel16.setLayout(roundedPanel16Layout);
        roundedPanel16Layout.setHorizontalGroup(
            roundedPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        roundedPanel16Layout.setVerticalGroup(
            roundedPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel35.add(roundedPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 740, -1));

        roundedPanel17.setBackground(new java.awt.Color(0, 87, 118));
        roundedPanel17.setRoundBottomLeft(15);
        roundedPanel17.setRoundBottomRight(15);
        roundedPanel17.setRoundTopLeft(15);
        roundedPanel17.setRoundTopRight(15);

        javax.swing.GroupLayout roundedPanel17Layout = new javax.swing.GroupLayout(roundedPanel17);
        roundedPanel17.setLayout(roundedPanel17Layout);
        roundedPanel17Layout.setHorizontalGroup(
            roundedPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        roundedPanel17Layout.setVerticalGroup(
            roundedPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel35.add(roundedPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 740, -1));

        tanggaltxt.setEditable(false);
        tanggaltxt.setBackground(new java.awt.Color(255, 200, 78));
        jPanel35.add(tanggaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 120, -1));

        tanggalan.setEditable(false);
        tanggalan.setBackground(new java.awt.Color(0, 87, 118));
        tanggalan.setForeground(new java.awt.Color(255, 255, 255));
        jPanel35.add(tanggalan, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 120, -1));

        roundedPanel2.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 80));

        roundedPanel3.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel3.setRoundBottomLeft(15);
        roundedPanel3.setRoundBottomRight(15);
        roundedPanel3.setRoundTopLeft(15);
        roundedPanel3.setRoundTopRight(15);
        roundedPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundedPanel4.setBackground(new java.awt.Color(0, 0, 0));
        roundedPanel4.setRoundBottomRight(25);
        roundedPanel4.setRoundTopLeft(25);
        roundedPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pendapatantxt.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        pendapatantxt.setForeground(new java.awt.Color(255, 255, 255));
        roundedPanel4.add(pendapatantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 100, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Pendapatan");
        roundedPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 30));

        labelbulan1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        labelbulan1.setForeground(new java.awt.Color(252, 249, 137));
        roundedPanel4.add(labelbulan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 60, 20));

        jSeparator5.setBackground(new java.awt.Color(0, 87, 118));
        jSeparator5.setForeground(new java.awt.Color(0, 87, 118));
        roundedPanel4.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("  Rp");
        roundedPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, 30));

        roundedPanel3.add(roundedPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 160, 90));

        panelluar.setBackground(new java.awt.Color(0, 0, 0));
        panelluar.setRoundBottomRight(25);
        panelluar.setRoundTopLeft(25);
        panelluar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pengeluarantxt.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        pengeluarantxt.setForeground(new java.awt.Color(255, 255, 255));
        panelluar.add(pengeluarantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 100, 30));

        labelluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelluar.setForeground(new java.awt.Color(255, 255, 255));
        labelluar.setText("  Total Pengeluaran");
        labelluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelluarMouseExited(evt);
            }
        });
        panelluar.add(labelluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 120, 30));

        labelbulan.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        labelbulan.setForeground(new java.awt.Color(252, 249, 137));
        panelluar.add(labelbulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 60, 20));

        jSeparator2.setBackground(new java.awt.Color(0, 87, 118));
        jSeparator2.setForeground(new java.awt.Color(0, 87, 118));
        panelluar.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("  Rp");
        panelluar.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, 30));

        roundedPanel3.add(panelluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 160, 90));

        roundedPanel7.setBackground(new java.awt.Color(0, 0, 0));
        roundedPanel7.setRoundBottomRight(25);
        roundedPanel7.setRoundTopLeft(25);
        roundedPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText(" Barang terjual hari ini");
        roundedPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        haritxt1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        haritxt1.setForeground(new java.awt.Color(252, 249, 137));
        haritxt1.setText("    ");
        roundedPanel7.add(haritxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 40, 20));

        terjualtxt.setFont(new java.awt.Font("Lucida Sans", 1, 18)); // NOI18N
        terjualtxt.setForeground(new java.awt.Color(255, 255, 255));
        roundedPanel7.add(terjualtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 80, 30));

        jSeparator3.setBackground(new java.awt.Color(0, 87, 118));
        jSeparator3.setForeground(new java.awt.Color(0, 87, 118));
        roundedPanel7.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, -1));

        roundedPanel3.add(roundedPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 160, 90));

        panelchart.setBackground(new java.awt.Color(0, 0, 0));
        panelchart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        roundedPanel3.add(panelchart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 380, 230));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(252, 249, 137));
        jButton1.setText("Pie Chart");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        roundedPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 80, 20));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(252, 249, 137));
        jButton2.setText("Bar Chart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        roundedPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, -1, 20));

        roundedPanel5.setBackground(new java.awt.Color(0, 87, 118));
        roundedPanel5.setRoundBottomLeft(15);
        roundedPanel5.setRoundBottomRight(15);
        roundedPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundedPanel9.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel9.setRoundBottomLeft(20);
        roundedPanel9.setRoundTopRight(20);
        roundedPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("   Alamat Toko");
        roundedPanel9.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 110, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 87, 118));
        jLabel29.setText(" Nganjuk");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel29)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        roundedPanel9.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 120, -1));

        roundedPanel5.add(roundedPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 140, 140));

        roundedPanel11.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel11.setRoundBottomLeft(20);
        roundedPanel11.setRoundTopRight(20);
        roundedPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("   Nama Toko");
        roundedPanel11.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, 30));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 87, 118));
        jLabel36.setText(" TOKO");
        roundedPanel11.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 87, 118));
        jLabel37.setText(" ELLI");
        roundedPanel11.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 80, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        roundedPanel11.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        roundedPanel5.add(roundedPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 140, 140));

        roundedPanel12.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel12.setRoundBottomLeft(20);
        roundedPanel12.setRoundTopRight(20);
        roundedPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("  Jam Operasional");
        roundedPanel12.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 120, 30));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 87, 118));
        jLabel39.setText("08 am-");
        roundedPanel12.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 87, 118));
        jLabel40.setText("  09 pm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addGap(28, 28, 28))
        );

        roundedPanel12.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        roundedPanel5.add(roundedPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 140, 140));

        roundedPanel14.setBackground(new java.awt.Color(252, 249, 137));
        roundedPanel14.setRoundBottomLeft(20);
        roundedPanel14.setRoundTopRight(20);
        roundedPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 87, 118));
        jLabel42.setText(" Toko");
        roundedPanel14.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 87, 118));
        jLabel43.setText("Kelontong");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addGap(28, 28, 28))
        );

        roundedPanel14.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 120, -1));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("  Jenis Usaha");
        roundedPanel14.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 30));

        roundedPanel5.add(roundedPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 140, 140));

        roundedPanel3.add(roundedPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 720, 160));

        panelbrg.setBackground(new java.awt.Color(0, 0, 0));
        panelbrg.setRoundBottomRight(25);
        panelbrg.setRoundTopLeft(25);
        panelbrg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jmlhpembelitxt.setFont(new java.awt.Font("Lucida Sans", 1, 18)); // NOI18N
        jmlhpembelitxt.setForeground(new java.awt.Color(255, 255, 255));
        jmlhpembelitxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmlhpembelitxtMouseClicked(evt);
            }
        });
        panelbrg.add(jmlhpembelitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("        Total Pembeli");
        panelbrg.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 30));

        haritxt.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        haritxt.setForeground(new java.awt.Color(252, 249, 137));
        panelbrg.add(haritxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 50, 20));

        jSeparator1.setBackground(new java.awt.Color(0, 87, 118));
        jSeparator1.setForeground(new java.awt.Color(0, 87, 118));
        panelbrg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, 20));

        roundedPanel3.add(panelbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 160, 90));

        findbutton3.setBackground(new java.awt.Color(230, 230, 230));
        findbutton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/search_icon-icons.com_74128-picsay.png"))); // NOI18N
        findbutton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        findbutton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                findbutton3MouseClicked(evt);
            }
        });
        findbutton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findbutton3ActionPerformed(evt);
            }
        });
        roundedPanel3.add(findbutton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, 30));

        carilaporan.setBackground(new java.awt.Color(0, 0, 0));
        carilaporan.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        carilaporan.setForeground(new java.awt.Color(252, 249, 137));
        carilaporan.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        carilaporan.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        carilaporan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                carilaporanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                carilaporanFocusLost(evt);
            }
        });
        carilaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                carilaporanMouseClicked(evt);
            }
        });
        carilaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carilaporanActionPerformed(evt);
            }
        });
        carilaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                carilaporanKeyReleased(evt);
            }
        });
        roundedPanel3.add(carilaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 140, 30));
        roundedPanel3.add(namabrgtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, -1, -1));

        jumlahtxt.setText("jTextField1");
        roundedPanel3.add(jumlahtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        hargatxt.setText("jTextField1");
        roundedPanel3.add(hargatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, -1));

        totaltxt.setText("jTextField1");
        roundedPanel3.add(totaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        tabelharian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelharian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelharianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelharian);

        roundedPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 340, 230));

        roundedPanel2.add(roundedPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 720, 530));

        getContentPane().add(roundedPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 740, 630));

        setSize(new java.awt.Dimension(930, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void labeltrans3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans3MouseClicked
        // TODO add your handling code here:
        pemasok3 sok = new pemasok3();
        sok.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_labeltrans3MouseClicked

    private void labeltrans3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans3MouseEntered
        // TODO add your handling code here:
        labeltrans3.setForeground(Color.BLACK);
        panelkrwn.setBackground(Color.WHITE);
    }//GEN-LAST:event_labeltrans3MouseEntered

    private void labeltrans3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans3MouseExited
        // TODO add your handling code here:
        labeltrans3.setForeground(Color.WHITE);
        panelkrwn.setBackground(Color.BLACK);
    }//GEN-LAST:event_labeltrans3MouseExited

    private void labeltrans4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans4MouseClicked
        
        try {
            // TODO add your handling code here:
            barang2 rg = new barang2();
            rg.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard2.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }//GEN-LAST:event_labeltrans4MouseClicked

    private void labeltrans4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans4MouseEntered
        // TODO add your handling code here:
        labeltrans4.setForeground(Color.BLACK);
        panelrang.setBackground(Color.WHITE);
    }//GEN-LAST:event_labeltrans4MouseEntered

    private void labeltrans4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans4MouseExited
        // TODO add your handling code here:
        labeltrans4.setForeground(Color.WHITE);
        panelrang.setBackground(Color.BLACK);
    }//GEN-LAST:event_labeltrans4MouseExited

    private void labeltrans5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans5MouseClicked
        // TODO add your handling code here:
        supplier2 s = new supplier2();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_labeltrans5MouseClicked

    private void labeltrans5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans5MouseEntered
        // TODO add your handling code here:
        labeltrans5.setForeground(Color.BLACK);
        panelsup.setBackground(Color.WHITE);
    }//GEN-LAST:event_labeltrans5MouseEntered

    private void labeltrans5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans5MouseExited
        // TODO add your handling code here:
        labeltrans5.setForeground(Color.WHITE);
        panelsup.setBackground(Color.BLACK);
    }//GEN-LAST:event_labeltrans5MouseExited

    private void labeltrans6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans6MouseClicked
   
    }//GEN-LAST:event_labeltrans6MouseClicked

    private void labeltrans6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans6MouseEntered
        // TODO add your handling code here:
    
    }//GEN-LAST:event_labeltrans6MouseEntered

    private void labeltrans6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans6MouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_labeltrans6MouseExited

    private void labelexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelexitMouseClicked
        Login2 login = new Login2();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_labelexitMouseClicked

    private void labelexitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelexitMouseEntered
        // TODO add your handling code here:
        labelexit.setForeground(Color.RED);
        panellogout.setBackground(Color.RED);
    }//GEN-LAST:event_labelexitMouseEntered

    private void labelexitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelexitMouseExited
        labelexit.setForeground(Color.WHITE);
        panellogout.setBackground(Color.BLACK);
    }//GEN-LAST:event_labelexitMouseExited

    private void labelluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelluarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_labelluarMouseExited

    private void labelluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelluarMouseEntered
        // TODO add your handling code here:      
    }//GEN-LAST:event_labelluarMouseEntered

    private void jmlhpembelitxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmlhpembelitxtMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jmlhpembelitxtMouseClicked

    private void carilaporanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carilaporanFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_carilaporanFocusGained

    private void carilaporanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_carilaporanFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_carilaporanFocusLost

    private void carilaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_carilaporanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_carilaporanMouseClicked

    private void carilaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carilaporanActionPerformed
        tampil_data();
    }//GEN-LAST:event_carilaporanActionPerformed

    private void carilaporanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_carilaporanKeyReleased
        tampil_data();
    }//GEN-LAST:event_carilaporanKeyReleased

    private void findbutton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findbutton3MouseClicked
        tampil_data();
    }//GEN-LAST:event_findbutton3MouseClicked

    private void findbutton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbutton3ActionPerformed

    }//GEN-LAST:event_findbutton3ActionPerformed

    private void tabelharianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelharianMouseClicked
        //Fungsi untuk menampilkan data dari tabelharian kedalam field" terkait
        int baris = tabelharian.rowAtPoint(evt.getPoint());
        String harga = tabelharian.getValueAt(baris, 1).toString();
        namabrgtxt.setText(harga);
        String stok = tabelharian.getValueAt(baris, 2).toString();
        jumlahtxt.setText(stok);
        String tgl = tabelharian.getValueAt(baris, 3).toString();
        hargatxt.setText(tgl);
        String total = tabelharian.getValueAt(baris, 4).toString();
        totaltxt.setText(total);
        
        
        
      
      
    }//GEN-LAST:event_tabelharianMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Fungsi untuk menampilkan pie chart serta untuk menampilkan data dari tabelharian
        try {
            int jumlah_barang = Integer.valueOf(jumlahtxt.getText());
            int harga_barang = Integer.valueOf(hargatxt.getText());
            int total_harga = Integer.valueOf(totaltxt.getText());
            DefaultPieDataset pie = new DefaultPieDataset();
            pie.setValue("Jumlah Barang", new Integer(jumlah_barang));
            pie.setValue("Harga Barang", new Integer(harga_barang));
            pie.setValue("Total Harga", new Integer(total_harga));

            JFreeChart car = ChartFactory.createPieChart("Akumulasi Data Penjualan Barang", pie);
            panelchart.setLayout(new java.awt.BorderLayout());
            ChartPanel fr = new ChartPanel(car);
            panelchart.add(fr, BorderLayout.CENTER);
            panelchart.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Fungsi untuk menampilkan bar chart serta untuk menampilkan data dari tabelharian
        
       
        try {
            int jumlah_barang = Integer.valueOf(jumlahtxt.getText());
            int harga_barang = Integer.valueOf(hargatxt.getText());
            int total_harga = Integer.valueOf(totaltxt.getText());
            DefaultCategoryDataset pie = new DefaultCategoryDataset();
            pie.setValue(new Integer(jumlah_barang), "1", "Jumlah Barang");
            pie.setValue(new Integer(harga_barang), "2", "Harga Barang");
            pie.setValue(new Integer(total_harga), "3", "Total Harga");

            JFreeChart car = ChartFactory.createBarChart3D("Akumulasi Data Penjualan Barang", "Parameter", "Nilai", pie);
            car.setBackgroundPaint(Color.WHITE);
            car.getTitle().setPaint(Color.BLACK);
            CategoryPlot p = car.getCategoryPlot();
            p.setRangeGridlinePaint(Color.YELLOW);
            panelchart.setLayout(new java.awt.BorderLayout());
            ChartPanel fr = new ChartPanel(car);
            panelchart.add(fr, BorderLayout.CENTER);
            panelchart.validate();
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void labeltrans1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans1MouseClicked
    transaksi2 t = new transaksi2();
    t.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_labeltrans1MouseClicked

    private void labeltrans1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans1MouseEntered
        labeltrans1.setForeground(Color.BLACK);
        paneltrans.setBackground(Color.WHITE);
    }//GEN-LAST:event_labeltrans1MouseEntered

    private void labeltrans1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans1MouseExited
        labeltrans1.setForeground(Color.WHITE);
        paneltrans.setBackground(Color.BLACK);
    }//GEN-LAST:event_labeltrans1MouseExited

    private void labeltrans2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans2MouseExited
        // TODO add your handling code here:
        labeltrans2.setForeground(Color.WHITE);
        panellap.setBackground(Color.BLACK);
    }//GEN-LAST:event_labeltrans2MouseExited

    private void labeltrans2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans2MouseEntered
        // TODO add your handling code here:
        labeltrans2.setForeground(Color.BLACK);
        panellap.setBackground(Color.WHITE);
    }//GEN-LAST:event_labeltrans2MouseEntered

    private void labeltrans2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labeltrans2MouseClicked
        // TODO add your handling code here:
        Laporan ran = new Laporan();
        ran.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_labeltrans2MouseClicked

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
            java.util.logging.Logger.getLogger(Dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Dashboard2().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Dashboard2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carilaporan;
    private javax.swing.JButton findbutton3;
    private javax.swing.JTextField hargatxt;
    private javax.swing.JLabel haritxt;
    private javax.swing.JLabel haritxt1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel jmlhpembelitxt;
    private javax.swing.JTextField jumlahtxt;
    private javax.swing.JLabel labelbulan;
    private javax.swing.JLabel labelbulan1;
    private javax.swing.JLabel labelexit;
    private javax.swing.JLabel labelluar;
    private javax.swing.JLabel labeltrans1;
    private javax.swing.JLabel labeltrans2;
    private javax.swing.JLabel labeltrans3;
    private javax.swing.JLabel labeltrans4;
    private javax.swing.JLabel labeltrans5;
    private javax.swing.JLabel labeltrans6;
    private javax.swing.JTextField namabrgtxt;
    private org.hq.RoundedPanel panelbrg;
    private org.hq.RoundedPanel panelchart;
    private org.hq.RoundedPanel paneldash;
    private org.hq.RoundedPanel panelkrwn;
    private org.hq.RoundedPanel panellap;
    private org.hq.RoundedPanel panellogout;
    private org.hq.RoundedPanel panelluar;
    private org.hq.RoundedPanel panelrang;
    private org.hq.RoundedPanel panelsup;
    private org.hq.RoundedPanel paneltrans;
    private javax.swing.JLabel pendapatantxt;
    private javax.swing.JLabel pengeluarantxt;
    private org.hq.RoundedPanel roundedPanel11;
    private org.hq.RoundedPanel roundedPanel12;
    private org.hq.RoundedPanel roundedPanel13;
    private org.hq.RoundedPanel roundedPanel14;
    private org.hq.RoundedPanel roundedPanel16;
    private org.hq.RoundedPanel roundedPanel17;
    private org.hq.RoundedPanel roundedPanel2;
    private org.hq.RoundedPanel roundedPanel3;
    private org.hq.RoundedPanel roundedPanel4;
    private org.hq.RoundedPanel roundedPanel5;
    private org.hq.RoundedPanel roundedPanel7;
    private org.hq.RoundedPanel roundedPanel9;
    private javax.swing.JTable tabelharian;
    private javax.swing.JTextField tanggalan;
    private javax.swing.JTextField tanggaltxt;
    private javax.swing.JLabel terjualtxt;
    private javax.swing.JTextField totaltxt;
    // End of variables declaration//GEN-END:variables
}
