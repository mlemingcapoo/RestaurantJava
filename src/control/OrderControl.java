

package control;

import DAO.FoodDAO;
import DAO.SQL;
import frame.OrderPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Food;

/**
 *
 * @author capoo
 */
public class OrderControl {
    
    private static OrderPanel panel;
    SQL dao = new FoodDAO();
    DefaultTableModel model = new DefaultTableModel();

    public void init(OrderPanel panel) {
        OrderControl.panel= panel;
//        panel.setVisible(false);
        refreshAll();
//        panel.
    }
    
    public void refreshAll(){
        getDishes();
        getPendingOrders();
    }
    
    @SuppressWarnings("unchecked")
    public void getDishes(){
        List<Food> food = new ArrayList<>();
        food = dao.selectAll();
        System.out.println("Hện có "+food.size()+" món ăn");
        
    }
    
  
    
    public void test(){
        panel.setVisible(false);
    }

    private void getPendingOrders() {
        
    }

}
