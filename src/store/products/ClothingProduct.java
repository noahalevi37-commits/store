package store.products;
/**
 * In this class I added a field for clothes (size).
 * I just extended Product and put the new info that is needed for clothing.
 * I tried to keep the code simple like the other classes so it fits the HW.
 *my name is noa halevi
 */
import java.awt.Color;
public class ClothingProduct extends Product {
    private String size;
    /**
     * constructor for ClothingProduct.
     * calls the super constructor and then saves the size of the clothing.
     */
    public ClothingProduct(String name, double price, int stock, String description, Category category, Color color, String size) {
        super(name, price, stock, description, category, color);
        this.size = size;
    }
    /**
     * returns a string with the product info and the size of the clothing.
     */
    @Override
    public String toString() {
        // keeping my own simple style
        return super.toString() + "the size is?" + size;
    }
    //getters for the futer
    public String getSize() {
        return size;
    }
}
