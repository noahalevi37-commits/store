package store.products;
/**
 * Interface for managing stock of a product.
 * Provides methods to read stock value and modify it safely.
 *my name is noa halevi
 */
public interface StockManageable {
    int getStock();
/**
 * Increases the stock by a positive amount.
 */
    boolean increaseStock(int amount);
/**
 * Decreases the stock by a positive amount,
 * and only if there is enough stock available.
 */
    boolean decreaseStock(int amount);
}
