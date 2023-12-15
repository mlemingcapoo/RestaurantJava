package helper;

import static frame.loadingFrame.loadingText;
import java.awt.Component;
import javax.swing.JDialog;

/**
 *
 * @author capoo
 * @param <T>
 */
public class LoadingHelper<T extends Component> {

    static private JDialog dialog;
    Thread loading;

    public LoadingHelper(T parent,String info) {
        dialog = new frame.loadingFrame(null, true);
        dialog.setLocationRelativeTo(parent);
        loadingText.setText(info);
    }

    public void showLoadingDialog() {
        loading = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loading.start();

    }

    public void done() {
        dialog.dispose();
        loading.interrupt();
    }

}
