import java.util.*;
import java.util.stream.Collectors;

public class AdminDashboard {

    private static List<Product> products;
    private static List<Order> orders;

    public AdminDashboard() {
        this.products = new ArrayList<>();
        initializeDefaultProducts();  // Initialize default products
        this.orders = new ArrayList<>(); // Initialize the orders list
    }

    private void initializeDefaultProducts() {
        // Default products for Shirt category
        addProduct(new Product("Glorious UTHM Shirt", "Comfortable cotton shirt", 19.99, "Shirt"));
        addProduct(new Product("Vita La UTHM Shirt", "Stylish shirt with UTHM design", 24.99, "Shirt"));

        // Default products for Lanyard category
        addProduct(new Product("Scientia UTHM Lanyard", "Stylish lanyard with UTHM logo", 5.99, "Lanyard"));
        addProduct(new Product("UTHM Best Lanyard", "High-quality UTHM branded lanyard", 7.99, "Lanyard"));

        // Default products for Bag category
        addProduct(new Product("UTHM Backpack", "Spacious backpack for daily use", 29.99, "Bag"));
        addProduct(new Product("UTHM Duffle Bag", "Durable duffle bag with UTHM branding", 39.99, "Bag"));
    }

    public Product getProductByName(String productName) {
        // Use stream to find the product by name
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst();

        // Return the product if found, otherwise return null
        return productOptional.orElse(null);
    }

    public String[] getProductCategories() {
        // Extract unique categories from the list of products
        Set<String> uniqueCategories = products.stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet());

        // Convert the set of categories to an array
        return uniqueCategories.toArray(new String[0]);
    }

    public List<Product> getProductsByCategory(String category) {
        // Use stream to filter products by category
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static void displayDashboard(Admin admin, Scanner scanner) {
        System.out.println("Welcome, Admin " + admin.getUsername() + "!");

        while (true) {
            displayOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    addProductFromUserInput(scanner);
                    break;

                case 2:
                    updateProduct(scanner);
                    break;

                case 3:
                    removeProduct(scanner);
                    break;

                case 4:
                    System.out.println("Logging out from Admin Dashboard. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayOptions() {
        System.out.println("Admin Dashboard Options:");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Remove Product");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    private static void removeProduct(Scanner scanner) {
        displayAvailableProducts();

        System.out.print("Enter the number of the product to remove: ");
        int productNumber = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (productNumber >= 1 && productNumber <= products.size()) {
            // Get the product using the index
            Product productToRemove = products.get(productNumber - 1);

            // Remove the product from the list
            products.remove(productToRemove);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Invalid product number.");
        }
    }

    private static void updateProduct(Scanner scanner) {
        displayAvailableProducts();
        System.out.print("Enter the number of the product to update: ");

        int productNumber = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (productNumber >= 1 && productNumber <= products.size()) {
            // Get the product using the index
            Product productToUpdate = products.get(productNumber - 1);

            System.out.print("Enter the new price for the product: RM");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline character

            // Update the product's price
            productToUpdate.setPrice(newPrice);

            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Invalid product number.");
        }
    }


    private static void addProductFromUserInput(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter product category: ");
        String category = scanner.nextLine();

        // Create a new Product object with user input
        Product newProduct = new Product(name, description, price, category);

        // Add the new product to the list
        addProduct(newProduct);

        System.out.println("Product added successfully!");
    }

    private static void displayAvailableProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println(i + 1 + ". " + product.getName() + " - RM" + product.getPrice());
        }
    }
}
