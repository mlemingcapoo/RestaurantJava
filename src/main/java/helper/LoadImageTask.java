package helper;

import static helper.CachingHelper.imageCache;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author capoo
 */
public class LoadImageTask implements Runnable {

    private final DefaultTableModel model;
    private final int rowIndex;
    private final String imgLink;

    public LoadImageTask(DefaultTableModel model, int rowIndex, String imgLink) {
        this.model = model;
        this.rowIndex = rowIndex;
        this.imgLink = imgLink;
    }

    @Override
    public void run() {
        
        new Thread(() -> {
            try {
                String imgKey = imgLink;
                Image image;
                if (imageCache.containsKey(imgKey)) {
                    image = imageCache.get(imgKey);
                    ImageIcon icon = new ImageIcon(image);
                    model.setValueAt(icon, rowIndex, 3); // 4th column index is 3

                    SwingUtilities.invokeLater(() -> {
                        model.setValueAt(icon, rowIndex, 3);
                    });
                } else {

                    try {
                        // existing image loading code
                        URL url = new URL(imgLink);
                        image = ImageIO.read(url);

                        imageCache.put(imgKey, image);

                        // Create icon and set cell value
                        ImageIcon icon = new ImageIcon(image);
                        model.setValueAt(icon, rowIndex, 3); // 4th column index is 3

                        SwingUtilities.invokeLater(() -> {
                            model.setValueAt(icon, rowIndex, 3);
                        });

                    } catch (Exception e) {
                        System.out.println("line 646 OrderControl: " + e.getMessage());
                        System.out.println(e.getCause());
                    }

                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
            
        }).start();
    }

}
