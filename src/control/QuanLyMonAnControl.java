

package control;

import DAO.FoodDAO;
import DAO.SQL;
import frame.QuanLyMonAnJPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Food;

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
        QuanLyMonAnControl.panel = panel;
        refresh();
    }
    
    public void refresh(){
        getMon();
        fillDishes();
    }
    public void fillDishes(){
        DefaultTableModel model = (DefaultTableModel) panel.tblDanhSachMonAn.getModel();
        model.setRowCount(0);
        for (Food fd : food) {
            Object[] row = new Object[]{fd.getName(), fd.getPrice(), fd.getType(), fd.getImg(), fd.isIsLocked()};
            model.addRow(row);
        }
    }
    
        

    public void getMon(){
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
        String tenMon = panel.txtTenMon.getText();
        float giaMon = Float.parseFloat(panel.txtGiaMon.getText());
        String loaiMon = (String) panel.cboLoaiMon.getSelectedItem();
        int trangThaiIndex = panel.cboTrangThai.getSelectedIndex(); // Get the selected index

        // Map the selected index from the combo box to the appropriate status
        boolean trangThai = (trangThaiIndex == 1); // 1 represents "đã tắt," 0 represents "hoạt động"

        String imgPath = null;

        // Check if the image path is empty or null, set a default image link
        if (imgPath == null || imgPath.isEmpty()) {
            imgPath = "https://example.com/default-image.jpg"; // Replace with your default image link
        }

        // Create a new Food object
        Food newFood = new Food();
        newFood.setName(tenMon);
        newFood.setPrice(giaMon);

        // Map the selected item from the combo box to the appropriate type
        switch (loaiMon.toLowerCase()) {
            case "food":
                newFood.setType("Food");
                break;
            case "drink":
                newFood.setType("Drink");
                break;
            case "dessert":
                newFood.setType("Dessert");
                break;
        }

        newFood.setIsLocked(trangThai);
        newFood.setImg(imgPath);

        // Insert the new food item into the database
        dao.insert(newFood);

        // Refresh the data and update the UI
        refresh();
        helper.DialogHelper.alert(panel, "Thêm món thành công!");
    } catch (NumberFormatException ex) {
        helper.DialogHelper.alert(panel, "Giá món phải là một số!");
    } catch (Exception ex) {
        helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm món!");
        ex.printStackTrace(); // Handle the exception appropriately, e.g., show an error message
    }
}

    
    public void fillToForm(int selectedRow){
    Food get = food.get(selectedRow);
        setForm(get);
}
    public void setForm(Food fd){
        panel.txtTenMon.setText(fd.getName());
        panel.txtGiaMon.setText(String.valueOf(fd.getPrice()));
        panel.cboLoaiMon.setSelectedItem(fd.getType());
        switch (String.valueOf(fd.isIsLocked())) {
            case "true":
                panel.cboTrangThai.setSelectedIndex(1);
                break;
            case "false":
                panel.cboTrangThai.setSelectedIndex(0);
                break;
        }
    }

    public void suaMon() {
        
    }
}
