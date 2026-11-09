package store;

import store.engine.StoreEngine;
import store.gui.StoreWindow;
import store.gui.controller.StoreController;
import store.gui.view.StartupWindow;

import store.products.SimpleProduct;
import java.awt.Color;
import java.awt.print.Book;


/*
  Main class - initializes the system with data so it won't be empty.
  name: noa halevi
*/


public class Main {

    public static void main(String[] args) {

        StoreEngine engine = StoreEngine.getInstance();

        StartupWindow NewWindow = new StartupWindow(engine);

        String path = "C:\\Users\\user\\IdeaProjects\\HW.1\\src\\store\\resources\\";

        engine.addProduct(new SimpleProduct(
                "Java Book", 120.0, 10, "Best book", "BOOKS", path + "book.jpg"
        ));
        engine.addProduct(new SimpleProduct(
                "Laptop", 4500.0, 5, "Fast laptop", "ELECTRONICS", path + "laptop.jpg"
        ));
        engine.addProduct(new SimpleProduct(
                "Headphones", 300.0, 15, "Good sound", "ELECTRONICS", path + "headphones.jpg"
        ));


    }
}
