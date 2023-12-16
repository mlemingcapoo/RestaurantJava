package control;

import DAO.FoodDAO;
import DAO.OrderDAO;
import DAO.OrderDetailsDAO;
import DAO.TableDAO;
import GUI.mainUI;
import static control.TableControl.tables;
import frame.OrderPanel;
import frame.ThanhToanJDialog;
import helper.DialogHelper;
import helper.FoodHelper;
import helper.LoadImageTask;
import helper.LoadingHelper;
import helper.NumberHelper;
import helper.imgHelper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Food;
import model.FoodType;
import model.Order;
import model.OrdersWithTable;
import model.Tables;
import model.orderedDishes;
import util.SQLThread;

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
    private static OrderPanel panel;
    
    static List<Food> food = new ArrayList<>();
    static List<Order> order = new ArrayList<>();
    static List<FoodType> types = new ArrayList<>();
//    static List<OrderDetails> orderDetails = new ArrayList<>();
    static List<OrdersWithTable> orderWithTable = new ArrayList<>();
    static List<orderedDishes> ordered = new ArrayList<>();

    OrderDetailsDAO daoOrderDetails = new OrderDetailsDAO();
    TableDAO daoTables = new TableDAO();
    OrderDAO daoOrder = new OrderDAO();
    FoodDAO dao = new FoodDAO();
    FoodDAO foodDao = new FoodDAO();
    

    static Scan_QR_Control scanqr = new Scan_QR_Control();
    DefaultTableModel foodModel = new DefaultTableModel();
    static int selectedOrder;
    static Double totalAmount;

    private static int order_choosen;

    public void init(OrderPanel panel) {
        LoadingHelper loading = new LoadingHelper("Updating");
        loading.setLoadingStatus(true);
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
            new Thread(() -> {
                try {

                    fillTables();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.tblDSMonAn.setRowHeight(100);
        panel.tblDSOrderDangLam.setRowHeight(30);
//        panel.tblDSMonAn.getColumnModel().getColumn(3).setPreferredWidth(80);
//        panel.tblDSMonAn.getColumnModel().getColumn(3).setWidth(50);

//        try {
//            JDBCManager.closeConnection();
//            new SQLThread().main(null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void refreshAll() {
        fetchTables();
        foodModel.setRowCount(0);
        Thread dish = new Thread(() -> {
//            getDishes();
            try {
                fetchDishes(order_choosen);
            } catch (Exception e) {
            }
        });
        dish.start();
//        getPendingOrders();
        fillPendingOrders();
        getDishes();
        fillDishes();
        panel.spinnerAmount.setValue(1);
        dish.interrupt();
    }

    public static void setSelectedRow(JTable table, int row) {
        // Simulating a mouse click on the 'row' row
        int viewRow = table.convertRowIndexToView(row);
        table.setRowSelectionInterval(viewRow, viewRow);
    }

    @SuppressWarnings("unchecked")
    public void getDishes() {

        food.clear();
        food = dao.selectAllUnlocked();
        types.clear();
        types = foodDao.getTypeNameList();
        System.out.println("Hện có " + food.size() + " món ăn");
        DefaultTableModel model = (DefaultTableModel) panel.tblDSMonAn.getModel();
        model.setRowCount(0);

        int rowIndex = 0;
        for (Food fd : food) {

            Object[] row = {fd.getName(), NumberHelper.formatNumber(fd.getPrice().toString()), FoodHelper.getTypeName(fd.getType(), types)};
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
        if(order_choosen<0){
            boolean confirm = DialogHelper.confirm(panel, "Chưa tạo đơn nào, bạn có muốn xác nhận tạo đơn mới?");
            if(confirm){
                newEmptyOrder();
            }
        } else {
            
        ThanhToanControl control2 = new ThanhToanControl(parentFrame);
        control2.setOrder(panel.txtSDTHoiVien.getText(), panel.txtMaVocher.getText(), order_choosen, panel.cboHinhThucThanhToan.getSelectedItem().toString(), panel.lblTotalPayment.getText(), panel.cboTableName.getSelectedItem().toString(),panel.txtOrderNote.getText());
        ThanhToanJDialog pay = new ThanhToanJDialog(parentFrame, true, control2);
        fillPendingOrders();
        }
    }

    public void createOrder() {
        newEmptyOrder();
    }

    private void getPendingOrders() {

    }

    public void calculate() {
        System.out.println("started cal");
        BigDecimal tienKhach = BigDecimal.ZERO;
        try {
            String converted = panel.txtTienKhachDua.getText();
//            if (converted.length() > 7) {
//                converted = converted.substring(0, 7);
//            }
            tienKhach = BigDecimal.valueOf(Long.parseLong(converted));
        } catch (NumberFormatException | NullPointerException e) {
            DialogHelper.alert(panel, "Nhập số tiền hợp lệ (VD: 24000)");
        }
        BigDecimal tienBill = BigDecimal.ZERO;
        try {
            tienBill = BigDecimal.valueOf(Double.parseDouble(panel.txtTienKhachMat.getText()));
        } catch (NumberFormatException | NullPointerException e) {
            DialogHelper.alert(panel, "Hoá đơn không có phí");
            tienKhach = BigDecimal.ZERO;
        }
        BigDecimal traKhach = BigDecimal.ZERO;
        try {
            traKhach = tienKhach.subtract(tienBill);
            if (traKhach.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException("Number cannot be negative");
            }
            panel.txtTraKhach.setText(
                    NumberHelper.formatNumber(
                            traKhach.toString()
                    )
                    + " VND"
            );
        } catch (Exception e) {
//            DialogHelper.alert(panel, "Khách chưa đưa đủ tiền!");
            String tienKhachMat = traKhach.toString().substring(1, traKhach.toString().length() - 0);
            panel.txtTraKhach.setText("Thiếu: " + NumberHelper.formatNumber(
                    tienKhachMat) + " VND"
            );
        }

        System.out.println("da tinh tien thoi, tien thua");

    }

    public void addDish(int selectedRow, int count) {
        if(order_choosen<0){
            boolean confirm = DialogHelper.confirm(panel, "Chưa tạo đơn nào, bạn có muốn xác nhận tạo đơn mới?");
            if(confirm){
                newEmptyOrder();
            }
        } else {
            LoadingHelper loading = new LoadingHelper("Adding");
        loading.setLoadingStatus(true);
        new Thread(() -> {
            try {
//                getDishes();
                System.out.println("addDish clicked: " + selectedRow);
//        Object data = table.getValueAt(row, 0);
                int orderID = order_choosen;
                System.out.println("order chosen: " + order_choosen);
                int quantity = count;
                int dishID = -1;
                BigDecimal amount = BigDecimal.ZERO;
                try {
                    dishID = food.get(selectedRow).getDish_ID();
                    amount = food.get(selectedRow).getPrice();
                } catch (IndexOutOfBoundsException e) {
                    DialogHelper.alert(parentFrame, "Phải chọn món trước khi bấm thêm!");
                }
                System.out.println("dishID chosen: " + dishID);
                try {
                    daoOrderDetails.addDishByID(orderID, quantity, dishID,amount);
                } catch (Exception e) {
//                    DialogHelper.alert(panel, );
                    System.out.println(e.getMessage());
                    LoadingHelper loading1 = new LoadingHelper("Add Failed");
                    loading.setLoadingStatus(true);
                    new SQLThread().main(null);
                } finally {
//                    refreshAll();
                    try {
                        fetchDishes(order_choosen);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            fillPendingOrders();
            new Thread(() -> {
                try {
                    setTotalPrice(order_choosen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            loading.setLoadingStatus(false);
        }).start();
        }

    }

    public void removeDishFromOrder(int selectedRow, int count) {
        if(order_choosen<0){
            DialogHelper.alert(panel, "Hiện không có đơn nào để xoá món!");
        } else {
            LoadingHelper loading = new LoadingHelper("Removing");
        loading.setLoadingStatus(true);
        new Thread(() -> {
            try {
                int orderID = -1;
                int quantity = -1;
                int dishID = -1;
                try {
                    fetchDishes(order_choosen);
                } catch (Exception e) {
                    throw e;
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
                    throw e;
                } finally {
//            refreshAll();
//            getPendingOrders();
                    try {
                        fetchDishes(order_choosen);
                        loading.setLoadingStatus(false);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            } catch (Exception e) {
                LoadingHelper loading1 = new LoadingHelper("Mất kết nối");
                loading.setLoadingStatus(true);
                new SQLThread().main(null);
                e.printStackTrace();
            }
            fillPendingOrders();
            new Thread(() -> {
                try {
                    setTotalPrice(order_choosen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            loading.setLoadingStatus(false);
        }).start();
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
        if(order.size()<=0){order_choosen=-1;}

        DefaultTableModel model = (DefaultTableModel) panel.tblDSOrderDangLam.getModel();
        order.clear();
//        new Thread(() -> {
        try {
            order = daoOrder.selectAllPending();
            model.setRowCount(0);
            int count = 1;
            for (Order od : order) {

                Object[] row = new Object[]{count, od.getOrder_ID(), od.getNote(), NumberHelper.formatNumber(calculateTotalPrice(od.getOrder_ID()).toString()), getTableName(od.getOrder_ID()), getStatus(od.getBill_ID()), getDate(od.getDateCreated())};
                model.addRow(row);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            panel.tblDSOrderDangLam.repaint();
            panel.tblDSOrderDangLam.revalidate();
        }
//        }).start();

    }

    private void fetchDishes(int orderID) throws SQLException, Exception {
        LoadingHelper loading = new LoadingHelper("Đang tải");
        loading.setLoadingStatus(true);
        new Thread(() -> {
            try {
                try {
                    ordered.clear();
                    ordered = daoOrderDetails.getOrderedDish(orderID);
                    System.out.println("Hện có " + ordered.size() + " số order con trong orderID: " + orderID);

                } catch (Exception e) {
                    System.out.println("order này trống.");
                    
//                    fillDishes();
                } finally {
                    fillDishes();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        model.setRowCount(0);
//        fillOrderForm(orderID);
    }

    private void fillDishes() {
        DefaultTableModel model = (DefaultTableModel) panel.tblDSDangOrder.getModel();
        model.setRowCount(0);
        int count = 1;
        for (orderedDishes od : ordered) {
            
            Object[] row = new Object[]{od.getFoodName(), od.getQuantity(), NumberHelper.formatNumber(od.getPrice().toString())};
            model.addRow(row);
            count++;
        }

    }

    public void deletePendingOrderClicked(int selectedRow) {
        if(order_choosen<0){
            DialogHelper.alert(panel, "Không có đơn nào để xoá");
        } else{
            
        int order_ID = -1;
        order_ID = order.get(selectedRow).getOrder_ID();
        boolean isBillCreated=false;
        isBillCreated=daoOrder.isBillCreated(order_ID);
        if(isBillCreated){
        try {
            order.clear();
        order = daoOrder.selectAllPending();
        System.out.println("order list size: " + order.size());
            
            if (order_ID < 0) {
                
                throw new IndexOutOfBoundsException();
            }
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
            new SQLThread().main(null);
        }
        } else{
            DialogHelper.alert(panel, "Hoá đơn chờ đã được tạo, không thể thao tác thêm.");
        }
        }

    }

    public void viewPendingOrderClicked(int selectedRow) {
        LoadingHelper loading = new LoadingHelper("Fetching...");
        try {
            new Thread(() -> {
                selectedOrder = selectedRow;
                order.clear();
                order = daoOrder.selectAllPending();
                
                System.out.println("order list size: " + order.size());
                int order_ID = -1;
                try {
                    order_ID = order.get(selectedRow).getOrder_ID();
                } catch (Exception e) {
                }
                System.out.println("orderID in viewpending order: " + order_ID);
                order_choosen = order_ID;
                try {
                    fetchDishes(order_ID);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                fillOrderForm(selectedRow);
                System.out.println("viewPendingOrder got choosen ID: " + order_choosen);
                final int orderid = order_ID;
                new Thread(() -> {
                    try {
                        setTotalPrice(orderid);
                        setCboTableName(order_choosen);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
            DialogHelper.alert(panel, "Không tìm thấy order nào, vui lòng kiểm tra kết nối!");
        }
    }
    
    private void setCboTableName(int order_choosen) {
        
        if(!getTableName(order_choosen).equals("")){
            panel.cboTableName.setSelectedItem(getTableName(order_choosen));
        }else{
            panel.cboTableName.setSelectedIndex(0);
        }
        
    }

    public void setTotalPrice(int orderid) {
        String price = calculateTotalPrice(orderid).toString();
        panel.lblTotalPayment.setText(price);
        panel.txtTienKhachMat.setText(price);
    }

    public void scanQR(OrderPanel aThis) {
        System.out.println("Starting QR Scan");
        scanqr.scan(aThis);
    }

    public void lockDish(int selectedRow) {
        dao.setStatus(food.get(selectedRow).getDish_ID(),1);
    }

    public void unlockDish(int selectedRow) {
        dao.setStatus(food.get(selectedRow).getDish_ID(),0);
        
    }

    public void setNote(int selectedRow, String text) {
        LoadingHelper load = new LoadingHelper("Saving...");
        int order_ID = order_choosen;
        try {

            daoOrder.setNote(order_ID, text);
            DialogHelper.alert(panel, "Đã lưu note!");
            fillDishes();
            fillPendingOrders();
            load.setLoadingStatus(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void search(String tuKhoa, String foodTypeName) {
        new Thread(() -> {
            try {
                int type1 = 0;
                for (FoodType food1 : types) {
                    if (foodTypeName.equals(food1.getType())) {
                        type1 = food1.getType_id();
                    }
                }

                try {
                    int type = 0;
                    if (foodTypeName.equalsIgnoreCase("Tất cả")) {
                        type = 0;
                    } else {
                        type = type1;
                    }
                    List<Food> fd = dao.searchByNameAndType("%" + tuKhoa + "%", type);
                    System.out.println("Tu Khoa search: " + tuKhoa);
                    System.out.println("Loai search: " + type1);
                    DefaultTableModel model = (DefaultTableModel) panel.tblDSMonAn.getModel();
                    model.setRowCount(0);
                    int rowIndex=0;
                    for (Food fd1 : fd) {
                        Object[] row = new Object[]{fd1.getName(), fd1.getPrice(), FoodHelper.getTypeName(fd1.getType(), types), fd1.getImg(), getTrangThai(fd1.isIsLocked())};
                        model.addRow(row);
                        rowIndex++;
                    }
                    TableColumn column = panel.tblDSMonAn.getColumnModel().getColumn(3);
                    column.setCellRenderer(new imgHelper());

                    for (int i = 0; i < rowIndex; i++) {
                        setImg(model, i, food.get(i).getImg());
                    }

                    panel.tblDSMonAn.updateUI();
                } catch (Exception ex) {
                    Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
    
    public String getTrangThai(boolean status) {
        return status ? "Khoá" : "Đang mở";
    }

//    private BigDecimal calculateTotalPrice(int order_choosen) {
//        LoadingHelper loading = new LoadingHelper("Updating");
//        BigDecimal price = BigDecimal.ZERO;
//        try {
//            price = daoOrderDetails.calculateTotalPrice(order_choosen);
//            loading.setLoadingStatus(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return price;
//    }
    private Integer calculateTotalPrice(int order_choosen) {

        LoadingHelper loading = new LoadingHelper("Updating");
        BigDecimal price = BigDecimal.ZERO;

        try {
            price = daoOrderDetails.calculateTotalPrice(order_choosen);

            // Set scale to avoid ".0"
            price = price.setScale(2, RoundingMode.HALF_UP);

            loading.setLoadingStatus(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return price.intValue();

    }

    public void fillTables() {
        panel.cboTableName.removeAllItems();
        panel.cboTableName.addItem("Khách mang về");
        System.out.println("filling table tabe...");
//        DefaultTableModel model = (DefaultTableModel) panel.tblListBan.getModel();
//        model.setRowCount(0);
        tables.clear();
        tables = daoTables.selectAll();
        for (Tables fd : tables) {
//            int listOrder = 0;
            try {
                System.out.println("searching orderamount table tabe...");
//                listOrder = dao.getOrderListByTableID(fd.getTable_ID());
            } catch (Exception ex) {
                System.out.println("what table? " + ex.getMessage());
            }
//            Object[] row = new Object[]{fd.getTable_ID(), fd.getTableName(), listOrder};
//            model.addRow(row);
            panel.cboTableName.addItem(fd.getTableName());
        }
    }

    private void setImg(DefaultTableModel model, int rowIndex, String imgLink) {

        Runnable loadTask = new LoadImageTask(model, rowIndex, imgLink);

        new Thread(loadTask).start();

    }

    private void fillOrderForm(int selected) {
        try {
            panel.txtOrderNote.setText(order.get(selected).getNote());

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private String getStatus(int billID) {
        boolean isCompleted = false;
        try {
            if (billID!=0){
//                DialogHelper.alert(panel, String.valueOf(billID));
            isCompleted = true;
        }
        } catch (Exception e) {
        }
        return isCompleted ? "Chưa Thanh Toán" : "Chưa Xong";
    }

    private void fetchTables() {
        orderWithTable.clear();
        orderWithTable = daoOrder.getOrdersWithTableName();
//        DialogHelper.alert(panel, "3"+orderWithTable.get(0).getTableName());
    }

    private String getTableName(int order_ID) {
        String tableName = "";
//        DialogHelper.alert(panel, "1"+tableName+order_ID);
        for (OrdersWithTable od : orderWithTable) {
            if (od.getOrder_ID() == order_ID) {
                tableName = od.getTableName();
//        DialogHelper.alert(panel, "2"+tableName+od.getOrder_ID());
            }
        }
        return tableName;
    }

    private String getDate(String dateCreated) {
        System.out.println("date: " + dateCreated);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String formattedDate = "?";
        try {
            Date date = inputFormat.parse(dateCreated);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public void setTable() {
        try {
            int cboIdOrder = order_choosen;
            Integer TableIndex = panel.cboTableName.getSelectedIndex()-1;
//            DialogHelper.alert(panel, TableIndex.toString()+cboIdOrder);
            try {
                int TableID = tables.get(TableIndex).getTable_ID();
                daoTables.setTableByOrderID(cboIdOrder,TableID);
            } catch (Exception e) {
                daoTables.setNullTableByOrderID(cboIdOrder);
            }
        } catch (Exception ex) {
            Logger.getLogger(TableControl.class.getName()).log(Level.SEVERE, null, ex);

        } finally{
            fetchTables();
            fillPendingOrders();
        }
    }

    public void datBan() {
        
    }

}
