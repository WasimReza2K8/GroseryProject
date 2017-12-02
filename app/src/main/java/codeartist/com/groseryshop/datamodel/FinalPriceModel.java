package codeartist.com.groseryshop.datamodel;

import java.util.ArrayList;

/**
 * Created by ASUS on 02-Dec-17.
 */

public class FinalPriceModel {
    private int couponNumber;

    public ArrayList<Float> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Float> price) {
        this.price = price;
    }

    private ArrayList<Float> price = new ArrayList<>();

    public ArrayList<String> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    private ArrayList<String> selectedItems = new ArrayList<>();

    public int getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(int couponNumber) {
        this.couponNumber = couponNumber;
    }


    public ArrayList<Float> getDiscount() {
        return discount;
    }

    public void setDiscount(ArrayList<Float> discount) {
        this.discount = discount;
    }

    private ArrayList<Float> discount = new ArrayList<>();

}
