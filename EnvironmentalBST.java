import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EnvironmentalBST extends BST<EnvironmentalData> {
    private AirQuality airQualityData;
    private WaterQuality waterQualityData;
    private NoisePollution noisePollutionData; // Added for noise pollution
    private int airSnapshotIndex = 0; // To keep track of Air Quality snapshots
    private int waterSnapshotIndex = 0; // To keep track of Water Quality snapshots
    private int noiseSnapshotIndex = 0; // To keep track of Noise Pollution snapshots

    public EnvironmentalBST() {}

    public EnvironmentalBST(EnvironmentalData[] array) {
        for (EnvironmentalData data : array) {
            insert(data);
        }
    }

    public EnvironmentalData searchByLocation(String location) {
        return searchByLocation(root, location);
    }

    private EnvironmentalData searchByLocation(TreeNode<EnvironmentalData> node, String location) {
        if (node == null) {
            return null;
        }

        EnvironmentalData data = node.element;

        if (data.getLocationName().equals(location)) {
            return data;
        }

        EnvironmentalData foundData = searchByLocation(node.left, location);
        if (foundData != null) {
            return foundData;
        }

        return searchByLocation(node.right, location);
    }

    
    public void saveRotatingSnapshot() {
        String filename = "";

        // Determine the appropriate filename based on the type of data in the tree
        if (root != null && root.element instanceof AirQuality) {
            airSnapshotIndex++;
            filename = "air_copy_" + airSnapshotIndex + ".txt"; // Use the index for the filename
        } else if (root != null && root.element instanceof WaterQuality) {
            waterSnapshotIndex++;
            filename = "water_copy_" + waterSnapshotIndex + ".txt";
        } else if (root != null && root.element instanceof NoisePollution) {
            noiseSnapshotIndex++;
            filename = "noise_copy_" + noiseSnapshotIndex + ".txt";
        } else {
            System.out.println("No data available to save.");
            return;
        }

        // Save the snapshot using the generated filename
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            saveSnapshotRec(root, writer); // Call the existing method directly to save data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSnapshotRec(TreeNode<EnvironmentalData> node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return;
        }
        saveSnapshotRec(node.left, writer);

        if (node.element instanceof AirQuality) {
            AirQuality airData = (AirQuality) node.element;
            writer.write(airData.getLocationName() + "," + airData.getLatitude() + "," + airData.getLongitude() + "," 
                         + airData.getAqi());
            writer.newLine();
        } else if (node.element instanceof WaterQuality) {
            WaterQuality waterData = (WaterQuality) node.element;
            writer.write(waterData.getLocationName() + "," + waterData.getLatitude() + "," + waterData.getLongitude() + "," 
                         + waterData.getWaterQualityIndex());
            writer.newLine();
        } else if (node.element instanceof NoisePollution) { 
            NoisePollution noiseData = (NoisePollution) node.element;
            writer.write(noiseData.getLocationName() + "," + noiseData.getLatitude() + "," + noiseData.getLongitude() + "," 
                         + noiseData.getNoiseLevel());
            writer.newLine();
        }

        saveSnapshotRec(node.right, writer);
    }
    //restorsion 
    public void restoreData() {
        Scanner scanner = new Scanner(System.in);
    
        // Prompt user to choose the data type to restore
        System.out.println("Choose the data type to restore (1: Air Quality, 2: Water Quality, 3: Noise Pollution): ");
        int choice = scanner.nextInt();
    
        int snapshotIndex;
    
        // Display available snapshot options based on the user's choice
        switch (choice) {
            case 1:
                // Display Air Quality snapshots if the user chooses Air Quality
                displayAirSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                // Validate the snapshot index for Air Quality
                if (!isValidAirSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Air Quality. Please enter a valid index.");
                    return;
                }
                airQualityData.restoreSnapshot(snapshotIndex - 1); // Using snapshotIndex - 1 to adjust for 0-based index
                break;
    
            case 2:
                // Display Water Quality snapshots if the user chooses Water Quality
                displayWaterSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                // Validate the snapshot index for Water Quality
                if (!isValidWaterSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Water Quality. Please enter a valid index.");
                    return;
                }
                waterQualityData.restoreSnapshot(snapshotIndex - 1); // Using snapshotIndex - 1 to adjust for 0-based index
                break;
    
            case 3:
                // Display Noise Pollution snapshots if the user chooses Noise Pollution
                displayNoiseSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                // Validate the snapshot index for Noise Pollution
                if (!isValidNoiseSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Noise Pollution. Please enter a valid index.");
                    return;
                }
                noisePollutionData.restoreSnapshot(snapshotIndex - 1); // Using snapshotIndex - 1 to adjust for 0-based index
                break;
    
            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }
    
    // Method to display available air snapshot options
    private void displayAirSnapshotOptions() {
        System.out.println("Available Air Quality snapshots: ");
        for (int i = 0; i < airSnapshotIndex; i++) { // Use airSnapshotIndex to determine available snapshots
            System.out.println("Air Copy " + (i + 1)); // Displaying the snapshots as 1-based index
        }
    }
    
    // Method to display available water snapshot options
    private void displayWaterSnapshotOptions() {
        System.out.println("Available Water Quality snapshots: ");
        for (int i = 0; i < waterSnapshotIndex; i++) { // Use waterSnapshotIndex to determine available snapshots
            System.out.println("Water Copy " + (i + 1)); // Displaying the snapshots as 1-based index
        }
    }
    
    // Method to display available noise snapshot options
    private void displayNoiseSnapshotOptions() {
        System.out.println("Available Noise Pollution snapshots: ");
        for (int i = 0; i < noiseSnapshotIndex; i++) { // Use noiseSnapshotIndex to determine available snapshots
            System.out.println("Noise Copy " + (i + 1)); // Displaying the snapshots as 1-based index
        }
    }
    
    // Method to check if the snapshot index is valid for Air Quality
    private boolean isValidAirSnapshotIndex(int index) {
        return index > 0 && index <= airSnapshotIndex; // Adjust based on how you count snapshots
    }
    
    // Method to check if the snapshot index is valid for Water Quality
    private boolean isValidWaterSnapshotIndex(int index) {
        return index > 0 && index <= waterSnapshotIndex; // Adjust based on how you count snapshots
    }
    
    // Method to check if the snapshot index is valid for Noise Pollution
    private boolean isValidNoiseSnapshotIndex(int index) {
        return index > 0 && index <= noiseSnapshotIndex; // Adjust based on how you count snapshots
    }
    public void reverseInorder() {
        reverseInorder(root);
    }

    private void reverseInorder(TreeNode<EnvironmentalData> node) {
        if (node == null) return;
        reverseInorder(node.right);
        node.element.displayCityAndQI();
        reverseInorder(node.left);
    }

    @Override /* Inorder traversal from the root */
    public void inorder() {
         inorder(root);
    }
   
    /* Inorder traversal from a subtree */
    protected void inorder(TreeNode<EnvironmentalData> root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print("(" + root.element + ") ");
        inorder(root.right);
    }
}
