package DAO;

import helper.DBHelper;
import helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Food;
import model.FoodType;

/**
 *
 * @author capoo
 */
public class FoodDAO
        extends SQL<Food, String> {

//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";
//    String DELETE_SQL = "DELETE FROM Food WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Food";
    String SELECT_ALL_SQL_EXCEPT = "SELECT * FROM Food WHERE isLocked = 0";
    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";
    String INSERT_SQL = "CALL ThemMonAn(?,?,?,?,?)";
    String UPDATEMONAN_SQL = "CALL CapNhatMonAn(?,?,?,?,?,?)";
    String DELETE_SQL = "CALL XoaMonAn(?)";
    String TIMKIEM_SQL = "SELECT * FROM `Food` WHERE name like ? and type_ID = ?";
    String TIMKIEM_SQL2 = "SELECT * FROM `Food` WHERE name like ? and type_ID like ?";

    @Override
    public List<Food> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    
    
    public List<Food> selectAllUnlocked() {
        return this.selectBySQL(SELECT_ALL_SQL_EXCEPT);

    }

  


    @Override
    protected List<Food> selectBySQL(String sql, Object... args) {
        List<Food> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                Food newFood = new Food();
                newFood.setDish_ID(rs.getInt(1));
                newFood.setName(rs.getString(2));
                newFood.setPrice(rs.getBigDecimal(3));
                newFood.setType(rs.getInt(4));
                newFood.setIsLocked(rs.getBoolean(5));
                newFood.setImg(rs.getString(6));
                list.add(newFood);
            }
//            System.out.println("dish name at index 0: " + list.get(0).getName());;
        } catch (Exception e) {
            System.out.println("line 48 FoodDAO: " + e.getMessage());
        }

        return list;
    }

    public void insert(Food food) throws Exception {
//        DBHelper.executeProc(INSERT_SQL,);
        DBHelper.executeProc("ThemMonAn", food.getName(), food.getPrice(), food.getType(), food.isIsLocked(), food.getImg());
    }

    public void update(Food food) throws Exception {
//        DBHelper.executeProc(INSERT_SQL,);
        DBHelper.executeProc("CapNhatMonAn", food.getDish_ID(), food.getName(), food.getPrice(), food.getType(), food.isIsLocked(), food.getImg());
    }

    public void delete(Food food) throws Exception {
        DBHelper.executeProc("XoaMonAn", food.getDish_ID());
    }

    public List<Food> searchByNameAndType(String name, int type) throws Exception {
        List<Food> selectBySQL;
        if (type == 0) {
            selectBySQL = selectBySQL(TIMKIEM_SQL2, name, "%");
        } else {
            selectBySQL = selectBySQL(TIMKIEM_SQL, name, type);

        }

        return selectBySQL;

    }

    public List<FoodType> getTypeNameList() {
        String typeName[] = null;
        List<FoodType> list = new ArrayList<>();
        try {
            ResultSet rs = DBHelper.query("SELECT * FROM FoodType");
            while (rs.next()) {
                FoodType type = new FoodType();
                type.setType_id(rs.getInt(1));
                type.setType(rs.getString(2));
                list.add(type);
            }
        } catch (Exception e) {
            System.out.println("line 79 FOODDAO: " + e.getMessage());
        }
        return list;
    }

    public void setStatus(int dish_ID, int status) {
        try {
            DialogHelper.alert(null, dish_ID+" "+status);
            ResultSet rs = DBHelper.query("UPDATE `Food` SET isLocked = ? WHERE dish_ID = ?", status,dish_ID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
