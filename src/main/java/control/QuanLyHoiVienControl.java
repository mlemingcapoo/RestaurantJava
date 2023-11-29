package control;

import DAO.HoiVienDAO;
import frame.QuanLyHoiVienJPanel;
import helper.DateHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;

public class QuanLyHoiVienControl {

    private static QuanLyHoiVienJPanel panel;
    DefaultTableModel HoiVienModel = new DefaultTableModel();
    static List<KhachHang> KhachHang = new ArrayList<>();
    HoiVienDAO dao = new HoiVienDAO();

    public void init(QuanLyHoiVienJPanel panel) {
        QuanLyHoiVienControl.panel = panel;
//        refresh();
    }

//    public void refresh() {
//        getHV();
//        fillTable();
//    }

//    void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
//        model.setRowCount(0);
//        for (KhachHang kh : KhachHang) {
//            Object[] row = new Object[]{kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
//            model.addRow(row);
//        }
//    }

    public void getHV() {
        KhachHang.clear();
        KhachHang = dao.selectAll();
        
    }

    public void themHV() {
        try {
            String tenHV = panel.txtTenHoiVien.getText();
            String email = panel.txtGmailHoiVien.getText();
            String sdt = panel.txtSDTHoiVien.getText();
            Date date = panel.NgaySinh.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");           
            String formattedDate = dateFormat.format(date);
            KhachHang newkh = new KhachHang();
            newkh.setName(tenHV);          
            newkh.setEmail(email);           
            newkh.setSDT(sdt);
            newkh.setBirthday(formattedDate);
            dao.insert(newkh);
            helper.DialogHelper.alert(panel, "Thêm khách hàng thành công!");
        } catch (Exception e) {
            helper.DialogHelper.alert(panel, "Đã xảy ra lỗi khi thêm khách hàng!");
            e.printStackTrace();
        }
    }

    public void setform(KhachHang kh) {
        panel.txtTenHoiVien.setText(kh.getName());
        panel.txtSDTHoiVien.setText(kh.getSDT());
        panel.txtGmailHoiVien.setText(kh.getEmail());
        Date dt = new Date();
        dt = DateHelper.toDate(kh.getBirthday(), "YYYY-MM-DD");
        panel.NgaySinh.setDate(dt);
    }
    public void fillToForm(int selectedRow) {
        KhachHang get = KhachHang.get(selectedRow);
        setform(get);
    }

//    public void fillToForm(int selectedRow) {
//        DefaultTableModel model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
//        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
//            String name = (String) model.getValueAt(selectedRow, 0);
//            String sdt = (String) model.getValueAt(selectedRow, 1);
//            String email = (String) model.getValueAt(selectedRow, 2);
//            String birthday = (String) model.getValueAt(selectedRow, 3); 
//
//            KhachHang kh = new KhachHang();
//            kh.setName(name);
//            kh.setSDT(sdt);
//            kh.setEmail(email);
//            kh.setBirthday(birthday);
//
//            setform(kh);
//        }
//    }
    
    public void clearform(){
        panel.txtTenHoiVien.setText("");
        panel.txtSDTHoiVien.setText("");
        panel.txtGmailHoiVien.setText("");
        panel.NgaySinh.setDate(null);
    }
    
//    public void xoaMon(int selectedRow) {
//        System.out.println("xoa mon: " + selectedRow);
//        int kh_id = KhachHang.get(selectedRow).getMa_KH();
//        KhachHang newkh = new KhachHang();
//        newkh.setMa_KH(kh_id);
//        try {
//            dao.delete(newkh);
//            clearForm();
//            helper.DialogHelper.alert(panel, "Xóa Món Thành Công!");
//        } catch (Exception ex) {
//            helper.DialogHelper.alert(panel, "Xóa món thất bại!");
//            Logger.getLogger(QuanLyMonAnControl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void suaKhachHang(int selectedRow) {
        KhachHang newkh = new KhachHang();

    }
}
