/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package serviceImpl;

import java.util.ArrayList;
import domainModels.KhachHang;
import viewModels.KhacHangViewModel;

/**
 *
 * @author giang
 */
public interface implKH {

    ArrayList<KhacHangViewModel> getAll();

    void them(KhachHang kh);

    void update(KhachHang kh, String id);

    int delete(String id);

    ArrayList<KhacHangViewModel> sreach(String hoTen);
}
