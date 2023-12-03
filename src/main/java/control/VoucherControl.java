package control;

import DAO.HoiVienDAO;
import DAO.VoucherDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import frame.VoucherJPanel;
import helper.DateHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import model.Voucher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *
 * @author capoo
 */
public class VoucherControl {

    private static VoucherJPanel panel;
    private static Voucher newVoucher;
    static List<Voucher> voucher = new ArrayList<>();
    DefaultTableModel Voucher = new DefaultTableModel();
    VoucherDAO vcdao = new VoucherDAO();
    
//KhachHang hoiVien = new KhachHang();
static List<KhachHang> hv = new ArrayList<>();
HoiVienDAO hvdao = new HoiVienDAO();

    public void init(VoucherJPanel panel) {
        VoucherControl.panel = panel;
        refresh();
        
    }

    public void refresh() {
        getVoucher();
        fillDishes();
        fillHoiVien();
    }
    
    public void fillHoiVien(){
        hv.clear();
        hv = hvdao.selectAll();
        DefaultTableModel model = (DefaultTableModel) panel.tblHoiVien.getModel();
        model.setRowCount(0);
        for (KhachHang fd : hv) {
            Object[] row = new Object[]{fd.getName(),fd.getSDT(),fd.getEmail()};
            model.addRow(row);
        }
        System.out.println("HoiVien filled");
    }

