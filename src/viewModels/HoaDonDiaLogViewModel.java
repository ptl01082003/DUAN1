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
public class HoaDonDiaLogViewModel {

    private String IdHD;
    private String maHD;
    private String IdNV;
    private String MaNV;
    private String TenNV;
    private String IdKH;
    private String MaKH;
    private String TenKH;
    private String idGG;
    private int HinhThuc;
    private int MucGiam;
    private Date ngayTao;
    private Date ngayThanhToan;
    private BigDecimal tienGoc;
    private BigDecimal tongTienSauGiamGia;
    private int hinhThucThanhToan;
    private int tinhTrang;
    private String ghiChu;
    private String tenCTGiay;
    private double giaBan;
    private int soLuong;

    public HoaDonDiaLogViewModel() {
    }

    public HoaDonDiaLogViewModel(String IdHD, String maHD, String IdNV, String MaNV, String TenNV, String IdKH, String MaKH, String TenKH, String idGG, int HinhThuc, int MucGiam, Date ngayTao, Date ngayThanhToan, BigDecimal tienGoc, BigDecimal tongTienSauGiamGia, int hinhThucThanhToan, int tinhTrang, String ghiChu, String tenCTGiay, double giaBan, int soLuong) {
        this.IdHD = IdHD;
        this.maHD = maHD;
        this.IdNV = IdNV;
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.IdKH = IdKH;
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.idGG = idGG;
        this.HinhThuc = HinhThuc;
        this.MucGiam = MucGiam;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.tienGoc = tienGoc;
        this.tongTienSauGiamGia = tongTienSauGiamGia;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
        this.tenCTGiay = tenCTGiay;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
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

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getIdKH() {
        return IdKH;
    }

    public void setIdKH(String IdKH) {
        this.IdKH = IdKH;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getIdGG() {
        return idGG;
    }

    public void setIdGG(String idGG) {
        this.idGG = idGG;
    }

    public int getHinhThuc() {
        return HinhThuc;
    }

    public void setHinhThuc(int HinhThuc) {
        this.HinhThuc = HinhThuc;
    }

    public int getMucGiam() {
        return MucGiam;
    }

    public void setMucGiam(int MucGiam) {
        this.MucGiam = MucGiam;
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

    public String getTenCTGiay() {
        return tenCTGiay;
    }

    public void setTenCTGiay(String tenCTGiay) {
        this.tenCTGiay = tenCTGiay;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

   
}
