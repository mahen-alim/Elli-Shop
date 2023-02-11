/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;


import static base.barang2.gettgl;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import static java.util.Objects.hash;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import static net.sf.jasperreports.engine.util.ObjectUtils.hash;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
public class transaksi_jual extends javax.swing.JFrame {
    public void savejual(){
    //Method untuk menyimpan transaksi penjualan ke  dalam tabel penjualan_rinci                               
        int jumlah, stok, bayar;

        jumlah = Integer.valueOf(jumlahtxt.getText());
        stok = Integer.valueOf(stoktxt.getText());
        bayar = Integer.valueOf(bayartxt.getText());

        if (jumlah > stok) {// Jika jumlah beli lebih dari stok barang maka akan muncul notifikasi peringatan
            JOptionPane.showMessageDialog(null, "Jumlah Stok Tidak Mencukupi/Habis");
            
        }else if (bayar == 0){// Jika nominal beli sama dengan nol/belum bayar maka akan muncul notifikasi peringatan
            JOptionPane.showMessageDialog(null, "Masukkan Nominal Bayar");
        }
        else{
            try {
            Connection c = (Connection) Koneksi.configDB();
            int baris = tabel_trans_jual.getRowCount();
            String tunai = bayartxt.getText();
            String kembalian = kembalitxt.getText();          

                for (int i = 0; i < baris; i++) {
                    String sql = "INSERT INTO penjualan_rinci(No_Transaksi, ID_Pembeli, ID_Barang, Nama_Barang, Jumlah, Harga_Jual, Total, Tanggal, Tunai, Kembalian) VALUES('"
                        + tabel_trans_jual.getValueAt(i, 0) +"','" + tabel_trans_jual.getValueAt(i, 1) + "','" + tabel_trans_jual.getValueAt(i, 2)
                        + "','" + tabel_trans_jual.getValueAt(i, 3) + "','" + tabel_trans_jual.getValueAt(i, 4) + "','" + tabel_trans_jual.getValueAt(i, 5)
                        + "','" + tabel_trans_jual.getValueAt(i, 6) + "','" + tabel_trans_jual.getValueAt(i, 7)+ "','"+tunai+"','"+kembalian+"')";
                PreparedStatement p1 = c.prepareStatement(sql);
                p1.executeUpdate();
                p1.close();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil");
                }
                
        } catch (Exception e) {
            System.out.println("Simpan penjual_rinci ERROR!");
        }
          //Method untuk menyimpan transaksi penjualan ke  dalam tabel laporan_jual
          try {
            Connection c = (Connection) Koneksi.configDB();
            int baris = tabel_trans_jual.getRowCount();
            String jenis = jenistxt.getText();
            
            for (int i = 0; i < baris; i++) {
            PreparedStatement p1 = c.prepareStatement("INSERT INTO laporan_jual VALUES(?,?,?,?,?,?,?,?)");
                      p1.setString(1, (String) tabel_trans_jual.getValueAt(i, 0));
                      p1.setString(2, (String) tabel_trans_jual.getValueAt(i, 2));
                      p1.setString(3, (String) tabel_trans_jual.getValueAt(i, 3));
                      p1.setString(4, (String) tabel_trans_jual.getValueAt(i, 4));
                      p1.setString(5, (String) tabel_trans_jual.getValueAt(i, 5));
                      p1.setString(6, (String) tabel_trans_jual.getValueAt(i, 6));
                      p1.setString(7, jenis);
                      p1.setString(8, (String) tabel_trans_jual.getValueAt(i, 7));
                     

                p1.executeUpdate();
                p1.close();
                JOptionPane.showMessageDialog(null,"Tersimpan ke Laporan");               
                }
          
        } catch (Exception e) {
            e.printStackTrace();
           
        }
         clear(); 
         kosong();
         simpantxt.setText("Rp 0,00");
         utama();
        }
          
        tampilkan_barang();
        autonumber();        
        autonumber3();
       
    }
    
