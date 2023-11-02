/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModels.ChiTietGiay;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JFileChooser;
import utilities.JDBCHelper;
import org.apache.poi.ss.usermodel.*;



/**
 *
 * @author ACER
 */
public class CTGiayRepository {
    private Connection conn;
    final String Select_All = "Select * from chitietGiay";
    final String Insert = "insert into chitietGiay(tenCTGiay, IDGiay, IdQR, IdMauSac, IDHang, IdSize, IdCl, IDLoaiGiay, IDDanhMuc, SoLuong, GiaNhap, GiaBan, anh, trangthai, mota) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String update = "update ChiTietGiay set tenCTGiay = ?, IDGiay =?, IdMauSac=?, IDHang=?, IdSize=?, IdCl=?, IDLoaiGiay=?, IDDanhMuc=?, SoLuong=?, GiaNhap=?, GiaBan=?, anh=?, trangthai=?, mota=? where id =?";
    final String Delete = "Delete from chitietgiay where id = ?";
    final String Check_TenCTGiay = "select count(*) from ChiTietGiay where tenCTGiay=?";
    final String findIDByMa = "select id from chitietGiay where maCTGiay = ?";
    final String searchTen = "select * from chitietGiay where tenCTGiay like '%'+?+'%'";
    final String searchMa = "select * from chitietGiay where CAST(maCTGiay  AS VARCHAR) like '%'+?+'%'";

    
    public CTGiayRepository() {
        conn = JDBCHelper.getConnection();
    }
    
