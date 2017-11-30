package codeartist.com.groseryshop.datamodel;

import java.util.ArrayList;

/**
 * Created by bjit-16 on 11/30/17.
 */

public class CouponDataModel {
    private int discount;
    private int id;
    private int couponNumber;
    private String item;
    private boolean isDeletable;

    public ArrayList<String> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<String> itemList) {
        this.itemList = itemList;
    }

    private ArrayList<String> itemList;


    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }



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

    public int getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(int couponNumber) {
        this.couponNumber = couponNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
