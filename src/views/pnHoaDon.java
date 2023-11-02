/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domainModels.ChiTietGiay;
import domainModels.GiamGia;
import domainModels.HoaDon;
import domainModels.HoaDonChiTiet;
import domainModels.KhachHang;
import domainModels.NhanVien;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import repositories.BanHangRepository;
import repositories.CTGiayRepository;
import repositories.ChatLieuRepository;
import repositories.DanhMucSPRepository;
import repositories.HangRepository;
import repositories.LoaiGiayRepository;
import repositories.MauSacRepository;
import repositories.SizeRepository;
import service.BanHangService;
import service.CTGiayService;
import service.IHoaDonChiTietViewModelService;
import service.IHoaDonService;
import serviceImpl.HoaDonChiTietViewModelServiceImpl;
import serviceImpl.HoaDonServiceImpl;
import viewModels.HoaDonChiTietViewModel;

/**
 *
 * @author ACER
 */
public class pnHoaDon extends javax.swing.JPanel {

    private BanHangService bhService;
    private CTGiayRepository ctRepo;
    private CTGiayService repo;
    private ChatLieuRepository clRepo;
    private DanhMucSPRepository dmRepo;
    private HangRepository hangRepo;
    private LoaiGiayRepository lgRepo;
    private MauSacRepository msRepo;
    private SizeRepository sRepo;
    private DefaultTableModel dtm;
    private DefaultTableModel dtmCT;
    private IHoaDonService service;
    private IHoaDonChiTietViewModelService serviceCT = new HoaDonChiTietViewModelServiceImpl();
    private List<HoaDon> lists;
    private List<HoaDonChiTietViewModel> listsCT;

    public pnHoaDon() {
        initComponents();
        this.ctRepo = new CTGiayRepository();
        this.repo = new CTGiayService();
        this.dmRepo = new DanhMucSPRepository();
        this.clRepo = new ChatLieuRepository();
        this.hangRepo = new HangRepository();
        this.lgRepo = new LoaiGiayRepository();
        this.msRepo = new MauSacRepository();
        this.sRepo = new SizeRepository();
        dtm = new DefaultTableModel();
        dtmCT = new DefaultTableModel();
        service = new HoaDonServiceImpl();
        bhService = new BanHangService();

        lists = service.getAll();
        listsCT = new ArrayList<>();
        listsCT = serviceCT.getAll();
        dtmCT = (DefaultTableModel) tblhoaDonCT.getModel();
        dtm = (DefaultTableModel) tblHoaDon.getModel();
        tblHoaDon.getColumnModel().getColumn(0).setMinWidth(0);
        tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHoaDon.getColumnModel().getColumn(0).setWidth(0);

        tblhoaDonCT.getColumnModel().getColumn(0).setMinWidth(0);
        tblhoaDonCT.getColumnModel().getColumn(0).setMaxWidth(0);
        tblhoaDonCT.getColumnModel().getColumn(0).setWidth(0);
        showDataTable(lists);
    }

    public void showDataTable(List<HoaDon> lists) {
        dtm.setRowCount(0);
        for (HoaDon dv : lists) {
            dtm.addRow(dv.toDataRow());
        }
    }

    public void showDataTableCT(List<HoaDonChiTietViewModel> lists) {

        dtmCT.setRowCount(0);
        for (HoaDonChiTietViewModel dv : lists) {
            System.out.println(dv.getIdMauSac() + " - " + dv.getIdCL());
            Object[] rowData = {
                dv.getIdHD(),
                dv.getIdChiTietSanPham(),
                ctRepo.findByID(dv.getIdChiTietSanPham()).getTenCTGiay(),
                dv.getSoLuong(),
                hangRepo.findById(dv.getIdHang()).getTenHang(),
                msRepo.findById(dv.getIdMauSac()).getTenMS(),
                sRepo.findById(dv.getIdSize()).getSizeGiay(),
                clRepo.findById(dv.getIdCL()).getChatLieuThan()
            };
            dtmCT.addRow(rowData);
        }
    }

