/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;


import domainModels.ChiTietGiay;
import domainModels.HoaDonChiTiet;
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
public class HoaDonChiTietViewModelRepositoryImpl implements IDHoaDonChiTietViewModel {

    @Override
    public List<HoaDonChiTietViewModel> getAll() {
        String query = """
                 SELECT HoaDonChiTiet.IdHoaDon, HoaDonChiTiet.IdChiTietGiay, LoaiGiay.Id, HoaDonChiTiet.SoLuong, Hang.Id AS Expr1, MauSac.Id AS Expr2, Size.Id AS Expr3, ChatLieu.Id
                                FROM   ChatLieu INNER JOIN
                                             ChiTietGiay ON ChatLieu.Id = ChiTietGiay.IdCL INNER JOIN
                                             Hang ON ChiTietGiay.IdHang = Hang.Id INNER JOIN
                                             HoaDonChiTiet ON ChiTietGiay.Id = HoaDonChiTiet.IdChiTietGiay INNER JOIN
                                             LoaiGiay ON ChiTietGiay.IdLoaiGiay = LoaiGiay.Id INNER JOIN
                                             MauSac ON ChiTietGiay.IdMauSac = MauSac.Id INNER JOIN
                                             Size ON ChiTietGiay.IdSize = Size.Id
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
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
    public List<HoaDonChiTietViewModel> getListById(UUID id) {
        String query = """
                     SELECT HoaDonChiTiet.IdHoaDon, HoaDonChiTiet.IdChiTietGiay, ChiTietGiay.IdMauSac, HoaDonChiTiet.SoLuong, ChiTietGiay.IdHang, ChiTietGiay.IdSize, ChiTietGiay.IdCL, ChiTietGiay.IdLoaiGiay
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
    public String getTenGiayById(UUID id) {
        String query = """
                      SELECT TenLoaiGiay
                      FROM   LoaiGiay
                      Where Id=?
                       """;
        try ( Connection con = JDBCHelper.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenGiay = rs.getString(1);
                return tenGiay;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public String getTenMauById(UUID id) {
          String query = """
                      SELECT TenMS
                      FROM   MauSac
                      Where Id=?
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
        return null;  }

    @Override
    public String getTenHangById(UUID id) {
          String query = """
                      SELECT TenHang
                      FROM   Hang
                      Where Id=?
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
        return null;   }

    @Override
    public String getGiaGiayById(UUID id) {
          String query = """
                      SELECT SizeGiay
                      FROM   Size
                      Where Id=?
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
        return null;   }

    @Override
    public String getTenCLById(UUID id) {
          String query = """
                      SELECT ChatLieuThan
                      FROM   ChatLieu
                      Where Id=?
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
        return null;    }

    @Override
    public List<HoaDonChiTiet> getListByIdHDCT(UUID id) {
        String query = """
                     select * from HoaDonChiTiet where IdHoaDon=?
                     
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<HoaDonChiTiet> lists = new ArrayList<>();
            while (rs.next()) {
                HoaDonChiTiet dv = new HoaDonChiTiet(UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(2)),
                        rs.getInt(3));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<ChiTietGiay> getListByIdCTG(UUID id) {
        String query = """
                     select * from chitietGiay where id = ?
                     
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            List<ChiTietGiay> lists = new ArrayList<>();
            while (rs.next()) {
                ChiTietGiay dv = new ChiTietGiay(
                         UUID.fromString(rs.getString(1)),
                         rs.getInt(2),
                         rs.getString(3),
                         UUID.fromString(rs.getString(4)),
                         UUID.fromString(rs.getString(5)),
                         UUID.fromString(rs.getString(6)),
                         UUID.fromString(rs.getString(7)),
                         UUID.fromString(rs.getString(8)),
                         UUID.fromString(rs.getString(9)),
                         UUID.fromString(rs.getString(10)),
                         rs.getInt(11),
                         rs.getDouble(12),
                         rs.getDouble(13),
                         rs.getString(14),
                         rs.getString(15),
                         rs.getInt(16)
                );

                lists.add(dv);
            }
            return lists;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }


}
