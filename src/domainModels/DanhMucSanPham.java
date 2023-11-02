package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class DanhMucSanPham {

    private UUID id;
    
    private int maDanhMuc;
    
    private String tenDanhMuc;
    
    private String moTa;
    
}
