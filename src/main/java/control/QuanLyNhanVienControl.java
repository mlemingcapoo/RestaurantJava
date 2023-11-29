

package control;

import DAO.NhanVienDao;
import frame.QuanLyNhanVienJPanel;
import frame.VoucherJPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;


/**
 *
 * @author capoo
 */
public class QuanLyNhanVienControl {
private static QuanLyNhanVienJPanel panel;
    DefaultTableModel NhanVienModel = new DefaultTableModel();
    static List<NhanVien> NhanVien = new ArrayList<>();
    NhanVienDao dao = new NhanVienDao();
    
    public void init(QuanLyNhanVienJPanel panel) {
        QuanLyNhanVienControl.panel=panel;
    }  
    public void getNV() {
    NhanVien.clear();
    NhanVien = dao.selectAll();
}
    public void themNV() {
        try {
            
             
        } catch (Exception e) {
        }
  
    }
}

