

package control;

import frame.OrderPanel;
import frame.Scan_QR;

/**
 *
 * @author capoo
 */
public class Scan_QR_Control {
private static Scan_QR panel;

    public void init(Scan_QR panel) {
        Scan_QR_Control.panel=panel;
    }

    public void scan(OrderPanel aThis) {
        Scan_QR scan = new Scan_QR(aThis);
        scan.setVisible(true);
    }
}
