package control;

import DAO.JDBCManager;
import GUI.mainUI;
import GUI.start;
import helper.DialogHelper;
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
        loading = new Timer(2, (ActionEvent e) -> {
            loadingPercent++;
            start.loadingBar.setValue(loadingPercent);
            System.out.println(loadingPercent);
            while (loadingPercent >= 100) {
                if (JDBCManager.isConnected()){
                    DialogHelper.alert(rootPane, "No Internet!");
                    stopLoading();
                    break;
                } else {
                    stopLoading();
                    break;
                }
            }
            if (loadingPercent == 1) {
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
        startMainUI();
    }

    public void startMainUI() {
        mainUI main = new mainUI();
        main.setVisible(true);

    }

}
