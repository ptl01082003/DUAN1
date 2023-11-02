/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import domainModels.HoaDon;

import repositories.HoaDonRepositoryImpl;
import service.IHoaDonService;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class HoaDonServiceImpl implements IHoaDonService {

    HoaDonRepositoryImpl svri = new HoaDonRepositoryImpl();

    @Override
    public List<HoaDon> getAll() {
        return svri.getAll();
    }

    @Override
    public List<String> getListMa() {
        return svri.getListMa();
    }

    public String getTenKHById(UUID id) {
        return svri.getTenKHById(id);
    }

    public String getMucGiamById(UUID id) {
        return svri.getMucGGById(id);
    }

    public String getTenNVKHById(UUID id) {
        return svri.getTenNVById(id);
    }

    public List<HoaDonChiTietViewModel> getListById(UUID id) {
        return svri.getListById(id);
    }

    @Override
    public List<HoaDon> getListByHTTT(int id) {
        return svri.getListByHTTT(id);
    }

    @Override
    public List<HoaDon> getListByMa(int ma) {
        return svri.getListByMa(ma);
    }

    @Override
    public List<HoaDon> getListByID(UUID id) {
        return svri.getListByID(id);
    }

}
