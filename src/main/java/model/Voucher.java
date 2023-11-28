

package model;

/**
 *
 * @author capoo
 */
public class Voucher {
    private int VoucherID;
    private float discountPercent;
    private String VCode;
    private String expireDate;

    public int getVoucherID() {
        return VoucherID;
    }

    public void setVoucherID(int VoucherID) {
        this.VoucherID = VoucherID;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getVCode() {
        return VCode;
    }

    public void setVCode(String VCode) {
        this.VCode = VCode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    
}
