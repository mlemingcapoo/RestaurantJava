package control;

import DAO.FoodDAO;
import DAO.OrderDAO;
import DAO.OrderDetailsDAO;
import DAO.SQL;
import GUI.mainUI;
import frame.OrderPanel;
import frame.ThanhToanJDialog;
import helper.DialogHelper;
import helper.LoadImageTask;
import helper.imgHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Food;
import model.Order;
import model.OrderDetails;
import model.orderedDishes;

/**
 *
 * @author capoo
 */
public class OrderControl {

    mainUI parentFrame;

    public OrderControl(mainUI aThis) {
        parentFrame = aThis;
    }

    public OrderControl() {

    }
    static List<Food> food = new ArrayList<>();
    static List<Order> order = new ArrayList<>();
    static List<OrderDetails> orderDetails = new ArrayList<>();
    static List<orderedDishes> ordered = new ArrayList<>();

    OrderDAO daoOrder = new OrderDAO();
    OrderDetailsDAO daoOrderDetails = new OrderDetailsDAO();

    static Scan_QR_Control scanqr = new Scan_QR_Control();
    private static OrderPanel panel;
    SQL dao = new FoodDAO();
    DefaultTableModel foodModel = new DefaultTableModel();
    int selectedOrder;

    private static int order_choosen;

