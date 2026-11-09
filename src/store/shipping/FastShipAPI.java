package store.shipping;

/**
 * External shipping service API.
 *
 * This class simulates a third-party shipping service ("FastShip")
 * whose interface does not match the store system's requirements.
 *
 * This class is not modified directly and is wrapped by an adapter.
 *
 * author Noa Halevi
 *
 */
public class FastShipAPI {

    /**
     * Executes the delivery using the external service.
     *
     * @param details string containing delivery details
     */
    public void executeDelivery(String details) {
        System.out.println("FastShip delivery: " + details);
    }
}
