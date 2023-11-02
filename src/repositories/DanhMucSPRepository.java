/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModels.ChiTietGiay;
import domainModels.DanhMucSanPham;
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
public class DanhMucSPRepository {
    private Connection conn;
    final String Select_All = "Select * from DanhMucSanPham order by madanhmuc";
    final String Insert = "insert into DanhMucSanPham(TenDanhMuc, MoTa) values(?, ?)";
    final String update = "update DanhMucSanPham set TenDanhMuc = ?, MoTa =? where id =?";
    final String Delete = "Delete from DanhMucSanPham where id = ?";
    final String Check_Ten = "select count(*) from DanhMucSanPham where tenDanhMuc=?";
    final String findByName = "Select * from DanhMucSanPham where tenDanhMuc = ?";
    final String findById = "Select * from DanhMucSanPham where id = ?";
    final String findByMa = "Select * from DanhMucSanPham where maDanhmuc = ?";
    final String checkXoa = "SELECT COUNT(*) FROM chitietgiay WHERE IdDanhMuc = ?;";

    
    public DanhMucSPRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<DanhMucSanPham> getAll() {

        try {
            List<DanhMucSanPham> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                
                String moTa = rs.getString(4);
                
                list.add(new DanhMucSanPham(id, ma, ten, moTa));
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
    
    public int them(DanhMucSanPham o){
        return JDBCHelper.update(Insert, o.getTenDanhMuc(), o.getMoTa());
    }
    
    public int sua(DanhMucSanPham o){
        return JDBCHelper.update(update, o.getTenDanhMuc(), o.getMoTa(), o.getId());
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
    
    public DanhMucSanPham findByName(String ten){
        DanhMucSanPham o = new DanhMucSanPham();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByName, ten);
            while (rs.next()) {

                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenDM = rs.getString(3);
                String moTa = rs.getString(4);
                o = new DanhMucSanPham(id, ma, tenDM, moTa);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public DanhMucSanPham findByName(UUID id){
        DanhMucSanPham o = new DanhMucSanPham();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findById, id);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String tenDM = rs.getString(3);
                String moTa = rs.getString(4);
                o = new DanhMucSanPham(uuid, ma, tenDM, moTa);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public DanhMucSanPham findByName(int ma){
        DanhMucSanPham o = new DanhMucSanPham();
        try {
            ResultSet rs = JDBCHelper.getResultSet(findByMa, ma);
            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString(1));
                int madm = rs.getInt(2);
                String tenDM = rs.getString(3);
                String moTa = rs.getString(4);
                o = new DanhMucSanPham(uuid, madm, tenDM, moTa);
            }
                return o;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
