# Java Store Application

A Java Swing desktop application simulating an online store with manager and customer modes.

## Features

- **Customer mode** – browse products, add to cart, place orders, view order history
- **Manager mode** – add/remove products, update stock, apply percentage discounts, save/load inventory from file
- **Design patterns** – Singleton (StoreEngine), Observer (live UI updates), Strategy (discount system), Adapter (shipping), Builder & Factory (products)
- **Thread-safe** – background operations run on a dedicated worker thread; shared state is synchronized

## Project Structure

```
src/store/
├── Main.java
├── cart/          # Cart and CartItem
├── core/          # User, Customer, interfaces
├── discount/      # DiscountStrategy, NoDiscount, PercentageDiscount
├── engine/        # StoreEngine (core logic), StoreFileManager
├── gui/           # Swing windows and StoreController
├── orders/        # Order, OrderStatus
├── products/      # Product hierarchy, Factory, Builder
├── reports/       # Report writers
└── shipping/      # ShippingProvider, FastShip adapter
```

## Getting Started

### Requirements
- Java 17+

### Run
Compile and run `store.Main` from the `src` directory:

```bash
javac -d out $(find src -name "*.java")
java -cp out store.Main
```
