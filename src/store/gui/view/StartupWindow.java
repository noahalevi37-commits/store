package store.gui.view;

import store.engine.StoreEngine;
import store.gui.StoreWindow;
import store.gui.controller.StoreController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * StartupWindow - stays open during the whole run.
 * From here we can open multiple customer windows and one manager window,
 * each one with its own controller thread.
 *
 * name: noa halevi
 *
 */

public class StartupWindow extends JFrame {

    // shared engine for all windows
    private StoreEngine engine;

    // start window
    public StartupWindow(StoreEngine engine) {
        this.engine = engine;

        setTitle("Store System - Startup");
        setSize(320, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton clientButton = new JButton("Open Customer Window");
        JButton managerButton = new JButton("Open Manager Window");

        // open customer
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWindow(false);
            }
        });

        // open manager
        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWindow(true);
            }
        });

        add(Box.createVerticalStrut(10));
        add(clientButton);
        add(Box.createVerticalStrut(10));
        add(managerButton);

        setLocationRelativeTo(null);
        setVisible(true);
    } /**
     * Opens a new window (customer or manager) using a dedicated thread.
     * GUI is still created on Swing EDT.
     */


    private void openWindow(boolean managerMode) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        StoreWindow window;
                        if (managerMode) {
                            window = StoreWindow.getManagerInstance();
                        } else {
                            window = new StoreWindow(false);
                        }
                        new StoreController(engine, window, managerMode);

                    }
                });
            }
        });

        t.setDaemon(false);
        t.start();
    }
}
