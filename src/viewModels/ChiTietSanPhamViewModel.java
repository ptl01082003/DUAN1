/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModels;

import domainModels.Hang;

/**
 *
 * @author giang
 */
public class ChiTietSanPhamViewModel {
    private String id;
    
    private int maCTGiay;

    private String tenCTGiay;

    private String giay;

    private String qr;
    
    private String ms;

    private Hang hang;

    private String size;

    private String cl;

    private String loaiGiay;

    private String danhMuc;

    private int soLuong;

    private double giaNhap;

    private double giaBan;
    
    private String anh;

    private String moTa;

    private int trangThai;

    public ChiTietSanPhamViewModel() {
    }

    public ChiTietSanPhamViewModel(String id, int maCTGiay, String tenCTGiay, String giay, String qr, String ms, Hang hang, String size, String cl, String loaiGiay, String danhMuc, int soLuong, double giaNhap, double giaBan, String anh, String moTa, int trangThai) {
        this.id = id;
        this.maCTGiay = maCTGiay;
        this.tenCTGiay = tenCTGiay;
        this.giay = giay;
        this.qr = qr;
        this.ms = ms;
        this.hang = hang;
        this.size = size;
        this.cl = cl;
        this.loaiGiay = loaiGiay;
        this.danhMuc = danhMuc;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.anh = anh;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaCTGiay() {
        return maCTGiay;
    }

    public void setMaCTGiay(int maCTGiay) {
        this.maCTGiay = maCTGiay;
    }

    public String getTenCTGiay() {
        return tenCTGiay;
    }

    public void setTenCTGiay(String tenCTGiay) {
        this.tenCTGiay = tenCTGiay;
    }

    public String getGiay() {
        return giay;
    }

    public void setGiay(String giay) {
        this.giay = giay;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public Hang getHang() {
        return hang;
    }

    public void setHang(Hang hang) {
        this.hang = hang;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getLoaiGiay() {
        return loaiGiay;
    }

    public void setLoaiGiay(String loaiGiay) {
        this.loaiGiay = loaiGiay;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

     public Object[] toDataRow() {
        return new Object[]{hang.getMaHang(), hang.getTenHang()};
    }
}
