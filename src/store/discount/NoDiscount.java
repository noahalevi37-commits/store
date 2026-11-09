package store.discount;

/**
 * NoDiscount applies no discount.
 */
public class NoDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice;
    }
}
