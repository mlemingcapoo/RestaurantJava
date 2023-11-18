package control;

import DAO.DBCManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBHelper;

/**
 *
 * @author capoo
 */
public class login_frame {

    public static void login(String usr, String psw) {
        Object[] lmao = {usr};
        ResultSet value = null;
        try {
            value = DBHelper.query("SELECT * FROM User WHERE username = ?", lmao);
            if (value.next()) {
                System.out.println(value.getString(2));
                if (value.getString(3).equals(psw)){
                    System.out.println("done login");
                } else {
                    System.out.println("wtf password?");
                }
            } else {
                System.out.println("outside of result");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(login_frame.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println();
    }
}
