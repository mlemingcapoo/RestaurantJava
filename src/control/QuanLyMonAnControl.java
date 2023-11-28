

package control;

import DAO.FoodDAO;
import DAO.SQL;
import frame.QuanLyMonAnJPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String tenMon = panel.txtTenMon.getText().trim();
        if (tenMon.isEmpty()) {
            helper.DialogHelper.alert(panel, "Vui lòng nhập tên món!");
            return;
        }

        float giaMon;
        try {
            giaMon = Float.parseFloat(panel.txtGiaMon.getText());
        } catch (NumberFormatException ex) {
            helper.DialogHelper.alert(panel, "Giá món phải là một số!");
            return;
        }

        String loaiMon = (String) panel.cboLoaiMon.getSelectedItem();
        int trangThaiIndex = panel.cboTrangThai.getSelectedIndex();
        boolean trangThai = (trangThaiIndex == 1);

        String imgPath = null;

        // Check if the image path is empty or null, set a default image link
        if (imgPath == null || imgPath.isEmpty()) {
            imgPath = "https://example.com/default-image.jpg"; // Replace with your default image link
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

    public void suaMon(int selectedRow) {
        System.out.println("sua mon: "+selectedRow);
        int dish_id= food.get(selectedRow).getDish_ID();
        Food newFood = new Food();
        newFood.setDish_ID(dish_id);
        newFood.setName(panel.txtTenMon.getText());
        newFood.setPrice(Float.parseFloat(panel.txtGiaMon.getText()));
        newFood.setType(panel.cboLoaiMon.getSelectedItem().toString());
        switch (panel.cboTrangThai.getSelectedIndex()) {
            case 1:
                newFood.setIsLocked(true);
                break;
            case 0:
                newFood.setIsLocked(false);
                break;
        }
        newFood.setImg(food.get(selectedRow).getImg());
    try {
        dao.update(newFood);
    } catch (Exception ex) {
        Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
   


}
