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
        init();
    }

//  @Override
//  public void buttonClicked() {
//    // xử lý sự kiện click button
//  }
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

}
