

package helper;

import java.util.List;
import model.FoodType;

/**
 *
 * @author capoo
 */
public class FoodHelper {
public static String getTypeName(int type_ID,List<FoodType> types){
        String typeName = "";
        for (FoodType type : types) {
            if(type.getType_id()==type_ID){
                typeName=type.getType();
            }
        }
        return typeName;
    }
}
