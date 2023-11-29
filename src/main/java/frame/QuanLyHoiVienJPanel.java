/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import DAO.HoiVienDAO;
import control.QuanLyHoiVienControl;
import helper.DateHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;

/**
 *
 * @author mynla
 */
public class QuanLyHoiVienJPanel extends javax.swing.JPanel {

    QuanLyHoiVienControl control = new QuanLyHoiVienControl();
    static List<KhachHang> KhachHang = new ArrayList<>();
    HoiVienDAO dao = new HoiVienDAO();


    public QuanLyHoiVienJPanel() {
        initComponents();
        this.refresh();
    }

    public final void refresh() {
        getHV();
        fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblQuanLyHoiVien.getModel();
        model.setRowCount(0);
        for (KhachHang kh : KhachHang) {
            Object[] row = new Object[]{kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
            model.addRow(row);
        }
    }

    public void getHV() {
        try {
            KhachHang = dao.selectAll();
            System.out.println("Số khách hàng có trong danh sách: " + KhachHang.size());
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }

    public void themHV() {
        try {
            String tenHV = txtTenHoiVien1.getText();
            String email = txtGmailHoiVien.getText();
            String sdt = txtSDTHoiVien.getText();
            Date date = chonngay.getDate();
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

    public void setform(KhachHang kh) {
        txtTenHoiVien1.setText(kh.getName());
        txtSDTHoiVien.setText(kh.getSDT());
        txtGmailHoiVien.setText(kh.getEmail());
        Date dt = new Date();
        dt = DateHelper.toDate(kh.getBirthday(), "YYYY-MM-DD");
        chonngay.setDate(dt);
    }

    public void fillToForm(int selectedRow) {
        System.out.println("line88: " + selectedRow);
        DefaultTableModel model = (DefaultTableModel) tblQuanLyHoiVien.getModel();
        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            String name = (String) model.getValueAt(selectedRow, 0);
            String sdt = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String birthday = (String) model.getValueAt(selectedRow, 4);

            KhachHang kh1 = new KhachHang();
            kh1.setName(name);
            kh1.setSDT(sdt);
            kh1.setEmail(email);
            kh1.setBirthday(birthday);

            setform(kh1);
        }
    }

//    public void suaHV(int selectedRow) {
//        System.out.println("sua mon: " + selectedRow);
//        String sdt1 = KhachHang.get(selectedRow).getSDT();
//        KhachHang newKh = new KhachHang();
//        newKh.setSDT(sdt1);
//        newKh.setName(txtTenHoiVien1.getText());
//        newKh.setEmail(txtGmailHoiVien.getText());
//        Date date = chonngay.getDate();
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
        System.out.println("line128: " + selectedRow);
        if (selectedRow >= 0 && selectedRow < KhachHang.size()) {            
            String sdt1 = KhachHang.get(selectedRow).getSDT();
            KhachHang newKh = new KhachHang();
            newKh.setSDT(sdt1);
            newKh.setName(txtTenHoiVien1.getText());
            newKh.setEmail(txtGmailHoiVien.getText());
            Date date = chonngay.getDate();
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
        txtTenHoiVien1.setText("");
        txtSDTHoiVien.setText("");
        txtGmailHoiVien.setText("");
        chonngay.setDate(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        cboLocHoiVien = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenHoiVien1 = new javax.swing.JTextField();
        txtSDTHoiVien = new javax.swing.JTextField();
        txtGmailHoiVien = new javax.swing.JTextField();
        chonngay = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuanLyHoiVien = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 153, 153));
        setForeground(new java.awt.Color(255, 204, 204));
        setMaximumSize(new java.awt.Dimension(652, 802));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1080, 720));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));

        cboLocHoiVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLocHoiVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocHoiVienActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel7.setText("Tìm Kiếm");

        jLabel8.setText("Lọc Hội Viên");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtTimKiem))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(cboLocHoiVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addGap(9, 9, 9))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem)
                    .addComponent(cboLocHoiVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Hội Viên"));

        jLabel5.setText("Tên Hội Viên");

        jLabel6.setText("Số Điện Thoại Hội Viên");

        jLabel9.setText("Gmail Hội Viên");

        jLabel10.setText("Ngày sinh");

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
                            .addComponent(txtTenHoiVien1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDTHoiVien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGmailHoiVien)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(chonngay, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenHoiVien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGmailHoiVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSDTHoiVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chonngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblQuanLyHoiVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Họ và tên", "Số điện thoại", "Gmail", "Điểm", "Ngày sinh"
            }
        ));
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
                .addGap(316, 316, 316)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
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
                        .addGap(36, 36, 36)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(123, 123, 123))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboLocHoiVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocHoiVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocHoiVienActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        themHV();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        suaHV1(tblQuanLyHoiVien.getSelectedRow());
        refresh();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        clearForm();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblQuanLyHoiVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyHoiVienMousePressed
        fillToForm(tblQuanLyHoiVien.getSelectedRow());
        refresh();
    }//GEN-LAST:event_tblQuanLyHoiVienMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnSua;
    public static javax.swing.JButton btnThem;
    public static javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLocHoiVien;
    public com.toedter.calendar.JDateChooser chonngay;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblQuanLyHoiVien;
    public javax.swing.JTextField txtGmailHoiVien;
    public javax.swing.JTextField txtSDTHoiVien;
    public javax.swing.JTextField txtTenHoiVien1;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
