/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import Api_upload_image.upanh;
import DAO.NhanVienDao;
import control.QuanLyNhanVienControl;
import helper.DateHelper;
import helper.DialogHelper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class QuanLyNhanVienJPanel extends javax.swing.JPanel {

    QuanLyNhanVienControl control = new QuanLyNhanVienControl();
    static List<NhanVien> nhanvien = new ArrayList<>();
    NhanVienDao dao = new NhanVienDao();

    public QuanLyNhanVienJPanel() {
        initComponents();
//        getNV();
        refresh();

    }

    public boolean validateQuanLyNhanVien() {
        if (txttaikhoan.getText().equals("")) {
            helper.DialogHelper.alert(this, " Tên người dùng không được để trống");
            txttaikhoan.requestFocus();
            return false;
        }
        if (txttaikhoan.getText().length() < 4) {
            helper.DialogHelper.alert(this, "Tên người dùng không được nhỏ hơn 4 kí tự");
            txttaikhoan.requestFocus();
            return false;
        }
        if (txtPass.getText().equals("")) {
            helper.DialogHelper.alert(this, " Mật Khẩu không được để trống");
            txtPass.requestFocus();
            return false;
        }
        if (txtPass.getText().length() < 4) {
            helper.DialogHelper.alert(this, "Mật Khẩu không được nhỏ hơn 4 kí tự");
            txtPass.requestFocus();
            return false;
        }
        if (txtHoTen.getText().equals("")) {
            helper.DialogHelper.alert(this, " Họ và Tên không được để trống");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtHoTen.getText().length() < 6) {
            helper.DialogHelper.alert(this, "Họ và Tên không được nhỏ hơn 6 kí tự");
            txtHoTen.requestFocus();
            return false;
        }

        try {
            if (dateNgaySinh.getDate().toString().equals("")) {
                helper.DialogHelper.alert(this, " Ngày Sinh không được để trống");
                dateNgaySinh.requestFocus();
                return false;
            }
        } catch (Exception e) {
            helper.DialogHelper.alert(this, " Ngày Sinh phải đúng định dạng");
            dateNgaySinh.requestFocus();
            return false;
        }

        if (txtSdt.getText().equals("")) {
            helper.DialogHelper.alert(this, " Sdt không được để trống");
            txtPass.requestFocus();
            return false;
        }
        if (txtSdt.getText().length() < 10) {
            helper.DialogHelper.alert(this, " Số điện Thoại không được nhỏ hơn 10 kí tự");
            txtSdt.requestFocus();
            return false;
        }

        Pattern pattern = Pattern.compile("^((84)|(0))[0-9]{9}$");

        String phone = txtSdt.getText();

        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            helper.DialogHelper.alert(this, "Số điện thoại không đúng định dạng");
            txtSdt.requestFocus();
            return false;
        }

        if (txtAdd.getText().equals("")) {
            helper.DialogHelper.alert(this, " Địa chỉ không được để trống");
            txtAdd.requestFocus();
            return false;
        }
        if (txtAdd.getText().length() < 12) {
            helper.DialogHelper.alert(this, "Địa chỉ  không được nhỏ hơn 12 kí tự");
            txtAdd.requestFocus();
            return false;
        }
        if (txtCccd.getText().equals("")) {
            helper.DialogHelper.alert(this, " Cccd không được để trống");
            txtCccd.requestFocus();
            return false;
        }
        try {
            String Cccd = txtCccd.getText();
        } catch (NumberFormatException e) {
            helper.DialogHelper.alert(this, " Cccd phải là số ");
            txtCccd.requestFocus();
            return false;
        }
        if (txtCccd.getText().length() < 12) {
            helper.DialogHelper.alert(this, "Cccd không được nhỏ hơn 12 kí tự");
            txtCccd.requestFocus();
            return false;
        }
        if (!txtCccd.getText().toString().matches("\\d+")) {

            helper.DialogHelper.alert(this, "CCCD chỉ được nhập số");
            txtCccd.requestFocus();
            return false;
        }
        return true;
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
        System.out.println("tao  lấy dc " + nv.getSodienthoai() + "|" + nv.getCccd() + "|" + nv.getAddress() + "|" + nv.getName() + "|" + nv.getCccd());
        txtSdt.setText(nv.getSodienthoai());
        txtCccd.setText(nv.getCccd());
        txtLinkAnh.setText(nv.getImg());
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
        model.setRowCount(0);

        for (NhanVien nv : nhanvien) {
            String role = nv.isRole() ? "Quản Lý" : "Nhân Viên";
            String accountStatus = nv.isIsLooked() ? "Khoá" : "Mở";

            Object[] row = new Object[]{nv.getUser(), nv.getPass(), role, accountStatus};
            model.addRow(row);
        }
    }

    public void getNV() {
//       clearForm();
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
            String cccd = txtCccd.getText();
            String img = txtLinkAnh.getText();
            System.out.println("String img = txtLinkAnh.getText(): " + txtLinkAnh.getText());
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
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception ex) {
//            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm món!");
            DialogHelper.alert(this, "Thêm mới không thành công!");
            ex.printStackTrace(); // Handle the exception appropriately, e.g., show an error message
        }
    }

    public void SuaNV(int selectedRow) {
        System.out.println("tao o hang : " + selectedRow);

        int maNV = -1;
        try {
            maNV = nhanvien.get(selectedRow).getMaNV();
        } catch (Exception e) {
            DialogHelper.alert(jPanel4, "Chọn nhân viên trong bảng để chỉnh sửa!");
        }
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
            updatedEmployee.setCccd(txtCccd.getText());
            updatedEmployee.setImg(txtLinkAnh.getText());
            dao.sua(updatedEmployee);

//        clearForm();
//        refresh();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception ex) {
            // Log or handle the exception appropriately
            ex.printStackTrace();
            DialogHelper.alert(this, "Có lỗi khi cập nhật");
            System.out.println("Error updating employee");
        }
    }

    public void xoaMon(int selectedRow) {
        System.out.println("TaoXoaNV nhe :" + selectedRow);
        int MaNV = 0;
        try {
            MaNV = nhanvien.get(selectedRow).getMaNV();
        } catch (Exception e) {
            DialogHelper.alert(this, "Chọn nhân viên trong bảng để xoá");
        }
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
            DialogHelper.alert(this, "Có lỗi khi xoá!");
//            helper.DialogHelper.alert(panel, "Xóa món thất bại!");
//            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upload() {
        String imageUrl = "";
        String apiKey = "43c266c2e17b3e719a7cd819e1d9d6a7"; // Replace with your ImgBB API key

        // Use a file chooser to let the user select the image file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an Image File");
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Image selection canceled.");
            return;
        }

        File imageFile = fileChooser.getSelectedFile();
        System.out.println("Selected Image: " + imageFile.getAbsolutePath());

        if (!imageFile.exists()) {
            System.out.println("Error: Image file not found at " + imageFile.getAbsolutePath());
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", apiKey)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(imageFile, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgbb.com/1/upload")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                if (jsonResponse.has("data")) {
                    JSONObject dataObject = jsonResponse.getJSONObject("data");
                    if (dataObject.has("url")) {
                        imageUrl = dataObject.getString("url");
                    }
                    if (dataObject.has("image")) {
                        JSONObject imageObject = dataObject.getJSONObject("image");

                        if (imageObject.has("filename")) {
                            String filename = imageObject.getString("filename");
                            System.out.println("Image uploaded successfully");
                            System.out.println("Image Filename: " + filename);
                            JOptionPane.showMessageDialog(this, "Tải Ảnh Thành Công !");
                            txtLinkAnh.setText(imageUrl);
                        } else {
                            System.out.println("Error: 'filename' not found in the response");
                        }
                    } else {
                        System.out.println("Error: 'image' not found in the response");
                    }
                } else {
                    System.out.println("Error: 'data' not found in the response");
                }
            } else {
                System.out.println("Error uploading image");
                System.out.println("Response code: " + response.code());
            }
        } catch (IOException ex) {
            Logger.getLogger(upanh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void timKiem(String tuKhoa, String type1) {
        try {
            String type;
            if ("Nhân Viên".equals(type1)) {
                type = "0";
            } else if ("QuảnLý".equals(type1)) {
                type = "1";
            } else {
                type = "%";
            }
            List<NhanVien> nhanvien = dao.searchByNameAndType("%" + tuKhoa + "%", "%" + type + "%");
            System.out.println("Tu Khoa search: " + tuKhoa);
            System.out.println("Loai search: " + type1);
            DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVien nv : nhanvien) {
                String role = nv.isRole() ? "Quản Lý" : "Nhân Viên";
                String accountStatus = nv.isIsLooked() ? "Khoá" : "Mở";

                Object[] row = new Object[]{nv.getUser(), nv.getPass(), role, accountStatus};
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
        cboloai = new javax.swing.JComboBox<>();
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

        setBackground(new java.awt.Color(255, 204, 204));

        txtUser.setBackground(new java.awt.Color(255, 204, 204));
        txtUser.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("American Typewriter", 0, 12))); // NOI18N
        txtUser.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel2.setText("Tên Người Dùng ");

        jLabel3.setText("Mật Khẩu");

        cboRole.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));

        cboTrangThai.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mở", "Khoá" }));

        jLabel5.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel5.setText("Trạng Thái");

        txttaikhoan.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        txtLinkAnh.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel6.setText("Uploading Ảnh (Hoặc dán link)");

        btnUpload.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        btnUpload.setText("UPLOAD");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        txtHoTen.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        txtSdt.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        txtAdd.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        txtCccd.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel7.setText("Họ Và Tên");

        jLabel8.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel8.setText("Ngày Sinh");

        jLabel9.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel9.setText("Số Điện Thoại");

        jLabel10.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel10.setText("Địa Chỉ");

        jLabel11.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        jLabel11.setText("Căn Cước Công Dân");

        jLabel4.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
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
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(btnUpload)
                            .addComponent(txtCccd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtUserLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addGap(51, 51, 51)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("American Typewriter", 0, 12))); // NOI18N

        txtTimKiem.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
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

        cboloai.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        cboloai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "role", "Nhân Viên", "Quản Lý" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("American Typewriter", 0, 12))); // NOI18N

        btnThem.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("American Typewriter", 0, 12)); // NOI18N
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

        tblDanhSachNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Pass", "Chức Vụ", "Tình Trạng"
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addComponent(txtUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(64, 64, 64)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        if (validateQuanLyNhanVien()) {
            if (DialogHelper.confirm(this, "Bạn có muốn thêm không?")) {
                themNV();

            }
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void tblDanhSachNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachNhanVienMousePressed
        fillToForm(tblDanhSachNhanVien.getSelectedRow());
//       refresh();
    }//GEN-LAST:event_tblDanhSachNhanVienMousePressed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        if (validateQuanLyNhanVien()) {
            if (DialogHelper.confirm(this, "Bạn có muốn sửa không?")) {
                SuaNV(tblDanhSachNhanVien.getSelectedRow());
                refresh();

            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        timKiem(txtTimKiem.getText(), cboRole.getSelectedItem().toString());
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        if (DialogHelper.confirm(this, "Bạn có muốn xóa không?")) {
            xoaMon(tblDanhSachNhanVien.getSelectedRow());
            refresh();

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (evt.getStateChange() == 1) {
            txtPass.setEchoChar((char) 0);
        } else {
            txtPass.setEchoChar('•');
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        upload();
    }//GEN-LAST:event_btnUploadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSua;
    public static javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpload;
    public static javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboRole;
    public javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboloai;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JCheckBox jCheckBox1;
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
