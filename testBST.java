import java.util.Scanner;

public class testBST {
    
    private BST environmentalTree;
    private Scanner scanner;

    // Constructor
    public testBST() {
        environmentalTree = new BST();
        scanner = new Scanner(System.in);
    }

    // Method to display the menu
    public static void displayMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Environmental Quality Monitoring System");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Log new environmental data");
        System.out.println("2. Update existing environmental data");
        System.out.println("3. Delete environmental data");
        System.out.println("4. View environmental report");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    // Method for logging new environmental data
    public void logNewData() {
        // Implementation to be added
    }

    // Method for updating existing environmental data
    public void updateData() {
        // Implementation to be added
    }

    // Method for deleting environmental data
    public void deleteData() {
        // Implementation to be added
    }

    // Method for viewing the environmental report
    public void viewReport() {
        // Implementation to be added
    }

    // Method to handle user input and menu navigation
    public void run() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    logNewData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    deleteData();
                    break;
                case 4:
                    viewReport();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
