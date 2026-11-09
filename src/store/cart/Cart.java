package store.cart;

import store.engine.StoreEngine;
import store.products.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the cart class. It keeps a list of CartItem. [cite: 23]
 * I made it simple: add items, remove items and calculate the total. [cite: 20]
 * The checkout in the customer uses clearCart(). [cite: 52]
 * * name: noa halevi, [cite: 7, 10]
 */
public class Cart {
    /** list of items in the cart */
    private List<CartItem> items;

    /**
     * constructor for Cart. makes an empty list.
     */
    public Cart() {
        items = new ArrayList<>();
    }

    /**
     * adds an item to the cart. if product already exists, updates quantity. [cite: 86]
     * * @param p the product to add
     * @param q the quantity to add
     * @return true if added successfully
     */
    public boolean addItem(Product p, int q) {
        for (CartItem i : items) {
            if (i.getProduct().equals(p)) {
                i.setQuantity(q + i.getQuantity());
                return true;
            }
        }
        CartItem ci = new CartItem(p, q);
        items.add(ci);
        return true;
    }

    /**
     * removes a product from the cart. [cite: 51, 87]
     * * @param p the product to remove
     * @return true if removed successfully
     */
    public boolean removeItem(Product p) {
        for (int i = 0; i < items.size(); i++) {
            CartItem ci = items.get(i);
            if (ci.getProduct().equals(p)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * calculates total price of all items in the cart. [cite: 50]
     * * @return total price
     */
    public double calculateTotal() {
        if (items == null) return 0;
        double sum = 0;
        for (CartItem i : items) {
            sum += i.getTotalPrice();
        }
        return StoreEngine.getInstance().getDiscountStrategy().applyDiscount(sum);
    }

    /**
     * clears all items (used in checkout). [cite: 52]
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * returns all cart items. [cite: 48]
     * * @return list of CartItem
     */
    public List<CartItem> getItems() {
        return items;
    }
}