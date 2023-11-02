package views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
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
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import repositories.ChatLieuRepository;
import repositories.HangRepository;
import repositories.LoaiGiayRepository;
import repositories.MauSacRepository;
import repositories.SizeRepository;
import service.BanHangService;
import service.CTGiayService;
import service.serviceKH;
import viewModels.KhacHangViewModel;

public class pnBanHang extends javax.swing.JPanel {

    private serviceKH serviceKH;
    private BanHangService service;
    private CTGiayService ctgService;
    private MauSacRepository msRepo;
    private SizeRepository sRepo;
    private HangRepository hangRepo;
    private ChatLieuRepository clRepo;
    DefaultTableModel dtm;
    private List<ChiTietGiay> ds;
    private String idNhanVien;

    public pnBanHang() {
        initComponents();

        service = new BanHangService();
        ctgService = new CTGiayService();
        msRepo = new MauSacRepository();
        sRepo = new SizeRepository();
        clRepo = new ChatLieuRepository();
        hangRepo = new HangRepository();
        serviceKH = new serviceKH();
        ds = new ArrayList<>();
//        this.idnv = 

        //load table
        loadHDcho();
        loadSP();

        //chỉ nhập số
        this.txtTienKhachDua.setDocument(new NumberOnlyDocument());
        this.txtGia.setDocument(new NumberOnlyDocument());

        //set up
        lblDK.setText("Chọn điều kiện để tìm");
        System.out.println(idNhanVien);

    }

    private void clearDiaLogKhachHang() {
        txtDC.setText("");
        txtSDT.setText("");
        txtten1.setText("");
        txtngaysinh.setText("");
        rdonam.setSelected(true);
        txtemail.setText("");
    }

