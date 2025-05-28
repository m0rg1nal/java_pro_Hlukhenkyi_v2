package hw38;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductRepository repository = new ProductRepository();
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();

        boolean running = true;

        while (running) {
            System.out.println("\n=== Cart Menu ===");
            System.out.println("1. Show all products");
            System.out.println("2. Add product to cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. View cart");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Available products:");
                    for (Product p : repository.findAll()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Enter product ID to add: ");
                    int addId = scanner.nextInt();
                    repository.findById(addId).ifPresentOrElse(
                            cart::addProduct,
                            () -> System.out.println("Product not found.")
                    );
                    cart.showCart();
                    break;
                case 3:
                    System.out.print("Enter product ID to remove: ");
                    int removeId = scanner.nextInt();
                    if (cart.removeProductById(removeId)) {
                        System.out.println("Product removed.");
                    } else {
                        System.out.println("Product not found in cart.");
                    }
                    cart.showCart();
                    break;
                case 4:
                    cart.showCart();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
        System.out.println("Program ended.");
    }
}