    public List<ChiTietGiay> getAll() {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(Select_All);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idGiay = UUID.fromString(rs.getString(4));
                 UUID idQR = null;
                if(rs.getString(5)!=null){
                    idQR = UUID.fromString(rs.getString(5));
                }
                
                UUID idMS = UUID.fromString(rs.getString(6));
                UUID idHang = UUID.fromString(rs.getString(7));
                UUID idSize = UUID.fromString(rs.getString(8));
                UUID idCL = UUID.fromString(rs.getString(9));
                UUID idLoaiGiay = UUID.fromString(rs.getString(10));
                UUID idDM = UUID.fromString(rs.getString(11));
                int sl = rs.getInt(12);
                double gNhap = rs.getDouble(13);
                double gBan = rs.getDouble(14);
                String anh = rs.getString(15);
                int trangThai = rs.getInt(16);
                String moTa = rs.getString(17);
                
                listCL.add(new ChiTietGiay(id, ma, ten, idGiay, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<ChiTietGiay> getAllByTen(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchTen, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idGiay = UUID.fromString(rs.getString(4));
                 UUID idQR = null;
                if(rs.getString(5)!=null){
                    idQR = UUID.fromString(rs.getString(5));
                }
                
                UUID idMS = UUID.fromString(rs.getString(6));
                UUID idHang = UUID.fromString(rs.getString(7));
                UUID idSize = UUID.fromString(rs.getString(8));
                UUID idCL = UUID.fromString(rs.getString(9));
                UUID idLoaiGiay = UUID.fromString(rs.getString(10));
                UUID idDM = UUID.fromString(rs.getString(11));
                int sl = rs.getInt(12);
                double gNhap = rs.getDouble(13);
                double gBan = rs.getDouble(14);
                String anh = rs.getString(15);
                int trangThai = rs.getInt(16);
                String moTa = rs.getString(17);
                
                listCL.add(new ChiTietGiay(id, ma, ten, idGiay, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<ChiTietGiay> getAllByMa(String tens) {

        try {
            ArrayList<ChiTietGiay> listCL = new ArrayList<>();
            ResultSet rs = JDBCHelper.getResultSet(searchMa, tens);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString(1));
                int ma = rs.getInt(2);
                String ten = rs.getString(3);
                UUID idGiay = UUID.fromString(rs.getString(4));
                 UUID idQR = null;
                if(rs.getString(5)!=null){
                    idQR = UUID.fromString(rs.getString(5));
                }
                
                UUID idMS = UUID.fromString(rs.getString(6));
                UUID idHang = UUID.fromString(rs.getString(7));
                UUID idSize = UUID.fromString(rs.getString(8));
                UUID idCL = UUID.fromString(rs.getString(9));
                UUID idLoaiGiay = UUID.fromString(rs.getString(10));
                UUID idDM = UUID.fromString(rs.getString(11));
                int sl = rs.getInt(12);
                double gNhap = rs.getDouble(13);
                double gBan = rs.getDouble(14);
                String anh = rs.getString(15);
                int trangThai = rs.getInt(16);
                String moTa = rs.getString(17);
                
                listCL.add(new ChiTietGiay(id, ma, ten, idGiay, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
            }
            return listCL;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int them(ChiTietGiay o){
        return JDBCHelper.update(Insert, o.getTenCTGiay(), o.getGiay(), o.getQr(), o.getMs(), o.getHang(), o.getSize(), o.getCl(), o.getLoaiGiay(),o.getDanhMuc(), o.getSoLuong(), o.getGiaNhap(), o.getGiaBan(), o.getAnh(), o.getTrangThai(), o.getMoTa());
    }
    
    public int sua(ChiTietGiay o){
        return JDBCHelper.update(update, o.getTenCTGiay(), o.getGiay(), o.getMs(), o.getHang(), o.getSize(), o.getCl(), o.getLoaiGiay(),o.getDanhMuc(), o.getSoLuong(), o.getGiaNhap(), o.getGiaBan(), o.getAnh(), o.getTrangThai(), o.getMoTa(), o.getId());
    }
    
    public int xoa(UUID id){
        return JDBCHelper.update(Delete, id);
    }
    
    public int checkTenGiay(String ten){
        int check = 0;

        try {
            ResultSet rs = JDBCHelper.getResultSet(Check_TenCTGiay, ten);
            while (rs.next()) {

                check = rs.getInt(1);
            }
            return check;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public UUID findByQR(String id){
        UUID uuid = null;
        try {
            ResultSet rs = JDBCHelper.getResultSet(findIDByMa, id);
            while (rs.next()) {

                uuid = UUID.fromString(rs.getString(1));
                
               
               
            }
                 return uuid;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
            
    }
    
    public void importExcel(){
            String excelFilePath = "";
            JFileChooser chooser = new JFileChooser();
//         FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "xlsx", "xlm", "xls", "xlsm");
//        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            File f = chooser.getSelectedFile();
            excelFilePath = f.getAbsolutePath();
            

        }
        if(excelFilePath==null){
            return;
        }
        try{
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String ten = row.getCell(0).getStringCellValue();
                UUID idGiay = UUID.fromString( row.getCell(1).getStringCellValue());
                UUID idQR = null;
                UUID idMs = UUID.fromString( row.getCell(3).getStringCellValue());
                UUID idH = UUID.fromString( row.getCell(4).getStringCellValue());
                UUID idS = UUID.fromString( row.getCell(5).getStringCellValue());
                UUID idCL = UUID.fromString( row.getCell(6).getStringCellValue());
                UUID idLG = UUID.fromString( row.getCell(7).getStringCellValue());
                UUID idDM = UUID.fromString( row.getCell(8).getStringCellValue());
                int sl = (int) row.getCell(9).getNumericCellValue();
                double gNhap = (double) row.getCell(10).getNumericCellValue();
                double gBan = (double) row.getCell(11).getNumericCellValue();
                String anh = row.getCell(12).getStringCellValue();
                int tt = (int)row.getCell(13).getNumericCellValue();
                String moTa = row.getCell(14).getStringCellValue();

                JDBCHelper.update(Insert, ten, idGiay, idQR, idMs, idH , idS, idCL, idLG, idDM, sl, gNhap, gBan, anh, tt, moTa);
            }

            System.out.println("Data inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
