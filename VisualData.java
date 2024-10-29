import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisualData {
    private static EnvironmentalBST environmentalBST;

    public VisualData(EnvironmentalBST environmentalBST) {
        this.environmentalBST = environmentalBST;
    }

    public static void visualizeAirQualityTrends() {
        System.out.println("Visualizing Air Quality Trends:");
        for (EnvironmentalData data : environmentalBST.getAllData()) { // Assume you have a method to get all data
            if (data instanceof AirQuality) {
                AirQuality airData = (AirQuality) data;
                List<Double> aqiHistory = airData.getAqiHistory();
                List<Date> timestampHistory = airData.getTimestampHistory();
                printTrends(aqiHistory, timestampHistory);
            }
        }
    }

    private static void printTrends(List<Double> values, List<Date> timestamps) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < values.size(); i++) {
            System.out.printf("Date: %s, AQI: %.2f%n", dateFormat.format(timestamps.get(i)), values.get(i));
        }
    }

    public static void visualizeWaterQualityTrends() {
        System.out.println("Visualizing Water Quality Trends:");
        for (EnvironmentalData data : environmentalBST.getAllData()) {
            if (data instanceof WaterQuality) {
                WaterQuality waterData = (WaterQuality) data;
                List<Double> wqiHistory = waterData.getWaterQualityIndexHistory(); // Assuming a similar method exists
                List<Date> timestampHistory = waterData.getTimestampHistory();
                printTrends(wqiHistory, timestampHistory);
            }
        }
    }

    public static void visualizeNoisePollutionTrends() {
        System.out.println("Visualizing Noise Pollution Trends:");
        for (EnvironmentalData data : environmentalBST.getAllData()) {
            if (data instanceof NoisePollution) {
                NoisePollution noiseData = (NoisePollution) data;
                List<Double> noiseHistory = noiseData.getNoiseLevelHistory(); // Assuming a similar method exists
                List<Date> timestampHistory = noiseData.getTimestampHistory();
                printTrends(noiseHistory, timestampHistory);
            }
        }
    }

    public static void displayHistogram(String title, double[] data) {
        System.out.println("\n" + title);
        System.out.println("===============================");
        
        for (double value : data) {
            // Scale the value for display; adjust the multiplier as needed for visibility
            int scaledValue = (int) (value * 0.5); // Scale down for histogram representation
            
            // Print the histogram line
            System.out.print(value + ": ");
            for (int i = 0; i < scaledValue; i++) {
                System.out.print("#");
            }
            System.out.println(); // Move to the next line
        }
    }
}



