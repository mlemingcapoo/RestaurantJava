/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TaoQR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;

public class QRCodeGenerator {

  public static void main(String[] args) throws Exception {

    String text = "MaVocherne";
    
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);

    File outputFile = new File("src\\TaoQR\\qrcode.png");
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());

    System.out.println("QR code generated successfully!");
  }

}