import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WaterQuality extends EnvironmentalData implements DataOperations {
    private double waterQualityIndex; // Water Quality Index

    // Reference to the BST instance that will store WaterQuality objects
    private static EnvironmentalBST waterQualityBST = new EnvironmentalBST();

    // Constructor
    public WaterQuality(String locationName, double latitude, double longitude,
                        double waterQualityIndex) {
        super(locationName, latitude, longitude);
        this.waterQualityIndex = waterQualityIndex;
    }
    public WaterQuality(){}

    // Getter and Setter for Water Quality Index
    public double getWaterQualityIndex() {
        return waterQualityIndex;
    }

    public void setWaterQualityIndex(double waterQualityIndex) {
        this.waterQualityIndex = waterQualityIndex;
    }

    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof WaterQuality) {
            WaterQuality waterQualityData = (WaterQuality) data;
            String locationName = waterQualityData.getLocationName();
            int index = Collections.binarySearch(sortedLocationsWater, locationName);
            if (index >= 0) {
                WaterQuality existingData = (WaterQuality) waterQualityBST.searchByLocation(locationName);
                existingData.setWaterQualityIndex(waterQualityData.getWaterQualityIndex());
            } else {
                waterQualityBST.insert(waterQualityData);
                System.out.println("Inserted water quality data for " + locationName);
                sortedLocationsWater.add(-(index + 1), locationName);
            }
        }
    }

    @Override
    public void update(String location, double newValue) {
        WaterQuality existingData = (WaterQuality) search(location);
        if (existingData != null) {
            existingData.setWaterQualityIndex(newValue); // Update the water quality index
            System.out.println("Updated water quality data for " + location);
        } else {
            System.out.println("Location not found for update: " + location);
        }
    }

    @Override
    public EnvironmentalData search(String location) {
        // Traverse the BST to find the WaterQuality data based on the location
        return waterQualityBST.searchByLocation(location);
    }

    @Override
    public void delete(String location) {
        WaterQuality existingData = (WaterQuality) search(location);
        if (existingData != null) {
            waterQualityBST.delete(existingData);
            System.out.println("Deleted water quality data for " + location);
        } else {
            System.out.println("Location not found for deletion: " + location);
        }
    }

    @Override
    public void displayAll() {
        System.out.println("Displaying all water quality data:");
        waterQualityBST.inorder(); // Assuming an inorder method exists in WaterQualityBST
    }

    @Override
    public void displayInfo() {
        System.out.println("Water Quality Data for " + getLocationName() + ":");
        System.out.println("  Latitude: " + getLatitude());
        System.out.println("  Longitude: " + getLongitude());
        System.out.println("  Timestamp: " + getMeasurementTimestamp());
        System.out.println("  Water Quality Index: " + waterQualityIndex);
    }

    @Override
    public double getMeasurementValue() {
        return waterQualityIndex;
    }

    @Override
    public void updateMeasurement(double newValue) {
        this.waterQualityIndex = newValue;
        System.out.println("Updated measurement value to: " + newValue);
    }

    @Override
    public int compareTo(EnvironmentalData o) {
        if (o instanceof WaterQuality) {
            WaterQuality other = (WaterQuality) o;
            if (this.waterQualityIndex <= other.waterQualityIndex) {
                return -1; // `this` is less than or equal to `other`
            } else if (this.waterQualityIndex > other.waterQualityIndex) {
                return 1; // `this` is greater than `other`
            }
        }
        return 1; // Default for non-WaterQuality comparisons
    }

    @Override
    public String toString() {
        return "Location: " + getLocationName() + ", Water Quality Index: " + waterQualityIndex;
    }

    public void restoreSnapshot(int snapshotIndex) {
        String filename = "water_copy" + snapshotIndex + ".txt";
        waterQualityBST.clear(); // Clear existing water quality data before restoring
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Update to match the number of parts written in saveSnapshot
                    String locationName = parts[0];
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    double waterQualityIndex = Double.parseDouble(parts[3]);
    
                    WaterQuality waterQualityData = new WaterQuality(locationName, latitude, longitude, waterQualityIndex);
                    waterQualityBST.insert(waterQualityData); // Insert into the WaterQuality BST
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void saveSnapshot() {
        waterQualityBST.saveRotatingSnapshot();
    }

    @Override
    public void displayRankings() {
        System.out.println("Water Quality Rankings (Best to Worst):");
        waterQualityBST.reverseInorder();
    }

    public void displayRankingsReverse() {
        System.out.println("Water Quality Rankings (Best to Worst):");
        waterQualityBST.inorder();
    }

    @Override
    public void displayCityAndQI() {
        System.out.println("  City: " + getLocationName());
        System.out.println("  Noise Level: " + getWaterQualityIndex());
    }
}
