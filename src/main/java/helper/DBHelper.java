package helper;

import DAO.JDBCManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author catty
 */
public class DBHelper {

    public static PreparedStatement prepareState(String sql, Object... args) throws ClassNotFoundException, SQLException, Exception {

        PreparedStatement psmt;
        if (sql.trim().startsWith("{")) {
            psmt = JDBCManager.getConnection().prepareCall(sql);
        } else {
            psmt = JDBCManager.getConnection().prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            psmt.setObject(i + 1, args[i]);
        }
        return psmt;
    }

    public static void executeUpdate(String sql, Object... args) throws ClassNotFoundException, SQLException, Exception {

        PreparedStatement psmt = prepareState(sql, args);
        psmt.executeUpdate();
    }

    public static ResultSet executeQuery(String sql, Object... args) throws ClassNotFoundException, SQLException, Exception {
        PreparedStatement psm = prepareState(sql, args);
        return psm.executeQuery();
    }

    public static ResultSet query(String sql, Object... args) throws SQLException, ClassNotFoundException, Exception {
//        JDBCManager.closeConnection();
//        JDBCManager.getConnection();
        PreparedStatement stmt = DBHelper.getStmt(sql, args);
        return stmt.executeQuery();
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException, ClassNotFoundException, Exception {
        Connection conn = JDBCManager.getConnection();
        PreparedStatement stmt;

        if (sql.trim().startsWith("{")) {
            stmt = conn.prepareCall(sql); //PROC
        } else {
            stmt = conn.prepareStatement(sql); //SQL
        }

        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = DBHelper.query(sql, args);
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Xjdbc.UPDATE()
    public static int update(String sql, Object... args) {
        try {
            PreparedStatement stmt = DBHelper.getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeProc(String procName, Object... args) throws Exception {
        
        StringBuilder sb = new StringBuilder();
        Connection conn = JDBCManager.getConnection();
        CallableStatement stmt = null;

        String prepareArgs = "";
        for (int i = 0; i < args.length; i++) {
//            System.out.println("?,");
            sb.append("?,");
            prepareArgs = sb.toString();
        }
//        String finalArg = "";
        System.out.println("preparedArgs before process: "+prepareArgs);
        String finalArg = removeComma(prepareArgs);
        System.out.println("preparedArgs after processed: "+finalArg);
        if (args.length < 1) {
            stmt = conn.prepareCall("CALL " + procName);
        } else {
            stmt = conn.prepareCall("CALL " + procName + "(" + finalArg + ")");
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
        }
        System.out.println("ảgs: " + args.length);
        System.out.println(stmt);
        
        stmt.execute();
        
        return stmt.getResultSet();
        
    }
    
    public static String removeComma(String str) {
    if(str.endsWith(",")) {
        str = str.substring(0, str.length() - 1);
    }
    return str;
}

}
