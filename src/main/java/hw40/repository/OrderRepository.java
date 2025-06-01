package hw40.repository;

import hw40.model.Order;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRepository {
    private final Map<Integer, Order> orders = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Order getById(int id) {
        return orders.get(id);
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public Order add(Order order) {
        int id = idGenerator.getAndIncrement();
        order.setId(id);
        orders.put(id, order);
        return order;
    }
}