    static class NumberOnlyDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            try {
                Integer.parseInt(str);
                super.insertString(offs, str, a);
            } catch (NumberFormatException e) {

            }
        }

    }

    public void getDta(ArrayList<KhacHangViewModel> list) {
        dtm = (DefaultTableModel) tbKhachHangDL.getModel();

        dtm.setRowCount(0);

        for (KhacHangViewModel kh : list) {
            dtm.addRow(new Object[]{
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

    private void loadHDcho() {
        dtm = (DefaultTableModel) tblHDC.getModel();
        dtm.setRowCount(0);
        List<HoaDon> list = service.getAllHDCho();
        for (HoaDon hd : list) {
            Object[] data = {
                hd.getMaHD(),
                hd.getNgayTao(),
                service.getMaNVFromID(hd.getIdNV()),
                service.getKH(hd.getIdKH()).getMa()
            };

            dtm.addRow(data);
        }

    }

    public void loadSP() {
        dtm = (DefaultTableModel) tblSP.getModel();
        dtm.setRowCount(0);
        List<ChiTietGiay> list = service.getAllCTG();
        for (ChiTietGiay ctg : list) {
            Object[] data = {
                ctg.getMaCTGiay(),
                ctg.getTenCTGiay(),
                msRepo.findById(ctg.getMs()).getTenMS(),
                clRepo.findById(ctg.getCl()).getChatLieuDe(),
                clRepo.findById(ctg.getCl()).getChatLieuThan(),
                hangRepo.findById(ctg.getHang()).getTenHang(),
                sRepo.findById(ctg.getSize()).getSizeGiay(),
                ctg.getSoLuong(),
                ctg.getGiaBan()
            };
            ds.add(ctg);

            dtm.addRow(data);
        }

    }

    private void loadHDCT(UUID idHD) {
        dtm = (DefaultTableModel) tblHDCT.getModel();
        dtm.setRowCount(0);
        List<HoaDonChiTiet> list = service.getAllHDCT(idHD);
        for (HoaDonChiTiet hd : list) {
            Object[] data = {
                ctgService.findByID(hd.getIdCTGiay()).getMaCTGiay(),
                ctgService.findByID(hd.getIdCTGiay()).getTenCTGiay(),
                ctgService.findByID(hd.getIdCTGiay()).getGiaBan(),
                hd.getSoLuong()
            };

            dtm.addRow(data);
        }

    }

    private void loadSearchTable(List<ChiTietGiay> list) {
        dtm = (DefaultTableModel) tblSP.getModel();
        dtm.setRowCount(0);
        for (ChiTietGiay ctg : list) {
            Object[] data = {
                ctg.getMaCTGiay(),
                ctg.getTenCTGiay(),
                msRepo.findById(ctg.getMs()).getTenMS(),
                clRepo.findById(ctg.getCl()).getChatLieuDe(),
                clRepo.findById(ctg.getCl()).getChatLieuThan(),
                hangRepo.findById(ctg.getHang()).getTenHang(),
                sRepo.findById(ctg.getSize()).getSizeGiay(),
                ctg.getSoLuong(),
                ctg.getGiaBan()
            };
            ds.add(ctg);

            dtm.addRow(data);
        }
    }

    private void lamMoi() {
        tblHDC.clearSelection();
        lblMaHD.setText("...");
        lblMaKH.setText("...");
        lblTenKH.setText("...");
        lblTongTien.setText("...");
        lblThanhToan.setText("...");
        lblGiamGia.setText("...");
        lblDonVi.setText("VND");
        txtTienKhachDua.setText(String.valueOf(0));
        lblTienThuaTraKhach.setText("...");
        txtGhiChu.setText(" ");
        dtm = (DefaultTableModel) tblHDCT.getModel();
        dtm.setRowCount(0);
    }

    private void inHoaDon(HoaDon hd, List<HoaDonChiTiet> list, String tenKhachHang, String giamGia, String donVi, String maNV) {
        Document document = new Document();
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
                double gia = ctgService.findByID(list.get(i).getIdCTGiay()).getGiaBan();
                table.addCell((i + 1) + "");
                table.addCell("" + ctgService.findByID(list.get(i).getIdCTGiay()).getTenCTGiay());
                table.addCell("" + sl);
                table.addCell("" + gia);
                table.addCell("" + (sl * gia));

            }

            document.add(setParagraph("CỬA HÀNG BÁN GIÀY XSHOES", font2, Element.ALIGN_CENTER));
            document.add(setParagraph("355 Nguyễn Văn Cừ, Long Biên, Hà Nội", font, Element.ALIGN_CENTER));
            document.add(setParagraph("Mã đơn hàng: " + hd.getMaHD(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Ngày tạo: " + hd.getNgayTao(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Ngày thanh toán: " + LocalDate.now(), font, Element.ALIGN_LEFT));
            document.add(setParagraph("Mã nhân viên: " + maNV, font, Element.ALIGN_LEFT));
            document.add(setParagraph("Tên khách hàng: " + tenKhachHang, font, Element.ALIGN_LEFT));
            document.add(new Paragraph("\n"));
            document.add(table);
            document.add(setParagraph("\nTổng tiền đơn hàng: " + hd.getTongTien() + " VNĐ", font, Element.ALIGN_RIGHT));
            document.add(setParagraph("Giảm giá: " + giamGia + " " + donVi, font, Element.ALIGN_RIGHT));
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

    public void setIdNhanVien(String id) {
        this.idNhanVien = id;
    }

    public String getIdNhanVien() {
        return this.idNhanVien;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        diaLogKhachHang = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbKhachHangDL = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTimTenKHDL = new javax.swing.JTextField();
        btnLM = new javax.swing.JButton();
        btnChon = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtngaysinh = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtDC = new javax.swing.JTextArea();
        txtemail = new javax.swing.JTextField();
        txtten1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        jLabel58 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHDC = new javax.swing.JTable();
        pnlCam = new javax.swing.JPanel();
        lblQR = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        btnChonKH = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        btnTaoHD = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        lblDonVi = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTienThuaTraKhach = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnHuyHD = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        cbbHinhThuc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        btnXoaSP = new javax.swing.JButton();
        btnXoaTatCaSP = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txtSP = new javax.swing.JTextField();
        btnThemSP = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        cbbGia = new javax.swing.JComboBox<>();
        txtGia = new javax.swing.JTextField();
        lblDK = new javax.swing.JLabel();

        diaLogKhachHang.setTitle("Danh Sach Khach Hang");
        diaLogKhachHang.setModal(true);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tbKhachHangDL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Ngày Sinh", "Giới Tính", "SĐT", "Địa Chỉ", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbKhachHangDL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKhachHangDLMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbKhachHangDL);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Danh sách khách hàng");

        jLabel4.setText("Tìm kiếm theo tên:");

        txtTimTenKHDL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimTenKHDLKeyReleased(evt);
            }
        });

        btnLM.setText("Làm mới");
        btnLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLMActionPerformed(evt);
            }
        });

        btnChon.setText("Chọn");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(311, 311, 311)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 231, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(32, 32, 32)
                        .addComponent(txtTimTenKHDL, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(btnChon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLM)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTimTenKHDL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLM)
                    .addComponent(btnChon))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel7);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Địa chỉ");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Email");

        txtDC.setColumns(20);
        txtDC.setRows(5);
        jScrollPane9.setViewportView(txtDC);

        txtten1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtten1ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("Họ Tên khách hàng");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Giới tính");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("Số điện thoại");

        rdonam.setSelected(true);
        rdonam.setText("Nam");
        rdonam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonamActionPerformed(evt);
            }
        });

        rdonu.setText("Nữ");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Ngày Sinh");

        btnAdd.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Thêm thông tin");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setText("Làm mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel39))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdonam)
                        .addGap(32, 32, 32)
                        .addComponent(rdonu))
                    .addComponent(txtten1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel58))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtngaysinh)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(74, 74, 74))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtten1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel39)
                        .addComponent(rdonam)
                        .addComponent(rdonu)
                        .addComponent(jLabel41)))
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
        );

        jTabbedPane1.addTab("Cập nhật khách hàng", jPanel2);

        javax.swing.GroupLayout diaLogKhachHangLayout = new javax.swing.GroupLayout(diaLogKhachHang.getContentPane());
        diaLogKhachHang.getContentPane().setLayout(diaLogKhachHangLayout);
        diaLogKhachHangLayout.setHorizontalGroup(
            diaLogKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diaLogKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        diaLogKhachHangLayout.setVerticalGroup(
            diaLogKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diaLogKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 750));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn chờ"));

        tblHDC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã HĐ", "Ngày tạo", "Mã nhân viên tạo", "Mã khách hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblHDCMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblHDC);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        pnlCam.setBorder(javax.swing.BorderFactory.createTitledBorder("Quét mã sản phẩm"));

        lblQR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQR.setText("Click here");
        lblQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQRMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlCamLayout = new javax.swing.GroupLayout(pnlCam);
        pnlCam.setLayout(pnlCamLayout);
        pnlCamLayout.setHorizontalGroup(
            pnlCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQR, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        pnlCamLayout.setVerticalGroup(
            pnlCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Mã khách hàng: ");

        lblMaKH.setText(".....");

        btnChonKH.setBackground(new java.awt.Color(24, 31, 75));
        btnChonKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChonKH.setForeground(new java.awt.Color(255, 255, 255));
        btnChonKH.setText("Chọn");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        jLabel3.setText("Tên khách hàng:");

        lblTenKH.setText(".....");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblMaKH)
                    .addComponent(btnChonKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTenKH))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel5.setText("Mã hóa đơn:");

        lblMaHD.setText(".....");

        btnTaoHD.setBackground(new java.awt.Color(24, 31, 75));
        btnTaoHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHD.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHD.setText("Tạo");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        jLabel7.setText("Tổng tiền:");

        lblTongTien.setText(".....");

        jLabel9.setText("VNĐ");

        jLabel10.setText("Giảm giá:");

        lblGiamGia.setText(".....");

        lblDonVi.setText("VNĐ");

        jLabel13.setText("Thanh toán:");

        lblThanhToan.setText(".....");

        jLabel15.setText("VNĐ");

        jLabel16.setText("Tiền khách đưa:");

        txtTienKhachDua.setText("0");
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        jLabel18.setText("VNĐ");

        jLabel17.setText("Tiền thừa trả khách:");

        lblTienThuaTraKhach.setText(".....");

        jLabel20.setText("VNĐ");

        jLabel21.setText("Hình thức thanh toán:");

        jLabel22.setText("Ghi chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        btnHuyHD.setBackground(new java.awt.Color(24, 31, 75));
        btnHuyHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyHD.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHD.setText("Hủy hóa đơn");
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(24, 31, 75));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(24, 31, 75));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        cbbHinhThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblDonVi)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel20))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTienThuaTraKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel22))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblMaHD)
                    .addComponent(btnTaoHD))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblGiamGia)
                    .addComponent(lblDonVi))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblThanhToan)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblTienThuaTraKhach)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbbHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuyHD)
                    .addComponent(btnLamMoi))
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        tblHDCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblHDCTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblHDCTKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tblHDCT);

        btnXoaSP.setBackground(new java.awt.Color(24, 31, 75));
        btnXoaSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSP.setText("Xóa sản phẩm");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        btnXoaTatCaSP.setBackground(new java.awt.Color(24, 31, 75));
        btnXoaTatCaSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaTatCaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTatCaSP.setText("Xóa tất cả");
        btnXoaTatCaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTatCaSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(btnXoaTatCaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnXoaSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnXoaTatCaSP)
                .addGap(33, 33, 33))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Màu", "CL Đế", "CL Thân", "Hãng", "Size", "Số lượng tồn", "Đơn giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblSP);

        jLabel23.setText("Tìm kiếm sản phẩm:");

        txtSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSPKeyReleased(evt);
            }
        });

        btnThemSP.setBackground(new java.awt.Color(24, 31, 75));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        jLabel24.setText("Giá:");

        cbbGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "=", ">=", "<=" }));
        cbbGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbGiaMouseClicked(evt);
            }
        });
        cbbGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiaActionPerformed(evt);
            }
        });

        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });

        lblDK.setForeground(new java.awt.Color(255, 0, 51));
        lblDK.setText(" ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtSP, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThemSP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(lblDK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(lblDK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSP)
                    .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlCam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn để thêm sản phẩm");
            return;
        }
        int rowSP = this.tblSP.getSelectedRow();
        if (rowSP == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 sản phẩm");
            return;
        }

        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();
        String maSP = tblSP.getValueAt(rowSP, 0).toString();

        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        UUID idSP = ctgService.findIDByMa(maSP);
        ChiTietGiay ctg = ctgService.findByID(idSP);
        double dongGia = ctg.getGiaBan();
        int sl = 1;
        double tongTien = 0;

        int giamGia = service.mucGiamFromHD(hd.getId());

        int check = service.checkCTGInHDCT(idSP, hd.getId());
        int checkSLTon0 = service.checkSLTonSP0(idSP);
        int checkSLTon = service.checkSLTonSP(hd.getId(), idSP);

        if (checkSLTon0 < 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
            return;
        } else {
            if (check != 0) {
                if (checkSLTon <= 0) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
                    return;
                }
                service.themSLSPVaoGio(idSP, hd.getId());
                loadHDCT(hd.getId());
                tongTien = service.getTongTien(hd.getId());
                System.out.println(tongTien);
                service.suaTongTien(tongTien, hd.getId());

                lblTongTien.setText(String.valueOf(tongTien));
                double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
                service.suaTongTienSauGiamGia(thanhToan, hd.getId());
                lblThanhToan.setText(String.valueOf(thanhToan));
                String tienKhachDua = txtTienKhachDua.getText();
                if (tienKhachDua == null || tienKhachDua.equals("")) {
                    tienKhachDua = "0";

                }

                double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
                lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                return;
            }
