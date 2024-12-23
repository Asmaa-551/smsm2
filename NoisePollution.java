import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class NoisePollution extends EnvironmentalData implements DataOperations {
    private double noiseLevel;
    public static EnvironmentalBST noisePollutionBST = new EnvironmentalBST();
    private List<Double> noiseLevelHistory; 
    private List<Date> timestampHistory; 

    // Constructor
    public NoisePollution(String locationName, double latitude, double longitude, double noiseLevel) {
        super(locationName, latitude, longitude);
        this.noiseLevel = noiseLevel;
        this.noiseLevelHistory = new ArrayList<>();
        this.timestampHistory = new ArrayList<>();
    }
    public NoisePollution(){}

    
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
            existingData.setNoiseLevel(newValue); 
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
        return 1; 
    }

    @Override
    public String toString() {
        return "City: " + getLocationName() + ", Noise Level: " + noiseLevel;
    }

    @Override
    public void displayRankings() {
        System.out.println(" Noise Pollution Rankings (Best to Worst):");
        noisePollutionBST.inorder();
    }

    public void displayRankingsReverse() {
        System.out.println("Noise Pollution Rankings (Worst to Best):");
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
                    String timestamp = parts[0];
                    String locationName = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    double noiseLevel = Double.parseDouble(parts[4]);
        
                    
                    NoisePollution noiseData = new NoisePollution(locationName, latitude, longitude, noiseLevel);
                    noiseData.setMeasurementTimestamp(timestamp); 
        
                    
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

    public void deleteOldData() {
        LocalDateTime now = LocalDateTime.now();
        Duration maxAge = Duration.ofDays(30); 
        List<String> oldLocations = new ArrayList<>(); 

        
        for (String location : sortedLocationsNoise) {
            NoisePollution data = (NoisePollution) noisePollutionBST.searchByLocation(location);
            if (data != null) {
                LocalDateTime dataTimestamp = data.getTimestampForComparison();
                
                if (dataTimestamp != null && Duration.between(dataTimestamp, now).compareTo(maxAge) > 0) {
                    oldLocations.add(location); 
                }
            }
        }

        
        for (String location : oldLocations) {
            NoisePollution data = (NoisePollution) noisePollutionBST.searchByLocation(location);
            if (data != null) {
                noisePollutionBST.delete(data); 
                sortedLocationsWater.remove(location); 
                System.out.println("Deleted old data for location: " + location);
            }
        }
    }
}
