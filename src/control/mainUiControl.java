package control;

import GUI.mainUI;
import frame.CaiDatJPanel;
import frame.DoiMatKhau;
import frame.LoginJPanel;
import frame.QuanLyDoanhThuJPanel;
import frame.QuanLyHoiVienJPanel;
import frame.QuanLyMonAnJPanel;
import frame.QuanLyNhanVienJPanel;
import frame.QuanLyQuanLyBanHangJPanel;
import frame.profile;
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
    JPanel QLBanHang = new QuanLyQuanLyBanHangJPanel();
    JPanel Profile = new profile();

    private static mainUI frame;
    
    public mainUiControl(mainUI frame) {
        mainUiControl.frame = frame;
        init();
    }

    @Override
    public void init() {
//        showFrame(loginFrame);
        System.out.println("initializing... setting up login frame");
        mainUI.BtnDangXuat.setText("Đăng Nhập");
        
        mainUI.panelDisplay.add(caiDat,"CaiDat");
//        mainUI.panelDisplay.add(DoiMatKhau,"DoiMatKhau");
        mainUI.panelDisplay.add(LoginJPanel,"Login");
        mainUI.panelDisplay.add(QLDoanhThu,"DoanhThu");
        mainUI.panelDisplay.add(QLHoiVien,"HoiVien");
        mainUI.panelDisplay.add(QLMonAn,"MonAn");
        mainUI.panelDisplay.add(QLNNhanVien,"NhanVien");
        mainUI.panelDisplay.add(QLBanHang,"BanHang");
        mainUI.panelDisplay.add(Profile,"Profile");
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

  

}
