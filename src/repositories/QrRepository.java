
package repositories;


import domainModels.QR;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;


public class QrRepository {
     private Connection conn;
    final String Select_All = "Select * from QR";
    final String Insert = "insert into QR(AnhQR) values(?)";
    final String update = "update QR set AnhQR=? where id =?";
    final String Delete = "Delete from QR where id = ?";
    final String findById = "Select * from QR where id = ?";
    final String findIDByQR = "select * from QR where AnhQR = ?";
    

    
    public QrRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<QR> getAll() {

        try {
            List<QR> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);

                
                list.add(new QR(id, ma, ten));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int them(QR o){
        return JDBCHelper.update(Insert, o.getAnhQR());
    }
    
    public int sua(QR o){
        return JDBCHelper.update(update, o.getAnhQR(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
    }
    
    public QR findById(UUID id){
        QR o = new QR();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                 String tenQR = rs.getString(2);
                int ma = rs.getInt(3);
                o = new QR(uuid, ma, tenQR);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public QR findByQR(String id){
        QR o = new QR();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findIDByQR, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                 String tenQR = rs.getString(2);
                int ma = rs.getInt(3);
                o = new QR(uuid, ma, tenQR);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
