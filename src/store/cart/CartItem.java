package store.cart;
import store.products.Product;
/**
 * This is the item inside the cart.
 * I just keep the product and how many of it.
 * The class is simple because the cart does most of the work.
 * my name is noa halevi
 */
public class CartItem {
    private Product product;
    private int quantity;
    /**
     * constructor for CartItem.
     * gets a product and starting quantity.
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;  // fix: was 0 before
    }
    /**
     * sets the quantity if it's positive.
     */
    public boolean setQuantity(int q) {
        if (q > 0) {
            this.quantity = q;
            return true;
        }
        return false;
    }
    /** returns the quantity. */
    public int getQuantity() {
        return quantity;
    }
    /** * returns total price for this cart item.
     * Added public so it can be accessed from GUI package.
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
    /** returns the product. */
    public Product getProduct() {
        return product;
    }
    /**
     * equals compares by product (same product means same cart item).
     */
    public boolean equals(Object o) {
        if (o instanceof CartItem ci) {
            return this.getProduct().equals(ci.getProduct());
        }
        return false;
    }
}