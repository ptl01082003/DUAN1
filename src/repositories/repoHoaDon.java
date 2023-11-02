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
import utilities.JDBCHelper;
import viewModels.HoaDonChiTietViewModel;
import viewModels.HoaDonViewModel;

/**
 *
 * @author giang
 */
public class repoHoaDon {
  
    public ArrayList<HoaDonViewModel> all() {
        ArrayList<HoaDonViewModel> list = new ArrayList<>();
        String sql = "select a.MaHD,b.MaNV,c.MaKH,d.MaGiamGia,a.NgayTao,a.NgayThanhToan,a.TongTienGoc,a.TongTienSauGiamGia,a.HinhThucThanhThoan,a.TrangThai,a.GhiChu from HoaDon a left join NhanVien b on a.IdNV = b.Id left join  KhachHang c on a.IdKH = c.Id join GiamGia d on a.IdGG = d.Id order by a.Ma asc";
        try (Connection con = JDBCHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonViewModel hoaDon = new HoaDonViewModel();
                hoaDon.setMaHD(rs.getString(1));
                hoaDon.setIdNV(rs.getString(2));
                hoaDon.setIdKH(rs.getString(3));
                hoaDon.setIdGG(rs.getString(4));
                hoaDon.setNgayTao(rs.getDate(5));
                hoaDon.setNgayThanhToan(rs.getDate(6));
                hoaDon.setTienGoc(rs.getBigDecimal(7));
                hoaDon.setTongTienSauGiamGia(rs.getBigDecimal(8));
                hoaDon.setHinhThucThanhToan(rs.getInt(9));
                hoaDon.setTinhTrang(rs.getInt(10));
                hoaDon.setGhiChu(rs.getString(11));
              

                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;
    }

    public List<HoaDonChiTietViewModel> getListById(String idhoaDon) throws SQLException {
        String query = "SELECT  c.MaGiay , c.TenGiay , b.SoLuong , a.GiaBan fROM HoaDonChiTiet a left join ChiTietGiay b on a.IdChitetGiay = b.Id left join Giay c on c.Id = b.IdGiay left join HoaDon d on a.IdHoaDon = d.Id where d.Ma = ?";
        List<HoaDonChiTietViewModel> list = new ArrayList<>();
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, idhoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietViewModel model = new HoaDonChiTietViewModel(rs.getString(1), rs.getString(2), rs.getInt(3));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
        }
        return null;
    }


    public List<HoaDonViewModel> ListHdSearch(Date bd, Date kt) {
        List<HoaDonViewModel> list = new ArrayList<>();
        String sql = "select a.MaHD,b.MaNV,c.MaKH,d.MaGiamGia,a.NgayTao,a.NgayThanhToan,a.TongTienGoc,a.TongTienSauGiamGia,a.HinhThucThanhThoan,a.TrangThai,a.GhiChu from HoaDon a left join NhanVien b on a.IdNV = b.Id left join  KhachHang c on a.IdKH = c.Id join GiamGia d on a.IdGG = d.Id "
                + "where a.NgayTao between  ? and ?\n"
                + "order by a.MaHD asc";
        try (Connection con = JDBCHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, bd);
            ps.setObject(2, kt);
            ResultSet rs = ps.executeQuery();
           while (rs.next()) {
                HoaDonViewModel hoaDon = new HoaDonViewModel();
                hoaDon.setMaHD(rs.getString(1));
                hoaDon.setIdNV(rs.getString(2));
                hoaDon.setIdKH(rs.getString(3));
                hoaDon.setIdGG(rs.getString(4));
                hoaDon.setNgayTao(rs.getDate(5));
                hoaDon.setNgayThanhToan(rs.getDate(6));
                hoaDon.setTienGoc(rs.getBigDecimal(7));
                hoaDon.setTongTienSauGiamGia(rs.getBigDecimal(8));
                hoaDon.setHinhThucThanhToan(rs.getInt(9));
                hoaDon.setTinhTrang(rs.getInt(10));
                hoaDon.setGhiChu(rs.getString(11));
              
                list.add(hoaDon);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;

    }
}
