package serviceImpl;

public interface DangNhapServiceImpl {

    public int check_exsist(String email, String mk);

    public int check_Email(String email);

    public String getName(String email, String mk);

    public String getMK(String email);

    public String getChucVu(String email, String mk);
    
    public String getId(String email, String mk);
}
