
package repositories;


import domainModels.LoaiGiay;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;



public class LoaiGiayRepository {
    private Connection conn;
    final String Select_All = "Select * from LoaiGiay";
    final String Insert = "insert into LoaiGiay(tenLoaiGiay) values( ?)";
    final String update = "update LoaiGiay set tenLoaiGiay=? where id =?";
    final String Delete = "Delete from LoaiGiay where id = ?";
    final String Check_Ten = "select count(*) from LoaiGiay where tenLoaiGiay=?";
    final String findByName = "Select * from LoaiGiay where tenLoaiGiay = ?";
    final String findById = "Select * from LoaiGiay where id = ?";
    final String findByMa = "Select * from LoaiGiay where maLoaiGiay = ?";
    final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdLoaiGiay = ?;";
    
    public LoaiGiayRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<LoaiGiay> getAll() {

        try {
            List<LoaiGiay> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);

                
                list.add(new LoaiGiay(id, ma, ten));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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
    
    public int them(LoaiGiay o){
        return JDBCHelper.update(Insert, o.getTenLoaiGiay());
    }
    
    public int sua(LoaiGiay o){
        return JDBCHelper.update(update, o.getTenLoaiGiay(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
    }
    
    public int checkTenCL(String ten){
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
    
    public LoaiGiay findByName(String ten){
        LoaiGiay o = new LoaiGiay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenLoaiGiay = rs.getString(3);
                o = new LoaiGiay(id, ma, tenLoaiGiay);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public LoaiGiay findById(UUID id){
        LoaiGiay o = new LoaiGiay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenLoaiGiay = rs.getString(3);
                o = new LoaiGiay(uuid, ma, tenLoaiGiay);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public LoaiGiay findByMa(int id){
        LoaiGiay o = new LoaiGiay();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenLoaiGiay = rs.getString(3);
                o = new LoaiGiay(uuid, ma, tenLoaiGiay);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
