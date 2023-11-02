/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package serviceImpl;

import domainModels.ChiTietGiay;
import domainModels.GiamGia;
import domainModels.HoaDon;
import domainModels.HoaDonChiTiet;
import domainModels.KhachHang;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ACER
 */
public interface BanHangImpl {

    public void TaoHD(UUID idNV, UUID idKH, UUID idGG);

    public List<ChiTietGiay> getAllCTG();

    public List<HoaDon> getAllHDCho();

    public List<HoaDonChiTiet> getAllHDCT(UUID id);

    public void huyHD(UUID idHD);

    public int getMaNVFromID(UUID id);

    public KhachHang getKH(UUID idKH);

    public HoaDon getHDByMa(int maHD);

    public void themSPVaoGio(UUID idHD, UUID idCTG, int sl);

    public int checkCTGInHDCT(UUID idCTG, UUID idHD);

    public void themSLSPVaoGio(UUID idCTG, UUID idHD);

    public void xoaTatCaSPHDCT(UUID idHD);

    public void xoa1SPHDCT(UUID idHD, UUID idCTG);

    public void suaTongTien(double tongTien, UUID idHD);

    public void suaTongTienSauGiamGia(double tongTien, UUID idHD);

    public void suaGhiChuVaHinhThuc(String ghiChu, boolean hinhThuc, UUID idHD);

    public double getTongTien(UUID idHD);

    public void suaTrangThai(UUID idHD);

    public void suaSLSP(UUID idHD);

    public int checkSLTonSP(UUID idHD, UUID idSP);

    public List<ChiTietGiay> getAllCTGLess(String s);

    public List<ChiTietGiay> getAllCTGGreater(String s);

    public List<ChiTietGiay> getAllCTGEqual(String s);

    public List<ChiTietGiay> getAllCTGByName(String s);

    public void suaKH(UUID idKH, UUID idHD);

    public UUID getIdKH(int maKH);

    public GiamGia getGiamGia();

    public int mucGiamFromHD(UUID idHD);

    public int getSLSPInHDCT(UUID idHD);

    public void suaNV(UUID idNV, UUID idHD);

    public int checkSLTonSP0(UUID idSP);
}
