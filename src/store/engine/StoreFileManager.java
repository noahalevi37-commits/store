package store.engine;

import store.products.Product;
import store.products.ProductFactory;
import store.products.Category;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Handles loading and saving products to files.
 *
 * This class centralizes all file operations related to products,
 * so file access is done in one controlled place.
 * Synchronization is used to prevent concurrent read/write conflicts.
 *
 *
 * This class was written as part of exercise 3.
 *
 * author Noa Halevi
 *
 */
public class StoreFileManager {

    /**
     * Lock object used to synchronize file operations.
     */
    private static final Object FILE_LOCK = new Object();

    /**
     * Loads products from a CSV file into the store engine.
     *
     * Each valid line in the file represents a single product.
     * Invalid or malformed lines are skipped.
     *
     *  engine the store engine to load products into
     *  file   the file containing the product data
     *  IOException if the file cannot be read
     */
    public static void loadProducts(StoreEngine engine, File file) throws IOException {
        if (engine == null || file == null) return;

        synchronized (FILE_LOCK) {
            Scanner sc = new Scanner(file);

            // skip header line
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line == null || line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                if (data.length < 6) continue;

                try {
                    Category category = Category.valueOf(data[4].trim().toUpperCase());
                    Product p = ProductFactory.createSimpleProduct(
                            data[0],
                            Double.parseDouble(data[1]),
                            Integer.parseInt(data[2]),
                            data[3],
                            category,
                            data[5]
                    );

                    engine.addProduct(p);
                } catch (Exception ignore) {
                    // skip invalid product line
                }
            }

            sc.close();
        }
    }

    /**
     * Saves all products from the store engine into a CSV file.
     *
     * The file is overwritten on each save operation.
     *
     *  engine the store engine containing the products
     *  file   the destination file
     *  IOException if the file cannot be written
     */
    public static void saveProducts(StoreEngine engine, File file) throws IOException {
        if (engine == null || file == null) return;

        synchronized (FILE_LOCK) {
            PrintWriter pw = new PrintWriter(file);

            // CSV header
            pw.println("name,price,stock,description,category,imagePath");

            for (Product p : engine.getProducts()) {
                if (p == null) continue;

                pw.println(
                        p.getName() + "," +
                                p.getPrice() + "," +
                                p.getStock() + "," +
                                p.getDescription() + "," +
                                p.getCategory() + "," +
                                p.getImagePath()
                );
            }

            pw.close();
        }
    }
}
