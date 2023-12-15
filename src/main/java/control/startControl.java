package control;

import DAO.JDBCManager;
import GUI.start;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import util.SQLThread;

/**
 *
 * @author capoo
 */
public class startControl extends start{
    private int loadingPercent = 0;
    Timer loading;

    public void loading() {
        loading = new Timer(10, (ActionEvent e) -> {
            loadingPercent++;
            start.loadingBar.setValue(loadingPercent);
            System.out.println(loadingPercent);
            while (loadingPercent >= 100) {
                if (JDBCManager.isConnected()){
//                    DialogHelper.alert(rootPane, "Please check your Internet!");
                    stopLoading();
                    break;
                } else {
                    stopLoading();
                    break;
                }
            }
            if (loadingPercent == 10) {
                new SQLThread().main(null);
            }
            if (!JDBCManager.isConnected()) {
                loadingPercent = 99;
            }
        });
        loading.start();
    }
    
    public void stopLoading(){
        loading.stop();
        System.out.println("timer stopped, starting main UI");
        super.dispose();
        System.out.println("stopped loading screen");
        startLogin();
//        startMainUI();
    }


    private void startLogin() {
        System.out.println("openning login frame");
        new loginControl().init();
    }

}
