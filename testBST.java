import java.util.Scanner;

public class testBST {
    
    private BST environmentalTree;
    private Scanner scanner;

    public testBST() {
        environmentalTree = new BST();
        scanner = new Scanner(System.in);
    }

    // Method to display the menu
    public static void displayMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Environmental Quality Monitoring System");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Insert new environmental data");
        System.out.println("2. Update existing environmental data");
        System.out.println("3. Delete environmental data");
        System.out.println("4. Search for specific environmental data");
        System.out.println("5. Display environmental rankings (Best to Worst)");
        System.out.println("6. Display environmental rankings (Worst to Best)");
        System.out.println("7. Backup data");
        System.out.println("8. Restore data from backup");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    // Method for inserting new environmental data
    public void insertNewData() {
        // Empty method (implementation to be added later)
    }

    // Method for updating existing environmental data
    public void updateData() {
        // Empty method (implementation to be added later)
    }

    // Method for deleting environmental data
    public void deleteData() {
        // Empty method (implementation to be added later)
    }

    // Method for searching specific environmental data
    public void searchData() {
        // Empty method (implementation to be added later)
    }

    // Method for displaying environmental rankings (Best to Worst)
    public void displayBestToWorst() {
        // Empty method (implementation to be added later)
    }

    // Method for displaying environmental rankings (Worst to Best)
    public void displayWorstToBest() {
        // Empty method (implementation to be added later)
    }

    // Method for backing up data
    public void backupData() {
        // Empty method (implementation to be added later)
    }

    // Method for restoring data from backup
    public void restoreData() {
        // Empty method (implementation to be added later)
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
                    insertNewData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    deleteData();
                    break;
                case 4:
                    searchData();
                    break;
                case 5:
                    displayBestToWorst();
                    break;
                case 6:
                    displayWorstToBest();
                    break;
                case 7:
                    backupData();
                    break;
                case 8:
                    restoreData();
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
