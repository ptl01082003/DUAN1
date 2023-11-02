/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModels;

import java.math.BigDecimal;
import java.util.Date;
import domainModels.KhachHang;
import domainModels.NhanVien;

/**
 *
 * @author giang
 */
public class HoaDonTKViewModel {
     private String id;
     private Date ngayThanhToan;
     private String maHD;
     private String tenNV;//join voi nv
     private String tenKH;//join voi kh
     private int tongHD;
     private int tongKH;
     private BigDecimal tongTien;
     private int trangThai;

    public HoaDonTKViewModel() {
    }

    public HoaDonTKViewModel(String id, Date ngayThanhToan, String maHD, String tenNV, String tenKH, int tongHD, int tongKH, BigDecimal tongTien, int trangThai) {
        this.id = id;
        this.ngayThanhToan = ngayThanhToan;
        this.maHD = maHD;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tongHD = tongHD;
        this.tongKH = tongKH;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getTongHD() {
        return tongHD;
    }

    public void setTongHD(int tongHD) {
        this.tongHD = tongHD;
    }

    public int getTongKH() {
        return tongKH;
    }

    public void setTongKH(int tongKH) {
        this.tongKH = tongKH;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    
    
}
