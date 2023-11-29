package DAO;

import helper.DBHelper;
import helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import util.SQLThread;

public class HoiVienDAO extends SQL<KhachHang, String> {

    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String INSERT_SQL ="CALL ThemKhachHang(?,?,?,?)";

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                KhachHang newKhachHang = new KhachHang();
                newKhachHang.setName(rs.getString(2));
                newKhachHang.setSDT(rs.getString(3));
                newKhachHang.setEmail(rs.getString(5));                
                newKhachHang.setDiem(rs.getInt(4));
                newKhachHang.setBirthday(rs.getString(6));
                list.add(newKhachHang);
            }
//            System.out.println("Ma_KH at index 0: " + list.get(0).getMa_KH());;
        } catch (Exception e) {
            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. Trying to reconnect...");
            new SQLThread().main(null);
//            throw new RuntimeException(e);
        }
        return list;
    }
    
    public void insert(KhachHang kh) throws Exception {
        DBHelper.executeProc("ThemKhachHang", kh.getName(), kh.getSDT(), kh.getEmail(),kh.getBirthday());
    }
   public void update(KhachHang kh) throws Exception {
        DBHelper.executeProc("SuaKhachHang", kh.getSDT(),kh.getName(), kh.getEmail(), kh.getBirthday());
    }
    
}
