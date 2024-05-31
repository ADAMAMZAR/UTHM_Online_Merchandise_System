import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdminDashboard adminDashboard = new AdminDashboard();
        StoreHelper.setAdminDashboard(adminDashboard);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            exit = displayMainMenu(scanner);
        }

        // Close the scanner outside the loop
        scanner.close();
    }

    public static boolean displayMainMenu(Scanner scanner) {
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

        return handleMainMenuChoice(choice, scanner);
    }

    static boolean handleMainMenuChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                registerUser(scanner);
                break;
            case 2:
                loginUser(scanner);
                break;
            case 3:
                loginAdmin(scanner);
                break;
            case 4:
                System.out.println("Exiting the application. Goodbye!");
                return true;  // Set exit condition to true
        }

        return false;  // Continue the loop
    }

    private static void registerUser(Scanner scanner) {
        User registeredUser = RegistrationForm.registerUser(scanner);

        if (registeredUser != null) {
            // Handle registered user, e.g., store the user object, display a message, etc.
            System.out.println("User registered successfully!");
        }
    }

    private static void loginUser(Scanner scanner) {
        User loggedInUser = LoginForm.loginUser(scanner);

        if (loggedInUser != null) {
            Store.runStore(loggedInUser, scanner);
        }
    }

    private static void loginAdmin(Scanner scanner) {
        Admin loggedInAdmin = LoginFormAdmin.loginAdmin(scanner);

        if (loggedInAdmin != null) {
            AdminDashboard.displayDashboard(loggedInAdmin, scanner);
        }
    }
}
