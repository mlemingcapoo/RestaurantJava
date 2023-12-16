package control;

import DAO.AuthenticateDAO;
import GUI.CustomerScreen;
import GUI.Settings;
import GUI.mainUI;
import static GUI.mainUI.BtnDatBan;
import static GUI.mainUI.BtnHoaDon;
import static GUI.mainUI.btnOrder;
import static GUI.mainUI.cardDisplayWrapper;
import static GUI.mainUI.jplMenuCover;
import static GUI.mainUI.panelDisplay;
import frame.CaiDatJPanel;
import frame.HoaDonJPanel;
import frame.LoginJPanel;
import frame.OrderPanel;
import frame.QuanLyDoanhThuJPanel;
import frame.QuanLyHoiVienJPanel;
import frame.QuanLyMonAnJPanel;
import frame.QuanLyNhanVienJPanel;
import frame.TableJPanel;
import frame.VoucherJPanel;
import frame.blank_frame;
import frame.profile;
import helper.DialogHelper;
import helper.LoadingHelper;
import helper.RoundedCornerBorder;
import java.awt.Color;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import util.SQLThread;
import util.panelNavigator;

/**
 *
 * @author capoo
 */
public final class mainUiControl implements GUI_Interface {

    int width;
    int height;
    boolean isMinimized;

//    OrderPanel QLBanHang = new OrderPanel();
    JPanel caiDat = new CaiDatJPanel();
//    JPanel DoiMatKhau = new DoiMatKhau();
    JPanel LoginJPanel = new LoginJPanel();
    JPanel QLDoanhThu = new QuanLyDoanhThuJPanel();
    JPanel QLHoiVien = new QuanLyHoiVienJPanel();
    JPanel QLMonAn = new QuanLyMonAnJPanel();
    JPanel QLNNhanVien = new QuanLyNhanVienJPanel();
    OrderPanel QLBanHang = new OrderPanel();
    blank_frame init = new blank_frame();
    TableJPanel QLTable = new TableJPanel();
    JPanel Vocher = new VoucherJPanel();
    CustomerScreen customerFrame = new CustomerScreen();
    JPanel Profile = new profile();
    HoaDonJPanel HoaDon = new HoaDonJPanel();

    public static mainUI mainUI;
    static JFrame parent;
    private final AuthenticateDAO dao = new AuthenticateDAO();
    Thread t;
    ExecutorService executor = Executors.newFixedThreadPool(1);

    public mainUiControl(mainUI frame) {
        mainUiControl.mainUI = frame;

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
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = graphicsDevice.getDisplayMode().getWidth();
        height = graphicsDevice.getDisplayMode().getHeight();
        y = width;
        System.out.println("chieu rong man hinh: " + width);
        panelDisplay.setSize(width, height);

        System.out.println("a wild thread has started... " + t.isAlive());
        System.out.println("Perm level: " + dao.getPermissonLevel());
        enforePerm(dao.getPermissonLevel());

//        mainUI.BtnDangXuat.setText("Đăng xuất");
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
//        panelDisplay.add(QLBanHang, "BanHang");
        mainUI.panelDisplay.add(QLBanHang, "BanHang");

//      panelNavigator.switchPanel(mainUI.panelDisplay, "");
        System.out.println("loading profile");
        mainUI.panelDisplay.add(Profile, "Profile");
        System.out.println("loading Table");
        mainUI.panelDisplay.add(QLTable, "Table");
        System.out.println("loading voucher");
        mainUI.panelDisplay.add(Vocher, "Voucher");
        mainUI.panelDisplay.add(HoaDon, "HoaDon");
        System.out.println("setting full screen");
        mainUI.panelDisplay.add(init, "blank");

        mainUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        System.out.println("interupting thread....");
//        t.interrupt();

//        System.out.println("All Done!!!!!!!"); 
        cardDisplayWrapper.setVisible(true);
        jplMenuCover.setSize(410, 900);
        mainUI.setExtendedState(mainUI.MAXIMIZED_BOTH);

        panelDisplay.add(init, "blank");

        mainUI.addWindowStateListener((WindowEvent e) -> {
            if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
                isMinimized = true;
            } else if (isMinimized && (e.getNewState() == Frame.NORMAL)) {
                isMinimized = false;
                mainUI.setExtendedState(MAXIMIZED_BOTH);
            }
        });
        btnOrder.setForeground(Color.darkGray);
        btnOrder.setBorder(new RoundedCornerBorder());
        BtnDatBan.setForeground(Color.darkGray);
        BtnDatBan.setBorder(new RoundedCornerBorder());
        BtnHoaDon.setForeground(Color.darkGray);
        BtnHoaDon.setBorder(new RoundedCornerBorder());
        panelNavigator.switchPanel(mainUI.panelDisplay, "blank");
        System.out.println("starting customer screen");
//        panelNavigator.switchPanel(mainUI.panelDisplay, "");
//        startCustomer();
    }

    static int x = 210;    //chieu rong
    static int y = 300;    //chieu cao

    public static void openMenu() {

        jplMenuCover.setSize(x, y);

        if (x == 52) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        for (int i = 52; i <= 210; i++) {
                            jplMenuCover.setSize(i, y);
                            Thread.sleep(1);
                        }

                    } catch (Exception e) {
                    }
                }
            }).start();
            x = 210;
        }
    }

    public static void closeMenu() {
        jplMenuCover.setSize(x, y);
        if (x == 210) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 210; i >= 52; i--) {
                            jplMenuCover.setSize(i, y);
                            Thread.sleep(1);

                        }
                        panelDisplay.repaint();
