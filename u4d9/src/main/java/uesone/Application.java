package uesone.example;

import uesone.entities.Customer;
import uesone.entities.Order;
import uesone.entities.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Implementa i metodi getOrders() e getProducts() per ottenere i dati di test
        List<Order> orders = getOrders();
        List<Product> products = getProducts();

        // Esercizio #1
        Map<Customer, List<Order>> ordersByCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        ordersByCustomer.forEach((customer, ordersList) -> {
            System.out.println("Customer: " + customer.getName());
            ordersList.forEach(order -> System.out.println(" - Order ID: " + order.getId()));
        });

        // Esercizio #2
        Map<Customer, Double> totalSalesPerCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(Order::getTotal)));

        totalSalesPerCustomer.forEach((customer, total) ->
                System.out.println("Customer: " + customer.getName() + ", Total Sales: " + total));

        // Esercizio #3
        Optional<Product> mostExpensiveProduct = products.stream()
                .max(Comparator.comparingDouble(Product::getPrice));

        mostExpensiveProduct.ifPresent(product ->
                System.out.println("Most Expensive Product: " + product.getName() + ", Price: " + product.getPrice()));

        // Esercizio #4
        OptionalDouble averageOrderTotal = orders.stream()
                .mapToDouble(Order::getTotal)
                .average();

        if (averageOrderTotal.isPresent()) {
            System.out.println("Average Order Total: " + averageOrderTotal.getAsDouble());
        } else {
            System.out.println("No orders available.");
        }
    }

    private static List<Order> getOrders() {
        // Implementa questo metodo per restituire una lista di ordini
        Customer customer1 = new Customer("John Doe", 1);
        Customer customer2 = new Customer("Jane Smith", 2);

        Product product1 = new Product("Laptop", "Electronics", 1200.00);
        Product product2 = new Product("Smartphone", "Electronics", 800.00);
        Product product3 = new Product("Headphones", "Accessories", 150.00);

        Order order1 = new Order(customer1);
        order1.addProduct(product1);
        order1.addProduct(product2);

        Order order2 = new Order(customer1);
        order2.addProduct(product3);

        Order order3 = new Order(customer2);
        order3.addProduct(product1);

        return List.of(order1, order2, order3);
    }

    private static List<Product> getProducts() {
        // Implementa questo metodo per restituire una lista di prodotti
        Product product1 = new Product("Laptop", "Electronics", 1200.00);
        Product product2 = new Product("Smartphone", "Electronics", 800.00);
        Product product3 = new Product("Headphones", "Accessories", 150.00);
        Product product4 = new Product("Smartwatch", "Accessories", 250.00);

        return List.of(product1, product2, product3, product4);
    }
}
