package util;


import DAO.JDBCManager;
import java.sql.*;
/**
 *
 * @author capoo
 */


public class SQLThread implements Runnable {

    @Override
    public void run() {
        try {
            Connection con = JDBCManager.getConnection();
            System.out.println(con.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void main(String[] args) {
        Thread t = new Thread(new SQLThread());
        t.start();
    }
}
