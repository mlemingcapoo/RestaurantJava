package control;

import GUI.mainUI;
import frame.LoginJPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.panelNavigator;

/**
 *
 * @author capoo
 */
public final class mainUiControl
        //        extends mainUI 
        implements GUI_Interface {

    private static mainUI frame;
    LoginJPanel loginPanel;
    
    public mainUiControl(mainUI frame) {
        mainUiControl.frame = frame;
        init();
    }

    @Override
    public void init() {
//        showFrame(loginFrame);
        System.out.println("initializing... setting up login frame");
        mainUI.BtnDangXuat.setText("Đăng Nhập");
    }

    @Override
    public void login() {
        loginPanel = new LoginJPanel();
        panelNavigator.navigate(frame, loginPanel, mainUI.panelDisplay);
        System.out.println("login");
    }


    @Override
    public void showFrame(JFrame frame) {
        System.out.println("lmao");
        frame = (mainUI) frame;
        frame.setVisible(true);

    }

    public void DangXuat() {
        System.out.println("logout!");
        frame.dispose();
    }

    @Override
    public void hideFrame(JFrame frame) {
       frame.setVisible(false);
    }

    public void QuanLyNhanVien() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void QuanLyDoanhThu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void QuanLyMon() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void QuanLyHoiVien() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void QuanLyBanHang() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void CaiDat() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int checkPermission() {
        System.out.println("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return 0;
    }

  

}
