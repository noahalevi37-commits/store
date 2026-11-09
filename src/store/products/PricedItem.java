package store.products;
/**
 * Represents something that has a price.
 *my name is noa halevi
 */
public interface PricedItem {
/**
 * Sets the price if it is positive.
 */
   double getPrice();
   boolean setPrice(double price);
}
