package store.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.List;

import store.engine.StoreEngine;
import store.products.Product;
import store.products.Category;
import store.core.SystemUpdatable;

/**
 * Store window GUI.
 * Can be opened as customer or manager.
 *
 * This window also acts as an Observer in the Observer design pattern.
 * It receives updates from the StoreEngine and refreshes its view automatically.
 *
 * name: noa halevi
 *
 */
public class StoreWindow implements SystemUpdatable {

    /** Indicates whether this window is opened in manager mode */
    private final boolean managerMode;
    private static StoreWindow managerInstance;

    private JFrame frame;
    private JList<Product> productList;
    private DefaultListModel<Product> listModel;
    private JComboBox<String> categoryFilter;

    // manager buttons
    private JButton loadButton;
    private JButton saveButton;
    private JButton updateStockButton;

    // shared button
    private JButton addButton;

    // customer buttons
    private JButton viewCartButton;
    private JButton orderHistoryButton;
    private JButton discountButton;

    private List<Product> allProducts;

    /**
     * Default constructor.
     * Opens the store window in customer mode
     * and registers the window as an observer of StoreEngine.
     */
    private StoreWindow() {
        this(false);
        StoreEngine engine = StoreEngine.getInstance();
        engine.addObserver(this);
        frame = new JFrame();
        discountButton = new JButton("Set Discount");

    }
    public static StoreWindow getManagerInstance() {
        if (managerInstance == null) {
            managerInstance = new StoreWindow(true);
        } else {
            managerInstance.frame.setVisible(true);
            managerInstance.frame.setExtendedState(JFrame.NORMAL);
            managerInstance.frame.setAlwaysOnTop(true);
            managerInstance.frame.setAlwaysOnTop(false);
        }
        return managerInstance;
    }



    /**
     * Creates store window.
     * Registers this window as an observer to receive updates
     * when the store data changes.
     *
     * @param managerMode true for manager window, false for customer window
     */
    public StoreWindow(boolean managerMode) {
        this.managerMode = managerMode;

        StoreEngine engine = StoreEngine.getInstance();
        engine.addObserver(this);

        allProducts = new ArrayList<Product>();

        frame = new JFrame(managerMode ? "Store - Manager" : "Store - Customer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout(15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filter by Category:");
        topPanel.add(filterLabel);

        categoryFilter = new JComboBox<String>();
        categoryFilter.addItem("ALL");

        for (Category c : Category.values()) {
            categoryFilter.addItem(c.name());
        }

        topPanel.add(categoryFilter);
        frame.add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<Product>();
        productList = new JList<Product>(listModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(productList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        updateStockButton = new JButton("Update Stock");

        addButton = new JButton(managerMode ? "Add Product" : "Add To Cart");
        viewCartButton = new JButton("View Cart");
        orderHistoryButton = new JButton("Order History");

        if (managerMode) {
            bottomPanel.add(loadButton);
            bottomPanel.add(saveButton);
            bottomPanel.add(addButton);
            bottomPanel.add(updateStockButton);
            bottomPanel.add(discountButton);

        } else {
            bottomPanel.add(addButton);
            bottomPanel.add(viewCartButton);
            bottomPanel.add(orderHistoryButton);
        }

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Called by StoreEngine when the store data changes.
     * Updates the product list displayed in the GUI.
     */
    @Override
    public void update() {
        setProducts(StoreEngine.getInstance().getProducts());
    }
    public JButton getDiscountButton() {
        return discountButton;
    }

    /**
     * Updates products shown in the list.
     *
     * @param products list of products to display
     */
    public void setProducts(List<Product> products) {
        allProducts.clear();
        listModel.clear();

        if (products == null) return;

        for (Product p : products) {
            allProducts.add(p);
            listModel.addElement(p);
        }

        applyCategoryFilter();
    }

    /**
     * Filters the product list by the selected category.
     */
    public void applyCategoryFilter() {
        String selected = (String) categoryFilter.getSelectedItem();
        listModel.clear();

        for (Product p : allProducts) {
            if (selected == null ||
                    "ALL".equals(selected) ||
                    p.getCategory().name().equalsIgnoreCase(selected)) {
                listModel.addElement(p);
            }
        }
    }

    /**
     * Returns true if this window is in manager mode.
     *
     * @return true for manager window, false otherwise
     */
    public boolean isManagerMode() {
        return managerMode;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JList<Product> getProductList() {
        return productList;
    }

    public JComboBox<String> getCategoryFilter() {
        return categoryFilter;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateStockButton() {
        return updateStockButton;
    }

    public JButton getViewCartButton() {
        return viewCartButton;
    }

    public JButton getOrderHistoryButton() {
        return orderHistoryButton;
    }
}
