import java.util.Scanner;

public class LoginForm {
    public static User loginUser(Scanner scanner) {
        int choice;

        do {
            System.out.println("Choose an option:");
            System.out.println("1. Login");
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
            // Continue with login
            User user = loginUserInMemory(scanner);

            if (user != null) {
                // Set the authenticated user
                StoreHelper.setUser(user);
                System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
                return user;
            } else {
                System.out.println("Login failed. Please check your credentials and try again.");
                return null;
            }
        } else {
            // Cancel and redirect to main menu
            return null;
        }
    }

    private static User loginUserInMemory(Scanner scanner) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        return authenticateUserInMemory(email, password, RegistrationForm.users);
    }

    private static User authenticateUserInMemory(String email, String password, User[] users) {
        for (User user : users) {
            if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Set the username to the first word of the name
                String firstName = extractFirstName(user.getName());
                user.setUsername(firstName);
                return user;
            }
        }
        return null;
    }

    private static String extractFirstName(String fullName) {
        // Split the full name into words and return the first word
        String[] words = fullName.split("\\s+");
        if (words.length > 0) {
            return words[0];
        } else {
            return ""; // Return an empty string if the full name is empty
        }
    }



}
