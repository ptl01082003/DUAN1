/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;
import DomainModels.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import repositories.DBConnection;

/**
 *
 * @author pt19t
 */
public class NhanVienRepos {
    
    private DBConnection connection;
    
    public ArrayList<NhanVien> getListFromdb(){
        ArrayList<NhanVien> list = new ArrayList<>();
        try (Connection con = connection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT Id,MaNV,hoTen,GioiTinh,NgaySinh,SDT,CCCD,DiaChi,Email,ChucVu,TrangThai From NhanVien")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              NhanVien nv = new NhanVien();
              
              nv.setId(rs.getString(1));
              nv.setMaNv(rs.getString(2));
              nv.setHoTen(rs.getString(3));
              nv.setGioiTinh(rs.getString(4));
              nv.setNgaySinh(rs.getString(5));
              nv.setSdt(rs.getString(6));
              nv.setCccd(rs.getString(7));
              nv.setDiaChi(rs.getString(8));
              nv.setEmail(rs.getString(9));
              nv.setChucVu(rs.getString(10));
              nv.setTrangThai(rs.getInt(11));
              list.add(nv);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return list;         
    }
    
     public Boolean addNew(NhanVien nv) {
        String sql = "INSERT INTO NhanVien(HoTen,GioiTinh,NgaySinh,SDT,CCCD,DiaChi,Email,ChucVu,TrangThai) Values(?,?,?,?,?,?,?,?,?)";
        int check;
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, nv.getHoTen());
            ps.setObject(2, nv.getGioiTinh());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.getSdt());
            ps.setObject(5, nv.getCccd());
            ps.setObject(6, nv.getDiaChi());
            ps.setObject(7, nv.getEmail());
            ps.setObject(8, nv.getChucVu());
            ps.setObject(9, nv.getTrangThai());

            check = ps.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


     public Boolean update(NhanVien nv, String id) {
        String sql = "UPDATE NhanVien SET HoTen = ?, GioiTinh = ?,NgaySinh = ?, SDT = ?, CCCD = ?, DiaChi = ?, Email = ?, ChucVu = ?, TrangThai = ? where id = ?";
        int checkUpdate;
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
          
            ps.setObject(1, nv.getHoTen());
            ps.setObject(2, nv.getGioiTinh());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.getSdt());
            ps.setObject(5, nv.getCccd());
            ps.setObject(6, nv.getDiaChi());
            ps.setObject(7, nv.getEmail());
            ps.setObject(8, nv.getChucVu());
            ps.setObject(9, nv.getTrangThai());
            ps.setObject(10, id);
            checkUpdate = ps.executeUpdate();
            return checkUpdate > 0;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
         public Boolean xoa(String id) {
        String sql = "delete NhanVien where id=?";
        int checkUpdate;
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {     
            
            ps.setObject(1, id);
            
            checkUpdate = ps.executeUpdate();
            return checkUpdate > 0;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
 
    
    
   
public ArrayList<NhanVien> timKiem(String hoTen) {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "select * FROM NhanVien where HoTen =? ";
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hoTen);
          
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
              NhanVien nv = new NhanVien();
               
              nv.setId(rs.getString(1));
              nv.setMaNv(rs.getString(2));
              nv.setHoTen(rs.getString(3));
              nv.setGioiTinh(rs.getString(4));
              nv.setNgaySinh(rs.getString(5));
              nv.setSdt(rs.getString(6));
              nv.setCccd(rs.getString(7));
              nv.setDiaChi(rs.getString(8));
              nv.setEmail(rs.getString(9));
              nv.setChucVu(rs.getString(10));
              nv.setTrangThai(rs.getInt(11));
                list.add(nv);
            
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;
    }
    
}
