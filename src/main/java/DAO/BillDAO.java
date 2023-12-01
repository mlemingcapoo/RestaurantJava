

package DAO;

import helper.DBHelper;
import helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Bill;

/**
 *
 * @author capoo
 */
public class BillDAO extends SQL<Bill, String>{
//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM OrderDetails WHERE orderID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM OrderDetails";
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
                Bill newOrderDetails = new Bill();
                newOrderDetails.setBill_ID(rs.getInt(1));
                newOrderDetails.setUser_ID(rs.getInt(2));
                newOrderDetails.setAmount(rs.getFloat(3));
                newOrderDetails.setBillDate(rs.getString(4));
                newOrderDetails.setNote(rs.getString(4));
                newOrderDetails.setMa_KH(rs.getInt(4));
                newOrderDetails.setVCode(rs.getInt(4));
                newOrderDetails.setBillCode(rs.getString(4));
                list.add(newOrderDetails);
            }
//            System.out.println("order details OrderID at index 0: "+list.get(0).getAmount());;
        } catch (Exception e) {
            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại database!");
//            throw new RuntimeException(e);
        }
        return list;
    }
    
//        @Override

    
    public List<Bill> getOrderedDish(int OrderID) throws Exception {
        
        
        List<Bill> list = new ArrayList<>();

            ResultSet rs = DBHelper.executeProc("getOrderListByOrderID",OrderID);
            while (rs.next()) {
                Bill newOrderDetails = new Bill();
                newOrderDetails.setBill_ID(rs.getInt(1));
                newOrderDetails.setUser_ID(rs.getInt(2));
                newOrderDetails.setAmount(rs.getFloat(3));
                newOrderDetails.setBillDate(rs.getString(4));
                newOrderDetails.setNote(rs.getString(4));
                newOrderDetails.setMa_KH(rs.getInt(4));
                newOrderDetails.setVCode(rs.getInt(4));
                newOrderDetails.setBillCode(rs.getString(4));
                list.add(newOrderDetails);
            }
            
            
            return list;
    }

//    public void addDishByID(int orderID, int quantity, int dishID) throws Exception{
//        DBHelper.executeProc("addDishByOrderID", orderID,quantity,dishID);
//    }

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
