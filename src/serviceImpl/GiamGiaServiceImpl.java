/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import domainModels.GiamGia;
import repositories.GiamGiaRepositoryImpl;
import service.IGiamGiaService;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GiamGiaServiceImpl implements IGiamGiaService {

    GiamGiaRepositoryImpl svri = new GiamGiaRepositoryImpl();

    @Override
    public List<GiamGia> getAll() {
        return svri.getAll();
    }
    
    @Override
    public List<String> getListMa() {
        return svri.getListMa();
    }
    
    @Override
    public String add(GiamGia gg) {
        boolean add = svri.add(gg);
        if (add) {
            return "Thêm Giảm giá thành công!";
        } else {
            return "Thêm Dịch Vụ thất bại!";
        }
    }
    
    @Override
    public String update(GiamGia gg, String id) {
        boolean update = svri.update(gg, id);
        if (update) {
            return "Sửa Dịch Vụ thành công!";
        } else {
            return "Sửa Dịch Vụ thất bại!";
        }
    }
    
    @Override
    public String delete(String id) {
        boolean delete = svri.delete(id);
        if (delete) {
            return "Xóa Dịch Vụ thành công!";
        } else {
            return "Xóa Dịch Vụ thất bại!";
        }
    }
    
    @Override
    public List<LocalDate> getListNgayKetThuc() {
        return svri.getListNgayKetThuc();
    }
    
    @Override
    public List<GiamGia> getListByMa(int ma) {
        return svri.getListByMa(ma);
    }

    @Override
    public List<LocalDate> getListNgayBD() {
        return svri.getListNgayBD();
    }
    
}
