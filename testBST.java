import java.util.Scanner;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class testBST {
    static EnvironmentalBST BST = new EnvironmentalBST();
    static AirQuality airClass = new AirQuality();
    static WaterQuality waterClass = new WaterQuality();
    static NoisePollution noiseClass = new NoisePollution();  
    static Scanner scanner = new Scanner(System.in);
    static VisualData visualData = new VisualData(BST);

	//public static void main(String[] args) {
      //  int choice;

    public static void main(String[] args) {
        int choice = -1; // Initialize choice to an invalid value

        do {
            displayMenu();
            System.out.print("Enter your choice: ");

            // Validate input for choice
            while (true) {
                try {
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                        break; // Valid input
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next(); // Clear the invalid input
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                } catch (NoSuchElementException e) {
                    System.out.println("Input stream is closed or has been exhausted.");
                    return; // Exit the program or handle as needed
                }
            }

            // Handle the menu choice
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
                    displayRankings();
                    break;
                case 6:
                    backupOrRestoreData();
                    break;
                case 7:
                    visualizeEnvironmentalData(); 
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
        scanner.close(); // Close the scanner when done
    }
	// Method to display the menu
    public static void displayMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Environmental Quality Monitoring System");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Insert new environmental data or Upload a data from file");
        System.out.println("2. Update existing environmental data");
        System.out.println("3. Delete environmental data");
        System.out.println("4. Search for specific environmental data");
        System.out.println("5. Display Rankings");
        System.out.println("6. Backup data or Restore data");
        System.out.println("7. Visulazie environmental data");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------------------");
	}

    // Method for inserting new environmental data
	public static void insertNewData() {
        Scanner scanner = new Scanner(System.in);
        int typeChoice = 0;
        System.out.println("Select data insertion method:");
        System.out.println("1. Manually enter environmental data");
        System.out.println("2. Upload data from file");
        int inputChoice = scanner.nextInt();
        scanner.nextLine();

    switch (inputChoice) {
      case 1:
        System.out.println("Select the type of environmental data to insert:");
        System.out.println("1. Air Quality");
        System.out.println("2. Water Quality");
        System.out.println("3. Noise Pollution");
        typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after int

        if (typeChoice >= 1 && typeChoice <= 3) {
            break; // Valid choice, exit loop
        } else {
            System.out.println("Invalid choice. Please select a number between 1 and 3.");
        }

        // Gather common data for all types, with validation
        System.out.println("Enter the location name:");
        String locationName = scanner.nextLine().trim();
        if (locationName.isEmpty()) {
            System.out.println("Invalid input: Location name cannot be empty.");
            return;
        }

    
        EnvironmentalData existingData = null; // To hold existing data if found
    
        // Proceed with the type-specific search based on typeChoice
        switch (typeChoice) {
            case 1: // Air Quality
                existingData = airClass.search(locationName); // Search for existing data
                break;
    
            case 2: // Water Quality
                existingData = waterClass.search(locationName); // Search for existing data
                break;
    
            case 3: // Noise Pollution
                existingData = noiseClass.search(locationName); // Search for existing data
                break;
        }
        
        // If data exists, ask if the user wants to update it
        if (existingData != null) {
            System.out.println("Data for " + locationName + " already exists. Would you like to update it? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                // Update the measurement value
                double newValue;
                if (typeChoice == 1) { // Air Quality
                    System.out.println("Enter new AQI value (current: " + ((AirQuality) existingData).getAqi() + "): ");
                    newValue = getValidatedIntInput(scanner, "Air Quality Index (0 to 500)", 0, 500);
                    ((AirQuality) existingData).updateMeasurement(newValue);
                    System.out.println("Air Quality data updated successfully!");
                } else if (typeChoice == 2) { // Water Quality
                    System.out.println("Enter new Water Quality Index value (current: " + ((WaterQuality) existingData).getWaterQualityIndex() + "): ");
                    newValue = getValidatedIntInput(scanner, "Water Quality Index (0 to 100)", 0, 100);
                    ((WaterQuality) existingData).updateMeasurement(newValue);
                    System.out.println("Water Quality data updated successfully!");
                } else if (typeChoice == 3) { // Noise Pollution
                    System.out.println("Enter new Noise Level value (current: " + ((NoisePollution) existingData).getNoiseLevel() + "): ");
                    newValue = getValidatedDoubleInput(scanner, "Noise Level in decibels (0.0 to 200.0)", 0.0, 200.0);
                    ((NoisePollution) existingData).updateMeasurement(newValue);
                    System.out.println("Noise Pollution data updated successfully!");
                }
                return; // Exit method after updating
            } else {
                System.out.println("Data insertion cancelled.");
                return; // Exit method if not updating
            }
        }
    
        // Get latitude and longitude with validation
        double latitude = getValidatedDoubleInput(scanner, "latitude (-90 to 90)", -90.0, 90.0);
        double longitude = getValidatedDoubleInput(scanner, "longitude (-180 to 180)", -180.0, 180.0);
    
        // Handle the insertion of new data based on the type
        switch (typeChoice) {
            case 1:
                // Air Quality data with validation
                int aqi = getValidatedIntInput(scanner, "Air Quality Index (0 to 500)", 0, 500);
                AirQuality airData = new AirQuality(locationName, latitude, longitude, aqi);
                airClass.insert(airData); // Insert into the Air Quality BST
                System.out.println("Air Quality data inserted successfully!");
                break;
    
            case 2:
                // Water Quality data with validation
                int wqi = getValidatedIntInput(scanner, "Water Quality Index (0 to 100)", 0, 100);
                WaterQuality waterData = new WaterQuality(locationName, latitude, longitude, wqi);
                waterClass.insert(waterData); // Insert into the Water Quality BST
                System.out.println("Water Quality data inserted successfully!");
                break;
    
            case 3:
                // Noise Pollution data with validation
                double noiseLevel = getValidatedDoubleInput(scanner, "Noise Level in decibels (0.0 to 200.0)", 0.0, 200.0);
                NoisePollution noiseData = new NoisePollution(locationName, latitude, longitude, noiseLevel);
                noiseClass.insert(noiseData); // Insert into the Noise Pollution BST
                System.out.println("Noise Pollution data inserted successfully!");
                break;
        }
    case 2:
        // Prompt for filename and load data from file
        System.out.print("Enter filename to upload data from: ");
        String filename = scanner.nextLine();
        Data.loadData(filename);  // Assume Data.loadData(filename) adds all data to the system
        System.out.println("Data from file " + filename + " uploaded successfully.");
        break;
        default:
        System.out.println("Invalid choice. Please select either 1 or 2.");
        break;
}
}

    
    
    private static int getValidatedIntInput(Scanner scanner, String fieldName, int minValue, int maxValue) {
        int value;
        while (true) {
            System.out.println("Enter " + fieldName + ":");
            try {
                value = scanner.nextInt();
                if (value < minValue || value > maxValue) {
                    System.out.println("Invalid input: " + fieldName + " must be between " + minValue + " and " + maxValue + ".");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input: Please enter a valid integer.");
                scanner.next();
            }
        }
        return value;
    }
    
    private static double getValidatedDoubleInput(Scanner scanner, String fieldName, double minValue, double maxValue) {
        double value;
        while (true) {
            System.out.println("Enter " + fieldName + ":");
            try {
                value = scanner.nextDouble();
                if (value < minValue || value > maxValue) {
                    System.out.println("Invalid input: " + fieldName + " must be between " + minValue + " and " + maxValue + ".");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input: Please enter a valid number.");
                scanner.next();
            }
        }
        return value;
    }

    // Method for updating existing environmental data
    public static void updateData() {        
        // Prompt for type of environmental data (Air, Water, or Noise)
        System.out.println("Choose the data type to update (1: Air Quality, 2: Water Quality, 3: Noise): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        System.out.println("Enter the location name of the environmental data: ");
        String locationName = scanner.nextLine().trim();
    
        // Search for existing data based on location name
        EnvironmentalData existingData;
        if (choice == 1) {
            existingData = airClass.search(locationName);
        } else if (choice == 2) {
            existingData = waterClass.search(locationName);
        } else if (choice == 3) {
            existingData = noiseClass.search(locationName);
        } else {
            System.out.println("Invalid choice.");
            return;
        }    
        if (existingData == null) {
            // If no existing data found, ask user if they want to add it
            System.out.println("No existing data found for " + locationName + ".");
            System.out.println("Would you like to add it? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
    
            if (response.equals("yes")) {
                // Ask for new latitude and longitude
                double newLatitude, newLongitude;
    
                System.out.println("Enter latitude: ");
                newLatitude = scanner.nextDouble();
                System.out.println("Enter longitude: ");
                newLongitude = scanner.nextDouble();
    
                // Validate latitude and longitude
                if (newLatitude < -90 || newLatitude > 90 || newLongitude < -180 || newLongitude > 180) {
                    System.out.println("Invalid latitude or longitude values. Please enter valid coordinates.");
                    return;
                }
    
                // Create and insert new data object based on the choice
                switch (choice) {
                    case 1: // Air Quality
                        System.out.println("Enter AQI value: ");
                        int aqi = scanner.nextInt();
                        if (aqi < 0) { 
                           throw new IllegalArgumentException("AQI value Index must be greater than 0");
                          }
                        AirQuality newAirData = new AirQuality(locationName, newLatitude, newLongitude, scanner.nextInt());
                        airClass.insert(newAirData); // Assuming insert method is available
                        System.out.println("Air Quality data added successfully.");
                        break;
                    case 2: // Water Quality
                        System.out.println("Enter Water Quality Index value: ");
                        int wqi = scanner.nextInt();
                        if (wqi < 0) { 
                          throw new IllegalArgumentException("Water Quality Index must be greater than 0");
                          }
                        WaterQuality newWaterData = new WaterQuality(locationName, newLatitude, newLongitude, scanner.nextInt());
                        waterClass.insert(newWaterData);
                        System.out.println("Water Quality data added successfully.");
                        break;
                    case 3: // Noise
                        System.out.println("Enter Noise Level value: ");
                        int noiseLevel = scanner.nextInt();
                           if (noiseLevel < 0) {
                            throw new IllegalArgumentException("Noise Level must be greater than 0");
                         }
                        NoisePollution newNoiseData = new NoisePollution(locationName, newLatitude, newLongitude, scanner.nextInt());
                        noiseClass.insert(newNoiseData);
                        System.out.println("Noise data added successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        break;
                }
            } else {
                System.out.println("No data added.");
                return;
            }
        } else {
            // Existing data found; prompt for AQI update
            double newValue;
            switch (choice) {
                case 1: // Air Quality
                    if (existingData instanceof AirQuality) {
                        System.out.println("Enter new AQI value (current: " + ((AirQuality) existingData).getAqi() + "): ");
                        newValue = scanner.nextDouble();
                        airClass.updateMeasurement(newValue);
                    } else {
                        System.out.println("The data for this location is not of Air Quality type.");
                    }
                    break;
                case 2: // Water Quality
                    if (existingData instanceof WaterQuality) {
                        System.out.println("Enter new Water Quality Index value (current: " + ((WaterQuality) existingData).getWaterQualityIndex() + "): ");
                        newValue = scanner.nextDouble();
                        waterClass.updateMeasurement(newValue);
                    } else {
                        System.out.println("The data for this location is not of Water Quality type.");
                    }
                    break;
                case 3: // Noise
                    if (existingData instanceof NoisePollution) {
                        System.out.println("Enter new Noise Level value (current: " + ((NoisePollution) existingData).getNoiseLevel() + "): ");
                        newValue = scanner.nextDouble();
                        noiseClass.updateMeasurement(newValue);
                    } else {
                        System.out.println("The data for this location is not of Noise type.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        }
    }
    public static void deleteData() {
        System.out.println("Do you want to:");
        System.out.println("1. Delete a specific environmental data entry");
        System.out.println("2. Remove all old environmental data");
        
        int deleteChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
    
        switch (deleteChoice) {
            case 1:
                deleteDataSpecific();
                break;
            case 2:
                removeAllOldData();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }
    // Method for deleting environmental data
    public static void deleteDataSpecific() {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to select the type of data to delete
        System.out.println("Select the type of environmental data to delete:");
        System.out.println("1. Air Quality");
        System.out.println("2. Water Quality");
        System.out.println("3. Noise Pollution");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after int
    
        // Gather the location name to delete
        System.out.println("Enter the location name to delete:");
        String locationName = scanner.nextLine().trim();
        if (locationName.isEmpty()) {
            System.out.println("Invalid input: Location name cannot be empty.");
            return;
        }
    
        // Handle deletion based on the type of data
        switch (typeChoice) {
            case 1: // Air Quality
                AirQuality airData = (AirQuality) airClass.search(locationName); // Search for existing data
                if (airData != null) {
                    airClass.delete(locationName); // Delete from Air Quality BST
                    System.out.println("Deleted Air Quality data for " + locationName);
                } else {
                    System.out.println("Air Quality data not found for deletion: " + locationName);
                }
                break;
    
            case 2: // Water Quality
                WaterQuality waterData = (WaterQuality) waterClass.search(locationName); // Search for existing data
                if (waterData != null) {
                    waterClass.delete(locationName); // Delete from Water Quality BST
                    System.out.println("Deleted Water Quality data for " + locationName);
                } else {
                    System.out.println("Water Quality data not found for deletion: " + locationName);
                }
                break;
    
            case 3: // Noise Pollution
                NoisePollution noiseData = (NoisePollution) noiseClass.search(locationName); // Search for existing data
                if (noiseData != null) {
                    noiseClass.delete(locationName); // Delete from Noise Pollution BST
                    System.out.println("Deleted Noise Pollution data for " + locationName);
                } else {
                    System.out.println("Noise Pollution data not found for deletion: " + locationName);
                }
                break;
    
            default:
                System.out.println("Invalid choice. Please select a valid type of environmental data.");
                break;
        }
    }
    private static void removeAllOldData() {
        System.out.println("Are you sure you want to remove all old environmental data? (yes/no)");
        String confirmation = scanner.nextLine().trim().toLowerCase();
    
        if (confirmation.equals("yes")) {
            // Clear data from each class
            airClass.deleteOldData(); 
            waterClass.deleteOldData(); 
            noiseClass.deleteOldData(); 
            } else {
            System.out.println("No data was removed.");
        }
    }

    public static void searchData() {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to select the type of data to search for
        System.out.println("Select the type of environmental data to search:");
        System.out.println("1. Air Quality");
        System.out.println("2. Water Quality");
        System.out.println("3. Noise Pollution");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after int
    
        // Gather the location name to search for
        System.out.println("Enter the location name to search:");
        String locationName = scanner.nextLine().trim();
        if (locationName.isEmpty()) {
            System.out.println("Invalid input: Location name cannot be empty.");
            return;
        }
    
        // Handle search based on the type of data
        switch (typeChoice) {
            case 1: // Air Quality
                AirQuality airData = (AirQuality) airClass.search(locationName); // Search for existing data
                if (airData != null) {
                    // Use the displayInfo method to show air quality data details
                    airData.displayInfo();
                } else {
                    System.out.println("Air Quality data not found for location: " + locationName);
                }
                break;
    
            case 2: // Water Quality
                WaterQuality waterData = (WaterQuality) waterClass.search(locationName); // Search for existing data
                if (waterData != null) {
                    // Use the displayInfo method to show water quality data details
                    waterData.displayInfo();
                } else {
                    System.out.println("Water Quality data not found for location: " + locationName);
                }
                break;
    
            case 3: // Noise Pollution
                NoisePollution noiseData = (NoisePollution) noiseClass.search(locationName); // Search for existing data
                if (noiseData != null) {
                    // Assuming similar displayInfo() method exists in NoisePollution class
                    noiseData.displayInfo();
                } else {
                    System.out.println("Noise Pollution data not found for location: " + locationName);
                }
                break;
    
            default:
                System.out.println("Invalid choice. Please select a valid type of environmental data.");
                break;
        }
    }
    

    // Method for displaying environmental rankings (Best to Worst)
    public static void displayRankings() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select ranking order:");
        System.out.println("1. Best to Worst");
        System.out.println("2. Worst to Best");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Air Quality Rankings (Best to Worst):");
                airClass.displayRankings(); // Assuming reverseInorder method exists
                System.out.println("Water Quality Rankings (Best to Worst):");
                waterClass.displayRankings();
                System.out.println("Noise Pollution Rankings (Best to Worst):");
                noiseClass.displayRankings();
                break;

            case 2:
                System.out.println("Air Quality Rankings (Worst to Best):");
                airClass.displayRankingsReverse(); // Assuming inorder method exists
                System.out.println("Water Quality Rankings (Worst to Best):");
                waterClass.displayRankingsReverse();
                System.out.println("Noise Pollution Rankings (Worst to Best):");
                noiseClass.displayRankingsReverse();
                break;

            default:
                System.out.println("Invalid choice. Please select a valid ranking order.");
                break;
        }
    }

    // Method for backing up data
    public static void backupData() {
        System.out.println("What type of data do you want to backup?");
        System.out.println("1: Air Quality");
        System.out.println("2: Water Quality");
        System.out.println("3: Noise Pollution");
        System.out.print("Enter your choice: ");
    
        // Validate input for choice
        int choice = -1; // Initialize to an invalid value
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                } else {
                    break; // Valid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
    
        // Switch case to handle backup based on user choice
        switch (choice) {
            case 1:
                // Save Air Quality snapshot if there is data
                airClass.saveSnapshot();
                System.out.println("Air Quality data backed up successfully.");
                break; // Added break statement
                
            case 2:
                // Save Water Quality snapshot if there is data
                waterClass.saveSnapshot();
                System.out.println("Water Quality data backed up successfully.");
                break; // Added break statement
    
            case 3:
                // Save Noise Pollution snapshot if there is data
                noiseClass.saveSnapshot();
                System.out.println("Noise Pollution data backed up successfully.");
                break; // Added break statement
    
            default:
                // This case should not be reached due to validation above
                System.out.println("An unexpected error occurred.");
                break;
        }
    }
    private static double[] getAQIValuesForHistogram() {
        List<EnvironmentalData> allData = BST.getAllData();
        List<Double> aqiValues = new ArrayList<>();

        for (EnvironmentalData data : allData) {
            if (data instanceof AirQuality) {
                AirQuality airData = (AirQuality) data;
                aqiValues.addAll(airData.getAqiHistory());
            }
        }

        double[] aqiArray = new double[aqiValues.size()];
        for (int i = 0; i < aqiValues.size(); i++) {
            aqiArray[i] = aqiValues.get(i);
        }

        return aqiArray;
    }

    // Method for restoring data from backup
    public static void restoreData() {
        BST.restoreData();
    }

    public static void printSelectedTree() {
        Scanner scanner = new Scanner(System.in);
        int typeChoice = 0;
    
        // Prompt the user to select the type of tree to print
        while (true) {
            System.out.println("Select the type of environmental data tree to print:");
            System.out.println("1. Air Quality");
            System.out.println("2. Water Quality");
            System.out.println("3. Noise Pollution");
            typeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after int
    
            if (typeChoice >= 1 && typeChoice <= 3) {
                break; // Valid choice, exit loop
            } else {
                System.out.println("Invalid choice. Please select a number between 1 and 3.");
            }
        }
    
        // Call the print method on the appropriate tree based on the user's choice
        switch (typeChoice) {
            case 1:
                System.out.println("Printing Air Quality data:");
                    airClass.print(); // Directly call print method for Air Quality
                break;
    
            case 2:
                System.out.println("Printing Water Quality data:");
                    waterClass.print(); // Directly call print method for Water Quality
                break;
    
            case 3:
                System.out.println("Printing Noise Pollution data:");
                    noiseClass.print(); // Directly call print method for Noise Pollution
                break;
        }
    }    
    public static void visualizeEnvironmentalData() {
        System.out.println("Select the type of data to visualize:");
        System.out.println("1. Air Quality Trends");
        System.out.println("2. Water Quality Trends");
        System.out.println("3. Noise Pollution Trends");
        
        int choice = scanner.nextInt(); // Get user input for data type
        scanner.nextLine(); // Consume newline
        
        // Prompt for city name
        System.out.print("Enter the city name to visualize data for: ");
        String city = scanner.nextLine();

        // Call visualization based on user choice
        switch (choice) {
            case 1:
                System.out.println("Visualizing Air Quality Trends for " + city);
                visualData.visualizeByCityUsingSnapshot(city, "air");
                break;
            case 2:
                System.out.println("Visualizing Water Quality Trends for " + city);
                visualData.visualizeByCityUsingSnapshot(city, "water");
                break;
            case 3:
                System.out.println("Visualizing Noise Pollution Trends for " + city);
                visualData.visualizeByCityUsingSnapshot(city, "noise");
                break;
            default:
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                break;
        }        
    }

    // Method for backing up or restoring data
public static void backupOrRestoreData() {
    System.out.println("What would you like to do?");
    System.out.println("1: Backup data");
    System.out.println("2: Restore data");
    System.out.print("Enter your choice: ");
    
    // Validate input for choice
    int choice = -1; // Initialize to an invalid value
    while (true) {
        try {
            choice = scanner.nextInt();
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            } else {
                break; // Valid input
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
        }
    }

    // Handle backup or restore based on user choice
    switch (choice) {
        case 1:
            // Backup data
            backupData(); // Call existing backupData method
            break;
        case 2:
            // Restore data
            restoreData(); // Call existing restoreData method
            System.out.println("Data restored successfully.");
            break;
        default:
            // This case should not be reached due to validation above
            System.out.println("An unexpected error occurred.");
            break;
    }
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
                    displayRankings();
                    break;
                case 6:
                backupOrRestoreData();
                    break;
                case 7:
                    visualizeEnvironmentalData(); 
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
