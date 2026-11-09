package store.gui.view;

import javax.swing.*;
import java.awt.*;

/**
 * main window of the store application.
 * this class is responsible only for gui.
 * it shows the product list and buttons.
 *
 * no business logic is handled here.
 *
 * name: noa halevi
 *
 */
public class StoreFrame extends JFrame {

    /** list that shows product names */
    private JList<String> productList;

    /** button to add selected product to cart */
    private JButton addToCartButton;

    /**
     * constructor.
     * builds the main store window and initializes components.
     */
    public StoreFrame() {
        setTitle("Online Store");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productList = new JList<>();
        addToCartButton = new JButton("Add to cart");


        setLayout(new BorderLayout());
        add(new JScrollPane(productList), BorderLayout.CENTER);
        add(addToCartButton, BorderLayout.SOUTH);
    }

    /**
     * updates the product list shown in the gui.
     *
     *  products array of product names to display
     */
    public void setProducts(String[] products) {
        productList.setListData(products);
    }

    /**
     * returns the product list component.
     *
     * product list
     */
    public JList<String> getProductList() {
        return productList;
    }

    /**
     * returns the add to cart button.
     *
     * add to cart button
     */
    public JButton getAddToCartButton() {
        return addToCartButton;
    }
}
