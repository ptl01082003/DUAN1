/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter

public class HoaDonChiTiet {
    private UUID idHD;
    private UUID idCTGiay;
    private int soLuong;
     public Object[] toDataRow() {
        return new Object[]{ idHD, idCTGiay,soLuong};
    }
}
