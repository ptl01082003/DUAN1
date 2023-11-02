package views;

import java.awt.Color;
import javax.swing.JOptionPane;

public class NhanVienForm extends javax.swing.JFrame {

    public NhanVienForm() {
        initComponents();
        
        this.setTitle("Phần mềm quản lý cửa hàng bán giày XShoes");
        pnSanPham2.setVisible(true);
        pnKH1.setVisible(false);
        pnBanHang1.setVisible(false);
        
        
        
    }

    public void setHelloText(String text){
        this.lblHello.setText(text);
    }
    
    public void setIdText(String text){
        this.lblId.setText(text);
    }
    
    public String getIdText(){
       return this.lblId.getText();
    }
    
   public void setIdNVBH(String id){
       pnBanHang1.setIdNhanVien(id);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidepane = new javax.swing.JPanel();
        panel_SanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel_KhachHang = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panel_banHang = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        panel_thoat = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panel_thoat1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnBanHang1 = new views.pnBanHang();
        pnKH1 = new views.pnKH();
        pnSanPham2 = new views.pnSanPham();
        lblId = new javax.swing.JLabel();
        lblHello = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidepane.setBackground(new java.awt.Color(24, 31, 75));
        sidepane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sidepaneMouseDragged(evt);
            }
        });
        sidepane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_SanPham.setBackground(new java.awt.Color(24, 31, 75));
        panel_SanPham.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                panel_SanPhamAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        panel_SanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_SanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel_SanPhamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_SanPhamMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_SanPhamMousePressed(evt);
            }
        });
        panel_SanPham.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_SanPham.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, 32));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-shoe-32 (1).png"))); // NOI18N
        jLabel2.setText("  Sản Phẩm");
        panel_SanPham.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        sidepane.add(panel_SanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 200, 70));

        panel_KhachHang.setBackground(new java.awt.Color(24, 31, 75));
        panel_KhachHang.setPreferredSize(new java.awt.Dimension(200, 50));
        panel_KhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_KhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel_KhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_KhachHangMouseExited(evt);
            }
        });
        panel_KhachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_KhachHang.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, 32));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-purchase-32.png"))); // NOI18N
        jLabel4.setText("  Khách Hàng");
        panel_KhachHang.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        sidepane.add(panel_KhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 200, 70));

        panel_banHang.setBackground(new java.awt.Color(24, 31, 75));
        panel_banHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_banHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel_banHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_banHangMouseExited(evt);
            }
        });
        panel_banHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_banHang.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 35, 32));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-fast-cart-32.png"))); // NOI18N
        jLabel17.setText("  Bán hàng");
        panel_banHang.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        sidepane.add(panel_banHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 200, 70));

        panel_thoat.setBackground(new java.awt.Color(24, 31, 75));
        panel_thoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_thoatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel_thoatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_thoatMouseExited(evt);
            }
        });
        panel_thoat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_thoat.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, 32));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-log-out-32.png"))); // NOI18N
        jLabel19.setText("Đăng xuất");
        panel_thoat.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 32));

        panel_thoat1.setBackground(new java.awt.Color(24, 31, 75));
        panel_thoat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_thoat1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel_thoat1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_thoat1MouseExited(evt);
            }
        });
        panel_thoat1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_thoat1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, 32));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-log-out-32.png"))); // NOI18N
        jLabel22.setText("  Thoát");
        panel_thoat1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 32));

        panel_thoat.add(panel_thoat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 760, 200, 70));

        sidepane.add(panel_thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 200, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-logo-96.png"))); // NOI18N
        sidepane.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Đoàn Trung Kiên");
        sidepane.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 70, -1, -1));

        jLabel20.setFont(new java.awt.Font("MV Boli", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("XShoes");
        sidepane.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 130, 40));

        getContentPane().add(sidepane, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 200, 900));

        jPanel1.setBackground(new java.awt.Color(4, 12, 49));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/giay.png"))); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 900));

        jLayeredPane1.add(pnBanHang1);
        pnBanHang1.setBounds(0, 40, 1300, 860);
        jLayeredPane1.add(pnKH1);
        pnKH1.setBounds(0, 40, 1300, 860);
        jLayeredPane1.add(pnSanPham2);
        pnSanPham2.setBounds(0, 40, 1300, 860);

        lblId.setBackground(new java.awt.Color(4, 12, 49));
        lblId.setFont(new java.awt.Font("Times New Roman", 3, 20)); // NOI18N
        lblId.setForeground(new java.awt.Color(255, 255, 255));
        lblId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblId.setText("Hello, Admin!!!!");
        lblId.setOpaque(true);
        jLayeredPane1.add(lblId);
        lblId.setBounds(0, 0, 650, 40);

        lblHello.setBackground(new java.awt.Color(4, 12, 49));
        lblHello.setFont(new java.awt.Font("Times New Roman", 3, 20)); // NOI18N
        lblHello.setForeground(new java.awt.Color(255, 255, 255));
        lblHello.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHello.setText("Hello, Admin!!!!");
        lblHello.setOpaque(true);
        jLayeredPane1.add(lblHello);
        lblHello.setBounds(650, 0, 650, 40);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1300, 900));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sidepaneMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidepaneMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_sidepaneMouseDragged

    private void panel_thoatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoatMouseExited
        // TODO add your handling code here:
        panel_thoat.setBackground(new Color(24, 31, 75));
    }//GEN-LAST:event_panel_thoatMouseExited

    private void panel_thoatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoatMouseEntered
        // TODO add your handling code here:
        panel_thoat.setBackground(new Color(26, 35, 157));
    }//GEN-LAST:event_panel_thoatMouseEntered

    private void panel_thoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoatMouseClicked
       int choose = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất không?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
       if(choose==JOptionPane.YES_OPTION){
           this.dispose();
           LoginForm f = new LoginForm();
           f.setVisible(true);
       }
    }//GEN-LAST:event_panel_thoatMouseClicked

    private void panel_banHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_banHangMouseExited
        // TODO add your handling code here:
        panel_banHang.setBackground(new Color(24, 31, 75));
    }//GEN-LAST:event_panel_banHangMouseExited

    private void panel_banHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_banHangMouseEntered
        // TODO add your handling code here:
        panel_banHang.setBackground(new Color(26, 35, 157));
    }//GEN-LAST:event_panel_banHangMouseEntered

    private void panel_banHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_banHangMouseClicked
        pnSanPham2.setVisible(false);
        pnKH1.setVisible(false);
        pnBanHang1.setVisible(true);
        pnBanHang1.loadSP();
    }//GEN-LAST:event_panel_banHangMouseClicked

    private void panel_KhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_KhachHangMouseExited
        // TODO add your handling code here:
        panel_KhachHang.setBackground(new Color(24, 31, 75));
    }//GEN-LAST:event_panel_KhachHangMouseExited

    private void panel_KhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_KhachHangMouseEntered
        // TODO add your handling code here:

        panel_KhachHang.setBackground(new Color(26, 35, 157));
    }//GEN-LAST:event_panel_KhachHangMouseEntered

    private void panel_KhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_KhachHangMouseClicked
        pnSanPham2.setVisible(false);
        pnKH1.setVisible(true);
        pnBanHang1.setVisible(false);
    }//GEN-LAST:event_panel_KhachHangMouseClicked

    private void panel_SanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_SanPhamMousePressed

    }//GEN-LAST:event_panel_SanPhamMousePressed

    private void panel_SanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_SanPhamMouseExited
        // TODO add your handling code here:
        panel_SanPham.setBackground(new Color(24, 31, 75));
    }//GEN-LAST:event_panel_SanPhamMouseExited

    private void panel_SanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_SanPhamMouseEntered
        // TODO add your handling code here:
        panel_SanPham.setBackground(new Color(26, 35, 157));
    }//GEN-LAST:event_panel_SanPhamMouseEntered

    private void panel_SanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_SanPhamMouseClicked
        pnSanPham2.setVisible(true);
        pnKH1.setVisible(false);
        pnBanHang1.setVisible(false);
    }//GEN-LAST:event_panel_SanPhamMouseClicked

    private void panel_SanPhamAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_panel_SanPhamAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_SanPhamAncestorAdded

    private void panel_thoat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoat1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_thoat1MouseClicked

    private void panel_thoat1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoat1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_thoat1MouseEntered

    private void panel_thoat1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_thoat1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_thoat1MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHello;
    private javax.swing.JLabel lblId;
    private javax.swing.JPanel panel_KhachHang;
    private javax.swing.JPanel panel_SanPham;
    private javax.swing.JPanel panel_banHang;
    private javax.swing.JPanel panel_thoat;
    private javax.swing.JPanel panel_thoat1;
    private views.pnBanHang pnBanHang1;
    private views.pnKH pnKH1;
    private views.pnSanPham pnSanPham2;
    private javax.swing.JPanel sidepane;
    // End of variables declaration//GEN-END:variables

}
