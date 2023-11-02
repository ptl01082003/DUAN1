
package repositories;

import domainModels.Hang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;



public class HangRepository {
    private Connection conn;
    final String Select_All = "Select * from Hang order by mahang";
    final String Insert = "insert into Hang(TenHang) values( ?)";
    final String update = "update Hang set TenHang=? where id =?";
    final String Delete = "Delete from Hang where id = ?";
    final String Check_Ten = "select count(*) from Hang where tenHang=?";
    final String findByName = "Select * from Hang where tenHang = ?";
    final String findById = "Select * from Hang where id = ?";
    final String findByMa = "Select * from Hang where maHang = ?";
    final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdHang = ?;";
    
    public HangRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<Hang> getAll() {

        try {
            List<Hang> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);

                
                list.add(new Hang(id, ma, ten));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int them(Hang o){
        return JDBCHelper.update(Insert, o.getTenHang());
    }
    
    public int sua(Hang o){
        return JDBCHelper.update(update, o.getTenHang(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
    }
    
    public int checkTruocXoa(UUID id){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(checkXoa, id);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int checkTen(String ten){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_Ten, ten);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public Hang findByName(String ten){
        Hang o = new Hang();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenHang = rs.getString(3);
                o = new Hang(id, ma, tenHang);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Hang findById(UUID id){
        Hang o = new Hang();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenHang = rs.getString(3);
                o = new Hang(uuid, ma, tenHang);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Hang findByMa(int id){
        Hang o = new Hang();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenHang = rs.getString(3);
                o = new Hang(uuid, ma, tenHang);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
