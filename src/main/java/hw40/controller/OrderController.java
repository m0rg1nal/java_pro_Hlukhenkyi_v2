package hw40.controller;

import hw40.model.Order;
import hw40.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository = new OrderRepository();

    public OrderController() {
        TestDataGenerator.populate(orderRepository);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable int id) {
        return orderRepository.getById(id);
    }

    @PostMapping
    public void add(@RequestBody Order order) {
        orderRepository.add(order);
    }
}
