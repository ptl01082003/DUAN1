/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package views;

import domainModels.ChatLieu;
import domainModels.MauSac;
import domainModels.QR;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import repositories.ChatLieuRepository;
import repositories.MauSacRepository;
import repositories.QrRepository;

/**
 *
 * @author ACER
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QrRepository s = new QrRepository();
        QR qr = new QR();
        qr.setAnhQR("Dowloads\\SPCT2.png");
        s.them(qr);
        QR qrs = s.findByQR(qr.getAnhQR());
        System.out.println(qrs.getId());
        
//        
//        MauSacRepository r = new MauSacRepository();
//        MauSac ms = new MauSac();
//        ms.setTenMS("Trắng hồng");
//        r.them(ms);
//        
//        System.out.println(ms.getId());
    }
    
}
