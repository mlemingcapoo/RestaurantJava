package model;

/**
 *
 * @author capoo
 */
public class OrderDetails {

    
    private int quantity;
    private int order_ID;
    private int dish_ID;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public int getDish_ID() {
        return dish_ID;
    }

    public void setDish_ID(int dish_ID) {
        this.dish_ID = dish_ID;
    }
}
