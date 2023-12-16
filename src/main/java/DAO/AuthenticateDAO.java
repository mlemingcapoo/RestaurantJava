/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
//
import helper.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLNonTransientConnectionException;

/**
 *
 * @author catty
 */
public class AuthenticateDAO {

    private static int permissonLevel=-1;
    private static String username;
    private static int id;
    private static String name;

    public boolean checkLogin(String user, String pass) throws SQLNonTransientConnectionException, Exception{
        ResultSet value = null;
        boolean result = false;
            value = DBHelper.query("SELECT * FROM User WHERE username = ? LIMIT 1", user);
            if (value.next()) {
                System.out.println(value.getString(2));
                if (value.getString(3).equals(pass)) {
                    username = value.getString(2);
                    permissonLevel = value.getInt(4);
                    id = value.getInt(1);
                    name = value.getString(7);
                    if (value.getBoolean(5)){
                        permissonLevel = -1;
                    }
                    System.out.println("done login");
                    result = true;
                } else {
                    System.out.println("wtf password?");
                    result = false;
                }
            } else {
                System.out.println("outside of result");
                result = false;
            }

        return result;
    }

    public static String getUsername() {

        return username;
    }
    
    public static int getUserID() {

        return id;
    }

    public static int getPermissonLevel() {
        return permissonLevel;
    }

    public String getName() {
        return name;
    }
    
    public static boolean isUserLocked() {
        return false;
    }

}
