

package helper;

import java.awt.Color;
import javax.swing.JInternalFrame;
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
        pane.add(new defaultPage());
    }
    
    public static void switchDisplay(JTabbedPane pane, JPanel frame){
        pane.removeAll();
        pane.setForeground(Color.BLACK);
        pane.addTab(null, frame);
        pane.setSelectedComponent(frame);
    }
}
