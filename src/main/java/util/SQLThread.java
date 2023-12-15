package util;

import DAO.JDBCManager;
import helper.LoadingHelper;
import java.sql.Connection;

/**
 *
 * @author capoo
 */
public class SQLThread implements Runnable {

    @Override
    public void run() {
        try {
            LoadingHelper load = new LoadingHelper("Refreshing");
            JDBCManager.closeConnection();
            Connection con = JDBCManager.reconnect();
            System.out.println(con.toString());
            load.setLoadingStatus(false);
        } catch (Exception e) {
            LoadingHelper load = new LoadingHelper("Mất kết nối");
            load.setLoadingStatus(true);
            System.out.println(e.toString());
            while (true) {
                System.out.println("trying.... to recommect....");
                try {
                    JDBCManager.closeConnection();
                    Thread.sleep(2000);
//                    new SQLThread().main(null);
                    System.out.println("reconnecting...");
                    Connection con = JDBCManager.getConnection();
                    load.setLoadingStatus(false);
                    System.out.println("about to done!");
                    break;
                } catch (Exception e1) {

                }
            }
        }
    }

    public void main(String[] args) {
        Thread t = new Thread(new SQLThread());
        t.start();
    }
}
