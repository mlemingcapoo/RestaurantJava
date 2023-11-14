package util;

import DAO.NhanVienDAO_1;
import DAO.loginAuthen;
import Helper.DialogHelper;
import Model.NhanVien;
import javax.swing.JPasswordField;
import DAO.SQL_queries_DAO;
import java.util.Calendar;

/**
 *
 * @author catty
 */
public class Validator implements implemented {

    @Override
    public boolean changePassValidate(JPasswordField currPass, JPasswordField newPass, JPasswordField rPass) {
        SQL_queries_DAO query = new NhanVienDAO_1();
        NhanVien nv = loginAuthen.getCurrentUsers().get(loginAuthen.getCurrentIndex());
        String maNV = nv.getMaNV();
        String password = nv.getPassword();
        String fullName = nv.getFullName();
        boolean role = nv.getRole();
        System.out.println("old pass: " + password);

        if (!currPass.getText().trim().isEmpty() && !newPass.getText().trim().isEmpty()) {
            if (currPass.getText().equals(password)) {
                if (newPass.getText().equals(rPass.getText())) {
                    password = rPass.getText();
                    nv.setPassword(password);
                    query.update(nv);
                    System.out.println("new pass: " + nv.getPassword());
                    DialogHelper.showDialog(null, "Đã update mật khẩu!!");
                    System.out.println("changed password for:");
                    System.out.println(maNV);
                    System.out.println(role);
                    System.out.println(fullName + "\nend");
                    return true;
                } else {
                    DialogHelper.showDialog(null, "Nhập lại mật khẩu phải trùng khớp!");
                    rPass.requestFocus();
                }
            } else {
                DialogHelper.showDialog(null, "Mật khẩu không trùng khớp");
            }
        } else {
            DialogHelper.showDialog(null, "Vui lòng nhập đầy đủ thông tin!");
        }
        return false;
    }




}
