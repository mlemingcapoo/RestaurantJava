package control;

import DAO.AuthenticateDAO;
import GUI.mainUI;
import helper.DialogHelper;
import helper.LoadingHelper;
import java.awt.Dimension;
import java.sql.SQLNonTransientConnectionException;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author capoo
 */
public class loginControl {

    private static GUI.login login;
    AuthenticateDAO auth = new AuthenticateDAO();
    boolean isLoggedIn = false;
    boolean connectionResetted = false;
    

    public void login(JTextField User, JPasswordField Pass) {
        new Thread(() -> {
            try {

                LoadingHelper<JFrame> helper = new LoadingHelper<>(login,"Đang đăng nhập...\nVui lòng đợi");
                helper.showLoadingDialog();

//                 jButton2.setEnabled(false);
                //action
                if (validated()) {

            
//            try {
//                isLoggedIn = auth.checkLogin(User.getText(), password);
//
//            } catch (SQLNonTransientConnectionException e) {
//                connectionResetted = true;
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
            threadLogin(User, Pass);
            if (isLoggedIn) {
                startMainUI(auth.getPermissonLevel());
                System.out.println("logged in");
            } else if (connectionResetted && !isLoggedIn) {
                isLoggedIn = false;
                System.out.println("connection lost.");
                DialogHelper.alert(login, "Mất kết nối!");
            } else {
                isLoggedIn = false;
                System.out.println("no.");
                DialogHelper.alert(login, "Thông tin đăng nhập không đúng!");
            }
        }
//                 jButton2.setEnabled(true);
                helper.done();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void threadLogin(JTextField User, JPasswordField Pass) {
        
        try {
            char[] passwordChars = Pass.getPassword();
            String password = new String(passwordChars);
//            System.out.println("got it!: " + User.getText() + " " + password);
            isLoggedIn = auth.checkLogin(User.getText(), password);
//            while (!isLoggedIn) {
                System.out.println("not logged in...");
//                threadLogin(User, Pass);
//            }
        } catch (SQLNonTransientConnectionException e){
            connectionResetted = true;
//            DialogHelper.alert(login, "Lỗi kết nối...");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startMainUI(int permissionLevel) {
        if (permissionLevel < 0) {
            DialogHelper.alert(login, "Tài khoản này đã bị khoá! Vui lòng liên hệ với quản trị.");
        } else {
            mainUI main = new mainUI();
            main.setMinimumSize(new Dimension(1044, 720));
            main.setVisible(true);
            login.dispose();
//            startCustomer();
        }
    }

    private boolean validated() {
        System.out.println("validated?");
        //        Object[] lmao = {user};
        return true;
    }

    void init() {
        login = new GUI.login(this);
        login.setLocationRelativeTo(null);
        Dimension defaultSize = new Dimension(800, 650);
        login.setSize(defaultSize);
        login.setMinimumSize(defaultSize);
        login.setMaximumSize(defaultSize);
        login.setVisible(true);
    }

    public void forgorPass() {

    }

}
