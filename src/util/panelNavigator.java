

package util;

import GUI.mainUI;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author capoo
 */
public class panelNavigator {
public static void navigate(JFrame frame,JPanel childPanel, JPanel parentPanel) {
        // Setting the panel variable to the JPanel parameter
        parentPanel = childPanel;
        System.out.println("assigned panel");
        // Refreshing the window
        parentPanel.revalidate();
        System.out.println("revalidated");
        parentPanel.repaint();
    }

public static void switchPanel(JPanel panel, String panelName) {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        cardLayout.show(panel, panelName);
    }
}
