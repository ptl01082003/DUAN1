/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModels;

import utilities.Support;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class GiamGia {

    private String id;
    private int maGiamGia;
    private boolean hinhThucGiam;
    private int mucGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private boolean trangThai;
    private String moTa;

    public GiamGia(String id, int maGiamGia, boolean hinhThucGiam, int mucGiam, Date ngayBatDa, Date ngayKetThuc, boolean trangThai, String moTa) {
        this.id = id;
        this.maGiamGia = maGiamGia;
        this.hinhThucGiam = hinhThucGiam;
        this.mucGiam = mucGiam;

        this.ngayBatDau = ngayBatDa;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    public GiamGia() {
    }

    public GiamGia(String id, boolean hinhThucGiam, int muc, Date ngBD, Date ngKT, String moTa) {
        this.id = id;

        this.hinhThucGiam = hinhThucGiam;
        this.mucGiam = muc;
        this.ngayBatDau = ngBD;
        this.ngayKetThuc = ngKT;

        this.moTa = moTa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(int maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public boolean isHinhThucGiam() {
        return hinhThucGiam;
    }

    public void setHinhThucGiam(boolean hinhThucGiam) {
        this.hinhThucGiam = hinhThucGiam;
    }

    public int getMucGiam() {
        return mucGiam;
    }

    public void setMucGiam(int mucGiam) {
        this.mucGiam = mucGiam;
    }

    public Date getNgayBatDa() {
        return ngayBatDau;
    }

    public void setNgayBatDa(Date ngayBatDa) {
        this.ngayBatDau = ngayBatDa;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Object[] toDataRow() {
//        SimpleDateFormat dateFomat = new SimpleDateFormat("dd/MM/yyyy");
        return new Object[]{id, maGiamGia, mucGiam, hinhThucGiam == true ? " VND" : "%", Support.toString(ngayBatDau, "dd-MM-yyyy"), Support.toString(ngayKetThuc, "dd-MM-yyyy"), trangThai == true ? "Hoạt động" : "Không hoạt động", moTa};
    }
}
