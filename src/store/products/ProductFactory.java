package store.products;

/**
 * Factory class responsible for creating Product objects.
 *
 * This class implements the Factory Method design pattern.
 * All product creation in the system must be done through this factory,
 * so the creation logic is centralized and hidden from the rest of the system.
 *
 * The factory uses ProductBuilder to construct product instances.
 *
 * author Noa Halevi
 *
 */
public class ProductFactory {

    /**
     * Creates a simple product using the ProductBuilder.
     *
     * @param name  the name of the product
     * @param price the price of the product
     * @return a new Product instance
     */
    public static Product createSimpleProduct(
            String name,
            double price,
            int stock,
            String description,
            Category category,
            String imagePath
    ) {
        return new ProductBuilder()
                .setName(name)
                .setPrice(price)
                .setStock(stock)
                .setDescription(description)
                .setCategory(category)
                .setImagePath(imagePath)
                .build();
    }

}
