package store.discount;

/**
 * PercentageDiscount applies a percentage-based discount.
 */
public class PercentageDiscount implements DiscountStrategy {

    private double percentage; // e.g. 10 = 10%

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - percentage / 100.0);
    }
}
