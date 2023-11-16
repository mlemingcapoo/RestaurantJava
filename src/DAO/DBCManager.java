

package DAO;

/**
 *
 * @author catty
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCManager {
    
    private static final String url = "jdbc:mysql://103.252.137.126:3306/"; // MySQL URL and port
    private static final String db_name = "hnguyenmanhidvn_DuAn1"; // Database name
    private static final String usr = "hnguyenmanhidvn_DuAn1"; // Database username
    private static final String psw = "hnguyenmanhidvn_DuAn1"; // Database password

    private static Connection connection;
    
    

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("getting Connection...");
        if (connection == null) {
            System.out.println("connection is null, try connecting...");
            try {
                // Load the JDBC driver 
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url + db_name, usr, psw);
                System.out.println("connected!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.toString());
                throw e;
            }
        } else {
            System.out.println("connection exists!");
        }
        return connection;
    }
    
    public static void reconnect(
            String usr,
            String psw,
            String url,
            String db_name) throws SQLException{
        connection = DriverManager.getConnection(url + db_name, usr, psw);
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }}

