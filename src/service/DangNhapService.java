package service;

import repositories.DangNhapRepository;
import serviceImpl.DangNhapServiceImpl;

public class DangNhapService implements DangNhapServiceImpl {

    private DangNhapRepository repo;

    public DangNhapService() {
        this.repo = new DangNhapRepository();
    }

    public int check_exsist(String email, String mk) {
        return repo.check_Exists(email, mk);
    }

    @Override
    public int check_Email(String email) {
        return repo.check_Exists_Email(email);
    }

    @Override
    public String getName(String email, String mk) {
        return repo.getName(email, mk);
    }

    @Override
    public String getMK(String email) {
        return repo.getMK(email);
    }

    @Override
    public String getChucVu(String email, String mk) {
        return repo.getChucVu(email, mk);
    }

    @Override
    public String getId(String email, String mk) {
        return repo.getId(email, mk);
    }
}
