/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import helper.DBHelper;
import java.sql.ResultSet;

/**
 *
 * @author catty
 */
public class AuthenticateDAO {

    private int permissonLevel;

    public boolean checkLogin(String username, String password) {
        ResultSet value = null;
        boolean result = false;
        try {
            value = DBHelper.query("SELECT TOP 1 FROM User WHERE username = ?", username);
            if (value.next()) {
                System.out.println(value.getString(2));
                if (value.getString(3).equals(password)) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String getUsername() {

        return null;
    }

    public int getPermissonLevel() {
        return permissonLevel;
    }

    public void setPermissonLevel(int permissonLevel) {
        this.permissonLevel = permissonLevel;
    }

}
