/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import repositories.HoaDonRepositoryImpl;
import utilities.Support;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class HoaDon {
    private HoaDonRepositoryImpl hd = new HoaDonRepositoryImpl();
    private UUID id;
    private int maHD;
    private UUID idNV;
    private UUID idKH;
    private UUID idGiamGia;
    private String ngayTao;
    private String ngayThanhToan;
    private double tongTien;
    private double tongTienSauGiam;
    private boolean hinhThuc;
    private int trangThai;
    private String ghiChu;

    public HoaDon(UUID id, int maHD, UUID idNV, UUID idKH, UUID idGiamGia, String ngayTao, String ngayThanhToan, double tongTien, double tongTienSauGiam, boolean hinhThuc, int trangThai, String ghiChu) {
        this.id = id;
        this.maHD = maHD;
        this.idNV = idNV;
        this.idKH = idKH;
        this.idGiamGia = idGiamGia;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.tongTienSauGiam = tongTienSauGiam;
        this.hinhThuc = hinhThuc;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }
    
    public Object[] toDataRow() {
        Object mucGG = hd.getMucGGById(idGiamGia);
        if (mucGG == null) {
            mucGG = 0;
        }
        return new Object[]{
            String.valueOf(id),
            maHD,
            hd.getTenNVById(idNV),
            hd.getTenKHById(idKH),
            mucGG,
            ngayTao,
            ngayThanhToan,
            tongTien,
            tongTienSauGiam,
            hinhThuc == true ? "Tiền Mặt" : "Tiền tài khoản",
            trangThai == 0 ? "Chờ" : "Đã thanh toán",
            ghiChu
        };
    }
    
}

