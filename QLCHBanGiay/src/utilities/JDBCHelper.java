/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class JDBCHelper {

    private static Connection conn;
    private final static String url = "jdbc:sqlserver://localhost:1433;databaseName=QLCHBanGiay";
    private final static String user = "sa";
    private final static String pass = "12345";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement getPreparedStatement(String sql, Object... args) {
        conn = getConnection();
        try {
            PreparedStatement ps;

            if(sql.trim().startsWith("{")){
                ps = conn.prepareCall(sql);
            } else {
                ps = conn.prepareStatement(sql);
            }
            
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static int update(String sql, Object...args){
        PreparedStatement ps = getPreparedStatement(sql, args);
        try {
            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static ResultSet getResultSet(String sql, Object...args){
        PreparedStatement ps = getPreparedStatement(sql, args);
        try {
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
