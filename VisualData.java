import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisualData {
    private EnvironmentalBST environmentalBST;

    // Constructor to initialize with EnvironmentalBST instance
    public VisualData(EnvironmentalBST environmentalBST) {
        this.environmentalBST = environmentalBST;
    }

    // Method to visualize data for a single city
    public void visualizeByCity(String city) {
        EnvironmentalData data = environmentalBST.searchByLocation(city);

        if (data instanceof AirQuality || data instanceof WaterQuality || data instanceof NoisePollution) {
            System.out.println("Visualizing data for: " + city);
            
            if (data instanceof AirQuality) {
                plotEnvironmentalParameter("Air Quality Index (AQI)", ((AirQuality) data).getAqi(), data.getMeasurementTimestamp());
            } else if (data instanceof WaterQuality) {
                plotEnvironmentalParameter("Water Quality Index", ((WaterQuality) data).getWaterQualityIndex(), data.getMeasurementTimestamp());
            } else if (data instanceof NoisePollution) {
                plotEnvironmentalParameter("Noise Level", ((NoisePollution) data).getNoiseLevel(), data.getMeasurementTimestamp());
            }
        } else {
            System.out.println("Data for city " + city + " is incomplete or unavailable.");
        }
    }

    private void plotEnvironmentalParameter(String parameter, double value, String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try {
            Date date = dateFormat.parse(timestamp);  // Convert string to Date
            System.out.println("Plotting " + parameter + " at " + dateFormat.format(date) + ": " + value);
        } catch (ParseException e) {
            System.out.println("Error parsing timestamp: " + timestamp);
        }
    }
}
