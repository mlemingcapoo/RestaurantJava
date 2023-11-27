

package DAO;

import helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Food;

/**
 *
 * @author capoo
 */
public class FoodDAO 
extends SQL<Food, String>
{

//    String INSERT_SQL = "INSERT INTO Food(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE Food SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";
//    String DELETE_SQL = "DELETE FROM Food WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Food";
    String SELECT_BY_ID_SQL = "SELECT * FROM Food WHERE FoodID = ?";
    
    @Override
    public List<Food> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
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
                newFood.setPrice(rs.getFloat(3));
                newFood.setType(rs.getString(4));
                newFood.setIsLocked(rs.getBoolean(5));
                newFood.setImg(rs.getString(6));
                list.add(newFood);
            }
            System.out.println("dish name at index 0: "+list.get(0).getName());;
        } catch (Exception e) {
//            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại kết nối mạng và bấm OK!");
            System.out.println("Connection was lost.. ");
//            new SQLThread().main(null);
//            throw new RuntimeException(e);
        }
        return list;
    }
    
}