    String tanggal;
    private DefaultTableModel model;
    // method untuk menampilkan data barang dari tabel barang
    public void tampilkan_barang(){
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("No.");
        model.addColumn("id_Barang");
        model.addColumn("Nama_barang");
        model.addColumn("Harga_jual");
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
                model.addRow(new Object[]{no++, res.getString(1), res.getString(2), res.getString(3), res.getString(5), res.getString(6)});

                tabel_barang1.setModel(model); 
            }
        } catch (SQLException e) {
            System.out.println("ERROR :" + e.getMessage());

        }
    }
    
public void totalBiaya(){
     //Method perhitungan total biaya yang didapat dari perkalian jumlah dengan harga barang lalu ditambahkan 0
    int jumlahbaris = tabel_trans_jual.getRowCount();
    int totalBiaya = 0;
    int jmlhbrg, hargabrg;
    for (int i = 0; i < jumlahbaris; i++) {
        jmlhbrg = Integer.parseInt(tabel_trans_jual.getValueAt(i, 4).toString());
        hargabrg = Integer.parseInt(tabel_trans_jual.getValueAt(i, 5).toString());
        totalBiaya = totalBiaya + (jmlhbrg * hargabrg);
    }
    totaltxt.setText(String.valueOf(totalBiaya));
    simpantxt.setText("Rp "+ totalBiaya +",00");
}
public void autonumber(){
    //Method menampilkan No. Transaksi secara otomatis dari tabel penjualan_rinci
        try {
            Connection c = (Connection)Koneksi.configDB();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM penjualan_rinci ORDER BY No_Transaksi DESC";
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
            System.out.println("Autonumber ERROR!");
        }
    }

public void autonumber3(){
    //Method menampilkan id pembeli secara otomatis dari tabel penjualan_rinci
        try {
            Connection c = (Connection)Koneksi.configDB();
            Statement stm = c.createStatement();
            String sql1 = "SELECT * FROM penjualan_rinci ORDER BY ID_Pembeli DESC";
            ResultSet res = stm.executeQuery(sql1);
            if (res.next()) {
                String nofaktur = res.getString("ID_Pembeli").substring(1);
                String TRi = "" +(Integer.parseInt(nofaktur)+1);
                String nol = "";

                if (TRi.length()==1)
                {nol = "000";}
                else if (TRi.length()==2)
                {nol = "00";}
                else if (TRi.length()==3) 
                {nol = "0";}
                else if (TRi.length()==4) 
                {nol = "";}
                
                pembelitxt.setText("P" + nol + TRi);
            } else {
                pembelitxt.setText("P0006");
            }
            res.close();
        
        } catch (Exception e) {
            System.out.println("Autonumber ERROR!");
        }
    }
