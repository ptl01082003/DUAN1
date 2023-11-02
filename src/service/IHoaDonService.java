/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModels.HoaDon;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface IHoaDonService {

    List<HoaDon> getAll();

    List<String> getListMa();

    String getTenKHById(UUID id);

    String getMucGiamById(UUID id);

    String getTenNVKHById(UUID id);

    List<HoaDonChiTietViewModel> getListById(UUID id);

    List<HoaDon> getListByHTTT(int id);
    
    List<HoaDon> getListByMa(int ma);
    
    List<HoaDon> getListByID(UUID id);

}
