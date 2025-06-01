package hw39;

import hw39.Dao.CustomerDao;
import hw39.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CustomerDao customerDao = context.getBean(CustomerDao.class);

        Customer newCustomer1 = new Customer(0, "John Smith", "smith@gmail.com", "123-45-6789");
        Customer newCustomer2 = new Customer(0, "Anthony Cobble", "cobble@gmail.com", "312-65-8659");
        Customer newCustomer3 = new Customer(0, "John Pork", "pork@gmail.com", "543-74-6254");
        customerDao.addCustomer(newCustomer1);
        customerDao.addCustomer(newCustomer2);
        customerDao.addCustomer(newCustomer3);

        List<Customer> customers = customerDao.getAllCustomers();
        customers.forEach(System.out::println);

        customerDao.getCustomerById(1);
        customerDao.deleteCustomer(2);

        context.close();
    }
}