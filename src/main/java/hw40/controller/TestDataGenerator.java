package hw40.controller;

import hw40.model.Order;
import hw40.model.Product;
import hw40.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TestDataGenerator {

    public static void populate(OrderRepository repository) {
        Product p1 = new Product(0, "Medium Coke", 35.0);
        Product p2 = new Product(1, "Burger", 75.0);
        Order order1 = new Order(0, LocalDateTime.now(), Arrays.asList(p1, p2));

        Product p3 = new Product(2, "Large Coke", 45.0);
        Product p4 = new Product(3, "Cheeseburger", 85.0);
        Order order2 = new Order(1, LocalDateTime.now(), Arrays.asList(p3, p4));

        repository.add(order1);
        repository.add(order2);
    }
}
