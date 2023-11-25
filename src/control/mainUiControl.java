package control;

import DAO.AuthenticateDAO;
import GUI.mainUI;
import frame.CaiDatJPanel;
import frame.QuanLyDoanhThuJPanel;
import frame.QuanLyHoiVienJPanel;
import frame.QuanLyMonAnJPanel;
import frame.QuanLyNhanVienJPanel;
import frame.QuanLyQuanLyBanHangJPanel;
import frame.VocherJPanel;
import frame.profile;
import helper.DialogHelper;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.panelNavigator;

/**
 *
 * @author capoo
 */
public final class mainUiControl implements GUI_Interface {

    JPanel caiDat = new CaiDatJPanel();
//    JPanel DoiMatKhau = new DoiMatKhau();
//    JPanel LoginJPanel = new LoginJPanel() ;
    JPanel QLDoanhThu = new QuanLyDoanhThuJPanel();
    JPanel QLHoiVien = new QuanLyHoiVienJPanel();
    JPanel QLMonAn = new QuanLyMonAnJPanel();
    JPanel QLNNhanVien = new QuanLyNhanVienJPanel();
    JPanel QLBanHang = new QuanLyQuanLyBanHangJPanel();
    
    JPanel Profile = new profile();

    private static mainUI frame;
    private AuthenticateDAO dao = new AuthenticateDAO();

    public mainUiControl(mainUI frame) {
        mainUiControl.frame = frame;
        init();
    }

    @Override
    public void init() {
        System.out.println("Perm level: " + dao.getPermissonLevel());
        enforePerm(dao.getPermissonLevel());

        mainUI.BtnDangXuat.setText("Đăng xuất");
        System.out.println("... loading frame into panelDisplay...");
        mainUI.panelDisplay.add(caiDat, "CaiDat");
//        mainUI.panelDisplay.add(DoiMatKhau,"DoiMatKhau");
//        mainUI.panelDisplay.add(LoginJPanel,"Login");
        mainUI.panelDisplay.add(QLDoanhThu, "DoanhThu");
        mainUI.panelDisplay.add(QLHoiVien, "HoiVien");
        mainUI.panelDisplay.add(QLMonAn, "MonAn");
        mainUI.panelDisplay.add(QLNNhanVien, "NhanVien");
        mainUI.panelDisplay.add(QLBanHang, "BanHang");
//        panelNavigator.switchPanel(mainUI.panelDisplay, "");
        mainUI.panelDisplay.add(Profile, "Profile");

    }

    @Override
    public void login() {
//        loginPanel = new LoginJPanel();

        System.out.println("login");
        panelNavigator.switchPanel(mainUI.panelDisplay, "Login");
    }

    @Override
    public void showFrame(JFrame frame) {
        System.out.println("lmao");
        frame = (mainUI) frame;
        frame.setVisible(true);

    }

    public void DangXuat() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "Login");
    }

    @Override
    public void hideFrame(JFrame frame) {
        frame.setVisible(false);
    }

    public void QuanLyNhanVien() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "NhanVien");
    }

    public void QuanLyDoanhThu() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "DoanhThu");
    }

    public void QuanLyMon() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "MonAn");
    }

    public void QuanLyHoiVien() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "HoiVien");
    }

    public void QuanLyBanHang() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");
    }

    public void CaiDat() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "CaiDat");
    }

    @Override
    public int checkPermission() {
        System.out.println("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return 0;
    }

    public void profile() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "Profile");

    }

    

    public void GiaoCa() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void DatBan() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void Order() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");
    }

    public void enforePerm(int permissonLevel) {
        lockAllBtn();
        unlockPerm(dao.getPermissonLevel());
        if (permissonLevel < 0) {
            frame.BtnDangXuat.setEnabled(true);
            DialogHelper.exitNow(frame, "Tài khoản này đã bị khoá! Vui lòng liên hệ với quản trị.");
            System.exit(1);
//            hideFrame(frame);
//            frame.BtnDangXuat.setText("fuck");
//            new startControl().loading();
        } 
    }

    public static void lockAllBtn() {
        frame.BtnCaiDat.setEnabled(false);
        frame.BtnGiaoCa.setEnabled(false);
        frame.BtnHoaDon.setEnabled(false);
//        frame.BtnDangXuat.setEnabled(false);
        frame.BtnDatBan.setEnabled(false);
        frame.BtnQuanLyBanHang.setEnabled(false);
        frame.BtnQuanLyMon.setEnabled(false);
        frame.BtnQuanLyHoiVien.setEnabled(false);
        frame.BtnQuanLyDoanhThu.setEnabled(false);
        frame.BtnQuanLyNhanVien.setEnabled(false);
        frame.BtnOrder.setEnabled(false);
        frame.panelProfilePhoto.setVisible(false);
    }

    private void unlockPerm(int level) {
        if (level>=0){
           frame.BtnCaiDat.setEnabled(true);
           frame.BtnGiaoCa.setEnabled(true);
            frame.BtnHoaDon.setEnabled(true);
            frame.BtnDatBan.setEnabled(true);
            frame.BtnQuanLyBanHang.setEnabled(true);
            frame.BtnQuanLyHoiVien.setEnabled(true);
            frame.BtnOrder.setEnabled(true);
            frame.panelProfilePhoto.setVisible(true);
        } if (level>=1){
            frame.BtnQuanLyDoanhThu.setEnabled(true);
        frame.BtnQuanLyNhanVien.setEnabled(true);
        frame.BtnQuanLyMon.setEnabled(true);
        }
    }



}
