/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;
import domainModels.Hang;
import viewModels.ChiTietSanPhamViewModel;
import viewModels.HoaDonDiaLogViewModel;
import viewModels.HoaDonTKViewModel;
import viewModels.HoaDonViewModel;

/**
 *
 * @author giang
 */
public class repoThongKe {

    public List<HoaDonTKViewModel> tkHD() {
        String query = "select a.Id,a.NgayThanhToan,a.MaHD,b.HoTen as TenNhanVien ,c.HoTen as TenKhachHang ,SUM(a.TongTienSauGiamGia) as TongDoanhThu,a.TrangThai from HoaDon a  join NhanVien b on a.IdNV = b.Id join KhachHang c on a.IdKH = c.Id where a.TrangThai=1 group by a.Id,a.NgayThanhToan,a.MaHD,b.HoTen,c.HoTen,a.TongTienSauGiamGia,a.TrangThai order by a.MaHD asc ";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setId(rs.getString(1));
                hdtk.setNgayThanhToan(rs.getDate(2));
                hdtk.setMaHD(rs.getString(3));
                hdtk.setTenNV(rs.getString(4));
                hdtk.setTenKH(rs.getString(5));
                hdtk.setTongTien(rs.getBigDecimal(6));
                hdtk.setTrangThai(rs.getInt(7));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//

    public List<HoaDonDiaLogViewModel> tkHD1(UUID id) {
        String query = "select a.Id,a.MaHD,a.idNV,b.MaNV,b.HoTen as TenNhanVien ,a.idKH,c.MaKH,c.HoTen as TenKhachHang ,a.IDGiamGia,d.HinhThucGiam,d.MucGiam,a.NgayTao,a.NgayThanhToan,a.TongTienGoc,a.TongTienSauGiamGia,a.HinhThucThanhToan,a.TrangThai,a.GhiChu,f.TenCTGiay,f.GiaBan,e.SoLuong \n"
                + "	from HoaDon a  join NhanVien b on a.IdNV = b.Id join KhachHang c on a.IdKH = c.Id join GiamGia d on a.IDGiamGia = d.Id join HoaDonChiTiet e on a.Id = e.IdHoaDon join ChiTietGiay f on e.IdChiTietGiay = f.Id\n"
                + "	where a.TrangThai=1 and a.Id = ?\n"
                + "	group by a.Id,a.MaHD,a.NgayThanhToan,a.idNV,b.MaNV,b.HoTen,a.idKH,c.MaKH,c.HoTen,a.IDGiamGia,d.HinhThucGiam,d.MucGiam,a.NgayTao,a.NgayThanhToan,a.TongTienGoc,a.TongTienSauGiamGia,a.HinhThucThanhToan,a.TrangThai,a.GhiChu,f.TenCTGiay,f.GiaBan,e.SoLuong \n"
                + "";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<HoaDonDiaLogViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonDiaLogViewModel hd = new HoaDonDiaLogViewModel();
                hd.setIdHD(rs.getString(1));
                hd.setMaHD(rs.getString(2));
                hd.setIdNV(rs.getString(3));
                hd.setMaNV(rs.getString(4));
                hd.setTenNV(rs.getString(5));
                hd.setIdKH(rs.getString(6));
                hd.setMaKH(rs.getString(7));
                hd.setTenKH(rs.getString(8));
                hd.setIdGG(rs.getString(9));
                hd.setHinhThuc(rs.getInt(10));
                hd.setMucGiam(rs.getInt(11));
                hd.setNgayTao(rs.getDate(12));
                hd.setNgayThanhToan(rs.getDate(13));
                hd.setTienGoc(rs.getBigDecimal(14));
                hd.setTongTienSauGiamGia(rs.getBigDecimal(15));
                hd.setHinhThucThanhToan(rs.getInt(16));
                hd.setTinhTrang(rs.getInt(17));
                hd.setGhiChu(rs.getString(18));
                hd.setTenCTGiay(rs.getString(19));
                hd.setGiaBan(rs.getDouble(20));
                hd.setSoLuong(rs.getInt(21));
                listhd.add(hd);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//

    public List<ChiTietSanPhamViewModel> tkSP() {
        String query = "SELECT c.TenHang,SUM(a.SoLuong) AS phanTram from HoaDonChiTiet a  join ChiTietGiay b on a.IdChiTietGiay = b.Id join Hang c on b.IdHang = c.Id join HoaDon d on a.IdHoaDon=d.Id where d.TrangThai=1 group by c.TenHang";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            List<ChiTietSanPhamViewModel> listsp = new ArrayList<>();
            while (rs.next()) {
                ChiTietSanPhamViewModel sptk = new ChiTietSanPhamViewModel();
                sptk.setTenCTGiay(rs.getString(1));
                sptk.setSoLuong(rs.getInt(2));
                listsp.add(sptk);
            }
            return listsp;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiTietSanPhamViewModel> tkSP1() {
        String query = "SELECT c.TenHang,SUM(d.TongTienSauGiamGia) AS phanTram from HoaDonChiTiet a join ChiTietGiay b on a.IdChiTietGiay = b.Id join Hang c on b.IdHang = c.Id join HoaDon d on a.IdHoaDon =d.Id  where d.TrangThai=1 group by c.TenHang";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            List<ChiTietSanPhamViewModel> listsp = new ArrayList<>();
            while (rs.next()) {
                ChiTietSanPhamViewModel sptk = new ChiTietSanPhamViewModel();
                sptk.setTenCTGiay(rs.getString(1));
                sptk.setSoLuong(rs.getInt(2));
                listsp.add(sptk);
            }
            return listsp;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //
    public List<HoaDonTKViewModel> tkHDpM(String bd) {
        String query = "select a.NgayThanhToan,a.MaHD,b.HoTen as TenNhanVien ,c.HoTen as TenKhachHang ,SUM(a.TongTienSauGiamGia) as TongDoanhThu,"
                + "a.TrangThai from HoaDon a  join NhanVien b on a.IdNV = b.Id join KhachHang c on a.IdKH = c.Id where a.TrangThai=1"
                + " and a.MaHD = ? group by a.NgayThanhToan,a.MaHD,b.HoTen,c.HoTen,a.TongTienSauGiamGia,a.TrangThai ";

        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, bd);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setNgayThanhToan(rs.getDate(1));
                hdtk.setMaHD(rs.getString(2));
                hdtk.setTenNV(rs.getString(3));
                hdtk.setTenKH(rs.getString(4));
                hdtk.setTongTien(rs.getBigDecimal(5));
                hdtk.setTrangThai(rs.getInt(6));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<ChiTietSanPhamViewModel> tkSPpM(Date bd) {
//        String query = "select  Giay.MaGiay, Giay.TenGiay, SUM(HoaDonChiTiet.SoLuong) as tongSLBan   \n"
//                + "from HoaDon join HoaDonChiTiet on HoaDon.Id = HoaDonChiTiet.IdHoaDon  \n"
//                + "join ChiTietGiay on HoaDonChiTiet.IdChiTietGiay = ChiTietGiay.Id\n"
//                + "join Giay on ChiTietGiay.IdGiay = Giay.Id\n"
//                + "where HoaDon.NgayTao = ?"
//                + "group by Giay.MaGiay, Giay.TenGiay,HoaDon.NgayTao order by SUM(HoaDonChiTiet.SoLuong) desc ";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setObject(1, bd);
//            ResultSet rs = ps.executeQuery();
//            List<ChiTietSanPhamViewModel> listsp = new ArrayList<>();
//            while (rs.next()) {
//                  Giay sp = new Giay();
//                sp.setMaGiay(rs.getInt(1));
//                sp.setTenGiay(rs.getString(2));
//                ChiTietSanPhamViewModel sptk = new ChiTietSanPhamViewModel();
//                sptk.setGiaBan(rs.getInt(3));         
//                listsp.add(sptk);
//            }
//            return listsp;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public List<HoaDonTKViewModel> tkTHD() {
        String query = "select count(Id) as SLHD\n"
                + "from HoaDon where TrangThai=1";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongHD(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTDT() {
        String query = "select SUM(TongTienSauGiamGia) as TongDoanhThu from HoaDon a JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n" +
"    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id where a.TrangThai=1";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkKH() {
        String query = " select count(DISTINCT IdKH) as SLKH from HoaDon where TrangThai=1";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongKH(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkBD() {
        String query = "select NgayThanhToan,SUM(TongTienSauGiamGia) as TongTien from HoaDon where trangthai = 1 group by NgayThanhToan";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setNgayThanhToan(rs.getDate(1));
                hdtk.setTongTien(rs.getBigDecimal(2));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkBD1(int nam) {
        String query = "SELECT MONTH(NgayThanhToan) AS thang, SUM(TongTienSauGiamGia) AS total_amount FROM HoaDon where YEAR(NgayThanhToan) =? GROUP BY MONTH(NgayThanhToan)";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongKH(rs.getInt("thang"));
                hdtk.setTongTien(rs.getBigDecimal(2));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkBD2() {
        String query = "SELECT YEAR(NgayThanhToan) AS nam, SUM(TongTienSauGiamGia) AS total_amount FROM HoaDon GROUP BY YEAR(NgayThanhToan)";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongKH(rs.getInt("nam"));
                hdtk.setTongTien(rs.getBigDecimal(2));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTHD1(int nam) {
        String query = "select count(Id) as SLHD\n"
                + "from HoaDon where TrangThai=1 and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongHD(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTDT1(int nam) {
        String query = "select SUM(TongTienSauGiamGia) as TongDoanhThu from HoaDon a JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n" +
"    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id where a.TrangThai=1 and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkKH1(int nam) {
        String query = "select count(IdKH) as SLKH\n"
                + "from HoaDon a join KhachHang b "
                + "on a.IdKH = b.Id where TrangThai=1 and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongKH(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTHD2(int thang, int nam) {
        String query = "select count(Id) as SLHD\n"
                + "from HoaDon where TrangThai=1 and MONTH(NgayThanhToan) = ? and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, thang);
            ps.setObject(2, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongHD(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTDT2(int thang, int nam) {
        String query = "select SUM(TongTienSauGiamGia) as TongDoanhThu from HoaDon a JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n" +
"    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id where a.TrangThai=1 and MONTH(NgayThanhToan) = ? and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, thang);
            ps.setObject(2, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkKH2(int thang, int nam) {
        String query = "select count(IdKH) as SLKH\n"
                + "from HoaDon a join KhachHang b "
                + "on a.IdKH = b.Id where TrangThai=1 and MONTH(NgayThanhToan) = ? and YEAR(NgayThanhToan) = ?";
        try (Connection conn = JDBCHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, thang);
            ps.setObject(2, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongKH(rs.getInt(1));
                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //*
//             public List<time> showYear(){
//                  String query = "SELECT YEAR(NgayThanhToan) as Nam FROM HoaDon order by Nam asc";
//        try (Connection conn = DBConnection.getConnection();
//            PreparedStatement ps = conn.prepareStatement(query)) {         
//            ResultSet rs = ps.executeQuery();
//            List<time> list = new ArrayList<>();
//            while (rs.next()) {
//                time tm = new time();
//                tm.setNam(rs.getInt(1));
//                list.add(tm);
//            }
//            return list;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//        }

    public List<HoaDonTKViewModel> tkTL() {
        String query = "SELECT SUM(TongTien) AS TongTienCuoiCung\n"
                + "FROM (\n"
                + "    SELECT (SUM(TongTienSauGiamGia) - SUM(c.GiaNhap*b.SoLuong)) as TongTien\n"
                + "    FROM HoaDon a\n"
                + "    JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n"
                + "    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id\n"
                + "	where a.TrangThai=1\n"
                + "    GROUP BY c.GiaNhap, b.SoLuong\n"
                + ") AS TongTienTamThoi";
        try (Connection conn = JDBCHelper.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));

                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTL1(int nam) {
        String query = "SELECT SUM(TongTien) AS TongTienCuoiCung\n"
                + "FROM (\n"
                + "    SELECT (SUM(TongTienSauGiamGia) - SUM(c.GiaNhap*b.SoLuong)) as TongTien\n"
                + "    FROM HoaDon a\n"
                + "    JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n"
                + "    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id\n"
                + "	where a.TrangThai=1 and YEAR(NgayThanhToan) = ?\n"
                + "    GROUP BY c.GiaNhap, b.SoLuong\n"
                + ") AS TongTienTamThoi";
        try (Connection conn = JDBCHelper.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));

                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDonTKViewModel> tkTL2(int thang, int nam) {
        String query = "SELECT SUM(TongTien) AS TongTienCuoiCung\n"
                + "FROM (\n"
                + "    SELECT (SUM(TongTienSauGiamGia) - SUM(c.GiaNhap*b.SoLuong)) as TongTien\n"
                + "    FROM HoaDon a\n"
                + "    JOIN HoaDonChiTiet b ON a.id = b.IdHoaDon\n"
                + "    JOIN ChiTietGiay c ON b.IdChiTietGiay = c.Id\n"
                + "	where a.TrangThai=1 and MONTH(NgayThanhToan) = ? and YEAR(NgayThanhToan) = ?\n"
                + "    GROUP BY c.GiaNhap, b.SoLuong\n"
                + ") AS TongTienTamThoi";
        try (Connection conn = JDBCHelper.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, thang);
            ps.setObject(2, nam);
            ResultSet rs = ps.executeQuery();
            List<HoaDonTKViewModel> listhd = new ArrayList<>();
            while (rs.next()) {
                HoaDonTKViewModel hdtk = new HoaDonTKViewModel();
                hdtk.setTongTien(rs.getBigDecimal(1));

                listhd.add(hdtk);
            }
            return listhd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
