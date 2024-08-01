package uesone;

import bertcoscia.entities.Customer;
import bertcoscia.entities.Order;
import bertcoscia.entities.Product;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        Faker faker = new Faker();

        Supplier<Customer> randomCustomer = () -> {
            String name = faker.lordOfTheRings().character();
            int tier = faker.number().numberBetween(1, 3);
            return new Customer(name, tier);
        };

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(randomCustomer.get());
        }

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = faker.book().title();
            String category = "Books";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        for (int i = 0; i < 10; i++) {
            String name = faker.pokemon().name();
            String category = "Baby";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        for (int i = 0; i < 10; i++) {
            String name = faker.beer().name();
            String category = "Boys";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        List<Order> orders = new ArrayList<>();
        for (Customer customer : customers) {
            int product1 = faker.number().numberBetween(0, 29);
            int product2 = faker.number().numberBetween(0, 29);
            int product3 = faker.number().numberBetween(0, 29);
            LocalDate dateOrder = LocalDate.now();
            Order order = customer.createOrder(dateOrder, products.get(product1), products.get(product2), products.get(product3));
            orders.add(order);
        }

        //--------------------------------------------------------------------EX1--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX1--------------------------------------------------------------------//");
        Map<Customer, List<Order>> ordersByClient = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
        ordersByClient.forEach((customer, orderList) -> System.out.println(customer + ": " + orderList));

        //--------------------------------------------------------------------EX2--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX2--------------------------------------------------------------------//");
        

        //--------------------------------------------------------------------EX3--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX3--------------------------------------------------------------------//");
        List<Product> mostExpensiveProducts = products.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(5).toList();
        for (Product product : mostExpensiveProducts) {
            System.out.println(product);
        }

        //--------------------------------------------------------------------EX4--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX4--------------------------------------------------------------------//");
        Map<Order, Double> avgOrderPrice = orders.stream()
                .collect(Collectors.groupingBy(
                                order -> order,
                                Collectors.averagingDouble(order -> order.getProducts().stream().collect(Collectors.averagingDouble(Product::getPrice))
                                )
                        )
                );
        avgOrderPrice.forEach((order, avgPrice) -> System.out.println("Order: " + order.getId() + ", average price: " + avgPrice));

        //--------------------------------------------------------------------EX5--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX5--------------------------------------------------------------------//");
        Map<String, Double> totalByCategory = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        totalByCategory.forEach((category, price) -> System.out.println(category + " " + price));
    }
}