package store.products;
/**
 * I added the book fields (author, pages) and kept the toString in my own style.
 * The class extends Product like the assignment asked, with the extra book info.
 * my name is noa halevi
 */
import java.awt.Color;
public class BookProduct extends Product {
    private String author;
    private int pages;
    public BookProduct(String name, double price, int stock, String description, Category category, Color color, String author, int pages) {
        super(name, price, stock, description, category, color);
        this.author = author;
        this.pages = pages;
    }
    @Override
    /**taking all the filed and adding filds of the class*/
    public String toString() {
        return super.toString() +
                ", this is some books" +
                ", name of auter: " + author +
                ", amount of pages: " + pages;
    }
    public String getAuthor() {
        return author;
    }
    public int getPages() {
        return pages;
    }

}
