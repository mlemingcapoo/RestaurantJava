package DAO;

import helper.DBHelper;
import model.ThongKe;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao extends SQL<ThongKe, String> {

    public void insert(ThongKe voucher) throws Exception {
        // Your implementation for inserting data goes here
    }

    @Override
    public List<ThongKe> selectAll() {
        return doSelect("DoanhThuNgay");
    }

    public List<ThongKe> selectByWeek() {
        return doSelect("DoanhThuTuan");
    }

    public List<ThongKe> selectByThang() {
        return doSelect("DoangThuThang");
    }

    public List<ThongKe> DemSoLuongKhachHang() {
        return doSelect("DemSoLuongKhachHang");
    }

    private List<ThongKe> doSelect(String storedProcedure) {
        List<ThongKe> list = new ArrayList<>();
        try {
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String sqlQuery = "CALL hnguyenmanhidvn_DuAn1." + storedProcedure + "('" + formattedDate + "')";
            System.out.println("SQL Query: " + sqlQuery);

            ResultSet rs = DBHelper.query(sqlQuery);

            while (rs.next()) {
                ThongKe newThongKe = new ThongKe();
                newThongKe.setNgay(rs.getString(1));
                newThongKe.setTong(rs.getFloat(2));
                list.add(newThongKe);
            }
        } catch (Exception e) {
            System.out.println("Error executing stored procedure: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected List<ThongKe> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void DoanhThuNgay(ThongKe ThongKe) throws Exception {
        DBHelper.executeProc("DoanhThuNgay", ThongKe.getNgay());
    }

    public void DoanhThuTuan(ThongKe ThongKe) throws Exception {
        DBHelper.executeProc("DoanhThuTuan", ThongKe.getNgay());
    }

    public void DoangThuThang(ThongKe ThongKe) throws Exception {
        DBHelper.executeProc("DoangThuThang", ThongKe.getNgay());
    }
}
