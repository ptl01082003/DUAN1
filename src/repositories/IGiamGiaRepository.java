/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import domainModels.GiamGia;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IGiamGiaRepository {

    List<GiamGia> getAll();

    List<String> getListMa();

    boolean add(GiamGia gg);

    boolean update(GiamGia dv, String id);

    boolean delete(String id);

    List<LocalDate> getListNgayKetThuc();

    List<GiamGia> getListByMa(int ma);
    
    List<LocalDate> getListNgayBD();

}
