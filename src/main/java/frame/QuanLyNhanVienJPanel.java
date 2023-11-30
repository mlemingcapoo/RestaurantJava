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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;

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
        txtAdd.setText("");
        txtCccd.setText("");
        txtHoTen.setText("");
        txtLinkAnh.setText("");
        txtSdt.setText("");
        dateNgaySinh.setDate(null);
    }

    public void fillToForm(int selectedRow) {
        NhanVien nv = nhanvien.get(selectedRow);
        System.out.println("may dang o: " + selectedRow);
        setForm(nv);
//       refresh();

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
                cboTrangThai.setSelectedIndex(1);
                break;
            case "false":
                cboTrangThai.setSelectedIndex(0);
                break;
        }
        txtAdd.setText(nv.getAddress());
        txtHoTen.setText(nv.getName());
        Date dt = new Date();
        dt = DateHelper.toDate(nv.getBirthday(), "YYYY-MM-DD");
        dateNgaySinh.setDate(dt);
        txtSdt.setText(nv.getSodienthoai());
        txtCccd.setText(String.valueOf(nv.getCccd()));
        txtLinkAnh.setText(nv.getImg());
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
        model.setRowCount(0);
        for (NhanVien nv : nhanvien) {
            Object[] row = new Object[]{nv.getMaNV(), nv.getUser(), nv.getPass(), nv.isRole(), nv.isIsLooked()};
            model.addRow(row);
        }
    }

    public void getNV() {
//        clearForm();
        try {
            nhanvien = dao.selectAll();
            System.out.println("Số Nhan vien có trong danh sách: " + nhanvien.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void themNV() {
        try {
            String user = txttaikhoan.getText();
            String pass = txtPass.getText();
            int roleIndex;
            roleIndex = cboRole.getSelectedIndex();
            boolean role = (roleIndex == 1);
            int TrangThaiIntdex = cboTrangThai.getSelectedIndex();
            boolean TrangThai = (TrangThaiIntdex == 1);
            String address = txtAdd.getText();
            String name = txtHoTen.getText();
            Date date = dateNgaySinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            String sdt = txtSdt.getText();
            int cccd = Integer.parseInt(txtCccd.getText());
            String img = txtLinkAnh.getText();
            NhanVien newnhanvien = new NhanVien();
            newnhanvien.setUser(user);
            newnhanvien.setPass(pass);
            newnhanvien.setRole(role);
            newnhanvien.setIsLooked(TrangThai);
            newnhanvien.setAddress(address);
            newnhanvien.setName(name);
            newnhanvien.setBirthday(formattedDate);
            newnhanvien.setSodienthoai(sdt);
            newnhanvien.setCccd(cccd);
            newnhanvien.setImg(img);

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
        System.out.println("tao o hang : " + selectedRow);

        int maNV = nhanvien.get(selectedRow).getMaNV();
        System.out.println("tao sua o id : " + maNV);
        try {

            System.out.println("Employee ID: " + maNV);

            NhanVien updatedEmployee = new NhanVien();
            updatedEmployee.setMaNV(maNV);
            updatedEmployee.setUser(txttaikhoan.getText());
            updatedEmployee.setPass(txtPass.getText());
            switch (cboRole.getSelectedIndex()) {
                case 1:
                    updatedEmployee.setRole(true);
                    break;
                case 0:
                    updatedEmployee.setRole(false);
                    break;
            }
            switch (cboTrangThai.getSelectedIndex()) {
                case 1:
                    updatedEmployee.setIsLooked(true);
                    break;
                case 0:
                    updatedEmployee.setIsLooked(false);
                    break;
            }
            updatedEmployee.setAddress(txtAdd.getText());
            updatedEmployee.setName(txtHoTen.getText());
            Date date = dateNgaySinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            updatedEmployee.setBirthday(formattedDate);
            updatedEmployee.setSodienthoai(txtSdt.getText());
            updatedEmployee.setCccd(Integer.parseInt(txtCccd.getText()));
            dao.sua(updatedEmployee);

//        clearForm();
//        refresh();
            System.out.println("Update successful");
        } catch (Exception ex) {
            // Log or handle the exception appropriately
            ex.printStackTrace();
            System.out.println("Error updating employee");
        }
    }

    public void xoaMon(int selectedRow) {
        System.out.println("TaoXoaNV nhe :" + selectedRow);
        int MaNV = nhanvien.get(selectedRow).getMaNV();
        System.out.println(" id tao ne :" + MaNV);
        NhanVien newNhanVien = new NhanVien();
        newNhanVien.setMaNV(MaNV);
        try {
            dao.delete(newNhanVien);
            refresh();
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
            List<NhanVien> hv = dao.searchByNameAndType("%" + tuKhoa + "%");
            System.out.println("Tu Khoa search: " + tuKhoa);
//            System.out.println("Loai search: "+type1);
            DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVien hv1 : hv) {
                Object[] row = new Object[]{hv1.getUser(), hv1.getPass(), hv1.isIsLooked(), hv1.isRole()};
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
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txttaikhoan = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtLinkAnh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        txtHoTen = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        txtAdd = new javax.swing.JTextField();
        txtCccd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachNhanVien = new javax.swing.JTable();

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

        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "false", "true" }));

        jLabel5.setText("Trang Thai");

        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel6.setText("Link Ảnh");

        btnUpload.setText("UPLOAD");

        jLabel7.setText("Họ Và Tên");

        jLabel8.setText("Ngày Sinh");

        jLabel9.setText("Số Điện Thoại");

        jLabel10.setText("Địa Chỉ");

        jLabel11.setText("Căn Cước Công Dân");

        jLabel4.setText("Role");

        javax.swing.GroupLayout txtUserLayout = new javax.swing.GroupLayout(txtUser);
        txtUser.setLayout(txtUserLayout);
        txtUserLayout.setHorizontalGroup(
            txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(373, 373, 373))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                                .addComponent(txtLinkAnh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpload))
                            .addComponent(cboTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                                .addComponent(txtPass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox1))
                            .addGroup(txtUserLayout.createSequentialGroup()
                                .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(76, 76, 76)))
                .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(279, 279, 279))
                    .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(txtCccd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(304, 304, 304))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(315, 315, 315))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(315, 315, 315))
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(315, 315, 315))
                    .addComponent(txtAdd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                .addContainerGap())
        );
        txtUserLayout.setVerticalGroup(
            txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtUserLayout.createSequentialGroup()
                .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txtUserLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addComponent(txttaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(txtUserLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBox1)
                                .addGap(30, 30, 30)))
                        .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(txtUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLinkAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload))
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(8, 8, 8)
                        .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCccd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
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
                "ID", "User", "Pas", "IsLooked", "role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(txtUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        refresh();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        timKiem(txtTimKiem.getText());
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaMon(tblDanhSachNhanVien.getSelectedRow());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (evt.getStateChange() == 1) {
            txtPass.setEchoChar((char) 0);
        } else {
            txtPass.setEchoChar('•');
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSua;
    public static javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpload;
    public static javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboRole;
    public javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDanhSachNhanVien;
    private javax.swing.JTextField txtAdd;
    private javax.swing.JTextField txtCccd;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLinkAnh;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPanel txtUser;
    private javax.swing.JTextField txttaikhoan;
    // End of variables declaration//GEN-END:variables

//    private void fillToForm(int selectedRow) {
//       System.out.println("line88: " + selectedRow);
//       
//    }
}
