/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Voucher;
/**
 *
 * @author mynla
 */
public class VoucherDAO extends SQL<Voucher, String> {
    
    String SELECT_ALL_SQL = "SELECT * FROM Voucher";
    String INSERT_SQL = "CALL ThemVoucher(?,?,?)";
    String UPDATE_SQL="CALL SuaVoucher(?,?,?)";
    String SELECT_PERCENT="SELECT discountPercent FROM Voucher WHERE VCode like ?";

    @Override
    public List<Voucher> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<Voucher> selectBySQL(String sql, Object... args) {
        List<Voucher> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Voucher newVoucher = new Voucher();
                newVoucher.setVoucherID(rs.getInt(1));
                newVoucher.setDiscountPercent(rs.getFloat(2));
                newVoucher.setVCode(rs.getString(3));
                newVoucher.setExpireDate(rs.getString(4));
                list.add(newVoucher);
            }
            if (!list.isEmpty()) {
//                System.out.println("Voucher code at index 0: " + list.get(0).getVCode(sql));
            }
        } catch (Exception e) {
            System.out.println("Connection was lost.. ");
            // Handle the exception as needed
        }
        return list;
    }
    public void insert(Voucher voucher)throws Exception{
        DBHelper.executeProc("ThemVoucher", voucher.getDiscountPercent(),voucher.getVCode(),voucher.getExpireDate());
    }
   public void update(Voucher voucher) throws Exception {
    DBHelper.executeProc("SuaVoucher", voucher.getVoucherID(), voucher.getDiscountPercent(), voucher.getExpireDate());
}
   public void deleteById(Voucher voucher) throws Exception {
    DBHelper.executeProc("XoaVocher", voucher.getVoucherID());
}

    public void deleteById(int voucher) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Double getDiscountPercent(String voucher) {
        Double percent=0.0;
        try {
            ResultSet rs = DBHelper.query(SELECT_PERCENT, voucher);
            while(rs.next()){
                percent = rs.getDouble(1);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return percent;
    }

}
  
