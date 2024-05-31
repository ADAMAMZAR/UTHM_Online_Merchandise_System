import java.util.Scanner;

public class LoginFormAdmin {
    private static Admin[] admins = {
            new Admin("01", "Amzar", "123456"),
            new Admin("02", "Danial", "123456"),
            new Admin("03", "Amjad", "123456"),
            new Admin("04", "Afiq", "123456"),
            new Admin("05", "Dhea", "123456"),
            new Admin("06", "Fatinie", "123456")
    };

    public static Admin loginAdmin(Scanner scanner) {
        int choice;

        do {
            System.out.println("Choose an option:");
            System.out.println("1. Login as Admin");
            System.out.println("2. Cancel");

            System.out.print("Enter your choice (1-2): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice (1-2): ");
                scanner.nextLine();  // Consume the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }

        } while (choice != 1 && choice != 2);

        if (choice == 1) {
            Admin admin = null;

            while (admin == null) {
                System.out.print("Enter admin username: ");
                String username = scanner.nextLine();

                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();

                admin = authenticateAdmin(username, password);

                if (admin == null) {
                    System.out.println("Admin login failed. Invalid username or password. Please try again.");
                } else {
                    System.out.println("Admin login successful!");
                }
            }

            return admin;
        } else {
            // Cancel and redirect to main menu
            return null;
        }
    }

    private static Admin authenticateAdmin(String username, String password) {
        for (Admin admin : admins) {
            if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }
}
