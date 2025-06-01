package hw40.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private int id;
    private LocalDateTime creationDate;
    private double totalCost;
    private List<Product> products;

    public Order(int id, LocalDateTime creationDate, List<Product> products) {
        this.id = id;
        this.creationDate = creationDate;
        this.products = products;
        this.totalCost = products.stream().mapToDouble(Product::getCost).sum();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.totalCost = products.stream().mapToDouble(Product::getCost).sum();
    }
}
