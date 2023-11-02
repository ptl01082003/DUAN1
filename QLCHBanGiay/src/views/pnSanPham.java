package views;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import domainModels.ChatLieu;
import domainModels.ChiTietGiay;
import domainModels.DanhMucSanPham;
import domainModels.Giay;
import domainModels.Hang;
import domainModels.LoaiGiay;
import domainModels.MauSac;
import domainModels.QR;
import domainModels.Size;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.CTGiayRepository;
import repositories.ChatLieuRepository;
import repositories.DanhMucSPRepository;
import repositories.GiayRepository;
import repositories.HangRepository;
import repositories.LoaiGiayRepository;
import repositories.MauSacRepository;
import repositories.QrRepository;
import repositories.SizeRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import utilities.JDBCHelper;

public class pnSanPham extends javax.swing.JPanel {

    private String image, excelPath;
    private CTGiayRepository repo;
    private GiayRepository giayRepo;
    private ChatLieuRepository clRepo;
    private DanhMucSPRepository dmRepo;
    private HangRepository hangRepo;
    private LoaiGiayRepository lgRepo;
    private MauSacRepository msRepo;
    private SizeRepository sRepo;
    private QrRepository qrRepo;
    private DefaultTableModel dtm = new DefaultTableModel();

    public pnSanPham() {
        initComponents();

        this.repo = new CTGiayRepository();
        this.giayRepo = new GiayRepository();
        this.dmRepo = new DanhMucSPRepository();
        this.clRepo = new ChatLieuRepository();
        this.hangRepo = new HangRepository();
        this.lgRepo = new LoaiGiayRepository();
        this.msRepo = new MauSacRepository();
        this.sRepo = new SizeRepository();
        this.qrRepo = new QrRepository();

        ButtonGroup group = new ButtonGroup();
        group.add(this.rdoConHang);
        group.add(this.rdoHetHang);
        group.add(this.rdoKhongHoatDong);

        //set border image
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        this.lblAnh.setBorder(blackline);

        //Load CBB
        this.loadCBB();

        //Load table
        this.loadTable();
        loadTableDM();
        loadTableCL();

        //trang thai
        this.rdoConHang.setSelected(true);
        rdoDM.setSelected(true);

        //lam moi
        lamMoi();
        lamMoi2();
        lamMoi3();
        //chỉ nhập số
//        this.txtSize.setDocument(new NumberOnlyDocument());
//        this.txtSL.setDocument(new NumberOnlyDocument());
//        this.txtGiaNhap.setDocument(new NumberOnlyDocument());
//        this.txtGiaBan.setDocument(new NumberOnlyDocument());
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

    private void loadTable() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

        dtm.setRowCount(0);

        List<ChiTietGiay> ds = repo.getAll();

        if (ds == null) {
            return;
        }

        for (ChiTietGiay d : ds) {
            Object[] data = {
                d.getMaCTGiay(),
                d.getTenCTGiay(),
                this.giayRepo.findByName(d.getGiay()).getTenGiay(),
                this.msRepo.findById(d.getMs()).getTenMS(),
                this.hangRepo.findById(d.getHang()).getTenHang(),
                this.sRepo.findById(d.getSize()).getSizeGiay(),
                this.clRepo.findById(d.getCl()).getChatLieuThan(),
                this.clRepo.findById(d.getCl()).getChatLieuDe(),
                this.lgRepo.findById(d.getLoaiGiay()).getTenLoaiGiay(),
                this.dmRepo.findByName(d.getDanhMuc()).getTenDanhMuc(),
                d.getSoLuong(),
                d.getGiaNhap(),
                d.getGiaBan(),
                d.getAnh(),
                d.getTrangThai() == 0 ? "Còn hàng" : d.getTrangThai() == 1 ? "Hết hàng" : "Không hoạt động",
                d.getMoTa()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableDM() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<DanhMucSanPham> ds = dmRepo.getAll();

        if (ds == null) {
            return;
        }

        for (DanhMucSanPham d : ds) {
            Object[] data = {
                d.getMaDanhMuc(),
                d.getTenDanhMuc()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableG() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<Giay> ds = giayRepo.getAll();

        if (ds == null) {
            return;
        }

        for (Giay d : ds) {
            Object[] data = {
                d.getMaGiay(),
                d.getTenGiay()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableH() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<Hang> ds = hangRepo.getAll();

        if (ds == null) {
            return;
        }

        for (Hang d : ds) {
            Object[] data = {
                d.getMaHang(),
                d.getTenHang()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableLG() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<LoaiGiay> ds = lgRepo.getAll();

        if (ds == null) {
            return;
        }

        for (LoaiGiay d : ds) {
            Object[] data = {
                d.getMaLoaiGiay(),
                d.getTenLoaiGiay()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableMS() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<MauSac> ds = msRepo.getAll();

        if (ds == null) {
            return;
        }

        for (MauSac d : ds) {
            Object[] data = {
                d.getMaMS(),
                d.getTenMS()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableS() {
        DefaultTableModel dtm = (DefaultTableModel) tblTTSP.getModel();

        dtm.setRowCount(0);

        List<Size> ds = sRepo.getAll();

        if (ds == null) {
            return;
        }

        for (Size d : ds) {
            Object[] data = {
                d.getMaSize(),
                d.getSizeGiay()
            };

            dtm.addRow(data);
        }
    }

    private void loadTableCL() {
        DefaultTableModel dtm = (DefaultTableModel) tblCL.getModel();

        dtm.setRowCount(0);

        List<ChatLieu> ds = clRepo.getAll();

        if (ds == null) {
            return;
        }

        for (ChatLieu d : ds) {
            Object[] data = {
                d.getMaChatLieu(),
                d.getChatLieuThan(),
                d.getChatLieuDe()
            };

            dtm.addRow(data);
        }
    }

    private void loadCBB() {
        //giay
        DefaultComboBoxModel<String> dcbm = (DefaultComboBoxModel<String>) this.cbbGiay.getModel();
        DefaultComboBoxModel<String> searchGiay = (DefaultComboBoxModel<String>) this.cbbSearchSP.getModel();
        List<Giay> listGiay = giayRepo.getAll();
        dcbm.removeAllElements();
        searchGiay.removeAllElements();

        for (Giay g : listGiay) {
            dcbm.addElement(g.getTenGiay());
            searchGiay.addElement(g.getTenGiay());
        }

        DefaultComboBoxModel<String> ms = (DefaultComboBoxModel<String>) this.cbbMauSac.getModel();
        List<MauSac> listMS = msRepo.getAll();

        ms.removeAllElements();

        for (MauSac o : listMS) {
            ms.addElement(o.getTenMS());
        }

        DefaultComboBoxModel<String> h = (DefaultComboBoxModel<String>) this.cbbHang.getModel();
        List<Hang> listHang = hangRepo.getAll();
        h.removeAllElements();

        for (Hang o : listHang) {
            h.addElement(o.getTenHang());
        }

        DefaultComboBoxModel<String> clThan = (DefaultComboBoxModel<String>) this.cbbCLThan.getModel();
        DefaultComboBoxModel<String> clDe = (DefaultComboBoxModel<String>) this.cbbCLDe.getModel();
        List<String> listCLDe = clRepo.getAllCLDe();
        List<String> listCLThan = clRepo.getAllCLThan();
        clDe.removeAllElements();
        clThan.removeAllElements();

        for (String de : listCLDe) {
            clDe.addElement(de);

        }

        for (String than : listCLThan) {
            clThan.addElement(than);
        }

        DefaultComboBoxModel<String> lg = (DefaultComboBoxModel<String>) this.cbbLoaiGiay.getModel();
        DefaultComboBoxModel<String> searchLg = (DefaultComboBoxModel<String>) this.cbbSearchLoaiGiay.getModel();
        List<LoaiGiay> listLoaiGiay = lgRepo.getAll();
        lg.removeAllElements();
        searchLg.removeAllElements();

        for (LoaiGiay lgiay : listLoaiGiay) {
            lg.addElement(lgiay.getTenLoaiGiay());
            searchLg.addElement(lgiay.getTenLoaiGiay());
        }

        DefaultComboBoxModel<String> dm = (DefaultComboBoxModel<String>) this.cbbDanhMuc.getModel();
        List<DanhMucSanPham> listDM = dmRepo.getAll();
        dm.removeAllElements();

        for (DanhMucSanPham dmsp : listDM) {
            dm.addElement(dmsp.getTenDanhMuc());
        }
    }

    private ChiTietGiay getData() {
        ChiTietGiay ctg = new ChiTietGiay();
        String ten = this.txtTen.getText().trim();
        String g = this.cbbGiay.getSelectedItem().toString();
        String ms = this.cbbMauSac.getSelectedItem().toString();
        String h = this.cbbHang.getSelectedItem().toString();
        String sizeG = this.txtSize.getText();
        String than = this.cbbCLThan.getSelectedItem().toString();
        String de = this.cbbCLDe.getSelectedItem().toString();
        String lGiay = this.cbbLoaiGiay.getSelectedItem().toString();
        String dm = this.cbbDanhMuc.getSelectedItem().toString();
        String solg = this.txtSL.getText();
        String gNhap = this.txtGiaNhap.getText();
        String gBan = this.txtGiaBan.getText();
        int trangThai = 0;
        if (this.rdoConHang.isSelected()) {
            trangThai = 0;
        } else if (this.rdoHetHang.isSelected()) {
            trangThai = 1;
        } else {
            trangThai = 2;
        }
        String moTa = this.txtMoTa.getText();

        // check trống
        if (ten.trim().length() == 0 || ten == null) {
            JOptionPane.showMessageDialog(this, "Không để trống tên");
            return null;
        }

        if (sizeG.trim().length() == 0 || sizeG == null) {
            JOptionPane.showMessageDialog(this, "Không để trống Size");
            return null;
        }

        if (solg.trim().length() == 0 || solg == null) {
            JOptionPane.showMessageDialog(this, "Không để trống số lượng");
            return null;
        }

        if (gNhap.trim().length() == 0 || gNhap == null) {
            JOptionPane.showMessageDialog(this, "Không để trống giá nhập");
            return null;
        }

        if (gBan.trim().length() == 0 || gBan == null) {
            JOptionPane.showMessageDialog(this, "Không để trống giá bán");
            return null;
        }

        //check tồn tại
        //size
        int size = Integer.parseInt(sizeG);
        int existSize = (int) this.sRepo.checkSize(size);
        if (existSize == 0) {
            Size s = new Size();
            s.setSizeGiay(size);
            sRepo.them(s);
            Size s2 = sRepo.findByName(s.getMaSize());

            ctg.setSize(s2.getId());
        } else {

            ctg.setSize(this.sRepo.findByName(size).getId());
        }
        //chat liệu
        int existCL = (int) this.clRepo.checkTenCL(than, de);
        if (existCL == 0) {
            ChatLieu cl = new ChatLieu();
            cl.setChatLieuThan(than);
            cl.setChatLieuDe(de);
            this.clRepo.themCL(cl);
            ChatLieu cl2 = clRepo.findByCL(cl.getChatLieuThan(), cl.getChatLieuDe());
            ctg.setCl(cl2.getId());
        } else {
            ctg.setCl(this.clRepo.findByCL(than, de).getId());
        }
        //tên chi tiêt giày
//        int existCTGiay = (int) this.repo.checkTenGiay(ten);
//        if (existCTGiay == 0) {
//            JOptionPane.showMessageDialog(this, "Tên chi tiết giày đã tồn tại");
//            return null;
//        }

        //chuyển đổi
        int sl = Integer.parseInt(solg);
        double giaNhap = Double.parseDouble(gNhap);
        double giaBan = Double.parseDouble(gBan);

        ctg.setTenCTGiay(ten);
        ctg.setGiay(this.giayRepo.findByName(g).getId());
        ctg.setMs(this.msRepo.findByName(ms).getId());
        ctg.setHang(this.hangRepo.findByName(h).getId());

        ctg.setLoaiGiay(this.lgRepo.findByName(lGiay).getId());
        ctg.setDanhMuc(this.dmRepo.findByName(dm).getId());
        ctg.setSoLuong(sl);
        ctg.setGiaNhap(giaNhap);
        ctg.setGiaBan(giaBan);
        ctg.setAnh(image);
        ctg.setMoTa(moTa);
        ctg.setTrangThai(trangThai);

        ctg.setQr(null);
        return ctg;
    }

//    private Size createSize() {
//        Size size = new Size();
//        size.setSizeGiay(Integer.parseInt(txtSize.getText()));
//        sRepo.them(size);
//        return size;
//    }
    private QR createQRCode(ChiTietGiay ctg) {
        //tạo qr code
        QR qrCode = new QR();

        String filePath = "D:\\FPT Polytechnic\\project\\QR\\SPCT_" + ctg.getTenCTGiay() + ".png"; // Đường dẫn và tên file hình ảnh mã QR

//        int width = 300; // Độ rộng của mã QR
//        int height = 300; // Chiều cao của mã QR
//        String format = "png"; // Định dạng của hình ảnh mã QR
//
//        // Tạo đối tượng BitMatrix để lưu trữ dữ liệu mã QR
//        BitMatrix bitMatrix;
//        try {
//            // Thiết lập các thông số cho mã QR
//            com.google.zxing.Writer writer = new MultiFormatWriter();
//            com.google.zxing.common.BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
//            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//            // Lưu dữ liệu từ BitMatrix vào BufferedImage
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
//                    image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
//                }
//            }
//
//            // Lưu hình ảnh mã QR vào file
//            File qrFile = new File(filePath);
//            ImageIO.write(image, format, qrFile);
//
//            System.out.println("Mã QR đã được tạo thành công!");
//
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//        }
        qrCode.setAnhQR(filePath);
//        this.qrRepo.them(qrCode);
        return qrCode;

    }

    private void lamMoi() {

        this.txtTen.setText("");
        this.cbbGiay.setSelectedIndex(0);
        this.cbbMauSac.setSelectedIndex(0);
        this.cbbHang.setSelectedIndex(0);
        this.txtSize.setText("");
        this.cbbCLDe.setSelectedIndex(0);
        this.cbbCLThan.setSelectedIndex(0);
        this.cbbLoaiGiay.setSelectedIndex(0);
        this.cbbDanhMuc.setSelectedIndex(0);
        this.txtSL.setText("");
        this.txtGiaNhap.setText("0");
        this.txtGiaBan.setText("0");
        this.rdoConHang.setSelected(true);
        this.txtMoTa.setText("");
        this.lblAnh.setIcon(null);
        this.lblAnh.setText("image");
        this.btnThem.setEnabled(true);
        this.btnSua.setEnabled(false);
        this.btnXoa.setEnabled(false);
        this.loadTable();
        this.loadCBB();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnCL = new javax.swing.JTabbedPane();
        pnSPCT = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTen = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbbGiay = new javax.swing.JComboBox<>();
        lblMauSac = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbbHang = new javax.swing.JComboBox<>();
        lblSize = new javax.swing.JLabel();
        txtSize = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbbCLThan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbbCLDe = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        cbbDanhMuc = new javax.swing.JComboBox<>();
        cbbLoaiGiay = new javax.swing.JComboBox<>();
        lblSL = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        lblGiaNhap = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        lblDonViTinh = new javax.swing.JLabel();
        lblGiaban = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rdoConHang = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();
        rdoKhongHoatDong = new javax.swing.JRadioButton();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        lblImage = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        btnin = new javax.swing.JButton();
        btndoc = new javax.swing.JButton();
        excel = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cbbSearchSP = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtSearchTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbbSearchLoaiGiay = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtMaCTGiay = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTSP = new javax.swing.JTable();
        pnTTSP = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnLamMoi1 = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTenTT = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        rdoDM = new javax.swing.JRadioButton();
        rdoGiay = new javax.swing.JRadioButton();
        rdoHang = new javax.swing.JRadioButton();
        rdoLG = new javax.swing.JRadioButton();
        rdoMS = new javax.swing.JRadioButton();
        rdoSize = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTTSP = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCLDe = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCLThan = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        btnLamMoi2 = new javax.swing.JButton();
        btnThem2 = new javax.swing.JButton();
        btnSua2 = new javax.swing.JButton();
        btnXoa2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCL = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 750));

        pnCL.setBackground(new java.awt.Color(255, 255, 255));

        pnSPCT.setBackground(new java.awt.Color(255, 255, 255));
        pnSPCT.setPreferredSize(new java.awt.Dimension(1100, 620));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        lblTen.setText("Tên:");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jLabel1.setText("Giày:");

        lblMauSac.setText("Màu sắc:");

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Hãng:");

        cbbHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblSize.setText("Size:");

        txtSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeActionPerformed(evt);
            }
        });

        jLabel3.setText("CL Thân:");

        cbbCLThan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("CL Đế:");

        cbbCLDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Loại Giày:");

        lblDanhMuc.setText("Danh mục:");

        cbbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbLoaiGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblSL.setText("Số lượng:");

        lblGiaNhap.setText("Giá nhập:");

        lblDonViTinh.setText("VNĐ");

        lblGiaban.setText("Giá bán:");

        jLabel7.setText("VNĐ");

        jLabel6.setText("Trạng thái:");

        rdoConHang.setText("Còn hàng");
        rdoConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoConHangActionPerformed(evt);
            }
        });

        rdoHetHang.setText("Hết hàng");

        rdoKhongHoatDong.setText("Không hoạt động");

        lblMoTa.setText("Mô tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        lblImage.setText("Ảnh:");

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setText("Image");
        lblAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAnhMouseEntered(evt);
            }
        });

        btnin.setText("In dữ liệu");
        btnin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninActionPerformed(evt);
            }
        });

        btndoc.setText("Import Excel");
        btndoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndocActionPerformed(evt);
            }
        });

        excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                    .addComponent(btndoc, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbbCLDe, javax.swing.GroupLayout.Alignment.LEADING, 0, 370, Short.MAX_VALUE)
                            .addComponent(cbbCLThan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSize, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbHang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbGiay, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLoaiGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblGiaban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(excel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnin, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSL)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(64, 64, 64))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoConHang)
                                .addGap(18, 18, 18)
                                .addComponent(rdoHetHang)
                                .addGap(18, 18, 18)
                                .addComponent(rdoKhongHoatDong)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblImage)
                                .addGap(40, 40, 40)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTen)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDanhMuc)
                            .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbbGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSL)
                            .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMauSac)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaNhap)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDonViTinh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaban)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSize)
                                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(rdoConHang)
                                    .addComponent(rdoHetHang)
                                    .addComponent(rdoKhongHoatDong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbbCLThan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblMoTa)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(cbbCLDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(cbbLoaiGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblImage))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btndoc)
                                            .addComponent(excel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnin)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel8.setText("Sản phẩm:");

        cbbSearchSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Tên:");

        txtSearchTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTenKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchTenKeyReleased(evt);
            }
        });

        jLabel11.setText("Loại giày:");

        cbbSearchLoaiGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Mã CT giày:");

        txtMaCTGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCTGiayActionPerformed(evt);
            }
        });
        txtMaCTGiay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaCTGiayKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbbSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchTen, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbSearchLoaiGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaCTGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbbSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtSearchTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbbSearchLoaiGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtMaCTGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Giày", "Tên CT Giày", "Giày", "Màu", "Hãng", "Size", "Chất liệu thân", "Chất liệu đế", "Loại giày", "Danh mục", "Số lượng", "Giá nhập", "Giá bán", "Ảnh", "Trạng thái", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTSP);

        javax.swing.GroupLayout pnSPCTLayout = new javax.swing.GroupLayout(pnSPCT);
        pnSPCT.setLayout(pnSPCTLayout);
        pnSPCTLayout.setHorizontalGroup(
            pnSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSPCTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        pnSPCTLayout.setVerticalGroup(
            pnSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSPCTLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pnCL.addTab("Sản phẩm chi tiết", pnSPCT);

        pnTTSP.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thuộc tính sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLamMoi1.setText("Làm mới");
        btnLamMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi1ActionPerformed(evt);
            }
        });

        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnSua1.setText("Sửa");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(btnThem1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLamMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Tên:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTenTT, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rdoDM);
        rdoDM.setText("Danh mục");
        rdoDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDMActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoGiay);
        rdoGiay.setText("Giày");
        rdoGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGiayActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHang);
        rdoHang.setText("Hãng");
        rdoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHangActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoLG);
        rdoLG.setText("Loại Giày");
        rdoLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLGActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoMS);
        rdoMS.setText("Màu sắc");
        rdoMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMSActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoSize);
        rdoSize.setText("Size");
        rdoSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rdoLG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoDM))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdoGiay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoMS, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdoHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoSize, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDM)
                    .addComponent(rdoGiay)
                    .addComponent(rdoHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoLG)
                    .addComponent(rdoMS)
                    .addComponent(rdoSize))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tblTTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Thuộc tính 1"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTTSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTTSP);

        javax.swing.GroupLayout pnTTSPLayout = new javax.swing.GroupLayout(pnTTSP);
        pnTTSP.setLayout(pnTTSPLayout);
        pnTTSPLayout.setHorizontalGroup(
            pnTTSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnTTSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        pnTTSPLayout.setVerticalGroup(
            pnTTSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTTSPLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnCL.addTab("Thuộc tính của sản phẩm", pnTTSP);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chất liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel13.setText("Chất liệu đế:");

        jLabel14.setText("Chất liệu thân:");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLamMoi2.setText("Làm mới");
        btnLamMoi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi2ActionPerformed(evt);
            }
        });

        btnThem2.setText("Thêm");
        btnThem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem2ActionPerformed(evt);
            }
        });

        btnSua2.setText("Sửa");
        btnSua2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua2ActionPerformed(evt);
            }
        });

        btnXoa2.setText("Xóa");
        btnXoa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(btnThem2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLamMoi2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCLDe)
                    .addComponent(txtCLThan, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 443, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCLDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCLThan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblCL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Chất liệu đế", "Chất liệu thân"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCLMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCL);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnCL.addTab("Chất liệu", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCL)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCL)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        lamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed


    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        ChiTietGiay ctg = this.getData();
//        QR qr = createQRCode(ctg);

//        ctg.setQr(qr.getId());
        QR qr = createQRCode(ctg);
        qrRepo.them(qr);
        ctg.setQr(qr.getId());

        QR qrs = qrRepo.findByQR(qr.getAnhQR());
        String text = " Mã CTGiay: " + ctg.getMaCTGiay() + "\n Tên: " + ctg.getTenCTGiay() + "\n Giày: " + this.giayRepo.findByName(ctg.getGiay()).getTenGiay() + "\n Màu Sắc: " + this.msRepo.findById(ctg.getMs()).getTenMS() + "\n Hãng: " + this.hangRepo.findById(ctg.getHang()).getTenHang() + "\n Chất liệu: " + this.clRepo.findById(ctg.getCl()).getChatLieuThan() + ", " + this.clRepo.findById(ctg.getCl()).getChatLieuDe() + "\n Loại giày: " + this.lgRepo.findById(ctg.getLoaiGiay()).getTenLoaiGiay() + "\n Danh mục: " + this.dmRepo.findByName(ctg.getDanhMuc()).getTenDanhMuc(); // Nội dung mã QR
//        String filePath = "D:\\FPT Polytechnic\\project\\QR\\SPCT_" + ctg.getId() + ".png";
        if (ctg == null) {
            return;
        }

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
//            String outputFile = "D:\\FPT Polytechnic\\DA1\\PhanMemBanHang\\QRCode\\"+maSP+".png";
            Path path = FileSystems.getDefault().getPath(qrs.getAnhQR());
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        } catch (Exception e) {
            e.printStackTrace();

        }
        ctg.setQr(qrs.getId());
        this.repo.them(ctg);
        this.lamMoi();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        ChiTietGiay ctg = this.getData();
        if (ctg == null) {
            return;
        }

        repo.sua(ctg);
        this.lamMoi();
        JOptionPane.showMessageDialog(this, "Sửa thành công");

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = this.tblCTSP.getSelectedRow();
        if (row == -1) {
            return;
        }
        UUID id = repo.findByQR(this.tblCTSP.getValueAt(row, 0).toString());

        int choose = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }

        repo.xoa(id);
        lamMoi();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSizeActionPerformed

    private void rdoConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoConHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoConHangActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.lblAnh.setText("");
            File f = chooser.getSelectedFile();
            image = f.getAbsolutePath();
            this.lblAnh.setIcon(load(image, 97, 100));

        }
        this.lblAnh.setToolTipText(image);

    }//GEN-LAST:event_lblAnhMouseClicked

    private void txtMaCTGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCTGiayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCTGiayActionPerformed

    private void lblAnhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseEntered

    }//GEN-LAST:event_lblAnhMouseEntered

    private void tblCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTSPMouseClicked
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
        int row = this.tblCTSP.getSelectedRow();

        if (row == -1) {
            return;
        }

        String ma = tblCTSP.getValueAt(row, 0).toString();
        String ten = this.tblCTSP.getValueAt(row, 1).toString();
        String g = this.tblCTSP.getValueAt(row, 2).toString();
        String ms = this.tblCTSP.getValueAt(row, 3).toString();
        String h = this.tblCTSP.getValueAt(row, 4).toString();
        String sizeG = this.tblCTSP.getValueAt(row, 5).toString();
        String than = this.tblCTSP.getValueAt(row, 6).toString();
        String de = this.tblCTSP.getValueAt(row, 7).toString();
        String lGiay = this.tblCTSP.getValueAt(row, 8).toString();
        String dm = this.tblCTSP.getValueAt(row, 9).toString();
        String solg = this.tblCTSP.getValueAt(row, 10).toString();
        String gNhap = this.tblCTSP.getValueAt(row, 11).toString();
        String gBan = this.tblCTSP.getValueAt(row, 12).toString();
        String trangThai = this.tblCTSP.getValueAt(row, 14).toString();
        if (trangThai.equals("Còn hàng")) {
            this.rdoConHang.setSelected(true);
        } else if (trangThai.equals("Hết hàng")) {
            this.rdoHetHang.setSelected(true);
        } else {
            this.rdoKhongHoatDong.setSelected(true);
        }
        String moTa = this.tblCTSP.getValueAt(row, 15).toString();
        String anh = this.tblCTSP.getValueAt(row, 13).toString();;

        this.txtTen.setText(ten);
        this.cbbGiay.setSelectedItem(g);
        this.cbbMauSac.setSelectedItem(ms);
        this.cbbHang.setSelectedItem(h);
        this.txtSize.setText(sizeG);
        this.cbbCLDe.setSelectedItem(de);
        this.cbbCLThan.setSelectedItem(than);
        this.cbbLoaiGiay.setSelectedItem(lGiay);
        this.cbbDanhMuc.setSelectedItem(dm);
        this.txtSL.setText(solg);
        this.txtGiaNhap.setText(gNhap);
        this.txtGiaBan.setText(gBan);
        this.txtMoTa.setText(moTa);
        if (anh != null || !anh.trim().equals("")) {
            this.lblAnh.setText("");
            lblAnh.setIcon(load(anh, 97, 100));
        }

        image = anh;


    }//GEN-LAST:event_tblCTSPMouseClicked

    private void btnLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi1ActionPerformed
        lamMoi2();
    }//GEN-LAST:event_btnLamMoi1ActionPerformed

    private void lamMoi2() {

        this.txtTenTT.setText("");

        this.btnThem1.setEnabled(true);
        this.btnSua1.setEnabled(false);
        this.btnXoa1.setEnabled(false);
    }

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        String ten = this.txtTenTT.getText().trim();

        if (ten.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống");
            return;
        }

        if (rdoDM.isSelected()) {
            DanhMucSanPham dm = new DanhMucSanPham();
            dm.setTenDanhMuc(ten);
            dmRepo.them(dm);
            loadTableDM();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoGiay.isSelected()) {
            Giay g = new Giay();
            g.setTenGiay(ten);
            giayRepo.them(g);
            loadTableG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoHang.isSelected()) {
            Hang h = new Hang();
            h.setTenHang(ten);
            hangRepo.them(h);
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoLG.isSelected()) {
            LoaiGiay lg = new LoaiGiay();
            lg.setTenLoaiGiay(ten);
            lgRepo.them(lg);
            loadTableLG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoMS.isSelected()) {
            MauSac ms = new MauSac();
            ms.setTenMS(ten);
            msRepo.them(ms);
            loadTableMS();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else {
            boolean isNumber = isNumeric(ten);
            if (isNumber) {
                int number = Integer.parseInt(ten);
                if (number <= 0) {
                    JOptionPane.showMessageDialog(this, "Size cần nhập số dương");
                    return;
                }
                Size s = new Size();
                s.setSizeGiay(number);
                sRepo.them(s);
                loadTableS();
                lamMoi2();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Size cần nhập 1 số");
                return;
            }

        }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        String ten = this.txtTenTT.getText().trim();

        if (ten.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống");
            return;
        }

        int row = tblTTSP.getSelectedRow();

        if (row == -1) {
            return;
        }

        int ma = Integer.parseInt(tblTTSP.getValueAt(row, 0).toString());

        if (rdoDM.isSelected()) {
            DanhMucSanPham dm = dmRepo.findByName(ma);
            dm.setTenDanhMuc(ten);
            dmRepo.sua(dm);
            loadTableDM();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else if (rdoGiay.isSelected()) {
            Giay g = giayRepo.findByName(ma);
            g.setTenGiay(ten);
            giayRepo.sua(g);
            loadTableG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else if (rdoHang.isSelected()) {
            Hang h = hangRepo.findByMa(ma);
            h.setTenHang(ten);
            hangRepo.sua(h);
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else if (rdoLG.isSelected()) {
            LoaiGiay lg = new LoaiGiay();
            lg.setTenLoaiGiay(ten);
            lgRepo.sua(lg);
            loadTableLG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else if (rdoMS.isSelected()) {
            MauSac ms = msRepo.findByMa(ma);
            ms.setTenMS(ten);
            msRepo.sua(ms);
            loadTableMS();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else {
            boolean isNumber = isNumeric(ten);
            if (isNumber) {
                int number = Integer.parseInt(ten);
                if (number <= 0) {
                    JOptionPane.showMessageDialog(this, "Size cần nhập số dương");
                    return;
                }
                Size s = sRepo.findByMa(ma);
                s.setSizeGiay(number);
                sRepo.sua(s);
                loadTableS();
                lamMoi2();
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Size cần nhập 1 số");
                return;
            }

        }
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed

        int row = tblTTSP.getSelectedRow();

        if (row == -1) {
            return;
        }

        int ma = Integer.parseInt(tblTTSP.getValueAt(row, 0).toString());
        int choose = JOptionPane.showConfirmDialog(this, "Có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }

        if (rdoDM.isSelected()) {
            DanhMucSanPham dm = dmRepo.findByName(ma);
            dmRepo.xoa(dm.getId());
            loadTableDM();
            lamMoi2();

            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoGiay.isSelected()) {
            Giay g = giayRepo.findByName(ma);
            giayRepo.xoa(g.getId());
            loadTableG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoHang.isSelected()) {
            Hang h = hangRepo.findByMa(ma);
            hangRepo.xoa(h.getId());
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoLG.isSelected()) {
            LoaiGiay lg = lgRepo.findByMa(ma);
            lgRepo.xoa(lg.getId());
            loadTableLG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoMS.isSelected()) {
            MauSac ms = msRepo.findByMa(ma);
            msRepo.xoa(ms.getId());
            loadTableMS();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else {

            Size s = sRepo.findByMa(ma);
            sRepo.xoa(s.getId());
            loadTableS();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");

        }
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void rdoLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLGActionPerformed
        lamMoi2();
        this.rdoLG.setSelected(true);
        loadTableLG();
    }//GEN-LAST:event_rdoLGActionPerformed

    private void rdoDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDMActionPerformed
        lamMoi2();
        this.rdoDM.setSelected(true);
        loadTableDM();
    }//GEN-LAST:event_rdoDMActionPerformed

    private void rdoGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGiayActionPerformed
        lamMoi2();
        this.rdoGiay.setSelected(true);
        loadTableG();
    }//GEN-LAST:event_rdoGiayActionPerformed

    private void rdoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHangActionPerformed
        lamMoi2();
        this.rdoHang.setSelected(true);
        loadTableH();
    }//GEN-LAST:event_rdoHangActionPerformed

    private void rdoMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMSActionPerformed
        lamMoi2();
        this.rdoMS.setSelected(true);
        loadTableMS();
    }//GEN-LAST:event_rdoMSActionPerformed

    private void rdoSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSizeActionPerformed
        lamMoi2();
        this.rdoSize.setSelected(true);
        loadTableS();
    }//GEN-LAST:event_rdoSizeActionPerformed

    private void tblTTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTTSPMouseClicked
        this.btnThem1.setEnabled(false);
        this.btnSua1.setEnabled(true);
        this.btnXoa1.setEnabled(true);

        int row = tblTTSP.getSelectedRow();

        if (row == -1) {
            return;
        }

        String ten = tblTTSP.getValueAt(row, 1).toString();

        this.txtTenTT.setText(ten);


    }//GEN-LAST:event_tblTTSPMouseClicked

    private void btnLamMoi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi2ActionPerformed
        lamMoi3();
    }//GEN-LAST:event_btnLamMoi2ActionPerformed

    private void lamMoi3() {
        txtCLDe.setText("");
        txtCLThan.setText("");
        btnThem2.setEnabled(true);
        btnXoa2.setEnabled(false);
        btnSua2.setEnabled(false);
        loadTableCL();
    }

    private void btnThem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem2ActionPerformed
        String de = txtCLDe.getText().trim();
        String than = txtCLThan.getText().trim();

        if (de.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống chất liệu đế");
            return;
        }

        if (than.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống chất liệu thân");
            return;
        }

        ChatLieu cl = new ChatLieu();
        cl.setChatLieuDe(de);
        cl.setChatLieuThan(than);
        clRepo.themCL(cl);
        lamMoi3();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnThem2ActionPerformed

    private void btnSua2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua2ActionPerformed
        int row = tblCL.getSelectedRow();

        if (row == -1) {
            return;
        }

        int ma = Integer.parseInt(tblCL.getValueAt(row, 0).toString());
        String de = txtCLDe.getText().trim();
        String than = txtCLThan.getText().trim();

        if (de.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống chất liệu đế");
            return;
        }

        if (than.length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống chất liệu thân");
            return;
        }

        ChatLieu cl = clRepo.findByMa(ma);
        cl.setChatLieuDe(de);
        cl.setChatLieuThan(than);
        clRepo.suaCL(cl);
        lamMoi3();
        JOptionPane.showMessageDialog(this, "Sửa thành công");
    }//GEN-LAST:event_btnSua2ActionPerformed

    private void btnXoa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa2ActionPerformed
        int row = tblCL.getSelectedRow();

        if (row == -1) {
            return;
        }

        int ma = Integer.parseInt(tblCL.getValueAt(row, 0).toString());
        int choose = JOptionPane.showConfirmDialog(this, "Có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        ChatLieu cl = clRepo.findByMa(ma);
        clRepo.xoaCL(cl.getId());
        lamMoi3();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
    }//GEN-LAST:event_btnXoa2ActionPerformed

    private void tblCLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCLMouseClicked
        int row = tblCL.getSelectedRow();

        if (row == -1) {
            return;
        }

        int ma = Integer.parseInt(tblCL.getValueAt(row, 0).toString());
        String de = tblCL.getValueAt(row, 1).toString();
        String than = tblCL.getValueAt(row, 2).toString();

        txtCLDe.setText(de);
        txtCLThan.setText(than);
        this.btnThem2.setEnabled(false);
        this.btnSua2.setEnabled(true);
        this.btnXoa2.setEnabled(true);
    }//GEN-LAST:event_tblCLMouseClicked

    private void txtSearchTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTenKeyPressed

    }//GEN-LAST:event_txtSearchTenKeyPressed

    private void txtSearchTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTenKeyReleased
        DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

        dtm.setRowCount(0);

        List<ChiTietGiay> ds = repo.getAllByTen(txtSearchTen.getText());

        if (ds == null) {
            return;
        }

        for (ChiTietGiay d : ds) {
            Object[] data = {
                d.getMaCTGiay(),
                d.getTenCTGiay(),
                this.giayRepo.findByName(d.getGiay()).getTenGiay(),
                this.msRepo.findById(d.getMs()).getTenMS(),
                this.hangRepo.findById(d.getHang()).getTenHang(),
                this.sRepo.findById(d.getSize()).getSizeGiay(),
                this.clRepo.findById(d.getCl()).getChatLieuThan(),
                this.clRepo.findById(d.getCl()).getChatLieuDe(),
                this.lgRepo.findById(d.getLoaiGiay()).getTenLoaiGiay(),
                this.dmRepo.findByName(d.getDanhMuc()).getTenDanhMuc(),
                d.getSoLuong(),
                d.getGiaNhap(),
                d.getGiaBan(),
                d.getAnh(),
                d.getTrangThai() == 0 ? "Còn hàng" : d.getTrangThai() == 1 ? "Hết hàng" : "Không hoạt động",
                d.getMoTa()
            };

            dtm.addRow(data);
        }
    }//GEN-LAST:event_txtSearchTenKeyReleased

    private void txtMaCTGiayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaCTGiayKeyReleased
        DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

        dtm.setRowCount(0);

        List<ChiTietGiay> ds = repo.getAllByMa(txtMaCTGiay.getText());

        if (ds == null) {
            return;
        }

        for (ChiTietGiay d : ds) {
            Object[] data = {
                d.getMaCTGiay(),
                d.getTenCTGiay(),
                this.giayRepo.findByName(d.getGiay()).getTenGiay(),
                this.msRepo.findById(d.getMs()).getTenMS(),
                this.hangRepo.findById(d.getHang()).getTenHang(),
                this.sRepo.findById(d.getSize()).getSizeGiay(),
                this.clRepo.findById(d.getCl()).getChatLieuThan(),
                this.clRepo.findById(d.getCl()).getChatLieuDe(),
                this.lgRepo.findById(d.getLoaiGiay()).getTenLoaiGiay(),
                this.dmRepo.findByName(d.getDanhMuc()).getTenDanhMuc(),
                d.getSoLuong(),
                d.getGiaNhap(),
                d.getGiaBan(),
                d.getAnh(),
                d.getTrangThai() == 0 ? "Còn hàng" : d.getTrangThai() == 1 ? "Hết hàng" : "Không hoạt động",
                d.getMoTa()
            };

            dtm.addRow(data);
        }
    }//GEN-LAST:event_txtMaCTGiayKeyReleased

    private void btninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninActionPerformed
        // TODO add your handling code here:
        List<ChiTietGiay> list = new ArrayList<>();
        ChatLieu listcl = new ChatLieu();

        try {
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet = wordkbook.createSheet("danhsach");
            XSSFRow row = null;
            Cell cell = null;
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

            list = repo.getAll();
            for (int i = 0; i < list.size(); i++) {
                //Modelbook book =arr.get(i);
                row = sheet.createRow(i);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(list.get(i).getId().toString());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getMaCTGiay());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(list.get(i).getTenCTGiay());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(list.get(i).getGiay().toString());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(list.get(i).getMs().toString());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(list.get(i).getHang().toString());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(list.get(i).getSize().toString());
                // chưa chuyền đc chất thiệu thân
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(list.get(i).getCl().toString());
                // chưa chuyền đc chất thiệu đế
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(list.get(i).getCl().toString());
                //
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(list.get(i).getLoaiGiay().toString());

                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(list.get(i).getDanhMuc().toString());

                cell = row.createCell(12, CellType.NUMERIC);
                cell.setCellValue(list.get(i).getSoLuong());

                cell = row.createCell(13, CellType.NUMERIC);
                cell.setCellValue(list.get(i).getGiaNhap());

                cell = row.createCell(14, CellType.NUMERIC);
                cell.setCellValue(list.get(i).getGiaNhap());

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(list.get(i).getAnh());

                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(list.get(i).getTrangThai());

                cell = row.createCell(17, CellType.NUMERIC);
                cell.setCellValue(list.get(i).getMoTa());

            }

            File f = new File("D://danhsachSP.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "in thanh cong D:\\danhsach");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi mo file");
        }

    }//GEN-LAST:event_btninActionPerformed

    private void excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_excelActionPerformed

    private void btndocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndocActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tblCTSP.getModel();
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
                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    XSSFCell excelLineNum = excelRow.getCell(1);
                    XSSFCell excelItemName = excelRow.getCell(2);
                    XSSFCell excelDescription = excelRow.getCell(3);
                    XSSFCell excelServiceDuration = excelRow.getCell(4);
                    XSSFCell excelQuantity = excelRow.getCell(5);
                    XSSFCell excelQuantity1 = excelRow.getCell(6);
                    XSSFCell excelQuantity2 = excelRow.getCell(7);
                    XSSFCell excelQuantity3 = excelRow.getCell(8);
                    XSSFCell excelQuantity4 = excelRow.getCell(9);
                    XSSFCell excelQuantity5 = excelRow.getCell(10);
                    XSSFCell excelQuantity6 = excelRow.getCell(11);
                    XSSFCell excelQuantity7 = excelRow.getCell(12);
                    XSSFCell excelQuantity8 = excelRow.getCell(12);
                    XSSFCell excelQuantity9 = excelRow.getCell(14);
                    XSSFCell excelQuantity10 = excelRow.getCell(15);
                    XSSFCell excelQuantity11 = excelRow.getCell(16);
                    model.addRow(new Object[]{excelLineNum, excelItemName, excelDescription, excelServiceDuration, excelQuantity, excelQuantity1, excelQuantity2, excelQuantity3, excelQuantity4, excelQuantity5, excelQuantity6, excelQuantity7, excelQuantity8, excelQuantity9, excelQuantity10, excelQuantity11});
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

    private Icon load(String linkImage, int k, int m) {/*linkImage là tên icon, k kích thước chiều rộng mình muốn,m chiều dài và hàm này trả về giá trị là 1 icon.*/
        try {
            BufferedImage image = ImageIO.read(new File(linkImage));//đọc ảnh dùng BufferedImage

            int x = k;
            int y = m;
            int ix = image.getWidth();
            int iy = image.getHeight();
            int dx = 0, dy = 0;

            if (x / y > ix / iy) {
                dy = y;
                dx = dy * ix / iy;
            } else {
                dx = x;
                dy = dx * iy / ix;
            }

            return new ImageIcon(image.getScaledInstance(dx, dy,
                    image.SCALE_SMOOTH));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoi1;
    private javax.swing.JButton btnLamMoi2;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnSua2;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnThem2;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JButton btnXoa2;
    private javax.swing.JButton btndoc;
    private javax.swing.JButton btnin;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbCLDe;
    private javax.swing.JComboBox<String> cbbCLThan;
    private javax.swing.JComboBox<String> cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbGiay;
    private javax.swing.JComboBox<String> cbbHang;
    private javax.swing.JComboBox<String> cbbLoaiGiay;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbSearchLoaiGiay;
    private javax.swing.JComboBox<String> cbbSearchSP;
    private javax.swing.JTextField excel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDonViTinh;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblGiaban;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMauSac;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblSL;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblTen;
    private javax.swing.JTabbedPane pnCL;
    private javax.swing.JPanel pnSPCT;
    private javax.swing.JPanel pnTTSP;
    private javax.swing.JRadioButton rdoConHang;
    private javax.swing.JRadioButton rdoDM;
    private javax.swing.JRadioButton rdoGiay;
    private javax.swing.JRadioButton rdoHang;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JRadioButton rdoKhongHoatDong;
    private javax.swing.JRadioButton rdoLG;
    private javax.swing.JRadioButton rdoMS;
    private javax.swing.JRadioButton rdoSize;
    private javax.swing.JTable tblCL;
    private javax.swing.JTable tblCTSP;
    private javax.swing.JTable tblTTSP;
    private javax.swing.JTextField txtCLDe;
    private javax.swing.JTextField txtCLThan;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaCTGiay;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSearchTen;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenTT;
    // End of variables declaration//GEN-END:variables
}
