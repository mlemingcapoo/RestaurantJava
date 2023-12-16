

package model;

/**
 *
 * @author capoo
 */
public class Bill {
    private int bill_ID;
    private int user_ID;
    private float amount;
    private String billDate;
    private String note;
    private int Ma_KH;
    private String VCode;
    private String billCode;
    private int status;
    private int order_ID;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBill_ID() {
        return bill_ID;
    }

    public void setBill_ID(int bill_ID) {
        this.bill_ID = bill_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMa_KH() {
        return Ma_KH;
    }

    public void setMa_KH(int Ma_KH) {
        this.Ma_KH = Ma_KH;
    }

    public String getVCode() {
        return VCode;
    }

    public void setVCode(String VCode) {
        this.VCode = VCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getStatusName() {
        String status="";
        switch (this.status) {
            case 0:
                status="Chưa hoàn tất";
                break;
            case 1:
                status="Đã hoàn tất";
                break;
            default:
                throw new AssertionError();
        }
        return status;
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }




}
