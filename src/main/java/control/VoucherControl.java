

package control;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Voucher;
/**
 *
 * @author capoo
 */
public class VoucherControl {
private static VoucherJPanel panel;
    private static Voucher newVoucher;
static List<Voucher> voucher = new ArrayList<>();
DefaultTableModel Voucher = new DefaultTableModel();
VoucherDAO dao = new VoucherDAO();
    public void init(VoucherJPanel panel) {
        VoucherControl.panel=panel;
        refresh();
        
    }  
    public void refresh(){
        getVoucher();
        fillDishes();
    }
    public void fillDishes(){
        DefaultTableModel model = (DefaultTableModel) panel.tblVoucher.getModel();
        model.setRowCount(0);
        for (Voucher fd : voucher) {
            Object[] row = new Object[]{fd.getDiscountPercent(),fd.getVCode(),fd.getExpireDate()};
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


    public void getVoucher(){
        voucher.clear();
        try {
            voucher = dao.selectAll();
//            System.out.println("số món có trong menu:" + voucher.size());
        } catch (Exception e) {
        }
    }

 public void them() {
    String discountText = panel.txtGiam.getText().trim();
    Date date = panel.NgayHetHan.getDate();

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

        dao.insert(newVoucher);

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
public void TaoQRVoucher() throws WriterException, IOException {
    String text = newVoucher.getVCode(); // Use the VCode from newVoucher

    if (text.isEmpty()) {
        System.out.println("Không thể tạo mã QR với mã ngẫu nhiên rỗng.");
        return;
    }

    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);

    File outputFile = new File("D:\\" + text + ".png");
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());

   // System.out.println("QR code generated successfully for: " + text);
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

        dao.update(selectedVoucher); // Update the voucher in the database

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


}
