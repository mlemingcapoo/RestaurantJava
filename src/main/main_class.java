/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import DAO.DBCManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author catty
 */
public class main_class {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("hello");
        try {
            // master changed
            DBCManager.getConnection();
            System.out.println("done");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(main_class.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
