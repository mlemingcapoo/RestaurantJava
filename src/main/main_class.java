/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import control.startControl;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author catty
 */
public class main_class {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String log4jConfPath = "src/main/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        // TODO code application logic here
        System.out.println("hello");
        try {
            // master changed
//            JDBCManager.getConnection();
            System.out.println("done");
            new startControl().loading();
        } catch (Exception ex) {
//            Logger.getLogger(main_class.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NO INTERNET");
        }
    }

}
