package DAO;

import helper.DBHelper;
import helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Tables;
import util.SQLThread;

/**
 *
 * @author capoo
 */
public class TableDAO extends SQL<Tables, String> {
//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";

    String DELETE_SQL = "DELETE FROM `Tables` WHERE table_ID = ?";

    String SELECT_ALL_SQL = "SELECT * FROM `Tables`";
    String SELECT_ALL_PENDING_SQL = "SELECT * from `Tables` WHERE `Table`.isCompleted = 0;";
//    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";

    @Override
    public List<Tables> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<Tables> selectBySQL(String sql, Object... args) {
        List<Tables> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Tables newTable = new Tables();
                newTable.setTable_ID(rs.getInt(1));
//                newTable.setOrder_ID(rs.getInt(2));
                newTable.setTableName(rs.getString(2));
                list.add(newTable);
            }
            System.out.println("TableID at index 0: " + list.get(0).getTable_ID());;
        } catch (Exception e) {
            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. Trying to reconnect...");
            new SQLThread().main(null);
//            throw new RuntimeException(e);
e.printStackTrace();
        }
        return list;
    }

    public List<Tables> newEmpty() throws Exception {

        List<Tables> list = new ArrayList<>();

        ResultSet rs = DBHelper.executeProc("addNewEmptyTable");
        while (rs.next()) {
            Tables newTable = new Tables();
            newTable.setTable_ID(rs.getInt(1));
//            newTable.setOrder_ID(rs.getInt(2));
            newTable.setTableName(rs.getString(2));
            list.add(newTable);
        }
//        System.out.println("TableID at index 0: " + list.get(0).getTable_ID());;

        return list;
    }

    public List<Tables> selectAllPending() {
        return this.selectBySQL(SELECT_ALL_PENDING_SQL);
    }

    public void delete(int Table_ID) throws Exception{

        DBHelper.executeProc("deleteTableByID", Table_ID);

    }

    public int getOrderListByTableID(int table_ID) throws ClassNotFoundException, Exception {
        ResultSet query = DBHelper.query("SELECT getTablesWithSumOrders(" + table_ID + ")");
        int totalOrders = 0;
        while (query.next()) {
            totalOrders = query.getInt(1);
            System.out.println("Total orders: " + totalOrders);
        }
        return totalOrders;
    }

    public void SuaTenBan(int table_ID, String TableName) throws Exception {
        DBHelper.executeProc("SuaTenBan", table_ID,TableName);
    }

    public void add(String text) throws Exception {
        DBHelper.executeProc("insertTableWithName", text);
    }

    public void setTableByOrderID(int cboIdOrder, int TableID) throws Exception {
        DBHelper.executeProc("setTableByOrderID", cboIdOrder,TableID);
    }

    public void setNullTableByOrderID(int orderID) throws Exception{
        DBHelper.executeProc("setNullTableByOrderID", orderID);
    }

}
