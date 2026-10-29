
package com.pluralsight;

import java.util.Scanner;

public class UserInterface {

    private Store store;
    private Scanner scanner;

    public UserInterface() {
        store = new Store();
        scanner = new Scanner(System.in);
    }

    public void displayHomeScreen() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== ONLINE STORE =====");
            System.out.println("1) Display Products");
            System.out.println("2) Display Cart");
            System.out.println("3) Exit");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    displayProducts();
                    break;

                case "2":
                    displayCart();
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for visiting!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayProducts() {

        boolean shopping = true;

        while (shopping) {

            System.out.println("\n===== PRODUCTS =====");

            for (Product product : store.getProducts()) {
                System.out.println(product);
            }

            System.out.println("\n1) Add Product to Cart");
            System.out.println("2) Search Product by SKU");
            System.out.println("3) Back to Home");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addProduct();
                    break;

                case "2":
                    searchProduct();
                    break;

                case "3":
                    shopping = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addProduct() {

        System.out.print("Enter product SKU: ");
        String sku = scanner.nextLine();

        Product product = store.searchProduct(sku);

        if (product != null) {

            store.addToCart(product);

            System.out.println("Product added to cart!");

        } else {

            System.out.println("Product not found.");
        }
    }

    private void searchProduct() {

        System.out.print("Enter product SKU to search: ");

        String sku = scanner.nextLine();

        Product product = store.searchProduct(sku);

        if (product != null) {

            System.out.println("\nProduct Found:");
            System.out.println(product);

        } else {

            System.out.println("Product not found.");
        }
    }

    private void displayCart() {

        boolean viewingCart = true;

        while (viewingCart) {

            System.out.println("\n===== SHOPPING CART =====");

            if (store.getCart().isEmpty()) {

                System.out.println("Your cart is empty.");

            } else {

                int number = 1;

                for (CartItem item : store.getCart()) {

                    System.out.println(
                            number + ") " +
                                    item.getProduct().getProductName() +
                                    " - $" +
                                    String.format("%.2f",
                                            item.getProduct().getPrice())
                    );

                    number++;
                }

                System.out.println(
                        "Total: $" +
                                String.format("%.2f", store.getCartTotal())
                );
            }

            System.out.println("\n1) Checkout");
            System.out.println("2) Back to Home");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    if (store.getCart().isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        checkout();
                    }

                    break;

                case "2":
                    viewingCart = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void checkout() {

        double total = store.getCartTotal();

        System.out.println("\n===== CHECKOUT =====");

        System.out.println(
                "Total Due: $" + String.format("%.2f", total)
        );

        System.out.print("Enter cash payment: $");

        String paymentInput = scanner.nextLine();

        double payment;

        try {

            payment = Double.parseDouble(paymentInput);

            if (!Double.isFinite(payment) || payment < 0) {
                System.out.println("Please enter a valid payment.");
                return;
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid payment amount.");
            return;
        }

        if (payment < total) {

            System.out.println("Not enough money.");
            System.out.println("Please try checkout again.");

            return;
        }

        double change = payment - total;

        printReceipt(change);

        store.clearCart();

        System.out.println("\nShopping cart cleared.");
        System.out.println("Returning to home screen.");
    }

    private void printReceipt(double change) {

        System.out.println("\n===== SALES RECEIPT =====");

        for (CartItem item : store.getCart()) {

            Product product = item.getProduct();

            System.out.printf(
                    "%s | %s | $%.2f%n",
                    product.getSku(),
                    product.getProductName(),
                    product.getPrice()
            );
        }

        System.out.println("-------------------------");

        System.out.printf("Total: $%.2f%n", store.getCartTotal());

        System.out.printf("Change Given: $%.2f%n", change);

        System.out.println("=========================");
        System.out.println("Thank you for shopping!");
    }
}
