/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import DAO.NhanVienDao;
import control.QuanLyNhanVienControl;
import helper.DateHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;
import model.User;

public class QuanLyNhanVienJPanel extends javax.swing.JPanel {

    QuanLyNhanVienControl control = new QuanLyNhanVienControl();
    static List<NhanVien> nhanvien = new ArrayList<>();
    NhanVienDao dao = new NhanVienDao();
    
    public QuanLyNhanVienJPanel() {
        initComponents();
//        getNV();
        refresh();
        
    }
    public void refresh() {
        getNV();
        fillTable();
    }
    public void clearForm() {
        txttaikhoan.setText("");
        txtPass.setText("");
        cboRole.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
    }
    public void  fillToForm(int selectedRow){
        NhanVien nv = nhanvien.get(selectedRow);
        System.out.println("may dang o: " + selectedRow);
        setForm(nv);
       
    }
     public void setForm(NhanVien nv) {
         txttaikhoan.setText(nv.getUser());
        txtPass.setText(nv.getPass());
        switch (String.valueOf(nv.isRole())) {
            case "true":
                cboRole.setSelectedIndex(1);
                break;
            case "false":
                cboRole.setSelectedIndex(0);
                break;
        }
        switch (String.valueOf(nv.isIsLooked())) {
            case "true":
                cboRole.setSelectedIndex(1);
                break;
            case "false":
                cboRole.setSelectedIndex(0);
                break;
        }
    }
   public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
        model.setRowCount(0);
        for (NhanVien nv : nhanvien) {
            Object[] row = new Object[]{nv.getUser(),nv.getPass(),nv.isRole(),nv.isIsLooked()};
            model.addRow(row);
        }
    }
    public void getNV() {
        clearForm();
        try {
            nhanvien = dao.selectAll();
               System.out.println("Số Nhan vien có trong danh sách: " + nhanvien.size());    
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        }
    public void themNV(){
        try {
            String user = txttaikhoan.getText();
            String pass = txtPass.getText();
            int roleIndex;
            roleIndex = cboRole.getSelectedIndex();
            boolean role = (roleIndex == 1);
            int TrangThaiIntdex = cboTrangThai.getSelectedIndex();
            boolean TrangThai = (TrangThaiIntdex == 1);
            
            NhanVien newnhanvien = new NhanVien();
            newnhanvien.setUser(user);
           
            newnhanvien.setPass(pass);
            newnhanvien.setRole(role);
            newnhanvien.setIsLooked(TrangThai);
            dao.them(newnhanvien);
            refresh();
            clearForm();
            System.out.println("done");
        } catch (Exception ex) {
//            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm món!");
            ex.printStackTrace(); // Handle the exception appropriately, e.g., show an error message
        }
    }
        public void SuaNV(int selectedRow) {
            System.out.println("sua mon: " + selectedRow);
        int MaNV = nhanvien.get(selectedRow).getMaNV();
        NhanVien newNh = new NhanVien();
        newNh.setMaNV(MaNV);
         newNh.setUser(txttaikhoan.getText());
          newNh.setPass(txtPass.getText());
        switch (String.valueOf(newNh.isRole())) {
            case "true":
                cboRole.setSelectedIndex(1);
                break;
            case "false":
                cboRole.setSelectedIndex(0);
                break;
        }
        switch (String.valueOf(newNh.isIsLooked())) {
            case "true":
                cboRole.setSelectedIndex(1);
                break;
            case "false":
                cboRole.setSelectedIndex(0);
                break;
    }try {
            dao.sua(newNh);
            clearForm();
            System.out.println("done");
//            helper.DialogHelper.alert(QuanLyNhanVienJPanel, "Sửa món thành công!!!");
        } catch (Exception ex) {
              System.out.println("loi");
//            helper.DialogHelper.alert(QuanLyNhanVienJPanel, "Xảy ra lỗi khi sửa món!!!");
//            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void xoaMon(int selectedRow) {
        System.out.println("TaoXoaNV nhe :" + selectedRow);
        int MaNV = nhanvien.get(selectedRow).getMaNV();
        NhanVien newNhanVien = new NhanVien();
        newNhanVien.setMaNV(MaNV);
        try {
            dao.delete(newNhanVien);
            clearForm();
            System.out.println("done");
//            helper.DialogHelper.alert(QuanLyNhanVienJPanel, "Xóa Món Thành Công!");
        } catch (Exception ex) {
            System.out.println("Loi");
//            helper.DialogHelper.alert(panel, "Xóa món thất bại!");
//            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void timKiem(String tuKhoa) {
        try {
//            String type = "%";
//            if (type1.equals("Chọn Loại")){
//                type = "%";
//            } else {
//                type = type1;
//            }
            List<NhanVien> hv = dao.searchByNameAndType("%"+tuKhoa+"%");
            System.out.println("Tu Khoa search: "+tuKhoa);
//            System.out.println("Loai search: "+type1);
            DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVien hv1 : hv) {
                Object[] row = new Object[]{hv1.getUser(),hv1.getPass(),hv1.isIsLooked(),hv1.isRole()};
                model.addRow(row);
            }
        } catch (Exception ex) {
            Logger.getLogger(QuanLyNhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        txtUser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboRole = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txttaikhoan = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachNhanVien = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btnKhoa = new javax.swing.JButton();
        btnMo = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 153, 153));

        txtUser.setBackground(new java.awt.Color(255, 153, 153));
        txtUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Chỉnh Sửa"));
        txtUser.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel2.setText("User");

        jLabel3.setText("Pass");

        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nhanVien", "QuanLy" }));

        jLabel4.setText("Role");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khoa", "Mo" }));

        jLabel5.setText("Trang Thai");

        javax.swing.GroupLayout txtUserLayout = new javax.swing.GroupLayout(txtUser);
        txtUser.setLayout(txtUserLayout);
        txtUserLayout.setHorizontalGroup(
            txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(788, 788, 788))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboRole, javax.swing.GroupLayout.Alignment.LEADING, 0, 733, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttaikhoan)
                            .addComponent(txtPass))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        txtUserLayout.setVerticalGroup(
            txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 153, 153));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm"));

        txtTimKiem.setBackground(new java.awt.Color(255, 153, 153));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));

        btnThem.setBackground(new java.awt.Color(255, 153, 153));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 153, 153));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 153, 153));
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(8, 8, 8))
        );

        tblDanhSachNhanVien.setBackground(new java.awt.Color(255, 153, 153));
        tblDanhSachNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "user", "pass", "IsLooked", "role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachNhanVien.setColumnSelectionAllowed(true);
        tblDanhSachNhanVien.getTableHeader().setReorderingAllowed(false);
        tblDanhSachNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDanhSachNhanVienMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhSachNhanVien);
        tblDanhSachNhanVien.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel9.setBackground(new java.awt.Color(255, 153, 153));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Kiểm Soát"));

        btnKhoa.setBackground(new java.awt.Color(255, 153, 153));
        btnKhoa.setText("Khoá");

        btnMo.setBackground(new java.awt.Color(255, 153, 153));
        btnMo.setText("Mở");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKhoa)
                    .addComponent(btnMo))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 763, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        themNV();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblDanhSachNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachNhanVienMousePressed
       fillToForm(tblDanhSachNhanVien.getSelectedRow());
//       refresh();
    }//GEN-LAST:event_tblDanhSachNhanVienMousePressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        SuaNV(tblDanhSachNhanVien.getSelectedRow());
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
       timKiem(txtTimKiem.getText());
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaMon(tblDanhSachNhanVien.getSelectedRow());
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnKhoa;
    public static javax.swing.JButton btnMo;
    public javax.swing.JButton btnSua;
    public static javax.swing.JButton btnThem;
    public static javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboRole;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDanhSachNhanVien;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPanel txtUser;
    private javax.swing.JTextField txttaikhoan;
    // End of variables declaration//GEN-END:variables

    

   

//    private void fillToForm(int selectedRow) {
//       System.out.println("line88: " + selectedRow);
//       
//    }
}
