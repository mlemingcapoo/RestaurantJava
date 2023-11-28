/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author capoo
 */
public interface GUI_Interface {
    public void login();
    public void init();
//    public void navigate(JPanel input);
    public void showFrame(JFrame frame);
    public void hideFrame(JFrame frame);
    public int checkPermission();
}
