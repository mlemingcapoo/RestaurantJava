package helper;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author catty
 */
public class imgHelper implements TableCellRenderer {

    public static Image getAppIcon() {
        URL url = imgHelper.class.getResource("/icn/logo.png");
        return new ImageIcon(url).getImage();
    }

    public static Image resize(Image originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
//        ImageIcon icon = new ImageIcon(resultingImage);
        return resultingImage;
    }

//    public static ImageIcon resize(Image originalImage, int width, int height) {
//  // Use a BufferedImage for better quality
//  BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//  
//  // Enable anti-aliasing for smoother scaling
//  Graphics2D g2 = resizedImage.createGraphics();
//  g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//  g2.drawImage(originalImage, 0, 0, width, height, null);
//  g2.dispose();
//
//  // Convert buffered image to icon
//  ImageIcon resizedIcon = new ImageIcon(resizedImage); 
//  
//  return resizedIcon;
//}
    public static void save(File src) {
        File dst = new File("logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs(); //Tao thu muc logos neu chua ton tai
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING); //Copy file vao thu muc logos
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageIcon read(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel label = null;
        try {
            ImageIcon icon = (ImageIcon) value;
            Image image = resize(icon.getImage(), 150, 100);

            label = new JLabel(new ImageIcon(image));
        } catch (Exception e) {
//            System.out.println(e.toString());
        }
        return label;
    }

}
