import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AirQuality extends EnvironmentalData implements DataOperations {
    private int aqi;
    public static EnvironmentalBST airQualityBST = new EnvironmentalBST();
    private List<Double> aqiHistory; // List to store historical AQI values
    private List<Date> timestampHistory; // List to store corresponding timestamps


    // Constructor
    public AirQuality(String locationName, double latitude, double longitude, int aqi) {
        super(locationName, latitude, longitude);
        this.aqi = aqi;
        this.aqiHistory = new ArrayList<>();
        this.timestampHistory = new ArrayList<>();
    }
    public AirQuality(){}

    // Getter and Setter for AQI
    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    // Implementing DataOperations methods
    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof AirQuality) {
            AirQuality airQualityData = (AirQuality) data;
            String locationName = airQualityData.getLocationName();
            int index = Collections.binarySearch(sortedLocationsAir, locationName);
            if (index >= 0) {
                AirQuality existingData = (AirQuality) airQualityBST.searchByLocation(locationName);
                existingData.setAqi(airQualityData.getAqi());
            } else {
                airQualityBST.insert(airQualityData);
                System.out.println("Inserted air quality data for " + locationName);
                sortedLocationsAir.add(-(index + 1), locationName);
            }
        }
    }

    @Override
    public void update(String location, double newValue) {
        AirQuality existingData = (AirQuality) search(location);
        if (existingData != null) {
            existingData.setAqi((int) newValue); // Update the AQI
            System.out.println("Updated air quality data for " + location);
        } else {
            System.out.println("Location not found for update: " + location);
        }
    }

    @Override
    public EnvironmentalData search(String location) {
        // Traverse the BST to find the AirQuality data based on the location
        return airQualityBST.searchByLocation(location);
    }

    @Override
    public void delete(String location) {
        AirQuality existingData = (AirQuality) search(location);
        if (existingData != null) {
            airQualityBST.delete(existingData);
            System.out.println("Deleted air quality data for " + location);
        } else {
            System.out.println("Location not found for deletion: " + location);
        }
    }

    @Override
    public void displayAll() {
        System.out.println("Displaying all air quality data:");
        airQualityBST.inorder();
    }

    @Override
    public void displayInfo() {
        System.out.println("Air Quality Data for " + getLocationName() + ":");
        System.out.println("  Latitude: " + getLatitude());
        System.out.println("  Longitude: " + getLongitude());
        System.out.println("  Timestamp: " + getMeasurementTimestamp());
        System.out.println("  Air Quality Index (AQI): " + aqi);
    }

    @Override
    public double getMeasurementValue() {
        return aqi;
    }

    @Override
    public void updateMeasurement(double newValue) {
        this.aqi = (int) newValue;
        System.out.println("Updated AQI to: " + newValue);
    }

    @Override
    public int compareTo(EnvironmentalData o) {
        if (o instanceof AirQuality) {
            AirQuality other = (AirQuality) o;
            if (this.aqi <= other.aqi) {
                return -1; // `this` is less than `other`
            } else if (this.aqi > other.aqi) {
                return 1; // `this` is greater than `other`
            }
        }
        return 1; // Default for non-AirQuality comparisons
    }

    @Override
    public String toString() {
        return "City: " + getLocationName() + ", AQI: " + aqi;
    }

    @Override
    public void displayRankings() {
        System.out.println(" Noise Pollution Rankings (Best to Worst):");
        airQualityBST.inorder();
    }

    public void displayRankingsReverse() {
        System.out.println("Noise Pollution Rankings (Worst to Best):");
        airQualityBST.reverseInorder();
    }
    
    public void restoreSnapshot(int snapshotIndex) {
        String filename = "air_copy_" + snapshotIndex + ".txt";
        airQualityBST.clear(); // Clear existing air quality data before restoring

        // Restore data from the snapshot
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                // Ensure the parts length matches the structure with timestamp included
                if (parts.length >= 5) { // Adjusted for timestamp + 4 other attributes
                    String timestamp = parts[0];
                    String locationName = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    int aqi = Integer.parseInt(parts[4]);
        
                    // Create the AirQuality object and set the timestamp
                    AirQuality airQualityData = new AirQuality(locationName, latitude, longitude, aqi);
                    airQualityData.setMeasurementTimestamp(timestamp); // Set the timestamp
        
                    // Insert into the AirQuality BST
                    airQualityBST.insert(airQualityData);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the snapshot: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values from the snapshot: " + e.getMessage());
        }
    }
    
    public void saveSnapshot() {
    airQualityBST.saveRotatingSnapshot();
    }

    @Override
    public void displayCityAndQI() {
        System.out.println("  City: " + getLocationName());
        System.out.println("  AQI: " + getAqi());
    }
    public List<Double> getAqiHistory() {
        return aqiHistory;
    }

    public List<Date> getTimestampHistory() {
        return timestampHistory;
    }
    public void print() {
        System.out.println("Air Quality Data Tree:");
        airQualityBST.print("", airQualityBST.getRoot(), false);
    }
    public void deleteOldData() {
        LocalDateTime now = LocalDateTime.now();
        Duration maxAge = Duration.ofDays(30); // Duration set to 30 days
        List<String> oldLocations = new ArrayList<>(); // To store locations of old data

        // Traverse each location in sortedLocationsWater
        for (String location : sortedLocationsAir) {
            AirQuality data = (AirQuality) airQualityBST.searchByLocation(location);
            if (data != null) {
                LocalDateTime dataTimestamp = data.getTimestampForComparison();
                // Check if data is older than maxAge
                if (dataTimestamp != null && Duration.between(dataTimestamp, now).compareTo(maxAge) > 0) {
                    oldLocations.add(location); // Collect old data for removal
                }
            }
        }

        // Remove old data from both the BST and sorted locations
        for (String location : oldLocations) {
            AirQuality data = (AirQuality) airQualityBST.searchByLocation(location);
            if (data != null) {
                airQualityBST.delete(data); // Delete from BST
                sortedLocationsWater.remove(location); // Remove from sorted list
                System.out.println("Deleted old data for location: " + location);
            }
        }
    }
}

