package control;

import DAO.AuthenticateDAO;
import DAO.HoiVienDAO;
import DAO.OrderDetailsDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import frame.ThanhToanJDialog;
import helper.BuffImgHelper;
import helper.QRCodeHelper;
import java.awt.Frame;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.orderedDishes;

/**
 *
 * @author capoo
 */
public class ThanhToanControl {

    ThanhToanJDialog dialog;
    Frame parent;
    private String sdt;
    private String voucher;
    private int order_choosen;
    OrderDetailsDAO orderDao = new OrderDetailsDAO();
    HoiVienDAO hoiVienDao = new HoiVienDAO();
    List<orderedDishes> listOrdered = new ArrayList<>();

    public ThanhToanControl(java.awt.Frame parent) {
//        dialog = new ThanhToanJDialog(parent, true,this);
        this.parent = parent;
    }

    public void setOrder(String sdt, String voucher, int order_choosen) {
        this.sdt = sdt;
        this.voucher = voucher;
        this.order_choosen = order_choosen;
        System.out.println("order choosen:  " + this.order_choosen);

    }

    public void setDialog(ThanhToanJDialog dialog) {
        this.dialog = dialog;
        getData();
    }

    public void confirm() {
        createBill();

    }

    public void getData() {
        try {
            listOrdered.clear();
            listOrdered = orderDao.getOrderedDish(order_choosen);
            DefaultTableModel model = (DefaultTableModel) dialog.tblListOrdered.getModel();
//            dialog.tblListOrdered.setModel(model);
            model.setRowCount(0);

            for (orderedDishes dishes : listOrdered) {
                Object[] row = new Object[]{dishes.getFoodName(), dishes.getQuantity(), dishes.getPrice()};
                model.addRow(row);
            }

            if (!sdt.isEmpty()) {
                dialog.lblSdtHoiVien.setText(sdt);
                dialog.lblHoiVien.setText(hoiVienDao.getNameBySDT(sdt));
            }
            
            Random random = new Random();
            Integer maBill = 1000000 + random.nextInt(9999999);

            System.out.println("sdt hoivien: " + this.sdt);
            dialog.lblMaBill.setText(maBill.toString());
            LocalDateTime currentTime = LocalDateTime.now();
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
            dialog.lblNgayThanhToan.setText(formattedTime);
            dialog.lblNameNV.setText(AuthenticateDAO.getUsername());

//            dialog.lblGiamGiaAmount.setText(getVoucherAmount(voucher).toString());
//            dialog.lblTotalPrice.setText(getTotalPrice(getVoucherAmount(voucher)).toString());
        } catch (Exception ex) {
            Logger.getLogger(ThanhToanControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private Double getVoucherAmount(String voucher) {
////        Double discount = ; 
////        return discount.toString(); 
//    }
//    
//    private Double getTotalPrice(double voucherAmount) {
//        
//        
////        return 
//    }

    public void createBill() {
        String pdfFolder = "/bill-files";
        String fontPath = System.getProperty("user.dir") + pdfFolder+"/Font_Anh/UTM Swiss 721 Black Condensed.ttf";
        //        

        // Tạo BaseFont từ file font chữ
        BaseFont baseFont = null;
        try {
            baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | java.io.IOException e) {
            e.printStackTrace();
        }

        // Tạo Font sử dụng BaseFont vừa tạo
        Font vietnameseFont = new Font(baseFont, 12, Font.NORMAL);

        // Tạo đối tượng Document mới
        Document document = new Document();

        try {
            // Thiết lập PdfWriter và mở tài liệu
            // config 
            
            // usage
            String filePath = System.getProperty("user.dir") + pdfFolder + "/bill.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Lấy ngày và giờ hiện tại
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            // Tạo các đoạn văn bản và font chữ
            Paragraph hello = new Paragraph();
            Paragraph wifi = new Paragraph();
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldFont1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

            // Tạo số ngẫu nhiên cho hóa đơn
//            Random random = new Random();
//            int random1 = 1000000 + random.nextInt(9999999);
//            int random2 = 1000000000 + random.nextInt(999999999);

            // Thêm hình ảnh logo vào tài liệu
            Image image2 = Image.getInstance(System.getProperty("user.dir") + pdfFolder+"/Font_Anh/2.png");
            Image QR = BuffImgHelper.bufferedToImage(QRCodeHelper.createAndGetQR(dialog.lblMaBill.getText()));
//                    Image.getInstance(System.getProperty("user.dir") + pdfFolder+"/Font_Anh/QR.png");
            image2.setAbsolutePosition(500f, 790f);
            image2.scaleAbsolute(100f, 50f);
            QR.setAbsolutePosition(240, 330);
            QR.scaleAbsolute(100, 100);
            document.add(image2);
            // Thêm thông báo cảm ơn và thông tin bổ sung
            hello.add(new com.itextpdf.text.Chunk("Cảm ơn !!", vietnameseFont));
            hello.setAlignment(Element.ALIGN_CENTER);
            hello.setFont(vietnameseFont);
            wifi.add(new com.itextpdf.text.Chunk("Pass WiFi: 190010-Không-Có!"));
            wifi.setAlignment(Element.ALIGN_CENTER);
            // Thêm tên cửa hàng và ngày/giờ
            Paragraph text = new Paragraph("CUSTOMER "+dialog.lblHoiVien.getText(), new Font(boldFont.getBaseFont(), 20, Font.BOLD | Font.NORMAL, vietnameseFont.getColor()));
            text.setAlignment(Element.ALIGN_CENTER);

            document.add(text);
            document.add(new Paragraph("   "));
            document.add(new Paragraph("   "));
            document.add(new Paragraph("   "));
            document.add(new Paragraph(formattedDate));

            // Thêm thông tin hóa đơn
            Paragraph line = new Paragraph("______________________________________________________________________________");
            document.add(new Paragraph("Mã GD: " + dialog.lblMaBill.getText(), vietnameseFont));
            document.add(new Paragraph("Tên: ", vietnameseFont));
            document.add(new Paragraph("Số Điện Thoại: " + dialog.lblSdtHoiVien.getText(), vietnameseFont));
            line.getFont().setStyle(Font.BOLD);
            document.add(new Paragraph("   "));
            document.add(line);
            document.add(new Paragraph("   "));

            // Thêm bảng chi tiết hàng hóa
            PdfPTable itemsTable = new PdfPTable(3);
            itemsTable.addCell(new PdfPCell(new Phrase("Tên món", vietnameseFont)));
            itemsTable.addCell(new PdfPCell(new Phrase("Số Lượng", vietnameseFont)));
            itemsTable.addCell(new PdfPCell(new Phrase("Giá", vietnameseFont)));

            // Điền dữ liệu vào bảng từ dữ liệu hóa đơn
            int rowCount = dialog.tblListOrdered.getRowCount();
            if (rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    try {
                        String id = String.valueOf(dialog.tblListOrdered.getValueAt(i, 0));
                        String name = String.valueOf(dialog.tblListOrdered.getValueAt(i, 1));
                        String price = String.valueOf(dialog.tblListOrdered.getValueAt(i, 2));

                        System.out.println("ID: " + id);

                        itemsTable.addCell(new PdfPCell(new Phrase(id, vietnameseFont)));
                        itemsTable.addCell(new PdfPCell(new Phrase(name, vietnameseFont)));
                        itemsTable.addCell(new PdfPCell(new Phrase(price, vietnameseFont)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            document.add(itemsTable);

            // Thêm tổng cộng
            document.add(new Paragraph("   "));
            Paragraph Trai = new Paragraph("Giá: " + dialog.lblTotalPrice.getText() + " VND", new Font(boldFont.getBaseFont(), boldFont.getSize(), Font.BOLD | Font.NORMAL, vietnameseFont.getColor()));
            Trai.setAlignment(Element.ALIGN_RIGHT);

            document.add(Trai);
            document.add(hello);
            document.add(new Paragraph("   "));
            document.add(wifi);
            document.add(line);
            document.add(QR);
            // Đóng tài liệu
            document.close();
            System.out.println("Hóa đơn được tạo thành công.");
            JOptionPane.showMessageDialog(dialog, "Hóa đơn được tạo thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hiển thị thông báo thành công
//        System.exit(0);
    }

    

}