//        } else {
//            if(ctg.getSoLuong()<0){
//                JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
//                return;
//            }

            service.themSPVaoGio(hd.getId(), idSP, 1);
            loadHDCT(hd.getId());
            tongTien = service.getTongTien(hd.getId());
            service.suaTongTien(tongTien, hd.getId());

            lblTongTien.setText(String.valueOf(tongTien));
            double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
            service.suaTongTienSauGiamGia(thanhToan, hd.getId());
            lblThanhToan.setText(String.valueOf(thanhToan));
            String tienKhachDua = txtTienKhachDua.getText();
            if (tienKhachDua == null || tienKhachDua.equals("")) {
                tienKhachDua = "0";

            }
            double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
            lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
            JOptionPane.showMessageDialog(this, "Thêm thành công");
//        }

        }


    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        UUID idNV = UUID.fromString(idNhanVien);
        GiamGia gg = service.getGiamGia();
        System.out.println(idNV);
        UUID idKH = UUID.fromString("4226782C-FD57-4454-94C5-78E61DCEB398");
        service.TaoHD(idNV, idKH, UUID.fromString(gg.getId()));
        loadHDcho();
        KhachHang kh = service.getKH(idKH);
        lblMaKH.setText(kh.getMa());
        lblTenKH.setText(kh.getHoTen());

        int mucGiam = gg.getMucGiam();
        lblDonVi.setText(mucGiam <= 100 ? "%" : "VND");
        lblGiamGia.setText(String.valueOf(gg.getMucGiam()));
