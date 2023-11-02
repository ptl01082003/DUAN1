/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import service.ThongKeInterface;
import serviceImpl.ThongKeService;
import viewModels.ChiTietSanPhamViewModel;
import viewModels.HoaDonTKViewModel;

/**
 *
 * @author ACER
 */
public class PanelThongKe extends javax.swing.JPanel {

    private ThongKeInterface service = new ThongKeService();
    DefaultTableModel dtmTKHD = new DefaultTableModel();

    private List<HoaDonTKViewModel> listTKHD = new ArrayList<>();
//    DefaultTableModel dtmTKSP = new DefaultTableModel();
    private List<ChiTietSanPhamViewModel> listTKSP = new ArrayList<>();
//    private List<time> listTime = new ArrayList<>();
//    private HoaDonInteface hoadonService = new HoaDonService();

    public PanelThongKe() {
        initComponents();
        this.setDataToChart1(jPanel13);
//        this.showPieChart(jPanel2);
//        this.showPieChart1(jPanel3);
        tblTkHD.setModel(dtmTKHD);

        Object[] headers = {"STT", "Thời Gian", "Mã Hóa Đơn", "Tên Nhân Viên", "Tên Khách Hàng", "Tổng Tiền", "Trạng Thái"};
        dtmTKHD.setColumnIdentifiers(headers);

        listTKHD = service.thongKeHD();
        this.loadTableThongKeHoaDon(listTKHD);

//        listTime = service.year();
//        this.loadTime(listTime);
        List<HoaDonTKViewModel> listTKHDD = service.tkTHD();
        HoaDonTKViewModel hd = new HoaDonTKViewModel();
        hd = listTKHDD.get(0);
        String thd = String.valueOf(hd.getTongHD());
        txtTongHoaDon.setText(thd);

        List<HoaDonTKViewModel> listTKHDDT = service.tkTDT();
        HoaDonTKViewModel hddt = new HoaDonTKViewModel();
        hddt = listTKHDDT.get(0);
        String tdt = String.valueOf(hddt.getTongTien());
        txtTongDoanhThu.setText(tdt);

        List<HoaDonTKViewModel> listTKKH = service.tkTKH();
        HoaDonTKViewModel hdkh = new HoaDonTKViewModel();
        hdkh = listTKKH.get(0);
        String kh = String.valueOf(hdkh.getTongKH());
        txtSoKhach.setText(kh);
        txtbd.setEditable(false);
        this.txtbd.setDocument(new NumberOnlyDocument());

        List<HoaDonTKViewModel> listTL = service.tkTL1();
        HoaDonTKViewModel hdtl = new HoaDonTKViewModel();
        hdtl = listTL.get(0);
        BigDecimal ban = hdtl.getTongTien();
        String lai = String.valueOf(ban);
        txtTongloiNhuan.setText(lai);
        
        txtnam.setEnabled(false);
        cboMonth.setEnabled(false);
        
        tblTkHD.getColumnModel().getColumn(0).setMinWidth(0);
    tblTkHD.getColumnModel().getColumn(0).setMaxWidth(0);
    tblTkHD.getColumnModel().getColumn(0).setWidth(0);

    }

    public String status(int status) {
        if (status == 1) {
            return "Đã Thanh Toán";
        } else {
            return "Chưa Thanh Toán";
        }

    }

    static class NumberOnlyDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            try {
                Integer.parseInt(str);
                super.insertString(offs, str, a);
            } catch (NumberFormatException e) {

            }
        }

    }

    private void loadTableThongKeHoaDon(List<HoaDonTKViewModel> listTKHD) {
        dtmTKHD = (DefaultTableModel) tblTkHD.getModel();
        dtmTKHD.setRowCount(0);
        for (HoaDonTKViewModel hd : listTKHD) {
            System.out.println(hd.toString());
            dtmTKHD.addRow(new Object[]{
                hd.getId(),
                hd.getNgayThanhToan(),
                hd.getMaHD(),
                hd.getTenKH(),
                hd.getTenKH(),
                hd.getTongTien(),
                status(hd.getTrangThai())
            });
        }
    }

