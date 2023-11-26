package helper;


import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author catty
 */
public class DialogHelper {
// show simple dialog

    public static void alert(Component parent, String content) {
        JOptionPane.showMessageDialog(parent, content,"Cảnh báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showDialog(Component parent, String content){
        JOptionPane.showMessageDialog(parent, content,"Cảnh báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirm(Component parent, String content){
        int result = JOptionPane.showConfirmDialog(parent, content,"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String prompt(Component parent, String content){
        return JOptionPane.showInputDialog(parent, content,"Lưu ý", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean exitNow(Component parent, String content){
        int result = JOptionPane.showConfirmDialog(parent, content,"Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
//    public void openMainUI(){
//        mainUI dialog = new mainUI();
//        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent e) {
//                System.exit(0);
//            }
//        });
//        dialog.setVisible(true);
//        
//    }
}
