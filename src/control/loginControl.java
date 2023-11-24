package control;

import DAO.AuthenticateDAO;
import GUI.mainUI;
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
                login.dispose();
                startMainUI();
                System.out.println("logged in");
            } else {
                System.out.println("no.");
            }
        }

    }
    
    public void startMainUI() {
        mainUI main = new mainUI();
        main.setVisible(true);

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
}
