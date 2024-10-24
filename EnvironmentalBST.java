import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EnvironmentalBST extends BST<EnvironmentalData>{
    private AirQuality airQualityData;
    private WaterQuality waterQualityData;
    private static final int MAX_SNAPSHOTS = 3;
    private int currentSnapshotIndex = 0;

    public EnvironmentalBST(){}
	public EnvironmentalBST(EnvironmentalData[] array) {
    for (int i = 0; i < array.length; i++)
           insert(array[i]);
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
    public void saveSnapshot(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            saveSnapshotRec(root, writer);
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
                    + airData.getMeasurementTimestamp() + "," + airData.getAqi());
            writer.newLine();
        } else if (node.element instanceof WaterQuality) {
            WaterQuality waterData = (WaterQuality) node.element;
            writer.write(waterData.getLocationName() + "," + waterData.getLatitude() + "," + waterData.getLongitude() + ","
                    + waterData.getMeasurementTimestamp() + "," + waterData.getWaterQualityIndex());
            writer.newLine();
        }

        saveSnapshotRec(node.right, writer);
    }

    public void saveRotatingSnapshot() {
        currentSnapshotIndex = (currentSnapshotIndex % MAX_SNAPSHOTS) + 1;

        String filename;
        if (root != null && root.element instanceof AirQuality) {
            filename = "air_copy" + currentSnapshotIndex + ".txt";
        } else if (root != null && root.element instanceof WaterQuality) {
            filename = "water_copy" + currentSnapshotIndex + ".txt";
        } else {
            return; // Do nothing if the root is null or of an unexpected type
        }

        saveSnapshot(filename);
    }


    public void restoreData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the data type to restore (1: Air Quality, 2: Water Quality): ");
        int choice = scanner.nextInt();
        int snapshotIndex;

        System.out.println("Choose the snapshot copy to restore (1, 2, or 3): ");
        snapshotIndex = scanner.nextInt();

        // Validate input
        if (snapshotIndex < 1 || snapshotIndex > 3) {
            System.out.println("Invalid snapshot index. Please enter 1, 2, or 3.");
            return;
        }

        if (choice == 1) {
            airQualityData.restoreSnapshot(snapshotIndex);
        } else if (choice == 2) {
            waterQualityData.restoreSnapshot(snapshotIndex);
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }   
    }
  
        public void reverseInOrderTraversal() {
            reverseInOrderTraversalRec(root);
        }
    
        private void reverseInOrderTraversalRec(TreeNode<EnvironmentalData> node) {
            if (node != null) {
                reverseInOrderTraversalRec(node.right);
                node.element.displayInfo(); 
                reverseInOrderTraversalRec(node.left);
            }
        }

        public void reverseInorder() {
            reverseInorder(root); 
        }
        private void reverseInorder(TreeNode<EnvironmentalData> node) {
                if (node == null) return;
                reverseInorder(node.right);
                System.out.println(node.element); 
                reverseInorder(node.left);
        }
    
    }


