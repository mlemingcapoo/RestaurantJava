

package control;

import frame.thanhToanJDialog;
import java.util.List;
import model.Order;

/**
 *
 * @author capoo
 */
public class thanhToanControl {
    
    thanhToanJDialog dialog;

    public thanhToanControl(java.awt.Frame parent) {
        dialog = new thanhToanJDialog(parent, true);
    }

    void setOrder(List<Order> order, int order_choosen) {
        System.out.println("order choosen:  "+order_choosen);
    }

}
