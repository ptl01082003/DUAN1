/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import domainModels.ChiTietGiay;
import domainModels.GiamGia;
import domainModels.HoaDon;
import domainModels.HoaDonChiTiet;
import domainModels.KhachHang;
import java.util.List;
import java.util.UUID;
import repositories.BanHangRepository;
import serviceImpl.BanHangImpl;

/**
 *
 * @author ACER
 */
public class BanHangService implements BanHangImpl {
    
    private BanHangRepository repo;
    
    public BanHangService() {
        repo = new BanHangRepository();
    }
    
    @Override
    public void TaoHD(UUID idNV, UUID idKH, UUID idGG) {
        repo.TaoHD(idNV, idKH, idGG);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTG() {
        return repo.getAllCTG();
    }
    
    @Override
    public List<HoaDon> getAllHDCho() {
        return repo.getAllHDCho();
    }
    
    @Override
    public List<HoaDonChiTiet> getAllHDCT(UUID id) {
        return repo.getAllHDCT(id);
    }
    
    @Override
    public void huyHD(UUID idHD) {
        repo.huyHD(idHD);
    }
    
    @Override
    public int getMaNVFromID(UUID id) {
        return repo.getMaNVFromID(id);
    }
    
    @Override
    public KhachHang getKH(UUID id) {
        return repo.getKH(id);
    }
    
    @Override
    public HoaDon getHDByMa(int maHD) {
        return repo.getHDByMa(maHD);
    }
    
    @Override
    public void themSPVaoGio(UUID idHD, UUID idCTG, int sl) {
        repo.themSPVaoGio(idHD, idCTG, sl);
    }
    
    @Override
    public int checkCTGInHDCT(UUID idCTG, UUID idHD) {
        return repo.checkCTGInHDCT(idCTG, idHD);
    }
    
    @Override
    public void themSLSPVaoGio(UUID idCTG, UUID idHD) {
        repo.themSLSPVaoGio(idCTG, idHD);
    }
    
    @Override
    public void xoaTatCaSPHDCT(UUID idHD) {
        repo.xoaTatCaSPHDCT(idHD);
    }
    
    @Override
    public void xoa1SPHDCT(UUID idHD, UUID idCTG) {
        repo.xoa1SPHDCT(idHD, idCTG);
    }
    
    @Override
    public void suaTongTien(double tongTien, UUID idHD) {
        repo.suaTongTienGoc(tongTien, idHD);
    }
    
    @Override
    public void suaGhiChuVaHinhThuc(String ghiChu, boolean hinhThuc, UUID idHD) {
        repo.suaGhiChuVaHinhThuc(ghiChu, hinhThuc, idHD);
    }
    
    @Override
    public void suaTongTienSauGiamGia(double tongTien, UUID idHD) {
        repo.suaTongTienSauGiamGia(tongTien, idHD);
    }
    
    @Override
    public double getTongTien(UUID idHD) {
        return repo.getTongTien(idHD);
    }
    
    @Override
    public void suaTrangThai(UUID idHD) {
        repo.suaTrangThai(idHD);
    }
    
    @Override
    public void suaSLSP(UUID idHD) {
        repo.suaSLSP(idHD);
    }
    
    @Override
    public int checkSLTonSP(UUID idHD, UUID idSP) {
        return repo.checkSLTonSP(idHD, idSP);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTGLess(String s) {
        return repo.getAllCTGLess(s);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTGGreater(String s) {
        return repo.getAllCTGGreater(s);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTGEqual(String s) {
        return repo.getAllCTGEqual(s);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTGByName(String s) {
        return repo.getAllCTGByName(s);
    }
    
    @Override
    public void suaKH(UUID idKH, UUID idHD) {
        repo.suaKH(idKH, idHD);
    }
    
    @Override
    public UUID getIdKH(int maKH) {
        return repo.getIdKH(maKH);
    }
    
    @Override
    public GiamGia getGiamGia() {
        return repo.getGiamGia();
    }
    
    @Override
    public int mucGiamFromHD(UUID idHD) {
        return repo.mucGiamFromHD(idHD);
    }
    
    @Override
    public int getSLSPInHDCT(UUID idHD) {
        return repo.getSLSPInHDCT(idHD);
    }
    
    @Override
    public void suaNV(UUID idNV, UUID idHD) {
        repo.suaNV(idNV, idHD);
    }
    
    @Override
    public int checkSLTonSP0(UUID idSP){
        return repo.checkSLTonSP0(idSP);
    }
}
