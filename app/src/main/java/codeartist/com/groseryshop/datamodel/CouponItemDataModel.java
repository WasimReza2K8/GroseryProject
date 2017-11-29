package codeartist.com.groseryshop.datamodel;

/**
 * Created by bjit-16 on 11/29/17.
 */

public class CouponItemDataModel {
    private int id;
    private int couponNumber;
    private String item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCouponNmber() {
        return couponNumber;
    }

    public void setCouponNmber(int couponNmber) {
        this.couponNumber = couponNmber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


}
