package store.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import store.cart.Cart;
import store.cart.CartItem;
import store.engine.StoreEngine;
import store.orders.Order;

/**
 * Displays the shopping cart content and allows checkout.
 *
 * name: noa halevi
 *
 */
public class CartWindow {

    private JFrame frame;
    private JList<CartItem> itemList;
    private DefaultListModel<CartItem> model;
    private JLabel totalLabel;
    private JButton removeButton;
    private JButton checkoutButton;

    /**
     * Constructs the cart window.
     *
     *  cart the shopping cart to display
     *  engine store engine (for creating orders)
     */
    public CartWindow(Cart cart, StoreEngine engine) {

        frame = new JFrame("Shopping Cart");
        frame.setSize(520, 420);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultListModel<CartItem>();
        for (CartItem item : cart.getItems()) {
            model.addElement(item);
        }

        itemList = new JList<CartItem>(model);
        itemList.setFixedCellHeight(55);
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                CartItem item = (CartItem) value;

                String text =
                        item.getProduct().getName()
                                + " | Unit: " + item.getProduct().getPrice() + " NIS"
                                + " | Qty: " + item.getQuantity();

                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        totalLabel = new JLabel("Total: " + cart.calculateTotal() + " NIS", SwingConstants.CENTER);

        removeButton = new JButton("Remove Item");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartItem selected = itemList.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(frame, "Select an item to remove.");
                    return;
                }

                boolean removed = cart.removeItem(selected.getProduct());
                if (removed) {
                    model.removeElement(selected);
                    totalLabel.setText("Total: " + cart.calculateTotal() + " NIS");
                } else {
                    JOptionPane.showMessageDialog(frame, "Remove failed (check Cart.removeItem).");
                }
            }
        });

        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (cart.getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Cart is empty.");
                    return;
                }

                Order created = engine.createOrderFromCart(cart);

                JOptionPane.showMessageDialog(frame, "Order Confirmed! Order id: " + created.getOrderId());

                cart.clearCart();
                model.clear();
                totalLabel.setText("Total: 0 NIS");

                new OrderHistoryWindow(engine.getAllOrders());
                frame.dispose();
            }
        });

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.add(totalLabel);
        bottomPanel.add(removeButton);
        bottomPanel.add(checkoutButton);

        frame.add(new JLabel("Cart Items", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(new JScrollPane(itemList), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
