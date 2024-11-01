import java.util.List;
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
            AirQuality airData = (AirQuality) AirQuality.airQualityBST.searchByLocation(city);
            WaterQuality waterData = (WaterQuality) WaterQuality.waterQualityBST.searchByLocation(city);
            NoisePollution noiseData = (NoisePollution) NoisePollution.noisePollutionBST.searchByLocation(city);
    
            if (airData != null && waterData != null && noiseData != null) {
                System.out.println("Visualizing data for: " + city);
    
                plotEnvironmentalParameter("Air Quality Index (AQI)", airData.getAqiHistory(), airData.getTimestampHistory());
                plotEnvironmentalParameter("Water Quality Index", waterData.getWaterQualityIndexHistory(), waterData.getTimestampHistory());
                plotEnvironmentalParameter("Noise Level", noiseData.getNoiseLevelHistory(), noiseData.getTimestampHistory());
            } else {
                System.out.println("Data for city " + city + " is incomplete or unavailable.");
            }
        }
    
        private void plotEnvironmentalParameter(String parameter, List<Double> values, List<Date> timestamps) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Plotting " + parameter + " over time:");
            for (int i = 0; i < values.size(); i++) {
                System.out.printf("%s: %.2f%n", dateFormat.format(timestamps.get(i)), values.get(i));
            }
        }
    }
    