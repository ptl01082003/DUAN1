/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import domainModels.HoaDon;
import viewModels.HoaDonChiTietViewModel;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public interface IHoaDonRepository {

    List<HoaDon> getAll();

    List<String> getListMa();

    String getTenKHById(UUID id);

    String getMucGGById(UUID id);

    String getTenNVById(UUID id);

    List<HoaDonChiTietViewModel> getListById(UUID id);

    List<HoaDon> getListByHTTT(int id);

    List<HoaDon> getListByMa(int ma);

    List<HoaDon> getListByID(UUID id);

}
