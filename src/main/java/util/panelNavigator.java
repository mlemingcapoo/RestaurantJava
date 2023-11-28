package util;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author capoo
 */
public class panelNavigator {

    public static void switchPanel(JPanel panel, String panelName) {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        cardLayout.show(panel, panelName);
    }
}
