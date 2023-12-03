package helper;

import com.itextpdf.text.BadElementException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.text.Image;
import javax.imageio.ImageIO;

public class BuffImgHelper {

  public static com.itextpdf.text.Image bufferedToImage(BufferedImage bufferedImage) throws IOException,BadElementException {
    
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "png", byteStream);
    byte[] imageBytes = byteStream.toByteArray();

    com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageBytes);

    return image; 
  }

}