    public void init(OrderPanel panel) {
        OrderControl.panel = panel;
//        panel.revalidate();

        try {
            //        panel.setVisible(false);

            refreshAll();

            System.out.println("refrshed all");
//        panel.
//        panel.tblDSMonAn.setModel(model);
//        fillPendingOrders();
            viewPendingOrderClicked(order.size() - 1);
            System.out.println("viewing laste record");
            System.out.println("order choosen in init: " + order_choosen);
//            panel.revalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.tblDSMonAn.setRowHeight(100);
        panel.tblDSOrderDangLam.setRowHeight(30);
//        panel.tblDSMonAn.getColumnModel().getColumn(3).setPreferredWidth(80);
//        panel.tblDSMonAn.getColumnModel().getColumn(3).setWidth(50);
    }

    public void refreshAll() {

        foodModel.setRowCount(0);
//        Thread dish = new Thread(() -> {
//            getDishes();
        try {
            fetchDishes(order_choosen);
        } catch (Exception e) {
        }
//        });
//        dish.start();
//        getPendingOrders();
        fillPendingOrders();
        getDishes();
        fillDishes();
        panel.spinnerAmount.setValue(1);
    }

    public static void setSelectedRow(JTable table, int row) {
        // Simulating a mouse click on the 'row' row
        int viewRow = table.convertRowIndexToView(row);
        table.setRowSelectionInterval(viewRow, viewRow);
    }

    @SuppressWarnings("unchecked")
    public void getDishes() {
        food.clear();
        food = dao.selectAll();
        System.out.println("Hện có " + food.size() + " món ăn");
        DefaultTableModel model = (DefaultTableModel) panel.tblDSMonAn.getModel();
        model.setRowCount(0);

        int rowIndex = 0;
        for (Food fd : food) {
            Object[] row = {fd.getName(), fd.getPrice(), fd.getType()};
            model.addRow(row);
            rowIndex++;
        }

        TableColumn column = panel.tblDSMonAn.getColumnModel().getColumn(3);
        column.setCellRenderer(new imgHelper());

        for (int i = 0; i < rowIndex; i++) {
            setImg(model, i, food.get(i).getImg());
        }

        panel.tblDSMonAn.updateUI();

    }

    public void payment() {
//        order_choosen;
//        order ;
        ThanhToanControl control2 = new ThanhToanControl(parentFrame);
        control2.setOrder(panel.txtSDTHoiVien.getText(), panel.txtMaVocher.getText(), order_choosen, panel.cboHinhThucThanhToan.getSelectedItem().toString());
        ThanhToanJDialog pay = new ThanhToanJDialog(parentFrame, true, control2);
    }

    public void createOrder() {
        newEmptyOrder();
    }

    private void getPendingOrders() {

    }

    public void calculate() {
        System.out.println("started cal");
        Double tienKhach = 0.0;
        try {
            String converted = panel.txtTienKhachDua.getText();
            if (converted.length() > 7) {
                converted = converted.substring(0, 7);
            }
            tienKhach = Double.valueOf(converted);
        } catch (NumberFormatException | NullPointerException e) {
            DialogHelper.alert(panel, "Nhập số tiền hợp lệ (VD: 24000)");
        }
        Double tienBill = 0.0;
        try {
            tienBill = Double.valueOf(panel.txtTienKhachMat.getText());
        } catch (NumberFormatException | NullPointerException e) {
            DialogHelper.alert(panel, "Hoá đơn không có phí");
            tienKhach = 0.0;
        }
        Double traKhach = 0.0;
        try {
            traKhach = tienKhach - tienBill;
        } catch (Exception e) {
        }
        panel.txtTraKhach.setText(traKhach.toString());

        System.out.println("da tinh tien thoi, tien thua");

    }

    public void addDish(int selectedRow, int count) {
        new Thread(() -> {
            try {
//                getDishes();
                System.out.println("addDish clicked: " + selectedRow);
//        Object data = table.getValueAt(row, 0);
                int orderID = order_choosen;
                System.out.println("order chosen: " + order_choosen);
                int quantity = count;
                int dishID = -1;
                try {
                    dishID = food.get(selectedRow).getDish_ID();
                } catch (IndexOutOfBoundsException e) {
                    DialogHelper.alert(parentFrame, "Phải chọn món trước khi bấm thêm!");
                }
                System.out.println("dishID chosen: " + dishID);
                try {
                    daoOrderDetails.addDishByID(orderID, quantity, dishID);
                } catch (Exception e) {
//                    DialogHelper.alert(panel, );
                    System.out.println(e.getMessage());
                } finally {
//                    refreshAll();
                    try {
                        fetchDishes(order_choosen);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void removeDishFromOrder(int selectedRow, int count) {
        int orderID = -1;
        int quantity = -1;
        int dishID = -1;
        try {
            fetchDishes(order_choosen);
        } catch (Exception e) {
        }
        try {
            System.out.println("removeDish clicked: " + selectedRow);
            orderID = order_choosen;
            System.out.println("order chosen: " + order_choosen);
            quantity = count;
            dishID = ordered.get(selectedRow).getDish_ID();
            System.out.println("dishID chosen: " + dishID);
        } catch (IndexOutOfBoundsException e) {
            DialogHelper.alert(parentFrame, "Chọn 1 loại món để xoá khỏi DS các món đã order");
        }
        try {
            daoOrderDetails.removeDishByID(orderID, quantity, dishID);
        } catch (Exception e) {
            DialogHelper.alert(panel, e.getMessage());
        } finally {
//            refreshAll();
//            getPendingOrders();
            try {
                fetchDishes(order_choosen);
            } catch (Exception e) {
            }
        }

    }

    public void dishClicked(int selectedRow) {
//        selectedDish = selectedRow;
        System.out.println("dish clicked: " + selectedRow);
        //handle -1 too pls
//        refreshAll();
    }

    public void orderClicked(int selectedRow) {
        System.out.println("Order clicked: " + selectedRow);
//        refreshAll();
    }

    public void changeDishAmount(JSpinner value) {
        System.out.println("comboValue: " + value.getValue());
        if (Integer.parseInt(value.getValue().toString()) < 1) {
            value.setValue(1);
        }
        if (Integer.parseInt(value.getValue().toString()) > 100) {
            value.setValue(100);
//            DialogHelper.alert(parentFrame, "Số lượng tối đa là 100!");
        }
    }

    private void newEmptyOrder() {
        try {
            order.clear();
            order = daoOrder.newEmpty(); // order da bao gom all order not completed
            fillPendingOrders();
            int orderID = order.get(0).getOrder_ID();
            fetchDishes(orderID);
            viewPendingOrderClicked(order.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillPendingOrders() {
        System.out.println("Hện có " + order.size() + " đơn chưa hoàn tất, hoặc mới tạo");

        DefaultTableModel model = (DefaultTableModel) panel.tblDSOrderDangLam.getModel();
        order.clear();
        order = daoOrder.selectAllPending();
        model.setRowCount(0);
        int count = 1;
        for (Order od : order) {
            Object[] row = new Object[]{count, od.getOrder_ID(), od.getNote()};
            model.addRow(row);
            count++;
        }
        calculateTotalPrice(order_choosen);
    }

//    private void fillDishedFromOrder(){
//        System.out.println("Hện có " + ordered.size() + " đơn chưa hoàn tất, hoặc mới tạo");
//        DefaultTableModel model = (DefaultTableModel) panel.tblDSOrderDangLam.getModel();
//        model.setRowCount(0);
//        int count =1;
//        for (orderedDishes od : ordered) {
//            Object[] row = new Object[]{count,od.get};
//            model.addRow(row);
//            count++;
//        }
//    }
    private void fetchDishes(int orderID) throws SQLException, Exception {

        try {
            ordered.clear();
            ordered = daoOrderDetails.getOrderedDish(orderID);
            System.out.println("Hện có " + ordered.size() + " số order con trong orderID: " + orderID);
        } catch (Exception e) {
            System.out.println("order này trống.");
        }
//        model.setRowCount(0);
        fillDishes();
    }

    private void fillDishes() {
        DefaultTableModel model = (DefaultTableModel) panel.tblDSDangOrder.getModel();
        model.setRowCount(0);
        int count = 1;
        for (orderedDishes od : ordered) {
            Object[] row = new Object[]{od.getFoodName(), od.getQuantity(), od.getPrice()};
            model.addRow(row);
            count++;
        }
    }

    public void deletePendingOrderClicked(int selectedRow) {

        order.clear();
        order = daoOrder.selectAllPending();
        System.out.println("order list size: " + order.size());
        int order_ID = -1;
        try {
            if (order_ID<0) throw new IndexOutOfBoundsException();
            order_ID = order.get(selectedRow).getOrder_ID();
            if (DialogHelper.confirm(panel, "Xác nhận xoá đơn hàng thứ " + (selectedRow + 1) + "?")) {
                try {
                    OrderDetailsDAO.deleteAllDishes(order_ID);
                    daoOrder.delete(order_ID);
                    fillPendingOrders();
                    order_choosen = order.get(order.size() - 1).getOrder_ID();
                    fetchDishes(order_choosen);
                } catch (Exception e) {
                    System.out.println("line 355: " + e.getCause() + e.getMessage());
//            DialogHelper.alert(panel, e.getMessage());

                }
            }
        } catch (Exception e) {
            DialogHelper.alert(panel, "Chọn 1 Đơn hàng muốn Xoá/Huỷ");
        }

    }

    public void viewPendingOrderClicked(int selectedRow) {
        selectedOrder = selectedRow;
        try {
            order.clear();
            order = daoOrder.selectAllPending();
            System.out.println("order list size: " + order.size());
            int order_ID = order.get(selectedRow).getOrder_ID();
            System.out.println("orderID in viewpending order: " + order_ID);
            order_choosen = order_ID;
            fetchDishes(order_ID);
            System.out.println("viewPendingOrder got choosen ID: " + order_choosen);
        } catch (Exception ex) {
            DialogHelper.alert(panel, "Không tìm thấy order nào, vui lòng kiểm tra kết nối!");
        }
    }

    public void scanQR(OrderPanel aThis) {
        System.out.println("Starting QR Scan");
        scanqr.scan(aThis);
    }

    public void lockDish(int selectedRow) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void unlockDish(int selectedRow) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNote(int selectedRow, String text) {
        try {
            daoOrder.setNote(selectedRow, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search(String text, String toString) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void calculateTotalPrice(int order_choosen) {
//        Double price = daoOrderDetails.calculateTotalPrice(order_choosen);
    }

    private void setImg(DefaultTableModel model, int rowIndex, String imgLink) {

        Runnable loadTask = new LoadImageTask(model, rowIndex, imgLink);

        new Thread(loadTask).start();

    }

    

}
