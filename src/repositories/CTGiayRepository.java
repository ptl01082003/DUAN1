/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import domainModels.ChatLieu;
import domainModels.ChiTietGiay;
import domainModels.DanhMucSanPham;
import domainModels.Hang;
import domainModels.LoaiGiay;
import domainModels.MauSac;
import domainModels.QR;
import domainModels.Size;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import utilities.JDBCHelper;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author ACER
 */
public class CTGiayRepository {
    private static final String CHARSET = "UTF-8";
    private static String FILE_NAME = "";
    private Connection conn;
    private ChatLieuRepository clRepo;
    private DanhMucSPRepository dmRepo;
    private HangRepository hangRepo;
    private LoaiGiayRepository lgRepo;
    private MauSacRepository msRepo;
    private SizeRepository sRepo;
    private QrRepository qrRepo;
    private final String Select_All = "select  * from chitietgiay order by mactgiay";
    private final String Insert = "insert into chitietGiay(tenCTGiay, IdMauSac, IDHang, IdSize, IdCl, IDLoaiGiay, IDDanhMuc, SoLuong, GiaNhap, GiaBan, anh, trangthai, mota) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String update = "update ChiTietGiay set tenCTGiay = ?, IdMauSac=?, IDHang=?, IdSize=?, IdCl=?, IDLoaiGiay=?, IDDanhMuc=?, SoLuong=?, GiaNhap=?, GiaBan=?, anh=?, trangthai=?, mota=? where id =?";
    private final String Delete = "Delete from chitietgiay where id = ?";
    private final String Check_CTGiay = "select count(*) from ChiTietGiay where tenCTGiay=? and IdMauSac=? and IDHang=? and IdSize=? and IdCl=? and IDLoaiGiay=? and IDDanhMuc=?";
    private final String findIDByMa = "select id from chitietGiay where maCTGiay = ?";
    private final String searchTen = "select * from chitietGiay where tenCTGiay like '%'+?+'%'";
    private final String searchLoaiGiay = "select * from chitietGiay where idloaigiay = (select id from loaigiay where tenloaigiay like ?)";
    private final String searchGiay = "select * from chitietGiay where idgiay = (select id from giay where tengiay like ?)";
    private final String searchMa = "select * from chitietGiay where CAST(maCTGiay  AS VARCHAR) like '%'+?+'%'";
    private final String findById = "select * from chitietGiay where id = ?";
    private final String updateQR = "update chitietgiay set idQR = ? where id = ?";
    private final String FindIDWhereMaMax = "select id from ChiTietGiay where mactgiay = (select MAX(mactgiay) from ChiTietGiay)";
    private final String InsertQR = "insert into QR(AnhQR) values(?)";
    private final String FindIdQRWhereMaMax = "select id from QR where MaQR = (select MAX(MaQR) from QR)";
    private final String SetTrangThaiSP = "update chitietgiay set trangthai = 1 where id = ?";
    private final String TimKiemTheoNhoHonGia = "select * from ChiTietGiay where GiaBan <= ?";
    private final String TimKiemTheoLonHonGia = "select * from ChiTietGiay where GiaBan >= ?";
    private final String TimKiemTheoGia = "select * from ChiTietGiay where GiaBan = ?";
    private final String checkTruocXoa = "select COUNT(*) from HoaDonChiTiet where IdChiTietGiay = ?";
    

    public CTGiayRepository() {
        conn = JDBCHelper.getConnection();
        this.dmRepo = new DanhMucSPRepository();
        this.clRepo = new ChatLieuRepository();
        this.hangRepo = new HangRepository();
        this.lgRepo = new LoaiGiayRepository();
        this.msRepo = new MauSacRepository();
        this.sRepo = new SizeRepository();
        this.qrRepo = new QrRepository();
    }

