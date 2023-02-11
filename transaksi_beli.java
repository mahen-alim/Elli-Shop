/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import static junit.runner.Version.id;


public class transaksi_beli extends javax.swing.JFrame {
    public void save_jual2(){
        //Fungsi untuk menyimpan transaksi pembelian ke  dalam tabel pembelian_rinci
        int jumlah;
        String sup = (String) idsuptxt.getSelectedItem();
        jumlah = Integer.valueOf(bayartxt.getText());
                if(sup.equals("Pilih ID")){// Jika idsuptxt bernilai "==ID_Supplier==" maka akan muncul notifikasi peringatan
                JOptionPane.showMessageDialog(null, "Salah Satu Data Belum Terisi");
                }else if (jumlah == 0){// Jika nominal beli sama dengan nol/belum bayar maka akan muncul notifikasi peringatan
                JOptionPane.showMessageDialog(null, "Masukkan Nominal Bayar");
                }else{
         try {
            Connection c = (Connection) Koneksi.configDB();
            int baris = tabel_trans_beli.getRowCount();

                for (int i = 0; i < baris; i++) {
                    String sql = "INSERT INTO pembelian_rinci(No_Transaksi, ID_supplier, ID_Barang, Nama_Barang, Jumlah, Harga_Beli, Total, Tanggal) VALUES('"
                        + tabel_trans_beli.getValueAt(i, 0) +"','" + tabel_trans_beli.getValueAt(i, 1) + "','" + tabel_trans_beli.getValueAt(i, 2)
                        + "','" + tabel_trans_beli.getValueAt(i, 3) + "','" + tabel_trans_beli.getValueAt(i, 4) + "','" + tabel_trans_beli.getValueAt(i, 5)
                        + "','" + tabel_trans_beli.getValueAt(i, 6) + "','" + tabel_trans_beli.getValueAt(i, 7)+ "')";
                PreparedStatement p1 = c.prepareStatement(sql);
                p1.executeUpdate();
                p1.close();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil");
                }
                
                
        } catch (Exception e) {
            System.out.println("Simpan pembelian_rinci ERROR!");
        }
        //Fungsi untuk menyimpan transaksi pembelian ke  dalam tabel laporan_beli
        try {
            Connection c = (Connection) Koneksi.configDB();
            int baris = tabel_trans_beli.getRowCount();
            String jenis = jenistxt.getText();
           
            for (int i = 0; i < baris; i++) {
            PreparedStatement p1 = c.prepareStatement("INSERT INTO laporan_beli VALUES(?,?,?,?,?,?,?,?)");
                    p1.setString(1, (String) tabel_trans_beli.getValueAt(i, 0));
                    p1.setString(2, (String) tabel_trans_beli.getValueAt(i, 2));
                    p1.setString(3, (String) tabel_trans_beli.getValueAt(i, 3));
                    p1.setString(4, (String) tabel_trans_beli.getValueAt(i, 4));
                    p1.setString(5, (String) tabel_trans_beli.getValueAt(i, 5));
                    p1.setString(6, (String) tabel_trans_beli.getValueAt(i, 6));
                    p1.setString(7, jenis);
                    p1.setString(8, (String) tabel_trans_beli.getValueAt(i, 7));
                    

                p1.executeUpdate();
                p1.close();
                JOptionPane.showMessageDialog(null,"Tersimpan ke Laporan");               
                }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        clear();      
        utama();
        kosong();
        simpantxt.setText("Rp. 0");       
        }
        tampilkan_barang();
        autonumber();
    }
    // method untuk menampilkan data barang dari tabel barang
    String tanggal;
    private DefaultTableModel model;
    public void tampilkan_barang(){
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("No.");
        model.addColumn("id_Barang");
        model.addColumn("Nama_barang");
        model.addColumn("Harga_Beli");
        model.addColumn("Stok_barang");
        model.addColumn("Expired");
        String cari = caribrgtxt1.getText(); //pencarian data berdasarkan nama barang
        try {
            int no = 1;
            String sql = "Select * From barang WHERE Nama_barang LIKE'%" + cari + "%'";
            java.sql.Connection con = (Connection) Koneksi.configDB();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()) {
                model.addRow(new Object[]{no++, res.getString(1), res.getString(2), res.getString(4), res.getString(5), res.getString(6)});

                tabelbrg2.setModel(model); 
            }
        } catch (SQLException e) {
            System.out.println("ERROR :" + e.getMessage());

        }
    }
      
    
public void totalBiaya(){
    //Method perhitungan total biaya yang didapat dari perkalian jumlah dengan harga barang lalu ditambahkan 0
    int jumlahbaris = tabel_trans_beli.getRowCount();
    int totalBiaya = 0;
    int jmlhbrg, hargabrg;
    for (int i = 0; i < jumlahbaris; i++) {
        jmlhbrg = Integer.parseInt(tabel_trans_beli.getValueAt(i, 4).toString());
        hargabrg = Integer.parseInt(tabel_trans_beli.getValueAt(i, 5).toString());
        totalBiaya = totalBiaya + (jmlhbrg * hargabrg);
    }
    totaltxt.setText(String.valueOf(totalBiaya));
    simpantxt.setText("Rp "+ totalBiaya +",00");
}
public void autonumber() {
    //Method menampilkan id barang secara otomatis dari tabel pembelian_rinci
        try {
            Connection c = (Connection)Koneksi.configDB();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM pembelian_rinci ORDER BY No_Transaksi DESC";
            ResultSet r = stm.executeQuery(sql);
            if (r.next()) {
                String nofaktur = r.getString("No_Transaksi").substring(2);
                String TR = "" +(Integer.parseInt(nofaktur)+1);
                String nol = "";

                if (TR.length()==1)
                {nol = "000";}
                else if (TR.length()==2)
                {nol = "00";}
                else if (TR.length()==3) 
                {nol = "0";}
                else if (TR.length()==4) 
                {nol = "";}
                transaksitxt.setText("TR" + nol + TR);
            } else {
                transaksitxt.setText("TR0001");
            }
            r.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

public void loadData(){
    //Method yang digunakan untuk menambahkan text kedalam tabel_trans_beli
    DefaultTableModel model = (DefaultTableModel) tabel_trans_beli.getModel();
    model.addRow(new Object[]{
        transaksitxt.getText(),
        idsuptxt.getSelectedItem(),
        idtxt.getText(),
        namatxt.getText(),
        jumlahtxt.getText(),
        hbtxt.getText(),
        totaltxt.getText(),
        tanggaltxt.getText()
        
    });
    
}

    public void kosong() {
        //Method untuk mengosongkan baris dari kolom tabel_trans_beli
        DefaultTableModel model = (DefaultTableModel) tabel_trans_beli.getModel();

        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }

    public void utama() {
        //Method untuk mengosongkan text yang telah didefinsikan menjadi tidak bernilai (NULL) dan sebagai penampil No Faktur yang baru
        transaksitxt.setText("");
        idtxt.setText("");
        namatxt.setText("");
        hbtxt.setText("");
        jumlahtxt.setText("");
        exptxt.setText("");
        autonumber();
    }

    public void clear() {
         //Method untuk mengosongkan text yang telah didefinsikan menjadi bernilai 0
        totaltxt.setText("0");
        bayartxt.setText("0");
        kembalitxt2.setText("0");
        simpantxt.setText("0");
    }

    public void clear2() {
        //Method untuk mengosongkan text yang telah didefinsikan menjadi nilai yang sudah didefinisikan
        idtxt.setText("");
        namatxt.setText("");
        hbtxt.setText("");
        jumlahtxt.setText("");
        stoktxt.setText("");
        idsuptxt.setSelectedItem("--ID_Supplier--");
        exptxt.setText("");
       
    }

    public void tambah_transaksi() {
        //Method yang digunakan untuk menampilkan text dari jumlahtxt & hbtxt ke totaltxt & simpantxt
        int jumlah, harga, total;

        jumlah = Integer.valueOf(jumlahtxt.getText());
        harga = Integer.valueOf(hbtxt.getText());
        total = jumlah * harga;

        totaltxt.setText(String.valueOf(total));
        simpantxt.setText(String.valueOf(total));

        loadData();
        totalBiaya();
        clear2();
        idtxt.requestFocus();
    }

    public void tanggal() {
        //Method yang berfungsi untuk menampilkan tanggal otomatis (continue)
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggaltxt.setText(s.format(date));
    }
    public void JOPS(){
        //Method untuk menampilkan informasi supplier ketika menginput ID Supplier
        String id = null;
        String Nama_Supplier = null;
        String Alamat_Supplier = null;
        String No_telepon = null;
        id = JOptionPane.showInputDialog("Masukkan ID Supplier: ");
       
        if(id.equals("SP0001")){
            Nama_Supplier = "Nestle";
            Alamat_Supplier = "Nganjuk";
            No_telepon = "082244796440";
        }else if(id.equals("SP0002")){
            Nama_Supplier = "Apotik Panggung";
            Alamat_Supplier = "Nganjuk";
            No_telepon = "085748826468";
        }else if(id.equals("SP0003")){
            Nama_Supplier = "Sinar Mas";
            Alamat_Supplier = "Nganjuk";
            No_telepon = "089509851946";           
        }else if(id.equals("SP0004")){
            Nama_Supplier = "Viena Roti";
            Alamat_Supplier = "Madiun";
            No_telepon = "082233671927";           
        }else if(id.equals("SP0005")){
            Nama_Supplier = "Berly Roti";
            Alamat_Supplier = "Wungu-Madiun";
            No_telepon = "0895807400305";           
        }else if(id.equals("SP0006")){
            Nama_Supplier = "Farma";
            Alamat_Supplier = "Jakarta";
            No_telepon = "0897888656552";     
       
        }
        JOptionPane.showMessageDialog(null, "Nama Supplier : "+Nama_Supplier+
        "\nAlamat Supplier : "+ Alamat_Supplier +
        "\nNo. Telepon : "+ No_telepon);
    }
    private static String getHari(){
            //Method yang berfungsi untuk menampilkan nama Hari otomatis (continue)
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
            //Method yang berfungsi untuk menampilkan nama Bulan otomatis (continue)
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
    public transaksi_beli() {
        //Method yang berfungsi untuk menampikan method-method yang telah didefinisikan 
        initComponents();
        model = new DefaultTableModel();
         tabel_trans_beli.setModel(model);
         
         model.addColumn("No transaksi");
         model.addColumn("ID Supplier");
         model.addColumn("ID Barang");
         model.addColumn("Nama Barang");
         model.addColumn("Jumlah");
         model.addColumn("Harga Beli");
         model.addColumn("Total");
          model.addColumn("Tanggal");
          tampilkan_barang();
          autonumber();
          tanggal();
           utama();
         totaltxt.setText("0");
         bayartxt.setText("0");
         kembalitxt2.setText("0");
         haritxt.setVisible(false);
         bulantxt.setVisible(false);
         jenistxt.setVisible(false);
        this.haritxt.setText(getHari());
        this.bulantxt.setText(getBulan());
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
        jPanel5 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        findbutton1 = new javax.swing.JButton();
        findbutton2 = new javax.swing.JButton();
        caribrgtxt1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        transaksitxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        idtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        namatxt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tanggaltxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jumlahtxt = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabel_trans_beli = new javax.swing.JTable();
        addbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        totaltxt = new javax.swing.JTextField();
        bayartxt = new javax.swing.JTextField();
        kembalitxt2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelbrg2 = new javax.swing.JTable();
        backbutton = new javax.swing.JLabel();
        simpantxt = new javax.swing.JTextField();
        savebutton = new javax.swing.JButton();
        refreshbtn = new javax.swing.JButton();
        hbtxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        exptxt = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        stoktxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        findbutton3 = new javax.swing.JButton();
        idsuptxt = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        haritxt = new javax.swing.JTextField();
        bulantxt = new javax.swing.JTextField();
        jenistxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(23, 176, 151));
        jLabel32.setText("TRANSAKSI PEMBELIAN");
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 50));

        jPanel3.setBackground(new java.awt.Color(23, 176, 151));
        jPanel3.setForeground(new java.awt.Color(224, 25, 25));
        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, -1, 90));

        findbutton1.setBackground(new java.awt.Color(230, 230, 230));
        findbutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/search_icon-icons.com_74128-picsay.png"))); // NOI18N
        findbutton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        findbutton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                findbutton1MouseClicked(evt);
            }
        });
        findbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findbutton1ActionPerformed(evt);
            }
        });
        jPanel5.add(findbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, 30));

        findbutton2.setBackground(new java.awt.Color(230, 230, 230));
        findbutton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/info.png"))); // NOI18N
        findbutton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        findbutton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                findbutton2MouseClicked(evt);
            }
        });
        findbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findbutton2ActionPerformed(evt);
            }
        });
        jPanel5.add(findbutton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 30, 30));

        caribrgtxt1.setBackground(new java.awt.Color(250, 250, 250));
        caribrgtxt1.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        caribrgtxt1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        caribrgtxt1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        caribrgtxt1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                caribrgtxt1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                caribrgtxt1FocusLost(evt);
            }
        });
        caribrgtxt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caribrgtxt1MouseClicked(evt);
            }
        });
        caribrgtxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caribrgtxt1ActionPerformed(evt);
            }
        });
        caribrgtxt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caribrgtxt1KeyReleased(evt);
            }
        });
        jPanel5.add(caribrgtxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 180, 30));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel3.setText("ID Supplier");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 70, 20));

        transaksitxt.setEditable(false);
        transaksitxt.setBackground(new java.awt.Color(23, 176, 151));
        transaksitxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(transaksitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 140, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel4.setText("ID Barang");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 80, -1));

        idtxt.setBackground(new java.awt.Color(0, 0, 0));
        idtxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(idtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel5.setText("Nama Barang");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, -1, -1));

        namatxt.setBackground(new java.awt.Color(0, 0, 0));
        namatxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(namatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 120, -1));

        jLabel21.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel21.setText("Tanggal");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 80, 20));

        tanggaltxt.setEditable(false);
        tanggaltxt.setBackground(new java.awt.Color(23, 176, 151));
        tanggaltxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(tanggaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 120, -1));

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel7.setText("Jumlah");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 80, -1));

        jumlahtxt.setBackground(new java.awt.Color(0, 0, 0));
        jumlahtxt.setForeground(new java.awt.Color(255, 255, 255));
        jumlahtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahtxtActionPerformed(evt);
            }
        });
        jPanel1.add(jumlahtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 130, -1));

        tabel_trans_beli.setBackground(new java.awt.Color(23, 176, 151));
        tabel_trans_beli.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabel_trans_beli.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        tabel_trans_beli.setForeground(new java.awt.Color(252, 249, 137));
        tabel_trans_beli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabel_trans_beli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabel_trans_beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_trans_beliMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabel_trans_beli);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 820, 150));

        addbutton.setBackground(new java.awt.Color(224, 25, 25));
        addbutton.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        addbutton.setForeground(new java.awt.Color(255, 255, 255));
        addbutton.setText("Tambah");
        addbutton.setBorder(null);
        addbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(addbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 270, 80, 40));

        deletebutton.setBackground(new java.awt.Color(224, 25, 25));
        deletebutton.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        deletebutton.setForeground(new java.awt.Color(255, 255, 255));
        deletebutton.setText("Hapus");
        deletebutton.setBorder(null);
        deletebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletebuttonMouseClicked(evt);
            }
        });
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(deletebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 380, 80, 40));

        totaltxt.setEditable(false);
        totaltxt.setBackground(new java.awt.Color(23, 176, 151));
        totaltxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(totaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 160, -1));

        bayartxt.setBackground(new java.awt.Color(0, 0, 0));
        bayartxt.setForeground(new java.awt.Color(255, 255, 255));
        bayartxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayartxtActionPerformed(evt);
            }
        });
        jPanel1.add(bayartxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 570, 160, -1));

        kembalitxt2.setEditable(false);
        kembalitxt2.setBackground(new java.awt.Color(23, 176, 151));
        kembalitxt2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(kembalitxt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 160, -1));

        jLabel20.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel20.setText("Kembalian");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 600, 70, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel9.setText("Bayar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 50, -1));

        jLabel10.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel10.setText("Total Bayar");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 70, -1));

        jLabel12.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel12.setText("No.Transaksi");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, 20));

        tabelbrg2.setBackground(new java.awt.Color(0, 0, 0));
        tabelbrg2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelbrg2.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        tabelbrg2.setForeground(new java.awt.Color(23, 176, 151));
        tabelbrg2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id_supplier", "Nama_supplier", "Alamat_supplier", "No_telpon"
            }
        ));
        tabelbrg2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelbrg2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbrg2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelbrg2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 820, 150));

        backbutton.setBackground(new java.awt.Color(0, 0, 0));
        backbutton.setFont(new java.awt.Font("Lucida Sans", 1, 36)); // NOI18N
        backbutton.setText("«");
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

        simpantxt.setEditable(false);
        simpantxt.setBackground(new java.awt.Color(23, 176, 151));
        simpantxt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        simpantxt.setForeground(new java.awt.Color(255, 255, 255));
        simpantxt.setText("Rp 0,00");
        simpantxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpantxtActionPerformed(evt);
            }
        });
        jPanel1.add(simpantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 550, 310, 40));

        savebutton.setBackground(new java.awt.Color(224, 25, 25));
        savebutton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        savebutton.setForeground(new java.awt.Color(255, 255, 255));
        savebutton.setText("SIMPAN");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(savebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 550, 80, 40));

        refreshbtn.setBackground(new java.awt.Color(0, 0, 0));
        refreshbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        refreshbtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshbtn.setText("Batal");
        refreshbtn.setBorder(null);
        refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbtnActionPerformed(evt);
            }
        });
        jPanel1.add(refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 330, 60, 30));

        hbtxt.setBackground(new java.awt.Color(0, 0, 0));
        hbtxt.setForeground(new java.awt.Color(255, 255, 255));
        hbtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hbtxtActionPerformed(evt);
            }
        });
        jPanel1.add(hbtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 130, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel6.setText("Harga");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 90, -1));

        exptxt.setEditable(false);
        exptxt.setBackground(new java.awt.Color(0, 0, 0));
        exptxt.setForeground(new java.awt.Color(255, 255, 255));
        exptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exptxtActionPerformed(evt);
            }
        });
        jPanel1.add(exptxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 130, -1));

        jLabel31.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel31.setText("Expired");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 90, 20));

        stoktxt.setBackground(new java.awt.Color(0, 0, 0));
        stoktxt.setForeground(new java.awt.Color(255, 255, 255));
        stoktxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stoktxtActionPerformed(evt);
            }
        });
        jPanel1.add(stoktxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 90, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/transaksi_jual_logo.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 360, 370));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 30, 560));

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
        jPanel1.add(findbutton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, 30));

        idsuptxt.setBackground(new java.awt.Color(23, 176, 151));
        idsuptxt.setForeground(new java.awt.Color(255, 255, 255));
        idsuptxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ID", "SP0001", "SP0002", "SP0003", "SP0004", "SP0005", "SP0006" }));
        idsuptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idsuptxtActionPerformed(evt);
            }
        });
        jPanel1.add(idsuptxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 110, -1));

        jTextField1.setText("jTextField1");
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 270, -1, -1));

        haritxt.setText("Rabu");
        haritxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                haritxtActionPerformed(evt);
            }
        });
        jPanel1.add(haritxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 50, -1));

        bulantxt.setText("Desember");
        bulantxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bulantxtActionPerformed(evt);
            }
        });
        jPanel1.add(bulantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, -1, -1));

        jenistxt.setText("Cash");
        jPanel1.add(jenistxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 290, -1, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel11.setText("Stok");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 90, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jumlahtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahtxtActionPerformed
        // TODO add your handling code here:
        tambah_transaksi();
    }//GEN-LAST:event_jumlahtxtActionPerformed

    private void caribrgtxt1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_caribrgtxt1FocusGained

    }//GEN-LAST:event_caribrgtxt1FocusGained

    private void caribrgtxt1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_caribrgtxt1FocusLost

    }//GEN-LAST:event_caribrgtxt1FocusLost

    private void caribrgtxt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caribrgtxt1MouseClicked

    }//GEN-LAST:event_caribrgtxt1MouseClicked

    private void caribrgtxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caribrgtxt1ActionPerformed
        tampilkan_barang();
    }//GEN-LAST:event_caribrgtxt1ActionPerformed

    private void caribrgtxt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caribrgtxt1KeyReleased
        tampilkan_barang();
    }//GEN-LAST:event_caribrgtxt1KeyReleased

    private void findbutton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findbutton1MouseClicked
        tampilkan_barang();
    }//GEN-LAST:event_findbutton1MouseClicked

    private void findbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbutton1ActionPerformed

    }//GEN-LAST:event_findbutton1ActionPerformed

    private void tabel_trans_beliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_trans_beliMouseClicked
       //Aksi yang digunakan untuk menampilkan data dalam kolom tabel_trans_beli ke kolom text yang telah didefinisikan 
       //ketika diklik oleh user
        int baris = tabel_trans_beli.rowAtPoint(evt.getPoint());
        String id = tabel_trans_beli.getValueAt(baris, 1).toString();
        idtxt.setText(id);
        String nama = tabel_trans_beli.getValueAt(baris, 2).toString();
        namatxt.setText(nama);
        String harga = tabel_trans_beli.getValueAt(baris, 4).toString();
        hbtxt.setText(harga);
    }//GEN-LAST:event_tabel_trans_beliMouseClicked

    private void addbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbuttonActionPerformed
        //Pemanggilan method tambah_transaksi();
        String id = (String) idsuptxt.getSelectedItem();
        String jmlh = jumlahtxt.getText();
        if(id=="Pilih ID"){
          JOptionPane.showMessageDialog(null, "Isikan ID Supplier terlebih dahulu");
        }else{
            tambah_transaksi();
        }
    }//GEN-LAST:event_addbuttonActionPerformed

    private void deletebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletebuttonMouseClicked

    }//GEN-LAST:event_deletebuttonMouseClicked

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        //Aksi yang digunakan untuk menghapus data dari kolom tabel_trans_beli
        DefaultTableModel model = (DefaultTableModel) tabel_trans_beli.getModel();
        int row = tabel_trans_beli.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        bayartxt.setText("0");
        kembalitxt2.setText("0");
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void backbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseClicked
       //Aksi untuk kembali ke window transaksi2
        transaksi2 board = new transaksi2();
        board.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_backbuttonMouseClicked

    private void bayartxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayartxtActionPerformed
        // Aksi untuk melakukan pembayaran, ketika harga beli lebih besar dari uang pembayaran maka akan muncul pesan yang telah didefinisikan
        int total, bayar, kembalian;

        total = Integer.valueOf(totaltxt.getText());
        bayar = Integer.valueOf(bayartxt.getText());

        if (total > bayar) {
            JOptionPane.showMessageDialog(null, "Uang tidak cukup untuk melakukan pembayaran");
        }else if (total == bayar){
            javax.swing.JOptionPane.showMessageDialog(null, "Uang Pas");
        }else { //Jika uang bayar lebih besar dari harga beli maka akan muncul nominal pengembalian di kolom kembalitxt
            kembalian = bayar - total;
            kembalitxt2.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_bayartxtActionPerformed

    private void simpantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpantxtActionPerformed

    }//GEN-LAST:event_simpantxtActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
       
        String bayar = bayartxt.getText();
        if(bayar=="0"){
            JOptionPane.showMessageDialog(null, "Masukkan nominal bayar terlebih dahulu");
        }else{
            autonumber();
        save_jual2();
        }
       
       
    }//GEN-LAST:event_savebuttonActionPerformed

    private void tabelbrg2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbrg2MouseClicked
        //Aksi untuk menampilkan data dari tabelbrg2 kedalam kolom-kolom text yang telah didefinisikan  ketika salah satu baris diklik oleh user
        int baris = tabelbrg2.rowAtPoint(evt.getPoint());
        String id = tabelbrg2.getValueAt(baris, 1).toString();
        idtxt.setText(id);
        String nama = tabelbrg2.getValueAt(baris, 2).toString();
        namatxt.setText(nama);
        String harga = tabelbrg2.getValueAt(baris, 3).toString();
        hbtxt.setText(harga);
        String stok = tabelbrg2.getValueAt(baris, 4).toString();
        stoktxt.setText(stok);
        String tgl = tabelbrg2.getValueAt(baris, 5).toString();
        exptxt.setText(tgl);
    }//GEN-LAST:event_tabelbrg2MouseClicked

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        //Pemanggilan method clear2();
        clear2();
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void hbtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hbtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hbtxtActionPerformed

    private void exptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exptxtActionPerformed

    private void stoktxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stoktxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stoktxtActionPerformed

    private void backbuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseExited
        // TODO add your handling code here:
        backbutton.setForeground(Color.BLACK);
    }//GEN-LAST:event_backbuttonMouseExited

    private void backbuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseEntered
        // TODO add your handling code here:
        backbutton.setForeground(Color.GREEN);
    }//GEN-LAST:event_backbuttonMouseEntered

    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1FocusGained

    private void findbutton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findbutton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_findbutton3MouseClicked

    private void findbutton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbutton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_findbutton3ActionPerformed

    private void haritxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_haritxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_haritxtActionPerformed

    private void findbutton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findbutton2MouseClicked
        // TODO add your handling code here:
        JOPS();
    }//GEN-LAST:event_findbutton2MouseClicked

    private void findbutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbutton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_findbutton2ActionPerformed

    private void bulantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bulantxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bulantxtActionPerformed

    private void idsuptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idsuptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idsuptxtActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_beli().setVisible(true);
            }
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbutton;
    private javax.swing.JLabel backbutton;
    private javax.swing.JTextField bayartxt;
    private javax.swing.JTextField bulantxt;
    private javax.swing.JTextField caribrgtxt1;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField exptxt;
    private javax.swing.JButton findbutton1;
    private javax.swing.JButton findbutton2;
    private javax.swing.JButton findbutton3;
    private javax.swing.JTextField haritxt;
    private javax.swing.JTextField hbtxt;
    private javax.swing.JComboBox<String> idsuptxt;
    private javax.swing.JTextField idtxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jenistxt;
    private javax.swing.JTextField jumlahtxt;
    private javax.swing.JTextField kembalitxt2;
    private javax.swing.JTextField namatxt;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JButton savebutton;
    private javax.swing.JTextField simpantxt;
    private javax.swing.JTextField stoktxt;
    private javax.swing.JTable tabel_trans_beli;
    private javax.swing.JTable tabelbrg2;
    private javax.swing.JTextField tanggaltxt;
    private javax.swing.JTextField totaltxt;
    private javax.swing.JTextField transaksitxt;
    // End of variables declaration//GEN-END:variables
}
