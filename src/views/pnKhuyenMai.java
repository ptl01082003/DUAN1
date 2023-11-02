/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainModels.GiamGia;
import service.IGiamGiaService;
import serviceImpl.GiamGiaServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utilities.Support;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Window;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class pnKhuyenMai extends javax.swing.JPanel {

     private DefaultTableModel dtm;
    private IGiamGiaService service;
    private List<GiamGia> lists;
    private List<String> listMa;
    public pnKhuyenMai() {
        initComponents();
        dtm = new DefaultTableModel();
        lists = new ArrayList<>();
        service = new GiamGiaServiceImpl();

        lists = service.getAll();
        listMa = service.getListMa();

        dtm = (DefaultTableModel) tblbang.getModel();
        showDataTable(lists);
        rdonam.setSelected(true);
        rdoCon.setSelected(true);
        
        tblbang.getColumnModel().getColumn(0).setMinWidth(0);
    tblbang.getColumnModel().getColumn(0).setMaxWidth(0);
    tblbang.getColumnModel().getColumn(0).setWidth(0);
    }

    public void showDataTable(List<GiamGia> lists) {
        dtm.setRowCount(0);
        for (GiamGia dv : lists) {
            dtm.addRow(dv.toDataRow());
        }
    }

    public boolean checkData() {
        List<LocalDate> listNgayKT = service.getListNgayKetThuc();
        List<LocalDate> listNgayBD = service.getListNgayBD();
        if (txtMucGiam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mức giảm không được để trống!");
            return false;
        } else if (txtngayKT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày không được để trống!");
            return false;
        } else if (!checkNumber(txtMucGiam.getText().trim().replace(".", ""))) {
            return false;
        } else {
            LocalDate ngayBD;
            LocalDate ngayKT;

            try {
                ngayBD = LocalDate.parse(txtngayBD.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                ngayKT = LocalDate.parse(txtngayKT.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày không đúng định dạng dd-MM-yyyy");
                return false;
            }

            LocalDate ngayHienTai = LocalDate.now();

            if (ngayBD.isBefore(ngayHienTai)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được là ngày trước ngày hiện tại");
                return false;
            }

            if (ngayBD.isAfter(ngayKT)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu");
                return false;
            } else {
                // Kiểm tra một mã giảm đã được thêm vào cơ sở dữ liệu và ngày kết thúc của nó
                if (!service.getListNgayKetThuc().isEmpty()) {
                    for (LocalDate ngayKetThuc : listNgayKT) {
                        if (ngayBD.isEqual(ngayKetThuc) || ngayKT.isEqual(ngayKetThuc)) {
                            JOptionPane.showMessageDialog(this, "Không thể thêm mã giảm, đã có mã giảm hoạt động trong khoảng thời gian này rồi!!");
                            return false;
                        } else if (ngayBD.isAfter(ngayKetThuc) && ngayKT.isBefore(ngayKetThuc)) {
                            JOptionPane.showMessageDialog(this, "Không thể thêm mã giảm, khoảng thời gian trùng với mã giảm đã có trong cơ sở dữ liệu!!");
                            return false;
                        }
                    }
                }
                if (!service.getListNgayKetThuc().isEmpty()) {
                    for (LocalDate ngayBDau : listNgayBD) {
                        if (ngayBD.isEqual(ngayBDau) || ngayKT.isEqual(ngayBDau)) {
                            JOptionPane.showMessageDialog(this, "Không thể thêm mã giảm, đã có mã giảm hoạt động trong khoảng thời gian này rồi!!");
                            return false;
                        } else if (ngayBD.isAfter(ngayBD) && ngayKT.isBefore(ngayBD)) {
                            JOptionPane.showMessageDialog(this, "Không thể thêm mã giảm, khoảng thời gian trùng với mã giảm đã có trong cơ sở dữ liệu!!");
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean checkNumber(String so) {
        String kyTu = "\\d+";
        if (so.matches(kyTu)) {
            int value = Integer.parseInt(so);
            if (value <= 0) {
                JOptionPane.showMessageDialog(this, "Mức giảm không hợp lệ!\n Phải nhập vào số");
                return false;
            } else {
                if (rdonam.isSelected() && value < 500) {
                    JOptionPane.showMessageDialog(this, "Tiền phải lớn hơn 500 VND");
                    return false;
                }
                if (rdonu.isSelected() && value >= 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nhỏ hơn 100%");
                    return false;
                }
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mức giảm không hợp lệ!\n Phải nhập vào số");
            return false;
        }
    }

    public boolean checkDataUpdate() {
        List<LocalDate> listNgayKT = service.getListNgayKetThuc();
        List<LocalDate> listNgayBD = service.getListNgayBD();
        if (txtMucGiam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mức giảm không được để trống!");
            return false;
        } else if (txtngayKT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày không được để trống!");
            return false;
        } else if (!checkNumber(txtMucGiam.getText().trim().replace(".", ""))) {
            return false;
        } else {
            LocalDate ngayBD;
            LocalDate ngayKT;

            try {
                ngayBD = LocalDate.parse(txtngayBD.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                ngayKT = LocalDate.parse(txtngayKT.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày không đúng định dạng dd-MM-yyyy");
                return false;
            }

            LocalDate ngayHienTai = LocalDate.now();

            if (ngayBD.isBefore(ngayHienTai)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được là ngày trước ngày hiện tại");
                return false;
            }

            if (ngayBD.isAfter(ngayKT)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu");
                return false;
            }
        }
        return true;
    }

    public void detail(int index) {
        lists = service.getAll();
        GiamGia dv = lists.get(index);
        txtid.setText(dv.getId());
        txtma.setText(String.valueOf(dv.getMaGiamGia()));
        txtMucGiam.setText(String.valueOf(dv.getMucGiam()));
        txtngayBD.setText(Support.toString(dv.getNgayBatDa()));
        txtngayKT.setText(Support.toString(dv.getNgayKetThuc()));
        String trangThaiString = dv.isTrangThai() ?"Hoạt động" : "không hoạt động";
        if (trangThaiString.contains("Hoạt động")) {
            rdoCon.setSelected(true);
        }
        if(trangThaiString.contains("không hoạt động")) {
            rdoHet.setSelected(true);
        }
        if (dv.isHinhThucGiam() == true) {
            rdonam.setSelected(true);
        } else {
            rdonu.setSelected(true);
        }
        txtMT.setText(dv.getMoTa());
    }

    private GiamGia getDataFromView() {
        UUID id = UUID.randomUUID();
        boolean hinhThuc = false;
        if (rdonam.isSelected()) {
            hinhThuc = true;
        }
        String muc = txtMucGiam.getText().trim();
        Date ngBD = Support.toDate(txtngayBD.getText());
        Date ngKT = Support.toDate(txtngayKT.getText());
        String moTa = txtMT.getText().trim();
        return new GiamGia(String.valueOf(id), hinhThuc, Integer.valueOf(muc), ngBD, ngKT, moTa);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel12 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtma = new javax.swing.JTextField();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtMT = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtMucGiam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rdoCon = new javax.swing.JRadioButton();
        rdoHet = new javax.swing.JRadioButton();
        txtngayBD = new javax.swing.JTextField();
        txtngayKT = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblbang = new javax.swing.JTable();
        txttim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(1271, 300));
        jPanel12.setLayout(new java.awt.CardLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập thông tin giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel23.setPreferredSize(new java.awt.Dimension(1271, 380));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("Mã Giảm Giá");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("Hình Thức giảm");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Mức giảm");

        txtma.setEnabled(false);
        txtma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdonam);
        rdonam.setText("Tiền");
        rdonam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdonu);
        rdonu.setText("% Giá sản phẩm");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Mô tả");

        txtMT.setColumns(20);
        txtMT.setRows(5);
        jScrollPane9.setViewportView(txtMT);

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

        btnreset.setText("Reload");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
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
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnreset)
                .addGap(9, 9, 9))
        );

        txtid.setEnabled(false);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel57.setText("ID ");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Ngày Bắt đầu");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Ngày kết thúc");

        jLabel2.setText("Trạng thái");

        buttonGroup2.add(rdoCon);
        rdoCon.setText("Hoạt Động");
        rdoCon.setEnabled(false);

        buttonGroup2.add(rdoHet);
        rdoHet.setText("Không hoạt động");
        rdoHet.setEnabled(false);

        txtngayBD.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtngayBDAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtngayBD.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                txtngayBDComponentShown(evt);
            }
        });
        txtngayBD.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtngayBDCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtngayBDInputMethodTextChanged(evt);
            }
        });
        txtngayBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngayBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39)
                            .addComponent(jLabel2))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(rdoCon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoHet)
                                .addGap(28, 28, 28))
                            .addComponent(txtma)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(rdonam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdonu))
                            .addComponent(txtMucGiam)
                            .addComponent(txtid))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtngayKT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(txtngayBD, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(206, 206, 206)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
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
                                    .addComponent(jLabel58)
                                    .addComponent(txtngayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel37)
                                    .addComponent(txtma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel59)
                                    .addComponent(txtngayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdonam)
                                    .addComponent(rdonu)
                                    .addComponent(jLabel41))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39)
                                    .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(rdoCon)
                                    .addComponent(rdoHet)))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );

        jPanel12.add(jPanel23, "card2");

        add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

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
                "ID", "Mã giảm giá", "Mức giảm giá", "Loại giảm giá", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
        txttim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimActionPerformed(evt);
            }
        });
        txttim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm theo mã giảm giá");

        jButton1.setText("Tìm kiếm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel1)
                .addGap(60, 60, 60)
                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
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
        if (checkData()) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm vào không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                GiamGia dv = getDataFromView();
                service.add(dv);
                lists = service.getAll();
                showDataTable(lists);
                JOptionPane.showMessageDialog(this, "Thêm thành công thành công!");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        lists = service.getAll();
        int index = tblbang.getSelectedRow();
        if (index >= 0) {
            String id = lists.get(index).getId();
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                service.delete(id);
                lists = service.getAll();
                showDataTable(lists);
                JOptionPane.showMessageDialog(this, "Xoá thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xoá!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int index = tblbang.getSelectedRow();
        if (index >= 0) {
            lists = service.getAll();
            String id = lists.get(index).getId();
            String i = lists.get(index).getId();
            GiamGia dv = getDataFromView();
            if (i.equalsIgnoreCase(dv.getId())) {
                if (checkDataUpdate()) {
                    int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        service.update(dv, id);
                        lists = service.getAll();
                        showDataTable(lists);
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    }
                }
            } else {
                if (checkDataUpdate()) {
                    int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        service.update(dv, id);
                        lists = service.getAll();
                        showDataTable(lists);
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String now = dateFormat.format(new Date());
        txtMT.setText("");
        txtMucGiam.setText("");
        txttim.setText("");
        txtma.setText("");
        txtid.setText("");
        txtngayKT.setText("");
        txtngayBD.setText("");
        lists = service.getAll();
        rdonam.setSelected(true);
        rdoCon.setSelected(true);

        showDataTable(lists);

    }//GEN-LAST:event_btnresetActionPerformed

    private void txtngayBDAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtngayBDAncestorAdded

    }//GEN-LAST:event_txtngayBDAncestorAdded

    private void txtngayBDComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_txtngayBDComponentShown

    }//GEN-LAST:event_txtngayBDComponentShown

    private void txtngayBDCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtngayBDCaretPositionChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_txtngayBDCaretPositionChanged

    private void txtngayBDInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtngayBDInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngayBDInputMethodTextChanged

    private void txtngayBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngayBDActionPerformed

    }//GEN-LAST:event_txtngayBDActionPerformed

    private void tblbangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangMouseClicked
        int index = tblbang.getSelectedRow();
        detail(index);
    }//GEN-LAST:event_tblbangMouseClicked

    private void txttimCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttimCaretUpdate

    }//GEN-LAST:event_txttimCaretUpdate

    private void txttimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttimActionPerformed

    private void txttimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txttimKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String input = txttim.getText();
        int ma = 0;
        try {
            ma = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
            return;
        }
        lists = service.getListByMa(ma);
        if (lists == null || lists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
            return;
        }
        showDataTable(lists);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnreset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JRadioButton rdoCon;
    private javax.swing.JRadioButton rdoHet;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTable tblbang;
    private javax.swing.JTextArea txtMT;
    private javax.swing.JTextField txtMucGiam;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtma;
    private javax.swing.JTextField txtngayBD;
    private javax.swing.JTextField txtngayKT;
    private javax.swing.JTextField txttim;
    // End of variables declaration//GEN-END:variables
}
