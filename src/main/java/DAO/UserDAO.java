    package DAO;

//package DAO;
//
//import java.util.List;
//import model.User;
//
//
//public class UserDAO 
//        extends SQL_queries_DAO<User, String>
//{
//    String INSERT_SQL = "INSERT INTO User(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
//    String UPDATE_SQL = "UPDATE User SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";
//    String DELETE_SQL = "DELETE FROM User WHERE MaNV = ?";
//    String SELECT_ALL_SQL = "SELECT * FROM User";
//    String SELECT_BY_ID_SQL = "SELECT * FROM User WHERE MaNV = ?";
//    
////    @Override
////    public void insert(User entity) {
////        DBHelper.update(INSERT_SQL, 
////                entity.getMaNV(), entity.getPassword(), entity.getFullName(), entity.getRole());
////    }
////
////    @Override
////    public void update(User entity) {
////        DBHelper.update(UPDATE_SQL, 
////                entity.getPassword(),entity.getFullName(), entity.getRole(), entity.getMaNV());
////    }
////
////    @Override
////    public void delete(String id) {
////        DBHelper.update(DELETE_SQL, id);
////    }
//
//    @Override
//    public List<User> selectAll() {
//        return this.selectBySQL(SELECT_ALL_SQL);
//    }
//
//    @Override
//    public User selectByID(String id) {
//        List<User> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
//        if(list.isEmpty()){
//            return null;
//        }
//        return list.size() > 0 ? list.get(0) : null;
//    }
//
//    @Override
//    protected List<User> selectBySQL(String sql, Object... args) {
//        List<User> list = new ArrayList<User>();
//        try {
//            ResultSet rs = DBHelper.query(sql, args);
//            while (rs.next()) {
//                User nv = new User();
//                nv.setMaNV(rs.getString("MaNV"));
//                nv.setPassword(rs.getString("MatKhau"));
//                nv.setFullName(rs.getString("HoTen"));
//                nv.setRole(rs.getBoolean("VaiTro"));
//                list.add(nv);
//            }
//            
//        } catch (Exception e) {
////            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại database!");
////            throw new RuntimeException(e);
//        }
//        return list;
//    }
//    
//}
