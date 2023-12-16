/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import util.SQLThread;

public class NhanVienDao extends SQL<NhanVien, String> {
      String SELECT_ALL_SQL = "SELECT * FROM User";
    String INSERT_SQL ="CALL ThemNhanVien(?,?,?,?)";
     String TIMKIEM_SQL = "SELECT * FROM `User` WHERE UserName LIKE ? AND role = ? ";

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
                newNhanVien.setMaNV(rs.getInt(1));
                newNhanVien.setUser(rs.getString(2));
                newNhanVien.setPass(rs.getString(3));
                newNhanVien.setRole(rs.getBoolean(4));
                newNhanVien.setIsLooked(rs.getBoolean(5));
                newNhanVien.setAddress(rs.getString(6));
                 newNhanVien.setName(rs.getString(7));
                  newNhanVien.setBirthday(rs.getString(8));
                   newNhanVien.setSodienthoai(rs.getString(9));
                    newNhanVien.setCccd(rs.getString(10));
                     newNhanVien.setImg(rs.getString(11));
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
        DBHelper.executeProc("AddNhanVien", nhanvien.getUser(),nhanvien.getPass(),nhanvien.isRole(),nhanvien.isIsLooked(),nhanvien.getAddress(),nhanvien.getName(),nhanvien.getBirthday(),nhanvien.getSodienthoai(),nhanvien.getCccd(),nhanvien.getImg());
}
public  void sua(NhanVien nhanvien)throws Exception{
    DBHelper.executeProc("UpdateNhanVien",nhanvien.getMaNV(),nhanvien.getUser(),nhanvien.getPass(),nhanvien.isRole(),nhanvien.isIsLooked(),nhanvien.getAddress(),nhanvien.getName(),nhanvien.getBirthday(),nhanvien.getSodienthoai(),nhanvien.getCccd(),nhanvien.getImg() );
}

    public List<NhanVien> searchByNameAndType(String user,String role) {
        return selectBySQL(TIMKIEM_SQL,user,role);
    }

    public void delete(NhanVien nhanvien) throws Exception {
        DBHelper.executeProc("XoaNguoiDungVaThongTin",nhanvien.getMaNV());
    }

    public List<NhanVien> searchNameByID(int user_ID) {
          List<NhanVien> selectBySQL = selectBySQL("SELECT * FROM NhanVien WHERE user_ID = ?",user_ID);
          
        return selectBySQL;
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

