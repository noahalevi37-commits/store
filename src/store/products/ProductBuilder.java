package store.products;

import java.awt.Color;

/**
 * Builder class for creating Product objects.
 *
 * Implements the Builder design pattern.
 * Allows step-by-step construction of Product instances.
 *
 * author Noa Halevi
 *
 */
public class ProductBuilder {

    private Product product;

    /**
     * Initializes the builder with a concrete Product instance.
     */
    public ProductBuilder() {
        product = new SimpleProduct();
    }

    public ProductBuilder setName(String name) {
        product.name = name;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        product.price = price;
        return this;
    }

    public ProductBuilder setStock(int stock) {
        product.stock = stock;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        product.description = description;
        return this;
    }

    public ProductBuilder setCategory(Category category) {
        product.category = category;
        return this;
    }

    public ProductBuilder setColor(Color color) {
        product.color = color;
        return this;
    }

    public ProductBuilder setImagePath(String imagePath) {
        product.imagePath = imagePath;
        return this;
    }

    /**
     * Finalizes and returns the constructed Product.
     *
     * @return fully built Product instance
     */
    public Product build() {
        return product;
    }
}
