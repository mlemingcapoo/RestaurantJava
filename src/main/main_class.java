/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import control.startControl;
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
//            JDBCManager.getConnection();
            System.out.println("done");
            new startControl().loading();
        } catch (Exception ex) {
//            Logger.getLogger(main_class.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NO INTERNET");
        }
    }
    
}
