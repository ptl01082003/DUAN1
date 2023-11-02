/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;


import domainModels.ChiTietGiay;
import domainModels.HoaDonChiTiet;
import repositories.HoaDonChiTietViewModelRepositoryImpl;
import service.IHoaDonChiTietViewModelService;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class HoaDonChiTietViewModelServiceImpl implements IHoaDonChiTietViewModelService{
    HoaDonChiTietViewModelRepositoryImpl svri = new HoaDonChiTietViewModelRepositoryImpl();

    @Override
    public List<HoaDonChiTietViewModel> getAll() {
        return svri.getAll();
    }

    @Override
    public List<HoaDonChiTietViewModel> getListByTT(UUID id) {
        return svri.getListById(id);
    }

    @Override
    public List<HoaDonChiTiet> getListByIDHDCT(UUID id) {
        return svri.getListByIdHDCT(id);
    }

    @Override
    public List<ChiTietGiay> getListByIdCTG(UUID id) {
        return svri.getListByIdCTG(id);
    }

    @Override
    public String getTenGiayById(UUID id) {
        return svri.getTenGiayById(id);
    }

}
