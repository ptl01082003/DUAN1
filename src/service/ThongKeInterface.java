/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import viewModels.ChiTietSanPhamViewModel;
import viewModels.HoaDonDiaLogViewModel;
import viewModels.HoaDonTKViewModel;

/**
 *
 * @author giang
 */
public interface ThongKeInterface {
    public List<HoaDonTKViewModel> thongKeHD();

    

    public List<HoaDonTKViewModel> tkHDpM(String bd);
    //thuong
    public List<HoaDonTKViewModel> tkTHD();
    public List<HoaDonTKViewModel> tkTDT();
    public List<HoaDonTKViewModel> tkTKH();
    //loc nam
    public List<HoaDonTKViewModel> tkTHD1(int nam);
    public List<HoaDonTKViewModel> tkTDT1(int nam);
    public List<HoaDonTKViewModel> tkTKH1(int nam);
    //loc thang
    public List<HoaDonTKViewModel> tkTHD2(int thang,int nam);
    public List<HoaDonTKViewModel> tkTDT2(int thang,int nam);
    public List<HoaDonTKViewModel> tkTKH2(int thang,int nam);
    //bieu do cot
    public List<HoaDonTKViewModel> tkBD();
    public List<HoaDonTKViewModel> tkBD1(int nam);
    public List<HoaDonTKViewModel> tkBD2();
    //bieu do tron
    public List<ChiTietSanPhamViewModel> thongKeSP();
    public List<ChiTietSanPhamViewModel> thongKeSP1();
    //Dialog
    public List<HoaDonDiaLogViewModel> thongKeHD1(UUID id);
//     public List<time> year();
    public List<HoaDonTKViewModel> tkTL1();
    public List<HoaDonTKViewModel> tkTL2(int nam);
    public List<HoaDonTKViewModel> tkTL3(int thang, int nam);
}
