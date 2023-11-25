package control;

import DAO.AuthenticateDAO;
import GUI.mainUI;
import helper.DialogHelper;
import java.awt.Dimension;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author capoo
 */
public class loginControl {

    private static GUI.login login;
    AuthenticateDAO auth = new AuthenticateDAO();

    public void login(JTextField User, JPasswordField Pass) {
        if (validated()) {
            char[] passwordChars = Pass.getPassword();
            String password = new String(passwordChars);
            System.out.println("got it!: " + User.getText() + " " + password);
            boolean isLoggedIn = auth.checkLogin(User.getText(), password);
            if (isLoggedIn) {
                startMainUI(auth.getPermissonLevel());
                System.out.println("logged in");
            } else {
                System.out.println("no.");
                DialogHelper.alert(login, "Thông tin đăng nhập không đúng!");
            }
        }

    }
    
    public void startMainUI(int permissionLevel) {
        if (permissionLevel<0){
            DialogHelper.exitNow(login, "Tài khoản này đã bị khoá! Vui lòng liên hệ với quản trị.");
        } else {
            mainUI main = new mainUI();
            login.dispose();
            main.setMinimumSize(new Dimension(1044 , 720));
            main.setVisible(true);
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
        login.setVisible(true);
    }

    private void startLocked() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
