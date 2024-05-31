import java.util.Scanner;

public class Store {
    private static User user;
    static Scanner scanner;

    public Store() {
        scanner = new Scanner(System.in);
    }

    public static boolean runStore(User user, Scanner scanner) {
        while (true) {
            displayStoreMenu(user, scanner);
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    StoreHelper.browseProducts(scanner);
                    break;
                case 2:
                    StoreHelper.updateProfile(user, scanner);
                    break;
                case 3:
                    StoreHelper.viewOrderHistory(user, scanner);
                    break;
                case 4:
                    System.out.println("Logging out. Goodbye!");
                    return true;  // Return true to indicate that the user has logged out
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static void displayStoreMenu(User user, Scanner scanner) {
        Store.user = user;
        Store.scanner = scanner;
        System.out.println("1. Browse Products");
        System.out.println("2. Update Profile");
        System.out.println("3. View Order History");
        System.out.println("4. Logout");

        System.out.print("Choose an option: ");
    }
}