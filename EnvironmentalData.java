public abstract class EnvironmentalData {
    private String locationName;      // Name of the geographical location
    private double latitude;           // Latitude of the location
    private double longitude;          // Longitude of the location
    private int measurementTimestamp;  // Timestamp of the measurement

    // Constructor to initialize common attributes
    public EnvironmentalData(String locationName, double latitude, double longitude, int measurementTimestamp) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.measurementTimestamp = measurementTimestamp;
    }

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

    public int getMeasurementTimestamp() {
        return measurementTimestamp;
    }

    public void setMeasurementTimestamp(int measurementTimestamp) {
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
}