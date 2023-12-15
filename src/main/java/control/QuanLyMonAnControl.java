package control;

import DAO.FoodDAO;
import static control.OrderControl.types;
import frame.QuanLyMonAnJPanel;
import helper.DialogHelper;
import helper.FoodHelper;
import helper.LoadImageTask;
import helper.imgHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Food;
import model.FoodType;

/**
 *
 * @author capoo
 */
public class QuanLyMonAnControl {

    private static QuanLyMonAnJPanel panel;
    static List<Food> food = new ArrayList<>();
    DefaultTableModel foodModel = new DefaultTableModel();
    FoodDAO dao = new FoodDAO();

    public void init(QuanLyMonAnJPanel panel) {
        this.panel = panel;
        refresh();
        panel.tblDanhSachMonAn.setRowHeight(100);
        panel.cboChonLoaiMon.setSelectedItem("Tất cả");
    }

    public void refresh() {
        getMon();
        types.clear();
        types = dao.getTypeNameList();
        fillTypeList(types);
        fillDishes();
    }

    public void fillDishes() {
        DefaultTableModel model = (DefaultTableModel) panel.tblDanhSachMonAn.getModel();
        model.setRowCount(0);
        int rowIndex = 0;
        for (Food fd : food) {
            Object[] row = new Object[]{fd.getName(), fd.getPrice(), FoodHelper.getTypeName(fd.getType(), types), fd.getImg(), getTrangThai(fd.isIsLocked())};
            model.addRow(row);
            rowIndex++;
        }

        TableColumn column = panel.tblDanhSachMonAn.getColumnModel().getColumn(3);
        column.setCellRenderer(new imgHelper());

        for (int i = 0; i < rowIndex; i++) {
            setImg(model, i, food.get(i).getImg());
        }

        panel.tblDanhSachMonAn.updateUI();
    }

    private void setImg(DefaultTableModel model, int rowIndex, String imgLink) {

        Runnable loadTask = new LoadImageTask(model, rowIndex, imgLink);

        new Thread(loadTask).start();

    }

    public void getMon() {
        food.clear();
        try {
            food = dao.selectAll();
            System.out.println("số món có trong menu:" + food.size());
        } catch (Exception e) {
        }
    }

    public void themMon() {
        try {
            // Retrieve information from UI components
            String tenMon = panel.txtTenMon.getText().trim();
//            if (tenMon.isEmpty()) {
//                helper.DialogHelper.alert(panel, "Vui lòng nhập tên món!");
//                return;
//            }

            BigDecimal giaMon;
            try {
                giaMon = BigDecimal.valueOf(Double.parseDouble(panel.txtGiaMon.getText()));
            } catch (NumberFormatException ex) {
                helper.DialogHelper.alert(panel, "Giá món phải là một số!");
                return;
            }

            int IDloaiMon = types.get(panel.cboLoaiMon.getSelectedIndex()).getType_id();
            int trangThaiIndex = panel.cboTrangThai.getSelectedIndex();
            boolean trangThai = (trangThaiIndex == 1);

            String imgPath = null;

            // Check if the image path is empty or null, set a default image link
            if (imgPath == null || imgPath.isEmpty()) {
                imgPath = "https://toppng.com/free-image/clipart-free-seaweed-clipart-draw-food-placeholder-PNG-free-PNG-Images_183132"; // Replace with default image link
            }

            // Check if the food item with the same name already exists
            boolean isDuplicateName = food.stream().anyMatch(f -> f.getName().equalsIgnoreCase(tenMon));
            if (isDuplicateName) {
                helper.DialogHelper.alert(panel, "Tên món đã tồn tại trong danh sách!");
                return;
            }

            // Create a new Food object
            Food newFood = new Food();
            newFood.setName(tenMon);
            newFood.setPrice(giaMon);
            newFood.setType(IDloaiMon);
            // Map the selected item from the combo box to the appropriate type
//            switch (loaiMon.toLowerCase()) {
//                case "food":
//                    newFood.setType("Food");
//                    break;
//                case "drink":
//                    newFood.setType("Drink");
//                    break;
//                case "dessert":
//                    newFood.setType("Dessert");
//                    break;
//            }

            newFood.setIsLocked(trangThai);
            newFood.setImg(imgPath);

            // Insert the new food item into the database
            dao.insert(newFood);

            // Refresh the data and update the UI
            refresh();
            clearForm();
            helper.DialogHelper.alert(panel, "Thêm món thành công!");
        } catch (Exception ex) {
            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm món!");
            ex.printStackTrace(); // Handle the exception appropriately, e.g., show an error message
        }
    }

