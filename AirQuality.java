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
    private List<Double> aqiHistory; 
    private List<Date> timestampHistory; 


    // Constructor
    public AirQuality(String locationName, double latitude, double longitude, int aqi) {
        super(locationName, latitude, longitude);
        this.aqi = aqi;
        this.aqiHistory = new ArrayList<>();
        this.timestampHistory = new ArrayList<>();
    }
    public AirQuality(){}

    
    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    // Update or add data to BST if it already exists.
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

    // Update the AQI of a location's current data
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

    // Provide comprehensive details about a single data entry
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
                return -1; 
            } else if (this.aqi > other.aqi) {
                return 1; 
            }
        }
        return 1; 
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
    
    // Snapshot restoration for a given index.
    public void restoreSnapshot(int snapshotIndex) {
        String filename = "air_copy_" + snapshotIndex + ".txt";
        airQualityBST.clear(); 

        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                // Parse and add data from snapshot files to BST.
                if (parts.length >= 5) { 
                    String timestamp = parts[0];
                    String locationName = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    int aqi = Integer.parseInt(parts[4]);
        
                    
                    AirQuality airQualityData = new AirQuality(locationName, latitude, longitude, aqi);
                    airQualityData.setMeasurementTimestamp(timestamp); // Set the timestamp
        
                    
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

    // Use rotating storage to save snapshots.
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
    // Remove data older than 30 days from the BST.
    public void deleteOldData() {
        LocalDateTime now = LocalDateTime.now();
        Duration maxAge = Duration.ofDays(30); 
        List<String> oldLocations = new ArrayList<>(); 

        
        for (String location : sortedLocationsAir) {
            AirQuality data = (AirQuality) airQualityBST.searchByLocation(location);
            if (data != null) {
                LocalDateTime dataTimestamp = data.getTimestampForComparison();
               
                if (dataTimestamp != null && Duration.between(dataTimestamp, now).compareTo(maxAge) > 0) {
                    oldLocations.add(location); 
                }
            }
        }

        // Remove obsolete data entries from BST
        for (String location : oldLocations) {
            AirQuality data = (AirQuality) airQualityBST.searchByLocation(location);
            if (data != null) {
                airQualityBST.delete(data); 
                sortedLocationsWater.remove(location); 
                System.out.println("Deleted old data for location: " + location);
            }
        }
    }
}

