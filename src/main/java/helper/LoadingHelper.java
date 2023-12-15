package helper;

import static GUI.mainUI.loadingIndicator;
import static frame.loadingFrame.loadingText;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

/**
 *
 * @author capoo
 * @param <T>
 */
public class LoadingHelper<T extends Component> {

    static private JDialog dialog;
    Thread loading;

    public LoadingHelper(T parent, String info) {
        dialog = new frame.loadingFrame(null, true);
        dialog.setLocationRelativeTo(parent);
        loadingText.setText(info);
    }
    
    public LoadingHelper(String info) {
        try {
        loadingIndicator.setText(info);
            
        } catch (Exception e) {
            e.getMessage();
        }
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

    public void setLoadingStatus(boolean isLoading) {
        try{
        if (isLoading) {

            ImageIcon gif = new ImageIcon(getClass().getResource("/images/loading.gif"));

// Set gif on label
            loadingIndicator.setIcon(gif);

//            loadingIndicator.setText("Updating");
        } else {
            loadingIndicator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Accept.png"))); // NOI18N
            loadingIndicator.setText("Updated");
        }
        }catch(Exception e){
            System.out.println("line 64 loading helper: "+e.getMessage());
        }
    }

    public void done() {
        dialog.dispose();
        loading.interrupt();
    }

}
