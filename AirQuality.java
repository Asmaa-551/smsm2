import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class AirQuality extends EnvironmentalData implements DataOperations {
    private int aqi;
    public static EnvironmentalBST airQualityBST = new EnvironmentalBST();


    // Constructor
    public AirQuality(String locationName, double latitude, double longitude, long measurementTimestamp, int aqi) {
        super(locationName, latitude, longitude, measurementTimestamp);
        this.aqi = aqi;
    }

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
            int index = Collections.binarySearch(sortedLocations, locationName);
            if (index >= 0) {
                AirQuality existingData = (AirQuality) airQualityBST.searchByLocation(locationName);
                existingData.setAqi(airQualityData.getAqi());
            } else {
                airQualityBST.insert(airQualityData);
                System.out.println("Inserted air quality data for " + locationName);
                sortedLocations.add(-(index + 1), locationName);
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
        airQualityBST.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String locationName = parts[0];
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    long measurementTimestamp = Long.parseLong(parts[3]);
                    int aqi = Integer.parseInt(parts[4]);

                    AirQuality airQualityData = new AirQuality(locationName, latitude, longitude, measurementTimestamp, aqi);
                    airQualityBST.insert(airQualityData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void saveSnapshot(String filename) {
    airQualityBST.saveRotatingSnapshot();
    }

    @Override
    public void displayCityAndQI() {
        System.out.println("  City: " + getLocationName());
        System.out.println("  Noise Level: " + getAqi());
    }


}