    public void searchByTT() {
        if (rdoTatCa.isSelected()) {
            lists = service.getAll();
            dtm = (DefaultTableModel) tblHoaDon.getModel();
            showDataTable(lists);
        } else if (rdoTien.isSelected()) {
            lists = service.getListByHTTT(1);
            dtm = (DefaultTableModel) tblHoaDon.getModel();
            showDataTable(lists);
        } else if (rdoCK.isSelected()) {
            lists = service.getListByHTTT(0);
            dtm = (DefaultTableModel) tblHoaDon.getModel();
            showDataTable(lists);
        }
    }

    private void timThangNamTrangThai(String thang, String nam, String trangthai) {
        TableRowSorter<TableModel> tr = new TableRowSorter<TableModel>(dtm);
        tblHoaDon.setRowSorter(tr);

        if (!thang.equals("Tất cả")) {
            tr.setRowFilter(RowFilter.regexFilter("\\d{4}-" + thang + "-\\d{2}"));
        }

        if (!nam.equals("Tất cả")) {
            if (tr.getRowFilter() != null) {
                RowFilter<TableModel, Object> rfThang = RowFilter.regexFilter("-" + thang + "-");
                RowFilter<TableModel, Object> rfNam = RowFilter.regexFilter("^" + nam + "-"); // Lọc theo tiền tố năm
                tr.setRowFilter(RowFilter.andFilter(Arrays.asList(rfThang, rfNam)));
            } else {
                RowFilter<TableModel, Object> rfNam = RowFilter.regexFilter("^" + nam + "-"); // Lọc theo tiền tố năm
                tr.setRowFilter(rfNam);
            }
        }

        if (!trangthai.equals("Tất cả")) {
            if (tr.getRowFilter() != null) {
                RowFilter<TableModel, Object> rfThang = RowFilter.regexFilter("-" + thang + "-");
                RowFilter<TableModel, Object> rfTrangThai = RowFilter.regexFilter(trangthai);
                tr.setRowFilter(RowFilter.andFilter(Arrays.asList(rfThang, rfTrangThai)));
            } else {
                tr.setRowFilter(RowFilter.regexFilter(trangthai));
            }
        }

    }

