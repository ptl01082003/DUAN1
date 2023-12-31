/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import DomainModels.NhanVien;
import ViewModel.NhanVienViewModel;
import java.util.ArrayList;

/**
 *
 * @author pt19t
 */
public interface NhanVienService {
    
     ArrayList<NhanVienViewModel> getAll();
    void them(NhanVien nv);
    void update(NhanVien nv,String id);
    void delete(String id);
    ArrayList<NhanVienViewModel> sreach(String hoTen);
    
}
