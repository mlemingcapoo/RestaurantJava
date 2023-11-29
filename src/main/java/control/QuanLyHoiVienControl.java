//package control;
//
//import DAO.HoiVienDAO;
//import frame.QuanLyHoiVienJPanel;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import javax.swing.table.DefaultTableModel;
//import model.KhachHang;
//
//public class QuanLyHoiVienControl {
//
//    private static QuanLyHoiVienJPanel panel;
//    DefaultTableModel model = new DefaultTableModel();
//     static List<KhachHang> KhachHang = new ArrayList<>();
//    HoiVienDAO dao = new HoiVienDAO();
//
//    public void init(QuanLyHoiVienJPanel panel) {
//        QuanLyHoiVienControl.panel = panel;
//        refresh();
////        QuanLyHoiVienControl.panel = panel;
////        
//    }
//
////    public void refresh() {
////        getHV();
////        fillTable();
////    }
//
////    void fillTable() {
////        DefaultTableModel model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
////        model.setRowCount(0);
////        for (KhachHang kh : KhachHang) {
////            Object[] row = new Object[]{kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
////            model.addRow(row);
////        }
////    }
//    
//    public final void refresh() {
//        getHV();
//        fillTable();
//    }
//
//    public void fillTable() {
//        
//        model =(DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
//        model.setRowCount(0);
//        for (KhachHang kh : KhachHang) {
//            Object[] row = new Object[]{kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
//            model.addRow(row);
//        }
//        System.out.println("FILLED HOIVEN TABLE");
//    }
//
//    public List<KhachHang> getHV() {
//        try {
//            KhachHang.clear();
//            KhachHang = dao.selectAll();
//            System.out.println("Số khách hàng có trong danh sách: " + KhachHang.size());
//        } catch (Exception e) {
//            // Handle the exception appropriately
//            e.printStackTrace();
//        }
//        return KhachHang;
//    }
//
//    public void themHV() {
//        try {
//            String tenHV = panel.txtTenHoiVien.getText();
//            String email = panel.txtGmailHoiVien.getText();
//              String sdt = panel.txtSDTHoiVien.getText();
//               Date date = panel.NgaySinh.getDate();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = dateFormat.format(date);
//            KhachHang newkh = new KhachHang();
//            newkh.setName(tenHV);
//            newkh.setEmail(email);
//            newkh.setSDT(sdt);
//            newkh.setBirthday(formattedDate);
//            dao.insert(newkh);
//            this.refresh();
//            helper.DialogHelper.alert(panel, "Thêm khách hàng thành công!");
//        } catch (Exception e) {
//            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm khách hàng!");
//            e.printStackTrace();
//        }
//    }
//
//    
//
//    public void fillToForm(int selectedRow) {
//        System.out.println("line 101????: "+selectedRow);
//        List<KhachHang> hv = getHV();
////        System.out.println("line88: " + selectedRow);
////        model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
////        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
////            String name = (String) model.getValueAt(selectedRow, 0);
////            String sdt = (String) model.getValueAt(selectedRow, 1);
////            String email = (String) model.getValueAt(selectedRow, 2);
////            String birthday = (String) model.getValueAt(selectedRow, 4);
////
////            KhachHang kh1 = new KhachHang();
////            kh1.setName(name);
////            kh1.setSDT(sdt);
////            kh1.setEmail(email);
////            kh1.setBirthday(birthday);
////
////        }
////            setform(selectedRow);
//    }
//
////    public void suaHV(int selectedRow) {
////        System.out.println("sua mon: " + selectedRow);
////        String sdt1 = KhachHang.get(selectedRow).getSDT();
////        KhachHang newKh = new KhachHang();
////        newKh.setSDT(sdt1);
////        newKh.setName(txtTenHoiVien1.getText());
////        newKh.setEmail(txtGmailHoiVien.getText());
////        Date date = NgaySinh.getDate();
////        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        String formattedDate = dateFormat.format(date);
////        newKh.setBirthday(formattedDate);
////        try {
////            dao.update(newKh);
////            clearForm();
////            helper.DialogHelper.alert(this, "Sửa khách hàng thành công!!!");
////        } catch (Exception ex) {
////            helper.DialogHelper.alert(this, "Xảy ra lỗi khi sửa món!!!");
////        }
////    }
//
//    public void suaHV1(int selectedRow) {
//        System.out.println("line128: " + selectedRow);
//        
//        if (selectedRow >= 0 && selectedRow < KhachHang.size()) {            
//            String sdt1 = KhachHang.get(selectedRow).getSDT();
//            KhachHang newKh = new KhachHang();
//            newKh.setSDT(sdt1);
//            newKh.setName(panel.txtTenHoiVien.getText());
//            newKh.setEmail(panel.txtGmailHoiVien.getText());
//            Date date = panel.NgaySinh.getDate();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = dateFormat.format(date);
//            newKh.setBirthday(formattedDate);
//            try {
//                dao.update(newKh);
//                clearForm();
//                helper.DialogHelper.alert(panel, "Sửa khách hàng thành công!!!");
//            } catch (Exception ex) {
//                helper.DialogHelper.alert(panel, "Xảy ra lỗi khi sửa món!!!");
//            }
//        } else {
//            helper.DialogHelper.alert(panel, "Selected row is out of bounds!");
//        }
//    }
//
//    public void clearForm() {
//        panel.txtTenHoiVien.setText("");
//        panel.txtSDTHoiVien.setText("");
//        panel.txtGmailHoiVien.setText("");
//        panel.NgaySinh.setDate(null);
//    }
//
//    
//    public void clearform(){
//        panel.txtTenHoiVien.setText("");
//        panel.txtSDTHoiVien.setText("");
//        panel.txtGmailHoiVien.setText("");
//        panel.NgaySinh.setDate(null);
//    }
//    
//
//
//    public void suaKhachHang(int selectedRow) {
//        KhachHang newkh = new KhachHang();
//
//    }
//}
