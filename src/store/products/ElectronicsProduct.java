package store.products;
/**
 * In this class I added the extra fields for electronics (warranty and brand).
 *my name is noa halevi
 */
import java.awt.Color;
public class ElectronicsProduct extends Product {
    private int warrantMonths;
    private String brand;
    /**
     * constructor for ElectronicsProduct.
     * calls the Product constructor and then saves warranty and brand.
     */
    public ElectronicsProduct(String name, double price, int stock, String description, Category category, Color color, int warrantMonths, String brand) {
        super(name, price, stock, description, category, color);
        this.brand = brand;
        if (warrantMonths <= 0) {
            this.warrantMonths = 12;  // default value if invalid
        } else {
            this.warrantMonths = warrantMonths;
        }

        this.brand = brand;
    }
    /**
     * returns a string with the product info and the electronics details.
     */
    @Override
    public String toString() {
        // keeping my simple style
        return super.toString() + "warrantMonths" + ";" + warrantMonths + ";" + brand;
    }
    //getters
    public int getWarrantMonths() {
        return warrantMonths;
    }
    public String getBrand() {
        return brand;
    }
}
