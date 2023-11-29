package control;

import DAO.HoiVienDAO;
import frame.QuanLyHoiVienJPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import helper.DateHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuanLyHoiVienControl {

    private static QuanLyHoiVienJPanel panel;
    DefaultTableModel HoiVienModel = new DefaultTableModel();
    static List<KhachHang> KhachHang = new ArrayList<>();
    HoiVienDAO dao = new HoiVienDAO();

    public void init(QuanLyHoiVienJPanel panel) {
        QuanLyHoiVienControl.panel = panel;
        refresh();
    }

    public void refresh() {
        getHV();
        fillTable();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
        model.setRowCount(0);
        for (KhachHang kh : KhachHang) {
            Object[] row = new Object[]{kh.getName(), kh.getSDT(), kh.getEmail(), kh.getDiem(), kh.getBirthday()};
            model.addRow(row);
        }
    }

    public void getHV() {
        KhachHang.clear();
        try {
            KhachHang = dao.selectAll();
//            System.out.println("số món có trong menu:" + KhachHang.size());
        } catch (Exception e) {
        }
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
        DefaultTableModel model = (DefaultTableModel) panel.tblQuanLyHoiVien.getModel();
        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            String name = (String) model.getValueAt(selectedRow, 0);
            String sdt = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String birthday = (String) model.getValueAt(selectedRow, 3); 

            KhachHang kh = new KhachHang();
            kh.setName(name);
            kh.setSDT(sdt);
            kh.setEmail(email);
            kh.setBirthday(birthday);

            setform(kh);
        }
    }

    public void suaKhachHang(int selectedRow) {
        KhachHang newkh = new KhachHang();

    }
}
