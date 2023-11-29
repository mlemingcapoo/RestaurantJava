/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mynla
 */
public class DoanhThuDAO {

    public int getTongKhachHang() {
        try {
            DBHelper.executeProc("TongKhachHang");
            return 0;
        } catch (Exception ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
