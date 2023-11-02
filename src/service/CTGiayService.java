package service;

import domainModels.ChiTietGiay;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import repositories.CTGiayRepository;
import serviceImpl.CTGiayImpl;
import viewModels.SPCTGiayViewModel;

public class CTGiayService implements CTGiayImpl {

    private CTGiayRepository repo;

    public CTGiayService() {
        this.repo = new CTGiayRepository();
    }

    @Override
    public List<SPCTGiayViewModel> getAll() {
        List<SPCTGiayViewModel> list1 = new ArrayList<>();
        List<ChiTietGiay> list2 = repo.getAll();
        for (ChiTietGiay o : list2) {
            UUID id = o.getId();
            int ma = o.getMaCTGiay();
            String ten = o.getTenCTGiay();
            UUID idQR = o.getQr();
            UUID idMS = o.getMs();
            UUID idHang = o.getHang();
            UUID idSize = o.getSize();
            UUID idCL = o.getCl();
            UUID idLoaiGiay = o.getLoaiGiay();
            UUID idDM = o.getDanhMuc();
            int sl = o.getSoLuong();
            double gNhap = o.getGiaNhap();
            double gBan = o.getGiaBan();
            String anh = o.getAnh();
            int trangThai = o.getTrangThai();
            String moTa = o.getMoTa();

            list1.add(new SPCTGiayViewModel(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
        }
        return list1;
    }

    @Override
    public List<SPCTGiayViewModel> getALlByTen(String tens) {
        List<SPCTGiayViewModel> list1 = new ArrayList<>();
        List<ChiTietGiay> list2 = repo.getAllByTen(tens);
        for (ChiTietGiay o : list2) {
            UUID id = o.getId();
            int ma = o.getMaCTGiay();
            String ten = o.getTenCTGiay();
            UUID idQR = o.getQr();
            UUID idMS = o.getMs();
            UUID idHang = o.getHang();
            UUID idSize = o.getSize();
            UUID idCL = o.getCl();
            UUID idLoaiGiay = o.getLoaiGiay();
            UUID idDM = o.getDanhMuc();
            int sl = o.getSoLuong();
            double gNhap = o.getGiaNhap();
            double gBan = o.getGiaBan();
            String anh = o.getAnh();
            int trangThai = o.getTrangThai();
            String moTa = o.getMoTa();

            list1.add(new SPCTGiayViewModel(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
        }
        return list1;
    }

    @Override
    public List<SPCTGiayViewModel> getAllByMa(String tens) {
        List<SPCTGiayViewModel> list1 = new ArrayList<>();
        List<ChiTietGiay> list2 = repo.getAllByMa(tens);
        for (ChiTietGiay o : list2) {
            UUID id = o.getId();
            int ma = o.getMaCTGiay();
            String ten = o.getTenCTGiay();
            UUID idQR = o.getQr();
            UUID idMS = o.getMs();
            UUID idHang = o.getHang();
            UUID idSize = o.getSize();
            UUID idCL = o.getCl();
            UUID idLoaiGiay = o.getLoaiGiay();
            UUID idDM = o.getDanhMuc();
            int sl = o.getSoLuong();
            double gNhap = o.getGiaNhap();
            double gBan = o.getGiaBan();
            String anh = o.getAnh();
            int trangThai = o.getTrangThai();
            String moTa = o.getMoTa();

            list1.add(new SPCTGiayViewModel(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
        }
        return list1;
    }

    @Override
    public int them(ChiTietGiay o) {
        return repo.them(o);
    }

    @Override
    public int sua(ChiTietGiay o) {
        return repo.sua(o);
    }

    @Override
    public int xoa(UUID id) {
        return repo.xoa(id);
    }

    @Override
    public int checkCTGiay(String ten, UUID mauSac, UUID hang, UUID size, UUID cl, UUID lg, UUID dm) {
        return repo.checkCTGiay(ten, mauSac, hang, size, cl, lg, dm);
    }

    @Override
    public UUID findIDByMa(String id) {
        return repo.findIDByMa(id);
    }

    @Override
    public ChiTietGiay findByID(UUID id) {
        return repo.findByID(id);
    }

    @Override
    public void importExcel() {
        repo.importExcel();
    }

    @Override
    public List<SPCTGiayViewModel> getAllByLG(String tens) {
        List<SPCTGiayViewModel> list1 = new ArrayList<>();
        List<ChiTietGiay> list2 = repo.getAllByLG(tens);
        for (ChiTietGiay o : list2) {
            UUID id = o.getId();
            int ma = o.getMaCTGiay();
            String ten = o.getTenCTGiay();
            UUID idQR = o.getQr();
            UUID idMS = o.getMs();
            UUID idHang = o.getHang();
            UUID idSize = o.getSize();
            UUID idCL = o.getCl();
            UUID idLoaiGiay = o.getLoaiGiay();
            UUID idDM = o.getDanhMuc();
            int sl = o.getSoLuong();
            double gNhap = o.getGiaNhap();
            double gBan = o.getGiaBan();
            String anh = o.getAnh();
            int trangThai = o.getTrangThai();
            String moTa = o.getMoTa();

            list1.add(new SPCTGiayViewModel(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
        }
        return list1;
    }

    @Override
    public List<SPCTGiayViewModel> getAllByG(String tens) {
        List<SPCTGiayViewModel> list1 = new ArrayList<>();
        List<ChiTietGiay> list2 = repo.getAllByG(tens);
        for (ChiTietGiay o : list2) {
            UUID id = o.getId();
            int ma = o.getMaCTGiay();
            String ten = o.getTenCTGiay();
            UUID idQR = o.getQr();
            UUID idMS = o.getMs();
            UUID idHang = o.getHang();
            UUID idSize = o.getSize();
            UUID idCL = o.getCl();
            UUID idLoaiGiay = o.getLoaiGiay();
            UUID idDM = o.getDanhMuc();
            int sl = o.getSoLuong();
            double gNhap = o.getGiaNhap();
            double gBan = o.getGiaBan();
            String anh = o.getAnh();
            int trangThai = o.getTrangThai();
            String moTa = o.getMoTa();

            list1.add(new SPCTGiayViewModel(id, ma, ten, idQR, idMS, idHang, idSize, idCL, idLoaiGiay, idDM, sl, gNhap, gBan, anh, moTa, trangThai));
        }
        return list1;
    }

    @Override
    public int suaTrangThaiHetHang(UUID id){
        return repo.suaTrangThaiHetHang(id);
    }
    
    @Override
    public List<ChiTietGiay> getAllCTGLess(String s) {
        return repo.getAllCTGLess(s);
    }

    @Override
    public List<ChiTietGiay> getAllCTGGreater(String s) {
        return repo.getAllCTGGreater(s);
    }

    @Override
    public List<ChiTietGiay> getAllCTGEqual(String s) {
        return repo.getAllCTGEqual(s);
    }
    
    @Override
    public int checkTruocXoa(UUID id){
        return repo.checkTruocXoa(id);
    }
}