    public void fillToForm(int selectedRow) {
        try {
            Food get = food.get(selectedRow);
            setForm(get);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    public void setForm(Food fd) {
        panel.txtTenMon.setText(fd.getName());
        panel.txtGiaMon.setText(String.valueOf(fd.getPrice()));
        panel.cboLoaiMon.setSelectedItem(types.get(panel.cboLoaiMon.getSelectedIndex()).getType_id());
        switch (String.valueOf(fd.isIsLocked())) {
            case "true":
                panel.cboTrangThai.setSelectedIndex(1);
                break;
            case "false":
                panel.cboTrangThai.setSelectedIndex(0);
                break;
        }
    }

    public String getTrangThai(boolean status) {
        return status ? "Khoá" : "Đang mở";
    }

    public void suaMon(int selectedRow) {
        int dish_id = -1;
        try {
            System.out.println("sua mon: " + selectedRow);
            dish_id = food.get(selectedRow).getDish_ID();
            Food newFood = new Food();
            newFood.setDish_ID(dish_id);
            newFood.setName(panel.txtTenMon.getText());
            newFood.setPrice(BigDecimal.valueOf(Double.parseDouble(panel.txtGiaMon.getText())));
            newFood.setType(getTypeID(panel.cboLoaiMon.getSelectedItem().toString()));
            switch (panel.cboTrangThai.getSelectedIndex()) {
                case 1:
                    newFood.setIsLocked(true);
                    break;
                case 0:
                    newFood.setIsLocked(false);
                    break;
            }
            newFood.setImg(food.get(selectedRow).getImg());

            dao.update(newFood);
            clearForm();
            helper.DialogHelper.alert(panel, "Sửa món thành công!!!");
        } catch (IndexOutOfBoundsException ie) {
            helper.DialogHelper.alert(panel, "Vui lòng chọn món cần sửa!!!");
        } catch (Exception ex) {
            helper.DialogHelper.alert(panel, "Xảy ra lỗi khi sửa món!!!");
            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void xoaMon(int selectedRow) {
        System.out.println("xoa mon: " + selectedRow);
        int dish_id = -1;
        try {
            if (dish_id < 0) {
                throw new IndexOutOfBoundsException();
            }
            dish_id = food.get(selectedRow).getDish_ID();
            if (DialogHelper.confirm(panel, "Xác nhận xoá món thứ " + (selectedRow + 1) + "?")) {
                Food newFood = new Food();
                newFood.setDish_ID(dish_id);
                try {
                    dao.delete(newFood);
                    clearForm();
                    helper.DialogHelper.alert(panel, "Xóa Món Thành Công!");
                } catch (Exception ex) {
                    helper.DialogHelper.alert(panel, "Xóa món thất bại!");
                    Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IndexOutOfBoundsException e) {

        }

    }

    public void clearForm() {
        panel.txtTenMon.setText("");
        panel.txtGiaMon.setText("");
        panel.cboLoaiMon.setSelectedIndex(0);
        panel.cboTrangThai.setSelectedIndex(0);
    }

    public void timKiem(String tuKhoa, String foodTypeName) {
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
                    DefaultTableModel model = (DefaultTableModel) panel.tblDanhSachMonAn.getModel();
                    model.setRowCount(0);
                    int rowIndex=0;
                    for (Food fd1 : fd) {
                        Object[] row = new Object[]{fd1.getName(), fd1.getPrice(), FoodHelper.getTypeName(fd1.getType(), types), fd1.getImg(), getTrangThai(fd1.isIsLocked())};
                        model.addRow(row);
                        rowIndex++;
                    }
                    TableColumn column = panel.tblDanhSachMonAn.getColumnModel().getColumn(3);
                    column.setCellRenderer(new imgHelper());

                    for (int i = 0; i < rowIndex; i++) {
                        setImg(model, i, food.get(i).getImg());
                    }

                    panel.tblDanhSachMonAn.updateUI();
                } catch (Exception ex) {
                    Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public boolean validateForm(boolean chk) {
        float giaMon;
        if (panel.txtTenMon.getText().length() == 0) {
            helper.DialogHelper.alert(panel, "Không được phép để trống tên món!");
            panel.txtTenMon.requestFocus();
            return false;
        } else if (panel.txtTenMon.getText().length() < 4) {
            helper.DialogHelper.alert(panel, "Tên không được ít hơn 6 kí tự");
            panel.txtTenMon.requestFocus();
            return false;
        } else if (panel.txtGiaMon.getText().length() == 0) {
            helper.DialogHelper.alert(panel, "Không được phép để trống giá món!");
            panel.txtGiaMon.requestFocus();
            return false;
        } else try {
            giaMon = Float.parseFloat(panel.txtGiaMon.getText());
        } catch (NumberFormatException ex) {
            helper.DialogHelper.alert(panel, "Giá món phải là một số!");
            return false;
        }

        return true;
    }

    private void fillTypeList(List<FoodType> types) {
        panel.cboLoaiMon.removeAllItems();
        panel.cboChonLoaiMon.removeAllItems();
        for (FoodType type : types) {
            panel.cboLoaiMon.addItem(type.getType());
            panel.cboChonLoaiMon.addItem(type.getType());
        }
        panel.cboChonLoaiMon.addItem("Tất cả");
    }

    private int getTypeID(String selectedItem) {
        int typeID = 0;
        for (FoodType food1 : types) {
            if (selectedItem.equalsIgnoreCase(food1.getType())) {
                typeID = food1.getType_id();
            }
        }
        return typeID;
    }

}