public void loadData(){
    //Method yang digunakan untuk menambahkan text kedalam tabel_trans_jual
    DefaultTableModel model = (DefaultTableModel) tabel_trans_jual.getModel();
    model.addRow(new Object[]{
        transaksitxt.getText(),
        pembelitxt.getText(),
        idtxt.getText(),
        namatxt.getText(),
        jumlahtxt.getText(),
        hargatxt.getText(),
        totaltxt.getText(),
        tanggaltxt.getText(),
   
    });
    
}

    public void kosong() {
        //Method untuk mengosongkan baris dari kolom tabel_trans_jual
        DefaultTableModel model = (DefaultTableModel) tabel_trans_jual.getModel();

        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }

    public void utama() {
        //Method untuk mengosongkan text yang telah didefinsikan menjadi tidak bernilai (NULL) dan sebagai penampil No Faktur yang baru
        transaksitxt.setText("");
        idtxt.setText("");
        namatxt.setText("");
        hargatxt.setText("");
        jumlahtxt.setText("");
        stoktxt.setText("");
        exptxt.setText("");
        autonumber();
    }

    public void clear() {
        //Method untuk mengosongkan text yang telah didefinsikan menjadi bernilai 0
        pembelitxt.setText("0");
        totaltxt.setText("0");
        bayartxt.setText("0");
        kembalitxt.setText("0");
        simpantxt.setText("0");
    }

    public void clear2() {
        //Method untuk mengosongkan text yang telah didefinsikan menjadi tidak bernilai (NULL)
        idtxt.setText("");
        namatxt.setText("");
        hargatxt.setText("");
        
        
    }

    public void tambah_transaksi() {
         //Method yang digunakan untuk menampilkan text dari jumlahtxt & hbtxt ke totaltxt & simpantxt
        int jumlah, harga, total;

        jumlah = Integer.valueOf(jumlahtxt.getText());
        harga = Integer.valueOf(hargatxt.getText());
        total = jumlah * harga;

        totaltxt.setText(String.valueOf(total));
        simpantxt.setText("Rp "+ total +",00");

        loadData();
        totalBiaya();
        clear2();
        
        idtxt.requestFocus();
    }
      public void tanggal() {
          //Method yang berfungsi untuk menampilkan tanggal otomatis (continue)
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        tanggaltxt.setText(s.format(date));}
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
      
    
     public void JOPS(){
        //Method untuk menampilkan informasi toko
        String Nama_Supplier = "Elli Shop";
        String namapemilik = "";
        String sektor = "UMKM";
        String Alamat_Supplier = "Nganjuk";
        String jenistoko = "Toko Kelontong";
        String jam = "08am - 09pm";
        String barang_termurah = "Royco Ayam";
        String barang_termahal = "Rokok Gudang Garam";
       
      
        JOptionPane.showMessageDialog(null, "Nama Toko : "+Nama_Supplier+
        "\nNama Pemilik : "+ namapemilik +       
        "\nSektor Usaha : "+ sektor +        
        "\nAlamat Toko : "+ Alamat_Supplier +
        "\nJenis Usaha : "+ jenistoko+
        "\nJam Operasional : "+ jam +         
        "\nBarang Termurah : "+ barang_termurah+
        "\nBarang Termahal : "+ barang_termahal);
    }
    

    public transaksi_jual() throws SQLException {
        //Method yang berfungsi untuk menampikan method-method yang telah didefinisikan 
        initComponents();
        //create table
        model = new DefaultTableModel();
        tabel_trans_jual.setModel(model);

        model.addColumn("No transaksi");
        model.addColumn("ID Pembeli");
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah");
        model.addColumn("Harga Jual");
        model.addColumn("Total");
        model.addColumn("Tanggal");
          tampilkan_barang();
          autonumber();         
            utama();
           tanggal();       
          autonumber3();  
        this.haritxt.setText(getHari());
        this.bulantxt.setText(getBulan());
        totaltxt.setText("0");
        bayartxt.setText("0");
        kembalitxt.setText("0");
        pembelitxt.requestFocus();

        idlaporantxt.setVisible(false);
        haritxt.setVisible(false);
        bulantxt.setVisible(false);
        jenistxt.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        kembalitxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        idtxt = new javax.swing.JTextField();
        namatxt = new javax.swing.JTextField();
        hargatxt = new javax.swing.JTextField();
        jumlahtxt = new javax.swing.JTextField();
        totaltxt = new javax.swing.JTextField();
        bayartxt = new javax.swing.JTextField();
        addbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        findbutton1 = new javax.swing.JButton();
        caribrgtxt1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        findbutton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_trans_jual = new javax.swing.JTable();
        tanggaltxt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        caribrgtxt = new javax.swing.JTextField();
        findbutton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_barang1 = new javax.swing.JTable();
        simpantxt = new javax.swing.JTextField();
        savebutton = new javax.swing.JButton();
        backbutton = new javax.swing.JLabel();
        refreshbtn = new javax.swing.JButton();
        stoktxt = new javax.swing.JTextField();
        exptxt = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        haritxt = new javax.swing.JTextField();
        bulantxt = new javax.swing.JTextField();
        jenistxt = new javax.swing.JTextField();
        idlaporantxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        transaksitxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        pembelitxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kembalitxt.setEditable(false);
        kembalitxt.setBackground(new java.awt.Color(255, 200, 78));
        jPanel1.add(kembalitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 160, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel3.setText("ID Pembeli");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 70, 20));

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel4.setText("ID Barang");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 80, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel5.setText("Nama Barang");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, -1, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel6.setText("Harga");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 80, -1));

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel7.setText("Jumlah");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 80, -1));

        jLabel10.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel10.setText("Total Bayar");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 70, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel9.setText("Bayar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 50, -1));

        idtxt.setEditable(false);
        idtxt.setBackground(new java.awt.Color(0, 0, 0));
        idtxt.setForeground(new java.awt.Color(255, 255, 255));
        idtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idtxtActionPerformed(evt);
            }
        });
        jPanel1.add(idtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, -1));

        namatxt.setBackground(new java.awt.Color(0, 0, 0));
        namatxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(namatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 120, -1));

        hargatxt.setBackground(new java.awt.Color(0, 0, 0));
        hargatxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(hargatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 130, -1));

        jumlahtxt.setBackground(new java.awt.Color(0, 0, 0));
        jumlahtxt.setForeground(new java.awt.Color(255, 255, 255));
        jumlahtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahtxtActionPerformed(evt);
            }
        });
        jPanel1.add(jumlahtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 130, -1));

        totaltxt.setEditable(false);
        totaltxt.setBackground(new java.awt.Color(255, 200, 78));
        jPanel1.add(totaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 160, -1));

        bayartxt.setBackground(new java.awt.Color(0, 0, 0));
        bayartxt.setForeground(new java.awt.Color(255, 255, 255));
        bayartxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayartxtActionPerformed(evt);
            }
        });
        jPanel1.add(bayartxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 570, 160, -1));

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

        jLabel20.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel20.setText("Kembalian");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 600, 70, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 200, 78));
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, 10, 90));

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
        jPanel2.add(findbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, 30));

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
        jPanel2.add(caribrgtxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 180, 30));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 200, 78));
        jLabel29.setText("TRANSAKSI PENJUALAN");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 50));

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
        jPanel2.add(findbutton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 30, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 70));

        tabel_trans_jual.setBackground(new java.awt.Color(255, 200, 78));
        tabel_trans_jual.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabel_trans_jual.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        tabel_trans_jual.setForeground(new java.awt.Color(0, 87, 118));
        tabel_trans_jual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabel_trans_jual.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabel_trans_jual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_trans_jualMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_trans_jual);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 820, 150));

        tanggaltxt.setEditable(false);
        tanggaltxt.setBackground(new java.awt.Color(255, 200, 78));
        jPanel1.add(tanggaltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 120, -1));

        jLabel21.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel21.setText("Expired");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 50, 20));

        caribrgtxt.setBackground(new java.awt.Color(250, 250, 250));
        caribrgtxt.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        caribrgtxt.setForeground(new java.awt.Color(153, 153, 153));
        caribrgtxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        caribrgtxt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        caribrgtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                caribrgtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                caribrgtxtFocusLost(evt);
            }
        });
        caribrgtxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caribrgtxtMouseClicked(evt);
            }
        });
        caribrgtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caribrgtxtActionPerformed(evt);
            }
        });
        caribrgtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caribrgtxtKeyReleased(evt);
            }
        });
        jPanel1.add(caribrgtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 410, 40));

        findbutton.setBackground(new java.awt.Color(230, 230, 230));
        findbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/search_icon-icons.com_74128-picsay.png"))); // NOI18N
        findbutton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(0, 0, 0)));
        findbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                findbuttonMouseClicked(evt);
            }
        });
        findbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(findbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 40, 40));

        tabel_barang1.setBackground(new java.awt.Color(0, 0, 0));
        tabel_barang1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabel_barang1.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        tabel_barang1.setForeground(new java.awt.Color(255, 200, 78));
        tabel_barang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id_Barang", "Nama_Barang", "Harga_Jual", "Stok_Barang"
            }
        ));
        tabel_barang1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabel_barang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_barang1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_barang1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 820, 150));

        simpantxt.setEditable(false);
        simpantxt.setBackground(new java.awt.Color(255, 200, 78));
        simpantxt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
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

        stoktxt.setBackground(new java.awt.Color(0, 0, 0));
        stoktxt.setForeground(new java.awt.Color(255, 255, 255));
        stoktxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stoktxtActionPerformed(evt);
            }
        });
        jPanel1.add(stoktxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 90, -1));

        exptxt.setEditable(false);
        exptxt.setBackground(new java.awt.Color(0, 0, 0));
        exptxt.setForeground(new java.awt.Color(255, 255, 255));
        exptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exptxtActionPerformed(evt);
            }
        });
        jPanel1.add(exptxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 130, -1));

        jLabel30.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel30.setText("Tanggal");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 50, 20));

        haritxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                haritxtActionPerformed(evt);
            }
        });
        jPanel1.add(haritxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 480, 70, -1));

        bulantxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bulantxtActionPerformed(evt);
            }
        });
        jPanel1.add(bulantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 480, 120, -1));

        jenistxt.setText("Cash");
        jPanel1.add(jenistxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 50, -1));

        idlaporantxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idlaporantxtActionPerformed(evt);
            }
        });
        jPanel1.add(idlaporantxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 110, 20));

        jLabel13.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel13.setText("No.Transaksi");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, 20));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 10, 560));

        transaksitxt.setEditable(false);
        transaksitxt.setBackground(new java.awt.Color(255, 200, 78));
        jPanel1.add(transaksitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 130, -1));

        jLabel11.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel11.setText("Stok");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 80, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_komponen_figma/transaksi_beli_logo.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 710, 530));

        pembelitxt.setEditable(false);
        jPanel1.add(pembelitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 630));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabel_trans_jualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_trans_jualMouseClicked
         //Aksi yang digunakan untuk menampilkan data dalam kolom tabel_trans_beli ke kolom text yang telah didefinisikan 
       //ketika diklik oleh user
        int baris = tabel_trans_jual.rowAtPoint(evt.getPoint());
        String id = tabel_trans_jual.getValueAt(baris, 1).toString();
        idtxt.setText(id);
        String nama = tabel_trans_jual.getValueAt(baris, 2).toString();
        namatxt.setText(nama);
        String harga = tabel_trans_jual.getValueAt(baris, 4).toString();
        hargatxt.setText(harga);
    }//GEN-LAST:event_tabel_trans_jualMouseClicked

    private void addbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbuttonActionPerformed
        //Pemanggilan method tambah_transaksi(); 
        tambah_transaksi();
        
    

    }//GEN-LAST:event_addbuttonActionPerformed

    private void deletebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletebuttonMouseClicked

    }//GEN-LAST:event_deletebuttonMouseClicked

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        //Aksi yang digunakan untuk menghapus data dari kolom tabel_trans_beli
        DefaultTableModel model = (DefaultTableModel) tabel_trans_jual.getModel();
        int row = tabel_trans_jual.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        bayartxt.setText("0");
        kembalitxt.setText("0");
        simpantxt.setText("Rp 0,00");

    }//GEN-LAST:event_deletebuttonActionPerformed

    private void jumlahtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahtxtActionPerformed
        // Pemanggilan method tambah_transaksi();
        tambah_transaksi();
    }//GEN-LAST:event_jumlahtxtActionPerformed

    private void bayartxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayartxtActionPerformed
      // Aksi untuk melakukan pembayaran, ketika harga beli lebih besar dari uang pembayaran maka akan muncul pesan yang telah didefinisikan
        int total, bayar, kembalian;

        total = Integer.valueOf(totaltxt.getText());
        bayar = Integer.valueOf(bayartxt.getText());

        if (total > bayar) {// Jika nominal bayar lebih kecil dari total harga maka akan muncul notifikasi peringatan
            JOptionPane.showMessageDialog(null, "Uang tidak cukup untuk melakukan pembayaran");
        }else if (total == bayar){// Jika nominal bayar sama dengan total harga maka akan muncul notifikasi "Uang Pas
            JOptionPane.showMessageDialog(null, "Uang Pas");
        }else{ //Jika uang bayar lebih besar dari harga beli maka akan muncul nominal pengembalian di kolom kembalitxt
            kembalian = bayar - total;
            kembalitxt.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_bayartxtActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        String bayar = bayartxt.getText();
        if (bayar == "0") {
            JOptionPane.showMessageDialog(null, "Masukkan nominal bayar terlebih dahulu");
        } else {
            savejual();
            autonumber3();
        }
    }//GEN-LAST:event_savebuttonActionPerformed

    private void caribrgtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_caribrgtxtFocusGained

    }//GEN-LAST:event_caribrgtxtFocusGained

    private void caribrgtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_caribrgtxtFocusLost

    }//GEN-LAST:event_caribrgtxtFocusLost

    private void caribrgtxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caribrgtxtMouseClicked

    }//GEN-LAST:event_caribrgtxtMouseClicked

    private void caribrgtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caribrgtxtActionPerformed

    }//GEN-LAST:event_caribrgtxtActionPerformed

    private void caribrgtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caribrgtxtKeyReleased

    }//GEN-LAST:event_caribrgtxtKeyReleased

    private void findbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_findbuttonMouseClicked
        //pemanggilan method tampilkan_barang();
        tampilkan_barang();
    }//GEN-LAST:event_findbuttonMouseClicked

    private void findbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbuttonActionPerformed

    }//GEN-LAST:event_findbuttonActionPerformed

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
         //pemanggilan method tampilkan_barang();
        tampilkan_barang();

    }//GEN-LAST:event_findbutton1MouseClicked

    private void findbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findbutton1ActionPerformed

    }//GEN-LAST:event_findbutton1ActionPerformed

    private void tabel_barang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barang1MouseClicked
        //Aksi untuk menampilkan data dari tabel_barang1 kedalam kolom-kolom text yang telah didefinisikan  ketika salah satu baris diklik oleh user
        int baris = tabel_barang1.rowAtPoint(evt.getPoint());
        String id = tabel_barang1.getValueAt(baris, 1).toString();
        idtxt.setText(id);
        String nama = tabel_barang1.getValueAt(baris, 2).toString();
        namatxt.setText(nama);
        String harga = tabel_barang1.getValueAt(baris, 3).toString();
        hargatxt.setText(harga);
        String stok = tabel_barang1.getValueAt(baris, 4).toString();
        stoktxt.setText(stok);
        String tgl = tabel_barang1.getValueAt(baris, 5).toString();
        exptxt.setText(tgl);
        
    }//GEN-LAST:event_tabel_barang1MouseClicked

    private void simpantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpantxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpantxtActionPerformed

    private void backbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseClicked
        transaksi2 board = new transaksi2();
        board.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_backbuttonMouseClicked

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        //pemanggilan method clear2();
        clear2();
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void stoktxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stoktxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stoktxtActionPerformed

    private void exptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exptxtActionPerformed

    private void idtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idtxtActionPerformed

    private void backbuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseExited
        // TODO add your handling code here:
        backbutton.setForeground(Color.BLACK);
    }//GEN-LAST:event_backbuttonMouseExited

    private void backbuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backbuttonMouseEntered
        // TODO add your handling code here:
         backbutton.setForeground(Color.orange);
    }//GEN-LAST:event_backbuttonMouseEntered

    private void bulantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bulantxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bulantxtActionPerformed

    private void idlaporantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idlaporantxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idlaporantxtActionPerformed

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

    /**
     * @param args the command line arguments
     */
        
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
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new transaksi_jual().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(transaksi_jual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbutton;
    private javax.swing.JLabel backbutton;
    private javax.swing.JTextField bayartxt;
    private javax.swing.JTextField bulantxt;
    private javax.swing.JTextField caribrgtxt;
    private javax.swing.JTextField caribrgtxt1;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField exptxt;
    private javax.swing.JButton findbutton;
    private javax.swing.JButton findbutton1;
    private javax.swing.JButton findbutton2;
    private javax.swing.JTextField hargatxt;
    private javax.swing.JTextField haritxt;
    private javax.swing.JTextField idlaporantxt;
    private javax.swing.JTextField idtxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jenistxt;
    private javax.swing.JTextField jumlahtxt;
    private javax.swing.JTextField kembalitxt;
    private javax.swing.JTextField namatxt;
    private javax.swing.JTextField pembelitxt;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JButton savebutton;
    private javax.swing.JTextField simpantxt;
    private javax.swing.JTextField stoktxt;
    private javax.swing.JTable tabel_barang1;
    private javax.swing.JTable tabel_trans_jual;
    private javax.swing.JTextField tanggaltxt;
    private javax.swing.JTextField totaltxt;
    private javax.swing.JTextField transaksitxt;
    // End of variables declaration//GEN-END:variables
}
