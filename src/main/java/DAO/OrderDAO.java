package DAO;

import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.OrdersWithTable;
import util.SQLThread;

/**
 *
 * @author capoo
 */
public class OrderDAO extends SQL<Order, String> {
//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";

    String DELETE_SQL = "DELETE FROM `Order` WHERE order_ID = ?";
    String UPDATE_ORDER = "UPDATE `Order` SET `Order`.note = ? WHERE `Order`.order_ID = ?;";

    String SELECT_ALL_SQL = "SELECT * FROM Order";
    String SELECT_ALL_PENDING_SQL = "SELECT * FROM `Order` WHERE `Order`.isCompleted = 0;";
//    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";

    @Override
    public List<Order> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<Order> selectBySQL(String sql, Object... args) {
        List<Order> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Order newOrder = new Order();
                newOrder.setOrder_ID(rs.getInt(1));
                newOrder.setBill_ID(rs.getInt(2));
                newOrder.setNote(rs.getString(3));
                newOrder.setIsCompleted(rs.getBoolean(4));
                newOrder.setDateCreated(rs.getString(5));
                newOrder.setTable_ID(rs.getInt(6));
                list.add(newOrder);
            }
//            System.out.println("OrderID at index 0: " + list.get(0).getOrder_ID());;
        } catch (Exception e) {
//            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. Trying to reconnect...");
            new SQLThread().main(null);
//            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Order> newEmpty() throws Exception {

        List<Order> list = new ArrayList<>();

        ResultSet rs = DBHelper.executeProc("addNewEmptyOrder");
        while (rs.next()) {
            Order newOrder = new Order();
            newOrder.setOrder_ID(rs.getInt(1));
            newOrder.setBill_ID(rs.getInt(2));
            newOrder.setNote(rs.getString(3));
            newOrder.setIsCompleted(rs.getBoolean(4));
            newOrder.setDateCreated(rs.getString(5));
            newOrder.setTable_ID(rs.getInt(6));
            list.add(newOrder);
        }
        System.out.println("orderID at index 0: " + list.get(0).getOrder_ID());;

        return list;
    }

    public List<Order> selectAllPending() {
        return this.selectBySQL(SELECT_ALL_PENDING_SQL);
    }

    public void delete(int order_ID) {

        DBHelper.update(DELETE_SQL, order_ID);

    }

    public List<OrdersWithTable> getOrdersWithTableName() {
        List<OrdersWithTable> list = null;
        try {
            ResultSet rs = DBHelper.executeProc("getOrdersWithTableName");
            list = new ArrayList<>();
            while (rs.next()) {
                OrdersWithTable orders = new OrdersWithTable();
                orders.setOrder_ID(rs.getInt(1));
                orders.setTableName(rs.getString(2));
                orders.setNote(rs.getString(3));
                list.add(orders);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void setNote(int order_ID, String text) {
        DBHelper.update(UPDATE_ORDER, text,order_ID);
    }

    public void setStatus(Integer orderID, int status) {
        DBHelper.update("UPDATE `Order` SET isCompleted = ? WHERE order_ID = ?", status,orderID);
    }

    public boolean isBillCreated(int order_ID) {
//        boolean result = false;
                int bill_ID=0;
        List<Order> list = selectBySQL("SELECT * FROM `Order` WHERE `Order`.order_ID = ?",order_ID);
        for (Order order : list) {
            if(order.getOrder_ID()==order_ID){
                try {
                bill_ID = order.getBill_ID();
                    System.out.println("yeah no shit bill"+bill_ID);
                } catch (Exception e) {
                    bill_ID=0;
                }
            }
        }
        return bill_ID==0;
    }

}
