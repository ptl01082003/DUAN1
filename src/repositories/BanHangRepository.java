package repositories;

import domainModels.ChiTietGiay;
import domainModels.GiamGia;
import domainModels.HoaDon;
import domainModels.HoaDonChiTiet;
import domainModels.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;

public class BanHangRepository {

    private Connection conn;
    private final String TaoHD = "insert into HoaDon(idNV, idKH, idGiamGia, NgayTao, HinhThucThanhToan, TrangThai) values (?, ?, ?,  GETDATE(), 1, 0)";
    private final String ThemVaoGioHang = "insert into HoaDonChiTiet(IdHoaDon, IdChiTietGiay, SoLuong) values (?, ?, ?)";
    private final String GetAllSP = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong, giaBan from ChiTietGiay where trangthai = 0 and soluong!=0 order by mactgiay";
    private final String TimKiemTheoTen = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong, giaBan from ChiTietGiay where TenCTGiay like '%'+?+'%'";
    private final String TimKiemTheoDM = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong from ChiTietGiay where IdDanhMuc = ?";
    private final String GetAllHDCT = "select * from HoaDonChiTiet where IdHoaDon=?";
    private final String GetAllHDCho = "select * from HoaDon where TrangThai = 0 order by MaHD desc";
    private final String Xoa1SpKhoiHDCT = "delete from HoaDonChiTiet where IdHoaDon = ? and IdChiTietGiay = ?";
    private final String XoaTatCaKhoiHDCT = "delete from HoaDonChiTiet where IdHoaDon = ?";
    private final String ThemSlKhiTrungSP = "update HoaDonChiTiet set SoLuong = SoLuong + 1 where IdChiTietGiay = ? and idHoaDon = ?";
    private final String HuyHD = "delete from HoaDon where id = ?";
    private final String MaNVByID = "Select manv from nhanvien where id =?";
    private final String KHByID = "Select maKH, hoten from khachhang where id =?";
    private final String HDByMa = "select * from hoadon where maHD = ?";
    private final String CheckExistCTG = "select count(*) from hoadonchitiet where idchitietgiay = ? and idHoaDon = ?";
    private final String SuaTongTien = "update HoaDon set TongTiengoc = ? where Id =?";
    private final String SuaGhiChuVaHinhThuc = "update HoaDon set ghichu = ?, hinhthucthanhtoan=? where Id =?";
    private final String SuaTongTienSauGiamGia = "update HoaDon set TongTiensaugiamgia = ? where Id =?";
    private final String SelectTongTien = "select sum(hdct.soluong*ctg.GiaBan) from hoadonchitiet hdct join chitietGiay ctg on hdct.idchitietgiay = ctg.id where idhoadon = ?";
    private final String SuaTrangThaiHD = "update hoadon set trangthai = 1, ngayThanhToan = getDate() where id = ?";
    private final String slConLaiCTG = "select  ctg.id,ctg.SoLuong -hdct.SoLuong from HoaDonChiTiet hdct join ChiTietGiay ctg on hdct.IdChiTietGiay=ctg.id where IdHoaDon = ?";
    private final String SuaSLCTG = "update ChiTietGiay set SoLuong = ? where Id = ?";
    private final String checkSLTon0 = "select  ctg.SoLuong -1 'slt' from  ChiTietGiay ctg where  ctg.id = ?";
    private final String checkSLTon = "select  ctg.SoLuong -hdct.SoLuong from HoaDonChiTiet hdct join ChiTietGiay ctg on hdct.IdChiTietGiay=ctg.id where IdHoaDon =? and idChiTietGiay = ?";
    private final String TimKiemTheoNhoHonGia = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong, giaBan from ChiTietGiay where GiaBan <= ?";
    private final String TimKiemTheoLonHonGia = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong, giaBan from ChiTietGiay where GiaBan >= ?";
    private final String TimKiemTheoGia = "select MaCTGiay, tenctGiay, IdMauSac, idCL, idHang, idSize, soLuong, giaBan from ChiTietGiay where GiaBan = ?";
    private final String SuaIDKHForHD = "Update hoadon set idKH = ? where id =?";
    private final String GetIDKHByMaKH = "Select id from khachhang where maKH = ?";
    private final String GetGiamGia = "select top 1 id, magiamgia, hinhthucgiam, mucgiam, trangthai from giamgia where trangThai = 1";
    private final String GetGiamGiaByHD = "select GiamGia.MucGiam from hoadon join giamgia on hoadon.idGiamGia = giamgia.id where hoadon.Id = ?";
    private final String SelectSPHDCT = "select count(*) from hoadonchitiet where idhoadon =? ";
    private final String suaNvHD = "update hoadon set idNV = ? where id = ?"; 


