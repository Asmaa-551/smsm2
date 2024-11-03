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
    private List<Double> values = new ArrayList<>();  
    private List<Date> timestamps = new ArrayList<>(); 

    
    public VisualData(EnvironmentalBST environmentalBST) {
        this.environmentalBST = environmentalBST;
    }

    
    public void visualizeByCityUsingSnapshot(String city, String type) {
        int snapshotIndex = 1;  

        
        while (true) {
            String filename = determineFilename(type, snapshotIndex);
    
            
            if (filename.isEmpty()) {
                System.out.println("Invalid type. Please choose 'air', 'water', or 'noise'.");
                return;
            }
    
            
            if (!loadDataFromFile(filename, city)) {
                
                break;
            }
    
            snapshotIndex++;  
        }
    
        
        plotEnvironmentalTrend(type + " Quality Index Over Time for " + city, values, timestamps);
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

                
                if (parts.length >= 5 && parts[1].equalsIgnoreCase(city)) {
                    double value = Double.parseDouble(parts[4]); 
                    Date timestamp = dateFormat.parse(parts[0]); 

                    values.add(value);        
                    timestamps.add(timestamp); 
                    dataLoaded = true; 
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading snapshot file: " + e.getMessage());
        }
        return dataLoaded;
    }

   
    private void plotEnvironmentalTrend(String title, List<Double> values, List<Date> timestamps) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Plotting " + title);

        for (int i = 0; i < values.size(); i++) {
            System.out.println(dateFormat.format(timestamps.get(i)) + " - " + values.get(i));
        }
    }
}
