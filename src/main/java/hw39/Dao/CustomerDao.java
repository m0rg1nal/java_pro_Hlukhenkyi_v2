package hw39.Dao;

import hw39.Customer;
import hw39.CustomerMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (full_name, email, social_security_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber());
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerMapper(), id);
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET full_name = ?, email = ?, social_security_number = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber(), customer.getId());
    }

    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }
}
