package frame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import helper.DialogHelper;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class Scan_QR extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private static Webcam webcam = null;

    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    private OrderPanel QuanLyQuanLyBanHang = null;
    
    public Scan_QR(OrderPanel QuanLyQuanLyBanHang) {
        initComponents();
        setLocationRelativeTo (null);
        initWebcam();
        this.QuanLyQuanLyBanHang = QuanLyQuanLyBanHang;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 360));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{webcam.close();}catch(Exception e){
            DialogHelper.alert(this,"Vui lòng kiểm tra lại quyền truy cập webcam");
        }
        this.dispose();
        System.out.println("Webcam Frame exited");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    
    public Webcam getCam() {
		Webcam webcam = Webcam.getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
		} else {
			System.out.println("No webcam detected");
		}
                return webcam;
	}
    
    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        try {
            
            webcam = Webcam.getWebcams().get(0);
        } catch (WebcamException w){
            DialogHelper.alert(panel, w.toString());
            
        } catch (IndexOutOfBoundsException e){
            DialogHelper.alert(panel, "Không tìm thấy webcam nào, đang thử lại...");
            try {
                webcam = getCam();
            } catch (Exception e1){
                DialogHelper.alert(panel, e1.toString());
            }
        } 
        
        //0 is default webcam
        //stop get webcam
        try{
        Webcam.getDiscoveryService().setEnabled(false);
        Webcam.getDiscoveryService().stop();
        webcam.setViewSize(size);
      
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        
        
        jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));
        executor.execute(this);
        } catch (Exception e){
            System.out.println("Webcam frame status: "+e.getMessage());
        }
    }

    @Override
public void run() {
    do {
        try {
            Thread.sleep(100);
            
        } catch (InterruptedException e) {
            
        }

        Result result = null;
        BufferedImage image = null;

        if (webcam.isOpen()) {
            if ((image = webcam.getImage()) == null) {
                continue;
            }
        }

        try {
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = new MultiFormatReader().decode(bitmap);
            }

        } catch (Exception e) {
            // Handle the exception appropriately, e.g., log it
            System.out.println("Đang tìm kiếm mã QR... ");
        }

        if (result != null) {
            OrderPanel quanLyPanel = this.QuanLyQuanLyBanHang;
            String[] code = result.getText().split(",");

            // Assuming QuanLyQuanLyBanHangJPanel has a static field txtMaVocher
            quanLyPanel.txtMaVocher.setText(result.getText()); // Set text to JTextField

            // You may want to add code to write the result to a text file here
            

            webcam.close();
            this.dispose();
            break;
        }
    } while (true);
}
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
}