//        this.tblHDC.setRowSelectionInterval(0, 0);
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void tblHDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCMouseClicked
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            return;
        }

        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();
        lblMaHD.setText(maHD);
        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        double tongTien = service.getTongTien(hd.getId());
        KhachHang kh = service.getKH(hd.getIdKH());
        int giamGia = service.mucGiamFromHD(hd.getId());

        lblDonVi.setText(giamGia <= 100 ? "%" : "VND");
        lblGiamGia.setText(String.valueOf(giamGia));
        lblMaKH.setText(kh.getMa());
        lblTenKH.setText(kh.getHoTen());
        lblTongTien.setText(String.valueOf(tongTien));
        double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
        lblThanhToan.setText(String.valueOf(thanhToan));
        String tienKhachDua = txtTienKhachDua.getText();
        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";

        }
        double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
        lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
        loadHDCT(hd.getId());
    }//GEN-LAST:event_tblHDCMouseClicked

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn muốn hủy");
            return;
        }
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();

        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));

        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        service.huyHD(hd.getId());

        int giamGia = 0;

        double tongTien = service.getTongTien(hd.getId());
        lblTongTien.setText(String.valueOf(tongTien));
        lblThanhToan.setText(String.valueOf(tongTien - giamGia));
        String tienKhachDua = txtTienKhachDua.getText();
        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";

        }
        lblTienThuaTraKhach.setText(String.valueOf(Integer.parseInt(tienKhachDua) - tongTien));
        loadHDcho();
        lamMoi();
        JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công");
    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        lamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXoaTatCaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTatCaSPActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn để xóa sản phẩm");
            return;
        }
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();
        UUID idHD = service.getHDByMa(Integer.parseInt(maHD)).getId();
        service.xoaTatCaSPHDCT(idHD);

        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        int giamGia = service.mucGiamFromHD(hd.getId());
        double tongTien = service.getTongTien(hd.getId());
        lblTongTien.setText(String.valueOf(tongTien));
        double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
        service.suaTongTienSauGiamGia(thanhToan, hd.getId());
        lblThanhToan.setText(String.valueOf(thanhToan));
        String tienKhachDua = txtTienKhachDua.getText();
        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";

        }
        double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
        lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
        loadHDCT(idHD);
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnXoaTatCaSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn để xóa sản phẩm");
            return;
        }
        int rowSP = this.tblHDCT.getSelectedRow();
        if (rowSP == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 sản phẩm");
            return;
        }
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();
        String maSP = tblHDCT.getValueAt(rowSP, 0).toString();

        UUID idHD = service.getHDByMa(Integer.parseInt(maHD)).getId();
        UUID idSP = ctgService.findIDByMa(maSP);
        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        service.xoa1SPHDCT(idHD, idSP);

        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        int giamGia = service.mucGiamFromHD(hd.getId());
        double tongTien = service.getTongTien(hd.getId());
        lblTongTien.setText(String.valueOf(tongTien));
        double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
        service.suaTongTienSauGiamGia(thanhToan, hd.getId());
        lblThanhToan.setText(String.valueOf(thanhToan));
        String tienKhachDua = txtTienKhachDua.getText();
        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";

        }
        double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
        lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
        loadHDCT(idHD);
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            return;
        }
        int giamGia = 0;
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();

        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        double tongTien = service.getTongTien(hd.getId());

        String tienKhachDua = txtTienKhachDua.getText();
        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";
        }

        lblTienThuaTraKhach.setText(String.valueOf(Integer.parseInt(tienKhachDua) - Double.parseDouble(lblThanhToan.getText())));
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn để thanh toán");
            return;
        }

        String maNV = tblHDC.getValueAt(rowHDC, 2).toString();
        String tenKH = lblTenKH.getText();
        String giamGia = lblGiamGia.getText();
        String donVi = lblDonVi.getText();
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();
        String ghiChu = txtGhiChu.getText();
        String hinhThuc = cbbHinhThuc.getSelectedItem().toString();
        boolean httt = hinhThuc.equalsIgnoreCase("Chuyển khoản") ? true : false;
        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));

        service.suaNV(UUID.fromString(idNhanVien), hd.getId());
        HoaDon hd2 = service.getHDByMa(Integer.parseInt(maHD));
        String tienKhachDua = txtTienKhachDua.getText();
        int slspinhdct = service.getSLSPInHDCT(hd.getId());

        if (slspinhdct <= 0) {
            JOptionPane.showMessageDialog(this, "Xin vui lòng chọn sản phẩm để thanh toán!");
            return;
        }

        if (tienKhachDua == null || tienKhachDua.equals("")) {
            tienKhachDua = "0";
        }
        int tkd = Integer.parseInt(tienKhachDua);
        double thanhToan = Double.parseDouble(lblThanhToan.getText());

        if (tkd < thanhToan) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không đủ");
            return;
        }

        int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn thanh toán không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        String tienThuTraKhach = lblTienThuaTraKhach.getText();
        service.suaTrangThai(hd.getId());
        service.suaSLSP(hd.getId());
        service.suaGhiChuVaHinhThuc(ghiChu, httt, hd.getId());
        loadHDcho();
        loadSP();
        lamMoi();
        if (tkd > thanhToan) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công. Tiền thừa của khách là: " + tienThuTraKhach);
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công");
        }

        int choose2 = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose2 == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        List<HoaDonChiTiet> listHDCT = service.getAllHDCT(hd.getId());

        System.out.println(tenKH + giamGia + donVi);
        inHoaDon(hd2, listHDCT, tenKH, giamGia, donVi, maNV);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void lblQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQRMouseClicked
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn để thêm bằng sản phẩm quét mã QR");
            return;
        }
        ReadQR readQR = new ReadQR();
        readQR.setVisible(true);
        String s = readQR.getText();
        System.out.println(s);
        String maHD = tblHDC.getValueAt(rowHDC, 0).toString();

        int indexTen = s.indexOf("Ten");

        String maSP = s.substring(12, indexTen).trim();
        System.out.println(maSP);
        HoaDon hd = service.getHDByMa(Integer.parseInt(maHD));
        UUID idSP = ctgService.findIDByMa(maSP);
        if (idSP == null) {
            JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại");
            return;
        }
        ChiTietGiay ctg = ctgService.findByID(idSP);
        double dongGia = ctg.getGiaBan();
        int sl = 1;
        double tongTien = 0;

        int giamGia = service.mucGiamFromHD(hd.getId());

        int check = service.checkCTGInHDCT(idSP, hd.getId());
        int checkSLTon0 = service.checkSLTonSP0(idSP);
        int checkSLTon = service.checkSLTonSP(hd.getId(), idSP);
        if (checkSLTon0 < 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
            return;
        } else {
            if (check != 0) {
                if (checkSLTon <= 0) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
                    return;
                }
                service.themSLSPVaoGio(idSP, hd.getId());
                loadHDCT(hd.getId());
                tongTien = service.getTongTien(hd.getId());
                System.out.println(tongTien);
                service.suaTongTien(tongTien, hd.getId());

                lblTongTien.setText(String.valueOf(tongTien));
                double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
                service.suaTongTienSauGiamGia(thanhToan, hd.getId());
                lblThanhToan.setText(String.valueOf(thanhToan));
                String tienKhachDua = txtTienKhachDua.getText();
                if (tienKhachDua == null || tienKhachDua.equals("")) {
                    tienKhachDua = "0";

                }

                double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
                lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                return;
            }
