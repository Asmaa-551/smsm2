import java.util.*;

public class WaterQuality extends EnvironmentalData implements DataOperations {
    private double waterQualityIndex; // Water Quality Index

    // Reference to the BST instance that will store WaterQuality objects
    private static EnvironmentalBST waterQualityBST = new EnvironmentalBST();

    // Constructor
    public WaterQuality(String locationName, double latitude, double longitude, int measurementTimestamp,
                        double waterQualityIndex) {
        super(locationName, latitude, longitude, measurementTimestamp);
        this.waterQualityIndex = waterQualityIndex;
    }

    // Getter and Setter for Water Quality Index
    public double getWaterQualityIndex() {
        return waterQualityIndex;
    }

    public void setWaterQualityIndex(double waterQualityIndex) {
        this.waterQualityIndex = waterQualityIndex;
    }

    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof AirQuality) {
            AirQuality airQualityData = (AirQuality) data;
            String locationName = airQualityData.getLocationName();
            int index = Collections.binarySearch(sortedLocations, locationName);
            if (index >= 0) {
                AirQuality existingData = (AirQuality) waterQualityBST.searchByLocation(locationName);
                existingData.setAqi(airQualityData.getAqi());
            } else {
                waterQualityBST.insert(airQualityData);
                System.out.println("Inserted air quality data for " + locationName);
                sortedLocations.add(-(index + 1), locationName);
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
            return Double.compare(this.waterQualityIndex, other.waterQualityIndex);
        }
        return 1; // Default comparison when compared with non-WaterQuality objects
    }

    @Override
    public String toString() {
        return "Location: " + getLocationName() + ", Water Quality Index: " + waterQualityIndex;
    }
}
