/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import domainModels.ChiTietGiay;
import domainModels.HoaDonChiTiet;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface IDHoaDonChiTietViewModel {

    List<HoaDonChiTietViewModel> getAll();

    List<HoaDonChiTietViewModel> getListById(UUID id);

    List<HoaDonChiTiet> getListByIdHDCT(UUID id);

    List<ChiTietGiay> getListByIdCTG(UUID id);

    String getTenGiayById(UUID id);

    String getTenMauById(UUID id);

    String getTenHangById(UUID id);

    String getGiaGiayById(UUID id);

    String getTenCLById(UUID id);

}
