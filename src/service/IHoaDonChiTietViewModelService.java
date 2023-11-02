/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModels.ChiTietGiay;
import domainModels.HoaDonChiTiet;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface IHoaDonChiTietViewModelService {

    List<HoaDonChiTietViewModel> getAll();

    List<HoaDonChiTietViewModel> getListByTT(UUID id);
    
    List<HoaDonChiTiet> getListByIDHDCT(UUID id);

    List<ChiTietGiay> getListByIdCTG(UUID id);
   
     String getTenGiayById(UUID id);
}
