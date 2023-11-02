/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModels.GiamGia;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IGiamGiaService {

    List<GiamGia> getAll();

    List<String> getListMa();

    String add(GiamGia gg);

    String update(GiamGia gg, String id);

    String delete(String id);

    List<LocalDate> getListNgayKetThuc();
    
    List<LocalDate> getListNgayBD();

    List<GiamGia> getListByMa(int ma);
}
