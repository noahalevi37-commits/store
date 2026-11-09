package store.discount;

/**
 * DiscountStrategy defines a pricing algorithm.
 *
 * This interface represents the Strategy design pattern.
 * Different implementations apply different discount logic.
 *
 * author Noa Halevi
 *
 */
public interface DiscountStrategy {

    /**
     * Applies a discount to the given price.
     *
     * @param originalPrice the original price
     * @return discounted price
     */
    double applyDiscount(double originalPrice);
}