    private void inHoaDon(HoaDon hd, List<HoaDon> listHD, NhanVien nv, KhachHang kh, ChiTietGiay ctg, GiamGia gg) {

        Document document = new Document();
        List<HoaDonChiTiet> list = bhService.getAllHDCT(hd.getId());
        int size = list.size();

        try {

            PdfWriter.getInstance(document, new FileOutputStream("D:\\HoaDon\\hoadon_" + hd.getMaHD() + ".pdf"));

            document.open();
            BaseFont baseFont = BaseFont.createFont("C:\\Users\\ACER\\Downloads\\font-unicode\\font-unicode\\taimienphi.vn-font-unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 16, Font.NORMAL);
            Font font2 = new Font(baseFont, 16, Font.BOLD);
            // Tạo bảng với 2 cột
            PdfPTable table = new PdfPTable(5);
            // Thêm tiêu đề cho từng cột
            PdfPCell cell1 = new PdfPCell(new Paragraph("Stt", font2));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Tên SP", font2));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Số lượng", font2));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Giá", font2));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Thành tiền", font2));

            // Thêm các ô vào bảng
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);

            // Thêm dữ liệu vào từng ô
            for (int i = 0; i < size; i++) {
                int sl = list.get(i).getSoLuong();

                double gia = ctRepo.findByID(list.get(i).getIdCTGiay()).getGiaBan();
                table.addCell((i + 1) + "");
                table.addCell("" + ctRepo.findByID(list.get(i).getIdCTGiay()).getTenCTGiay());

                table.addCell("" + sl);
                table.addCell("" + gia);
                table.addCell("" + (sl * gia));

            }

            document.add(setParagraph("CỬA HÀNG BÁN GIÀY XSHOES", font2, Element.ALIGN_CENTER));
            document.add(setParagraph("355 Nguyễn Văn Cừ, Long Biên, Hà Nội", font, Element.ALIGN_CENTER));
            document.add(setParagraph("Mã đơn hàng: " + hd.getMaHD(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Ngày tạo: " + hd.getNgayTao(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Ngày thanh toán: " + hd.getNgayThanhToan(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Tên nhân viên: " + nv.getHoTen(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Tên khách hàng: " + kh.getHoTen(), font, Element.ALIGN_LEFT));
            document.add(new Paragraph("\n"));
            document.add(table);
            document.add(setParagraph("\nTổng tiền đơn hàng: " + hd.getTongTien() + " VNĐ", font, Element.ALIGN_RIGHT));
            document.add(setParagraph("Giảm giá: " + gg.getMucGiam(), font, Element.ALIGN_RIGHT));
            document.add(setParagraph("Tổng tiền thanh toán: " + hd.getTongTienSauGiam() + " VNĐ", font2, Element.ALIGN_RIGHT));
            document.add(setParagraph("\n\nCảm ơn và hẹn gặp lại quý khách!", font2, Element.ALIGN_CENTER));
            document.close();
            JOptionPane.showMessageDialog(this, "In thành công");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Paragraph setParagraph(String s, Font f, int alight) {
        Paragraph paragraph = new Paragraph(s, f);
        paragraph.setAlignment(alight);
        return paragraph;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoTien = new javax.swing.JRadioButton();
        rdoCK = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboTT = new javax.swing.JComboBox<>();
        cboThang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblhoaDonCT = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(1271, 380));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã hóa đơn", "Nhân viên", "Khách Hàng", "Giảm giá", "Ngày tạo", "Ngày Thanh Toán", "Tổng tiền gốc", "Tiền sau giảm giá", "Hình thức thanh toán", "Trạng thái", "Ghi chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblHoaDon);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm theo Mã HĐ");

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setSelected(true);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTatCaMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoTien);
        rdoTien.setText("Tiền mặt");
        rdoTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTienMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoCK);
        rdoCK.setText("Chuyển khoản");
        rdoCK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCKMouseClicked(evt);
            }
        });

        jLabel3.setText("Trạng thái");

        cboTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chờ" }));
        cboTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTTItemStateChanged(evt);
            }
        });
        cboTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTTActionPerformed(evt);
            }
        });

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cboThang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThangItemStateChanged(evt);
            }
        });
        cboThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangActionPerformed(evt);
            }
        });

        jLabel4.setText("Tháng");

        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel2.setText("Năm");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        jButton1.setText("In hoá đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(rdoTatCa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoTien)
                                .addGap(28, 28, 28)
                                .addComponent(rdoCK)))
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem)
                        .addGap(75, 75, 75)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTatCa)
                    .addComponent(rdoTien)
                    .addComponent(rdoCK)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(cboTT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(btnTimKiem)
                        .addComponent(jLabel2)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1238, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tblhoaDonCT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblhoaDonCT.setModel(new javax.swing.table.DefaultTableModel(
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
                "ID hóa đơn", "ID chi tiết sản phẩm", "Tên Giày", "Số lượng giày", "Hãng", "Màu", "Size", "Chất Liệu"
            }
        ));
        tblhoaDonCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhoaDonCTMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblhoaDonCT);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 1238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(362, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(366, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1286, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:'
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        UUID idHD = UUID.fromString(tblHoaDon.getValueAt(row, 0).toString());
        List<HoaDonChiTietViewModel> listCTGH = new ArrayList<>();
        listCTGH = service.getListById(idHD);

        showDataTableCT((ArrayList<HoaDonChiTietViewModel>) listCTGH);

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void rdoTatCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTatCaMouseClicked
        // TODO add your handling code here:
        searchByTT();
    }//GEN-LAST:event_rdoTatCaMouseClicked

    private void rdoTienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTienMouseClicked
        // TODO add your handling code here:
        searchByTT();
    }//GEN-LAST:event_rdoTienMouseClicked

    private void rdoCKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCKMouseClicked
        // TODO add your handling code here:
        searchByTT();
    }//GEN-LAST:event_rdoCKMouseClicked

    private void cboTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTTItemStateChanged
        // TODO add your handling code here:
        String queryThang = cboThang.getSelectedItem().toString();
        String queryNam = cboNam.getSelectedItem().toString();
        String TT = cboTT.getSelectedItem().toString();
        timThangNamTrangThai(queryThang, queryNam, TT);
    }//GEN-LAST:event_cboTTItemStateChanged

    private void cboTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTTActionPerformed

    private void cboThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThangItemStateChanged
        // TODO add your handling code here:
        String queryThang = cboThang.getSelectedItem().toString();
        String queryNam = cboNam.getSelectedItem().toString();
        String TT = cboTT.getSelectedItem().toString();
        timThangNamTrangThai(queryThang, queryNam, TT);
    }//GEN-LAST:event_cboThangItemStateChanged

    private void cboThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangActionPerformed

    }//GEN-LAST:event_cboThangActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String input = txtTimKiem.getText().trim();
        int hoten = 0;
        try {
            hoten = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Hãy nhập mã để tìm");
            return;
        }
        lists = service.getListByMa(hoten);
        if (lists == null || lists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
            return;
        }
        showDataTable(lists);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
        // TODO add your handling code here:
        String queryThang = cboThang.getSelectedItem().toString();
        String queryNam = cboNam.getSelectedItem().toString();
        String TT = cboTT.getSelectedItem().toString();
        timThangNamTrangThai(queryThang, queryNam, TT);
    }//GEN-LAST:event_cboNamItemStateChanged

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed

    }//GEN-LAST:event_cboNamActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            String idHD = tblHoaDon.getValueAt(selectedRow, 0).toString();
            String MaHD = tblHoaDon.getValueAt(selectedRow, 1).toString();
            String NgayTao = tblHoaDon.getValueAt(selectedRow, 5).toString();
            Object ngayThanhToan = tblHoaDon.getValueAt(selectedRow, 6);
            String NgayTT = ngayThanhToan!=null?tblHoaDon.getValueAt(selectedRow, 6).toString():"";
            String TenNV = tblHoaDon.getValueAt(selectedRow, 2).toString();
            String TenKH = tblHoaDon.getValueAt(selectedRow, 3).toString();

            double TienGoc = Double.parseDouble(tblHoaDon.getValueAt(selectedRow, 7).toString());
            double TienTra = Double.parseDouble(tblHoaDon.getValueAt(selectedRow, 8).toString());
            int GG = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 4).toString());
            String tt = tblHoaDon.getValueAt(selectedRow, 10).toString();

            HoaDon hd = new HoaDon();
            NhanVien nv = new NhanVien();
            KhachHang kh = new KhachHang();
            ChiTietGiay ctg = new ChiTietGiay();
            GiamGia gg = new GiamGia();
            hd.setId(UUID.fromString(idHD));
            hd.setMaHD(Integer.valueOf(MaHD));

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            hd.setNgayTao(NgayTao);

            hd.setNgayThanhToan(NgayTT);

            nv.setHoTen(TenNV);
            kh.setHoTen(TenKH);

            hd.setTongTien(TienGoc);
            hd.setTongTienSauGiam(TienTra);
            gg.setMucGiam(GG);

            int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                if (tt.equals("Chờ")) {
                    JOptionPane.showMessageDialog(this, "Hóa đơn chưa được thanh toán, không thể in!");
                } else {
                    List<HoaDon> listHDCT = service.getListByID(hd.getId());
                    inHoaDon(hd, listHDCT, nv, kh, ctg, gg);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hãy chọn một hoá đơn");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblhoaDonCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhoaDonCTMouseClicked

    }//GEN-LAST:event_tblhoaDonCTMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List<HoaDon> listHD = service.getAll();
        showDataTable(listHD);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboTT;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JRadioButton rdoCK;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JRadioButton rdoTien;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblhoaDonCT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
