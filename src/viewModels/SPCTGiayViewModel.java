
package viewModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class SPCTGiayViewModel {
    private UUID id;

    private int maCTGiay;

    private String tenCTGiay;

    private UUID qr;
    
    private UUID ms;

    private UUID hang;

    private UUID size;

    private UUID cl;

    private UUID loaiGiay;

    private UUID danhMuc;

    private int soLuong;

    private double giaNhap;

    private double giaBan;
    
    private String anh;

    private String moTa;

    private int trangThai;
}
