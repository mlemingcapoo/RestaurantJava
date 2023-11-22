

package control;

import java.util.Arrays;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author capoo
 */
public class loginControl {
 public void login(JTextField testUser, JPasswordField testPass) {
        System.out.println("got it!: "+testUser.getText()+" "+Arrays.toString(testPass.getPassword()));
//        DialogHelper.alert(login, "lmao");
        //        ResultSet value = null;
//        try {
//            value = DBHelper.query("SELECT * FROM User WHERE username = ?", lmao);
//            if (value.next()) {
//                System.out.println(value.getString(2));
//                if (value.getString(3).equals(txtPass.getText())){
//                    System.out.println("done login");
//                } else {
//                    System.out.println("wtf password?");
//                }
//            } else {
//                System.out.println("outside of result");
//            }
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(DangNhapControl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        System.out.println();

    }
    
    private void validate(){
        System.out.println("lmao");
        //        Object[] lmao = {user};

    }
}
