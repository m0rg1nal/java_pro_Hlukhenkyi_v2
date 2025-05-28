package hw38;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(1, "Apple", 1.0));
        products.add(new Product(2, "Banana", 0.5));
        products.add(new Product(3, "Chocolate", 2.5));
    }

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean deleteProductById(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    public boolean updateProduct(Product updated) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == updated.getId()) {
                products.set(i, updated);
                return true;
            }
        }
        return false;
    }
}

