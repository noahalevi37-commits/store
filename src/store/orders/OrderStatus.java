package store.orders;
/**
 * Represents the different possible states of an order
 * in the store system.
 * NEW      - order was created but not processed yet
 * PAID     - payment was completed successfully
 * SHIPPED  - order was sent to delivery
 * DELIVERED - order arrived to the customer
 *my name is noa halevi
 */
public enum OrderStatus {
    NEW,
    PAID,
    SHIPPED,
    DELIVERED;
}
