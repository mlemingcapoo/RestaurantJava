

package helper;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import frame.blank_frame;

/**
 *
 * @author catty
 */
public class PanelHelper {

    public static void reset(JTabbedPane pane){
        pane.removeAll();
        pane.setForeground(Color.BLACK);
        pane.add(new blank_frame());
    }
    
    public static void switchDisplay(JTabbedPane pane, JPanel frame){
        pane.removeAll();
        pane.setForeground(Color.BLACK);
        pane.addTab(null, frame);
        pane.setSelectedComponent(frame);
    }
}
