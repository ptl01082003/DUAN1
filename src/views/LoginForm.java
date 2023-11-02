package views;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import service.DangNhapService;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

public class LoginForm extends javax.swing.JFrame {

    MatteBorder borderBlack = new MatteBorder(0, 0, 1, 0, Color.BLACK);
    MatteBorder borderGray = new MatteBorder(0, 0, 1, 0, new Color(204, 204, 204));
    private DangNhapService service;

    public LoginForm() {
        initComponents();
        this.service = new DangNhapService();
        this.setTitle("Login Form");
        this.setLocationRelativeTo(null);
        this.txtTaiKhoan.setBorder(borderBlack);
        this.txtMatKhau.setBorder(borderGray);
        this.setResizable(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        cbHienMK = new javax.swing.JCheckBox();
        lblCheckTK = new javax.swing.JLabel();
        lblCheckMK = new javax.swing.JLabel();
        lblQuenMK = new javax.swing.JLabel();
        lblStore = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(18, 41, 73));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đăng nhập");

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tài khoản");

        txtTaiKhoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        txtTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTaiKhoanMouseClicked(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Mật khẩu");

        btnDangNhap.setBackground(new java.awt.Color(18, 41, 73));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.setBorder(null);
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        txtMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMatKhauMouseClicked(evt);
            }
        });
        txtMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauKeyPressed(evt);
            }
        });

        cbHienMK.setBackground(new java.awt.Color(255, 255, 255));
        cbHienMK.setText("Hiện mật khẩu");
        cbHienMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHienMKActionPerformed(evt);
            }
        });

        lblCheckTK.setForeground(new java.awt.Color(255, 51, 51));
        lblCheckTK.setText(" ");

        lblCheckMK.setForeground(new java.awt.Color(255, 51, 51));
        lblCheckMK.setText(" ");

        lblQuenMK.setForeground(new java.awt.Color(51, 102, 255));
        lblQuenMK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuenMK.setText("Lấy lại mật khẩu ?");
        lblQuenMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCheckMK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbHienMK, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDangNhap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTaiKhoan)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(lblQuenMK)
                                .addGap(0, 86, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblCheckTK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblCheckMK, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbHienMK)
                .addGap(43, 43, 43)
                .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblQuenMK)
                .addContainerGap())
        );

        lblStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStore, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTaiKhoanMouseClicked
        this.txtMatKhau.setBorder(borderGray);
        this.txtTaiKhoan.setBorder(borderBlack);
        this.lblCheckTK.setText(" ");
    }//GEN-LAST:event_txtTaiKhoanMouseClicked

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        String tk = this.txtTaiKhoan.getText();

        char[] mk = this.txtMatKhau.getPassword();
        String matKhau = "";
        int size = mk.length;
        for (int i = 0; i < size; i++) {
            matKhau += mk[i];
        }

        String chucVu = service.getChucVu(tk, matKhau);

        if (tk.trim().length() == 0 && matKhau.trim().length() == 0) {
            this.lblCheckTK.setText("Tài khoản không để trống");
            this.lblCheckMK.setText("Mật khẩu không để trống");
            return;
        }

        if (tk.trim().length() == 0) {
            this.lblCheckTK.setText("Tài khoản không để trống");
            return;
        } else {
            this.lblCheckTK.setText(" ");
        }

        if (matKhau.trim().length() == 0) {
            this.lblCheckMK.setText("Mật khẩu không để trống");
            return;
        } else {
            this.lblCheckMK.setText(" ");
        }

        int dangNhap = this.service.check_exsist(tk, matKhau);
        if (dangNhap == 0) {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác");
            return;
        }

        String id = service.getId(tk, matKhau);
        System.out.println(service.getChucVu(tk, matKhau));
        if (service.getChucVu(tk, matKhau).equalsIgnoreCase("Nhân Viên")) {
            NhanVienForm f = new NhanVienForm();
            this.dispose();
            f.setHelloText("Xin Chào, " + service.getName(tk, matKhau) + "!  ");
            f.setIdText("ID: " + id);
            f.setIdNVBH(id);
            
            f.setVisible(true);
            return;
        }
        TrangChuViews v = new TrangChuViews();
        this.dispose();
        v.setHelloText("Xin Chào, " + service.getName(tk, matKhau) + "!  ");
        v.setIdText("ID: " + id);
        v.setIdNVBH(id);

        v.setVisible(true);
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void txtMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMatKhauMouseClicked
        this.txtTaiKhoan.setBorder(borderGray);
        this.txtMatKhau.setBorder(borderBlack);
        this.lblCheckMK.setText(" ");
    }//GEN-LAST:event_txtMatKhauMouseClicked

    private void txtMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauKeyPressed

    }//GEN-LAST:event_txtMatKhauKeyPressed

    private void cbHienMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHienMKActionPerformed
        if (cbHienMK.isSelected()) {
            this.txtMatKhau.setEchoChar((char) 0);
        } else {
            this.txtMatKhau.setEchoChar('*');
        }
    }//GEN-LAST:event_cbHienMKActionPerformed

    private void lblQuenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseClicked
        String mailNhan = txtTaiKhoan.getText(); // Thông tin email người nhận

        if (mailNhan.trim().length() == 0) {
            this.lblCheckTK.setText("Tài khoản không để trống");
            return;
        } else {
            this.lblCheckTK.setText(" ");
        }

        int check = this.service.check_Email(mailNhan);
        if (check == 0) {
            JOptionPane.showMessageDialog(this, "Email không tồn tại");
            return;
        }
//        
        //Email gửi
        String mailGui = "giayxshoes@gmail.com";
        String mkMailGui = "ztdzhoynlvzgbypv";

        // Tạo đối tượng Properties để cấu hình kết nối
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Bật hỗ trợ STARTTLS
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Cấu hình máy chủ
        properties.put("mail.smtp.port", 587); // Cấu hình máy chủ

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailGui, mkMailGui);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailGui));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailNhan));

            // Tiêu đề email
            message.setSubject("Quên Mật Khẩu");

            // Nội dung email
            message.setText("Mật khẩu của bạn là: " + service.getMK(mailNhan));

            Transport.send(message);
            JOptionPane.showMessageDialog(rootPane, "Đã gửi mật khẩu về Mail!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_lblQuenMKMouseClicked

    private void lblQuenMKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseEntered
        this.lblQuenMK.setText("<html><u>Lấy lại mật khẩu ?</u><html/>");
    }//GEN-LAST:event_lblQuenMKMouseEntered

    private void lblQuenMKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseExited
        this.lblQuenMK.setText("Lấy lại mật khẩu ?");
    }//GEN-LAST:event_lblQuenMKMouseExited

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JCheckBox cbHienMK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCheckMK;
    private javax.swing.JLabel lblCheckTK;
    private javax.swing.JLabel lblQuenMK;
    private javax.swing.JLabel lblStore;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
