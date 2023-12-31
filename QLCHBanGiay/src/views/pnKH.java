/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainModels.KhachHang;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.serviceKH;
import serviceImpl.implKH;
import viewModels.KhacHangViewModel;

/**
 *
 * @author ACER
 */
public class pnKH extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
private  implKH service =  new serviceKH();
ArrayList<KhacHangViewModel> list = new ArrayList<>();
    /**
     * Creates new form pnKH
     */
    public pnKH() {
        initComponents();
        getDta(service.getAll());
        txtma.setEditable(false);
    }

     public void getDta(ArrayList<KhacHangViewModel> list){
           dtm = (DefaultTableModel) tblbang.getModel();
        dtm.setRowCount(0);
        for (KhacHangViewModel kh : list) {
            dtm.addRow(new Object[]{
               kh.getId(),
               kh.getMa(),
               kh.getHoTen(),
               kh.getNgaySinh(),
               kh.getGioiTinh(),
               kh.getSdt(),
               kh.getDiaChi(),
               kh.getEmail()
            });
            
        }       
    }
      private void clearTblKH() {
        this.txtid.setText("");
        this.txtma.setText("");
        this.txtten1.setText("");
        this.txtngaysinh.setText("");
        this.txtSDT.setText("");
        this.txtDC.setText("");
        this.txtemail.setText("");
        this.txttim.setText("");
        this.buttonGroup1.clearSelection();
        getDta(service.getAll());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        txtSDT = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtDC = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtngaysinh = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        txtten1 = new javax.swing.JTextField();
        btnreset = new javax.swing.JButton();
        btndoc = new javax.swing.JButton();
        excel = new javax.swing.JTextField();
        btnin = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblbang = new javax.swing.JTable();
        txttim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(1271, 300));
        jPanel12.setLayout(new java.awt.CardLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel23.setPreferredSize(new java.awt.Dimension(1271, 380));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("Mã khách hàng");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("Họ Tên khách hàng");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Giới tính");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("Số điện thoại");

        txtma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaActionPerformed(evt);
            }
        });

        rdonam.setSelected(true);
        rdonam.setText("Nam");
        rdonam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonamActionPerformed(evt);
            }
        });

        rdonu.setText("Nữ");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Địa chỉ");

        txtDC.setColumns(20);
        txtDC.setRows(5);
        jScrollPane9.setViewportView(txtDC);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel24.setForeground(new java.awt.Color(102, 255, 102));

        btnAdd.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Thêm thông tin");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 204, 204));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setText("Xóa thông tin");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdate.setText("Sửa thông tin");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnAdd)
                .addGap(26, 26, 26)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(44, 44, 44))
        );

        txtid.setEditable(false);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel57.setText("ID khách hàng");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Ngày Sinh");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Email");

        txtten1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtten1ActionPerformed(evt);
            }
        });

        btnreset.setText("Re");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btndoc.setText("Import");
        btndoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndocActionPerformed(evt);
            }
        });

        btnin.setText("Export");
        btnin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(txtten1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39))
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(rdonam)
                                .addGap(52, 52, 52)
                                .addComponent(rdonu))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel41)
                        .addComponent(jLabel58)
                        .addComponent(jLabel59)))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(145, 145, 145)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(excel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btndoc)
                .addGap(20, 20, 20)
                .addComponent(btnin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel57))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel58)
                                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtten1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel59)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39)
                                    .addComponent(rdonam)
                                    .addComponent(rdonu))
                                .addGap(23, 23, 23))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel41)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(excel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndoc)
                    .addComponent(btnin)
                    .addComponent(btnreset))
                .addGap(17, 17, 17))
        );

        jPanel12.add(jPanel23, "card2");

        add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tblbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã khách hàng", "Tên khách hàng", "Ngày Sinh", "Giới tính", "Số điện thoại", "Địa chỉ", "Email"
            }
        ));
        tblbang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbangMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblbang);

        txttim.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txttimCaretUpdate(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tìm tên khách hàng");

        btnTim.setBackground(new java.awt.Color(204, 204, 204));
        btnTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTim.setText("Tìm Kiếm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnTim)
                .addContainerGap(457, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnTim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel3.add(jPanel11, "card2");

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtmaActionPerformed

    private void rdonamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdonamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdonamActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (txtDC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống địa chỉ khách hàng");
            return;
        }
        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống số điện thoại khách hàng");
            return;
        }
        if (txtten1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống tên khách hàng");
            return;
        }
        if (txtngaysinh.getText()== null) {
            JOptionPane.showMessageDialog(this, "Không để trống ngày sinh khách hàng");
            return;
        }
        KhachHang kh = new KhachHang();
        kh.setHoTen(txtten1.getText());
        kh.setNgaySinh(txtngaysinh.getText());
        if (rdonam.isSelected()) {
            kh.setGioiTinh("Nam");
        } else {
            kh.setGioiTinh("Nữ");
        }
        if (txtSDT.getText().matches("^0\\d{9,10}")) {
            kh.setSdt(txtSDT.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhập chưa đúng");
            return;
        }
        kh.setDiaChi(txtDC.getText());
        kh.setEmail(txtemail.getText());

        int xacNhan = JOptionPane.showConfirmDialog(this,"ban co muon them k?");
        if(xacNhan == 0){

            service.them(kh);
            JOptionPane.showMessageDialog(this, "them thanh cong");
            getDta(service.getAll());

        }
        else{
            JOptionPane.showMessageDialog(this, "Them that bai");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String id = txtid.getText();
        int xacNhan = JOptionPane.showConfirmDialog(this,"ban co muon xoa k?");
        if(xacNhan == 0){

            service.delete(id);
            JOptionPane.showMessageDialog(this, "Xoa thanh cong");
            getDta(service.getAll());

        }
        else{
            JOptionPane.showMessageDialog(this, "xoa that bai");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtDC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống địa chỉ khách hàng");
            return;
        }
        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống số điện thoại khách hàng");
            return;
        }
        if (txtten1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trống tên khách hàng");
            return;
        }
        if (txtngaysinh.getText()== null) {
            JOptionPane.showMessageDialog(this, "Không để trống ngày sinh khách hàng");
            return;
        }
        String id = txtid.getText();
        KhachHang kh = new KhachHang();
        kh.setHoTen(txtten1.getText());

        kh.setNgaySinh(txtngaysinh.getText());
        if (rdonam.isSelected()) {
            kh.setGioiTinh("Nam");
        } else {
            kh.setGioiTinh("Nữ");
        }

        if (txtSDT.getText().matches("^0\\d{9,10}")) {
            kh.setSdt(txtSDT.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Số điện thoại nhập chưa đúng");
            return;
        }
        kh.setDiaChi(txtDC.getText());
        kh.setEmail(txtemail.getText());

        int xacNhan = JOptionPane.showConfirmDialog(this,"Ban co muon sua k?");
        if(xacNhan == 0){

            service.update(kh);
            JOptionPane.showMessageDialog(this, "Sua thanh cong");
            getDta(service.getAll());

        }
        else{
            JOptionPane.showMessageDialog(this, "Sua that bai");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtten1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtten1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtten1ActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        clearTblKH();

    }//GEN-LAST:event_btnresetActionPerformed

    private void tblbangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangMouseClicked
        int row = tblbang.getSelectedRow();
        txtid.setText(tblbang.getValueAt(row, 0).toString());
        txtma.setText(tblbang.getValueAt(row, 1).toString());
        txtten1.setText(tblbang.getValueAt(row, 2).toString());
        txtngaysinh.setText(tblbang.getValueAt(row, 3).toString());
        String sex = this.tblbang.getValueAt(row, 4).toString();
        txtSDT.setText(tblbang.getValueAt(row, 5).toString());
        txtDC.setText(tblbang.getValueAt(row, 6).toString());
        txtemail.setText(tblbang.getValueAt(row, 7).toString());
        if(sex.equalsIgnoreCase("Nam")){
            rdonam.setSelected(true);
        }else if(sex.equalsIgnoreCase("Nữ")){
            rdonu.setSelected(true);
        }
    }//GEN-LAST:event_tblbangMouseClicked

    private void txttimCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttimCaretUpdate

    }//GEN-LAST:event_txttimCaretUpdate

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        String hoten = txttim.getText();
        int xacNhan = JOptionPane.showConfirmDialog(this,"ban co muon tim k?");
        if(xacNhan == 0){

            ArrayList<KhacHangViewModel>  kq = service.sreach(hoten);
            JOptionPane.showMessageDialog(this, "tim thanh cong");
            getDta(kq);

        }
        else{
            JOptionPane.showMessageDialog(this, "tim that bai");
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void btndocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndocActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model= (DefaultTableModel) tblbang.getModel();
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = "D:";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Select Excel File");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            dtm.setRowCount(0);
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excel.setText(excelFile.toString());
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
                for (int row = 0; row < excelSheet.getLastRowNum(); row ++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    XSSFCell excelLineNum = excelRow.getCell(1);
                    XSSFCell excelItemName = excelRow.getCell(2);
                    XSSFCell excelDescription = excelRow.getCell(3);
                    XSSFCell excelServiceDuration = excelRow.getCell(4);
                    XSSFCell excelQuantity = excelRow.getCell(5);
                    XSSFCell excelQuantity1 = excelRow.getCell(6);
                    XSSFCell excelQuantity2 = excelRow.getCell(7);
                    XSSFCell excelQuantity3 = excelRow.getCell(8);
                    model.addRow(new Object[]{excelLineNum, excelItemName, excelDescription, excelServiceDuration,excelQuantity,excelQuantity1,excelQuantity2,excelQuantity3});
                }
                JOptionPane.showMessageDialog(null, "Imported Successfully !!.....");
            } catch (IOException iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    JOptionPane.showMessageDialog(null, iOException.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btndocActionPerformed

    private void btninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninActionPerformed
        // TODO add your handling code here:
        try
        {
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet=wordkbook.createSheet("danhsach");
            XSSFRow row =null;
            Cell cell=null;
            //        row=sheet.createRow(0);
            //        cell=row.createCell(0,CellType.STRING);
            //        cell.setCellValue("DANH SACH KHACH HANG");
            //
            //        row=sheet.createRow(1);
            //        cell=row.createCell(0,CellType.STRING);
            //        cell.setCellValue("STT");
            //
            //        cell=row.createCell(1,CellType.STRING);
            //        cell.setCellValue("ID");
            //
            //        cell=row.createCell(2,CellType.STRING);
            //        cell.setCellValue("MA");
            //
            //        cell=row.createCell(3,CellType.STRING);
            //        cell.setCellValue("HOTEN");
            //
            //        cell=row.createCell(4,CellType.STRING);
            //        cell.setCellValue("NGAYSINH");
            //
            //        cell=row.createCell(5,CellType.STRING);
            //        cell.setCellValue("GIOITINH");
            //
            //        cell=row.createCell(6,CellType.STRING);
            //        cell.setCellValue("SDT");
            //
            //        cell=row.createCell(7,CellType.STRING);
            //        cell.setCellValue("DIACHI");
            //
            //        cell=row.createCell(8,CellType.STRING);
            //        cell.setCellValue("EMAIL");

            list = service.getAll();
            for(int i=0; i<list.size(); i++)
            {
                //Modelbook book =arr.get(i);
                row=sheet.createRow(i);

                cell=row.createCell(0,CellType.NUMERIC);
                cell.setCellValue(i+1);

                cell=row.createCell(1,CellType.STRING);
                cell.setCellValue(list.get(i).getId().toString());

                cell=row.createCell(2,CellType.STRING);
                cell.setCellValue(list.get(i).getMa());

                cell=row.createCell(3,CellType.STRING);
                cell.setCellValue(list.get(i).getHoTen());

                cell=row.createCell(4,CellType.STRING);
                cell.setCellValue(list.get(i).getNgaySinh());

                cell=row.createCell(5,CellType.STRING);
                cell.setCellValue(list.get(i).getGioiTinh());

                cell=row.createCell(6,CellType.STRING);
                cell.setCellValue(list.get(i).getSdt());

                cell=row.createCell(7,CellType.STRING);
                cell.setCellValue(list.get(i).getDiaChi());

                cell=row.createCell(8,CellType.STRING);
                cell.setCellValue(list.get(i).getEmail());
            }

            File f = new File("D://danhsachKH.xlsx");
            try
            {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
                fis.close();
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "in thanh cong D:\\danhsach");

        }

        catch(Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi mo file");
        }

    }//GEN-LAST:event_btninActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btndoc;
    private javax.swing.JButton btnin;
    private javax.swing.JButton btnreset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField excel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTable tblbang;
    private javax.swing.JTextArea txtDC;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtten1;
    private javax.swing.JTextField txttim;
    // End of variables declaration//GEN-END:variables
}
