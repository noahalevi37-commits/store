package store.orders;
/**
 * In this class I made the order object.
 * It keeps the items, total price and the status of the order.
 * I used deep copy for the items list
 * The methods just move the order in the right status flow.
 * my name is noa halevi
 */
import store.cart.CartItem;
import store.core.Persistable;

import java.util.ArrayList;
import java.util.List;

public class Order implements Persistable {

    private int orderId;
    private List<CartItem> items;
    private double totalAmount;
    private OrderStatus status;

    /**
     * constructor for Order.
     * gets cart items and total amount, makes deep copy and starts status as NEW.
     */
    public Order(List<CartItem> items, double totalAmount) {
        this.totalAmount = totalAmount;
        this.status = OrderStatus.NEW;
        this.orderId = 0;

        this.items = new ArrayList<>();
        for (CartItem c : items) {
            this.items.add(new CartItem(c.getProduct(), c.getQuantity()));
        }
    }

    @Override
    public void saveToFile(String path) {
        // not implemented because the HW said not to
    }

    /**
     * sets the order to PAID.
     */
    public boolean pay() {
        status = OrderStatus.PAID;
        return true;
    }

    /**
     * sets the order to SHIPPED only if it was PAID.
     */
    public boolean ship() {
        if (status == OrderStatus.PAID) {
            status = OrderStatus.SHIPPED;
            return true;
        }
        return false;
    }

    /**
     * sets the order to DELIVERED only if it was SHIPPED.
     */
    public boolean deliver() {
        if (status == OrderStatus.SHIPPED) {
            status = OrderStatus.DELIVERED;
            return true;
        }
        return false;
    }

    /**
     * compares orders by ID only.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Order oi) {
            return this.orderId == oi.orderId;
        }
        return false;
    }

    /**
     * returns order details as string.
     */
    @Override
    public String toString() {
        return "Number of order: " + orderId +
                "\nPrice: " + totalAmount +
                "\nIn status: " + status + ".";
    }

    /**
     * sets the id for the order (used by store engine).
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /* ===== FIX for ex3 (thread-safety) ===== */

    /**
     * returns a copy of items list so external code will not change the order.
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items); // FIX: return copy
    }



    public int getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
