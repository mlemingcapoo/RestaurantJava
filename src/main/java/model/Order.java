

package model;

import java.sql.Date;

/**
 *
 * @author capoo
 */
public class Order {
    private int order_ID;
    private int bill_ID;
    private String note;
    private boolean isCompleted;
    private Date dateCreated;

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public int getBill_ID() {
        return bill_ID;
    }

    public void setBill_ID(int bill_ID) {
        this.bill_ID = bill_ID;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDateCreated(Date date) {
        this.dateCreated = date;
    }
    
    public Date getDateCreated(){
        return dateCreated;
    } 
    
     
}
