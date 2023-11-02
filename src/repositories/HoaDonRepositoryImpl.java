/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModels.HoaDon;
import repositories.IHoaDonRepository;
import utilities.JDBCHelper;
import viewModels.HoaDonChiTietViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class HoaDonRepositoryImpl implements IHoaDonRepository {

    @Override
    public List<HoaDon> getAll() {
        String query = """
                      SELECT [Id]
                                 ,[MaHD]
                                 ,[idNV]
                                 ,[idKH]
                                 ,[IDGiamGia]
                                 ,[NgayTao]
                                 ,[NgayThanhToan]
                                 ,[TongTienGoc]
                                 ,[TongTienSauGiamGia]
                                 ,[HinhThucThanhToan]
                                 ,[TrangThai]
                                 ,[GhiChu]
                             FROM [dbo].[HoaDon]
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<HoaDon> lists = new ArrayList<>();
            while (rs.next()) {
                                HoaDon  dv = new HoaDon(UUID.fromString(rs.getString(1)), rs.getInt(2), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)),rs.getString(5)==null?null:UUID.fromString(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10), rs.getInt(11), rs.getString(12));

                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<String> getListMa() {
        String query = """
                       SELECT [ID]
                         FROM [dbo].[HoaDon]
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<String> lists = new ArrayList<>();
            while (rs.next()) {
                String ma = rs.getString(1);
                lists.add(ma);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public String getTenKHById(UUID id) {
        String query = """
                      SELECT [HoTen]
                       FROM [dbo].[KhachHang]
                       WHERE [Id] = ? 
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenKH = rs.getString(1);
                return tenKH;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public String getMucGGById(UUID id) {
        String query = """
                      SELECT [MucGiam]
                       FROM [dbo].[GiamGia]
                       WHERE [Id] = ? 
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenKH = rs.getString(1);
                return tenKH;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public String getTenNVById(UUID id) {
        String query = """
                      SELECT [HoTen]
                       FROM [dbo].[NhanVien]
                       WHERE [Id] = ? 
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenKH = rs.getString(1);
                return tenKH;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<HoaDonChiTietViewModel> getListById(UUID id) {
        String query = """
                     SELECT HoaDonChiTiet.IdHoaDon, HoaDonChiTiet.IdChiTietGiay, ChiTietGiay.IdLoaiGiay, HoaDonChiTiet.SoLuong, ChiTietGiay.IdHang, ChiTietGiay.IdMauSac, ChiTietGiay.IdSize, ChiTietGiay.IdCL
                                                                                                                       FROM   ChiTietGiay INNER JOIN
                                                                                                                                    HoaDonChiTiet ON ChiTietGiay.Id = HoaDonChiTiet.IdChiTietGiay
                                            WHERE [IdHoaDon] = ?
                                           
                     
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<HoaDonChiTietViewModel> lists = new ArrayList<>();
            while (rs.next()) {
                HoaDonChiTietViewModel dv = new HoaDonChiTietViewModel(UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(2)),
                        UUID.fromString(rs.getString(3)), rs.getInt(4), UUID.fromString(rs.getString(5)), UUID.fromString(rs.getString(6)),
                        UUID.fromString(rs.getString(7)), UUID.fromString(rs.getString(8)));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<HoaDon> getListByHTTT(int id) {
        String query = """
                      SELECT [Id]
                                  ,[MaHD]
                                  ,[idNV]
                                  ,[idKH]
                                  ,[IDGiamGia]
                                  ,[NgayTao]
                                  ,[NgayThanhToan]
                                  ,[TongTienGoc]
                                  ,[TongTienSauGiamGia]
                                  ,[HinhThucThanhToan]
                                  ,[TrangThai]
                                  ,[GhiChu]
                              FROM [dbo].[HoaDon]
                      WHERE [HinhThucThanhToan] = ?
                     
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<HoaDon> lists = new ArrayList<>();
            while (rs.next()) {
                HoaDon dv = new HoaDon(UUID.fromString(rs.getString(1)), rs.getInt(2), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)), rs.getString(5) == null ? null : UUID.fromString(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10), rs.getInt(11), rs.getString(12));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;

    }

    @Override
    public List<HoaDon> getListByMa(int ma) {
        String query = """
                  SELECT [Id]
                              ,[MaHD]
                              ,[idNV]
                              ,[idKH]
                              ,[IDGiamGia]
                              ,[NgayTao]
                              ,[NgayThanhToan]
                              ,[TongTienGoc]
                              ,[TongTienSauGiamGia]
                              ,[HinhThucThanhToan]
                              ,[TrangThai]
                              ,[GhiChu]
                          FROM [dbo].[HoaDon]
                  WHERE MaHD = ?
                   """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, ma);
            ResultSet rs = ps.executeQuery();
            List<HoaDon> lists = new ArrayList<>();
            while (rs.next()) {
                HoaDon dv = new HoaDon(UUID.fromString(rs.getString(1)), rs.getInt(2), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)), rs.getString(5) == null ? null : UUID.fromString(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10), rs.getInt(11), rs.getString(12));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<HoaDon> getListByID(UUID id) {
          String query = """
                  SELECT [Id]
                              ,[MaHD]
                              ,[idNV]
                              ,[idKH]
                              ,[IDGiamGia]
                              ,[NgayTao]
                              ,[NgayThanhToan]
                              ,[TongTienGoc]
                              ,[TongTienSauGiamGia]
                              ,[HinhThucThanhToan]
                              ,[TrangThai]
                              ,[GhiChu]
                          FROM [dbo].[HoaDon]
                  WHERE Id = ?
                   """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<HoaDon> lists = new ArrayList<>();
            while (rs.next()) {
                HoaDon dv = new HoaDon(UUID.fromString(rs.getString(1)), rs.getInt(2), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)), rs.getString(5) == null ? null : UUID.fromString(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getBoolean(10), rs.getInt(11), rs.getString(12));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null; }

}
