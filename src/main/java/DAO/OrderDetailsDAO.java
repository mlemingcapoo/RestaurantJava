package DAO;

import helper.DBHelper;
import helper.DialogHelper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetails;
import model.orderedDishes;

/**
 *
 * @author capoo
 */
public class OrderDetailsDAO extends SQL<OrderDetails, String> {
//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";

    String DELETE_SQL = "DELETE FROM OrderDetails WHERE orderID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM OrderDetails";
//    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";

    @Override
    public List<OrderDetails> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<OrderDetails> selectBySQL(String sql, Object... args) {
        List<OrderDetails> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                OrderDetails newOrderDetails = new OrderDetails();
                newOrderDetails.setQuantity(rs.getInt(2));
                newOrderDetails.setOrder_ID(rs.getInt(3));
                newOrderDetails.setDish_ID(rs.getInt(4));
                list.add(newOrderDetails);
            }
            System.out.println("order details OrderID at index 0: " + list.get(0).getOrder_ID());;
        } catch (Exception e) {
            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại database!");
//            throw new RuntimeException(e);
        }
        return list;
    }

//        @Override
    public List<orderedDishes> getOrderedDish(int OrderID) throws Exception {

        List<orderedDishes> list = new ArrayList<>();

        ResultSet rs = DBHelper.executeProc("getOrderListByOrderID", OrderID);
        while (rs.next()) {
            orderedDishes orderList = new orderedDishes();
            orderList.setDish_ID(rs.getInt(1));
            orderList.setFoodName(rs.getString(2));
            orderList.setPrice(rs.getBigDecimal(3));
            orderList.setQuantity(rs.getInt(4));
            list.add(orderList);
        }
//        System.out.println("ordered food name at index 0: " + list.get(0).getFoodName());

        return list;
    }

//    public void addDishByID(int orderID, int quantity, int dishID) throws Exception{
//        DBHelper.executeProc("addDishByOrderID", orderID,quantity,dishID);
//    }
    public void addDishByID(int orderID, int quantity, int dishID, BigDecimal amount) throws Exception {
//    for(int i = 0; i < quantity; i++) {
        DBHelper.executeProc("addDishByOrderID", orderID, quantity, dishID, amount);
//    }
    }

    public void removeDishByID(int orderID, int quantity, int dishID) throws Exception {
        DBHelper.executeProc("deleteDishesByOrderID", orderID, quantity, dishID);
    }

    public static void deleteAllDishes(int order_ID) throws Exception {
        DBHelper.executeProc("deleteAllDishesByOrderID", order_ID);
    }

    public BigDecimal calculateTotalPrice(int order_choosen) throws Exception {
        BigDecimal totalPrice=BigDecimal.ZERO;
        try{
//        DBHelper.executeProc("calculateTotalPriceByOrderID",order_choosen);
        ResultSet query = DBHelper.query("SELECT calculateTotalPriceByOrderID(" + order_choosen + ")");
            totalPrice = BigDecimal.ZERO;
        while (query.next()) {
            totalPrice = BigDecimal.valueOf(query.getDouble(1));
            System.out.println("Total orders: " + totalPrice);
        }}catch(Exception e){
//            e.printStackTrace();
        }
        return totalPrice;
    }

}