//                        panelDisplayCustomer.revalidate();
//                        toolBarSeparator.setVisible(true);
                    } catch (Exception e) {
                    }
                }
            }).start();
            x = 52;
        }
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
        mainUiControl.mainUI = (mainUI) frame;
        frame.setVisible(true);

    }

    public void DangXuat() {
//        panelNavigator.switchPanel(mainUI.panelDisplay, "Login");
        mainUI.dispose();
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
//            DialogHelper.alert(mainUI, "Phần mềm khuyến nghị cài đặt thêm màn hình thứ 2 để sử dụng tính năng màn hình khách!");
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
//        panelNavigator.switchPanel(mainUI.panelDisplay, "CaiDat");
        parent = mainUI;
        new Settings(parent, true).setVisible(true);
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
        LoadingHelper loading = new LoadingHelper("Updating");
        loading.setLoadingStatus(true);
        panelNavigator.switchPanel(mainUI.panelDisplay, "Table");
        TableControl control = new TableControl();
        control.init(QLTable);
    }

    public void Order(mainUI aThis) {
        LoadingHelper loading = new LoadingHelper("Updating");
        loading.setLoadingStatus(true);
        mainUI.btnOrder.setEnabled(false);
        new Thread(() -> {
            try {
                panelNavigator.switchPanel(mainUI.panelDisplay, "BanHang");
                OrderControl control = new OrderControl(aThis);
                control.init(QLBanHang);
                mainUI.btnOrder.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void enforePerm(int permissonLevel) {
        lockAllBtn();
        unlockPerm(dao.getPermissonLevel());
        if (permissonLevel < 0) {
            mainUI.BtnDangXuat.setEnabled(true);
//            DialogHelper.showDialog(frame, "");
            DialogHelper.alert(mainUI, "Tài khoản này đã bị khoá! Vui lòng liên hệ với quản trị.");
            System.out.println("vai lon?");
//            System.exit();
//            hideFrame(frame);
//            frame.BtnDangXuat.setText("fuck");
//            new startControl().loading();
//            frame.dispose();
        }
    }

    public static void lockAllBtn() {
//        mainUI.BtnCaiDat.setVisible(false);
//        mainUI.BtnGiaoCa.setEnabled(false);
        mainUI.BtnHoaDon.setVisible(false);
//        frame.BtnDangXuat.setEnabled(false);
        mainUI.BtnDatBan.setVisible(false);
//        mainUI.BtnQuanLyBanHang.setEnabled(false);
        mainUI.BtnQuanLyMon.setVisible(false);
        mainUI.BtnQuanLyHoiVien.setVisible(false);
//        mainUI.BtnQuanLyDoanhThu.setEnabled(false);
        mainUI.BtnQuanLyNhanVien.setVisible(false);
        mainUI.btnOrder.setVisible(false);
//        mainUI.panelProfilePhoto.setVisible(false);
    }

    private void unlockPerm(int level) {
        if (level >= 0) {
//            mainUI.BtnCaiDat.setVisible(true);
//            mainUI.BtnGiaoCa.setEnabled(true);
            mainUI.BtnHoaDon.setVisible(true);
            mainUI.BtnDatBan.setVisible(true);
//            mainUI.BtnQuanLyBanHang.setEnabled(true);
            mainUI.BtnQuanLyHoiVien.setVisible(true);
            mainUI.btnOrder.setVisible(true);
//            mainUI.panelProfilePhoto.setVisible(true);
        }
        if (level >= 1) {
//            mainUI.BtnQuanLyDoanhThu.setEnabled(true);
            mainUI.BtnQuanLyNhanVien.setVisible(true);
            mainUI.BtnQuanLyMon.setVisible(true);
        }
    }

    public void HoaDon() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "HoaDon");
        HoaDon.init();
    }

    public void QuanLyVoucher() {

        panelNavigator.switchPanel(mainUI.panelDisplay, "Voucher");
    }

    public void minimize(mainUI aThis) {
        aThis.setState(Frame.ICONIFIED);

    }

    public void GioiThieu() {
        panelNavigator.switchPanel(mainUI.panelDisplay, "blank");
    }

    public void refreshCon() {
        LoadingHelper loading = new LoadingHelper("Refreshing");
        loading.setLoadingStatus(true);
        new SQLThread().main(null);
    }

}
