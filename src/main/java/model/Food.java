

package model;

/**
 *
 * @author capoo
 */
public class Food {
    private int dish_ID;
    private String name;
    private float price;
    private int type;
    private boolean isLocked;
    private String img;
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    

    public int getDish_ID() {
        return dish_ID;
    }

    public void setDish_ID(int dish_ID) {
        this.dish_ID = dish_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

}
