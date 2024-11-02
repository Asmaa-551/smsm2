import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisualData {
    private EnvironmentalBST environmentalBST;

    // Constructor to initialize with EnvironmentalBST instance
    public VisualData(EnvironmentalBST environmentalBST) {
        this.environmentalBST = environmentalBST;
    }

    // Method to visualize data trend for a specific parameter (air, water, or noise) for a city
    public void visualizeByCityUsingSnapshot(String city, String type) {
        String filename = "";

        // Determine the snapshot file based on the type
        switch (type.toLowerCase()) {
            case "air":
                filename = "air_copy_" + EnvironmentalBST.getAirSnapshotIndex() + ".txt";
                break;
            case "water":
                filename = "water_copy_" + EnvironmentalBST.getWaterSnapshotIndex() + ".txt";
                break;
            case "noise":
                filename = "noise_copy_" + EnvironmentalBST.getNoiseSnapshotIndex() + ".txt";
                break;
            default:
                System.out.println("Invalid type. Please choose 'air', 'water', or 'noise'.");
                return;
        }

        // Read data from the snapshot file for the specified city and parameter type
        List<Double> values = new ArrayList<>();
        List<Date> timestamps = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Assuming the structure: "timestamp,city,value"
                if (parts.length >= 4 && parts[1].equalsIgnoreCase(city)) { 
                    double value = Double.parseDouble(parts[2]); // Environmental index (AQI, Water Quality, or Noise Level)
                    Date timestamp = dateFormat.parse(parts[3]); // Timestamp
                    
                    values.add(value);
                    timestamps.add(timestamp);
                }
            }

            plotEnvironmentalTrend(type + " Quality Index Over Time for " + city, values, timestamps);

        } catch (IOException | ParseException e) {
            System.out.println("Error reading snapshot file: " + e.getMessage());
        }
    }

    // Method to plot environmental data trend (simplified for demonstration)
    private void plotEnvironmentalTrend(String title, List<Double> values, List<Date> timestamps) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Plotting " + title);

        for (int i = 0; i < values.size(); i++) {
            System.out.println(dateFormat.format(timestamps.get(i)) + " - " + values.get(i));
        }

    }
}
