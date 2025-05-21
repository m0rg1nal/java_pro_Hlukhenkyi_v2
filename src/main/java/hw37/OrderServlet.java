package hw37;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    public final Map<Integer, Order> orderMap = new HashMap<>();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Тут у справжньому додатку має бути парсинг параметрів з тіла запиту (form-data або параметри)
        // Для прикладу жорстко створюємо замовлення:
        Product product = new Product(1, "Example Product", 10.0);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(1, new Date(), 10.0, products);

        orderMap.put(order.getId(), order);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        try (PrintWriter out = resp.getWriter()) {
            out.println("Order created with ID: " + order.getId());
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getIdFromPath(req);
        Order order = orderMap.get(id);

        if (order != null) {
            resp.setContentType("text/plain");
            try (PrintWriter out = resp.getWriter()) {
                out.println("Order ID: " + order.getId());
                out.println("Date: " + order.getDate());
                out.println("Cost: " + order.getCost());
                out.println("Products:");
                for (Product p : order.getProducts()) {
                    out.println("- " + p.getName() + " (" + p.getCost() + ")");
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("Order not found");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getIdFromPath(req);
        // Тут теж можна було б парсити запит, але зараз оновимо вручну
        if (orderMap.containsKey(id)) {
            Product newProduct = new Product(2, "Updated Product", 15.0);
            List<Product> newProducts = Collections.singletonList(newProduct);
            Order updatedOrder = new Order(id, new Date(), 15.0, newProducts);
            orderMap.put(id, updatedOrder);
            resp.getWriter().println("Order updated");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("Order not found");
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = getIdFromPath(req);
        if (orderMap.remove(id) != null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("Order not found");
        }
    }

    private int getIdFromPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            throw new IllegalArgumentException("ID is required in the path, e.g. /orders/1");
        }
        return Integer.parseInt(path.substring(1));
    }
}