//        } else {
//            if(ctg.getSoLuong()<0){
//                JOptionPane.showMessageDialog(this, "Sản phẩm này đã hết hàng");
//                return;
//            }

            service.themSPVaoGio(hd.getId(), idSP, 1);
            loadHDCT(hd.getId());
            tongTien = service.getTongTien(hd.getId());
            service.suaTongTien(tongTien, hd.getId());

            lblTongTien.setText(String.valueOf(tongTien));
            double thanhToan = giamGia > 100 ? (tongTien - giamGia) : (tongTien - tongTien * giamGia / 100);
            service.suaTongTienSauGiamGia(thanhToan, hd.getId());
            lblThanhToan.setText(String.valueOf(thanhToan));
            String tienKhachDua = txtTienKhachDua.getText();
            if (tienKhachDua == null || tienKhachDua.equals("")) {
                tienKhachDua = "0";

            }
            double tienThuaKhachTra = Double.parseDouble(tienKhachDua) - thanhToan;
            lblTienThuaTraKhach.setText(String.valueOf(tienThuaKhachTra));
            JOptionPane.showMessageDialog(this, "Thêm thành công");
//        }

        }

    }//GEN-LAST:event_lblQRMouseClicked

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        String soSanh = this.cbbGia.getSelectedItem().toString();
        String gia = txtGia.getText();

        if (gia.trim().equals("")) {
            lblDK.setText("Nhập giá để tìm");
            return;
        }

        if (soSanh.trim().equals("")) {
            lblDK.setText("Chọn điều kiện để tìm");
            return;
        }

        if (soSanh.equalsIgnoreCase("=")) {
            loadSearchTable(service.getAllCTGEqual(gia));
        } else if (soSanh.equalsIgnoreCase(">=")) {
            loadSearchTable(service.getAllCTGGreater(gia));
        } else {
            loadSearchTable(service.getAllCTGLess(gia));
        }
        txtSP.setText("");
    }//GEN-LAST:event_txtGiaKeyReleased

    private void txtSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSPKeyReleased
        String ten = txtSP.getText();
        loadSearchTable(service.getAllCTGByName(ten));
        txtGia.setText("");
    }//GEN-LAST:event_txtSPKeyReleased

    private void cbbGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbGiaMouseClicked

    }//GEN-LAST:event_cbbGiaMouseClicked

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed
        Object o = this.cbbGia.getSelectedItem();
        if (o == null) {
            return;
        }
        String soSanh = o.toString();
        String gia = txtGia.getText();
        txtSP.setText("");

        if (soSanh.trim().equals("")) {
            lblDK.setText("Chọn điều kiện để tìm");
            return;
        } else {
            lblDK.setText(" ");
        }

        if (gia.trim().equals("")) {
            lblDK.setText("Nhập giá để tìm");
            return;
        } else {
            lblDK.setText(" ");
        }

        if (soSanh.equalsIgnoreCase("=")) {
            loadSearchTable(service.getAllCTGEqual(gia));
        } else if (soSanh.equalsIgnoreCase(">=")) {
            loadSearchTable(service.getAllCTGGreater(gia));
        } else {
            loadSearchTable(service.getAllCTGLess(gia));
        }
    }//GEN-LAST:event_cbbGiaActionPerformed

    private void txtTimTenKHDLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimTenKHDLKeyReleased
        // TODO add your handling code here:
        String ten = txtTimTenKHDL.getText();
        ArrayList<KhacHangViewModel> kq = serviceKH.sreach(ten);
        getDta(kq);
    }//GEN-LAST:event_txtTimTenKHDLKeyReleased

    private void txtten1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtten1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtten1ActionPerformed

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
        if (txtngaysinh.getText() == null) {
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

        serviceKH.them(kh);
        getDta(serviceKH.getAll());
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }//GEN-LAST:event_btnAddActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.clearDiaLogKhachHang();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        int rowHDC = this.tblHDC.getSelectedRow();
        if (rowHDC == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 hóa đơn");
            return;
        }
        diaLogKhachHang.setBounds(0, 0, 935, 530);
        diaLogKhachHang.setLocationRelativeTo(null);
        diaLogKhachHang.setVisible(true);
        this.clearDiaLogKhachHang();
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMActionPerformed
        getDta(serviceKH.getAll());
    }//GEN-LAST:event_btnLMActionPerformed

    private void tbKhachHangDLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhachHangDLMouseClicked

    }//GEN-LAST:event_tbKhachHangDLMouseClicked

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        int row = this.tbKhachHangDL.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 khách hàng");
            return;
        }
        int rowHDC = this.tblHDC.getSelectedRow();

        int maKH = Integer.parseInt(this.tbKhachHangDL.getValueAt(row, 0).toString());
        int maHD = Integer.parseInt(this.tblHDC.getValueAt(rowHDC, 0).toString());
        UUID idHD = service.getHDByMa(maHD).getId();
        UUID idKH = service.getIdKH(maKH);
        KhachHang kh = service.getKH(idKH);
        service.suaKH(idKH, idHD);
        this.diaLogKhachHang.dispose();
        lblMaKH.setText(String.valueOf(maKH));
        lblTenKH.setText(kh.getHoTen());
        loadHDcho();
    }//GEN-LAST:event_btnChonActionPerformed

    private void tblHDCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDCMouseEntered

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void tblHDCTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblHDCTKeyTyped

    }//GEN-LAST:event_tblHDCTKeyTyped

    private void tblHDCTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblHDCTKeyReleased
//        int row = tblHDCT.getSelectedRow();
//        if(row==-1){
//            return;
//        }
//        String tenSP = tblHDCT.getValueAt(row, 1).toString();
//        int soLuong = Integer.parseInt(tblHDCT.getValueAt(row, 3).toString());
//        int donGia = Integer.parseInt(tblHDCT.getValueAt(row, 2).toString());
//        System.out.println("so luong: "+soLuong);
//        System.out.println("Tổng tiền của sản phầm "+tenSP+" là: "+soLuong*donGia);
    }//GEN-LAST:event_tblHDCTKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnChonKH;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnLM;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JButton btnXoaTatCaSP;
    private javax.swing.JComboBox<String> cbbGia;
    private javax.swing.JComboBox<String> cbbHinhThuc;
    private javax.swing.JDialog diaLogKhachHang;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDK;
    private javax.swing.JLabel lblDonVi;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblQR;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThuaTraKhach;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel pnlCam;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTable tbKhachHangDL;
    private javax.swing.JTable tblHDC;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextArea txtDC;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSP;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimTenKHDL;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtten1;
    // End of variables declaration//GEN-END:variables
}
