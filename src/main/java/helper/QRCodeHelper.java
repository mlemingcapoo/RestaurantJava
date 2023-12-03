package helper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author capoo
 */
public class QRCodeHelper {

    public static void createQR(String text) throws IOException,WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
        String pdfFolder = "/bill-files";
        File outputFile = new File(System.getProperty("user.dir") + pdfFolder + "/Font_Anh/2.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());
        System.out.println("QR code generated into file successfully!");
    }
    
    public static BufferedImage createAndGetQR(String text) throws IOException,WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
        System.out.println("QR code generated successfully!");
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
