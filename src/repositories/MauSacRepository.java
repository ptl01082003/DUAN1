
package repositories;

import domainModels.MauSac;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;


public class MauSacRepository {
    private Connection conn;
    final String Select_All = "Select * from MauSac order by mams";
    final String Insert = "insert into MauSac(tenMS) values( ?)";
    final String update = "update MauSac set tenMS=? where id =?";
    final String Delete = "Delete from MauSac where id = ?";
    final String Check_Ten = "select count(*) from MauSac where tenMS=?";
    final String findByName = "Select * from MauSac where tenMS = ?";
    final String findById = "Select * from MauSac where id = ?";
    final String findByMa = "Select * from MauSac where maMS = ?";
    final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdMauSac = ?;";

    
    public MauSacRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<MauSac> getAll() {

        try {
            List<MauSac> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);

                
                list.add(new MauSac(id, ma, ten));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int them(MauSac o){
        return JDBCHelper.update(Insert, o.getTenMS());
    }
    
    public int sua(MauSac o){
        return JDBCHelper.update(update, o.getTenMS(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
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
    
    public MauSac findByName(String ten){
        MauSac o = new MauSac();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenMauSac = rs.getString(3);
                o = new MauSac(id, ma, tenMauSac);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public MauSac findById(UUID id){
        MauSac o = new MauSac();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenMS = rs.getString(3);
                o = new MauSac(uuid, ma, tenMS);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public MauSac findByMa(int id){
        MauSac o = new MauSac();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenMS = rs.getString(3);
                o = new MauSac(uuid, ma, tenMS);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
