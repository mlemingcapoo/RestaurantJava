/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SendMail;

import Api_upload_image.upanh;
import java.io.File;
import java.io.IOException; 
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *
 * @author mynla
 */
public class SendMail extends javax.swing.JFrame {

    /**
     * Creates new form SendMail
     */
    public SendMail() {
        initComponents();
    }

    public void getlink() {
        String apiKey = "43c266c2e17b3e719a7cd819e1d9d6a7"; // Replace with your ImgBB API key

        // Use a file chooser to let the user select the image file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an Image File");
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Image selection canceled.");
            return;
        }

        File imageFile = fileChooser.getSelectedFile();
        System.out.println("Selected Image: " + imageFile.getAbsolutePath());

        if (!imageFile.exists()) {
            System.out.println("Error: Image file not found at " + imageFile.getAbsolutePath());
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", apiKey)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(imageFile, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgbb.com/1/upload")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                if (jsonResponse.has("data")) {
                    JSONObject dataObject = jsonResponse.getJSONObject("data");
                    if (dataObject.has("url")) {
                        String imageUrl = dataObject.getString("url");
                        System.out.println("Image uploaded successfully");
                        System.out.println("Image URL: " + imageUrl);
                        JOptionPane.showMessageDialog(this, "Tải Ảnh Thành Công !");
                        txtgetlinkAnh.setText(imageUrl);
                    } else {
                        System.out.println("Error: 'url' not found in the response");
                    }
                } else {
                    System.out.println("Error: 'data' not found in the response");
                }
            } else {
                System.out.println("Error uploading image");
                System.out.println("Response code: " + response.code());
            }
        } catch (IOException ex) {
            Logger.getLogger(upanh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send() {
    final String username = txtMail.getText();
    final String password = txtPass.getText();

    // Cấu hình các thuộc tính cho việc gửi email
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true"); // Kích hoạt TLS

    // Tạo phiên làm việc (Session) sử dụng thông tin đăng nhập
    Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        // Tạo đối tượng Message để xây dựng nội dung email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(txtMail.getText()));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(txtMailto.getText())
        );
        message.setSubject(txtNdchinh.getText());

        // Tạo phần nội dung email dạng multipart
        MimeMultipart multipart = new MimeMultipart();

        // Thêm phần văn bản vào email
        BodyPart textPart = new MimeBodyPart();
        textPart.setText(txtnd.getText());
        multipart.addBodyPart(textPart);

        String imagePath = txtgetlinkAnh.getText();

        // Thêm phần ảnh vào email
        BodyPart imagePart = new MimeBodyPart();
        URL imageUrl = new URL(imagePath);
        InputStream imageStream = imageUrl.openStream();
        imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageStream, "image/jpeg")));
        imagePart.setFileName("image.jpg"); // You can set any file name here
        multipart.addBodyPart(imagePart);

        // Đặt nội dung của email là phần nội dung multipart vừa tạo
        message.setContent(multipart);

        // Gửi email
        Transport.send(message);

        // Hiển thị thông báo khi gửi thành công
        JOptionPane.showMessageDialog(this, "done");
        System.out.println("Done");

    } catch (MessagingException | IOException e) {
        e.printStackTrace();
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMail = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        txtMailto = new javax.swing.JTextField();
        txtNdchinh = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtnd = new javax.swing.JTextArea();
        btngui = new javax.swing.JButton();
        txtgetlinkAnh = new javax.swing.JTextField();
        getLinkAnh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMail.setText("infobasil.click@gmail.com");

        txtPass.setText("c g x u h r y g v j z p e x l q ");

        txtMailto.setText("mynlatui@gmail.com");

        txtnd.setColumns(20);
        txtnd.setRows(5);
        jScrollPane1.setViewportView(txtnd);

        btngui.setText("gui");
        btngui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguiActionPerformed(evt);
            }
        });

        txtgetlinkAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgetlinkAnhActionPerformed(evt);
            }
        });

        getLinkAnh.setText("Ảnh");
        getLinkAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getLinkAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(getLinkAnh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btngui))
                    .addComponent(jScrollPane1)
                    .addComponent(txtMail)
                    .addComponent(txtPass)
                    .addComponent(txtMailto)
                    .addComponent(txtNdchinh, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(txtgetlinkAnh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMailto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNdchinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtgetlinkAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btngui)
                    .addComponent(getLinkAnh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguiActionPerformed
       
            send();
  
    }//GEN-LAST:event_btnguiActionPerformed

    private void getLinkAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getLinkAnhActionPerformed
        getlink();
    }//GEN-LAST:event_getLinkAnhActionPerformed

    private void txtgetlinkAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgetlinkAnhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgetlinkAnhActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SendMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SendMail().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btngui;
    private javax.swing.JButton getLinkAnh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtMailto;
    private javax.swing.JTextField txtNdchinh;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtgetlinkAnh;
    private javax.swing.JTextArea txtnd;
    // End of variables declaration//GEN-END:variables
}
