package store.shipping;

import store.orders.Order;

/**
 * Adapter class that allows the system to use the FastShipAPI
 * through the ShippingProvider interface.
 *
 * This class implements the Adapter design pattern by translating
 * calls from the system's expected interface (ShippingProvider)
 * to the external FastShipAPI.
 *
 * author Noa Halevi
 *
 */
public class FastShipAdapter implements ShippingProvider {

    private FastShipAPI api = new FastShipAPI();

    /**
     * Ships the given order by adapting it to the FastShipAPI format.
     *
     * @param order the order to be shipped
     */
    @Override
    public void shipOrder(Order order) {
        api.executeDelivery(order.toString());
    }
}
