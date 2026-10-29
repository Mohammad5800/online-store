
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileLoader {

    public static ArrayList<Product> loadProducts(String fileName) {

        ArrayList<Product> products = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            // Skip CSV header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                if (parts.length != 4) {
                    continue;
                }

                String sku = parts[0].trim();
                String productName = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                String department = parts[3].trim();

                Product product = new Product(
                        sku,
                        productName,
                        price,
                        department
                );

                products.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }

        return products;
    }
}
