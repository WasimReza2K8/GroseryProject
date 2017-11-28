package codeartist.com.groseryshop.datamodel;

/**
 * Created by bjit-16 on 11/28/17.
 */

public class ProductDataModel {
    private String productName;
    private float price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;
}
