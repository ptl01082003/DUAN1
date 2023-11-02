
package repositories;
import java.sql.*;
import utilities.JDBCHelper;

public class DangNhapRepository {
    private Connection conn;
    private final String EXISTS_TaiKhoan = "Select count(*) from nhanvien where email = ? and matkhau = ?";
    private final String EXISTS_Email = "Select count(*) from nhanvien where email = ?";
    private final String Select_name = "select HoTen from nhanvien where email = ? and matkhau = ?";
    private final String Select_MK = "select matKhau from nhanvien where email = ?";
    private final String Select_chucVu = "Select chucvu from nhanvien where email = ? and matKhau = ?";
    private final String Select_id = "Select id from nhanvien where email = ? and matKhau = ?";
    
    public DangNhapRepository() {
        this.conn = JDBCHelper.getConnection();
    }
    
    public int check_Exists(String email, String mk){
        int check = 0;
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(EXISTS_TaiKhoan, email, mk);
            while(rs.next()){
                check = rs.getInt(1);
            }
            return check;
        } catch (Exception e) {
            e.printStackTrace();
            return check;
        }
    }
    
    public int check_Exists_Email(String email){
        int check = 0;
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(EXISTS_Email, email);
            while(rs.next()){
                check = rs.getInt(1);
            }
            return check;
        } catch (Exception e) {
            e.printStackTrace();
            return check;
        }
    }
    
    public String getName(String email, String mk){
        String ten = "";
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(Select_name, email, mk);
            while(rs.next()){
                ten = rs.getString(1);
            }
            return ten;
        } catch (Exception e) {
            e.printStackTrace();
            return ten;
        }
    }
    
    public String getMK(String email){
        String mk = "";
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(Select_MK, email);
            while(rs.next()){
                mk = rs.getString(1);
            }
            return mk;
        } catch (Exception e) {
            e.printStackTrace();
            return mk;
        }
    }
    
    public String getChucVu(String email, String mk){
        String cv = "";
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(Select_chucVu, email, mk);
            while(rs.next()){
                cv = rs.getString(1);
            }
            return cv;
        } catch (Exception e) {
            e.printStackTrace();
            return cv;
        }
    }
    
    public String getId(String email, String mk){
        String id = "";
        
        try {
            ResultSet rs = JDBCHelper.getResultSet(Select_id, email, mk);
            while(rs.next()){
                id = rs.getString(1);
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }
}
