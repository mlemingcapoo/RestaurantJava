package helper;

import DAO.DBCManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author catty
 */
public class DBHelper {

    public static PreparedStatement prepareState(String sql, Object... args) throws ClassNotFoundException, SQLException {

        PreparedStatement psmt = null;
        if (sql.trim().startsWith("{")) {
            psmt = DBCManager.getConnection().prepareCall(sql);
        } else {
            psmt = DBCManager.getConnection().prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            psmt.setObject(i + 1, args[i]);
        }
        return psmt;
    }

    public static void executeUpdate(String sql, Object... args) throws ClassNotFoundException, SQLException {

        PreparedStatement psmt = prepareState(sql, args);
        psmt.executeUpdate();
    }
    
    public static ResultSet executeQuery(String sql, Object...args) throws ClassNotFoundException,SQLException{
        PreparedStatement psm = prepareState(sql, args);
        return psm.executeQuery();
    }
    
    public static ResultSet query(String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = DBHelper.getStmt(sql, args);
        return stmt.executeQuery();
    }
    
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection conn = DBCManager.getConnection();
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
    
}
