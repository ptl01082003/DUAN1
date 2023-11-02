/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModels.GiamGia;
import repositories.IGiamGiaRepository;
import utilities.JDBCHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GiamGiaRepositoryImpl implements IGiamGiaRepository {

    @Override
    public List<GiamGia> getAll() {
        String query = """
                      SELECT [Id]
                               ,[MaGiamGia]
                               ,[HinhThucGiam]
                               ,[MucGiam]
                               ,[NgayBatDau]
                               ,[NgayKetThuc]
                               ,[TrangThai]
                               ,[MoTa]
                           FROM [dbo].[GiamGia]
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<GiamGia> lists = new ArrayList<>();
            while (rs.next()) {
                GiamGia dv = new GiamGia(rs.getString(1), rs.getInt(2), rs.getBoolean(3), rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getBoolean(7), rs.getString(8));
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
                       SELECT [MaGiamGIa]
                         FROM [dbo].[GiamGia]
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
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

  @Override
public boolean add(GiamGia gg) {
    LocalDate ngayBatDau = LocalDate.now(); // Lấy ngày hôm nay
    int check = 0;
    String query = """
                   INSERT INTO [dbo].[GiamGia]
                                         ([Id]
                                         ,[HinhThucGiam]
                                         ,[MucGiam]
                                         ,[NgayBatDau]
                                         ,[NgayKetThuc]
                                         ,[TrangThai]
                                         ,[MoTa])
                                     
                        VALUES
                           (?,?,?,?,?,?,?)
                   """;
    try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
        ps.setObject(1, gg.getId());
        ps.setObject(2, gg.isHinhThucGiam());
        ps.setObject(3, gg.getMucGiam());
        ps.setObject(4, ngayBatDau);
        ps.setObject(5, gg.getNgayKetThuc());
        ps.setObject(6, gg.isTrangThai());
        ps.setObject(7, gg.getMoTa());
        check = ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return check > 0;
}
    @Override
    public boolean update(GiamGia dv, String id) {
        int check = 0;
        String query = """
                       UPDATE [dbo].[GiamGia]
                          SET  [HinhThucGiam]=?
                                 ,[MucGiam]=?
                                 ,[NgayBatDau]=?
                                 ,[NgayKetThuc]=?
                                 ,[TrangThai]=?
                                 ,[MoTa]=?
                        WHERE [Id] = ?
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, dv.isHinhThucGiam());
            ps.setObject(2, dv.getMucGiam());
            ps.setObject(3, dv.getNgayBatDa());
            ps.setObject(4, dv.getNgayKetThuc());
            ps.setObject(5, dv.isTrangThai());
            ps.setObject(6, dv.getMoTa());
            ps.setObject(7, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean delete(String id) {
        int check = 0;
        String query = """
                       DELETE FROM [dbo].[GiamGia]
                             WHERE [Id] = ?
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

   @Override
public List<LocalDate> getListNgayKetThuc() {
    String query = "SELECT NgayKetThuc FROM dbo.GiamGia";
    try (Connection con = JDBCHelper.getConnection(); 
         PreparedStatement ps = con.prepareStatement(query)) {

        ResultSet rs = ps.executeQuery();
        List<LocalDate> lists = new ArrayList<>();
        while (rs.next()) {
            LocalDate ngayKT = rs.getDate(1).toLocalDate();
            lists.add(ngayKT);
        }
        return lists;
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return null;
}

    @Override
    public List<GiamGia> getListByMa(int ma) {
          String query = """
                      SELECT [Id]
                               ,[MaGiamGia]
                               ,[HinhThucGiam]
                               ,[MucGiam]
                               ,[NgayBatDau]
                               ,[NgayKetThuc]
                               ,[TrangThai]
                               ,[MoTa]
                           FROM [dbo].[GiamGia]
                         WHERE MaGiamGia= ?
                       """;
        try (Connection con = JDBCHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, ma);
            ResultSet rs = ps.executeQuery();
            List<GiamGia> lists = new ArrayList<>();
            while (rs.next()) {
                GiamGia dv = new GiamGia(rs.getString(1), rs.getInt(2), rs.getBoolean(3), rs.getInt(4),rs.getDate(5), rs.getDate(6), rs.getBoolean(7), rs.getString(8));
                lists.add(dv);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;    }

    @Override
    public List<LocalDate> getListNgayBD() {
        String query = "SELECT NgayBatDau FROM dbo.GiamGia";
        try (Connection con = JDBCHelper.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            List<LocalDate> lists = new ArrayList<>();
            while (rs.next()) {
                LocalDate ngayKT = rs.getDate(1).toLocalDate();
                lists.add(ngayKT);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }


}
