

package model;

/**
 *
 * @author capoo
 */
public class orderedDishes {

    private int Dish_ID;
    private String foodName;
    private Double price;
    private int quantity;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDish_ID() {
        return Dish_ID;
    }

    public void setDish_ID(int Dish_ID) {
        this.Dish_ID = Dish_ID;
    }
}
