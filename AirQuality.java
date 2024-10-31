import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        recordAQI(aqi); // Record initial AQI value
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
        System.out.println("Air Quality Rankings (Best to Worst):");
        airQualityBST.reverseInorder(); 
    }
    
    public void restoreSnapshot(int snapshotIndex) {
        String filename = "air_copy" + snapshotIndex + ".txt";
        airQualityBST.clear(); // Clear existing air quality data before restoring

        // Restore data from the snapshot
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure that the parts length matches your save method structure
                if (parts.length >= 4) { // Adjust to your actual number of attributes
                    String locationName = parts[0];
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    int aqi = Integer.parseInt(parts[3]); // Ensure this matches your saved structure
    
                    AirQuality airQualityData = new AirQuality(locationName, latitude, longitude, aqi);
                    airQualityBST.insert(airQualityData); // Insert into the AirQuality BST
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
        System.out.println("  Noise Level: " + getAqi());
    }

    public void displayRankingsReverse() {
        System.out.println("Water Quality Rankings (Best to Worst):");
        airQualityBST.inorder();
    }

    public void recordAQI(double aqi) {
        this.aqiHistory.add(aqi);
        this.timestampHistory.add(new Date()); // Store the current time
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
}

