import java.util.*;
public class WaterQuality extends EnvironmentalData implements DataOperations {
    private double waterQualityIndex; 
    private static List<WaterQuality> waterQualityDataList = new ArrayList<>();
    public WaterQuality(String locationName, double latitude, double longitude, int measurementTimestamp,
                        double waterQualityIndex) {
        super(locationName, latitude, longitude, measurementTimestamp);
        this.waterQualityIndex = waterQualityIndex;
    }
    // Implementing DataOperations methods
    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof WaterQuality) {
            waterQualityDataList.add((WaterQuality) data);
            System.out.println("Inserted water quality data for " + data.getLocationName());
        }
    }

    @Override
    public void update(String location, double newValue) {
        for (WaterQuality waterQuality : waterQualityDataList) {
            if (waterQuality.getLocationName().equals(location)) {
                waterQuality.waterQualityIndex = newValue; // Update the water quality index
                System.out.println("Updated water quality data for " + location);
                return;
            }
        }
        System.out.println("Location not found for update: " + location);
    }

    @Override
    public EnvironmentalData search(String location) {
        for (WaterQuality waterQuality : waterQualityDataList) {
            if (waterQuality.getLocationName().equals(location)) {
                System.out.println("Found water quality data for " + location);
                return waterQuality;
            }
        }
        System.out.println("No data found for location: " + location);
        return null;
    }

    @Override
    public void delete(String location) {
        waterQualityDataList.removeIf(waterQuality -> waterQuality.getLocationName().equals(location));
        System.out.println("Deleted water quality data for " + location);
    }

    @Override
    public void displayAll() {
        System.out.println("Displaying all water quality data:");
        for (WaterQuality waterQuality : waterQualityDataList) {
            waterQuality.displayInfo(); 
        }
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
}
