
package repositories;

import domainModels.Giay;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;



public class GiayRepository {
    private Connection conn;
    final String Select_All = "Select * from Giay";
    final String Insert = "insert into Giay(TenGiay) values(?)";
    final String update = "update Giay set TenGiay = ? where id =?";
    final String Delete = "Delete from Giay where id = ?";
    final String Check_Ten = "select count(*) from Giay where TenGiay=?";
    final String findByName = "Select * from Giay where TenGiay = ?";
    final String findById = "Select * from Giay where id = ?";
    final String findByMa = "Select * from Giay where maGiay = ?";
    
    public GiayRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<Giay> getAll() {

        try {
            List<Giay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
 
                
                list.add(new Giay(id, ma, ten));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int them(Giay o){
        return JDBCHelper.update(Insert, o.getTenGiay());
    }
    
    public int sua(Giay o){
        return JDBCHelper.update(update, o.getTenGiay(), o.getId());
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
    
    public Giay findByName(String ten){
        Giay o = new Giay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenG = rs.getString(3);

                o = new Giay(id, ma, tenG);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Giay findByName(UUID id){
        Giay o = new Giay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);

                o = new Giay(uuid, ma, ten);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Giay findByName(int ma){
        Giay o = new Giay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, ma);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int mag = rs.getInt(2);
                String ten = rs.getString(3);

                o = new Giay(uuid, mag, ten);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
