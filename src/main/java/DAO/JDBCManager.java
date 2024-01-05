

package DAO;

/**
 *
 * @author catty
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager {
    
//    private static final String url = "jdbc:mariadb://localhost:3306/"; // MySQL URL and port
    private static final String url = "jdbc:mariadb://103.252.137.187:3306/"; // MySQL URL and port
    private static final String db_name = "hnguyenmanhidvn_DuAn1"; // Database name
    private static final String usr = "hnguyenmanhidvn_DuAn1"; // Database username
    private static final String psw = "hnguyenmanhidvn_DuAn1"; // Database password

    private static Connection connection;
    
    public static Connection getConnection() throws Exception {
        System.out.println("getting Connection...");
        if (true) {
            System.out.println("try connecting...");
            try {
                // Load the JDBC driver 
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(url + db_name, usr, psw);
                System.out.println("connected!");
            } catch (Exception e) {
                System.out.println(e.toString());
                throw e;
            }
        } else {
            System.out.println("connection exists!");
        }
        return connection;
    }

    // function that prints hello String
    public static void hello() {
        System.out.println("hello");
    }

    public static Connection reconnect() throws SQLException, Exception{
        connection.close();
        connection = DriverManager.getConnection(url + db_name, usr, psw);
        return getConnection();
    }
    
    public static boolean isConnected() {
        return connection == null;
//        return false;
    }

    public static void closeConnection() throws SQLException,Exception{
        if (connection != null) {
            
                connection.close();

            
        }
    }}

