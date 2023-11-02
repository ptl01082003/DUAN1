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
import domainModels.Hang;
import domainModels.LoaiGiay;
import domainModels.MauSac;
import domainModels.QR;
import domainModels.Size;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
import repositories.HangRepository;
import repositories.LoaiGiayRepository;
import repositories.MauSacRepository;
import repositories.QrRepository;
import repositories.SizeRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.Timer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import service.BanHangService;
import service.CTGiayService;
import utilities.JDBCHelper;
import viewModels.SPCTGiayViewModel;

public class pnSanPham extends javax.swing.JPanel {

    private String image, excelPath;
    private CTGiayRepository ctRepo;
    private CTGiayService repo;
    private ChatLieuRepository clRepo;
    private DanhMucSPRepository dmRepo;
    private HangRepository hangRepo;
    private LoaiGiayRepository lgRepo;
    private MauSacRepository msRepo;
    private SizeRepository sRepo;
    private QrRepository qrRepo;
    private DefaultTableModel dtm;
    private DefaultComboBoxModel<String> dtmcbb;
    private List<ChiTietGiay> ds;

    public pnSanPham() {
        initComponents();

        this.ctRepo = new CTGiayRepository();
        this.repo = new CTGiayService();
        this.dmRepo = new DanhMucSPRepository();
        this.clRepo = new ChatLieuRepository();
        this.hangRepo = new HangRepository();
        this.lgRepo = new LoaiGiayRepository();
        this.msRepo = new MauSacRepository();
        this.sRepo = new SizeRepository();
        this.qrRepo = new QrRepository();
        ds = new ArrayList<>();

        ButtonGroup group = new ButtonGroup();
        group.add(this.rdoConHang);
        group.add(this.rdoHetHang);
        group.add(this.rdoKhongHoatDong);

        //set border image
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        this.lblAnh.setBorder(blackline);

//        Load CBB
//        loadCBB();
        //Load table
//        this.loadTable();
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
        this.txtSize.setDocument(new NumberOnlyDocument());
        this.txtSL.setDocument(new NumberOnlyDocument());
        this.txtGiaNhap.setDocument(new NumberOnlyDocument());
        this.txtGiaBan.setDocument(new NumberOnlyDocument());
        this.txtGia.setDocument(new NumberOnlyDocument());
        
        tblCTSP.getColumnModel().getColumn(0).setMinWidth(0);
    tblCTSP.getColumnModel().getColumn(0).setMaxWidth(0);
    tblCTSP.getColumnModel().getColumn(0).setWidth(0);

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
        dtm = (DefaultTableModel) this.tblCTSP.getModel();

        dtm.setRowCount(0);

        List<SPCTGiayViewModel> ds = repo.getAll();

        if (ds == null) {
            return;
        }

        for (SPCTGiayViewModel d : ds) {
            Object[] data = {
                d.getId(),
                d.getMaCTGiay(),
                d.getTenCTGiay(),
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
        dtm = (DefaultTableModel) tblTTSP.getModel();

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

    private void loadTableH() {
        dtm = (DefaultTableModel) tblTTSP.getModel();

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
        dtm = (DefaultTableModel) tblTTSP.getModel();

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
        dtm = (DefaultTableModel) tblTTSP.getModel();

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
        dtm = (DefaultTableModel) tblTTSP.getModel();

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
        dtm = (DefaultTableModel) tblCL.getModel();

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
        dtmcbb = (DefaultComboBoxModel<String>) this.cbbMauSac.getModel();
        List<MauSac> listMS = msRepo.getAll();

        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (MauSac o : listMS) {
            dtmcbb.addElement(o.getTenMS());
        }

        dtmcbb = (DefaultComboBoxModel<String>) this.cbbHang.getModel();
        List<Hang> listHang = hangRepo.getAll();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (Hang o : listHang) {
            dtmcbb.addElement(o.getTenHang());
        }

        dtmcbb = (DefaultComboBoxModel<String>) this.cbbCLDe.getModel();
        List<String> listCLDe = clRepo.getAllCLDe();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (String de : listCLDe) {
            dtmcbb.addElement(de);

        }

        dtmcbb = (DefaultComboBoxModel<String>) this.cbbCLThan.getModel();
        List<String> listCLThan = clRepo.getAllCLThan();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (String than : listCLThan) {
            dtmcbb.addElement(than);
        }

        List<LoaiGiay> listLoaiGiay = lgRepo.getAll();
        dtmcbb = (DefaultComboBoxModel<String>) this.cbbLoaiGiay.getModel();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (LoaiGiay lgiay : listLoaiGiay) {
            dtmcbb.addElement(lgiay.getTenLoaiGiay());
        }

        dtmcbb = (DefaultComboBoxModel<String>) this.cbbSearchLoaiGiay.getModel();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (LoaiGiay lgiay : listLoaiGiay) {
            dtmcbb.addElement(lgiay.getTenLoaiGiay());
        }

        dtmcbb = (DefaultComboBoxModel<String>) this.cbbDanhMuc.getModel();
        List<DanhMucSanPham> listDM = dmRepo.getAll();
        dtmcbb.removeAllElements();
        dtmcbb.addElement(" ");
        for (DanhMucSanPham dmsp : listDM) {
            dtmcbb.addElement(dmsp.getTenDanhMuc());
        }
    }

    private ChiTietGiay getData() {
        ChiTietGiay ctg = new ChiTietGiay();
        String ten = this.txtTen.getText().trim();
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

        if (ms.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn màu sắc");
            return null;
        }

         if (h.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn hãng");
            return null;
        }
        
        if (sizeG.trim().length() == 0 || sizeG == null) {
            JOptionPane.showMessageDialog(this, "Không để trống Size");
            return null;
        }

       

        if (than.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn chất liệu thân");
            return null;
        }

        if (de.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn chất liệu đế");
            return null;
        }

        if (lGiay.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn loại giày");
            return null;
        }

        if (dm.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn danh mục");
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

            if (size > 50 || size < 16) {
                JOptionPane.showMessageDialog(this, "Size phải từ 16-50");
                return null;
            }
            Size s = new Size();
            s.setSizeGiay(size);
            sRepo.them(s);
            ctg.setSize(sRepo.findByName(size).getId());
        } else {
            if (size > 50 || size < 16) {
                JOptionPane.showMessageDialog(this, "Size phải từ 16-50");
                return null;
            }
            ctg.setSize(this.sRepo.findByName(size).getId());
        }
        //chat liệu
        int existCL = (int) this.clRepo.checkTenCL(de, than);
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

        //chuyển đổi
        int sl = Integer.parseInt(solg);

        double giaNhap = Double.parseDouble(gNhap);
        double giaBan = Double.parseDouble(gBan);

        int number2 = sl;
        if (number2 <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng cần nhập số dương");
            return null;
        }

        double number3 = giaNhap;
        if (number3 <= 0) {
            JOptionPane.showMessageDialog(this, "Giá nhập cần nhập số dương");
            return null;
        }

        double number4 = giaBan;
        if (number4 <= 0) {
            JOptionPane.showMessageDialog(this, "Giá bán cần nhập số dương");
            return null;
        }

        if (this.lblAnh.getIcon() == null) {
            image = "C:\\Users\\ACER\\Downloads\\store.png";

        }
        System.out.println("getdata: " + image);
        ctg.setTenCTGiay(ten);
        ctg.setMs(this.msRepo.findByName(ms).getId());
        ctg.setHang(this.hangRepo.findByName(h).getId());

        ctg.setLoaiGiay(this.lgRepo.findByName(lGiay).getId());
        ctg.setDanhMuc(this.dmRepo.findByName(dm).getId());
        ctg.setSoLuong(number2);
        ctg.setGiaNhap(number3);
        ctg.setGiaBan(number4);
        ctg.setAnh(image);
        ctg.setMoTa(moTa);
        ctg.setTrangThai(trangThai);

        return ctg;
    }

    private void lamMoi() {
        this.loadCBB();
        this.txtTen.setText("");
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

        lblAnh.setToolTipText("Image");
    }

    private void loadSearchTable(List<ChiTietGiay> list) {
        dtm = (DefaultTableModel) tblCTSP.getModel();
        dtm.setRowCount(0);
        for (ChiTietGiay d : list) {
            Object[] data = {
                d.getId(),
                d.getMaCTGiay(),
                d.getTenCTGiay(),
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
            ds.add(d);

            dtm.addRow(data);
        }
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
        btnImportExcel = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSearchTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbbSearchLoaiGiay = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtSearchMaCTGiay = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cbbGia = new javax.swing.JComboBox<>();
        txtGia = new javax.swing.JTextField();
        lblDK = new javax.swing.JLabel();
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
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btnImportExcel.setText("Import Excel");
        btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportExcelActionPerformed(evt);
            }
        });

        btnExportExcel.setText("Export Excel");
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImportExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbbCLDe, javax.swing.GroupLayout.Alignment.LEADING, 0, 370, Short.MAX_VALUE)
                    .addComponent(cbbCLThan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSize, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbHang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbLoaiGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGiaban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdoConHang)
                        .addGap(18, 18, 18)
                        .addComponent(rdoHetHang)
                        .addGap(18, 18, 18)
                        .addComponent(rdoKhongHoatDong))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblImage)
                        .addGap(18, 18, 18)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTen)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDanhMuc)
                            .addComponent(cbbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSL)
                                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblGiaNhap)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDonViTinh))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblGiaban)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(rdoConHang)
                                    .addComponent(rdoHetHang)
                                    .addComponent(rdoKhongHoatDong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMoTa)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblImage))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMauSac)
                                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSize)
                                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(cbbCLThan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbbCLDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbbLoaiGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel10.setText("Tên:");

        txtSearchTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchTenActionPerformed(evt);
            }
        });
        txtSearchTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchTenKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchTenKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchTenKeyTyped(evt);
            }
        });

        jLabel11.setText("Loại giày:");

        cbbSearchLoaiGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSearchLoaiGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSearchLoaiGiayActionPerformed(evt);
            }
        });

        jLabel12.setText("Mã CT giày:");

        txtSearchMaCTGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchMaCTGiayActionPerformed(evt);
            }
        });
        txtSearchMaCTGiay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchMaCTGiayKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchMaCTGiayKeyReleased(evt);
            }
        });

        jLabel24.setText("Giá Bán:");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblDK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
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
                .addComponent(txtSearchMaCTGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSearchTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbbSearchLoaiGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtSearchMaCTGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDK))
        );

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Mã Giày", "Tên CT Giày", "Màu", "Hãng", "Size", "Chất liệu thân", "Chất liệu đế", "Loại giày", "Danh mục", "Số lượng", "Giá nhập", "Giá bán", "Ảnh", "Trạng thái", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
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
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoMS, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(rdoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoSize, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDM)
                    .addComponent(rdoHang)
                    .addComponent(rdoSize))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoLG)
                    .addComponent(rdoMS))
                .addContainerGap(85, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
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

        if (ctg == null) {
            return;
        }

        this.repo.them(ctg);
        this.lamMoi();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        int row = this.tblCTSP.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng muốn sửa");
        }
        ChiTietGiay ctg = this.repo.findByID(UUID.fromString(this.tblCTSP.getValueAt(row, 0).toString()));
        ChiTietGiay ctg2 = this.getData();
        if (ctg2 == null) {
            return;
        }
        ctg.setTenCTGiay(ctg2.getTenCTGiay());
        ctg.setMs(ctg2.getMs());
        ctg.setSize(ctg2.getSize());
        ctg.setCl(ctg2.getCl());
        ctg.setLoaiGiay(ctg2.getLoaiGiay());
        ctg.setDanhMuc(ctg2.getDanhMuc());
        ctg.setGiaNhap(ctg2.getGiaNhap());
        ctg.setGiaBan(ctg2.getGiaBan());
        ctg.setTrangThai(ctg2.getTrangThai());
        ctg.setMoTa(ctg2.getMoTa());
        ctg.setAnh(image);

        ctg.setSoLuong(ctg2.getSoLuong());
        ctg.setHang(ctg2.getHang());
        System.out.println("CTG2: " + image);
        int check = repo.sua(ctg);
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Sửa thất bại");
            return;
        }
        this.lamMoi();
        JOptionPane.showMessageDialog(this, "Sửa thành công");

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = this.tblCTSP.getSelectedRow();
        if (row == -1) {
            return;
        }
        UUID id = UUID.fromString(this.tblCTSP.getValueAt(row, 0).toString());

        int choose = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CLOSED_OPTION) {
            return;
        }
        int checkTrcXoa = repo.checkTruocXoa(id);
        if (checkTrcXoa == 0) {
            repo.xoa(id);
            lamMoi();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Không thể xóa vì sản phẩm đang tồn tại trong hóa đơn");
        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        this.repo.importExcel();
        lamMoi();
    }//GEN-LAST:event_btnImportExcelActionPerformed

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
        System.out.println(image);
        this.lblAnh.setToolTipText(image);

    }//GEN-LAST:event_lblAnhMouseClicked

    private void txtSearchMaCTGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchMaCTGiayActionPerformed
        this.txtSearchTen.setText("");
    }//GEN-LAST:event_txtSearchMaCTGiayActionPerformed

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

        String id = tblCTSP.getValueAt(row, 0).toString();
        String ma = tblCTSP.getValueAt(row, 1).toString();
        String ten = this.tblCTSP.getValueAt(row, 2).toString();
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
        Object value = this.tblCTSP.getValueAt(row, 13);
        String anh = (value != null) ? value.toString() : "";
        int giaNhap = (int) Double.parseDouble(gNhap);
        int giaBan = (int) Double.parseDouble(gBan);
        this.txtTen.setText(ten);
        this.cbbMauSac.setSelectedItem(ms);
        this.cbbHang.setSelectedItem(h);
        this.txtSize.setText(sizeG);
        this.cbbCLDe.setSelectedItem(de);
        this.cbbCLThan.setSelectedItem(than);
        this.cbbLoaiGiay.setSelectedItem(lGiay);
        this.cbbDanhMuc.setSelectedItem(dm);
        this.txtSL.setText(solg);
        this.txtGiaNhap.setText(String.valueOf(giaNhap));
        this.txtGiaBan.setText(String.valueOf(giaBan));
        this.txtMoTa.setText(moTa);
        if (anh != null || !anh.equals("") || anh.trim().length() != 0) {
            this.lblAnh.setText("");
            lblAnh.setIcon(load(anh, 97, 100));
        } else {
            this.lblAnh.setText("image");
            this.lblAnh.setIcon(null);
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
            int checkDM = dmRepo.checkTenCL(ten);
            if (checkDM != 0) {
                JOptionPane.showMessageDialog(this, "Tên danh mục đã tồn tại");
                return;
            }
            DanhMucSanPham dm = new DanhMucSanPham();
            dm.setTenDanhMuc(ten);
            dmRepo.them(dm);
            loadTableDM();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoHang.isSelected()) {
            int checHang = hangRepo.checkTen(ten);
            if (checHang != 0) {
                JOptionPane.showMessageDialog(this, "Tên hãng đã tồn tại");
                return;
            }
            Hang h = new Hang();
            h.setTenHang(ten);
            hangRepo.them(h);
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoLG.isSelected()) {
            int checkLg = lgRepo.checkTenCL(ten);
            if (checkLg != 0) {
                JOptionPane.showMessageDialog(this, "Tên loại giày đã tồn tại");
                return;
            }
            LoaiGiay lg = new LoaiGiay();
            lg.setTenLoaiGiay(ten);
            lgRepo.them(lg);
            loadTableLG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } else if (rdoMS.isSelected()) {
            int checkMS = msRepo.checkTen(ten);
            if (checkMS != 0) {
                JOptionPane.showMessageDialog(this, "Tên màu sắc đã tồn tại");
                return;
            }
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
                int checkSize = sRepo.checkSize(Integer.parseInt(ten));
                if (checkSize != 0) {
                    JOptionPane.showMessageDialog(this, "Size đã tồn tại");
                    return;
                }
                if (number > 50 || number < 16) {
                    JOptionPane.showMessageDialog(this, "Size phải từ 16-50");
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
        } else if (rdoHang.isSelected()) {
            Hang h = hangRepo.findByMa(ma);
            h.setTenHang(ten);
            hangRepo.sua(h);
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } else if (rdoLG.isSelected()) {
            LoaiGiay lg = lgRepo.findByMa(ma);
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
            int checkXoaDm = dmRepo.checkTruocXoa(dm.getId());
            if (checkXoaDm != 0) {
                JOptionPane.showMessageDialog(this, "Doanh mục này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa doanh mục này để xóa doanh mục này");
                return;
            }
            dmRepo.xoa(dm.getId());
            loadTableDM();
            lamMoi2();

            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoHang.isSelected()) {
            Hang h = hangRepo.findByMa(ma);
            int checkXoaH = hangRepo.checkTruocXoa(h.getId());
            if (checkXoaH != 0) {
                JOptionPane.showMessageDialog(this, "Hãng này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa hãng này để xóa hãng này");
                return;
            }
            hangRepo.xoa(h.getId());
            loadTableH();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoLG.isSelected()) {
            LoaiGiay lg = lgRepo.findByMa(ma);
            int checkXoaLg = lgRepo.checkTruocXoa(lg.getId());
            if (checkXoaLg != 0) {
                JOptionPane.showMessageDialog(this, "Loại giày này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa loại giày này để xóa loại giày này");
                return;
            }
            lgRepo.xoa(lg.getId());
            loadTableLG();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else if (rdoMS.isSelected()) {

            MauSac ms = msRepo.findByMa(ma);

            int checkXoa = msRepo.checkTruocXoa(ms.getId());
            if (checkXoa != 0) {
                JOptionPane.showMessageDialog(this, "Màu sắc này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa màu sắc này để xóa màu sắc này");
                return;
            }
            msRepo.xoa(ms.getId());
            loadTableMS();
            lamMoi2();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else {

            Size s = sRepo.findByMa(ma);
            int checkXoaS = sRepo.checkTruocXoa(s.getId());
            if (checkXoaS != 0) {
                JOptionPane.showMessageDialog(this, "Size này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa size này để xóa size này");
                return;
            }
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
        int checkXoa = clRepo.checkTruocXoa(cl.getId());
        if (checkXoa == 0) {
            clRepo.xoaCL(cl.getId());
            lamMoi3();
            JOptionPane.showMessageDialog(this, "Xóa thành công");

        } else {
            JOptionPane.showMessageDialog(this, "Chất liệu này hiện đang liên kết với bảng chi tiết giày.\n Vui lòng xóa CT giày chứa doanh mục này để xóa doanh mục này");
        }
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
        this.txtSearchMaCTGiay.setText("");
    }//GEN-LAST:event_txtSearchTenKeyPressed

    private void txtSearchTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTenKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

            dtm.setRowCount(0);

            List<SPCTGiayViewModel> ds = repo.getALlByTen(txtSearchTen.getText());

            if (ds == null) {
                return;
            }

            for (SPCTGiayViewModel d : ds) {
                Object[] data = {
                    d.getId(),
                    d.getMaCTGiay(),
                    d.getTenCTGiay(),
                    msRepo.findById(d.getMs()).getTenMS(),
                    hangRepo.findById(d.getHang()).getTenHang(),
                    sRepo.findById(d.getSize()).getSizeGiay(),
                    clRepo.findById(d.getCl()).getChatLieuThan(),
                    clRepo.findById(d.getCl()).getChatLieuDe(),
                    lgRepo.findById(d.getLoaiGiay()).getTenLoaiGiay(),
                    dmRepo.findByName(d.getDanhMuc()).getTenDanhMuc(),
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

    }//GEN-LAST:event_txtSearchTenKeyReleased

    private void txtSearchMaCTGiayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchMaCTGiayKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

            dtm.setRowCount(0);

            List<SPCTGiayViewModel> ds = repo.getAllByMa(txtSearchMaCTGiay.getText());

            if (ds == null) {
                return;
            }

            for (SPCTGiayViewModel d : ds) {
                Object[] data = {
                    d.getId(),
                    d.getMaCTGiay(),
                    d.getTenCTGiay(),
                    msRepo.findById(d.getMs()).getTenMS(),
                    hangRepo.findById(d.getHang()).getTenHang(),
                    sRepo.findById(d.getSize()).getSizeGiay(),
                    clRepo.findById(d.getCl()).getChatLieuThan(),
                    clRepo.findById(d.getCl()).getChatLieuDe(),
                    lgRepo.findById(d.getLoaiGiay()).getTenLoaiGiay(),
                    dmRepo.findByName(d.getDanhMuc()).getTenDanhMuc(),
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


    }//GEN-LAST:event_txtSearchMaCTGiayKeyReleased

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        String name = JOptionPane.showInputDialog(this, "Hãy nhập tên file", "Export file");
        File f = new File("D://" + name + ".xlsx");

        if (name == null) {
            return;
        }

        if (name.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Không để trống");
            return;
        }

        if (f.exists()) {
            JOptionPane.showMessageDialog(this, "File đã tồn tại");
            return;
        }

        try {
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet = wordkbook.createSheet("danhsach");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("ID");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Tên CT giày");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Màu sắc");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Hãng");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Size");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Chất liệu thân");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Chất liệu đế");

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Loại giày");

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Doanh mục");

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue("Số lượng");

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue("Giá nhập");

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue("Giá bán");

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue("Ảnh");

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue("Trạng thái");

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue("Mô tả");

            List<SPCTGiayViewModel> list = repo.getAll();
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                //Modelbook book =arr.get(i);
                row = sheet.createRow(i + 1);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(list.get(i).getId().toString());

                cell = row.createCell(1, CellType.NUMERIC);
                cell.setCellValue(list.get(i).getMaCTGiay());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getTenCTGiay());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(msRepo.findById(list.get(i).getMs()).getTenMS());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(hangRepo.findById(list.get(i).getHang()).getTenHang());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(sRepo.findById(list.get(i).getSize()).getSizeGiay());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(clRepo.findById(list.get(i).getCl()).getChatLieuThan());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(clRepo.findById(list.get(i).getCl()).getChatLieuDe());

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(lgRepo.findById(list.get(i).getLoaiGiay()).getTenLoaiGiay());

                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(dmRepo.findByName(list.get(i).getDanhMuc()).getTenDanhMuc());

                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(list.get(i).getSoLuong());

                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(list.get(i).getGiaNhap());

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(list.get(i).getGiaBan());

                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(list.get(i).getAnh());

                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(list.get(i).getTrangThai() == 0 ? "Còn hàng" : list.get(i).getTrangThai() == 1 ? "Hết hàng" : "Không hoạt động");

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(list.get(i).getMoTa());

            }

            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "In thành công D:\\" + name + ".xlsx");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi mo file");
        }
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private void txtSearchMaCTGiayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchMaCTGiayKeyPressed

    }//GEN-LAST:event_txtSearchMaCTGiayKeyPressed

    private void txtSearchTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchTenActionPerformed
        txtSearchMaCTGiay.setText("");
    }//GEN-LAST:event_txtSearchTenActionPerformed

    private void cbbSearchLoaiGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSearchLoaiGiayActionPerformed
        Object g0 = cbbSearchLoaiGiay.getSelectedItem();
        if (g0 == null) {
            return;
        }
        String g = g0.toString();
        for (int i = 0; i < cbbSearchLoaiGiay.getItemCount(); i++) {
            if (cbbSearchLoaiGiay.getItemAt(i).equals(g)) {
                DefaultTableModel dtm = (DefaultTableModel) this.tblCTSP.getModel();

                dtm.setRowCount(0);

                List<SPCTGiayViewModel> ds = repo.getAllByLG(g);

                if (ds == null) {
                    return;
                }

                for (SPCTGiayViewModel d : ds) {
                    Object[] data = {
                        d.getId(),
                        d.getMaCTGiay(),
                        d.getTenCTGiay(),
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
                break;
            }
        }
    }//GEN-LAST:event_cbbSearchLoaiGiayActionPerformed

    private void cbbGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbGiaMouseClicked

    }//GEN-LAST:event_cbbGiaMouseClicked

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed
        Object o = this.cbbGia.getSelectedItem();
        if (o == null) {
            return;
        }
        String soSanh = o.toString();
        String gia = txtGia.getText();
        txtSearchMaCTGiay.setText("");
        txtSearchTen.setText("");

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
            loadSearchTable(repo.getAllCTGEqual(gia));
        } else if (soSanh.equalsIgnoreCase(">=")) {
            loadSearchTable(repo.getAllCTGGreater(gia));
        } else {
            loadSearchTable(repo.getAllCTGLess(gia));
        }
    }//GEN-LAST:event_cbbGiaActionPerformed

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
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
                loadSearchTable(repo.getAllCTGEqual(gia));
            } else if (soSanh.equalsIgnoreCase(">=")) {
                loadSearchTable(repo.getAllCTGGreater(gia));
            } else {
                loadSearchTable(repo.getAllCTGLess(gia));
            }
            txtSearchMaCTGiay.setText("");
            txtSearchTen.setText("");
        }
    }//GEN-LAST:event_txtGiaKeyReleased

    private void txtSearchTenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTenKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchTenKeyTyped

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
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnImportExcel;
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbCLDe;
    private javax.swing.JComboBox<String> cbbCLThan;
    private javax.swing.JComboBox<String> cbbDanhMuc;
    private javax.swing.JComboBox<String> cbbGia;
    private javax.swing.JComboBox<String> cbbHang;
    private javax.swing.JComboBox<String> cbbLoaiGiay;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbSearchLoaiGiay;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JLabel lblDK;
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
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSearchMaCTGiay;
    private javax.swing.JTextField txtSearchTen;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenTT;
    // End of variables declaration//GEN-END:variables
}
