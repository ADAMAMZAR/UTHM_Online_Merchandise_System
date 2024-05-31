import java.util.Scanner;

public class RegistrationForm {
    static User[] users = new User[100]; // Assuming a maximum of 100 users, adjust as needed
    private static int userCount = 0;

    public static User registerUser(Scanner scanner) {
        int choice;

        do {
            System.out.println("Choose an option:");
            System.out.println("1. Fill the registration form");
            System.out.println("2. Cancel");

            System.out.print("Enter your choice (1-2): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice (1-2): ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }

        } while (choice != 1 && choice != 2);

        if (choice == 1) {
            // Continue with registration
            // Validate and get name
            String name = getValidatedName(scanner);

            // Validate and get email
            String email = getValidatedEmail(scanner);

            // Validate and get address (modified from phoneNumber)
            String address = getValidatedAddress(scanner);

            // Validate and get password
            String password = getValidatedPassword(scanner);

            // Confirm password
            String confirmPassword = getConfirmedPassword(scanner, password);

            // Create a new User object
            User newUser = new User(name, email, address, password);

            if (userCount < users.length) {
                users[userCount] = newUser;
                userCount++;
                System.out.println("Registration successful!");
                return newUser;  // Return the User object upon successful registration
            } else {
                System.out.println("Registration failed. User limit reached.");
                return null;  // Return null to indicate registration failure
            }
        } else {
            // Cancel and redirect to the main menu
            return null;
        }
    }

    private static String getValidatedName(Scanner scanner) {
        String name;
        do {
            System.out.print("Enter your name (each word capitalized): ");
            name = scanner.nextLine().trim();
        } while (!name.matches("^(?i)[A-Z][a-z]*(\\s[A-Z][a-z]*)*$"));
        return name;
    }

    private static String getValidatedEmail(Scanner scanner) {
        String email;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();
        } while (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$"));
        return email;
    }

    private static String getValidatedAddress(Scanner scanner) {
        String address;
        do {
            System.out.print("Enter your address: ");
            address = scanner.nextLine().trim();
        } while (address.isEmpty());  // Basic validation, you can add more specific checks
        return address;
    }

    private static String getValidatedPassword(Scanner scanner) {
        String password;
        do {
            System.out.print("Enter your password (at least 6 characters, starting with a capital letter): ");
            password = scanner.nextLine().trim();
        } while (!password.matches("^[A-Z][a-zA-Z0-9]{5,}$"));
        return password;
    }

    private static String getConfirmedPassword(Scanner scanner, String originalPassword) {
        String confirmPassword;
        do {
            System.out.print("Confirm your password: ");
            confirmPassword = scanner.nextLine().trim();

            if (!confirmPassword.equals(originalPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            }
        } while (!confirmPassword.equals(originalPassword));
        return confirmPassword;
    }
}