    public int checkTruocXoa(UUID id){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(checkTruocXoa, id);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public List<ChiTietGiay> getAll() {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                listCL.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<ChiTietGiay> getAllByTen(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchTen, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                listCL.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<ChiTietGiay> getAllCTGEqual(String s) {

        try {
            List<ChiTietGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(TimKiemTheoGia, s);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                list.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public List<ChiTietGiay> getAllCTGLess(String s) {

        try {
            List<ChiTietGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(TimKiemTheoNhoHonGia, s);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                list.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public List<ChiTietGiay> getAllCTGGreater(String s) {

        try {
            List<ChiTietGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(TimKiemTheoLonHonGia, s);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                list.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public List<ChiTietGiay> getAllByG(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchGiay, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                listCL.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<ChiTietGiay> getAllByLG(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchLoaiGiay, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                listCL.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<ChiTietGiay> getAllByMa(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchMa, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                listCL.add(new ChiTietGiay(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int them(ChiTietGiay o) {
        JDBCHelper.update(Insert, o.getTenCTGiay(), o.getMs(), o.getHang(), o.getSize(), o.getCl(), o.getLoaiGiay(), o.getDanhMuc(), o.getSoLuong(), o.getGiaNhap(), o.getGiaBan(), o.getAnh(), o.getTrangThai(), o.getMoTa());
        UUID id = findIDByMaMax();
        ChiTietGiay ctg = findByID(id);
        
        
        
        String filePath = "D:\\QR\\SPCT_" + o.getTenCTGiay() + ".png"; // Đường dẫn và tên file hình ảnh mã QR
        JDBCHelper.update(InsertQR, filePath);
        UUID idQr = findIDQRByMaMax();
        String text = " Ma CTGiay: " + ctg.getMaCTGiay() + "\n Ten: " + ctg.getTenCTGiay() + "\n Mau Sac: " + this.msRepo.findById(ctg.getMs()).getTenMS() + "\n Hang: " + this.hangRepo.findById(ctg.getHang()).getTenHang() + "\n Chat lieu: " + this.clRepo.findById(ctg.getCl()).getChatLieuThan() + ", " + this.clRepo.findById(ctg.getCl()).getChatLieuDe() + "\n Loai giay: " + this.lgRepo.findById(ctg.getLoaiGiay()).getTenLoaiGiay() + "\n Danh muc: " + this.dmRepo.findByName(ctg.getDanhMuc()).getTenDanhMuc(); // Nội dung mã QR


        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage qrCodeImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            qrCodeImage.createGraphics();

            Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
            graphics.setColor(java.awt.Color.WHITE);
            graphics.fillRect(0, 0, 300, 300);
            graphics.setColor(java.awt.Color.BLACK);

            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 300; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ImageIO.write(qrCodeImage, "png", new File(filePath));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return JDBCHelper.update(updateQR, idQr, id);
    }

    private UUID findIDByMaMax() {
        UUID uuid = null;
        try {
            ResultSet rs = JDBCHelper.getResultSet(FindIDWhereMaMax);
            while (rs.next()) {

                uuid = UUID.fromString(rs.getString(1));

            }
            return uuid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    private UUID findIDQRByMaMax() {
        UUID uuid = null;
        try {
            ResultSet rs = JDBCHelper.getResultSet(FindIdQRWhereMaMax);
            while (rs.next()) {

                uuid = UUID.fromString(rs.getString(1));

            }
            return uuid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public int sua(ChiTietGiay o) {
        return JDBCHelper.update(update, o.getTenCTGiay(), o.getMs(), o.getHang(), o.getSize(), o.getCl(), o.getLoaiGiay(), o.getDanhMuc(), o.getSoLuong(), o.getGiaNhap(), o.getGiaBan(), o.getAnh(), o.getTrangThai(), o.getMoTa(), o.getId());
    }
    
    public int suaTrangThaiHetHang(UUID id) {
        return JDBCHelper.update(SetTrangThaiSP, id);
    }


    public int xoa(UUID id) {
        return JDBCHelper.update(Delete, id);
        
    }

    public int checkCTGiay(String ten, UUID mauSac, UUID hang, UUID size, UUID cl, UUID lg, UUID dm) {
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_CTGiay, ten, mauSac, hang, size, cl, lg, dm);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public UUID findIDByMa(String id) {
        UUID uuid = null;
        try {
            ResultSet rs = JDBCHelper.getResultSet(findIDByMa, id);
            while (rs.next()) {

                uuid = UUID.fromString(rs.getString(1));

            }
            return uuid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ChiTietGiay findByID(UUID id) {
        ChiTietGiay uuid = new ChiTietGiay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);

            while (rs.next()) {
                UUID ids = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idQR = null;
                if (rs.getString(4) != null) {
                    idQR = UUID.fromString(rs.getString(4));
                }

                UUID idMS = UUID.fromString(rs.getString(5));
                UUID idHang = UUID.fromString(rs.getString(6));
                UUID idSize = UUID.fromString(rs.getString(7));
                UUID idCL = UUID.fromString(rs.getString(8));
                UUID idLoaiGiay = UUID.fromString(rs.getString(9));
                UUID idDM = UUID.fromString(rs.getString(10));
                int sl = rs.getInt(11);
                double gNhap = rs.getDouble(12);
                double gBan = rs.getDouble(13);
                String anh = rs.getString(14);
                int trangThai = rs.getInt(15);
                String moTa = rs.getString(16);

                uuid = new ChiTietGiay(ids, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai);
            }
            return uuid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void importExcel() {
        int sum = 0;
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String FILENAME = selectedFile.getAbsolutePath();
            FILE_NAME = FILENAME.replace('\\', '/');
            System.out.println("Đường dẫn file đã chọn: " + FILE_NAME);
            // Tiếp tục xử lý với đường dẫn file

        }
        try (FileInputStream fis = new FileInputStream(FILE_NAME);) {

            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is present in the first sheet

            boolean firstRowSkipped = false;
            for (Row row : sheet) {
                // Bỏ qua dòng đầu tiên
                if (!firstRowSkipped) {
                    firstRowSkipped = true;
                    continue; // Bỏ qua lần lặp hiện tại
                }
                // Thực hiện xử lý với các dòng còn lại
//                (tenCTGiay, IDGiay, IdQR, IdMauSac, IDHang, IdSize, IdCl, IDLoaiGiay, IDDanhMuc, SoLuong, GiaNhap, GiaBan, anh, trangthai, mota)
                String ten = row.getCell(0).toString();          

                String mauSac = row.getCell(1).toString();
                UUID IdMauSac = null;
                MauSac ms;
                if (msRepo.checkTen(mauSac) == 0) {
                    ms = new MauSac();
                    ms.setTenMS(mauSac);
                    msRepo.them(ms);
                    IdMauSac = msRepo.findByName(mauSac).getId();
                } else {
                    IdMauSac = msRepo.findByName(mauSac).getId();
                }

                String hang = row.getCell(2).toString();
                UUID IDHang = null;
                Hang h;
                if (hangRepo.checkTen(hang) == 0) {
                    h = new Hang();
                    h.setTenHang(hang);
                    hangRepo.them(h);
                    IDHang = hangRepo.findByName(hang).getId();
                } else {
                    IDHang = hangRepo.findByName(hang).getId();
                }

                String sizeG = row.getCell(3).toString();
                int size = (int) Double.parseDouble(sizeG);
                UUID IdSize = null;
                Size s;
                if (sRepo.checkSize(size) == 0) {
                    s = new Size();
                    s.setSizeGiay(size);
                    sRepo.them(s);
                    IdSize = sRepo.findByName(size).getId();
                } else {
                    IdSize = sRepo.findByName(size).getId();
                }

                String clThan = row.getCell(4).toString();
                String clDe = row.getCell(5).toString();
                ChatLieu cl;
                UUID IdCl = null;
                if (clRepo.checkTenCL(clDe, clThan) == 0) {
                    cl = new ChatLieu();
                    cl.setChatLieuDe(clDe);
                    cl.setChatLieuThan(clThan);
                    clRepo.themCL(cl);
                    IdCl = clRepo.findByCL(clThan, clDe).getId();
                } else {
                    IdCl = clRepo.findByCL(clThan, clDe).getId();
                }

                String loaiGiay = row.getCell(6).toString();
                UUID IDLoaiGiay = null;
                LoaiGiay lg;
                if (lgRepo.checkTenCL(loaiGiay) == 0) {
                    lg = new LoaiGiay();
                    lg.setTenLoaiGiay(loaiGiay);
                    lgRepo.them(lg);
                    IDLoaiGiay = lgRepo.findByName(loaiGiay).getId();
                } else {
                    IDLoaiGiay = lgRepo.findByName(loaiGiay).getId();
                }

                String danhMuc = row.getCell(7).toString();
                UUID IDDanhMuc = null;
                DanhMucSanPham dm;
                if (dmRepo.checkTenCL(danhMuc) == 0) {
                    dm = new DanhMucSanPham();
                    dm.setTenDanhMuc(danhMuc);
                    dmRepo.them(dm);
                    IDDanhMuc = dmRepo.findByName(danhMuc).getId();
                } else {
                    IDDanhMuc = dmRepo.findByName(danhMuc).getId();
                }

                int SoLuong = (int) Double.parseDouble(row.getCell(8).toString());
                double GiaNhap = Double.parseDouble(row.getCell(9).toString());
                double GiaBan = Double.parseDouble(row.getCell(10).toString());
                String anh = row.getCell(11).toString();
                int trangthai = row.getCell(12).toString().equalsIgnoreCase("Còn hàng") ? 0 : row.getCell(13).toString().equalsIgnoreCase("Hết hàng") ? 1 : 2;
                String mota = row.getCell(13).toString();

                UUID IdQR = null;
                if(this.checkCTGiay(ten, IdMauSac, IDHang, IdSize, IdCl, IDLoaiGiay, IDDanhMuc)!=0){
                    System.out.println("Sản phẩm trùng lặp sẽ bỏ qua");
                    continue;
                }
                
                ChiTietGiay o = new ChiTietGiay();
                o.setTenCTGiay(ten);
                o.setQr(IdQR);
                o.setMs(IdMauSac);
                o.setHang(IDHang);
                o.setSize(IdSize);
                o.setCl(IdCl);
                o.setLoaiGiay(IDLoaiGiay);
                o.setDanhMuc(IDDanhMuc);
                o.setSoLuong(SoLuong);
                o.setGiaNhap(GiaNhap);
                o.setGiaBan(GiaBan);
                o.setAnh(anh);
                o.setTrangThai(trangthai);
                o.setMoTa(mota);
                int themSP = them(o);

                if(themSP!=0){
                    sum++;
                }
            }
            JOptionPane.showMessageDialog(null, "Thêm thành công "+sum+" sản phẩm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
