package control;

import DAO.AuthenticateDAO;
import GUI.mainUI;
import frame.CaiDatJPanel;
import frame.LoginJPanel;
import frame.OrderPanel;
import frame.QuanLyDoanhThuJPanel;
import frame.QuanLyHoiVienJPanel;
import frame.QuanLyMonAnJPanel;
import frame.QuanLyNhanVienJPanel;
import frame.TableJPanel;
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
    JPanel LoginJPanel = new LoginJPanel() ;
    JPanel QLDoanhThu = new QuanLyDoanhThuJPanel();
    JPanel QLHoiVien = new QuanLyHoiVienJPanel();
    JPanel QLMonAn = new QuanLyMonAnJPanel();
    JPanel QLNNhanVien = new QuanLyNhanVienJPanel();
    OrderPanel QLBanHang = new OrderPanel();
    TableJPanel QLTable = new TableJPanel();
    

    JPanel Profile = new profile();

    private static mainUI frame;
    private final AuthenticateDAO dao = new AuthenticateDAO();

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
        mainUI.panelDisplay.add(LoginJPanel,"Login");
        mainUI.panelDisplay.add(QLDoanhThu, "DoanhThu");
        mainUI.panelDisplay.add(QLHoiVien, "HoiVien");
        mainUI.panelDisplay.add(QLMonAn, "MonAn");
        mainUI.panelDisplay.add(QLNNhanVien, "NhanVien");
        mainUI.panelDisplay.add(QLBanHang, "BanHang");
//        panelNavigator.switchPanel(mainUI.panelDisplay, "");
        mainUI.panelDisplay.add(Profile, "Profile");
        mainUI.panelDisplay.add(QLTable, "Table");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        mainUiControl.frame = (mainUI) frame;
        frame.setVisible(true);

    }

    public void DangXuat() {
//        panelNavigator.switchPanel(mainUI.panelDisplay, "Login");
            frame.dispose();
            new loginControl().init();
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
//        panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");

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
        panelNavigator.switchPanel(mainUI.panelDisplay, "Table");
        TableControl control = new TableControl();
        control.init(QLTable);
    }

    public void Order() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");
        OrderControl control = new OrderControl();
        control.init(QLBanHang);
    }

    public void enforePerm(int permissonLevel) {
        lockAllBtn();
        unlockPerm(dao.getPermissonLevel());
        if (permissonLevel < 0) {
            mainUI.BtnDangXuat.setEnabled(true);
//            DialogHelper.showDialog(frame, "");
            DialogHelper.alert(frame, "Tài khoản này đã bị khoá! Vui lòng liên hệ với quản trị.");
            System.out.println("vai lon?");
//            System.exit();
//            hideFrame(frame);
//            frame.BtnDangXuat.setText("fuck");
//            new startControl().loading();
//            frame.dispose();
        }
    }

    public static void lockAllBtn() {
        mainUI.BtnCaiDat.setEnabled(false);
//        mainUI.BtnGiaoCa.setEnabled(false);
        mainUI.BtnHoaDon.setEnabled(false);
//        frame.BtnDangXuat.setEnabled(false);
        mainUI.BtnDatBan.setEnabled(false);
        mainUI.BtnQuanLyBanHang.setEnabled(false);
        mainUI.BtnQuanLyMon.setEnabled(false);
        mainUI.BtnQuanLyHoiVien.setEnabled(false);
        mainUI.BtnQuanLyDoanhThu.setEnabled(false);
        mainUI.BtnQuanLyNhanVien.setEnabled(false);
        mainUI.BtnOrder.setEnabled(false);
        mainUI.panelProfilePhoto.setVisible(false);
    }

    private void unlockPerm(int level) {
        if (level >= 0) {
            mainUI.BtnCaiDat.setEnabled(true);
//            mainUI.BtnGiaoCa.setEnabled(true);
            mainUI.BtnHoaDon.setEnabled(true);
            mainUI.BtnDatBan.setEnabled(true);
            mainUI.BtnQuanLyBanHang.setEnabled(true);
            mainUI.BtnQuanLyHoiVien.setEnabled(true);
            mainUI.BtnOrder.setEnabled(true);
            mainUI.panelProfilePhoto.setVisible(true);
        }
        if (level >= 1) {
            mainUI.BtnQuanLyDoanhThu.setEnabled(true);
            mainUI.BtnQuanLyNhanVien.setEnabled(true);
            mainUI.BtnQuanLyMon.setEnabled(true);
        }
    }

    public void HoaDon() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
