package store.core;

import store.cart.Cart;
import store.engine.StoreEngine;
import store.orders.Order;
import store.products.Product;

import java.util.List;
import java.util.ArrayList;

/**
 * customer of the store, holds a cart and orders history.
 * very simple like in the hw.
 * my name is noa halevi
 */
public class Costomer extends User {

    private Cart cart;
    private List<Order> orderHistory;

    /** build customer with name email and cart. constructor */
    public Costomer(String username, String email, Cart cart) {
        super(username, email);
        this.cart = cart;
        this.orderHistory = new ArrayList<>();
    }

    /** add product to cart if stock ok. */
    public boolean addToCart(Product p, int quantity) {
        if (p == null) return false;
        if (quantity <= 0) return false;
        if (p.getStock() < quantity) return false;
        return cart.addItem(p, quantity);
    }

    /** remove product from cart. */
    public boolean removeFromCart(Product p) {
        if (p == null) return false;
        return cart.removeItem(p);
    }

    /** checkout just clears the cart. engine makes actual order. */
    public boolean checkout(StoreEngine engine) {
        if (engine == null) return false;
        if (cart == null) return false;

        Order o = engine.createOrderFromCart(cart);
        if (o == null) return false;

        addOrderToHistory(o);
        cart.clearCart();

        return true;
    }

    /** add order to history. */
    public void addOrderToHistory(Order o) {
        if (o != null) {
            orderHistory.add(o);
        }
    }

    @Override
    public String toString() {
        return "Costomer{name='" + getUsername() +
                "', email='" + getEmil() +
                "', orders=" + orderHistory.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Costomer)) return false;
        Costomer other = (Costomer) o;
        return this.getUsername().equals(other.getUsername());
    }

    /** getters */
    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory); // FIX: return copy (thread-safety)
    }
}
