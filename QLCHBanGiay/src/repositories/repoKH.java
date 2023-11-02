/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;
import java.sql.*;
import java.util.ArrayList;
import viewModels.KhacHangViewModel;
import domainModels.KhachHang;
import java.util.UUID;
/**
 *
 * @author giang
 */
public class repoKH {
     private DBConnection connection;
        public ArrayList<KhachHang> getListFromdb(){
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = " SELECT * FROM KhachHang";
        try (Connection con = connection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
              KhachHang kh = new KhachHang();
               
               
                kh.setId(UUID.fromString(rs.getString(1)));              
                kh.setMa(rs.getInt(2));
                kh.setHoTen(rs.getString(3));
                kh.setNgaySinh(rs.getString(4));
                kh.setGioiTinh(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setDiaChi(rs.getString(7));
                kh.setEmail(rs.getString(8));
                list.add(kh);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;
            
            
    }
        
        
      public Boolean addNew(KhachHang kh) {
        String sql = "insert into KhachHang(HoTen,NgaySinh,GioiTinh,SDT,DiaChi,Email) VALUES (?,?,?,?,?,?)";
        int check;
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

          
           
         
          
            ps.setObject(1, kh.getHoTen());
            ps.setObject(2, kh.getNgaySinh());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getSdt());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.getEmail());
           

            check = ps.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }


     public Boolean update(KhachHang kh) {
        String sql = "update KhachHang set HoTen=?,NgaySinh=?,GioiTinh=?,SDT=?,DiaChi=?,Email=? where id = ?";
        int checkUpdate;
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
          
            ps.setObject(1, kh.getHoTen());
            ps.setObject(2, kh.getNgaySinh());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getSdt());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.getEmail());
            ps.setObject(7, kh.getId());
            checkUpdate = ps.executeUpdate();
            return checkUpdate > 0;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
         public Boolean xoa(String id) {
        String sql = "delete KhachHang where id=?";
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
 
    
    
   
public ArrayList<KhachHang> timKiem(String hoTen) {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "select id,MaKH,HoTen,NgaySinh,GioiTinh,SDT,DiaChi,Email FROM KhachHang where HoTen =? ";
        try (Connection con = connection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hoTen);
           
             
             

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHang kh = new KhachHang();
               
               
               
                kh.setId(UUID.fromString(rs.getString(1)));              
                kh.setMa(rs.getInt(2));
                kh.setHoTen(rs.getString(3));
                kh.setNgaySinh(rs.getString(4));
                kh.setGioiTinh(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setDiaChi(rs.getString(7));
                kh.setEmail(rs.getString(8));
                list.add(kh);
               
                            
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;
    }
}
