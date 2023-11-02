/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModels;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author giang
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class KhacHangViewModel {
    private UUID id;
    private int ma;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
    private String email;

    
}
