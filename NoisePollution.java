import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
public class NoisePollution extends EnvironmentalData implements DataOperations {
    private double noiseLevel;
    public static EnvironmentalBST noisePollutionBST = new EnvironmentalBST();

    // Constructor
    public NoisePollution(String locationName, double latitude, double longitude, long measurementTimestamp, double noiseLevel) {
        super(locationName, latitude, longitude, measurementTimestamp);
        this.noiseLevel = noiseLevel;
    }

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
            int index = Collections.binarySearch(sortedLocations, locationName);
            if (index >= 0) {
                NoisePollution existingData = (NoisePollution) noisePollutionBST.searchByLocation(locationName);
                existingData.setNoiseLevel(noiseData.getNoiseLevel());
            } else {
                noisePollutionBST.insert(noiseData);
                System.out.println("Inserted noise pollution data for " + locationName);
                sortedLocations.add(-(index + 1), locationName);
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
        noisePollutionBST.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String locationName = parts[0];
                    double latitude = Double.parseDouble(parts[1]);
                    double longitude = Double.parseDouble(parts[2]);
                    long measurementTimestamp = Long.parseLong(parts[3]);
                    double noiseLevel = Double.parseDouble(parts[4]);

                    NoisePollution noiseData = new NoisePollution(locationName, latitude, longitude, measurementTimestamp, noiseLevel);
                    noisePollutionBST.insert(noiseData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSnapshot(String filename) {
        noisePollutionBST.saveRotatingSnapshot();
    }

    @Override
    public void displayCityAndQI() {
        System.out.println("  City: " + getLocationName());
        System.out.println("  Noise Level: " + getNoiseLevel());
    }
}
