import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data {

    public static void loadData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length >= 5) {
                    String type = parts[0].trim(); // First part specifies the type (e.g., "Air" or "Noise")
                    String locationName = parts[1].trim();
                    double latitude = Double.parseDouble(parts[2].trim());
                    double longitude = Double.parseDouble(parts[3].trim());
                    double level = Double.parseDouble(parts[4].trim()); // AQI or noise level
                    
                    // Instantiate based on type and add to respective BST
                    if (type.equalsIgnoreCase("Air")) {
                        AirQuality airQualityData = new AirQuality(locationName, latitude, longitude, (int) level);
                        AirQuality.airQualityBST.insert(airQualityData);
                        System.out.println("Loaded air quality data for " + locationName);
                    } else if (type.equalsIgnoreCase("Noise")) {
                        NoisePollution noisePollutionData = new NoisePollution(locationName, latitude, longitude, level);
                        NoisePollution.noisePollutionBST.insert(noisePollutionData);
                        System.out.println("Loaded noise pollution data for " + locationName);
                    } else if (type.equalsIgnoreCase("Water")) { // Added for Water Quality
                        WaterQuality waterQualityData = new WaterQuality(locationName, latitude, longitude, (int) level);
                        WaterQuality.waterQualityBST.insert(waterQualityData);
                        System.out.println("Loaded water quality data for " + locationName);
                    } else {
                        System.out.println("Unknown data type for line: " + line);
                    }
                } else {
                    System.out.println("Invalid data format for line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values: " + e.getMessage());
        }
    }
}