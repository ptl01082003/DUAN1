/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package views;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import javax.swing.JPanel;

/**
 *
 * @author ACER
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class NewMain extends JFrame {
    private JTable table;
    private PaginationModel paginationModel;
    private List<String> data; // Dữ liệu mẫu

    public NewMain() {
        setTitle("Pagination Example");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo dữ liệu mẫu
        data = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            data.add("Item " + i);
        }

        // Khởi tạo model và thiết lập số hàng mỗi trang
        paginationModel = new PaginationModel(data, 20); // 10 là số hàng trên mỗi trang

        // Tạo bảng và thiết lập model
        table = new JTable();
        table.setModel(paginationModel);

        // Thêm bảng vào scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Thiết lập sự kiện để lắng nghe khi lựa chọn hàng trong bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                // Lấy dữ liệu từ model dựa trên chỉ số hàng được lựa chọn
                int rowIndex = table.getSelectedRow();
                if (rowIndex >= 0) {
                    int actualRowIndex = table.convertRowIndexToModel(rowIndex);
                    String selectedData = paginationModel.getData(actualRowIndex);
                    System.out.println("Selected: " + selectedData);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewMain().setVisible(true);
            }
        });
    }

    // Class PaginationModel mở rộng AbstractTableModel để tạo model cho bảng với phân trang
    private class PaginationModel extends AbstractTableModel {
        private List<String> dataList; // Dữ liệu cần hiển thị
        private int pageSize; // Số hàng mỗi trang

        public PaginationModel(List<String> dataList, int pageSize) {
            this.dataList = dataList;
            this.pageSize = pageSize;
        }

        // Lấy dữ liệu từ trang hiện tại
        public String getData(int rowIndex) {
            return dataList.get(rowIndex);
        }

        // Lấy tất cả dữ liệu
        public List<String> getAllData() {
            return dataList;
        }

        // Lấy tổng số trang
        public int getTotalPages() {
            return (int) Math.ceil((double) dataList.size() / pageSize);
        }

        // Lấy chỉ số hàng trên trang hiện tại
        private int getActualRowIndex(int rowIndex) {
            return rowIndex + getPageStartRow();
        }

        // Lấy chỉ số hàng bắt đầu của trang hiện tại
        private int getPageStartRow() {
            return pageSize * currentPageIndex;
        }

        // Lấy chỉ số hàng kết thúc của trang hiện tại
        private int getPageEndRow() {
            int endRow = getPageStartRow() + pageSize;
            return Math.min(endRow, dataList.size());
        }

        // Thiết lập trang hiện tại
        public void setCurrentPage(int pageIndex) {
            if (pageIndex >= 0 && pageIndex < getTotalPages()) {
                currentPageIndex = pageIndex;
                fireTableDataChanged();
            }
        }

        // Khởi tạo giá trị mặc định cho trang hiện tại
        private int currentPageIndex = 0;

        // Override phương thức của AbstractTableModel để hiển thị dữ liệu
        @Override
        public int getRowCount() {
            return getPageEndRow() - getPageStartRow();
        }

        @Override
        public int getColumnCount() {
            return 1; // Số cột cần hiển thị
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int actualRowIndex = getActualRowIndex(rowIndex);
            return dataList.get(actualRowIndex);
        }
    }
}

    

