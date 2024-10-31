import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class NoisePollution extends EnvironmentalData implements DataOperations {
    private double noiseLevel;
    public static EnvironmentalBST noisePollutionBST = new EnvironmentalBST();
    private List<Double> noiseLevelHistory; // List to store historical noise levels
    private List<Date> timestampHistory; // List to store corresponding timestamps

    // Constructor
    public NoisePollution(String locationName, double latitude, double longitude, double noiseLevel) {
        super(locationName, latitude, longitude);
        this.noiseLevel = noiseLevel;
        this.noiseLevelHistory = new ArrayList<>();
        this.timestampHistory = new ArrayList<>();
        recordNoiseLevel(noiseLevel); // Record initial noise level
    }
    public NoisePollution(){}

    // Getter and Setter for Noise Level
    public double getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(double noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    // Implementing DataOperations methods
    @Override
    public void insert(EnvironmentalData data) {
        if (data instanceof NoisePollution) {
            NoisePollution noiseData = (NoisePollution) data;
            String locationName = noiseData.getLocationName();
            int index = Collections.binarySearch(sortedLocationsNoise, locationName);
            if (index >= 0) {
                NoisePollution existingData = (NoisePollution) noisePollutionBST.searchByLocation(locationName);
                existingData.setNoiseLevel(noiseData.getNoiseLevel());
            } else {
                noisePollutionBST.insert(noiseData);
                System.out.println("Inserted noise pollution data for " + locationName);
                sortedLocationsNoise.add(-(index + 1), locationName);
            }
        }
    }

    @Override
    public void update(String location, double newValue) {
        NoisePollution existingData = (NoisePollution) search(location);
        if (existingData != null) {
            existingData.setNoiseLevel(newValue); // Update the noise level
            System.out.println("Updated noise pollution data for " + location);
        } else {
            System.out.println("Location not found for update: " + location);
        }
    }

    @Override
    public EnvironmentalData search(String location) {
        return noisePollutionBST.searchByLocation(location);
    }

    @Override
    public void delete(String location) {
        NoisePollution existingData = (NoisePollution) search(location);
        if (existingData != null) {
            noisePollutionBST.delete(existingData);
            System.out.println("Deleted noise pollution data for " + location);
        } else {
            System.out.println("Location not found for deletion: " + location);
        }
    }

    @Override
    public void displayAll() {
        System.out.println("Displaying all noise pollution data:");
        noisePollutionBST.inorder();
    }

    @Override
    public void displayInfo() {
        System.out.println("Noise Pollution Data for " + getLocationName() + ":");
        System.out.println("  Latitude: " + getLatitude());
        System.out.println("  Longitude: " + getLongitude());
        System.out.println("  Timestamp: " + getMeasurementTimestamp());
        System.out.println("  Noise Level: " + noiseLevel);
    }

    @Override
    public double getMeasurementValue() {
        return noiseLevel;
    }

    @Override
    public void updateMeasurement(double newValue) {
        this.noiseLevel = newValue;
        System.out.println("Updated noise level to: " + newValue);
    }

    @Override
    public int compareTo(EnvironmentalData o) {
        if (o instanceof NoisePollution) {
            NoisePollution other = (NoisePollution) o;
            return Double.compare(this.noiseLevel, other.noiseLevel);
        }
        return 1; // Default for non-NoisePollution comparisons
    }

    @Override
    public String toString() {
        return "City: " + getLocationName() + ", Noise Level: " + noiseLevel;
    }

    @Override
    public void displayRankings() {
        System.out.println("Noise Pollution Rankings (Best to Worst):");
        noisePollutionBST.reverseInorder(); 
    }
    public void restoreSnapshot(int snapshotIndex) {
        String filename = "noise_copy" + snapshotIndex + ".txt";
        noisePollutionBST.clear(); // Clear existing noise pollution data before restoring
    
        // Restore data from the snapshot
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Validate the number of parts
                if (parts.length >= 4) { // Ensure this matches the actual attributes saved
                    String locationName = parts[0];
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    double noiseLevel = Double.parseDouble(parts[3]); // Adjust as needed
    
                    // Create and insert the NoisePollution object
                    NoisePollution noiseData = new NoisePollution(locationName, latitude, longitude, noiseLevel);
                    noisePollutionBST.insert(noiseData);
                } else {
                    System.out.println("Invalid data format in line: " + line);
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
        noisePollutionBST.saveRotatingSnapshot();
    }

    @Override
    public void displayCityAndQI() {
        System.out.println("  City: " + getLocationName());
        System.out.println("  Noise Level: " + getNoiseLevel());
    }

    public void displayRankingsReverse() {
        System.out.println("Water Quality Rankings (Best to Worst):");
        noisePollutionBST.inorder();
    }

    public void recordNoiseLevel(double noiseLevel) {
        this.noiseLevelHistory.add(noiseLevel);
        this.timestampHistory.add(new Date()); // Store the current time
    }
    public List<Double> getNoiseLevelHistory() {
        return noiseLevelHistory;
    }

    public List<Date> getTimestampHistory() {
        return timestampHistory;
    }
    public void print() {
        System.out.println("Noise Pollution Data Tree:");
        noisePollutionBST.print("", noisePollutionBST.getRoot(), false);
    }
}
