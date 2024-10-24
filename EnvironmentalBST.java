import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EnvironmentalBST extends BST<EnvironmentalData> {
    private AirQuality airQualityData;
    private WaterQuality waterQualityData;
    private NoisePollution noisePollutionData; // Added for noise pollution
    private static final int MAX_SNAPSHOTS = 3;
    private int currentSnapshotIndex = 0;

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
        } else if (node.element instanceof NoisePollution) { // Added for noise pollution
            NoisePollution noiseData = (NoisePollution) node.element;
            writer.write(noiseData.getLocationName() + "," + noiseData.getLatitude() + "," + noiseData.getLongitude() + ","
                    + noiseData.getMeasurementTimestamp() + "," + noiseData.getNoiseLevel());
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
        } else if (root != null && root.element instanceof NoisePollution) { // Added for noise pollution
            filename = "noise_copy" + currentSnapshotIndex + ".txt";
        } else {
            return;
        }

        saveSnapshot(filename);
    }

    public void restoreData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the data type to restore (1: Air Quality, 2: Water Quality, 3: Noise Pollution): ");
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
        } else if (choice == 3) { // Added for noise pollution
            noisePollutionData.restoreSnapshot(snapshotIndex);
        } else {
            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
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
}
