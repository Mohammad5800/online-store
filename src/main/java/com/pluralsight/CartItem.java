package com.pluralsight;

public class CartItem {

    private Product product;

    public CartItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
