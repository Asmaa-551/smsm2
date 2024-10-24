import java.util.*;

public class NoisePollution extends EnvironmentalData implements DataOperations {
    private double noiseLevel; // Noise level in decibels (dB)

    // Reference to the BST instance that will store NoisePollution objects
    private static EnvironmentalBST noisePollutionBST = new EnvironmentalBST();

    // Constructor
    public NoisePollution(String locationName, double latitude, double longitude, int measurementTimestamp,
                          double noiseLevel) {
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
        // Traverse the BST to find the NoisePollution data based on the location
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
        noisePollutionBST.inorder(); // Assuming an inorder method exists in NoisePollutionBST
    }

    @Override
    public void displayInfo() {
        System.out.println("Noise Pollution Data for " + getLocationName() + ":");
        System.out.println("  Latitude: " + getLatitude());
        System.out.println("  Longitude: " + getLongitude());
        System.out.println("  Timestamp: " + getMeasurementTimestamp());
        System.out.println("  Noise Level: " + noiseLevel + " dB");
    }

    @Override
    public double getMeasurementValue() {
        return noiseLevel;
    }

    @Override
    public void updateMeasurement(double newValue) {
        this.noiseLevel = newValue;
        System.out.println("Updated measurement value to: " + newValue);
    }

    @Override
    public int compareTo(EnvironmentalData o) {
        if (o instanceof NoisePollution) {
            NoisePollution other = (NoisePollution) o;
            return Double.compare(this.noiseLevel, other.noiseLevel);
        }
        return 1; // Default comparison when compared with non-NoisePollution objects
    }

    @Override
    public String toString() {
        return "Location: " + getLocationName() + ", Noise Level: " + noiseLevel + " dB";
    }

    @Override
    public void displayRankings() {
    System.out.println("Noise Pollution Rankings (Best to Worst):");
    noisePollutionBST.reverseInOrder(noisePollutionBST.getRoot());
}
}
