package control;

import DAO.AuthenticateDAO;
import GUI.CustomerScreen;
import GUI.mainUI;
import frame.CaiDatJPanel;
import frame.LoginJPanel;
import frame.OrderPanel;
import frame.QuanLyDoanhThuJPanel;
import frame.QuanLyHoiVienJPanel;
import frame.QuanLyMonAnJPanel;
import frame.QuanLyNhanVienJPanel;
import frame.TableJPanel;
import frame.VoucherJPanel;
import frame.profile;
import helper.DialogHelper;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import util.panelNavigator;

/**
 *
 * @author capoo
 */
public final class mainUiControl implements GUI_Interface {

    JPanel caiDat = new CaiDatJPanel();
//    JPanel DoiMatKhau = new DoiMatKhau();
    JPanel LoginJPanel = new LoginJPanel();
    JPanel QLDoanhThu = new QuanLyDoanhThuJPanel();
    JPanel QLHoiVien = new QuanLyHoiVienJPanel();
    JPanel QLMonAn = new QuanLyMonAnJPanel();
    JPanel QLNNhanVien = new QuanLyNhanVienJPanel();
    OrderPanel QLBanHang = new OrderPanel();
    TableJPanel QLTable = new TableJPanel();
    JPanel Vocher = new VoucherJPanel();
    CustomerScreen customerFrame = new CustomerScreen();
    JPanel Profile = new profile();

    public static mainUI frame;
    private final AuthenticateDAO dao = new AuthenticateDAO();
    Thread t;
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public mainUiControl(mainUI frame) {
        mainUiControl.frame = frame;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("executor is a lone wolf");
                timer();
                // Code to be executed in a separate thread
//                frame.lblTime
            }
        });
        mainThread();
    }

    public void timer() {
        setTime();
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTime();
            }
        });
        timer.start();
    }

    public void setTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
        mainUI.lblTime.setText(formattedTime);
//        System.out.println("whattttttt");
    }

    public void mainThread() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    init();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void init() {
        System.out.println("a wild thread has started... " + t.isAlive());
        System.out.println("Perm level: " + dao.getPermissonLevel());
        enforePerm(dao.getPermissonLevel());

        mainUI.BtnDangXuat.setText("Đăng xuất");
        System.out.println("... loading frame into panelDisplay...");
        System.out.println("loading Settings");
        mainUI.panelDisplay.add(caiDat, "CaiDat");
//        mainUI.panelDisplay.add(DoiMatKhau,"DoiMatKhau");
        System.out.println("loading Login");
        mainUI.panelDisplay.add(LoginJPanel, "Login");
        System.out.println("loading DoanhThu");
        mainUI.panelDisplay.add(QLDoanhThu, "DoanhThu");
        System.out.println("loading HoiVien");
        mainUI.panelDisplay.add(QLHoiVien, "HoiVien");
        System.out.println("loading MonAn");
        mainUI.panelDisplay.add(QLMonAn, "MonAn");
        System.out.println("loading NhanVien");
        mainUI.panelDisplay.add(QLNNhanVien, "NhanVien");
        System.out.println("loading BanHang");
        mainUI.panelDisplay.add(QLBanHang, "BanHang");

//      panelNavigator.switchPanel(mainUI.panelDisplay, "");
        System.out.println("loading profile");
        mainUI.panelDisplay.add(Profile, "Profile");
        System.out.println("loading Table");
        mainUI.panelDisplay.add(QLTable, "Table");
        System.out.println("loading voucher");
        mainUI.panelDisplay.add(Vocher, "Voucher");
        System.out.println("setting full screen");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        System.out.println("interupting thread....");
//        t.interrupt();
        System.out.println("starting customer screen");
        startCustomer();
//        System.out.println("All Done!!!!!!!"); 
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
        customerFrame.dispose();
    }

    private void startCustomer() {

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("getting display list...");
        // Get all graphics devices
        GraphicsDevice[] devices = env.getScreenDevices();

        // Loop through devices to find external one
        GraphicsDevice externalDevice = null;
        System.out.println("display devices: " + Arrays.toString(devices));
        System.out.println("default: " + Arrays.toString(env.getDefaultScreenDevice().getDisplayModes()));
        for (GraphicsDevice device : devices) {

            if (device.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
                System.out.println("Display type IS TYPE_RASTER");
                // Get first external GPU device
                externalDevice = device;
                break;
            }
        }

        System.out.println("devices length?: " + devices.length);
        if (externalDevice != null && devices.length > 1) {
            System.out.println("external?: " + externalDevice.getIDstring());
            System.out.println("external?: " + externalDevice.getAvailableAcceleratedMemory());
            System.out.println("external?: " + externalDevice.getDisplayMode());
            System.out.println("external?: " + Arrays.toString(externalDevice.getDisplayModes()));
            System.out.println("external?: " + Arrays.toString(externalDevice.getConfigurations()));
            // Get bounds of external display
            Rectangle bounds = externalDevice.getDefaultConfiguration().getBounds();

            customerFrame.setBounds(bounds);
            customerFrame.setVisible(true);

//            login.dispose();
        } else {
            System.out.println("No external display found...");
            DialogHelper.alert(frame, "Phần mềm khuyến nghị cài đặt thêm màn hình thứ 2 để sử dụng tính năng màn hình khách!");
        }
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

    public void Order(mainUI aThis) {
        panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");
        OrderControl control = new OrderControl(aThis);
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
//        mainUI.BtnQuanLyBanHang.setEnabled(false);
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
//            mainUI.BtnQuanLyBanHang.setEnabled(true);
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

    public void QuanLyVoucher() {

        panelNavigator.switchPanel(mainUI.panelDisplay, "Voucher");
    }

    public void minimize(mainUI aThis) {
        aThis.setState(Frame.ICONIFIED);

    }

    public void GioiThieu() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
