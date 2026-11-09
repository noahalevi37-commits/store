package store.products;
import java.awt.Color;

/**
 * Concrete class to allow instantiation of products from file.
 * name: noa halevi
 */
public class SimpleProduct extends Product {
    public SimpleProduct(String name, double price, int stock, String description, String categoryName, String imagePath) {
        super(name, price, stock, description, categoryName, imagePath);
    }
    public SimpleProduct() {
        super();
    }


    @Override
    public void saveToFile(String path) {}
}