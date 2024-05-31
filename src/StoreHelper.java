import java.util.*;

public class StoreHelper {
    private static Cart cart = new Cart();
    private static AdminDashboard adminDashboard;

    private static User user;

    public static void setUser(User newUser) {
        user = newUser;
    }
    public static void setAdminDashboard(AdminDashboard adminDashboard) {
        StoreHelper.adminDashboard = adminDashboard;
    }

    public static void browseProducts(Scanner scanner) {
        while (true) {
            System.out.println("Browse Products Category:");
            int categoryIndex = 1;

            for (String category : adminDashboard.getProductCategories()) {
                System.out.println(categoryIndex + ". " + category);
                categoryIndex++;
            }

            System.out.println(categoryIndex + ". Go back to main menu");
            System.out.println((categoryIndex + 1) + ". Go to Cart"); // Option to go to the cart

            System.out.print("Choose a product category (1-" + (categoryIndex + 1) + "): ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            if (categoryChoice >= 1 && categoryChoice <= adminDashboard.getProductCategories().length) {
                String selectedCategory = adminDashboard.getProductCategories()[categoryChoice - 1];
                browseCategoryProducts(selectedCategory, scanner);
            } else if (categoryChoice == categoryIndex) {
                System.out.println("Going back to the main menu.");
                Store.runStore(user, scanner);
                return;
            } else if (categoryChoice == (categoryIndex + 1)) {
                System.out.println("Going to the cart.");
                viewCart(scanner);
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static void browseCategoryProducts(String selectedCategory, Scanner scanner) {
        List<Product> categoryProducts = adminDashboard.getProductsByCategory(selectedCategory);

        while (true) {
            System.out.println("Browse " + selectedCategory + " Products:");

            int productIndex = 1;
            for (Product product : categoryProducts) {
                System.out.println(productIndex + ". " + product.getName() + " - RM" + product.getPrice());
                productIndex++;
            }

            System.out.println(productIndex + ". Go back to product categories");
            System.out.println((productIndex + 1) + ". Go to Cart"); // Option to go to the cart
            System.out.println((productIndex + 2) + ". Go back to main menu"); // Option to go back to the main menu

            System.out.print("Choose a " + selectedCategory.toLowerCase() + " product (1-" + (productIndex + 2) + "): ");
            int productChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            if (productChoice >= 1 && productChoice <= categoryProducts.size()) {
                Product selectedProduct = categoryProducts.get(productChoice - 1);
                addProductToCart(selectedProduct, scanner);
            } else if (productChoice == productIndex) {
                System.out.println("Going back to product categories.");
                return;
            } else if (productChoice == (productIndex + 1)) {
                System.out.println("Going to the cart.");
                viewCart(scanner);
                return;
            } else if (productChoice == (productIndex + 2)) {
                System.out.println("Going back to the main menu.");
                Store.runStore(user, scanner);
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void addProductToCart(Product product, Scanner scanner) {

        System.out.println("Product Details:");
        System.out.println("Name: " + product.getName());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Price: RM" + String.format("%.2f", product.getPrice()));

        // Check if the selected category is "Shirt" before displaying size options
        if ("Shirt".equals(product.getCategory())) {
            while (true) {
                System.out.println("Available Sizes: " + Size.SMALL + ", " + Size.MEDIUM + ", " + Size.LARGE + ", " + Size.EXTRA_LARGE);
                System.out.print("Choose a size for the product: ");
                String sizeChoice = scanner.nextLine();

                if (isValidSize(sizeChoice)) {
                    product.setSize(sizeChoice);
                    break; // Break the loop if a valid size is entered
                } else {
                    System.out.println("Invalid size. Please try again.");
                }
            }
        }

        addToCart(product, scanner);
    }

    private static void addToCart(Product product, Scanner scanner) {
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        product.setQuantity(quantity);

        // Check if the product with the same name is already in the cart
        boolean productExists = false;
        for (Product cartItem : cart.getItems()) {
            if (cartItem.getName().equals(product.getName()) && Objects.equals(cartItem.getSize(), product.getSize())) {
                // Product with the same name and size exists in the cart
                productExists = true;
                updateCartItem(cartItem, product);
                break;
            }
        }

        if (!productExists) {
            // Product with the same name and size is not in the cart, add a new entry
            cart.addItem(product);
        }

        // Recalculate the total price for the entire cart
        cart.calculateTotalPrice();

        System.out.println(product.getName() + " (Size: " + product.getSize() + ", Quantity: " + quantity + ") added to the cart.");

        // Ask the user whether to continue shopping or go to the cart
        System.out.println("1. Continue Shopping");
        System.out.println("2. Go to Cart");
        System.out.print("Enter your choice (1-2): ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (userChoice) {
            case 1:
                browseProducts(scanner);
                break;  // Continue shopping (do nothing)
            case 2:
                viewCart(scanner);  // Go to the cart
                break;
            default:
                System.out.println("Invalid choice. Continuing shopping.");
        }
    }


    private static void updateCartItem(Product cartItem, Product newProduct) {
        // Update the existing entry in the cart with the new size and quantity
        cartItem.setSize(newProduct.getSize());
        cartItem.setQuantity(cartItem.getQuantity() + newProduct.getQuantity());
    }
    public static void viewCart(Scanner scanner) {
        List<Product> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            // Do not show certain options if the cart is empty
            System.out.println("1. Continue Shopping");
            System.out.println("2. Go back to main menu");
            System.out.print("Enter your choice (1-2): ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (userChoice) {
                case 1:
                    browseProducts(scanner);  // Continue shopping
                    break;
                case 2:
                    System.out.println("Going back to the main menu.");
                    Store.runStore(user, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Continuing shopping.");
            }
            return;
        } else {
            System.out.println("View Cart:");

            // Use a map to group products by name and size
            Map<String, Map<String, List<Product>>> groupedProducts = new HashMap<>();

            for (Product item : cartItems) {
                groupedProducts
                        .computeIfAbsent(item.getName(), k -> new HashMap<>())
                        .computeIfAbsent(item.getSize(), k -> new ArrayList<>())
                        .add(item);
            }

            int index = 1;

            for (Map<String, List<Product>> sizeMap : groupedProducts.values()) {
                for (List<Product> sizeProducts : sizeMap.values()) {
                    for (Product product : sizeProducts) {
                        System.out.println("Index " + index + ":");
                        System.out.println("Product: " + product.getName());
                        System.out.println("Description: " + product.getDescription());
                        System.out.println("Size: " + product.getSize());
                        int totalQuantity = sizeProducts.stream().mapToInt(Product::getQuantity).sum();
                        double totalPricePerProduct = product.getPrice() * product.getQuantity();  // Calculate total price for the product

                        System.out.println("Quantity: " + totalQuantity);
                        System.out.printf("Price per unit: RM%.2f%n", product.getPrice());
                        System.out.printf("Total Price: RM%.2f%n", totalPricePerProduct);  // Display the total price for the product
                        System.out.println();
                        index++;
                    }
                }
            }

            System.out.printf("Total Price: RM%.2f%n", cart.calculateTotalPrice());
        }

        // Additional options for the user
        System.out.println("1. Checkout");
        System.out.println("2. Continue Shopping");
        System.out.println("3. Clear Cart");
        System.out.println("4. Remove Product from Cart");
        System.out.println("5. Go back to main menu");
        System.out.print("Enter your choice (1-5): ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (userChoice) {
            case 1:
                checkout(scanner);
                break;
            case 2:
                browseProducts(scanner);  // Go back to browsing products
                break;
            case 3:
                clearCart();
                System.out.println("Cart cleared successfully.");
                viewCart(scanner);
                break;
            case 4:
                removeProductFromCart(scanner);
                viewCart(scanner);
                break;
            case 5:
                System.out.println("Going back to the main menu.");
                Store.runStore(user, scanner);
                break;
            default:
                System.out.println("Invalid choice. Continuing shopping.");
        }
    }


    public static void checkout(Scanner scanner) {
        List<Product> cartItems = cart.getItems();

        if (!cartItems.isEmpty()) {
            System.out.println("Checkout:");

            for (Product item : cartItems) {
                System.out.println("Product: " + item.getName());
                System.out.println("Size: " + item.getSize());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Price per unit: RM" + item.getPrice());
                System.out.println("Total Price: RM" + item.getTotalPrice());
                System.out.println();
            }

            System.out.println("Total Price: RM" + cart.calculateTotalPrice());

            System.out.println("Choose a payment method:");
            System.out.println("1. eWallet");
            System.out.println("2. Online Banking");
            System.out.println("3. Debit/Credit Card");
            System.out.print("Enter your choice (1-3): ");
            int paymentChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (paymentChoice) {
                case 1:
                    processEWalletPayment(scanner, user);
                    break;
                case 2:
                    processOnlineBankingPayment(scanner, user);
                    break;
                case 3:
                    processCardPayment(scanner, user);
                    break;
                default:
                    System.out.println("Invalid choice. Payment failed.");
            }

            // Clear the cart after checkout
            cart.clearCart();
        } else {
            System.out.println("Your shopping cart is empty.");
        }
        browseProducts(scanner);
    }

    private static void clearCart() {
        cart.clearCart();
    }

    private static void removeProductFromCart(Scanner scanner) {
        System.out.print("Enter the index of the product to remove (1-" + cart.getItems().size() + "): ");
        int productIndex = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (productIndex >= 1 && productIndex <= cart.getItems().size()) {
            Product removedProduct = cart.getItems().remove(productIndex - 1);
            System.out.println("Product removed from cart: " + removedProduct.getName());
        } else {
            System.out.println("Invalid index. Please try again.");
        }
    }

    private static void processEWalletPayment(Scanner scanner, User user) {
        boolean paymentSuccessful = redirectAfterSuccessfulPayment(scanner, user);
        if (paymentSuccessful) {
            Store.runStore(user, scanner);
        }
    }

    private static void processOnlineBankingPayment(Scanner scanner, User user) {
        boolean paymentSuccessful = redirectAfterSuccessfulPayment(scanner, user);
        if (paymentSuccessful) {
            Store.runStore(user, scanner);
        }
    }

    private static void processCardPayment(Scanner scanner, User user) {
        boolean paymentSuccessful = redirectAfterSuccessfulPayment(scanner, user);
        if (paymentSuccessful) {
            Store.runStore(user, scanner);
        }
    }

    private static boolean redirectAfterSuccessfulPayment(Scanner scanner, User user) {
        if (user != null) {
            Order order = new Order(StoreHelper.cart.getItems(), StoreHelper.cart.calculateTotalPrice());
            user.addOrderToHistory(order);
            System.out.println("Payment successful! Order added to order history.");
            return true;  // Return true to indicate successful payment
        } else {
            System.out.println("User is null. Unable to add order to order history.");
            return false;  // Return false to indicate payment failure
        }
    }


    public static void updateProfile(User user, Scanner scanner) {
        System.out.println("Current Profile Information:");
        System.out.println("1. Name: " + user.getName());
        System.out.println("2. Email: " + user.getEmail());
        System.out.println("3. Address: " + user.getAddress());
        System.out.println("4. Password: " + hidePassword(user.getPassword()));  // Display password in asterisk style
        System.out.println("5. Go back to main menu");

        System.out.print("Choose an option (1-5): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (choice) {
            case 1:
                updateName(user, scanner);
                break;
            case 2:
                updateEmail(user, scanner);
                break;
            case 3:
                updatePhoneNumber(user, scanner);
                break;
            case 4:
                updatePassword(user, scanner);
                break;
            case 5:
                System.out.println("Going back to the main menu.");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        System.out.println("Profile updated successfully!");

        // Display updated profile details
        System.out.println("Updated Profile Information:");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Password: " + hidePassword(user.getPassword()));  // Display password in asterisk style

        System.out.println("Do you want to update another profile information?");
        System.out.println("1. Yes");
        System.out.println("2. No, go back to main menu");
        System.out.print("Enter your choice (1-2): ");
        int continueChoice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        if (continueChoice == 1) {
            // Recursively call the updateProfile method for another update
            updateProfile(user, scanner);
        } else {
            System.out.println("Going back to the main menu.");
        }
    }


    private static void updateName(User user, Scanner scanner) {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        user.setName(newName);
    }

    private static void updateEmail(User user, Scanner scanner) {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        user.setEmail(newEmail);
    }

    private static void updatePhoneNumber(User user, Scanner scanner) {
        System.out.print("Enter new phone number: ");
        String newAddress = scanner.nextLine();
        user.setAddress(newAddress);
    }

    private static void updatePassword(User user, Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
    }

    private static String hidePassword(String password) {
        // Convert the password to asterisk (*) style
        return "*".repeat(Math.max(0, password.length()));
    }

    public static void viewOrderHistory(User user, Scanner scanner) {
        List<Order> orderHistory = user.getOrderHistory();

        if (!orderHistory.isEmpty()) {
            System.out.println("Order History:");
            System.out.printf("%-10s %-15s %-10s %-10s %-15s%n",
                    "OrderID", "Product Name", "Price", "Quantity", "Total Price");

            for (Order order : orderHistory) {
                int orderId = order.getOrderId();

                for (Product product : order.getProducts()) {
                    String productName = product.getName();
                    double price = product.getPrice();
                    int quantity = product.getQuantity();
                    double productTotalPrice = price * quantity;

                    System.out.printf("%-10s %-15s %-10s %-10s %-15s%n",
                            orderId, productName, price, quantity, productTotalPrice);
                }
            }

            // After displaying order history, provide an option to go back to the main menu
            System.out.println("0. Go back to the main menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (0-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 0:
                    if (user == null) {
                        displayInitialMenu(scanner);
                    } else {
                        Store.displayStoreMenu(user, scanner);
                    }
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0); // This will terminate the program
                    break;
                default:
                    System.out.println("Invalid choice. Returning to the main menu.");
            }
        } else {
            System.out.println("You have not placed any orders yet.");
        }
    }



    private static boolean isValidSize(String size) {
        switch (size.toUpperCase()) {
            case "S":
            case "M":
            case "L":
            case "XL":
                return true;
            default:
                return false;
        }
    }

    private static void displayInitialMenu(Scanner scanner) {
        System.out.println("Welcome TO UTHM Merchandise");
        System.out.println("1. Register");
        System.out.println("2. Login (User)");
        System.out.println("3. Login (Admin)");
        System.out.println("4. Exit");

        boolean validChoice = false;
        int choice = 0;

        while (!validChoice) {
            System.out.print("Choose an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                if (choice >= 1 && choice <= 4) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.nextLine();  // Consume the invalid input
            }
        }
        Main.handleMainMenuChoice(choice, scanner);
    }
}