/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModels;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author giang
 */
public class HoaDonViewModel {
    private String IdHD;
    private String maHD;
    private String IdNV;
    private String IdKH;
    private String idGG;
    private Date ngayTao;
    private Date ngayThanhToan;
    private BigDecimal tienGoc;   
    private BigDecimal tongTienSauGiamGia;
    private int hinhThucThanhToan;
    private int tinhTrang;
    private String ghiChu;

    public HoaDonViewModel() {
    }

    public HoaDonViewModel(String IdHD, String maHD, String IdNV, String IdKH, String idGG, Date ngayTao, Date ngayThanhToan, BigDecimal tienGoc, BigDecimal tongTienSauGiamGia, int hinhThucThanhToan, int tinhTrang, String ghiChu) {
        this.IdHD = IdHD;
        this.maHD = maHD;
        this.IdNV = IdNV;
        this.IdKH = IdKH;
        this.idGG = idGG;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.tienGoc = tienGoc;
        this.tongTienSauGiamGia = tongTienSauGiamGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
    }

    public String getIdHD() {
        return IdHD;
    }

    public void setIdHD(String IdHD) {
        this.IdHD = IdHD;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getIdNV() {
        return IdNV;
    }

    public void setIdNV(String IdNV) {
        this.IdNV = IdNV;
    }

    public String getIdKH() {
        return IdKH;
    }

    public void setIdKH(String IdKH) {
        this.IdKH = IdKH;
    }

    public String getIdGG() {
        return idGG;
    }

    public void setIdGG(String idGG) {
        this.idGG = idGG;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public BigDecimal getTienGoc() {
        return tienGoc;
    }

    public void setTienGoc(BigDecimal tienGoc) {
        this.tienGoc = tienGoc;
    }

    public BigDecimal getTongTienSauGiamGia() {
        return tongTienSauGiamGia;
    }

    public void setTongTienSauGiamGia(BigDecimal tongTienSauGiamGia) {
        this.tongTienSauGiamGia = tongTienSauGiamGia;
    }

    public int getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(int hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

   
    
}
