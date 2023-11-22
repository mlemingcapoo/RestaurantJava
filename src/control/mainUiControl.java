package control;

import GUI.mainUI;
import javax.swing.JFrame;

/**
 *
 * @author capoo
 */
public final class mainUiControl
        //        extends mainUI 
        implements GUI_Interface {

    private static mainUI frame;

    public mainUiControl(mainUI frame) {
//        super();
        mainUiControl.frame = frame;
//        init();
    }

//  @Override
//  public void buttonClicked() {
//    // xử lý sự kiện click button
////  }
    @Override
    public void init() {
//        showFrame(loginFrame);
        System.out.println("init!");

    }

    @Override
    public void login() {
        System.out.println("login");
//        frame.dispose();
    }

    @Override
    public void navigate() {
        System.out.println("rediect");
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
