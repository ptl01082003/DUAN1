/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import domainModels.KhachHang;
import repositories.repoKH;
import serviceImpl.implKH;
import viewModels.KhacHangViewModel;

/**
 *
 * @author giang
 */
public class serviceKH implements implKH {

    private repoKH repo = new repoKH();

    @Override
    public ArrayList<KhacHangViewModel> getAll() {
        ArrayList<KhacHangViewModel> lista = new ArrayList<>();
        ArrayList<KhachHang> listb = repo.getListFromdb();
        for (KhachHang nv : listb) {
            KhacHangViewModel vnv = new KhacHangViewModel();
            vnv.setId(nv.getId());
            vnv.setMa(nv.getMa());
            vnv.setHoTen(nv.getHoTen());
            vnv.setNgaySinh(nv.getNgaySinh());
            vnv.setGioiTinh(nv.getGioiTinh());
            vnv.setSdt(nv.getSdt());
            vnv.setDiaChi(nv.getDiaChi());
            vnv.setEmail(nv.getEmail());
            lista.add(vnv);
        }
        return lista;
    }

    @Override
    public int delete(String id) {
        return this.repo.xoa(id);
    }

    @Override
    public ArrayList<KhacHangViewModel> sreach(String hoTen) {
        ArrayList<KhacHangViewModel> lista = new ArrayList<>();
        ArrayList<KhachHang> listb = repo.timKiem(hoTen);
        for (KhachHang nv : listb) {
            KhacHangViewModel vnv = new KhacHangViewModel();
            vnv.setId(nv.getId());
            vnv.setMa(nv.getMa());
            vnv.setHoTen(nv.getHoTen());
            vnv.setNgaySinh(nv.getNgaySinh());
            vnv.setGioiTinh(nv.getGioiTinh());
            vnv.setSdt(nv.getSdt());
            vnv.setDiaChi(nv.getDiaChi());
            vnv.setEmail(nv.getEmail());
            lista.add(vnv);
        }
        return lista;
    }

    @Override
    public void them(KhachHang kh) {
        this.repo.addNew(kh);
    }

    @Override
    public void update(KhachHang kh, String id) {
        this.repo.update(kh, id);
    }

}
