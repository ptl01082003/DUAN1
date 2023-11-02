
package repositories;

import domainModels.Size;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;


public class SizeRepository {
    private Connection conn;
    final String Select_All = "Select * from Size order by sizegiay";
    final String Insert = "insert into Size(Sizegiay) values( ?)";
    final String update = "update Size set Sizegiay=? where id =?";
    final String Delete = "Delete from Size where id = ?";
    final String Check_Ten = "select count(*) from Size where Sizegiay=?";
    final String findByName = "Select * from Size where Sizegiay = ?";
    final String findById = "Select * from Size where id = ?";
    final String findByMa = "Select * from Size where MaSize = ?";
    final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdSize = ?;";

    
    public SizeRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<Size> getAll() {

        try {
            List<Size> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                int ten = rs.getInt(3);

                
                list.add(new Size(id, ma, ten));
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
    
    public int them(Size o){
        return JDBCHelper.update(Insert, o.getSizeGiay());
    }
    
    public int sua(Size o){
        return JDBCHelper.update(update, o.getSizeGiay(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
    }
    
    public int checkSize(int ten){
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
    
    public Size findByName(int ten){
        Size o = new Size();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                int tenSize = rs.getInt(3);
                o = new Size(id, ma, tenSize);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Size findById(UUID id){
        Size o = new Size();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                 int tenSize = rs.getInt(3);
                o = new Size(uuid, ma, tenSize);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Size findByMa(int id){
        Size o = new Size();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                 int tenSize = rs.getInt(3);
                o = new Size(uuid, ma, tenSize);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
