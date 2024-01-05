/*
 * trungpvpp02786
 */

package main;

import control.startControl;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;


/**
 *
 * @author capoo
 */
public class main_class {

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String log4jConfPath = "src/main/java/resources/log4j.properties";
//        PropertyConfigurator.configure(log4jConfPath);
        // TODO code application logic here
        System.out.println("hello");
        try {
            // master changed
//            JDBCManager.getConnection();
            System.out.println("done");
            new startControl().loading();
//            mainUI hehe = new mainUI();
//            hehe.setVisible(true);
        } catch (Exception ex) {
//            Logger.getLogger(main_class.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NO INTERNET");
        }
    }

    //function that prints a String with given loop counts
    /**
     * Prints a string a given number of times
     * @param str the string to print
     * @param count the number of times to print the string
     */

    public static void printString(String str, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(str);
        }
    }

    // new jdialog frame
    /**
     * @param args the command line arguments
     */

//public static void main(String[] args) {
//new JDialog frame;
//
//}




}
