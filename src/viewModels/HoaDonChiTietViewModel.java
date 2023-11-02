/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModels;

import repositories.HoaDonChiTietViewModelRepositoryImpl;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class HoaDonChiTietViewModel {

    private HoaDonChiTietViewModelRepositoryImpl hd = new HoaDonChiTietViewModelRepositoryImpl();
    private UUID IdHD;
    private UUID IdChiTietSanPham;
    private UUID LoaiGiay;
    private int soLuong;
    private UUID Hang;
    private UUID MauSac;
    private UUID Size;
    private UUID CL;

    public HoaDonChiTietViewModel(String string, String string0, int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public UUID getIdHD() {
        return IdHD;
    }

    public UUID getIdChiTietSanPham() {
        return IdChiTietSanPham;
    }

    public UUID getIdMauSac() {
        return MauSac;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public UUID getIdHang() {
        return Hang;
    }

    public UUID getIdSize() {
        return Size;
    }

    public UUID getIdCL() {
        return CL;
    }

    public UUID getIdLoaiGiay() {
        return LoaiGiay;
    }

    public void setIdHD(UUID IdHD) {
        this.IdHD = IdHD;
    }

    public void setIdChiTietSanPham(UUID IdChiTietSanPham) {
        this.IdChiTietSanPham = IdChiTietSanPham;
    }

    public void setIdMauSac(UUID MauSac) {
        this.MauSac = MauSac;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setIdHang(UUID Hang) {
        this.Hang = Hang;
    }

    public void setIdSize(UUID Size) {
        this.Size = Size;
    }

    public void setIdCL(UUID CL) {
        this.CL = CL;
    }

    public void setIdLoaiGiay(UUID LoaiGiay) {
        this.LoaiGiay = LoaiGiay;
    }

    public HoaDonChiTietViewModel() {
    }

    public HoaDonChiTietViewModel(UUID IdHD, UUID IdChiTietSanPham, UUID LoaiGiay, int soLuong, UUID Hang, UUID MauSac, UUID Size, UUID CL) {
        this.IdHD = IdHD;
        this.IdChiTietSanPham = IdChiTietSanPham;
        this.LoaiGiay = LoaiGiay;
        this.soLuong = soLuong;
        this.Hang = Hang;
        this.MauSac = MauSac;
        this.Size = Size;
        this.CL = CL;

    }

    public Object[] toDataRow() {
        return new Object[]{IdHD, IdChiTietSanPham, String.valueOf(hd.getTenGiayById(LoaiGiay)), soLuong, String.valueOf(hd.getTenHangById(Hang)), String.valueOf(hd.getTenMauById(MauSac)), String.valueOf(hd.getGiaGiayById(Size)), String.valueOf(hd.getTenCLById(CL))};
    }

}
