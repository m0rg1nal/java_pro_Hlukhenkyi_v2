package hw37;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Order {
    private int id;
    private Date date;
    private double cost;
    private List<Product> products;
}
