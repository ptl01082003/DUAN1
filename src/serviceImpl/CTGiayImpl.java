package serviceImpl;

import domainModels.ChiTietGiay;
import java.util.List;
import java.util.UUID;
import viewModels.SPCTGiayViewModel;

public interface CTGiayImpl {

    public List<SPCTGiayViewModel> getAll();

    public List<SPCTGiayViewModel> getALlByTen(String tens);

    public List<SPCTGiayViewModel> getAllByMa(String tens);

    public List<SPCTGiayViewModel> getAllByLG(String tens);

    public List<SPCTGiayViewModel> getAllByG(String tens);

    public int them(ChiTietGiay o);

    public int sua(ChiTietGiay o);

    public int xoa(UUID id);

    public int checkCTGiay(String ten, UUID mauSac, UUID hang, UUID size, UUID cl, UUID lg, UUID dm);

    public UUID findIDByMa(String id);

    public ChiTietGiay findByID(UUID id);

    public void importExcel();
    
    public int suaTrangThaiHetHang(UUID id);
    
        public List<ChiTietGiay> getAllCTGLess(String s);
    
    public List<ChiTietGiay> getAllCTGGreater(String s);
    
    public List<ChiTietGiay> getAllCTGEqual(String s);
    
    public int checkTruocXoa(UUID id);

}