//    private void loadTime(List<time> listTime) {
//        for (time tm : listTime) {
//            System.out.println(tm.toString());
//            cbonam.addItem(String.valueOf(tm.getNam()));
//
//        }
//    }
    private void loadTableThongKeHoaDonPM(List<HoaDonTKViewModel> listTKHD) {
        String bd = txttim.getText();
        dtmTKHD.setRowCount(0);
        int i = 1;
        for (HoaDonTKViewModel hd : listTKHD) {
            dtmTKHD.addRow(new Object[]{
                i++,
                //               hd.setNgay(bd"-"kt),
                hd.getTongHD(),
                hd.getTongTien()
            });
        }
    }

    private Color getColor(int index) {
        Color[] color = new Color[]{
            new Color(255, 204, 204), new Color(255, 229, 204), new Color(255, 255, 204),
            new Color(229, 225, 204), new Color(255, 153, 153), new Color(255, 204, 153),
            new Color(255, 255, 102), new Color(204, 255, 255), new Color(255, 153, 255),
            new Color(51, 153, 255), new Color(192, 192, 192), new Color(102, 204, 0),
            new Color(255, 0, 255), new Color(204, 229, 205), new Color(204, 204, 255)};
        return color[index];
    }

    public void showPieChart(JPanel jpnItem) {
        List<ChiTietSanPhamViewModel> listItem = service.thongKeSP();

        DefaultPieDataset barDataset = new DefaultPieDataset();
        for (ChiTietSanPhamViewModel sp : listItem) {
            barDataset.setValue("Hãng: " + sp.getTenCTGiay(), sp.getSoLuong());
            System.out.println(sp.getTenCTGiay() + " - " + sp.getSoLuong());
        }
        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Biểu đồ thống kê sản phẩm theo số lượng bán được", barDataset, false, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();
        int index = 0;
        //changing pie chart blocks colors
        for (ChiTietSanPhamViewModel sp : listItem) {
            piePlot.setSectionPaint(sp.getTenCTGiay(), getColor(index++));
            piePlot.setBackgroundPaint(Color.white);
        }

        //create chartPanel to display chart(graph)
        ChartPanel chartPanel = new ChartPanel(piechart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 400));
        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
    }

    public void showPieChart1(JPanel jpnItem) {
        List<ChiTietSanPhamViewModel> listItem = service.thongKeSP1();

        DefaultPieDataset barDataset = new DefaultPieDataset();
        for (ChiTietSanPhamViewModel sp : listItem) {
            barDataset.setValue("Hãng: " + sp.getTenCTGiay(), sp.getSoLuong());
            System.out.println(sp.getTenCTGiay() + " - " + sp.getSoLuong());
        }
        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Biểu đồ thống kê sản phẩm theo doanh thu bán được", barDataset, false, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();
        int index = 0;
        //changing pie chart blocks colors
        for (ChiTietSanPhamViewModel sp : listItem) {
            piePlot.setSectionPaint(sp.getTenCTGiay(), getColor(index++));
            piePlot.setBackgroundPaint(Color.white);
        }

        //create chartPanel to display chart(graph)
        ChartPanel chartPanel = new ChartPanel(piechart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 400));
        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
    }

    public void setDataToChart1(JPanel jpnItem) {
        List<HoaDonTKViewModel> listItem = service.tkBD();

//        HoaDonTKViewModel hd = new HoaDonTKViewModel();
//        List<HoaDonTKViewModel> listTKHDDT = service.tkBD();
//        HoaDonTKViewModel hddt = new HoaDonTKViewModel();
//        hddt = listTKHDDT.get(0);
        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (HoaDonTKViewModel hd : listItem) {
                dataset.addValue(hd.getTongTien(), "Tổng doanh thu", hd.getNgayThanhToan());
                System.out.println(hd.getNgayThanhToan());
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thống kê doanh thu theo ngày".toUpperCase(),
                    "Thời gian", "Doanh thu",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 400));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }
