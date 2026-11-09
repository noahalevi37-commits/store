package store.products;

import java.awt.Color;
import store.core.StoreEntity;
import store.core.Persistable;

/**
 * Abstract base class for all products in the store system.
 * name: noa halevi
 *
 */
public abstract class Product implements StoreEntity, PricedItem, Persistable, StockManageable {
    protected String name;
    protected double price;
    protected int stock;
    protected String description;
    protected Category category;
    protected Color color;
    protected String imagePath;


    /**
     * Standard constructor for creating a new product.
     */
    protected Product() {
    }
    protected Product(String name, double price, int stock, String description, Category category, Color color) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
        this.color = color;
        this.imagePath = "";
    }

    /**
     * Constructor for loading products from a file (CSV).
     */
    public Product(String name, double price, int stock, String description, String categoryName, String imagePath) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = Category.valueOf(categoryName.toUpperCase());
        this.imagePath = imagePath;
        this.color = Color.BLACK;
    }

    @Override
    public String getDisplayName() { return name; }

    @Override
    public String getDisplayDetails() { return description; }

    @Override
    public double getPrice() { return price; }

    @Override
    public synchronized boolean setPrice(double price) {
        if (price < 0) return false;
        this.price = price;
        return true;
    }

    public synchronized int getStock() { return stock; }
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public synchronized boolean increaseStock(int amount) {
        if (amount <= 0) return false;
        this.stock += amount;
        return true;
    }

    @Override
    public synchronized boolean decreaseStock(int amount) {
        if (amount < 0 || amount > stock) return false;
        this.stock -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Product: " + name + " (Price: " + price + ", Stock: " + stock + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Product p) {
            return this.category == p.category && this.name.equals(p.name);
        }
        return false;
    }

    // Getters
    public Category getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getName() { return name; }

    @Override
    public void saveToFile(String path) {}
}