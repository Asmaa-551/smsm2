

	// public static void main(String[] args) {
	// 	BST_Integer tree1, tree2;
	// 	int[] intArray = {18, 4, 10, 60, 40, 100, 2, 1, 200, 250, 30, 3, 5, 6, 150, 45};
	// 	//Creating the special tree1 as shown in the figure provided in the assignment
	// 	tree1 = new BST_Integer(intArray);
	// 	tree2 = new BST_Integer(intArray);
	// 	tree2.delete(4);

	// 	System.out.print("InOrder : ");  tree1.inorder();System.out.print("\n"); 
	// 	System.out.print("postOrder : ");  tree1.postorder();System.out.print("\n"); 
	// 	System.out.print("PreOrder : ");  tree1.preorder();System.out.print("\n"); 
		
	// 	System.out.println("Tree1: "); 
	// 	tree1.print();
	// }


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
public class testBST {
    public static void main(String[] args) {
        EnvironmentalBST bst = new EnvironmentalBST();
        Scanner scanner = new Scanner(System.in);

        // Test Case 1: Inserting new data points
        System.out.println("Enter the number of environmental data points to insert:");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        for (int i = 0; i < n; i++) {
            System.out.println("Enter city name:");
            String cityName = scanner.nextLine();

            System.out.println("Enter AQI:");
            int aqi = scanner.nextInt();

            System.out.println("Enter Water Quality Index:");
            int waterQuality = scanner.nextInt();

            System.out.println("Enter Noise Pollution Level:");
            int noiseLevel = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            // Automatically generate the current timestamp
            LocalDateTime timestamp = LocalDateTime.now();

            // Insert into BST
            bst.insert(new EnvironmentalData(cityName, aqi, waterQuality, noiseLevel, timestamp));
        }

        // Test Case 2: Searching for specific environmental data
        System.out.println("Enter city name to search for:");
        String searchCity = scanner.nextLine();
        EnvironmentalData searchResult = bst.search(searchCity);
        if (searchResult != null) {
            System.out.println("Found data: " + searchResult);
        } else {
            System.out.println("City not found.");
        }

        // Test Case 3: Updating existing data
        System.out.println("Enter city name to update data for:");
        String updateCity = scanner.nextLine();
        EnvironmentalData updateData = bst.search(updateCity);
        if (updateData != null) {
            System.out.println("Enter new AQI:");
            int newAqi = scanner.nextInt();
            System.out.println("Enter new Water Quality Index:");
            int newWaterQuality = scanner.nextInt();
            System.out.println("Enter new Noise Pollution Level:");
            int newNoiseLevel = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            // Automatically generate a new timestamp for the update
            LocalDateTime newTimestamp = LocalDateTime.now();

            // Update data
            updateData.setAqi(newAqi);
            updateData.waterQualityIndex(newWaterQuality);
            updateData.setNoiseLevel(newNoiseLevel);
            updateData.setTimestamp(newTimestamp);
            System.out.println("Data updated successfully.");
        } else {
            System.out.println("City not found.");
        }

        // Test Case 4: Removing outdated data
        System.out.println("Enter city name to delete:");
        String deleteCity = scanner.nextLine();
        boolean isDeleted = bst.delete(deleteCity);
        if (isDeleted) {
            System.out.println("Data deleted successfully.");
        } else {
            System.out.println("City not found or could not be deleted.");
        }

        // Test Case 5: Displaying rankings based on AQI
        bst.reverseInOrderTraversal();

        // Test Case 6: Handling tied AQI values
        // Insert multiple cities with the same AQI to test how the system handles it
        bst.insert(new EnvironmentalData("City1", 100, 80, 60, LocalDateTime.now()));
        bst.insert(new EnvironmentalData("City2", 100, 85, 65, LocalDateTime.now()));
        bst.insert(new EnvironmentalData("City3", 100, 90, 70, LocalDateTime.now()));
        System.out.println("Inserted cities with tied AQI values (100).");
        bst.reverseInOrderTraversal();

        // Test Case 7: Simulating a larger dataset
        try {
            File file = new File("environmental_data.txt"); // Adjust file path as needed
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(","); // Assuming data is comma-separated

                String cityName = data[0];
                int aqi = Integer.parseInt(data[1]);
                int waterQuality = Integer.parseInt(data[2]);
                int noiseLevel = Integer.parseInt(data[3]);

                // Automatically generate the current timestamp for file input as well
                LocalDateTime timestamp = LocalDateTime.now();

                // Insert into BST
                bst.insert(new EnvironmentalData(cityName, aqi, waterQuality, noiseLevel, timestamp));
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        // Test Case 8: Data backup and restoration (optional advanced feature)        
        // Backup current tree
        EnvironmentalBST backupTree = bst.cloneTree();
        System.out.println("Backup created.");

        // Make some changes to the original tree (insertions, deletions)
        System.out.println("Making changes to the original tree...");
        bst.insert(new EnvironmentalData("NewCity", 55, 70, 65, LocalDateTime.now()));
        bst.delete("City1");

        // Restore from backup
        System.out.println("Restoring from backup...");
        bst.restoreFromBackup(backupTree);
        bst.printTree(); // Should display the original tree as it was before changes

        // Close scanner
        scanner.close();
    }
}



