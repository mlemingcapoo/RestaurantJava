

package main;

import DAO.JDBCManager;
import helper.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author capoo
 */
public class testClass {
public static void main(String[] args) {
        
        try {
            JDBCManager.getConnection();
            ResultSet executeProc =
// DBHelper.executeProc("ThemVoucher",2,10,"trungVoucherXD","2023-01-01");
            DBHelper.executeProc("XoaVocher");
            while (executeProc.next()){
                System.out.print (executeProc.getDate(1));
                System.out.print(" | ");
                System.out.println(executeProc.getDouble(2));
                
            }
            System.out.println(executeProc.toString());
        } catch (SQLException ex) {
            Logger.getLogger(testClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(testClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
