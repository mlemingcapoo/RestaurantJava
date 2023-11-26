

package control;

import frame.OrderPanel;

/**
 *
 * @author capoo
 */
public class OrderControl {
    
    private static OrderPanel panel;

    public void init(OrderPanel panel) {
        OrderControl.panel= panel;
//        panel.setVisible(false);
        
    }
    
    public void test(){
        panel.setVisible(false);
    }

}
