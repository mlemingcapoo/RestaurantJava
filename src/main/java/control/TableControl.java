package control;

import DAO.OrderDAO;
import DAO.TableDAO;
import frame.TableJPanel;
import helper.DialogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.OrdersWithTable;
import model.Tables;

/**
 *
 * @author capoo
 */
public class TableControl {

    static TableJPanel panel;
    static List<OrdersWithTable> order = new ArrayList<>();
    static List<Tables> tables = new ArrayList<>();
    TableDAO dao = new TableDAO();
    OrderDAO orderDao = new OrderDAO();

    void init(TableJPanel panel) {
        TableControl.panel = panel;
        refresh();
//        fillTables();
    }

    public void refresh() {
        tables.clear();
        tables = dao.selectAll();
        fillTables();
        getFillOrders();
    }

    public void fillTables() {
        System.out.println("filling table tabe...");
        DefaultTableModel model = (DefaultTableModel) panel.tblListBan.getModel();
        model.setRowCount(0);
        for (Tables fd : tables) {
            int listOrder = 0;
            try {
                System.out.println("searching orderamount table tabe...");
                listOrder = dao.getOrderListByTableID(fd.getTable_ID());
            } catch (Exception ex) {
                System.out.println("what table? " + ex.getMessage());
            }
            Object[] row = new Object[]{fd.getTable_ID(), fd.getTableName(), listOrder};
            model.addRow(row);
            panel.cboTableName.addItem(fd.getTableName());
        }
    }

    public void changeTableName() {
        String TableName = panel.txtTableName.getText();
//        System.out.println("selected: " + selectedRow + " tbl name: " + TableName);
        int table_ID = -1;
        try {
            int selectedRow = panel.tblListBan.getSelectedRow();
            table_ID = tables.get(selectedRow).getTable_ID();
        } catch (ArrayIndexOutOfBoundsException e) {
            DialogHelper.showDialog(panel, "Chọn 1 bàn để sửa");
        }
        try {
            dao.SuaTenBan(table_ID, TableName);
        } catch (Exception ex) {
            Logger.getLogger(TableControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            refresh();

        }
    }

    public void fillTableName() {
//        System.out.println("TEXT FUCKING NOT SET");
        try {
            int selectedRow = panel.tblListBan.getSelectedRow();
            panel.txtTableName.setText(tables.get(selectedRow).getTableName());
        } catch (Exception e) {
        }
//        System.out.println("TEXT FUCKING SET");
    }

    public void deleteTable() {
            System.out.println("what33?");
        
    }

    private void getFillOrders() {
        order.clear();
        order = orderDao.getOrdersWithTableName();
        System.out.println("filling...");
        DefaultTableModel model = (DefaultTableModel) panel.tblOrderList.getModel();
        model.setRowCount(0);
        int count = 1;
        for (OrdersWithTable fd : order) {
            Object[] row = new Object[]{count, fd.getOrder_ID(), fd.getTableName(), fd.getNote()};
            model.addRow(row);
            count++;
            panel.cboIdOrder.addItem(String.valueOf(fd.getOrder_ID()));
        }
    }

    public void addTable() {
        try {
            dao.add(panel.txtTableName.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();
        panel.txtTableName.setText("");
    }

}
