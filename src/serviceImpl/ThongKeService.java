/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import repositories.repoThongKe;
import service.ThongKeInterface;
import viewModels.ChiTietSanPhamViewModel;
import viewModels.HoaDonDiaLogViewModel;
import viewModels.HoaDonTKViewModel;

/**
 *
 * @author giang
 */
public class ThongKeService implements ThongKeInterface{
    repoThongKe repo = new repoThongKe();

    @Override
    public List<HoaDonTKViewModel> thongKeHD() {
         return repo.tkHD();
    }

    @Override
    public List<ChiTietSanPhamViewModel> thongKeSP() {
       return repo.tkSP();
    }

    @Override
    public List<HoaDonTKViewModel> tkHDpM(String bd) {
     return repo.tkHDpM(bd);
    }

    @Override
    public List<HoaDonTKViewModel> tkTHD() {
        return repo.tkTHD(); 
    }

    @Override
    public List<HoaDonTKViewModel> tkTDT() {
       return repo.tkTDT();
    }

    @Override
    public List<HoaDonTKViewModel> tkTKH() {
      return repo.tkKH();
    }

    @Override
    public List<HoaDonTKViewModel> tkBD() {
      return repo.tkBD();
    }

    @Override
    public List<HoaDonTKViewModel> tkTHD1(int nam) {
        return repo.tkTHD1(nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTDT1(int nam) {
         return repo.tkTDT1(nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTKH1(int nam) {
        return repo.tkKH1(nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTHD2(int thang,int nam) {
        return repo.tkTHD2(thang,nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTDT2(int thang,int nam) {
        return repo.tkTDT2(thang,nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTKH2(int thang,int nam) {
       return repo.tkKH2(thang,nam);
    }

//    @Override
//    public List<time> year() {
//       return repo.showYear();
//    }

    @Override
    public List<HoaDonTKViewModel> tkBD1(int nam) {
      return repo.tkBD1(nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkBD2() {
      return repo.tkBD2();
    }

    @Override
    public List<ChiTietSanPhamViewModel> thongKeSP1() {
   return repo.tkSP1();
    }

    @Override
    public List<HoaDonDiaLogViewModel> thongKeHD1(UUID id) {
        return repo.tkHD1(id);
    }

    @Override
    public List<HoaDonTKViewModel> tkTL1() {
        return repo.tkTL();
    }

    @Override
    public List<HoaDonTKViewModel> tkTL2(int nam) {
        return repo.tkTL1(nam);
    }

    @Override
    public List<HoaDonTKViewModel> tkTL3(int thang, int nam) {
        return repo.tkTL2(thang, nam);
    }





    
}