    public BanHangRepository() {
        conn = JDBCHelper.getConnection();
    }

    public void TaoHD(UUID idNV, UUID idKH, UUID idGiamGia) {
        JDBCHelper.update(TaoHD, idNV, idKH, idGiamGia);
    }

    public void themSPVaoGio(UUID idHD, UUID idCTG, int sl) {
        JDBCHelper.update(ThemVaoGioHang, idHD, idCTG, sl);
    }

    public void themSLSPVaoGio(UUID idCTG, UUID idHD) {
        JDBCHelper.update(ThemSlKhiTrungSP, idCTG, idHD);
    }

    public void xoa1SPHDCT(UUID idHD, UUID idCTG) {
        JDBCHelper.update(Xoa1SpKhoiHDCT, idHD, idCTG);
    }

    public void xoaTatCaSPHDCT(UUID idHD) {
        JDBCHelper.update(XoaTatCaKhoiHDCT, idHD);
    }

    public void suaSLSP(UUID idHD){
        UUID idSp = null;
        int sl = 0;
        try {

            ResultSet rs = JDBCHelper.getResultSet(slConLaiCTG, idHD);
            while (rs.next()) {
                idSp = UUID.fromString(rs.getString(1));
                sl = rs.getInt(2);
                JDBCHelper.update(SuaSLCTG, sl, idSp);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
    
    public void suaKH(UUID idKH, UUID idHD){
        JDBCHelper.update(SuaIDKHForHD, idKH, idHD);
    }
    
    public void suaNV(UUID idNV, UUID idHD){
        JDBCHelper.update(suaNvHD, idNV, idHD);
    }
    
    public int checkSLTonSP0(UUID idSP){
        int sl = 0;
        try {

            ResultSet rs = JDBCHelper.getResultSet(checkSLTon0, idSP);
            while (rs.next()) {
                sl = rs.getInt(1);

            }
            return sl;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int checkSLTonSP(UUID idHD, UUID idSP){
        int sl = 0;
        try {

            ResultSet rs = JDBCHelper.getResultSet(checkSLTon, idHD, idSP);
            while (rs.next()) {
                sl = rs.getInt(1);

            }
            return sl;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int mucGiamFromHD(UUID idHD){
        int mucGiam = 0;
        try {

            ResultSet rs = JDBCHelper.getResultSet(GetGiamGiaByHD, idHD);
            while (rs.next()) {
                mucGiam = rs.getInt(1);

            }
            return mucGiam;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public double getTongTien(UUID idHD) {
        double tongTien = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(SelectTongTien, idHD);
            while (rs.next()) {
                
                tongTien = rs.getInt(1);
            }
            return tongTien;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public void suaTongTienGoc(double tongTien, UUID idHD) {
        JDBCHelper.update(SuaTongTien, tongTien, idHD);

    }
    
    public void suaTongTienSauGiamGia(double tongTien, UUID idHD) {
        JDBCHelper.update(SuaTongTienSauGiamGia, tongTien, idHD);
        
    }
    
    public void suaGhiChuVaHinhThuc(String ghiChu, boolean hinhThuc, UUID idHD) {
        JDBCHelper.update(SuaGhiChuVaHinhThuc, ghiChu, hinhThuc, idHD);
        
    }

    public void suaTrangThai(UUID idHD){
        JDBCHelper.update(SuaTrangThaiHD, idHD);
    }
    
    public List<ChiTietGiay> getAllCTG() {

        try {
            List<ChiTietGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(GetAllSP);
            while (rs.next()) {
                int ma = rs.getInt(1);
                String ten = rs.getString(2);
                UUID idMS = UUID.fromString(rs.getString(3));
                UUID idCL = UUID.fromString(rs.getString(4));
                UUID idH = UUID.fromString(rs.getString(5));
                UUID idS = UUID.fromString(rs.getString(6));
                int sl = rs.getInt(7);
                double dg = rs.getDouble(8);
                ChiTietGiay ctg = new ChiTietGiay();
                ctg.setMaCTGiay(ma);
                ctg.setTenCTGiay(ten);
                ctg.setCl(idCL);
                ctg.setMs(idMS);
                ctg.setHang(idH);
                ctg.setSize(idS);
                ctg.setSoLuong(sl);
                ctg.setGiaBan(dg);
                list.add(ctg);
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
                int ma = rs.getInt(1);
                String ten = rs.getString(2);
                UUID idMS = UUID.fromString(rs.getString(3));
                UUID idCL = UUID.fromString(rs.getString(4));
                UUID idH = UUID.fromString(rs.getString(5));
                UUID idS = UUID.fromString(rs.getString(6));
                int sl = rs.getInt(7);
                double dg = rs.getDouble(8);
                ChiTietGiay ctg = new ChiTietGiay();
                ctg.setMaCTGiay(ma);
                ctg.setTenCTGiay(ten);
                ctg.setCl(idCL);
                ctg.setMs(idMS);
                ctg.setHang(idH);
                ctg.setSize(idS);
                ctg.setSoLuong(sl);
                ctg.setGiaBan(dg);
                list.add(ctg);
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
                int ma = rs.getInt(1);
                String ten = rs.getString(2);
                UUID idMS = UUID.fromString(rs.getString(3));
                UUID idCL = UUID.fromString(rs.getString(4));
                UUID idH = UUID.fromString(rs.getString(5));
                UUID idS = UUID.fromString(rs.getString(6));
                int sl = rs.getInt(7);
                double dg = rs.getDouble(8);
                ChiTietGiay ctg = new ChiTietGiay();
                ctg.setMaCTGiay(ma);
                ctg.setTenCTGiay(ten);
                ctg.setCl(idCL);
                ctg.setMs(idMS);
                ctg.setHang(idH);
                ctg.setSize(idS);
                ctg.setSoLuong(sl);
                ctg.setGiaBan(dg);
                list.add(ctg);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public List<ChiTietGiay> getAllCTGByName(String s) {

        try {
            List<ChiTietGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(TimKiemTheoTen, s);
            while (rs.next()) {
                int ma = rs.getInt(1);
                String ten = rs.getString(2);
                UUID idMS = UUID.fromString(rs.getString(3));
                UUID idCL = UUID.fromString(rs.getString(4));
                UUID idH = UUID.fromString(rs.getString(5));
                UUID idS = UUID.fromString(rs.getString(6));
                int sl = rs.getInt(7);
                double dg = rs.getDouble(8);
                ChiTietGiay ctg = new ChiTietGiay();
                ctg.setMaCTGiay(ma);
                ctg.setTenCTGiay(ten);
                ctg.setCl(idCL);
                ctg.setMs(idMS);
                ctg.setHang(idH);
                ctg.setSize(idS);
                ctg.setSoLuong(sl);
                ctg.setGiaBan(dg);
                list.add(ctg);
            }
            return list;
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
                int ma = rs.getInt(1);
                String ten = rs.getString(2);
                UUID idMS = UUID.fromString(rs.getString(3));
                UUID idCL = UUID.fromString(rs.getString(4));
                UUID idH = UUID.fromString(rs.getString(5));
                UUID idS = UUID.fromString(rs.getString(6));
                int sl = rs.getInt(7);
                double dg = rs.getDouble(8);
                ChiTietGiay ctg = new ChiTietGiay();
                ctg.setMaCTGiay(ma);
                ctg.setTenCTGiay(ten);
                ctg.setCl(idCL);
                ctg.setMs(idMS);
                ctg.setHang(idH);
                ctg.setSize(idS);
                ctg.setSoLuong(sl);
                ctg.setGiaBan(dg);
                list.add(ctg);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
    

    public List<HoaDon> getAllHDCho() {

        try {
            List<HoaDon> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(GetAllHDCho);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                UUID idNV = UUID.fromString(rs.getString(3));
                UUID idKH = UUID.fromString(rs.getString(4));
                UUID idGiamGia = null;
                if (rs.getString(5) != null) {
                    idGiamGia = UUID.fromString(rs.getString(5));
                }
                String ngayTao = rs.getString(6);
                String ngayTTT = rs.getString(7);
                double tongTien = rs.getDouble(8);
                double tongTienSauGiam = rs.getDouble(9);
                boolean hinhThuc = rs.getBoolean(10);
                int tt = rs.getInt(11);
                String ghiChu = rs.getString(12);

                HoaDon o = new HoaDon(id, ma, idNV, idKH, idGiamGia, ngayTao, ngayTTT, tongTien, tongTienSauGiam, hinhThuc, tt, ghiChu);
//                o.setMaHD(ma);
//                o.setNgayTao(ngayTao);
//                o.setIdNV(idNV);
//                o.setIdKH(idKH);
                list.add(o);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HoaDonChiTiet> getAllHDCT(UUID id) {

        try {
            List<HoaDonChiTiet> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(GetAllHDCT, id);
            while (rs.next()) {

                UUID idHD = UUID.fromString(rs.getString(1));
                UUID idCTG = UUID.fromString(rs.getString(2));
                int sl = rs.getInt(3);
                HoaDonChiTiet ctg = new HoaDonChiTiet(idHD, idCTG, sl);
                list.add(ctg);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void huyHD(UUID idHD) {
        JDBCHelper.update(XoaTatCaKhoiHDCT, idHD);
        JDBCHelper.update(HuyHD, idHD);
    }

    public int getMaNVFromID(UUID idNV) {
        int maNV = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(MaNVByID, idNV);
            while (rs.next()) {

                maNV = rs.getInt(1);
            }
            return maNV;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int checkCTGInHDCT(UUID idCTG, UUID idHD) {
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(CheckExistCTG, idCTG, idHD);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int getSLSPInHDCT(UUID idHD) {
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(SelectSPHDCT, idHD);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public HoaDon getHDByMa(int maHD) {
        HoaDon hd = null;
        try {
            ResultSet rs = JDBCHelper.getResultSet(HDByMa, maHD);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                UUID idNV = UUID.fromString(rs.getString(3));
                UUID idKH = UUID.fromString(rs.getString(4));
                UUID idGiamGia = null;
                if (rs.getString(5) != null) {
                    idGiamGia = UUID.fromString(rs.getString(5));
                }
                String ngayTao = rs.getString(6);
                String ngayTTT = rs.getString(7);
                double tongTien = rs.getDouble(8);
                double tongTienSauGiam = rs.getDouble(9);
                boolean hinhThuc = rs.getBoolean(10);
                int tt = rs.getInt(11);
                String ghiChu = rs.getString(12);

                hd = new HoaDon(id, ma, idNV, idKH, idGiamGia, ngayTao, ngayTTT, tongTien, tongTienSauGiam, hinhThuc, tt, ghiChu);
                
            }
            return hd;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public KhachHang getKH(UUID idKH) {
        KhachHang kh = new KhachHang();

        try {
            ResultSet rs = JDBCHelper.getResultSet(KHByID, idKH);
            while (rs.next()) {

                String ma = rs.getString(1);
                String ten = rs.getString(2);

                kh.setMa(ma);
                kh.setHoTen(ten);
            }
            return kh;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public GiamGia getGiamGia() {
        GiamGia gg = null;

        try {
            ResultSet rs = JDBCHelper.getResultSet(GetGiamGia);
            while (rs.next()) {
                String id = rs.getString(1);
                int ma = rs.getInt(2);
                boolean ht = rs.getBoolean(3);
                int mucGiam = rs.getInt(4);
                boolean trangThai = rs.getBoolean(5);

                gg = new GiamGia();
                gg.setId(id);
                gg.setMaGiamGia(ma);
                gg.setHinhThucGiam(ht);
                gg.setMucGiam(mucGiam);
                gg.setTrangThai(trangThai);
            }
            return gg;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public UUID getIdKH(int maKH){
        UUID idKH = null;
        ResultSet rs = JDBCHelper.getResultSet(GetIDKHByMaKH, maKH);
        try {
            while(rs.next()){
            idKH = UUID.fromString(rs.getString(1));
            }
            return idKH;
        } catch (Exception e) {
            e.printStackTrace();
            return idKH;
        }
    }
}