//public void time(List<HoaDonTKViewModel> listItem){
//             String year = (String)cboa.getSelectedItem();
//             listItem = service.tkBD1(Integer.parseInt(year));
//             HoaDonTKViewModel hd = new HoaDonTKViewModel();
//             hd = listItem.get(0);
//}

    public void setDataToChart2(JPanel jpnItem, int nam) {
//          String year = (String)cboa.getSelectedItem();
        List<HoaDonTKViewModel> listItem = service.tkBD1(nam);

        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (HoaDonTKViewModel hd : listItem) {

                dataset.addValue(hd.getTongTien(), "Tổng doanh thu", "Tháng :" + hd.getTongKH() + " - " + nam);
                System.out.println(hd.getNgayThanhToan());
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thống kê doanh thu theo tháng".toUpperCase(),
                    "Thời gian", "Doanh thu",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), jpnItem.getHeight()));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }

    public void setDataToChart3(JPanel jpnItem) {
//          String year = (String)cboa.getSelectedItem();
        List<HoaDonTKViewModel> listItem = service.tkBD2();

        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (HoaDonTKViewModel hd : listItem) {

                dataset.addValue(hd.getTongTien(), "Tổng doanh thu", "Năm: " + hd.getTongKH());
                System.out.println(hd.getNgayThanhToan());
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thống kê doanh thu theo năm".toUpperCase(),
                    "Thời gian", "Doanh thu",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), jpnItem.getHeight()));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        don = new javax.swing.JLabel();
        txtTongHoaDon = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTkHD = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txttim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lblBieuDo = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        txtbd = new javax.swing.JTextField();
        lblWarning = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        vnd = new javax.swing.JLabel();
        txtTongDoanhThu = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtSoKhach = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        rdoa1 = new javax.swing.JRadioButton();
        rdoa2 = new javax.swing.JRadioButton();
        rdoa3 = new javax.swing.JRadioButton();
        cboMonth = new javax.swing.JComboBox<>();
        txtnam = new javax.swing.JTextField();
        btnloc = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        vnd2 = new javax.swing.JLabel();
        txtTongloiNhuan = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel27.setBackground(new java.awt.Color(24, 31, 75));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel25.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Tổng số hóa đơn");

        don.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        don.setForeground(new java.awt.Color(255, 255, 255));
        don.setText("Đơn");

        txtTongHoaDon.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtTongHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        txtTongHoaDon.setText("Null");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(txtTongHoaDon)
                        .addGap(35, 35, 35)
                        .addComponent(don)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(don, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtTongHoaDon)))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.setBackground(new java.awt.Color(102, 102, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tblTkHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Hóa Dơn", "Thời gian", "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTkHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTkHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTkHD);

        jPanel5.setBackground(new java.awt.Color(24, 31, 75));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSearch.setBackground(new java.awt.Color(204, 204, 204));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(204, 204, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        txttim.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txttim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tìm theo mã");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addGap(109, 109, 109)
                .addComponent(btnClear)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(btnClear)))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 52, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Bảng doanh thu", jPanel9);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(1299, 1299, 1299)
                .addComponent(lblBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Năm");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Ngày");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setText("Tháng");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        txtbd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbdActionPerformed(evt);
            }
        });
        txtbd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbdKeyReleased(evt);
            }
        });

        lblWarning.setForeground(new java.awt.Color(255, 51, 51));
        lblWarning.setText("Cần nhập năm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(txtbd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Biểu đồ", jPanel8);

        jPanel7.setBackground(new java.awt.Color(24, 31, 75));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tổng doanh thu");

        vnd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        vnd.setForeground(new java.awt.Color(255, 255, 255));
        vnd.setText("VND");

        txtTongDoanhThu.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txtTongDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        txtTongDoanhThu.setText("Null");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(vnd)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTongDoanhThu)
                        .addGap(41, 41, 41))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(txtTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vnd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel6.setBackground(new java.awt.Color(24, 31, 75));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tổng số khách");

        txtSoKhach.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtSoKhach.setForeground(new java.awt.Color(255, 255, 255));
        txtSoKhach.setText("Null");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(txtSoKhach)))
                .addGap(142, 142, 142))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(rdoa1);
        rdoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoa1.setSelected(true);
        rdoa1.setText("Tất cả");
        rdoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoa1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoa2);
        rdoa2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoa2.setText("Năm");
        rdoa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoa2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoa3);
        rdoa3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoa3.setText("Tháng");
        rdoa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoa3ActionPerformed(evt);
            }
        });

        cboMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        btnloc.setText("Lọc");
        btnloc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlocActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(24, 31, 75));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tổng Lợi Nhuận");

        vnd2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        vnd2.setForeground(new java.awt.Color(255, 255, 255));
        vnd2.setText("VND");

        txtTongloiNhuan.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txtTongloiNhuan.setForeground(new java.awt.Color(255, 255, 255));
        txtTongloiNhuan.setText("Null");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(vnd2)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(txtTongloiNhuan)
                        .addGap(41, 41, 41))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTongloiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vnd2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(607, 607, 607))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdoa1)
                    .addComponent(btnloc))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoa2)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtnam)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoa3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoa1)
                    .addComponent(rdoa2)
                    .addComponent(rdoa3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnloc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoa1ActionPerformed
        // TODO add your handling code here:
        txtnam.setEnabled(false);
        cboMonth.setEnabled(false);
        txtnam.setText(null);
        //
        List<HoaDonTKViewModel> listTKHDD = service.tkTHD();
        HoaDonTKViewModel hd = new HoaDonTKViewModel();
        hd = listTKHDD.get(0);
        String thd = String.valueOf(hd.getTongHD());
        txtTongHoaDon.setText(thd);

        List<HoaDonTKViewModel> listTKHDDT = service.tkTDT();
        HoaDonTKViewModel hddt = new HoaDonTKViewModel();
        hddt = listTKHDDT.get(0);
        String tdt = String.valueOf(hddt.getTongTien());
        txtTongDoanhThu.setText(tdt);

        List<HoaDonTKViewModel> listTKKH = service.tkTKH();
        HoaDonTKViewModel hdkh = new HoaDonTKViewModel();
        hdkh = listTKKH.get(0);
        String kh = String.valueOf(hdkh.getTongKH());
        txtSoKhach.setText(kh);
    }//GEN-LAST:event_rdoa1ActionPerformed

    private void rdoa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoa2ActionPerformed
        // TODO add your handling code here:
        txtnam.setEnabled(true);
        cboMonth.setEnabled(false);
    }//GEN-LAST:event_rdoa2ActionPerformed

    private void rdoa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoa3ActionPerformed
        // TODO add your handling code here:
        txtnam.setEnabled(true);
        cboMonth.setEnabled(true);

    }//GEN-LAST:event_rdoa3ActionPerformed

    private void btnlocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlocActionPerformed
        // TODO add your handling code here:
        if (rdoa2.isSelected()) {
            if (txtnam.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mời nhập năm cần tìm");
                return;
            } else {
                int nam = Integer.parseInt(txtnam.getText());
                List<HoaDonTKViewModel> listTKHDD = service.tkTHD1(nam);
                HoaDonTKViewModel hd = new HoaDonTKViewModel();
                hd = listTKHDD.get(0);
                String thd = String.valueOf(hd.getTongHD());
                txtTongHoaDon.setText(thd);

                List<HoaDonTKViewModel> listTKHDDT = service.tkTDT1(nam);
                HoaDonTKViewModel hddt = new HoaDonTKViewModel();
                hddt = listTKHDDT.get(0);
                String tdt = String.valueOf(hddt.getTongTien());
                txtTongDoanhThu.setText(tdt);

                List<HoaDonTKViewModel> listTKKH = service.tkTKH1(nam);
                HoaDonTKViewModel hdkh = new HoaDonTKViewModel();
                hdkh = listTKKH.get(0);
                String kh = String.valueOf(hdkh.getTongKH());
                txtSoKhach.setText(kh);

                List<HoaDonTKViewModel> listTL = service.tkTL2(nam);
                HoaDonTKViewModel hdtl = new HoaDonTKViewModel();
                hdtl = listTL.get(0);
                BigDecimal ban = hdtl.getTongTien();
                String lai = String.valueOf(ban);
                txtTongloiNhuan.setText(lai);
            }
        } else if (rdoa3.isSelected()) {
            if (txtnam.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mời nhập năm cần tìm");
                return;
            } else {

                String thang = (String) cboMonth.getSelectedItem();
                int nam = Integer.parseInt(txtnam.getText());
                //
                List<HoaDonTKViewModel> listTKHDD = service.tkTHD2(Integer.parseInt(thang), nam);
                HoaDonTKViewModel hd = new HoaDonTKViewModel();
                hd = listTKHDD.get(0);
                String thd = String.valueOf(hd.getTongHD());
                txtTongHoaDon.setText(thd);

                List<HoaDonTKViewModel> listTKHDDT = service.tkTDT2(Integer.parseInt(thang), nam);
                HoaDonTKViewModel hddt = new HoaDonTKViewModel();
                hddt = listTKHDDT.get(0);
                String tdt = String.valueOf(hddt.getTongTien());
                txtTongDoanhThu.setText(tdt);

                List<HoaDonTKViewModel> listTKKH = service.tkTKH2(Integer.parseInt(thang), nam);
                HoaDonTKViewModel hdkh = new HoaDonTKViewModel();
                hdkh = listTKKH.get(0);
                String kh = String.valueOf(hdkh.getTongKH());
                txtSoKhach.setText(kh);

                List<HoaDonTKViewModel> listTL = service.tkTL3(Integer.parseInt(thang), nam);
                HoaDonTKViewModel hdtl = new HoaDonTKViewModel();
                hdtl = listTL.get(0);
                BigDecimal ban = hdtl.getTongTien();
                String lai = String.valueOf(ban);
                txtTongloiNhuan.setText(lai);
            }
        }


    }//GEN-LAST:event_btnlocActionPerformed

    private void txtbdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbdKeyReleased
        if (txtbd.getText().trim().length() == 0) {
            lblWarning.setText("Cần nhập năm");
        } else {
            jRadioButton3.setEnabled(true);
            int nam = Integer.parseInt(txtbd.getText());
            this.setDataToChart2(jPanel13, nam);
            lblWarning.setText("");
        }
    }//GEN-LAST:event_txtbdKeyReleased

    private void txtbdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbdActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        txtbd.setEditable(true);
        lblWarning.setText("Cần nhập năm");
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        txtbd.setEditable(false);
        lblWarning.setText("");
        this.setDataToChart1(jPanel13);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        txtbd.setEditable(false);
        lblWarning.setText("");
        this.setDataToChart3(jPanel13);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txttimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttimActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txttim.setText(null);
        listTKHD = service.thongKeHD();
        this.loadTableThongKeHoaDon(listTKHD);
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String bd = txttim.getText();
        listTKHD = service.tkHDpM(bd);
        this.loadTableThongKeHoaDon(listTKHD);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblTkHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTkHDMouseClicked
        int row = tblTkHD.getSelectedRow();
        if (row >= 0 && row < tblTkHD.getRowCount()) {
            String ma = tblTkHD.getValueAt(row, 0).toString();
            try {
                UUID id = UUID.fromString(ma);
                dialog dialog = new dialog(id);
                dialog.setVisible(true);
            } catch (IllegalArgumentException ex) {
                // Xử lý nếu chuỗi không phải là UUID hợp lệ
                ex.printStackTrace();
            }
        } else {
            // Xử lý nếu không có dòng nào được chọn
        }
    }//GEN-LAST:event_tblTkHDMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnloc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboMonth;
    private javax.swing.JLabel don;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBieuDo;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JRadioButton rdoa1;
    private javax.swing.JRadioButton rdoa2;
    private javax.swing.JRadioButton rdoa3;
    private javax.swing.JTable tblTkHD;
    private javax.swing.JLabel txtSoKhach;
    private javax.swing.JLabel txtTongDoanhThu;
    private javax.swing.JLabel txtTongDoanhThu1;
    private javax.swing.JLabel txtTongHoaDon;
    private javax.swing.JLabel txtTongloiNhuan;
    private javax.swing.JTextField txtbd;
    private javax.swing.JTextField txtnam;
    private javax.swing.JTextField txttim;
    private javax.swing.JLabel vnd;
    private javax.swing.JLabel vnd1;
    private javax.swing.JLabel vnd2;
    // End of variables declaration//GEN-END:variables
}
