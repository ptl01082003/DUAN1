/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import domainModels.NhanVien;
import repositories.NhanVienRepos;
import service.NhanVienService;
import viewModels.NhanVienViewModel;
import java.util.ArrayList;

/**
 *
 * @author pt19t
 */
public class NhanVienServiceImpl implements NhanVienService{
    
    private NhanVienRepos repo = new NhanVienRepos();

    @Override
    public ArrayList<NhanVienViewModel> getAll() {
        ArrayList<NhanVienViewModel> lista = new ArrayList<>();
        ArrayList<NhanVien> listb = repo.getListFromdb();
        for (NhanVien nv : listb) {
            NhanVienViewModel vnv = new NhanVienViewModel();
            vnv.setId(nv.getId());
            vnv.setMaNv(nv.getMaNv());
            vnv.setHoTen(nv.getHoTen());
            vnv.setNgaySinh(nv.getNgaySinh());
            vnv.setGioiTinh(nv.getGioiTinh());
            vnv.setSdt(nv.getSdt());
            vnv.setCccd(nv.getCccd());
            vnv.setDiaChi(nv.getDiaChi());
            vnv.setEmail(nv.getEmail());
            vnv.setChucVu(nv.getChucVu());
            vnv.setMatKhau(nv.getMatKhau());
            vnv.setTrangThai(nv.getTrangThai());
            lista.add(vnv);
            
        }
        return lista;
    }

    @Override
    public void them(NhanVien nv) {
        this.repo.addNew(nv);
    }

    @Override
    public void update(NhanVien nv, String id) {
        this.repo.update(nv, id);
    }

    @Override
    public int delete(String id) {
        
        return this.repo.xoa(id);
    }

    @Override
    public ArrayList<NhanVienViewModel> sreach(String hoTen) {
        ArrayList<NhanVienViewModel> lista = new ArrayList<>();
        ArrayList<NhanVien> listb = repo.timKiem(hoTen);
        for (NhanVien nv : listb) {
            NhanVienViewModel vnv = new NhanVienViewModel();
            vnv.setId(nv.getId());
            vnv.setMaNv(nv.getMaNv());
            vnv.setHoTen(nv.getHoTen());
            vnv.setNgaySinh(nv.getNgaySinh());
            vnv.setGioiTinh(nv.getGioiTinh());
            vnv.setSdt(nv.getSdt());
            vnv.setCccd(nv.getCccd());
            vnv.setDiaChi(nv.getDiaChi());
            vnv.setEmail(nv.getEmail());
            vnv.setChucVu(nv.getChucVu());
            vnv.setTrangThai(nv.getTrangThai());
            lista.add(vnv);
            
        }
        return lista;
    
    }
    
}
