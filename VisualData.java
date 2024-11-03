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
    private List<Double> values = new ArrayList<>();  // Keep track of all values across multiple files
    private List<Date> timestamps = new ArrayList<>(); // Keep track of all timestamps across multiple files

    // Constructor to initialize with EnvironmentalBST instance
    public VisualData(EnvironmentalBST environmentalBST) {
        this.environmentalBST = environmentalBST;
    }

    // Method to visualize data trend for a specific parameter (air, water, or noise) for a city
    public void visualizeByCityUsingSnapshot(String city, String type) {
        int snapshotIndex = 1;  // Start with the first snapshot file index

        // Loop through all available snapshot files for the specified type
        while (true) {
            String filename = determineFilename(type, snapshotIndex);
    
            // Check if filename is empty (invalid type) or file does not exist
            if (filename.isEmpty()) {
                System.out.println("Invalid type. Please choose 'air', 'water', or 'noise'.");
                return;
            }
    
            // Attempt to load data from the snapshot file for the specified city
            if (!loadDataFromFile(filename, city)) {
                // Exit the loop when no more files are available
                break;
            }
    
            snapshotIndex++;  // Move to the next snapshot file
        }
    
        
        plotEnvironmentalTrend(type + " Quality Index Over Time for " + city, values, timestamps);
    }

        
    // Method to determine the filename based on the parameter type
    private String determineFilename(String type) {
        switch (type.toLowerCase()) {
            case "air":
                return "air_copy_" + EnvironmentalBST.getAirSnapshotIndex() + ".txt";
            case "water":
                return "water_copy_" + EnvironmentalBST.getWaterSnapshotIndex() + ".txt";
            case "noise":
                return "noise_copy_" + EnvironmentalBST.getNoiseSnapshotIndex() + ".txt";
            default:
                return "";
        }
    }
    private String determineFilename(String type, int index) {
        switch (type.toLowerCase()) {
            case "air":
                return "air_copy_" + index + ".txt";
            case "water":
                return "water_copy_" + index + ".txt";
            case "noise":
                return "noise_copy_" + index + ".txt";
            default:
                return "";
        }
    }

    private boolean loadDataFromFile(String filename, String city) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean dataLoaded = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Assuming the structure: "timestamp,locationName,latitude,longitude,value"
                if (parts.length >= 5 && parts[1].equalsIgnoreCase(city)) {
                    double value = Double.parseDouble(parts[4]); // Environmental index
                    Date timestamp = dateFormat.parse(parts[0]); // Timestamp

                    values.add(value);        // Append new value
                    timestamps.add(timestamp); // Append new timestamp
                    dataLoaded = true; 
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading snapshot file: " + e.getMessage());
        }
        return dataLoaded;
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
