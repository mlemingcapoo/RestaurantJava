

package DAO;

/**
 *
 * @author catty
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCManager {

    private static final String url = "jdbc:sqlserver://localhost:1433;encrypt=false;trustServerCertificate=false;databaseName=";
    private static final String db_name = "EduSys";
    private static final String usr = "edusys";
    private static final String psw = "1234";

    private static Connection connection;
    
    

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("getting Connection...");
        if (connection == null) {
            System.out.println("connection is null, try connecting...");
            try {
                // Load the JDBC driver 
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

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

