/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import DAO.HoiVienDAO;
import helper.DateHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;

/**
 *
 * @author mynla
 */
public class QuanLyHoiVienJPanel extends javax.swing.JPanel {

//    QuanLyHoiVienControl control = new QuanLyHoiVienControl();
    static int selected = 0;
    static List<KhachHang> KhachHang = new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    HoiVienDAO dao = new HoiVienDAO();

    public QuanLyHoiVienJPanel() {
        initComponents();
        refresh();
    }

    public List<KhachHang> getHV() {
        try {
            KhachHang.clear();
            KhachHang = dao.selectAll();
            System.out.println("Số khách hàng có trong danh sách: " + KhachHang.size());
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
        return KhachHang;
    }

    public void fillToForm(int selectedRow) {
        KhachHang.clear();
        KhachHang = dao.selectAll();
        List<KhachHang> hv = getHV();
        KhachHang kh = KhachHang.get(selected);
        txtTenHoiVien.setText(kh.getName());
        txtSDTHoiVien.setText(kh.getSDT());
        txtGmailHoiVien.setText(kh.getEmail());
        Date dt = new Date();
        dt = DateHelper.toDate(kh.getBirthday(), "yyyy-MM-dd");
        NgaySinh.setDate(dt);
        System.out.println(kh.getEmail());
    }

    public final void refresh() {
        KhachHang.clear();
        KhachHang = getHV();
        fillTable();
    }

    public void fillTable() {

        model = (DefaultTableModel) tblQuanLyHoiVien.getModel();
        model.setRowCount(0);
        for (KhachHang kh : KhachHang) {
            Object[] row = new Object[]{"Ma: " + kh.getMaKH(), kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
            model.addRow(row);
        }
        System.out.println("FILLED HOIVEN TABLE");
    }

//    public List<KhachHang> getHV() {
//        try {
//            KhachHang.clear();
//            KhachHang = dao.selectAll();
//            System.out.println("Số khách hàng có trong danh sách: " + KhachHang.size());
//        } catch (Exception e) {
//            // Handle the exception appropriately
//            e.printStackTrace();
//        }
//        return KhachHang;
//    }
    public void themHV() {
        try {
            String tenHV = txtTenHoiVien.getText();
            String email = txtGmailHoiVien.getText();
            String sdt = txtSDTHoiVien.getText();
            Date date = NgaySinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            KhachHang newkh = new KhachHang();
            newkh.setName(tenHV);
            newkh.setEmail(email);
            newkh.setSDT(sdt);
            newkh.setBirthday(formattedDate);
            dao.insert(newkh);
            this.refresh();
            helper.DialogHelper.alert(this, "Thêm khách hàng thành công!");
        } catch (Exception e) {
            helper.DialogHelper.alert(this, "Đã xảy ra lỗi khi thêm khách hàng!");
            e.printStackTrace();
        }
    }

//
//    public void fillToForm(int selectedRow) {
//        System.out.println("line 101????: "+selectedRow);
//        List<KhachHang> hv = getHV();
////        System.out.println("line88: " + selectedRow);
////        model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
////        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
////            String name = (String) model.getValueAt(selectedRow, 0);
////            String sdt = (String) model.getValueAt(selectedRow, 1);
////            String email = (String) model.getValueAt(selectedRow, 2);
////            String birthday = (String) model.getValueAt(selectedRow, 4);
////
////            KhachHang kh1 = new KhachHang();
////            kh1.setName(name);
////            kh1.setSDT(sdt);
////            kh1.setEmail(email);
////            kh1.setBirthday(birthday);
////
////        }
////            setform(selectedRow);
//    }
//    public void suaHV(int selectedRow) {
//        System.out.println("sua mon: " + selectedRow);
//        String sdt1 = KhachHang.get(selectedRow).getSDT();
//        KhachHang newKh = new KhachHang();
//        newKh.setSDT(sdt1);
//        newKh.setName(txtTenHoiVien1.getText());
//        newKh.setEmail(txtGmailHoiVien.getText());
//        Date date = NgaySinh.getDate();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = dateFormat.format(date);
//        newKh.setBirthday(formattedDate);
//        try {
//            dao.update(newKh);
//            clearForm();
//            helper.DialogHelper.alert(this, "Sửa khách hàng thành công!!!");
//        } catch (Exception ex) {
//            helper.DialogHelper.alert(this, "Xảy ra lỗi khi sửa món!!!");
//        }
//    }
    public void suaHV1(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < KhachHang.size()) {
            int makh = KhachHang.get(selectedRow).getMaKH();
            KhachHang newKh = new KhachHang();
            newKh.setMaKH(makh);
            newKh.setName(txtTenHoiVien.getText());
            newKh.setSDT(txtSDTHoiVien.getText());
            newKh.setEmail(txtGmailHoiVien.getText());
            Date date = NgaySinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            newKh.setBirthday(formattedDate);
            try {
                dao.update(newKh);
                clearForm();
                helper.DialogHelper.alert(this, "Sửa khách hàng thành công!!!");
            } catch (Exception ex) {
                helper.DialogHelper.alert(this, "Xảy ra lỗi khi sửa món!!!");
            }
        } else {
            helper.DialogHelper.alert(this, "Selected row is out of bounds!");
        }
    }

    public void clearForm() {
        txtTenHoiVien.setText("");
        txtSDTHoiVien.setText("");
        txtGmailHoiVien.setText("");
        NgaySinh.setDate(null);
    }

    public void clearform() {
        txtTenHoiVien.setText("");
        txtSDTHoiVien.setText("");
        txtGmailHoiVien.setText("");
        NgaySinh.setDate(null);
    }

    public void searchByName(String tuKhoa) {
        try {
//            String type = "%";
            List<KhachHang> kh1 = dao.searchByNameAndType("%" + tuKhoa + "%");
            System.out.println("Tu Khoa search: " + tuKhoa);
            DefaultTableModel model = (DefaultTableModel) tblQuanLyHoiVien.getModel();
            model.setRowCount(0);
            for (KhachHang kh : kh1) {
                Object[] row = new Object[]{kh.getMaKH(), kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
                model.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(QuanLyHoiVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateForm(boolean chk) {
        Pattern pattern = Pattern.compile("^((84)|(0))[0-9]{9}$");
        String phone = txtSDTHoiVien.getText();
        Matcher matcher = pattern.matcher(phone);
        String emailRegex = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        if (txtTenHoiVien.getText().length() == 0) {
            helper.DialogHelper.alert(this, "Không được phép để trống tên!");
            txtTenHoiVien.requestFocus();
            return false;
        } else if (txtTenHoiVien.getText().length() < 6) {
            helper.DialogHelper.alert(this, "Tên không được ít hơn 6 kí tự");
            txtTenHoiVien.requestFocus();
            return false;
        } else if (txtSDTHoiVien.getText().length() == 0) {
            helper.DialogHelper.alert(this, "Không được phép để trống số điện thoại!");
            txtSDTHoiVien.requestFocus();
            return false;
        } else if (txtSDTHoiVien.getText().length() < 10) {
            helper.DialogHelper.alert(this, "Số điện thoại phải có độ dài ít nhất 10 kí tự");
            txtSDTHoiVien.requestFocus();
            return false;
        } else if (!matcher.matches()) {
            helper.DialogHelper.alert(this, "Số điện thoại không đúng định dạng");
            txtSDTHoiVien.requestFocus();
            return false;
        } else if (txtGmailHoiVien.getText().length() == 0) {
            helper.DialogHelper.alert(this, "Không được phép để trống email!");
            txtGmailHoiVien.requestFocus();
            return false;
        } else if (!txtGmailHoiVien.getText().matches(emailRegex)) {
            helper.DialogHelper.alert(this, "Sai định dạng email!");
            txtGmailHoiVien.requestFocus();
            return false;
        } else if (NgaySinh.getDate() == null) {
            helper.DialogHelper.alert(this, "Ngày sinh không đúng định dạng!");
            NgaySinh.requestFocus();
            return false;
        }
        return true;
    }

    private boolean confirmAdd() {
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    private boolean confirmEdit() {
        int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenHoiVien = new javax.swing.JTextField();
        txtSDTHoiVien = new javax.swing.JTextField();
        txtGmailHoiVien = new javax.swing.JTextField();
        NgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuanLyHoiVien = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 204, 204));
        setForeground(new java.awt.Color(255, 204, 204));
        setMaximumSize(new java.awt.Dimension(652, 802));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1080, 720));

        jPanel7.setBackground(new java.awt.Color(255, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức Năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("American Typewriter", 0, 12))); // NOI18N

        btnThem.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        btnXoa.setText("Làm mới Form");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel7.setText("Tìm Kiếm");

        txtTimKiem.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hội Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("American Typewriter", 0, 12))); // NOI18N

        jLabel5.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        jLabel5.setText("Tên Hội Viên");

        jLabel6.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        jLabel6.setText("Số Điện Thoại Hội Viên");

        jLabel9.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        jLabel9.setText("Gmail Hội Viên");

        jLabel10.setFont(new java.awt.Font("American Typewriter", 0, 10)); // NOI18N
        jLabel10.setText("Ngày sinh");

        txtTenHoiVien.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        txtSDTHoiVien.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        txtGmailHoiVien.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenHoiVien, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDTHoiVien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGmailHoiVien)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(0, 142, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(NgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenHoiVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGmailHoiVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDTHoiVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblQuanLyHoiVien.setBackground(new java.awt.Color(255, 204, 204));
        tblQuanLyHoiVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ma_KH", "Họ và tên", "Số điện thoại", "Gmail", "Điểm", "Ngày sinh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLyHoiVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblQuanLyHoiVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblQuanLyHoiVien);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm(true)) {
            if (confirmAdd()) { 
                themHV();
                refresh();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateForm(true)) {
            if (confirmEdit()) {
                suaHV1(selected);
                refresh();
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        clearForm();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblQuanLyHoiVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyHoiVienMousePressed
        selected = tblQuanLyHoiVien.getSelectedRow();
        fillToForm(selected);
    }//GEN-LAST:event_tblQuanLyHoiVienMousePressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        searchByName(txtTimKiem.getText());
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.toedter.calendar.JDateChooser NgaySinh;
    public static javax.swing.JButton btnSua;
    public static javax.swing.JButton btnThem;
    public static javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblQuanLyHoiVien;
    public javax.swing.JTextField txtGmailHoiVien;
    public javax.swing.JTextField txtSDTHoiVien;
    public javax.swing.JTextField txtTenHoiVien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
