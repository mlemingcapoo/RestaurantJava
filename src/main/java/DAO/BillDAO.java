

package DAO;

import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Bill;
import util.SQLThread;

/**
 *
 * @author capoo
 */
public class BillDAO extends SQL<Bill, String> {
//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";

    String DELETE_SQL = "DELETE FROM `Bill` WHERE order_ID = ?";

    String SELECT_ALL_SQL = "SELECT * FROM Bill";
    String SELECT_ALL_PENDING_SQL = "SELECT * from `Bill` WHERE `Bill`.isCompleted = 0;";
//    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";

    @Override
    public List<Bill> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<Bill> selectBySQL(String sql, Object... args) {
        List<Bill> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Bill newBill = new Bill();
                newBill.setBill_ID(rs.getInt(1));
                newBill.setUser_ID(rs.getInt(2));
                newBill.setAmount(rs.getFloat(3));
                newBill.setBillDate(rs.getString(4));
                newBill.setNote(rs.getString(5));
                newBill.setMa_KH(rs.getInt(6));
                newBill.setVCode(rs.getString(7));
                newBill.setBillCode(rs.getString(8));
                list.add(newBill);
            }
//            System.out.println("BillID at index 0: " + list.get(0).getBill_ID());;
        } catch (Exception e) {
//            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. Trying to reconnect...");
            new SQLThread().main(null);
//            throw new RuntimeException(e);
        }
        return list;
    }

   

    public List<Bill> selectAllPending() {
        return this.selectBySQL(SELECT_ALL_PENDING_SQL);
    }

    public void delete(int order_ID) {

        DBHelper.update(DELETE_SQL, order_ID);

    }

    


    public void addDishByID(int orderID, int quantity, int dishID) throws Exception {
//    for(int i = 0; i < quantity; i++) {
        DBHelper.executeProc("addDishByOrderID", orderID, quantity, dishID);
//    }
}

    public void removeDishByID(int orderID, int quantity, int dishID) throws Exception{
        DBHelper.executeProc("deleteDishesByOrderID", orderID, quantity, dishID);
    }

    public static void deleteAllDishes(int order_ID) throws Exception{
        DBHelper.executeProc("deleteAllDishesByOrderID",order_ID);
    }
}