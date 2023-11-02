/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModels.ChatLieu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import utilities.JDBCHelper;


/**
 *
 * @author ACER
 */
public class ChatLieuRepository {
    private Connection conn;
    private final String Select_All_CL = "Select * from chatlieu order by maChatlieu";
    private final String InsertCL = "Insert into chatlieu(chatLieuThan, chatlieuDe) values(?, ?)";
    private final String updateCL = "Update chatlieu set chatlieuDe = ? , chatLieuthan=? where id = ?";
    private final String DeleteCL = "Delete from chatlieu where id = ?";
    private final String Check_CLThan = "select count(*) from chatlieu where chatLieuThan=?";
    private final String Check_CLDe = "select count(*) from chatlieu where chatLieuDe=?";
    private final String Check_CL = "select count(*) from chatlieu where chatLieuDe=? and chatlieuthan = ?";
    private final String listThan = "select distinct chatLieuthan from chatlieu";
    private final String listDe = "select distinct chatLieude from chatlieu";
    private final String findCL = "select * from chatLieu where chatlieuthan = ? and chatlieude = ?";
    private final String findById = "select * from chatlieu where Id=?";
    private final String findByMa = "select * from chatlieu where maChatLieu=?";
    private final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdCL = ?";
    
    public ChatLieuRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<ChatLieu> getAll() {

        try {
            List<ChatLieu> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All_CL);
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String chatlieuThan = rs.getString(3);
                String chatLieuDe = rs.getString(4);
                
                ChatLieu cl = new ChatLieu();
                cl.setId(uuid);
                cl.setMaChatLieu(ma);
                cl.setChatLieuDe(chatLieuDe);
                cl.setChatLieuThan(chatlieuThan);
                listCL.add(cl);
            }
            return listCL;
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
    
    public List<String> getAllCLThan() {

        try {
            List<String> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(listThan);
            while (rs.next()) {
                String chatlieuThan = rs.getString(1);

                listCL.add(chatlieuThan);
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllCLDe() {

        try {
            List<String> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(listDe);
            while (rs.next()) {
                String chatlieuDe = rs.getString(1);

                listCL.add(chatlieuDe);
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int themCL(ChatLieu cl){
        return JDBCHelper.update(InsertCL, cl.getChatLieuThan(), cl.getChatLieuDe());
    }
    
    public int suaCL(ChatLieu cl){
        return JDBCHelper.update(updateCL, cl.getChatLieuThan(), cl.getChatLieuDe(), cl.getId());
    }
    
    public int xoaCL(UUID id){
        return JDBCHelper.update(DeleteCL, id);
    }
    
    public int checkTenCLThan(String clThan){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_CLThan, clThan);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
     public int checkTenCLDe(String clDe){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_CLDe, clDe);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
     
     public int checkTenCL(String clDe, String clThan){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_CL, clDe, clThan);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public ChatLieu findByCL(String than, String de){
        ChatLieu cl = new ChatLieu();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findCL, than, de);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String clThan = rs.getString(3);
                String clDe = rs.getString(4);
                cl = new ChatLieu(id, ma, clThan, clDe);
            }
                return cl;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ChatLieu findById(UUID id){
        ChatLieu cl = new ChatLieu();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String clThan = rs.getString(3);
                String clDe = rs.getString(4);
                cl = new ChatLieu(uuid, ma, clThan, clDe);
            }
                return cl;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ChatLieu findByMa(int id){
        ChatLieu cl = new ChatLieu();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String clThan = rs.getString(3);
                String clDe = rs.getString(4);
                cl = new ChatLieu(uuid, ma, clThan, clDe);
            }
                return cl;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
