
package com.pluralsight;

import java.util.ArrayList;

public class Store {

    private ArrayList<Product> products;
    private ArrayList<CartItem> cart;

    public Store() {

        products = FileLoader.loadProducts(
                "src/main/resources/products.csv"
        );

        cart = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public Product searchProduct(String sku) {

        for (Product product : products) {

            if (product.getSku().equalsIgnoreCase(sku)) {
                return product;
            }
        }

        return null;
    }

    public void addToCart(Product product) {

        if (product != null) {
            cart.add(new CartItem(product));
        }
    }

    public double getCartTotal() {

        double total = 0;

        for (CartItem item : cart) {
            total += item.getProduct().getPrice();
        }

        return total;
    }

    public void clearCart() {
        cart.clear();
    }
}