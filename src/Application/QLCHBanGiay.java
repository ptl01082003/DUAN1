/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Application;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import views.LoginForm;
import views.TestView;
import views.TrangChuViews;

/**
 *
 * @author ACER
 */
public class QLCHBanGiay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        LoginForm v = new LoginForm();
        v.setVisible(true);
//        Document document = new Document();
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream("D:\\invoices.pdf"));
//
//            document.open();
//            BaseFont baseFont = BaseFont.createFont("C:\\Users\\ACER\\Downloads\\font-unicode\\font-unicode\\taimienphi.vn-font-unicode\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font font = new Font(baseFont, 16, Font.NORMAL);
//            Font font2 = new Font(baseFont, 16, Font.BOLD);
//            // Tạo bảng với 2 cột
//            PdfPTable table = new PdfPTable(5);
//            // Thêm tiêu đề cho từng cột
//            PdfPCell cell1 = new PdfPCell(new Paragraph("Stt", font2));
//            PdfPCell cell2 = new PdfPCell(new Paragraph("Tên SP", font2));
//            PdfPCell cell3 = new PdfPCell(new Paragraph("Số lượng", font2));
//            PdfPCell cell4 = new PdfPCell(new Paragraph("Giá", font2));
//            PdfPCell cell5 = new PdfPCell(new Paragraph("Thành tiền", font2));
//
//            // Thêm các ô vào bảng
//            table.addCell(cell1);
//            table.addCell(cell2);
//            table.addCell(cell3);
//            table.addCell(cell4);
//            table.addCell(cell5);
//            
//            // Thêm dữ liệu vào từng ô
//            for(int i=0;i<5;i++){
//                table.addCell((i+1)+"");
//                table.addCell("SP"+(i+1));
//                table.addCell("1");
//                table.addCell("100000");
//                table.addCell("100000");
//                
//            }
//            
//    
//            
//            document.add(setParagraph("CỬA HÀNG BÁN GIÀY XSHOES", font2, Element.ALIGN_CENTER));
//            document.add(setParagraph("355 Nguyễn Văn Cừ, Long Biên, Hà Nội", font, Element.ALIGN_CENTER));
//            document.add(setParagraph("Mã đơn hàng: 1", font, Element.ALIGN_LEFT));
//            document.add(setParagraph("Ngày tạo: 01/01/2022", font, Element.ALIGN_LEFT));
//            document.add(setParagraph("Mã nhân viên: 1", font, Element.ALIGN_LEFT));
//            document.add(setParagraph("Tên khách hàng: Khách hàng lẻ", font, Element.ALIGN_LEFT));            
//            document.add(new Paragraph("\n"));
//            document.add(table);
//            document.add(setParagraph("\nTổng tiền đơn hàng: 500000 VNĐ", font, Element.ALIGN_RIGHT));
//            document.add(setParagraph("Giảm giá: 0%", font, Element.ALIGN_RIGHT));
//            document.add(setParagraph("Tổng tiền thanh toán: 500000 VNĐ", font2, Element.ALIGN_RIGHT));
//            document.add(setParagraph("\n\nCảm ơn và hẹn gặp lại quý khách!", font2, Element.ALIGN_CENTER));
//            document.close();
//            System.out.println("Hóa đơn đã được xuất ra file PDF thành công.");
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//       
        
    }

//    private static Paragraph setParagraph(String s, Font f, int alight) {
//        Paragraph paragraph = new Paragraph(s, f);
//        paragraph.setAlignment(alight);
//        return paragraph;
//    }

}
