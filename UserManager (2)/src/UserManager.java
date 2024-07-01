import java.util.Scanner;

public class UserManager {
    private final DatabaseManager dbManager = new DatabaseManager();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.dbManager.createUserTable(); // Ensure table exists on startup
        userManager.run();
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    saveUser();
                    break;
                case 2:
                    getAllUsers();
                    break;
                case 3:
                    getUserById();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Save User");
        System.out.println("2. Get All Users");
        System.out.println("3. Get User by ID");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void saveUser() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter profession: ");
        String profession = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        User user = new User(0, firstName, lastName, age, profession, address);
        dbManager.saveUser(user);
    }

    private void getAllUsers() {
        dbManager.getAllUsers();
    }

    private void getUserById() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User user = dbManager.getUserById(userId);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void updateUser() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User existingUser = dbManager.getUserById(userId);
        if (existingUser == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new profession: ");
        String profession = scanner.nextLine();
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        User newUser = new User(userId, firstName, lastName, age, profession, address);
        dbManager.updateUser(newUser);
    }

    private void deleteUser() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        dbManager.deleteUser(userId);
    }

    public void setScanner(Scanner testScanner) {
    }
}
