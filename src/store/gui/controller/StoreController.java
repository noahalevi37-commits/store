package store.gui.controller;

import store.engine.StoreEngine;
import store.engine.StoreFileManager;
import store.gui.CartWindow;
import store.gui.OrderHistoryWindow;
import store.gui.StoreWindow;
import store.products.Product;
import store.products.ProductFactory;
import store.products.Category;

import store.cart.Cart;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import store.discount.NoDiscount;
import store.discount.PercentageDiscount;

/**
 * StoreController is responsible for controlling the StoreWindow.
 *
 * This class connects the GUI components to the StoreEngine logic.
 * It handles user actions such as loading/saving products,
 * adding products, updating stock, managing the cart,
 * and opening additional windows.
 *
 * name: noa halevi
 *
 */
public class StoreController {

    /** Reference to the store engine */
    private StoreEngine engine;

    /** Reference to the store window */
    private StoreWindow window;

    /** Indicates whether the controller is used for manager mode */
    private boolean managerMode;

    /** Executor service for background tasks */
    private ExecutorService worker;

    /** Shopping cart for customer mode */
    private Cart cart;

    /**
     * Creates a controller for a customer window.
     *
     * @param engine the store engine
     * @param window the store window
     */
    public StoreController(StoreEngine engine, StoreWindow window) {
        this.engine = engine;
        this.window = window;
        this.managerMode = false;

        worker = Executors.newSingleThreadExecutor();
        cart = new Cart();

        init();
        window.setProducts(engine.getProducts());
    }

    /**
     * Creates a controller for a manager or customer window.
     *
     * @param engine the store engine
     * @param window the store window
     * @param managerMode true if manager window, false if customer window
     */
    public StoreController(StoreEngine engine, StoreWindow window, boolean managerMode) {
        this.engine = engine;
        this.window = window;
        this.managerMode = managerMode;

        worker = Executors.newSingleThreadExecutor();

        if (!managerMode) {
            cart = new Cart();
        }

        init();
        window.setProducts(engine.getProducts());
    }

    /**
     * Initializes all listeners and connects GUI actions
     * to the appropriate controller logic.
     */
    private void init() {

        window.getCategoryFilter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.applyCategoryFilter();
            }
        });

        window.getProductList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;

                Product p = window.getProductList().getSelectedValue();
                if (p == null) return;

                JOptionPane.showMessageDialog(
                        window.getFrame(),
                        p.getDescription() +
                                "\nPrice: " + p.getPrice() +
                                "\nStock: " + p.getStock()
                );
            }
        });

        if (managerMode) {
            window.getDiscountButton().addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Enter discount percentage (0 for none):");
                if (input == null) return;

                try {
                    double percent = Double.parseDouble(input);

                    if (percent <= 0) {
                        engine.setDiscountStrategy(new NoDiscount());
                    } else {
                        engine.setDiscountStrategy(new PercentageDiscount(percent));
                    }

                } catch (Exception ignore) {}
            });


            window.getLoadButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    load();
                }
            });

            window.getSaveButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    save();
                }
            });

            window.getAddButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addProduct();
                }
            });

            window.getUpdateStockButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateStock();
                }
            });

        } else {

            window.getAddButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToCart();
                }
            });

            window.getViewCartButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CartWindow(cart, engine);
                }
            });

            window.getOrderHistoryButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openOrders();
                }
            });
        }
    }

    /**
     * Loads products from a file into the store.
     */
    private void load() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                JFileChooser c = new JFileChooser();
                if (c.showOpenDialog(window.getFrame()) != JFileChooser.APPROVE_OPTION) return;

                File f = c.getSelectedFile();

                try {
                    StoreFileManager.loadProducts(engine, f);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            window.setProducts(engine.getProducts());
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * Saves all products to a file.
     */
    private void save() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                JFileChooser c = new JFileChooser();
                if (c.showSaveDialog(window.getFrame()) != JFileChooser.APPROVE_OPTION) return;

                File f = c.getSelectedFile();

                try {
                    StoreFileManager.saveProducts(engine, f);
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * Adds the selected product to the shopping cart.
     */
    private void addToCart() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                Product p = window.getProductList().getSelectedValue();
                if (p == null) return;
                if (p.getStock() <= 0) return;

                cart.addItem(p, 1);
            }
        });
    }

    /**
     * Opens the order history window.
     */
    private void openOrders() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                List orders = engine.getAllOrders();
                new OrderHistoryWindow(orders);
            }
        });
    }

    /**
     * Adds a new product to the store (manager mode).
     */
    private void addProduct() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                String name = JOptionPane.showInputDialog("Name:");
                if (name == null || name.trim().isEmpty()) return;

                String priceStr = JOptionPane.showInputDialog("Price:");
                String stockStr = JOptionPane.showInputDialog("Stock:");
                String desc = JOptionPane.showInputDialog("Description:");
                String cat = JOptionPane.showInputDialog("Category:");
                String img = JOptionPane.showInputDialog("Image:");

                try {
                    double price = Double.parseDouble(priceStr);
                    int stock = Integer.parseInt(stockStr);

                    if (desc == null) desc = "";
                    if (cat == null || cat.trim().isEmpty()) cat = "OTHER";
                    if (img == null) img = "";

                    Category category = Category.valueOf(cat.trim().toUpperCase());

                    Product p = ProductFactory.createSimpleProduct(
                            name.trim(),
                            price,
                            stock,
                            desc,
                            category,
                            img.trim()
                    );

                    engine.addProduct(p);

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            window.setProducts(engine.getProducts());
                        }
                    });

                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * Updates the stock of the selected product.
     */
    private void updateStock() {
        worker.execute(new Runnable() {
            @Override
            public void run() {
                Product p = window.getProductList().getSelectedValue();
                if (p == null) return;

                String diffStr = JOptionPane.showInputDialog("Change stock:");
                if (diffStr == null) return;

                try {
                    int diff = Integer.parseInt(diffStr);

                    if (diff > 0) {
                        p.increaseStock(diff);
                    } else if (diff < 0) {
                        p.decreaseStock(-diff);
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            window.setProducts(engine.getProducts());
                        }
                    });

                } catch (Exception e) {
                }
            }
        });
    }
}
