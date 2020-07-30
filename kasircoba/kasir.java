/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasircoba;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YUMA
 */
public class kasir extends javax.swing.JFrame {
    Connection con = config_db.config_db();
    private ResultSet res;
    private Statement stm;
    private PreparedStatement ps;
    String sql, barang;
    int i, karyawan, tr, total, subtot, price, jumlah, brgtotal, s, total_stok, hb, hj, profit, total_p;
    int cek_id, cek_id2, subtot2, profit2, jumlah2;
    int indeks = 1;

    /**
     * Creates new form kasir
     */
    public kasir(int kr) {
        initComponents();
        karyawan = kr;
        try{
            stm = con.createStatement();
            sql = "select * from karyawan where id_karyawan = '" + karyawan +"';";
            res = stm.executeQuery(sql);
            res.next();
            nama.setText("(" + res.getString("nama_karyawan") + ")");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        }
    }
   
    private void showLog(){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.addColumn("Id");
        model.addColumn("Total Transaksi");
        model.addColumn("Total Barang");
        model.addColumn("Tanggal");
        try{
            stm = con.createStatement();
            sql = "select id_kasir, total_transaksi, total_barang, tanggal_transaksi"
                    + " from log_kasir where id_karyawan = '" + karyawan + "';";
            res = stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getString("id_kasir"),
                    res.getString("total_transaksi"),
                    res.getString("total_barang"),
                    res.getString("tanggal_transaksi")});
            }
            log.setModel(model);
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        } 
    }
    
    private void showKeranjang(){
        DefaultTableModel model2 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model2.addColumn("No");
        model2.addColumn("Id Barang");
        model2.addColumn("Nama Barang");
        model2.addColumn("Jenis Barang");
        model2.addColumn("Harga");
        model2.addColumn("Jumlah");
        model2.addColumn("Sub Total");
        try{
            i=1;
            stm = con.createStatement();
            sql = "select b.nama_barang, b.id_barang, b.jenis_barang, b.harga_jual, dk.qty_barang, dk.subtotal "
                    + "from detail_log_kasir dk inner join barang b "
                    + "on dk.id_barang = b.id_barang where dk.id_kasir = '" + tr + "';";
            res = stm.executeQuery(sql);
            while(res.next()){
                model2.addRow(new Object[]{i++, res.getString("b.id_barang"),
                    res.getString("b.nama_barang"),
                    res.getString("b.jenis_barang"),
                    res.getString("b.harga_jual"),
                    res.getString("dk.qty_barang"),
                    res.getString("dk.subtotal")});
            }
            keranjang.setModel(model2);
            byr.setText("Rp. " + Integer.toString(total));
            itm.setText(Integer.toString(brgtotal) + " pcs");
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat Permasalahan di "+ e);
        } 
    }
    
    private void insertKeranjang(){
        try{
            sql = "insert into detail_log_kasir value (null, '"
                    + tr + "','" + cek_id + "','"
                    + jumlah + "','" + subtot + "','" + profit + "');";
            ps = con.prepareStatement(sql);
            ps.execute();
        }catch (NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "Terdapat permasalahan di " + e);
        }
    }
    
    private void insertKeranjang2(){
        try{
            sql = "update detail_log_kasir set qty_barang = '" + jumlah2 + "', subtotal = '" + subtot2 + "', profit_product = '" + profit2 + "'"
                    + " where id_kasir = '" + tr + "' and id_barang = '" + cek_id2 + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }
    
    private void scanProfit(){
        try{
            total_p = 0;
            sql = "select profit_product from detail_log_kasir where id_kasir = '" + tr + "';";
            res = stm.executeQuery(sql);
            while(res.next()){
                profit = Integer.parseInt(res.getString("profit_product"));
                total_p = total_p + profit; 
            }
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }
    
    private void scanLog(){
        try{
            sql = "select id_kasir from log_kasir order by id_kasir asc;";
            res = stm.executeQuery(sql);
            while(res.next()){
                tr = Integer.parseInt(res.getString("id_kasir"));
            }
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }
   
    private void updateStok(int a){
        try{
            sql = "update barang set stok_barang = '" + total_stok + "' where id_barang = '" + a + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }

    private void clearForm(){
        idbrg.setText(null);
        brg.setText(null);
        hrg.setText(null);
        jenis.setText(null);
        stok.setText(null);
        qty.setText(null);
    }
    
    private void clearForm2(){
        itm.setText(null);
        byr.setText(null);
        jumlahBayar.setText(null);
        kembalian.setText(null);
    }
    
    public void setIDBRG(String z){
        idbrg.setText(z);
    }
    
    public void infoBarang(){
        try{
            sql = "select nama_barang, jenis_barang, stok_barang, harga_jual, harga_beli"
                    + " from barang where id_barang = '" + Integer.parseInt(idbrg.getText()) + "';";
            res = stm.executeQuery(sql);
            res.next();
            brg.setText(res.getString("nama_barang"));
            jenis.setText(res.getString("jenis_barang"));
            hrg.setText(res.getString("harga_jual"));
            stok.setText(res.getString("stok_barang"));
            hb = Integer.parseInt(res.getString("harga_beli"));
            hj = Integer.parseInt(hrg.getText());
            profit = hj - hb;
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
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
        brg = new javax.swing.JLabel();
        idbrg = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        keranjang = new javax.swing.JTable();
        add = new javax.swing.JButton();
        changeUser = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jumlahBayar = new javax.swing.JTextField();
        bayar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        hrg = new javax.swing.JLabel();
        byr = new javax.swing.JLabel();
        kembalian = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        stok = new javax.swing.JLabel();
        newTR = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        itm = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        log = new javax.swing.JTable();
        listProduk = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jenis = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("KASIR");

        nama.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        idbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idbrgActionPerformed(evt);
            }
        });

        jLabel2.setText("ID     :");

        cari.setText("CARI");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Barang :");

        jLabel4.setText("Jenis Barang :");

        jLabel5.setText("Jumlah :");

        qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyActionPerformed(evt);
            }
        });

        keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keranjangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(keranjang);

        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

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

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("TOTAL BAYAR : ");

        jLabel8.setText("Jumlah Bayar : ");

        jumlahBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahBayarActionPerformed(evt);
            }
        });
        jumlahBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahBayarKeyReleased(evt);
            }
        });

        bayar.setText("BAYAR");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        jLabel9.setText("Kembalian : ");

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jLabel7.setText("Stok :");

        newTR.setText("TRANSAKSI BARU");
        newTR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTRActionPerformed(evt);
            }
        });

        jLabel10.setText("TOTAL ITEM : ");

        log.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(log);

        listProduk.setText("LIST BARANG");
        listProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listProdukActionPerformed(evt);
            }
        });

        jLabel11.setText("Harga Jual :");

        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setText("LOG TRANSAKSI");

        jLabel13.setText("LIST PEMBELIAN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(477, 477, 477))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(listProduk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newTR)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(changeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(243, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(305, 305, 305))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel7))
                                                .addGap(10, 10, 10))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(11, 11, 11)
                                                .addComponent(add)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(delete))
                                            .addComponent(brg, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(idbrg, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(cari))
                                            .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(hrg, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(64, 64, 64))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(itm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(byr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jumlahBayar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(bayar))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(changeUser)
                        .addComponent(exit)))
                .addGap(9, 9, 9)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(newTR))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(listProduk)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idbrg, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(brg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(hrg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(add)
                            .addComponent(delete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(itm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(byr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlahBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bayar))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jumlahBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahBayarActionPerformed

    private void qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
        infoBarang();
    }//GEN-LAST:event_cariActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void changeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUserActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new loginKasir(indeks).setVisible(true);
    }//GEN-LAST:event_changeUserActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed

        s = Integer.parseInt(stok.getText());
        if(s == 0){
            JOptionPane.showMessageDialog(null,"Stok habis, mohon hubungi pihak terkait untuk melakukan restock!");
        }
        else{
            jumlah = Integer.parseInt(qty.getText());
            price = Integer.parseInt(hrg.getText());
            subtot = price*jumlah;
            profit = profit*jumlah;
            cek_id = Integer.parseInt(idbrg.getText());
            if(cek_id2 == cek_id){
                subtot2 = subtot2 + subtot;
                profit2 = profit2 + profit;
                jumlah2 = jumlah2 + jumlah;
                insertKeranjang2();
            }
            else{
                insertKeranjang();
            }
            total = total + subtot;
            brgtotal = brgtotal + jumlah;
            total_stok = s - jumlah;
            updateStok(cek_id);
            showKeranjang();
            clearForm();
        }
    }//GEN-LAST:event_addActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
        try{
            scanProfit();
            sql = "update log_kasir set total_transaksi = '" + total + "', total_barang = '"
                    + brgtotal + "', total_profit = '" + total_p + "' where id_kasir = '" + tr + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null,"Pembayaran Sukses!");
            showLog();
            clearForm2();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_bayarActionPerformed

    private void keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangMouseClicked
        if(evt.getClickCount()==2){
            int row = keranjang.getSelectedRow();
            idbrg.setText(keranjang.getValueAt(row, 1).toString());
            qty.setText(keranjang.getValueAt(row, 5).toString());
            infoBarang();
            cek_id2 = Integer.parseInt(idbrg.getText());
            jumlah2 = Integer.parseInt(qty.getText());
            subtot2 = Integer.parseInt(keranjang.getValueAt(row, 6).toString());
        }
    }//GEN-LAST:event_keranjangMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        try{
            s = Integer.parseInt(stok.getText());
            jumlah = Integer.parseInt(qty.getText());
            price = Integer.parseInt(hrg.getText());
            subtot = price*jumlah;
            total = total - subtot;
            brgtotal = brgtotal - jumlah;
            total_stok = s + jumlah;
            sql = "delete from detail_log_kasir where id_kasir = '"
                    + tr + "' and id_barang = '" + cek_id2 + "';";
            ps = con.prepareStatement(sql);
            ps.execute();
            updateStok(cek_id2);
            showKeranjang();
            clearForm();
            JOptionPane.showMessageDialog(null,"Item Berhasil Dihapus!");
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di " + e);
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void jumlahBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahBayarKeyReleased
        // TODO add your handling code here:
        int tot, change, pay;
        pay = Integer.parseInt(jumlahBayar.getText());
        tot = total;
        change = pay - tot;
        kembalian.setText("Rp. " + Integer.toString(change));
    }//GEN-LAST:event_jumlahBayarKeyReleased

    private void newTRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTRActionPerformed
        // TODO add your handling code here:
        try{
            total = 0;
            brgtotal = 0;
            sql = "insert into log_kasir value(null, '" + karyawan + "', 0, 0, 0, now());";
            ps = con.prepareStatement(sql);
            ps.execute();
            clearForm2();
            showKeranjang();
            showLog();
            scanLog();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null,"Terdapat Permasalahan di "+ e);
        }
    }//GEN-LAST:event_newTRActionPerformed

    private void logMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_logMouseClicked

    private void listProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listProdukActionPerformed
        // TODO add your handling code here:
        list_barang list = new list_barang(this);
        list.setVisible(true);
    }//GEN-LAST:event_listProdukActionPerformed

    private void idbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idbrgActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton bayar;
    private javax.swing.JLabel brg;
    private javax.swing.JLabel byr;
    private javax.swing.JButton cari;
    private javax.swing.JButton changeUser;
    private javax.swing.JButton delete;
    private javax.swing.JButton exit;
    private javax.swing.JLabel hrg;
    private javax.swing.JTextField idbrg;
    private javax.swing.JLabel itm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jenis;
    private javax.swing.JTextField jumlahBayar;
    private javax.swing.JLabel kembalian;
    private javax.swing.JTable keranjang;
    private javax.swing.JButton listProduk;
    private javax.swing.JTable log;
    private javax.swing.JLabel nama;
    private javax.swing.JButton newTR;
    private javax.swing.JTextField qty;
    private javax.swing.JLabel stok;
    // End of variables declaration//GEN-END:variables
}
