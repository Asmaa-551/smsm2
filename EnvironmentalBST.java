import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class EnvironmentalBST extends BST<EnvironmentalData> {
    // Static counters keep track of snapshot indices for each data type.
    private static int airSnapshotIndex = 0; 
    private static int waterSnapshotIndex = 0; 
    private static int noiseSnapshotIndex = 0; 

    public EnvironmentalBST() {}

    public EnvironmentalBST(EnvironmentalData[] array) {
        for (EnvironmentalData data : array) {
            insert(data);
        }
    }

    public EnvironmentalData searchByLocation(String location) {
        return searchByLocation(root, location);
    }

    // A method for searching the tree for EnvironmentalData using a location name.
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

    // Method for saving the BST's current state to a snapshot file.
    public void saveRotatingSnapshot() {
        String filename = "";

        // Determine the kind of data in the tree and set the filename appropriately.
        if (root != null && root.element instanceof AirQuality) {
            airSnapshotIndex++;
            filename = "air_copy_" + airSnapshotIndex + ".txt"; 
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

       // Use resources to safely write files.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            saveSnapshotRec(root, writer); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getAirSnapshotIndex() {
        return airSnapshotIndex;
    }
    
    public static int getWaterSnapshotIndex() {
        return waterSnapshotIndex;
    }
    
    public static int getNoiseSnapshotIndex() {
        return noiseSnapshotIndex;
    }    

    // Recursive helper function for writing tree data to a file during an in-order traverse.
    private void saveSnapshotRec(TreeNode<EnvironmentalData> node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return;
        }
        saveSnapshotRec(node.left, writer);

        // Write data to file based on the kind of environmental data.
        if (node.element instanceof AirQuality) {
            AirQuality airData = (AirQuality) node.element;
            writer.write(airData.getMeasurementTimestamp() + "," + airData.getLocationName() + "," 
                         + airData.getLatitude() + "," + airData.getLongitude() + "," + airData.getAqi());
            writer.newLine();
        } else if (node.element instanceof WaterQuality) {
            WaterQuality waterData = (WaterQuality) node.element;
            writer.write(waterData.getMeasurementTimestamp() + "," + waterData.getLocationName() + "," 
                         + waterData.getLatitude() + "," + waterData.getLongitude() + "," 
                         + waterData.getWaterQualityIndex());
            writer.newLine();
        } else if (node.element instanceof NoisePollution) { 
            NoisePollution noiseData = (NoisePollution) node.element;
            writer.write(noiseData.getMeasurementTimestamp() + "," + noiseData.getLocationName() + "," 
                         + noiseData.getLatitude() + "," + noiseData.getLongitude() + "," 
                         + noiseData.getNoiseLevel());
            writer.newLine();
        }
        
        saveSnapshotRec(node.right, writer);
    }
    // Method for restoring data from a specified snapshot.
    public void restoreData() {
        Scanner scanner = new Scanner(System.in);
    
       
        System.out.println("Choose the data type to restore (1: Air Quality, 2: Water Quality, 3: Noise Pollution): ");
        int choice = scanner.nextInt();
    
        int snapshotIndex;
    
        switch (choice) {
            case 1:
                displayAirSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                if (!isValidAirSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Air Quality. Please enter a valid index.");
                    return;
                }
                testBST.airClass.restoreSnapshot(snapshotIndex); 
                break;
    
            case 2:
                
                displayWaterSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                
                if (!isValidWaterSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Water Quality. Please enter a valid index.");
                    return;
                }
                testBST.waterClass.restoreSnapshot(snapshotIndex); 
                break;
    
            case 3:
                
                displayNoiseSnapshotOptions();
                System.out.println("Enter the snapshot copy number to restore: ");
                snapshotIndex = scanner.nextInt();
    
                
                if (!isValidNoiseSnapshotIndex(snapshotIndex)) {
                    System.out.println("Invalid snapshot index for Noise Pollution. Please enter a valid index.");
                    return;
                }
                testBST.noiseClass.restoreSnapshot(snapshotIndex); 
                break;
    
            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }
    
    
    private void displayAirSnapshotOptions() {
        if (airSnapshotIndex == 0) {
            System.out.println("No Air Quality snapshots available.");
            return;
        }
        System.out.println("Available Air Quality snapshots: ");
        for (int i = 0; i < airSnapshotIndex; i++) {
            System.out.println("Air Copy " + (i + 1));
        }
    }
    
   
    private void displayWaterSnapshotOptions() {
        System.out.println("Available Water Quality snapshots: ");
        for (int i = 0; i < waterSnapshotIndex; i++) { 
            System.out.println("Water Copy " + (i + 1)); 
        }
    }
    
    
    private void displayNoiseSnapshotOptions() {
        System.out.println("Available Noise Pollution snapshots: ");
        for (int i = 0; i < noiseSnapshotIndex; i++) { 
            System.out.println("Noise Copy " + (i + 1)); 
        }
    }
    
    
    private boolean isValidAirSnapshotIndex(int index) {
        return index > 0 && index <= airSnapshotIndex; 
    }
    
    
    private boolean isValidWaterSnapshotIndex(int index) {
        return index > 0 && index <= waterSnapshotIndex; 
    }
    
    
    private boolean isValidNoiseSnapshotIndex(int index) {
        return index > 0 && index <= noiseSnapshotIndex; 
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

    @Override 
    public void inorder() {
         inorder(root);
    }
   
   
    protected void inorder(TreeNode<EnvironmentalData> node) {
        if (node == null) return;
        inorder(node.left);
        node.element.displayCityAndQI();
        inorder(node.right);
    }
    // Method for collecting all environmental data in the BST into a list.
    public List<EnvironmentalData> getAllData() {
        List<EnvironmentalData> dataList = new ArrayList<>();
        collectAllData(root, dataList); 
        return dataList; 
    }

  
    private void collectAllData(TreeNode<EnvironmentalData> node, List<EnvironmentalData> dataList) {
        if (node == null) {
            return; 
        }
        collectAllData(node.left, dataList); 
        dataList.add(node.element); 
        collectAllData(node.right, dataList); 
    }
}
