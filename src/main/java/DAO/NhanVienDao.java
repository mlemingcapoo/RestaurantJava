/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import helper.DBHelper;
import helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import util.SQLThread;

public class NhanVienDao extends SQL<NhanVien, String> {
      String SELECT_ALL_SQL = "SELECT * FROM User";
    String INSERT_SQL ="CALL ThemNhanVien(?,?,?,?)";
     String TIMKIEM_SQL = "SELECT * FROM `User` WHERE UserName like ?";

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                NhanVien newNhanVien = new NhanVien();
                newNhanVien.setUser(rs.getString(2));
                newNhanVien.setPass(rs.getString(3));
                newNhanVien.setRole(rs.getBoolean(4));
                newNhanVien.setIsLooked(rs.getBoolean(5));
                list.add(newNhanVien);
            }
        }catch(Exception e){
//           DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. Trying to reconnect...");
            new SQLThread().main(null);
        }
          return list;
    }
public void them(NhanVien nhanvien) throws Exception{
        DBHelper.executeProc("ThemNguoiDung", nhanvien.getUser(),nhanvien.getPass(),nhanvien.isRole(),nhanvien.isIsLooked());
}
public  void sua(NhanVien nhanvien)throws Exception{
    DBHelper.executeProc("SuaThongTinNhanVien",nhanvien.getMaNV(),nhanvien.getUser(),nhanvien.getPass(),nhanvien.isRole(),nhanvien.isIsLooked() );
}

    public List<NhanVien> searchByNameAndType(String User) {
        return selectBySQL(TIMKIEM_SQL,User);
    }

    public void delete(NhanVien nhanvien) throws Exception {
        DBHelper.executeProc("XoaNhanVienTheoID",nhanvien.getMaNV());
    }
    }
   
                
    //@Override
//    public List<NhanVien> selectAll() {
//        return this.selectBySQL(SELECT_ALL_SQL);
//}
//     @Override
//    protected List<NhanVien> selectBySQL(String sql, Object... args) {
//        List<NhanVien> list = new ArrayList<>();
//        try{
//            ResultSet rs = DBHelper.query(sql, args);
//            while (rs.next()) {
//                NhanVien newNhanVien = new NhanVien();
//                newNhanVien.setName(rs.getString(2));
//                newNhanVien.setSDT(rs.getString(3));
//                newNhanVien.setCccd(rs.getString(5));                
//                newNhanVien.setBirthday(rs.getInt(4));
//                list.add(newNhanVIen);
//            }
//        }
//    }

