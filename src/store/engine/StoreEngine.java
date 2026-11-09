package store.engine;
/**
 * StoreEngine core logic.
 * Handles products, customers and orders.
 *
 * Implements:
 * - Singleton pattern (Double-Checked Locking)
 * - Observer pattern (Subject)
 * - Strategy pattern for discount calculation
 *
 * author Noa Halevi
 *
 */


import store.cart.Cart;
import store.cart.CartItem;
import store.core.Costomer;
import store.discount.DiscountStrategy;
import store.discount.NoDiscount;
import store.orders.Order;
import store.products.Product;

import java.util.List;
import java.util.ArrayList;
import store.core.SystemUpdatable;

public class StoreEngine {
    private static volatile StoreEngine instance;
    /**
     * Returns the single instance of StoreEngine.
     *
     * This method implements the Singleton design pattern
     * using Double-Checked Locking to ensure thread safety.
     *
     * @return the single StoreEngine instance
     */
    private DiscountStrategy discountStrategy = new NoDiscount();
    public void setDiscountStrategy(DiscountStrategy strategy) {
        if (strategy != null) {
            this.discountStrategy = strategy;
            notifyObservers();
        }

    }
    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }


    public static StoreEngine getInstance() {
        if (instance == null) {
            synchronized (StoreEngine.class) {
                if (instance == null) {
                    instance = new StoreEngine();
                }
            }
        }
        return instance;
    }


    List<Product> products;
    List<Order> allOrders;
    List<Costomer> customers;
    private int nextOrderId;
    private List<SystemUpdatable> observers = new ArrayList<>();
    /**
     * constructor for StoreEngine.
     * creates the lists and starts order id from 1.
     */
    private StoreEngine() {
        customers = new ArrayList<>();
        products = new ArrayList<>();
        allOrders = new ArrayList<>();
        nextOrderId = 1;
    }

    /**
     * adds a product to the store.
     */
    public synchronized void addProduct(Product p) {
        if (p == null) return; // FIX: small safety
        products.add(p);
        notifyObservers();
    }
    public void updateStock(Product product, int newStock) {
        product.setStock(newStock);
        notifyObservers();
    }

    public synchronized void addObserver(SystemUpdatable o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }
    public void removeObserver(SystemUpdatable observer) {

        observers.remove(observer);
    }
    public void removeProduct(Product product) {
        products.remove(product);
        notifyObservers();
    }


    private synchronized void notifyObservers() {
        for (SystemUpdatable o : observers) {
            o.update();
        }
    }



    /**
     * returns all available products.
     */
    public synchronized List<Product> getAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product p : products) {
            if (p.getStock() > 0) {
                available.add(p);
            }
        }
        return available;
    }

    /**
     * registers a customer if username is not taken.
     */
    public synchronized boolean registerCustomer(Costomer c) {
        if (c == null) return false; // FIX: safety
        for (Costomer i : customers) {
            if (i.getUsername().equals(c.getUsername())) {
                return false;
            }
        }
        customers.add(c);
        return true;
    }

    /**
     * creates an order from a cart, sets the id and saves it.
     * this method is synchronized so stock and order creation are atomic.
     */
    public synchronized Order createOrderFromCart(Cart cart) {
        List<CartItem> items = cart.getItems();
        if (items == null || items.isEmpty()) return null;

        // validate stock first (no partial changes)
        for (CartItem item : items) {
            if (item == null) continue;
            Product p = item.getProduct();
            int q = item.getQuantity();
            if (p == null || q <= 0) return null;
            if (p.getStock() < q) return null;
        }

        // decrease stock atomically
        for (CartItem item : items) {
            Product p = item.getProduct();
            int q = item.getQuantity();
            boolean ok = p.decreaseStock(q);
            if (!ok) return null;
        }

        double originalTotal = cart.calculateTotal();
        double finalTotal = discountStrategy.applyDiscount(originalTotal);
        Order o = new Order(items, finalTotal);

        o.setOrderId(nextOrderId);
        nextOrderId++;
        allOrders.add(o);
        notifyObservers();
        return o;
    }


    /**
     * Returns all products in the store.
     */
    public synchronized List<Product> getProducts() {
        return new ArrayList<>(products); // FIX: return copy
    }

    public synchronized List<Order> getAllOrders() {
        return new ArrayList<>(allOrders); // FIX: return copy
    }

    public synchronized int nextOrderId() {
        return nextOrderId++;
    }
}
