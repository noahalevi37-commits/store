package store.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import store.orders.Order;

/**
 * Displays the order history of the store.
 *
 * name: noa halevi
 *
 */
public class OrderHistoryWindow {

    private JFrame frame;
    private JList<Order> orderList;
    private DefaultListModel<Order> model;

    /**
     * Constructs the order history window.
     *
     *  orders list of past orders
     */
    public OrderHistoryWindow(List<Order> orders) {

        frame = new JFrame("Order History");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultListModel<Order>();
        for (Order o : orders) {
            model.addElement(o);
        }

        orderList = new JList<Order>(model);

        frame.add(new JLabel("Previous Orders", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(new JScrollPane(orderList), BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
