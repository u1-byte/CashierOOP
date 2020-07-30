/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasircoba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class manajer extends javax.swing.JFrame {
    Connection con = config_db.config_db();
    md5hashing md5 = new md5hashing();
    private ResultSet res;
    private Statement stm;
    private PreparedStatement ps;
    String sql, barang;
    String pass;
    int karyawan, i, cek_id, cek_id2, stok, stok2, total_stok, act, detkasir, detgudang, in, out;
    int indeks = 3;
    private DefaultTableModel m = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

    /**
     * Creates new form manajer
     */
    public manajer(int kr) {
        initComponents();
        karyawan = kr;
        m.addColumn("Id Barang");
        m.addColumn("Id Supplier");
        m.addColumn("Nama Barang");
        m.addColumn("Jenis Barang");
        m.addColumn("Stok");
        m.addColumn("Harga Beli");
        m.addColumn("Harga Jual");
        try{
            stm = con.createStatement();
            sql = "select * from karyawan where id_karyawan = '" + karyawan +"';";
            res = stm.executeQuery(sql);
            res.next();
            nama.setText("(" + res.getString("nama_karyawan") + ")");
            showBarang();
            showKaryawan();
            showRiwayatTR();
            showGudang();
            showInfo();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }

    private void clearForm(){
        idSup.setText(null);
        idBrg.setText(null);
        namaBrg.setText(null);
        jenisBrg.setText(null);
        stokBrg.setText(null);
        hbBrg.setText(null);
        hjBrg.setText(null);
    }
    
    private void clearForm2(){
        userKar.setText(null);
        passKar.setText(null);
        namaKar.setText(null);
        pilJabatan.setSelectedItem(null);
        alamatKar.setText(null);
        telpKar.setText(null);
    }
    
    private void showBarang(){
        try{
            stm = con.createStatement();
            sql = "select * from barang;";
            res = stm.executeQuery(sql);
            while(res.next()){
                m.addRow(new Object[]{res.getString("id_barang"),
                    res.getString("id_supplier"),
                    res.getString("nama_barang"),
                    res.getString("jenis_barang"),
                    res.getString("stok_barang"),
                    res.getString("harga_beli"),
                    res.getString("harga_jual")});
            }
            listbrg.setModel(m);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showKaryawan(){
        DefaultTableModel m2 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        m2.addColumn("Id Karyawan");
        m2.addColumn("Username");
        m2.addColumn("Password");
        m2.addColumn("Nama");
        m2.addColumn("Jabatan");
        m2.addColumn("Alamat");
        m2.addColumn("No Telp");
        try{
            stm = con.createStatement();
            sql = "select * from karyawan;";
            res = stm.executeQuery(sql);
            while(res.next()){
                m2.addRow(new Object[]{res.getString("id_karyawan"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("nama_karyawan"),
                    res.getString("jabatan_karyawan"),
                    res.getString("alamat_karyawan"),
                    res.getString("notelp_karyawan")});
            }
            listkar.setModel(m2);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showRiwayatTR(){
        DefaultTableModel m3 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        m3.addColumn("Id Transaksi");
        m3.addColumn("Nama Kasir");
        m3.addColumn("Total Transaksi");
        m3.addColumn("Total Barang");
        m3.addColumn("Total Profit");
        m3.addColumn("Tanggal");
        try{
            stm = con.createStatement();
            sql = "select lk.id_kasir, k.nama_karyawan, lk.total_transaksi, lk.total_barang, lk.total_profit, lk.tanggal_transaksi"
                    + " from log_kasir lk inner join karyawan k on lk.id_karyawan = k.id_karyawan;";
            res = stm.executeQuery(sql);
            while(res.next()){
                m3.addRow(new Object[]{res.getString("lk.id_kasir"),
                    res.getString("k.nama_karyawan"),
                    res.getString("lk.total_transaksi"),
                    res.getString("lk.total_barang"),
                    res.getString("lk.total_profit"),
                    res.getString("lk.tanggal_transaksi")});
            }
            hisTR.setModel(m3);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showDetTR(){
        DefaultTableModel m4 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        m4.addColumn("No");
        m4.addColumn("Nama Barang");
        m4.addColumn("Jenis Barang");
        m4.addColumn("Harga");
        m4.addColumn("Jumlah Beli");
        m4.addColumn("Subtotal");
        m4.addColumn("Profit");
        try{
            i = 1;
            stm = con.createStatement();
            sql = "select b.nama_barang, b.jenis_barang, b.harga_jual, dk.qty_barang, dk.subtotal, dk.profit_product"
                    + " from detail_log_kasir dk inner join barang b on dk.id_barang = b.id_barang where dk.id_kasir = '" + detkasir + "';";
            res = stm.executeQuery(sql);
            while(res.next()){
                m4.addRow(new Object[]{i++, res.getString("b.nama_barang"),
                    res.getString("b.jenis_barang"),
                    res.getString("b.harga_jual"),
                    res.getString("dk.qty_barang"),
                    res.getString("dk.subtotal"),
                    res.getString("dk.profit_product")});
            }
            detTR.setModel(m4);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showGudang(){
        DefaultTableModel m5 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        m5.addColumn("No");
        m5.addColumn("Id");
        m5.addColumn("Username");
        m5.addColumn("Password");
        m5.addColumn("Nama");
        m5.addColumn("Alamat");
        m5.addColumn("No Telp");
        try{
            i = 1;
            stm = con.createStatement();
            sql = "select id_karyawan, username, password, nama_karyawan, alamat_karyawan, notelp_karyawan from karyawan where jabatan_karyawan = 'Gudang';";
            res = stm.executeQuery(sql);
            while(res.next()){
                m5.addRow(new Object[]{i++, res.getString("id_karyawan"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("nama_karyawan"),
                    res.getString("alamat_karyawan"),
                    res.getString("notelp_karyawan")});
            }
            orgGudang.setModel(m5);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showDetAct(){
        DefaultTableModel m6 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        m6.addColumn("No");
        m6.addColumn("Id Aktivitas");
        m6.addColumn("Nama Aktivitas");
        m6.addColumn("Keterangan");
        m6.addColumn("Tanggal");
        try{
            i = 1;
            stm = con.createStatement();
            sql = "select a.id_activity, ja.nama_activity, a.keterangan, a.tanggal_activity from log_activity a inner join jenis_activity ja on a.id_jenisactivity = ja.id_jenisactivity"
                    + " where id_karyawan = '" + detgudang + "';";
            res = stm.executeQuery(sql);
            while(res.next()){
                m6.addRow(new Object[]{i++, res.getString("a.id_activity"),
                    res.getString("ja.nama_activity"),
                    res.getString("a.keterangan"),
                    res.getString("a.tanggal_activity")});
            }
            actGudang.setModel(m6);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
    
    private void showInfo(){
        tMasuk();
        tKeluar();
        Laba();
        jBarang();
        tStok();
        jTR();
        tTR();
        tBarangOut();
    }
    
    private void jBarang(){
        try{
            stm = con.createStatement();
            sql = "select count(*) as BRG from barang;";
            res = stm.executeQuery(sql);
            res.next();
            jumBRG.setText(res.getString("BRG"));
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void tStok(){
        try{
            stm = con.createStatement();
            sql = "SELECT SUM(stok_barang) as stok FROM `barang`";
            res = stm.executeQuery(sql);
            res.next();
            jumSTOK.setText(res.getString("stok") + " pcs");
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void jTR(){
        try{
            stm = con.createStatement();
            sql = "SELECT COUNT(total_transaksi) as jtr FROM `log_kasir`";
            res = stm.executeQuery(sql);
            res.next();
            outJTR.setText(res.getString("jtr"));
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void tTR(){
        try{
            stm = con.createStatement();
            sql = "SELECT SUM(total_transaksi) as tot FROM `log_kasir`";
            res = stm.executeQuery(sql);
            res.next();
            outTR.setText("Rp. " + res.getString("tot"));
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void tBarangOut(){
        try{
            stm = con.createStatement();
            sql = "SELECT SUM(total_barang) as tbr FROM `log_kasir`";
            res = stm.executeQuery(sql);
            res.next();
            outBRG.setText(res.getString("tbr") + " pcs");
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void tMasuk(){
        try{
            stm = con.createStatement();
            sql = "SELECT SUM(total_profit) as pro FROM `log_kasir`";
            res = stm.executeQuery(sql);
            res.next();
            masuk.setText("Rp. " + res.getString("pro"));
            in = Integer.parseInt(res.getString("pro"));
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void tKeluar(){
        try{
            stm = con.createStatement();
            sql = "SELECT SUM(total_tagihan) as plr FROM log_pengeluaran";
            res = stm.executeQuery(sql);
            res.next();
            keluar.setText("Rp. " + res.getString("plr"));
            out = Integer.parseInt(res.getString("plr"));
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void Laba(){
        int lb;
        lb = in - out;
        laba.setText("Rp. " + lb);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        changeUser = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        btnpemasukan = new javax.swing.JButton();
        btnpengeluaran = new javax.swing.JButton();
        bayarTagihan = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        masuk = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        keluar = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        laba = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        userKar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        passKar = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        namaKar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        pilJabatan = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        alamatKar = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        telpKar = new javax.swing.JTextField();
        add1 = new javax.swing.JButton();
        update1 = new javax.swing.JButton();
        del1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listkar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        hjBrg = new javax.swing.JTextField();
        hbBrg = new javax.swing.JTextField();
        stokBrg = new javax.swing.JTextField();
        jenisBrg = new javax.swing.JTextField();
        namaBrg = new javax.swing.JTextField();
        idBrg = new javax.swing.JTextField();
        idSup = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        del = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listbrg = new javax.swing.JTable();
        findNama = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jumBRG = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jumSTOK = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        orgGudang = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        actGudang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        hisTR = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        detTR = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        outTR = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        outBRG = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        outJTR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MANAGER");

        nama.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        changeUser.setText("GANTI USER");
        changeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUserActionPerformed(evt);
            }
        });

        exit.setText("EXIT");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnpemasukan.setText("LAPORAN PEMASUKAN");
        btnpemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpemasukanActionPerformed(evt);
            }
        });

        btnpengeluaran.setText("LAPORAN PENGELUARAN");
        btnpengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpengeluaranActionPerformed(evt);
            }
        });

        bayarTagihan.setText("BAYAR TAGIHAN");
        bayarTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarTagihanActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel24.setText("TOTAL PEMASUKAN :");
        jLabel24.setToolTipText("");

        masuk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel27.setText("TOTAL PENGELUARAN :");
        jLabel27.setToolTipText("");

        keluar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel28.setText("TOTAL LABA :");
        jLabel28.setToolTipText("");

        laba.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jSeparator1.setPreferredSize(new java.awt.Dimension(8, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnpengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addComponent(btnpemasukan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bayarTagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(laba, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(masuk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(keluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(laba, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnpemasukan)
                        .addGap(15, 15, 15)
                        .addComponent(btnpengeluaran)
                        .addGap(18, 18, 18)
                        .addComponent(bayarTagihan)))
                .addContainerGap(307, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("MANAGER", jPanel6);

        jLabel15.setText("Username :");

        jLabel16.setText("Password :");

        jLabel17.setText("Nama Karyawan :");

        jLabel18.setText("Jabatan :");

        pilJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kasir", "Gudang", "Manajer"}));
        pilJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilJabatanActionPerformed(evt);
            }
        });
        pilJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilJabatanActionPerformed(evt);
            }
        });

        jLabel19.setText("Alamat :");

        jLabel20.setText("No Telp :");

        add1.setText("ADD");
        add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add1ActionPerformed(evt);
            }
        });

        update1.setText("UPDATE");
        update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update1ActionPerformed(evt);
            }
        });

        del1.setText("DELETE");
        del1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                del1ActionPerformed(evt);
            }
        });

        listkar.setModel(new javax.swing.table.DefaultTableModel(
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
        listkar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listkarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listkar);

        jLabel2.setText("DAFTAR KARYAWAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passKar)
                            .addComponent(namaKar)
                            .addComponent(alamatKar)
                            .addComponent(telpKar)
                            .addComponent(userKar)
                            .addComponent(pilJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(add1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(update1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(del1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(passKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(namaKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(pilJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alamatKar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(telpKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(update1)
                            .addComponent(del1)
                            .addComponent(add1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("KARYAWAN", jPanel1);

        jLabel12.setText("ID Supplier :");

        jLabel8.setText("ID Barang :");

        jLabel9.setText("Nama Barang :");

        jLabel10.setText("Jenis Barang :");

        jLabel11.setText("Stok Barang :");

        jLabel13.setText("Harga Beli :");

        jLabel14.setText("Harga Jual :");

        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        del.setText("DELETE");
        del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delActionPerformed(evt);
            }
        });

        listbrg.setModel(new javax.swing.table.DefaultTableModel(
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
        listbrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listbrgMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listbrg);

        findNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findNamaKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel25.setText("JUMLAH BARANG :");
        jLabel25.setToolTipText("");

        jumBRG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel26.setText("TOTAL STOK :");
        jLabel26.setToolTipText("");

        jumSTOK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idBrg)
                            .addComponent(namaBrg)
                            .addComponent(jenisBrg)
                            .addComponent(stokBrg)
                            .addComponent(hbBrg)
                            .addComponent(hjBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idSup, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(del, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(findNama)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
                        .addGap(41, 41, 41))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jumBRG, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jumSTOK, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(findNama, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(idBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(namaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jenisBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(stokBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(hbBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(hjBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(update)
                            .addComponent(del)
                            .addComponent(add))
                        .addGap(70, 70, 70))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jumBRG, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jumSTOK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addContainerGap(46, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("BARANG", jPanel2);

        jLabel7.setText("DAFTAR ORANG GUDANG");

        orgGudang.setModel(new javax.swing.table.DefaultTableModel(
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
        orgGudang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orgGudangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(orgGudang);

        jLabel6.setText("DETAIL AKTIVITAS");

        actGudang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(actGudang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RIWAYAT AKTIVITAS", jPanel4);

        hisTR.setModel(new javax.swing.table.DefaultTableModel(
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
        hisTR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hisTRMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(hisTR);

        jLabel4.setText("RIWAYAT TRANSAKSI");

        jLabel5.setText("DETAIL TRANSAKSI");

        detTR.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(detTR);

        jLabel21.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel21.setText("TOTAL TRANSAKSI :");
        jLabel21.setToolTipText("");

        outTR.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel22.setText("TOTAL BARANG KELUAR :");
        jLabel22.setToolTipText("");

        outBRG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        jLabel23.setText("JUMLAH TRANSAKSI :");
        jLabel23.setToolTipText("");

        outJTR.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(outJTR, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(outTR, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outBRG, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(outJTR, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(outTR, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(outBRG, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RIWAYAT TRANSAKSI", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exit)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(changeUser)
                        .addComponent(exit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUserActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new loginKasir(indeks).setVisible(true);
    }//GEN-LAST:event_changeUserActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void pilJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilJabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pilJabatanActionPerformed

    private void add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add1ActionPerformed
        // TODO add your handling code here:
        try{
            pass = md5.generatedPassword(passKar.getText());
            sql = "insert into karyawan value (null,'" + userKar.getText() + "','" + pass + "','"
            + namaKar.getText() + "','" + pilJabatan.getSelectedItem() + "','"
            + alamatKar.getText() + "','" + telpKar.getText() +"');";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Data Karyawan Berhasil Ditambahkan!");
            clearForm2();
            showKaryawan();
            showInfo();
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }//GEN-LAST:event_add1ActionPerformed

    private void update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update1ActionPerformed
        // TODO add your handling code here:
        try{
            pass = md5.generatedPassword(passKar.getText());
            sql = "update karyawan set username = '" + userKar.getText() + "', password = '"
            + pass + "', nama_karyawan = '"
            + namaKar.getText() + "', jabatan_karyawan = '" + pilJabatan.getSelectedItem() + "', alamat_karyawan = '"
            + alamatKar.getText() + "', notelp_karyawan = '" + telpKar.getText() + "' where id_karyawan = '" + cek_id + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Update Data Karyawan Sukses!");
            clearForm2();
            showKaryawan();
            showInfo();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_update1ActionPerformed

    private void del1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_del1ActionPerformed
        // TODO add your handling code here:
        try{
            sql = "update karyawan set jabatan_karyawan = 'NONAKTIF' where id_karyawan = '" + cek_id + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Akun Karyawan Berhasil Dinonaktifkan!");
            clearForm2();
            showKaryawan();
            showInfo();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_del1ActionPerformed

    private void listkarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listkarMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int row = listkar.getSelectedRow();
            userKar.setText(listkar.getValueAt(row, 1).toString());
            passKar.setText(listkar.getValueAt(row, 2).toString());
            namaKar.setText(listkar.getValueAt(row, 3).toString());
            pilJabatan.setSelectedItem(listkar.getValueAt(row, 4).toString());
            alamatKar.setText(listkar.getValueAt(row, 5).toString());
            telpKar.setText(listkar.getValueAt(row, 6).toString());
            cek_id = Integer.parseInt(listkar.getValueAt(row, 0).toString());
        }
    }//GEN-LAST:event_listkarMouseClicked

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
        try{
            cek_id2 = Integer.parseInt(idBrg.getText());
            stok2 = Integer.parseInt(stokBrg.getText());
            total_stok = stok + stok2;
            if(cek_id2 == cek_id){
                sql = "update barang set stok_barang = '" + total_stok + "' where id_barang = '" + cek_id2 + "';";
                ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(null, namaBrg.getText() + " berhasil ditambahkan sebanyak " + stok2 + " pcs menjadi " + total_stok + " pcs!");
            }
            else{
                sql = "insert into barang value ('" + cek_id2 + "','" + Integer.parseInt(idSup.getText()) + "','"
                + namaBrg.getText() + "','" + jenisBrg.getText() + "','" + stok2 + "','" + Integer.parseInt(hjBrg.getText()) + "','"
                + Integer.parseInt(hbBrg.getText()) + "');";
                ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(null, namaBrg.getText() + " berhasil ditambahkan sebanyak " + stok2 + " pcs!");
            }
            clearForm();
            showBarang();
            showInfo();
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }//GEN-LAST:event_addActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:
        try{
            sql = "update barang set id_barang = '" + Integer.parseInt(idBrg.getText()) + "', id_supplier = '"
            + Integer.parseInt(idSup.getText()) + "', nama_barang = '"
            + namaBrg.getText() + "', jenis_barang = '" + jenisBrg.getText() + "', stok_barang = '"
            + Integer.parseInt(stokBrg.getText()) + "', harga_jual = '" + Integer.parseInt(hjBrg.getText()) + "', harga_beli = '"
            + Integer.parseInt(hbBrg.getText()) + "' where id_barang = '" + cek_id + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Update Data Barang Sukses!");
            clearForm();
            showBarang();
            showInfo();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_updateActionPerformed

    private void delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delActionPerformed
        // TODO add your handling code here:
        try{
            cek_id2 = Integer.parseInt(idBrg.getText());
            stok2 = Integer.parseInt(stokBrg.getText());
            total_stok = stok - stok2;
            if(total_stok < 0){
                JOptionPane.showMessageDialog(null, "Stok yang dihapus kurang dari jumlah minimal!");
            }
            else{
                sql = "update barang set stok_barang = '" + total_stok + "' where id_barang = '" + cek_id2 + "';";
                ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(null, namaBrg.getText() + " berhasil dihapus sebanyak " + stok2 + " pcs menjadi " + total_stok + " pcs!");
                clearForm();
                showBarang();
                showInfo();
            }
        } catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_delActionPerformed

    private void listbrgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listbrgMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int row = listbrg.getSelectedRow();
            idSup.setText(listbrg.getValueAt(row, 1).toString());
            idBrg.setText(listbrg.getValueAt(row, 0).toString());
            namaBrg.setText(listbrg.getValueAt(row, 2).toString());
            jenisBrg.setText(listbrg.getValueAt(row, 3).toString());
            stokBrg.setText(listbrg.getValueAt(row, 4).toString());
            hbBrg.setText(listbrg.getValueAt(row, 5).toString());
            hjBrg.setText(listbrg.getValueAt(row, 6).toString());
            cek_id = Integer.parseInt(idBrg.getText());
            stok = Integer.parseInt(stokBrg.getText());
        }
    }//GEN-LAST:event_listbrgMouseClicked

    private void findNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findNamaKeyReleased
        // TODO add your handling code here:
        String cari;
        cari = findNama.getText();
        try{
            m.setRowCount(0);
            stm = con.createStatement();
            sql = "select * from barang where nama_barang like '%" + cari + "%';";
            res = stm.executeQuery(sql);
            while(res.next()){
                m.addRow(new Object[]{res.getString("id_barang"),
                    res.getString("id_supplier"),
                    res.getString("nama_barang"),
                    res.getString("jenis_barang"),
                    res.getString("stok_barang"),
                    res.getString("harga_beli"),
                    res.getString("harga_jual")});
        }
        listbrg.setModel(m);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }//GEN-LAST:event_findNamaKeyReleased

    private void hisTRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hisTRMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int row = hisTR.getSelectedRow();
            detkasir = Integer.parseInt(hisTR.getValueAt(row, 0).toString());
            showDetTR();
        }
    }//GEN-LAST:event_hisTRMouseClicked

    private void orgGudangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orgGudangMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int row = orgGudang.getSelectedRow();
            detgudang = Integer.parseInt(orgGudang.getValueAt(row, 1).toString());
            showDetAct();
        }
    }//GEN-LAST:event_orgGudangMouseClicked

    private void btnpengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpengeluaranActionPerformed
        // TODO add your handling code here:
        showInfo();
        new pengeluaran().setVisible(true);
    }//GEN-LAST:event_btnpengeluaranActionPerformed

    private void btnpemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpemasukanActionPerformed
        // TODO add your handling code here:
        showInfo();
        new pemasukan().setVisible(true);
    }//GEN-LAST:event_btnpemasukanActionPerformed

    private void bayarTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarTagihanActionPerformed
        // TODO add your handling code here:
        showInfo();
        new form_bayartagihan(karyawan).setVisible(true);
    }//GEN-LAST:event_bayarTagihanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable actGudang;
    private javax.swing.JButton add;
    private javax.swing.JButton add1;
    private javax.swing.JTextField alamatKar;
    private javax.swing.JButton bayarTagihan;
    private javax.swing.JButton btnpemasukan;
    private javax.swing.JButton btnpengeluaran;
    private javax.swing.JButton changeUser;
    private javax.swing.JButton del;
    private javax.swing.JButton del1;
    private javax.swing.JTable detTR;
    private javax.swing.JButton exit;
    private javax.swing.JTextField findNama;
    private javax.swing.JTextField hbBrg;
    private javax.swing.JTable hisTR;
    private javax.swing.JTextField hjBrg;
    private javax.swing.JTextField idBrg;
    private javax.swing.JTextField idSup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jenisBrg;
    private javax.swing.JLabel jumBRG;
    private javax.swing.JLabel jumSTOK;
    private javax.swing.JLabel keluar;
    private javax.swing.JLabel laba;
    private javax.swing.JTable listbrg;
    private javax.swing.JTable listkar;
    private javax.swing.JLabel masuk;
    private javax.swing.JLabel nama;
    private javax.swing.JTextField namaBrg;
    private javax.swing.JTextField namaKar;
    private javax.swing.JTable orgGudang;
    private javax.swing.JLabel outBRG;
    private javax.swing.JLabel outJTR;
    private javax.swing.JLabel outTR;
    private javax.swing.JTextField passKar;
    private javax.swing.JComboBox<String> pilJabatan;
    private javax.swing.JTextField stokBrg;
    private javax.swing.JTextField telpKar;
    private javax.swing.JButton update;
    private javax.swing.JButton update1;
    private javax.swing.JTextField userKar;
    // End of variables declaration//GEN-END:variables
}