    public void fillDishes() {
        DefaultTableModel model = (DefaultTableModel) panel.tblVoucher.getModel();
        model.setRowCount(0);
        for (Voucher fd : voucher) {
            Object[] row = new Object[]{fd.getDiscountPercent(), fd.getVCode(), fd.getExpireDate()};
            model.addRow(row);
        }
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    public void fillToForm(int selectedRow) {
        Voucher get = voucher.get(selectedRow);
        setForm(get);
    }

    public void setForm(Voucher fd) {
        panel.txtGiam.setText(String.valueOf(fd.getDiscountPercent()));

        // Assuming toDate method takes a String and a date format
        Date dt = DateHelper.toDate(fd.getExpireDate(), "yyyy-MM-dd");
        // Check if the date is not null before setting it
        if (dt != null) {
            panel.NgayHetHan.setDate(dt);
        } else {
            // Handle the case where the date is null
            System.out.println("Error: The date is null.");
        }
    }

    public void getVoucher() {
        voucher.clear();
        try {
            voucher = vcdao.selectAll();
//            System.out.println("số món có trong menu:" + voucher.size());
        } catch (Exception e) {
        }
    }

    public void them() {
        Date date = panel.NgayHetHan.getDate();
        String discountText = panel.txtGiam.getText().trim();

        try {
            if (discountText.isEmpty()) {
                helper.DialogHelper.alert(panel, "Vui lòng nhập giảm giá cho voucher.");
                return;
            }

            if (date == null) {
                helper.DialogHelper.alert(panel, "Vui lòng chọn ngày hết hạn cho voucher.");
                return;
            }

            double discount = Double.parseDouble(discountText);
            String voucherCode = generateRandomCode();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            Date expireDate = dateFormat.parse(formattedDate);

            newVoucher = new Voucher();
            newVoucher.setDiscountPercent((float) discount);
            newVoucher.setVCode(voucherCode);
            newVoucher.setExpireDate(formattedDate);

            voucher.add(newVoucher);

            vcdao.insert(newVoucher);

            refresh();

            // Thông báo khi tạo voucher thành công
            helper.DialogHelper.alert(panel, "Tạo voucher thành công!");
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            helper.DialogHelper.alert(panel, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại giá trị nhập.");
        } catch (Exception e) {
            e.printStackTrace();
            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
    }

    public void TaoQRVoucher() {
        try {
            String text = newVoucher.getVCode();

            if (text.isEmpty()) {
                System.out.println("Không thể tạo mã QR với mã ngẫu nhiên rỗng.");
                return;
            }

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);

            File outputFile = new File("D:\\" + text + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());

            System.out.println("QR code generated successfully for: " + text);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            System.out.println("Error generating QR code.");
        }
    }

   public String upload() {
    String apiKey = "43c266c2e17b3e719a7cd819e1d9d6a7"; // Replace with your ImgBB API key
    String imageUrl = "";

    OkHttpClient client = new OkHttpClient();

    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("key", apiKey)
            .addFormDataPart("image", newVoucher.getVCode() + ".png",
                    //System.out.print("");
                    RequestBody.create(new File("D:\\" + newVoucher.getVCode() + ".png"), MediaType.parse("image/png")))
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
                    imageUrl = dataObject.getString("url");
                    System.out.println("Image uploaded successfully");
                    System.out.println("Image URL: " + imageUrl);
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
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error uploading image.");
    }

    return imageUrl;
}


    public void suaVoucher(int selectedRow) {
        try {
            Voucher selectedVoucher = voucher.get(selectedRow);

            int voucherID = selectedVoucher.getVoucherID();
            String discountText = panel.txtGiam.getText().trim();
            Date date = panel.NgayHetHan.getDate();

            if (discountText.isEmpty()) {
                helper.DialogHelper.alert(panel, "Vui lòng nhập giảm giá cho voucher.");
                return;
            }

            if (date == null) {
                helper.DialogHelper.alert(panel, "Vui lòng chọn ngày hết hạn cho voucher.");
                return;
            }

            double discount = Double.parseDouble(discountText);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            Date expireDate = dateFormat.parse(formattedDate);

            selectedVoucher.setDiscountPercent((float) discount);
            selectedVoucher.setExpireDate(formattedDate);

            vcdao.update(selectedVoucher); // Update the voucher in the database

            refresh();

            // Thông báo khi cập nhật voucher thành công
            helper.DialogHelper.alert(panel, "Cập nhật voucher thành công!");
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            helper.DialogHelper.alert(panel, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại giá trị nhập.");
        } catch (Exception e) {
            e.printStackTrace();
            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
    }

    public void clearForm() {
        panel.txtGiam.setText("");
    }

    public void xoaVoucher(int selectedRow) {
        System.out.println("xoa Voucher: " + selectedRow);
        int VoucherID = voucher.get(selectedRow).getVoucherID();
        Voucher newVocher = new Voucher();
        newVocher.setVoucherID(VoucherID);
        try {
            vcdao.deleteById(newVocher);
            clearForm();
            helper.DialogHelper.alert(panel, "Xóa Voucher Thành Công!");
        } catch (Exception ex) {
            helper.DialogHelper.alert(panel, "Xóa Voucher thất bại!");
            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void send() {
    final String username = "infobasil.click@gmail.com";
    final String password = "c g x u h r y g v j z p e x l q ";

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

    // Check if required fields are not empty
    String recipientEmail = panel.txtMail.getText();
    String subject = panel.txtTieuDe.getText();
    String content = panel.txtNDung.getText();

    if (recipientEmail.isEmpty() || subject.isEmpty() || content.isEmpty()) {
        helper.DialogHelper.alert(panel, "Xin vui lòng điền đầy đủ thông tin vào các ô bắt buộc!");
        return; // Do not proceed with sending the email
    }

    // Check subject length
    if (subject.length() > 100) {
        helper.DialogHelper.alert(panel, "Chủ đề không thể vượt quá 100 ký tự.");
        return; // Do not proceed with sending the email
    }

    // Check if there is a valid image link
    String imagePath = upload();
    if (imagePath != null && !imagePath.isEmpty()) {
        try {
            // Tạo đối tượng Message để xây dựng nội dung email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Tạo phần nội dung email dạng multipart
            MimeMultipart multipart = new MimeMultipart();

            // Thêm phần văn bản vào email
            BodyPart textPart = new MimeBodyPart();
            textPart.setContent(
                    "<p style=\"font-family: 'Arial', sans-serif;\">" + content + "</p>"
                            + "<p style=\"font-family: 'Arial', sans-serif;\">Mã Voucher của bạn là: " + newVoucher.getVCode() + "</p>"
                            + "<p style=\"font-family: 'Arial', sans-serif;\">Ngày hết hạn là: " + panel.NgayHetHan.getDate() + "</p>",
                    "text/html; charset=utf-8"
            );
            multipart.addBodyPart(textPart);

            // Thêm phần ảnh vào email
            BodyPart imagePart = new MimeBodyPart();
            URL imageUrl = new URL(imagePath);
            InputStream imageStream = imageUrl.openStream();
            imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageStream, "image/jpeg")));
            System.out.println("line 99");
            imagePart.setFileName(newVoucher.getVCode() + ".jpg"); // You can set any file name here
            multipart.addBodyPart(imagePart);
System.out.println("line 100");
            // Đặt nội dung của email là phần nội dung multipart vừa tạo
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);

            // Hiển thị thông báo khi gửi thành công
            helper.DialogHelper.alert(panel, "Gửi Thành Công !");
            System.out.println("Done");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            helper.DialogHelper.alert(panel, "Error sending email: " + e.getMessage());
        }
    } else {
        helper.DialogHelper.alert(panel, "Please provide a valid image link.");
    }
}


    public void fillMail(int selectedRow) {
        panel.txtMail.setText(hv.get(selectedRow).getEmail());
    }

    
}
