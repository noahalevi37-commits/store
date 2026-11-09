package store.shipping;

import store.orders.Order;

/**
 * ShippingProvider defines a common interface for shipping services.
 *
 * This interface allows the store system to work with different
 * shipping providers in a uniform way.
 *
 * It is used as part of the Adapter design pattern.
 *
 * author Noa Halevi
 *
 */
public interface ShippingProvider {

    /**
     * Ships the given order using an external shipping service.
     *
     * @param order the order to be shipped
     */
    void shipOrder(Order order);
}
