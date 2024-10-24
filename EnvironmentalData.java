import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class EnvironmentalData implements Comparable<EnvironmentalData> {

    public static ArrayList<String> sortedLocationsWater = new ArrayList<>();
    public static ArrayList<String> sortedLocationsAir = new ArrayList<>();
    public static ArrayList<String> sortedLocationsNoise = new ArrayList<>();

    private String locationName; // Name of the geographical location
    private double latitude; // Latitude of the location
    private double longitude; // Longitude of the location
    private String measurementTimestamp; // Timestamp of the measurement

    // Constructor to initialize common attributes
    public EnvironmentalData(String locationName, double latitude, double longitude) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.measurementTimestamp = getFormattedCurrentTimestamp();
    }
    public EnvironmentalData(){}

    @Override
    public abstract int compareTo(EnvironmentalData o);

    // Getters and Setters
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMeasurementTimestamp() {
        return measurementTimestamp;
    }

    public void setMeasurementTimestamp(String measurementTimestamp) {
        this.measurementTimestamp = measurementTimestamp;
    }

    // Abstract method to get the measurement value
    public abstract double getMeasurementValue();

    // Abstract method to update the measurement
    public abstract void updateMeasurement(double newValue);

    // Method to display information about the environmental data
    public void displayInfo() {
        System.out.println("Location: " + locationName);
        System.out.println("Coordinates: (" + latitude + ", " + longitude + ")");
        System.out.println("Timestamp: " + measurementTimestamp);
    }

    public abstract void displayRankings();
    public abstract void displayCityAndQI();

    private String getFormattedCurrentTimestamp() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }


    // Method to convert formatted timestamp back to LocalDateTime for comparison
    public LocalDateTime getTimestampForComparison() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(measurementTimestamp, formatter);
        } catch (DateTimeParseException e) {
            // Handle parsing error if needed
            return null; // or throw an exception, depending on your error handling strategy
        }
    }

    // Example method for comparison
    public boolean isMoreRecentThan(EnvironmentalData other) {
        LocalDateTime thisTimestamp = this.getTimestampForComparison();
        LocalDateTime otherTimestamp = other.getTimestampForComparison();

        return thisTimestamp != null && otherTimestamp != null && thisTimestamp.isAfter(otherTimestamp);
    }

}
